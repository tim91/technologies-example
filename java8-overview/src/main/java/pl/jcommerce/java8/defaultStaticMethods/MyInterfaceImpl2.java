package pl.jcommerce.java8.defaultStaticMethods;

public class MyInterfaceImpl2 implements MyInterface {
	
	@Override
	public void executeMe(String parameter) {
		System.out.println("Second implementation: " + parameter);
	}
}
