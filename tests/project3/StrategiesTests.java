package project3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * TODO
 */
class StrategiesTests {

	@Test
	void test() {
//		fail("Not yet implemented");
	}
	
	//name weight rating
//	Experiment[] xps = {{"foo", 3, 3}, {"bar", 4, 2}, {"baz", 5, 1}};
	//Experiment e = new Experiment("foo", 3, 3);
//	Experiment[] xps = { new Experiment("foo", 3, 3),  new Experiment("bar", 4, 2),  new Experiment("baz", 5, 1)};
	
//	List<Experiment> xps = List.of(
//			new Experiment("foo", 36, 5),
//			new Experiment("bar", 264, 9),
//			new Experiment("baz", 188, 6)
//			);
	
//	static final List<Experiment> EXPERIMENTS = List.of(
//			new Experiment("Cloud Patterns", 36, 5),
//			new Experiment("Solar Flares", 264, 9),
//			new Experiment("Solar Power", 188, 6),
//			new Experiment("Binary Stars", 203, 8),
//			new Experiment("Relativity", 104, 8),
//			new Experiment("Seed Viablity", 7, 4),
//			new Experiment("Sun Spots", 90, 2),
//			new Experiment("Mice Tumors", 65, 8),
//			new Experiment("Microgravity Plant Growth", 75, 5),
//			new Experiment("Micrometeorites", 170, 9),
//			new Experiment("Cosmic Rays", 80, 7),
//			new Experiment("Yeast Fermentation", 27, 4)
//			);
	Strategies s;
	
	
	void testHighestRatingStrat()
	{
//		xps.HighestRating(3)
		Payload small = new Payload();
		small.add(new Experiment("Micrometeorites", 170, 9));
		
		assertEquals(s.highestRatingFirst(170), small);
		
//		assertEquals(true, false);
	}
	
	void testLightestFirst()
	{
//		xps.HighestRating(3)
		Payload small = new Payload();
		small.add(new Experiment("Seed Viablity", 7, 4));
		
		assertEquals(s.lightestFirst(7), small);
		
//		assertEquals(true, false);
	}
	
	
	
}
