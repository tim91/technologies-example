package pl.jcommerce.java8.reflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.jcommerce.java8.methodReferences.Car;

public class ReflectionExample {

	public ReflectionExample() {
		
		try {
			Arrays.asList(Car.class.getMethods())
			.stream()
			.map(m -> {
				Map<String, List<Object>> methods = new HashMap<String, List<Object>>();
				methods.put(m.getName(),
						
						Arrays.asList( Arrays.asList(m.getParameters())
						.stream()
						.map(p -> p.getName())
						.toArray())
				);
				return methods;
			})
			.filter(m -> {
				return m.keySet().contains("create") 
						|| m.keySet().contains("collide") 
						|| m.keySet().contains("repair")
						|| m.keySet().contains("follow");
			})
			.forEach(System.out::println);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
