package project3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * TODO
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author NathanJohnson
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
		small.add(new Experiment("bass", 10, 4));

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
	
	@Test
	void testSolarLimit() {
		ArrayList<Experiment> exclusiveConstraint1 = new ArrayList<>(List.of(
				new Experiment("Solar Flares", 264, 9), 
				new Experiment("Solar Power", 188, 6), 
				new Experiment("Sun Spots", 90, 2)));
		
		Payload impossible = new Payload();
		impossible.addConstraints(exclusiveConstraint1, null, 0, null);
//		impossible.addexclusiveConstraint(exclusiveConstraint1);
		impossible.add(new Experiment("Solar Flares", 264, 9));
		impossible.add(new Experiment("Solar Power", 188, 6));
		impossible.add(new Experiment("Sun Spots", 90, 2));
		impossible.interactionEffects();
		assertEquals(false, impossible.validPayload);
	}
	
	@Test
	void testMassCredit() {
		ArrayList<Experiment> combinedSavings1  = new ArrayList<>(List.of(
				new Experiment("Seed Viability", 7, 4),
				new Experiment("Yeast Fermentation", 27, 4)));
		int savings1 = 7;
		
		Payload efficientCombo = new Payload();
		efficientCombo.add(new Experiment("Seed Viability", 7, 4));
		efficientCombo.add(new Experiment("Yeast Fermentation", 27, 4));
		
		assertEquals(34, efficientCombo.totalWeight);
		
		efficientCombo.addConstraints(null, combinedSavings1, savings1, null);
		efficientCombo.interactionEffects();
		
		assertEquals(27, efficientCombo.totalWeight);	
	}
	
	@Test
	void testPreReqConstraint() {
		Experiment a = new Experiment("Cosmic Rays (Primary Band)", 80, 7);
		Experiment b = new Experiment("Cosmic Rays (Secondary Band)", 10, 5);
		
		ArrayList<Experiment> preReqConstraint1  = new ArrayList<>(List.of(
				a, b));
		
		Payload payloadA = new Payload();
		payloadA.addConstraints(null, null, 0, preReqConstraint1);
		payloadA.interactionEffects();
		assertEquals(payloadA.validPayload, true);
		
		payloadA.add(a);
		payloadA.interactionEffects();
		assertEquals(payloadA.validPayload, true);
		
		Payload payloadB = new Payload();
		payloadB.addConstraints(null, null, 0, preReqConstraint1);
		payloadB.interactionEffects();
		assertEquals(payloadB.validPayload, true);

		payloadB.add(b);
		payloadB.interactionEffects();
		assertEquals(false, payloadB.validPayload);
		
		Payload payloadAAndB = new Payload();
		payloadAAndB.add(a);
		payloadAAndB.add(b);
		payloadAAndB.interactionEffects();
		assertEquals(payloadAAndB.validPayload, true);
	}
	
	//if this test fails, modify Experiment constructors, .equals, and hashCode BEFORE incrementing 
	@Test
	void testNumOfExperimentParameters() {
		Experiment e = new Experiment("Cosmic Rays (Primary Band)", 80, 7);
		assertEquals(3, e.getClass().getDeclaredFields().length);
	}
	
	//if this test fails, modify Payload constructors BEFORE incrementing 
		@Test
		void testNumOfObjectParameters() {
			Payload p = new Payload();
			assertEquals(9, p.getClass().getDeclaredFields().length);
		}
	
	/*
	 List.of(
						new Experiment("Cloud Patterns", 36, 5),
						new Experiment("Solar Flares", 264, 9),
						new Experiment("Solar Power", 188, 6),
						new Experiment("Binary Stars", 203, 8),
						new Experiment("Relativity", 104, 8),
						new Experiment("Seed Viability", 7, 4),
						new Experiment("Sun Spots", 90, 2),
						new Experiment("Mice Tumors", 65, 8),
						new Experiment("Microgravity Plant Growth", 75, 5),
						new Experiment("Micrometeorites", 170, 9),
						new Experiment("Cosmic Rays (Primary Band)", 80, 7),
						new Experiment("Cosmic Rays (Secondary Band)", 10, 5),
						new Experiment("Yeast Fermentation", 27, 4)
				));
	 */
}
