package pl.jcommerce.java8.lambdas;

public class ObjectUsingFunctionalInterface{

	public void makeModification(FunctionalInterfaceExample om){
		//do some stuff
		System.out.println("Make modification");
		int res = om.doSomeStuff(this);
		System.out.println("Response: " + res);
	}

}
