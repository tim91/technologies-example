package pl.jcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jcommerce.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Author findByFirstName(String firstName);
}
