package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal).withDayOfMonth(13);
        while (date.isBefore((ChronoLocalDate) temporal) || date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusMonths(1).withDayOfMonth(13);
        }
        return date;
    }
}
