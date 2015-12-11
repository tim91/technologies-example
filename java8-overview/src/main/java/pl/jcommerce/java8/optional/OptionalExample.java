package pl.jcommerce.java8.optional;

import java.util.Optional;

public class OptionalExample {

	public OptionalExample() {
		
		Optional<String> emptyString = Optional.ofNullable(null);
		System.out.println(emptyString.isPresent());
		System.out.println(emptyString.orElse("String is empty"));
		System.out.println(emptyString.orElseGet(() -> "Backup value"));
	}
	
}
