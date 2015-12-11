package pl.jcommerce.search;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pl.jcommerce.domain.Book;

@Repository
@Transactional
public class BookSearch extends LuceneSearch<Book> {

}
