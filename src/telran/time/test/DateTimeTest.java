package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import telran.time.*;

class DateTimeTest {

    @Test
    void barMizvaAdjusterTest() {
        LocalDate birthDate = LocalDate.parse("1799-06-06");
        LocalDate expected = LocalDate.of(1812, 6, 6);
        assertEquals(expected, birthDate.with(new BarMizvaAdjuster()));
    }

    @Test
    void nextFriday13Test() {
        LocalDate date = LocalDate.of(2020, 3, 1);
        LocalDate expected = LocalDate.of(2020, 3, 13);
        assertEquals(expected, date.with(new NextFriday13()));
    }

    @Test
    void friday13RangeTest() {
        Friday13Range range = new Friday13Range(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31));
        Iterator<Temporal> iterator = range.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(LocalDate.of(2020, 3, 13), iterator.next());
        assertEquals(LocalDate.of(2020, 11, 13), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void displayCurrentDateTimeTest() {
        displayCurrentDateTime("Canada");
    }

    private void displayCurrentDateTime(String zonePart) {
        ZoneId.getAvailableZoneIds().stream()
            .filter(z -> z.contains(zonePart))
            .forEach(zone -> {
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of(zone));
                System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")));
            });
    }
}
