package pl.jcommerce.java8.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Example {

	public Base64Example() {
		String encoded = Base64.getEncoder().encodeToString("Java 8 ąęćśążźćczxs".getBytes());
		System.out.println("Encoded: " + encoded);
		String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
		System.out.println("Decoded: " + decoded);
	}
}
