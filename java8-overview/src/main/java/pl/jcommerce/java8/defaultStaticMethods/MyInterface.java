package pl.jcommerce.java8.defaultStaticMethods;

@FunctionalInterface
public interface MyInterface {

	public void executeMe(String parameter);
	
	default public void secondExecuteMe(String parameter){
		System.out.println("Additional method added after implementation: " + parameter);
	}
	
	public default void aa(){
		
	}
	
	static String a(){
		return "";
	}
}
