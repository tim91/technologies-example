package pl.jcommerce.java8.lambdas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Lambdas {

	public Lambdas() {
	
		/*
		 * Execution
		 */
		List<String> li = Arrays.asList("a", "b", "c");
		li.forEach(e -> {
			System.out.println(e);
			System.out.println(e);
		});
		li.forEach(new StringConsumer());
		
		/*
		 * Returning
		 */
		li.sort((a1,a2) -> a1.compareTo(a2));
		li.sort((a1, a2) -> {
			int comparationResult = a1.compareTo(a2);
			return comparationResult;
		});
		
		/**
		 * Custom functional interface
		 */
		ObjectUsingFunctionalInterface mo = new ObjectUsingFunctionalInterface();
		mo.makeModification(e -> {
			System.out.println(e);
			return 10;
		});
	}
	
	private class StringConsumer implements Consumer<String>
	{
		private String sep = "aa";
		@Override
		public void accept(String t) {
			System.out.println(t + " " + sep);
		}
	}
}
