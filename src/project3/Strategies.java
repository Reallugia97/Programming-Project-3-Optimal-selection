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
	
	static Payload greedy(Comparator<Experiment> order, int maxWeight, List<Experiment> experiments) {
		List<Experiment> sorted = new ArrayList<>(experiments);
		sorted.sort(order);
		
		Payload payload = new Payload();
		
		for (Experiment e : sorted) {
			if (payload.totalWeight + e.weight <= maxWeight) {
				payload.add(e);
			}
		}
		return payload;
	}
	
	//comparingInt sorts ascending order, so for highest first it has to be reversed after
	//thenComparing is for breaking ties using weight
	static Payload highestRatingFirst(int maxWeight, List<Experiment> experiments) {
		return greedy(Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparing(e -> e.weight), maxWeight, experiments);
	}
	
	static Payload lightestFirst(int maxWeight, List<Experiment> experiments) {
		return greedy(Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()), maxWeight, experiments);
	}
	
	static Payload highestRatioFirst(int maxWeight, List<Experiment> experiments) {
		return greedy(Comparator.comparingDouble(Experiment::ratio).reversed().thenComparing(e -> e.weight), maxWeight, experiments);
	}
	
	
	static void bruteForce(int maxWeight, List<Experiment> experiments) {
		
	}
	
	
	public static void main(String[] args) {
		List<Experiment> experiments = List.of(
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
		
		System.out.println(highestRatingFirst(700, experiments));
	}
	
}
