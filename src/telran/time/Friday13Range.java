package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Friday13Range implements Iterable<Temporal> {
    Temporal from;
    Temporal to;

    public Friday13Range(Temporal from, Temporal to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<Temporal> iterator() {
        return new FridayIterator();
    }

    private class FridayIterator implements Iterator<Temporal> {
        private Temporal current;

        FridayIterator() {
            current = from;
            moveToNextFriday13();
        }

        private void moveToNextFriday13() {
            LocalDate date = LocalDate.from(current);
            while (date.isBefore(LocalDate.from(to))) {
                date = date.plusDays(1);
                if (date.getDayOfWeek() == DayOfWeek.FRIDAY && date.getDayOfMonth() == 13) {
                    current = date;
                    return;
                }
            }
            current = null; 
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Temporal next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Temporal next = current;
            moveToNextFriday13();
            return next;
        }
    }
}
