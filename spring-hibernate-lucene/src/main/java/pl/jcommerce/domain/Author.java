package pl.jcommerce.domain;

import javax.persistence.Entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Indexed
public class Author extends Base{
    
    @Field
    private String firstName;
    
    @Field
    private String lastName;

    public Author() {
    }

}
