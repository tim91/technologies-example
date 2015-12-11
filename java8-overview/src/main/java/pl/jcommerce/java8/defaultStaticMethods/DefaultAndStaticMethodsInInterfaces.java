package pl.jcommerce.java8.defaultStaticMethods;

public class DefaultAndStaticMethodsInInterfaces {

	public DefaultAndStaticMethodsInInterfaces() {
		
		MyInterface my = new MyInterfaceImpl();
		my.executeMe("Hey!");
		
		
		MyInterface mi = MyInterfaceFactory.getImpl();
		mi.executeMe("");
		
	}
}
