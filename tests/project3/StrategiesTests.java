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

		assertEquals(s.highestRatingFirst(10).items.getFirst().name, small.items.getFirst().name);
	}

	@Test
	void testLightestFirst() {
		Payload small = new Payload();
		small.add(new Experiment("foo", 3, 2));
		assertEquals(s.lightestFirst(6).items.getFirst().name, small.items.getFirst().name);
	}

	@Test
	void testHighestRatioFirst() {
		Payload small = new Payload();
		small.add(new Experiment("baz", 5, 3));
		assertEquals(s.highestRatioFirst(6).items.getFirst().name, small.items.getFirst().name);
	}
	
	//constraints:
	//limit constraint: cannot have all 3 solar on the same payload
	//mass credit/shared component: a and b have lower weight together
	//pre-req constraint: cannot have b without a (11a requires 11)
	
	//called with something like
	//applyCombinationEffects();
	@Test
	void testSolarLimit() {
		
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
	
	/*
	 * ID	Science package	Baseline mass (kg)	Science rating
1	Cloud Patterns	36	5
2	Solar Flares	264	9
3	Solar Power	188	6
4	Binary Stars	203	8
5	Relativity	104	8
6	Seed Viability	7	4
7	Sun Spots	90	2
8	Mice Tumors	65	8
9	Microgravity Plant Growth	75	5
10	Micrometeorites	170	9
11	Cosmic Rays (Primary Band)	80	7
11a	Cosmic Rays (Secondary Band)	10	5
12	Yeast Fermentation	27	4

	 */


}
