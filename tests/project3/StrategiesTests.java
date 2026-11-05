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

	Strategies s = new Strategies(List.of(
			new Experiment("foo", 3, 1),
			new Experiment("bar", 4, 2),
			new Experiment("baz", 5, 3),
			new Experiment("bass", 10, 4)));

	@Test
	void testHighestRatingStrat() {
		Payload small = new Payload();
		small.add(new Experiment("bass", 11, 4));

		assertEquals(s.highestRatingFirst(6).items.getFirst().name, small.items.getFirst().name);
	}

	@Test
	void testLightestFirst() {
		Payload small = new Payload();
		small.add(new Experiment("foo", 3, 2));
		assertEquals(s.lightestFirst(6).items.getFirst().name, small.items.getFirst().name);
	}

	@Test
	void testHighestRatioFirst() {

	}

//	static final List<Experiment> EXPERIMENTS = List.of(
//	new Experiment("Cloud Patterns", 36, 5),
//	new Experiment("Solar Flares", 264, 9),
//	new Experiment("Solar Power", 188, 6),
//	new Experiment("Binary Stars", 203, 8),
//	new Experiment("Relativity", 104, 8),
//	new Experiment("Seed Viablity", 7, 4),
//	new Experiment("Sun Spots", 90, 2),
//	new Experiment("Mice Tumors", 65, 8),
//	new Experiment("Microgravity Plant Growth", 75, 5),
//	new Experiment("Micrometeorites", 170, 9),
//	new Experiment("Cosmic Rays", 80, 7),
//	new Experiment("Yeast Fermentation", 27, 4)
//	);

}
