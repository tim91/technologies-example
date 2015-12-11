package pl.jcommerce.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.midi.ControllerEventListener;

import org.springframework.util.StopWatch;

public class ParallelTest {

	public ParallelTest() {
		StopWatch watch = new StopWatch("Timer");
		List<Integer> numbersToSum = new ArrayList<Integer>();
		
		System.out.println("Preparing data...");
		for(int i=0; i<1_000_000; i++){
			numbersToSum.add(i);
		}
		System.out.println("Data prepared");
		watch.start("Parallel sum");
		Long sum = numbersToSum
				.stream()
				.parallel()
				.map(it -> it.longValue())
				.reduce(0L, Long::sum);
		watch.stop();
		
		watch.start("Sequence sum");
		sum = numbersToSum
				.stream()
				.map(it -> it.longValue())
				.reduce(0L, Long::sum);
		watch.stop();
		
		
		
		Long[] arrayOfLong = new Long[20_000_000];
		//prepare array
		Arrays.parallelSetAll(arrayOfLong, it ->ThreadLocalRandom.current().nextLong(100_000));
		
		//make a copy
		Long[] arrayOfLongCopy = Arrays.copyOf(arrayOfLong, arrayOfLong.length);
		
		//print array
		Arrays.stream(arrayOfLong)
		.limit(10)
		.forEach(it -> System.out.print(it + ", "));
		System.out.println();
		watch.start("Parallel sort");
		//sort array and print
		Arrays.parallelSort(arrayOfLong);
		watch.stop();
		Arrays.stream(arrayOfLong)
		.limit(10)
		.forEach(it -> System.out.print(it + ", "));
		System.out.println();
		watch.start("Seq sort");
		Arrays.sort(arrayOfLongCopy);
		watch.stop();
		Arrays.stream(arrayOfLongCopy).limit(10).forEach(it -> System.out.print(it + ", "));
		
		
		System.out.println(watch.prettyPrint());
	}
}
