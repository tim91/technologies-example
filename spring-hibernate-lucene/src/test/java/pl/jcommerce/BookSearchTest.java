package pl.jcommerce;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.search.FullTextQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import pl.jcommerce.domain.Book;
import pl.jcommerce.repository.BookRepository;
import pl.jcommerce.search.BookSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class BookSearchTest {

    @Autowired
    private BookSearch bookSearch;

    @Autowired
    private BookRepository bookRepo;

    private static StopWatch watch;

    @BeforeClass
    public static void onTestStart() {
        watch = new StopWatch();
    }

    @AfterClass
    public static void onTestEnd() {
        System.out.println(watch.prettyPrint());
    }

    @Before
    public void prepareData() {

        Book lokomotywa = new Book();
        lokomotywa.setTitle("Lokomotywa");
        Calendar gc = GregorianCalendar.getInstance();
        gc.set(2010, 5, 15);
        lokomotywa.setPublicationDate(new GregorianCalendar(2010, Calendar.JULY, 15).getTime());
        lokomotywa.setPageNumbers(100L);
        lokomotywa.setContent("books/lokomotywa.pdf");
        bookRepo.save(lokomotywa);

        Book lokomotywa2 = new Book();
        lokomotywa2.setTitle("Lokomotywa 2");
        lokomotywa2.setPublicationDate(new GregorianCalendar(2010, Calendar.JULY, 10).getTime());
        System.out.println("publicDate: " + lokomotywa2.getPublicationDate().getTime());
        lokomotywa2.setPageNumbers(99L);
        lokomotywa2.setContent("books/ksiazka.pdf");
        bookRepo.save(lokomotywa2);

        Book l2 = new Book();
        l2.setTitle("Książka");
        l2.setPageNumbers(200L);
        l2.setContent("books/ksiazka.pdf");
        bookRepo.save(l2);
    }

    @After
    public void clear() {
        bookSearch.clearIndex();
        bookRepo.deleteAllInBatch();
    }

    @Test
    public void searchByNameTest() {

        watch.start("Lucene with DB access");
        
        List<Book> result = bookSearch.search("title:Lokomotywa");
        Assert.assertEquals(2, result.size());
        
        result = bookSearch.search("title:lokomotywa");
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("title:2");
        Assert.assertEquals(1, result.size());

        result = bookSearch.search("+title:lokomotywa");
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("+title:lokomotywa 2");
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("+title:2");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());

        result = bookSearch.search("+title:Książka");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Książka", result.get(0).getTitle());

        result = bookSearch.search("+title:lokomotywa -title:książka");
        Assert.assertEquals(2, result.size());
        watch.stop();
        
    }

    @Test
    public void findByNumericValuesTest() {

        List<Book> result = bookSearch.rangeQuery("pageNumbers", 99L, 99L);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());

        result = bookSearch.rangeQuery("pageNumbers", 99L, 100L);
        Assert.assertEquals(2, result.size());

        result = bookSearch.rangeQuery("pageNumbers", 99L, 200L);
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void findByRangeDateTest() {

        List<Book> res = bookSearch.rangeQuery("publicationDate", new GregorianCalendar(2010, Calendar.JULY, 10).getTime(),
                new GregorianCalendar(2010, Calendar.JULY, 10).getTime());

        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Lokomotywa 2", res.get(0).getTitle());

        res = bookSearch.rangeQuery("publicationDate", new GregorianCalendar(2010, Calendar.JULY, 10).getTime(),
                new GregorianCalendar(2010, Calendar.JULY, 15).getTime());

        Assert.assertEquals(2, res.size());
    }

    @Test
    public void fullTextSearchTest() {

        List<Book> res = bookSearch.search("+title:Lokomotywa +content:przy*");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Lokomotywa", res.get(0).getTitle());

        res = bookSearch.search("+title:książka -content:przy*");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Książka", res.get(0).getTitle());

        res = bookSearch.search("+title:książka +content:wiem");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Książka", res.get(0).getTitle());

        res = bookSearch.search("+title:książka -content:wiem");
        Assert.assertEquals(0, res.size());

        res = bookSearch.search("+content:wiem");
        Assert.assertEquals(2, res.size());

    }

    @Test
    public void bookUpdateTest() {
        List<Book> result = bookSearch.search("title:Lokomotywa");
        Assert.assertEquals(2, result.size());

        Book b = bookRepo.findByTitle("Lokomotywa");
        b.setTitle("lokomotywka");
        bookRepo.save(b);

        result = bookSearch.search("title:Lokomotywa");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());

        result = bookSearch.search("title:lokomotywka");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("lokomotywka", result.get(0).getTitle());
    }

    @Test
    public void projectionSearchTest() {
        
        watch.start("Only Lucene");
        List<Object[]> result = bookSearch.search("title:Lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());
        
        result = bookSearch.search("title:lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("title:2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());

        result = bookSearch.search("+title:lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("+title:lokomotywa 2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("+title:2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0)[1]);

        result = bookSearch.search("+title:Książka", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Książka", result.get(0)[1]);

        result = bookSearch.search("+title:lokomotywa -title:książka", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());
        
        watch.stop();
    }
}
