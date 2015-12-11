package pl.jcommerce;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.jcommerce.domain.Author;
import pl.jcommerce.domain.Book;
import pl.jcommerce.domain.Library;
import pl.jcommerce.repository.AuthorRepository;
import pl.jcommerce.repository.BookRepository;
import pl.jcommerce.repository.LibraryRepository;
import pl.jcommerce.search.BookSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class BookSearchIntegrationTest {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;
    
    @Autowired
    private LibraryRepository libraryRepo;
    
    @Autowired
    private BookSearch bookSearch;
    
    @Before
    public void prepareData(){
        System.out.println("Set UP");
        
        Book lokomotywa = new Book();
        lokomotywa.setTitle("Lokomotywa");
        Calendar gc = GregorianCalendar.getInstance();
        gc.set(2010, 5, 15);
        lokomotywa.setPublicationDate(new GregorianCalendar(2010, Calendar.JULY, 15).getTime());
        lokomotywa.setPageNumbers(100L);
        lokomotywa.setContent("books/lokomotywa.pdf");
        bookRepo.save(lokomotywa);
        
        Author tuwim = new Author("Julian", "Tuwim");
        authorRepo.save(tuwim);
        
        Author mickiewicz = new Author("Adam", "Mickiewicz");
        authorRepo.save(mickiewicz);
        Library l = new Library();
        l.setAddress("kolejowa 15");
        libraryRepo.save(l);
        
        Book lokomotywa2 = new Book();
        lokomotywa2.setTitle("Lokomotywa 2");
        lokomotywa2.setPublicationDate(new GregorianCalendar(2010, Calendar.JULY, 10).getTime());
        lokomotywa2.setPageNumbers(99L);
        lokomotywa2.addAuthor(tuwim);
        lokomotywa2.setContent("books/ksiazka.pdf");
        bookRepo.save(lokomotywa2);
        
        Book l2 = new Book();
        l2.setTitle("Książka");
        l2.setPageNumbers(200L);
        l2.addAuthor(mickiewicz);
        l2.setContent("books/ksiazka.pdf");
        l2.setLibrary(l);
        bookRepo.save(l2);
        
        System.out.println("Set UP DONE");
    }
    
//    @After
//    public void clear(){
//        bookSearch.clearIndex();
//        bookRepo.deleteAllInBatch();
//        authorRepo.deleteAllInBatch();
//    }
    

    @Test
    public void searchByAssociatedObjectsProsTest(){
        
        List<Book> result = bookSearch.search("+title:lokomotywa +authors.firstName:Julian");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa 2", result.get(0).getTitle());
        
        result = bookSearch.search("+title:lokomotywa -authors.firstName:Julian");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa", result.get(0).getTitle());
        
        result = bookSearch.search("title:l* title:k* -authors.firstName:Julian");
        Assert.assertEquals(2, result.size());
        
        result = bookSearch.search("+title:k* -authors.firstName:Adam");
        Assert.assertEquals(0, result.size());
        
        result = bookSearch.search("+authors.firstName:Adam +library.address:kolejowa");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Książka", result.get(0).getTitle());
    }
    
//    @Test
//    public void associationSynchronizationTest(){
//        
//        List<Book> res = bookSearch.search("+library.address:kolejowa");
//        Assert.assertEquals(1, res.size());
//        Assert.assertEquals("Książka", res.get(0).getTitle());
//        
//        Library l = libraryRepo.findAll().get(0);
//        l.setAddress("kolejkowa");
//        libraryRepo.save(l);
//        
//        res = bookSearch.search("+library.address:kolejowa");
//        Assert.assertEquals(0, res.size());
//        
//        res = bookSearch.search("+library.address:kolejkowa");
//        Assert.assertEquals(1, res.size());
//        Assert.assertEquals("Książka", res.get(0).getTitle());
//        
//    }
   
}
