package testing;

import utils.HashIntSet;

/**
 * @author Sam Hooper
 *
 */
public class IntSetTesting {
	
	public static void main(String[] args) {
		HashIntSet set = new HashIntSet(10, 0.75f);
		
		assert set.isEmpty();
		assert set.size() == 0;
		assert set.add(3);
		assert !set.isEmpty();
		assert set.size() == 1;
		assert set.contains(3);
		assert !set.add(3);
		assert set.add(13);
		assert !set.add(13);
		assert !set.isEmpty() && set.size() == 2 && set.contains(3) && set.contains(13);
		
		System.out.println(set);
		try {
			assert false;
		}
		catch(AssertionError e) {
			System.out.println("Success!");
		}
		
		Timing.timeAndDetail(() -> {
			HashIntSet s = new HashIntSet(10);
			for(int i = 0; i < 10_000; i++)
				s.add(i);
		});
		
		Timing.timeAndDetail(() -> {
			HashIntSet s = new HashIntSet(100);
			for(int i = 0; i < 10_000; i++)
				s.add(i);
		});
		
		Timing.timeAndDetail(() -> {
			HashIntSet s = new HashIntSet(1000);
			for(int i = 0; i < 10_000; i++)
				s.add(i);
		});
	}
	
}
