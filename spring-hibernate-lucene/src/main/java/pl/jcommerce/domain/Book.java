package pl.jcommerce.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TikaBridge;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Indexed
public class Book extends Base {
    
    @Field(store=Store.YES)
    private String title;

    @Field
    private Long pageNumbers;
    
    @OneToMany(fetch=FetchType.EAGER)
    @IndexedEmbedded
    private Set<Author> authors = new HashSet<Author>();
    
    @ManyToOne
    @IndexedEmbedded(depth=1)
    private Library library;
    
    @Field
    @TikaBridge
    private String content;

    @Field
    @DateBridge(resolution = Resolution.HOUR)
    private Date publicationDate;
    
    public void addAuthor(Author a){
        this.authors.add(a);
    }
}
