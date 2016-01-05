package pl.jcommerce;

import java.util.Calendar;
import java.util.Date;
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
    
    private String title;
    private long pageNumbers;
    private String contentPath;
    private Date publicationDate;

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

        this.title = "Lokomotywa";
        this.pageNumbers = 100;
        this.contentPath = "books/lokomotywa.pdf";
        this.publicationDate = new GregorianCalendar(2010, Calendar.JULY, 15).getTime();
        prepareBook();
        
        this.title = "Lokomotywa 2";
        this.pageNumbers = 99;
        this.contentPath = "books/ksiazka.pdf";
        this.publicationDate = new GregorianCalendar(2010, Calendar.JULY, 10).getTime();
        prepareBook();
        
        this.title = "Książka";
        this.pageNumbers = 200;
        this.contentPath = "books/ksiazka.pdf";
        this.publicationDate = null;
        prepareBook();
        
        this.title = "Artykuł";
        this.pageNumbers = 2;
        this.contentPath = "books/artykuł.txt";
        this.publicationDate = null;
        prepareBook();
    }

    private void prepareBook(){
        
        Book bookToSave = new Book();
        bookToSave.setTitle(this.title);
        bookToSave.setPageNumbers(this.pageNumbers);
        bookToSave.setContent(this.contentPath);
        bookToSave.setPublicationDate(this.publicationDate);
        bookRepo.save(bookToSave);
    }
    
    @After
    public void clear() {
        bookSearch.clearIndex();
        bookRepo.deleteAllInBatch();
    }

    @Test
    public void searchByNameTest() {

        watch.start("Lucene with DB access");
        
        List<Book> result = bookSearch.search("+title:Lokomotywa");
        Assert.assertEquals(2, result.size());
        
        result = bookSearch.search("+title:lokomotywa");
        Assert.assertEquals(2, result.size());

        result = bookSearch.search("+title:2");
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

        res = bookSearch.search("+content: aktywów wolnych od podatku");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Artykuł", res.get(0).getTitle());
        
        res = bookSearch.search("+title:artykuł");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Artykuł", res.get(0).getTitle());
    }

    @Test
    public void searchBookUpdateTest() {
        List<Book> result = bookSearch.search("+title:Lokomotywa");
        Assert.assertEquals(2, result.size());

        Book b = bookRepo.findByTitle("Lokomotywa");
        b.setTitle("lokomotywka");
        bookRepo.save(b);

        result = bookSearch.search("+title:Lokomotywa");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());

        result = bookSearch.search("+title:lokomotywka");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("lokomotywka", result.get(0).getTitle());
    }

    @Test
    public void searchBookDeleteTest() {
        List<Book> result = bookSearch.search("+title:Lokomotywa");
        Assert.assertEquals(2, result.size());

        Book b = bookRepo.findByTitle("Lokomotywa");
        bookRepo.delete(b);

        result = bookSearch.search("+title:Lokomotywa");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());
    }
    
    @Test
    public void searchByNameUsingProjectionTest() {
        
        watch.start("Only Lucene");
        List<Object[]> result = bookSearch.searchUsingProjection("+title:Lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());
        
        result = bookSearch.searchUsingProjection("+title:lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.searchUsingProjection("+title:2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());

        result = bookSearch.searchUsingProjection("+title:lokomotywa", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.searchUsingProjection("+title:lokomotywa 2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());

        result = bookSearch.searchUsingProjection("+title:2", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0)[1]);

        result = bookSearch.searchUsingProjection("+title:Książka", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Książka", result.get(0)[1]);

        result = bookSearch.searchUsingProjection("+title:lokomotywa -title:książka", new String[] { FullTextQuery.SCORE, "title" });
        Assert.assertEquals(2, result.size());
        
        watch.stop();
    }
}
