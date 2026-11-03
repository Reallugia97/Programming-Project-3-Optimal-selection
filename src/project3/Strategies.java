package project3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TODO
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author BraydenBigelow, NathanJohnson
 */
public class Strategies {
	
	static final List<Experiment> EXPERIMENTS = List.of(
			new Experiment("Cloud Patterns", 36, 5),
			new Experiment("Solar Flares", 264, 9),
			new Experiment("Solar Power", 188, 6),
			new Experiment("Binary Stars", 203, 8),
			new Experiment("Relativity", 104, 8),
			new Experiment("Seed Viablity", 7, 4),
			new Experiment("Sun Spots", 90, 2),
			new Experiment("Mice Tumors", 65, 8),
			new Experiment("Microgravity Plant Growth", 75, 5),
			new Experiment("Micrometeorites", 170, 9),
			new Experiment("Cosmic Rays", 80, 7),
			new Experiment("Yeast Fermentation", 27, 4)
			);

	static Payload greedy(Comparator<Experiment> order, int maxWeight) {
		List<Experiment> sorted = new ArrayList<>(EXPERIMENTS);
		sorted.sort(order);
		
		Payload payload = new Payload();
		
		for (Experiment e : sorted) {
			if (payload.totalWeight + e.weight <= maxWeight) {
				payload.add(e);
			}
		}
		return payload;
	}
	
	static Payload highestRatingFirst(int maxWeight) {
		return greedy(Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparing(e -> e.weight), maxWeight);
	}
	
	static Payload lightestFirst(int maxWeight) {
		return greedy(Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()), maxWeight);
	}
	
	static Payload ratioFirst(int maxWeight) {
		return greedy(Comparator.comparingDouble(Experiment::ratio).reversed().thenComparing(e -> e.weight), 700);
	}
		
	public static void main(String[] args) {
		
	}
	
}
