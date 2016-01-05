package pl.jcommerce.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Indexed
@Getter
@Setter
@AllArgsConstructor
public class PublishingHouse extends Base {

    @ManyToOne(cascade=CascadeType.PERSIST)
    @IndexedEmbedded
    private City city;
    
    @Field
    private String name;
    
    public PublishingHouse() {
    }
}
