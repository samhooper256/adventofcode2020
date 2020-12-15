package testing;

import static testing.Timing.TimeUnit.*;

import java.util.*;

public class Timing {
	public static void main(String[] args) {
		timeAndDetail("Slow Method", () -> slowMethod(), TimeUnit.MINUTE);
	}
	
	
	private static void slowMethod() {
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 20_000_000; i++)
			list.add(i);
	}
	
	public static long timeAndDetail(final Runnable r) {
		return timeAndDetail("Untitled", r, TimeUnit.SECOND);
	}
	
	public static long timeAndDetail(final String name, final Runnable r) {
		return timeAndDetail(name, r, TimeUnit.SECOND);
	}

	public static long timeAndDetail(final String name, final Runnable r, final TimeUnit unit) {
		System.out.printf("Starting test \"%s\"...", name);
		long time = time(r);
		final double conversion = convert(time, NANOSECOND, unit);
		System.out.printf("Test \"%s\" took %d nanoseconds (%.3f %s%s)%n", 
				name, time, conversion, unit.getName(), conversion == 1 ? "" : "s");
		return time;
	}
	
	public static long time(final Runnable r) {
		final long start = System.nanoTime();
		r.run();
		final long end = System.nanoTime();
		return end - start;
	}
	
	public enum TimeUnit{
		HOUR(1d/3600, "hour"), MINUTE(1d/60, "minute"), SECOND(1, "second"), MILLISECOND(1e3, "millisecond"),
		MICROSECOND(1e6, "microsecond"), NANOSECOND(1e9, "nanosecond");
		
		private final double multiplier;
		private final String name;
		
		TimeUnit(final double multiplier, final String name){
			this.multiplier = multiplier;
			this.name = name;
		}
		
		public static double convert(double time, TimeUnit from, TimeUnit to) {
			return time * to.multiplier / from.multiplier;
		}
		
		public String getName() { return name; }
	}
}
