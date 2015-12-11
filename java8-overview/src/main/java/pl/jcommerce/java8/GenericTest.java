package pl.jcommerce.java8;

public class GenericTest<T> {

	public static<T> T defaultValue(){
		return null;
		
		
	}
	
	public T getNoNull(T value, T defaultValue){
		return (value != null)? value : defaultValue; 
	}
}
