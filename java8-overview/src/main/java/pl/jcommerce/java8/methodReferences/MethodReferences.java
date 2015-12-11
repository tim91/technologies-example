package pl.jcommerce.java8.methodReferences;

import java.util.Arrays;
import java.util.function.Function;

public class MethodReferences {

	public MethodReferences() {
		/**
		 * Supplier
		 */
		Car c = Car.create(Car::new); //Supplier T get()
		Car c1 = Car.create(Car::getInstance); //Supplier
		
		/**
		 * Consumer
		 */
		Arrays.asList(c).forEach(c::getMe); //Consumer void accept(T t);
		Arrays.asList(c).forEach(Car::repair);
		Arrays.asList(c).forEach(Car::collide);
//		Arrays.asList(c).forEach(Car::follow);// without parameter in method is ok
		Arrays.asList(c).forEach(c::follow);
		
		/**
		 * Predicate
		 */
		Arrays.asList(c).stream().filter(c::isWorking); //Predicate boolean test(T t);
		
		/**
		 * Function
		 */
		executeFunction(this::reverseString); //Function R apply(T t)
		executeFunction(this::reverseStringAndCut);
	}
	
	public String reverseString(String stringToReverse){
		return new StringBuilder(stringToReverse).reverse().toString();
	}
	
	public String reverseStringAndCut(String stringToReverse, Integer limit){
		String reversed = reverseString(stringToReverse);
		String cutted = reversed.substring(0, limit);
		return cutted;
	}
	
	public void executeFunction(Function<String,String> fn){
		String functionResult = fn.apply("Ala ma kota");
		System.out.println("Function returned: " + functionResult);
	}
	
	public void executeFunction(ReferenceToFunctionWithTwoParams<String, Integer, String> fn){
		String result = fn.execute("Ala ma kota", 4);
		System.out.println("Function2 returned: " + result);
	}

}
