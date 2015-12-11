package pl.jcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jcommerce.domain.Library;

public interface LibraryRepository extends JpaRepository<Library, Long>{

}
