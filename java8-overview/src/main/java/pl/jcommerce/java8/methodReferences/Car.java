package pl.jcommerce.java8.methodReferences;

import java.util.function.Supplier;

public class Car {
	
	public static Car getInstance(){
		return new Car();
	}
	
	public static Car create( final Supplier< Car > supplier ) {
        return supplier.get();
    }              
        
    public static void collide( final Car car ) {
        System.out.println( "Collided " + car.toString() );
    }
    
    public void follow (final Car car){
    }
    
    public void repair(){
    	
    }
    
    public Car getMe(Car c){
    	return c;
    }
    
    public boolean isWorking(Car c){
    	return false;
    }
}
