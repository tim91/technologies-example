package pl.jcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jcommerce.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Book findByTitle(String title);
}
