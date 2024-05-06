package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import telran.time.BarMizvaAdjuster;
import telran.time.Friday13Range;
import telran.time.NextFriday13;

class DateTimeTest {
	LocalDate ld = LocalDate.of(2024, 5, 4);
	@Test
	void barMizvaAdjusterTest() {
		LocalDate birthDate = LocalDate.parse("1799-06-06");
		LocalDate expected = LocalDate.of(1812, 6, 6);
		assertEquals(expected, birthDate.with(new BarMizvaAdjuster()));
	}
	@Test
	void nextFriday13Test() {
		NextFriday13 adjuster = new NextFriday13();
		
		ZonedDateTime zdt = ZonedDateTime.of(ld, LocalTime.of(0, 0), ZoneId.systemDefault());
		LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.of(0, 0));
		LocalDate ldExpected = LocalDate.of(2024, 9, 13);
		ZonedDateTime zdtExpected = ZonedDateTime.of(ldExpected, LocalTime.of(0, 0), ZoneId.systemDefault());
		LocalDateTime ldtExpected = LocalDateTime.of(ldExpected, LocalTime.of(0, 0));
		assertEquals(ldExpected, ld.with(adjuster));
		assertEquals(ldtExpected, ldt.with(adjuster));
		assertEquals(zdtExpected, zdt.with(adjuster));
		LocalDate ldExpected2 = LocalDate.of(2024, 12, 13);
		assertEquals(ldExpected2, ldExpected.with(adjuster));
		
		
	}
	@Test
	void friday13RangeTest() {
		Temporal from = ld;
		Temporal to = ZonedDateTime.of(2025, 3, 31, 0, 0,0,0, ZoneId.systemDefault());
		
		Friday13Range range = Friday13Range.getRange(from, to);
		Temporal [] expectedArray = {
				LocalDate.of(2024, 9, 13),
				LocalDate.of(2024, 12, 13)
		};
		int index = 0;
		Iterator<Temporal> it = range.iterator();
		while(it.hasNext()) {
			assertEquals(expectedArray[index++], it.next());
		}
		assertEquals(expectedArray.length, index);
		
	}
	@Test
	void displayCurrentDateTimeTest() {
		//The following test implies printing out
		//all current date/time in Time Zones containing string Canada
		
		displayCurrentDateTime("Canada");
		
	}
	private void displayCurrentDateTime(String zonePart) {
		
		// prints out current date/time in all zones containing zonePart
		//format <year>-<month number>-<day> <hh:mm> <zone name>
		//ZonedDateTime class
		for(String zoneId: ZoneId.getAvailableZoneIds()) {
			if (zoneId.contains(zonePart)) {
				ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(zoneId));
				System.out.println(
						zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm zzzz")));
			}
		}
	}
	

}