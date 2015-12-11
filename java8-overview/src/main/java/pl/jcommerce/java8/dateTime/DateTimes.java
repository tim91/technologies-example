package pl.jcommerce.java8.dateTime;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimes {

	public DateTimes() {
		
		/**
		 * Clock
		 */
		final Clock clock = Clock.systemDefaultZone();
		System.out.println(clock.millis());
		try {
			Thread.sleep(875);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(clock.millis());
		System.out.println(clock.instant());
		
		/*
		 * Local Date
		 */
		System.out.println("\nLocal Date");
		LocalDate ld = LocalDate.now();
		System.out.println(ld + " " + ld.getMonthValue());//numbering from 1!!
		LocalDate ldAddedDays = ld.plusDays(45);
		System.out.println(ldAddedDays);
		
		LocalDate ld1 = LocalDate.now();
		System.out.println("Local? " + (ld.equals(ld1)));
		
		/**
		 * Local Time
		 */
		System.out.println("\nLocal Time");
		
		LocalTime time = LocalTime.now();
		System.out.println(time);
		
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt + " nanos: " + ldt.getNano());
		
		Integer a = 1;
		int b = 1;
		System.out.println(a == b);
		
		/**
		 * Zoned date time
		 */
		System.out.println("\nZonedDateTime");
		ZonedDateTime zdt = ZonedDateTime.now();
		System.out.println(zdt);
		
		ZonedDateTime zdtNY = ZonedDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("ECT")));
		System.out.println(zdtNY);
		
		/**
		 * Local Date Time
		 */
		System.out.println("\nLocalDateTime");
		LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
		LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 0, 0, 0 );

		/**
		 * Duration
		 */
		System.out.println("\nDuration");
		Duration duration = Duration.between(  from, to );
		System.out.println( "Duration in days: " + duration.toDays() );
		System.out.println( "Duration in hours: " + duration.toHours() );
		
		/**
		 * Date Time formatter
		 */
		System.out.println("\nDate time formatter");
		String dateAsString = "03-01-2016";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Thread Safe!
		LocalDate parsedDate = LocalDate.parse(dateAsString, dtf);
		System.out.println("Parsed date: " + parsedDate);
		
		String parsedDateString = parsedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ENGLISH));
		System.out.println("String week date: " + parsedDateString);
	}
}
