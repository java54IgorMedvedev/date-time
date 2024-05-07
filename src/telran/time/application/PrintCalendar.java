package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

record MonthYear(int month, int year) {}

public class PrintCalendar {

    private static final int TITLE_OFFSET = 5;
    private static final int COLUMN_WIDTH = 12; 
    private static DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY; 

    public static void main(String[] args) {
        try {
            if (args.length >= 3) {
                firstDayOfWeek = DayOfWeek.valueOf(args[2].toUpperCase());
            }
            MonthYear monthYear = getMonthYear(args);
            printCalendar(monthYear);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        int monthNumber = getMonth(args);
        int year = getYear(args);
        return new MonthYear(monthNumber, year);
    }

    private static int getYear(String[] args) throws Exception {
        return args.length < 2 ? getCurrentYear() : Integer.parseInt(args[1]);
    }

    private static int getMonth(String[] args) throws Exception {
        return args.length == 0 ? getCurrentMonth() : Integer.parseInt(args[0]);
    }

    private static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    private static int getCurrentMonth() {
        return LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDays(monthYear);
    }

    private static void printWeekDays() {
        System.out.printf("%" + TITLE_OFFSET + "s", ""); 
        for (int i = 0; i < 7; i++) {
            DayOfWeek day = firstDayOfWeek.plus(i % 7);
            System.out.printf("%" + COLUMN_WIDTH + "s", day.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        System.out.println();
    }

    private static void printDays(MonthYear monthYear) {
        YearMonth ym = YearMonth.of(monthYear.year(), monthYear.month());
        LocalDate firstOfMonth = ym.atDay(1);
        int shift = (firstOfMonth.getDayOfWeek().getValue() - firstDayOfWeek.getValue() + 7) % 7;

        for (int i = 0; i < shift * COLUMN_WIDTH; i++) {
            System.out.print(" ");
        }

        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            System.out.printf("%" + COLUMN_WIDTH + "d", day);
            if ((shift + day) % 7 == 0) {
                System.out.println();
            }
        }
        if ((shift + ym.lengthOfMonth()) % 7 != 0) {
            System.out.println();
        }
    }

    private static void printTitle(MonthYear monthYear) {
        String monthName = Month.of(monthYear.month()).getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.printf("%" + TITLE_OFFSET + "s%s %d\n", "", monthName, monthYear.year());
    }
}
