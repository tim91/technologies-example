package pl.jcommerce.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import lombok.Getter;
import lombok.Setter;

@Entity
@Indexed
@Getter
@Setter
public class Library extends Base {

    @Field
    private String address;

    @ContainedIn
    @OneToMany(mappedBy="library")
    private Set<Book> books = new HashSet<Book>();
    
    
    public Library() {
    }


    public Library(String address) {
        super();
        this.address = address;
    }
}
