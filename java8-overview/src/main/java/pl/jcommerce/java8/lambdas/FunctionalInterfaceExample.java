package pl.jcommerce.java8.lambdas;

@FunctionalInterface //information only
public interface FunctionalInterfaceExample {

	public int doSomeStuff(ObjectUsingFunctionalInterface mo);
	
	@Override
	String toString(); //OK
}
