package pl.jcommerce.domain;

import javax.persistence.Entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Indexed
@Getter
@Setter
@AllArgsConstructor
public class City extends Base {

    @Field
    private String cityName;
    
    public City() {
    }

}
