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
	
	private static List<Experiment> experiments;
	
	private static ArrayList<Experiment> exclusiveConstraint1  = new ArrayList<>();
	private static ArrayList<Experiment> combinedSavings1  = new ArrayList<>();
	private static int savings1;
	private static ArrayList<Experiment> preReqConstraint1  = new ArrayList<>();
	
	Strategies() {
		
	}
	
	Strategies(List<Experiment> experiments) {
		Strategies.experiments = experiments;
	}
	
	public static List<Experiment> getExperiments()	{
		return experiments;
	}
	
	static Payload greedy(Comparator<Experiment> order, int maxWeight, List<Experiment> experiments) {
		List<Experiment> sorted = new ArrayList<>(experiments);
		sorted.sort(order);
		
		Payload payload = new Payload();
		
		for (Experiment e : sorted) {
			// Check payload change request requirements here
			
			if (payload.totalWeight + e.weight <= maxWeight) {
				payload.add(e);
			}
		}
		return payload;
	}
	
	//comparingInt sorts ascending order, so for highest first it has to be reversed after
	//thenComparing is for breaking ties using weight
	static Payload highestRatingFirst(int maxWeight) {		
		return greedy(Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparingInt(e -> e.weight), maxWeight, experiments);
	}
	
	static Payload lightestFirst(int maxWeight) {
		return greedy(Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()), maxWeight, experiments);
	}
	
	static Payload highestRatioFirst(int maxWeight) {
		return greedy(Comparator.comparingDouble(Experiment::ratio).reversed().thenComparingInt(e -> e.weight), maxWeight, experiments);
	}
	
	/**
	 * Searches all 2^n possible payloads and returns a list of all payloads the do
	 * not exceed maxWeight
	 */
	static List<Payload> bruteForce(int maxWeight) {
		int n = experiments.size();
		int totalPayloads = 1 << n;
		List<Payload> valid = new ArrayList<>();
		
		// Buids every combination of n bits
		for (int mask = 0; mask < totalPayloads; mask++) {
			Payload p = new Payload();
			
			// Builds the payload for the current bit combination
			for (int bit = 0; bit < n; bit++) {
				if ((mask & (1 << bit)) != 0) {
					p.add(experiments.get(bit));
				}
			}
			
			// Adds payload to list of valid payloads if its within maxWeight
			// Check payload change request requirements here
			if (p.totalWeight <= maxWeight) {
				valid.add(p);
			}
		}
		return valid;
		
	}
	
	static void printPayload(Payload p) {
		printDashes(45);
		System.out.printf("%-25s %5s %8s%n%n", "Experiment", "Weight", "Rating");
		for (Experiment e : p.items) {
			System.out.printf("%-25s %5d %8d%n", e.name, e.weight, e.rating);
		}
		printDashes(45);
		System.out.printf("Total weight: %d%nTotal Rating: %d%n%n", p.totalWeight, p.totalRating);
	}
	
	static void printTopThree(List<Payload> payload) {
		List<Payload> sorted = new ArrayList<>(payload);
		sorted.sort(Comparator.comparingInt((Payload p) -> p.totalRating).reversed().thenComparingInt(p -> p.totalWeight));
		for(int i = 0; i < Math.min(3, sorted.size()); i++) {
				printDashes(45);
				System.out.printf("Brute Force #%d Solution: %n%n", (i + 1));
				printPayload(sorted.get(i));
		}
	}
	
	static void printDashes(int length) {
		System.out.println("-".repeat(length));
	}
	
	public static void main(String[] args) {
		Strategies s = new Strategies(
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
		int limit = 700;
		
//		List<String> exclusiveConstraint  = new ArrayList<>(List.of("Solar Flares", "Solar Power", "Sun Spots"));
		ArrayList<Experiment> exclusiveConstraint1 = new ArrayList<>(List.of(
				new Experiment("Solar Flares", 264, 9), 
				new Experiment("Solar Power", 188, 6), 
				new Experiment("Sun Spots", 90, 2)));
		combinedSavings1  = new ArrayList<>(List.of(
				new Experiment("Seed Viability", 7, 4),
				new Experiment("Yeast Fermentation", 27, 4)));
		savings1 = 7;
		preReqConstraint1  = new ArrayList<>(List.of(
				new Experiment("Cosmic Rays (Primary Band)", 80, 7), 
				new Experiment("Cosmic Rays (Secondary Band)", 10, 5)));
		
		
		
		System.out.println("Greedy Strategies: \n");
		
		printDashes(45);
		System.out.println("Highest Rating First: \n");
		Payload rating = highestRatingFirst(limit);
		printPayload(rating);
		
		printDashes(45);
		System.out.println("Lowest Weight First: \n");
		Payload weight = lightestFirst(limit);
		printPayload(weight);
		
		printDashes(45);
		System.out.println("Best Ratio First: \n");
		Payload ratio = highestRatioFirst(limit);
		printPayload(ratio);
		
		System.out.println("\nBrute Force Strategy: \n");
		printTopThree(bruteForce(limit));
	}
}