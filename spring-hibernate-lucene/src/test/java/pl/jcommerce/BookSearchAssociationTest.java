package pl.jcommerce;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import org.junit.After;
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
public class BookSearchAssociationTest {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;
    
    @Autowired
    private LibraryRepository libraryRepo;
    
    @Autowired
    private BookSearch bookSearch;
    
    private String title;
    private long pageNumbers;
    private String contentPath;
    private Author author;
    private Library library;
    
    @Before
    public void prepareData(){
        
        this.title = "Lokomotywa";
        this.pageNumbers = 100;
        this.contentPath = "books/lokomotywa.pdf";
        prepareBook();
        
        this.title = "Lokomotywa 2";
        this.pageNumbers = 99;
        this.contentPath = "books/ksiazka.pdf";
        this.author = new Author("Julian", "Tuwim");
        prepareBook();
        
        this.title = "Książka";
        this.pageNumbers = 200;
        this.contentPath = "books/ksiazka.pdf";
        this.author = new Author("Adam", "Mickiewicz");
        this.library = new Library("kolejowa 15");
        prepareBook();
        
    }
    
    private void prepareBook(){
        Book bookToSave = new Book();
        bookToSave.setTitle(this.title);
        bookToSave.setPageNumbers(this.pageNumbers);
        bookToSave.setContent(this.contentPath);
        
        if(!Objects.isNull(this.author)){
            bookToSave.addAuthor(this.author);
        }
            
        bookToSave.setLibrary(this.library);
        bookRepo.save(bookToSave);
    }
    
    @After
    public void clear(){
        bookSearch.clearIndex();
        bookRepo.deleteAllInBatch();
        authorRepo.deleteAllInBatch();
    }
    

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
    
    @Test
    public void associationSynchronizationTest(){
        
        List<Book> res = bookSearch.search("+library.address:kolejowa");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Książka", res.get(0).getTitle());
        
        Library l = libraryRepo.findAll().get(0);
        l.setAddress("kolejkowa");
        libraryRepo.save(l);
        
        res = bookSearch.search("+library.address:kolejowa");
        Assert.assertEquals(0, res.size());
        
        res = bookSearch.search("+library.address:kolejkowa");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("Książka", res.get(0).getTitle());
        
    }
   
}
