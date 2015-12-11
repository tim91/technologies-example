package pl.jcommerce.java8.streams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsExample {

	public StreamsExample() {
	
		List<Product> products = Arrays.asList(
				new Product("Milk",2.5),
				new Product("Bread", 1.8),
				new Product("Jam", 3.65),
				new Product("Batter", 3.65));
		
		double sum = products.stream()
				.filter(it ->it.getPrice() > 2.0)
				.mapToDouble(it -> it.getPrice())
				.sum();
		System.out.println("Price of products 2: " + sum);
		
		Double overallSum = products
				.stream()
				.parallel() //intermediate
				.map(it -> it.getPrice()) //intermediate
				.reduce(0.0, Double::sum);// terminal
		System.out.println(overallSum);
		
		
		Map<Double, List<Product>> groupedProducts = products
		.stream()
		.collect(Collectors.groupingBy(Product::getPrice));
		
		System.out.println(groupedProducts);
		
		Path path = new File("src/main/resources/alaMaKota.txt").toPath();
		
		//try-with-resources
		try(Stream<String> lines = Files.lines(path)){
			lines.onClose(() -> System.out.println("Koniec!"))
			.filter(line -> line.contains("a"))
			.peek(it -> {
				File resultOfFiltering = new File("src/main/resources/alaMaKotaF_" + Clock.systemUTC().millis());
				
				try(BufferedWriter writer = Files.newBufferedWriter(resultOfFiltering.toPath(), StandardOpenOption.CREATE)){
					writer.write(it);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			})
			.forEach(System.out::println);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
