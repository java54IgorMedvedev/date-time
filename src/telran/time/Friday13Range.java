package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Friday13Range implements Iterable<Temporal> {
	Temporal from;
	Temporal to;

	private Friday13Range(Temporal from, Temporal to) {
		this.from = from;
		this.to = to;
	}

	public static Friday13Range getRange(Temporal from, Temporal to) {

		if (compare(from, to) >= 0) {
			throw new IllegalArgumentException("from Temporal must be less than to Temporal");
		}
		return new Friday13Range(from, to);
	}

	@Override
	public Iterator<Temporal> iterator() {

		return new FridayIterator();
	}

	private static int compare(Temporal t1, Temporal t2) {

		return (int) (0 - ChronoUnit.DAYS.between(t1, t2));

	}

	private class FridayIterator implements Iterator<Temporal> {
		NextFriday13 nextFriday13Adjuster = new NextFriday13();
		Temporal currentFriday13 = getFirstCurrent();

		@Override
		public boolean hasNext() {

			return compare(currentFriday13, to) <= 0;
		}

		private Temporal getFirstCurrent() {
			return from.get(ChronoField.DAY_OF_MONTH) == 13
					&& from.get(ChronoField.DAY_OF_WEEK)
					== DayOfWeek.FRIDAY.getValue() ?
							from : from.with(nextFriday13Adjuster);
		}

		@Override
		public Temporal next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Temporal res = currentFriday13;
			currentFriday13 = currentFriday13.with(nextFriday13Adjuster);
			return res;
		}

	}

}