package pl.jcommerce.search;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pl.jcommerce.domain.Library;

@Repository
@Transactional
public class LibrarySearch extends LuceneSearch<Library> {

}
