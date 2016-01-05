package pl.jcommerce;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.jcommerce.domain.Book;
import pl.jcommerce.domain.City;
import pl.jcommerce.domain.PublishingHouse;
import pl.jcommerce.repository.BookRepository;
import pl.jcommerce.search.BookSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class BookSearchDeeperAssociationTest {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private BookSearch bookSearch;
    
    private String title;
    private String cityName;
    private String printName;
    
    @Before
    public void prepareData(){
        
        this.title = "Lokomotywa";
        this.cityName = "Warszawa";
        this.printName = "Wydawnictwo Nowa Era";
        prepareBook();
        
        this.title = "Potop";
        this.cityName = "Kraków";
        this.printName = "Wydawnictwo Greg";
        prepareBook();
        
    }
    
    private void prepareBook(){
        Book book = new Book();
        book.setTitle(this.title);
        
        City city = new City(this.cityName);
        PublishingHouse print = new PublishingHouse(city, this.printName);
        
        book.setPublishingHouse(print);
        bookRepo.save(book);
    }
    
    @After
    public void clear(){
        bookSearch.clearIndex();
        bookRepo.deleteAllInBatch();
    }
    

    @Test
    public void searchByAssociatedObjectsProsTest(){
        
        List<Book> result = bookSearch.search("+title:lokomotywa +publishingHouse.city.cityName:Warszawa");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Lokomotywa", result.get(0).getTitle());
        
        result = bookSearch.search("+publishingHouse.city.cityName:Kraków");
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Potop", result.get(0).getTitle());
    }
   
}
