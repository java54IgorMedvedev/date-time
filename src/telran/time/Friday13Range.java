package telran.time;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Friday13Range implements Iterable<Temporal> {
    LocalDate from;
    LocalDate to;
    NextFriday13 adjuster = new NextFriday13();

    public Friday13Range(Temporal from, Temporal to) {
        this.from = LocalDate.from(from);
        this.to = LocalDate.from(to);
    }

    @Override
    public Iterator<Temporal> iterator() {
        return new FridayIterator();
    }

    private class FridayIterator implements Iterator<Temporal> {
        private LocalDate current;

        FridayIterator() {
            current = (LocalDate) adjuster.adjustInto(from);
            if (current.isAfter(to)) {
                current = null;
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Temporal next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more Fridays 13th available");
            }
            Temporal next = current;
            current = (LocalDate) adjuster.adjustInto(current.plusDays(1));
            if (current.isAfter(to)) {
                current = null;
            }
            return next;
        }
    }
}
