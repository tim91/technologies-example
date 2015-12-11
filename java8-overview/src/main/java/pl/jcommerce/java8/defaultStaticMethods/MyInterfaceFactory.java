package pl.jcommerce.java8.defaultStaticMethods;

public interface MyInterfaceFactory {

	static MyInterface getImpl(){
		return new MyInterfaceImpl();
	}
}
