package project3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * TODO
 */
class GreedyStrategiesTests {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	//name weight rating
	//Experiment[] xps = {{"foo", 3, 3}, {"bar", 4, 2}, {"baz", 5, 1}};
	//Experiment e = new Experiment("foo", 3, 3);
	Experiment[] xps = { new Experiment("foo", 3, 3),  new Experiment("bar", 4, 2),  new Experiment("baz", 5, 1)};
	
	void testHighestRatingStrat()
	{
//		xps.HighestRating(3)
		AssertEquals(xps.HighestRating(3), [true, false, false]);
	}
}
