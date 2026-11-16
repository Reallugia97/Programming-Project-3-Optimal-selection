package project3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author BraydenBigelow, NathanJohnson
 */
public class Strategies {
	
	private static List<Experiment> experiments;
	private static List<Experiment> experimentsWithMassCredit = List.of(
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
			new Experiment("Yeast Fermentation", 20, 4)
	);
	
	private static ArrayList<Experiment> exclusiveConstraint1  = new ArrayList<>();
	private static ArrayList<Experiment> combinedSavings1  = new ArrayList<>();
//	private static ArrayList<Experiment> combinedSavings1  = new ArrayList<>(List.of(
//			new Experiment("Seed Viability", 7, 4),
//			new Experiment("Yeast Fermentation", 27, 4)));
	private static int savings1;
	private static ArrayList<Experiment> preReqConstraint1  = new ArrayList<>();
//	private static boolean massCreditApplied = false;
	static Experiment y = new Experiment("Yeast Fermentation", 27, 4);
	static Experiment y2 = new Experiment("Yeast Fermentation", 20, 4);
	
	Strategies() {
		
	}
	
	Strategies(List<Experiment> experiments) {
		Strategies.experiments = experiments;
	}
	
	public static List<Experiment> getExperiments()	{
		return experiments;
	}
	
//	private void checkMassCredit(Payload p)
//	{
//		if(massCreditApplied)
//			return;
//		
//		if(p.items.contains(combinedSavings1.getFirst()))
//		{
//			
//		}
//	}
	
//	static Payload greedy(Comparator<Experiment> order, Comparator<Payload> order2, int maxWeight, List<Experiment> experiments) {
	static Payload greedy(Comparator<Experiment> order, int maxWeight, List<Experiment> experiments) {
		List<Experiment> sorted = new ArrayList<>(experiments);
		sorted.sort(order);
		
		Payload payload = new Payload();
		payload.addConstraints(exclusiveConstraint1, combinedSavings1, savings1, preReqConstraint1);
		boolean massCreditApplied = false;
		
		for (int i = 0; i < sorted.size(); i=i+1)
		{
			Experiment e = sorted.get(i);
			
			if(payload.items.contains(e))
				continue;
			
			if(massCreditApplied && e.equals(y))
				continue;
			
			Payload speculativePayload = new Payload(payload);
			speculativePayload.add(e);
			speculativePayload.interactionEffects(massCreditApplied);
			
			if(speculativePayload.massCredit)
			{
				Payload sPWithMassCredit = new Payload();
				sPWithMassCredit.addConstraints(exclusiveConstraint1, combinedSavings1, savings1, preReqConstraint1);
				for(Experiment old : speculativePayload.items)
				{
					if(!old.equals(y))
						sPWithMassCredit.add(old);
					else
					{
						sPWithMassCredit.add(y2);
						//massCreditApplied = true;
						//We don't do this because this payload is speculative
						//if the experiment is added below, then we do this
					}
				}
				sPWithMassCredit.checkPayloadValidity();
				speculativePayload = sPWithMassCredit;
			}
			
			if (speculativePayload.totalWeight <= maxWeight && speculativePayload.validPayload) {
				payload.add(e);
				
				if(payload.massCredit)
					massCreditApplied = true;
				
				i = -1;
				payload.interactionEffects(massCreditApplied);
				
				if(!massCreditApplied && combinedSavings1 != null && !combinedSavings1.isEmpty())
				{
					if(e.equals(combinedSavings1.getFirst()) && sorted.contains(y) && !payload.items.contains(y))
					{
						sorted.remove(sorted.indexOf(y));
						sorted.add(y2);
						sorted.sort(order);
						massCreditApplied = true;
					}
				}
			}
		}
		
		return payload;
	}
	
	//comparingInt sorts ascending order, so for highest first it has to be reversed after
		//thenComparing is for breaking ties using weight
	static Payload highestRatingFirst(int maxWeight) {		
		return greedy(
				Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparingInt(e -> e.weight),
				maxWeight,
				experiments);
	}
	
	static Payload lightestFirst(int maxWeight) {
		return greedy(
				Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()),
				maxWeight,
				experiments);
	}
	
	static Payload highestRatioFirst(int maxWeight) {
		return greedy(
				Comparator.comparingDouble(Experiment::ratio).reversed().thenComparingInt(e -> e.weight),
				maxWeight,
				experiments);
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
			boolean massCreditApplied = false;
			Payload p = new Payload();
			p.addConstraints(exclusiveConstraint1, combinedSavings1, savings1, preReqConstraint1);
			
			// Builds the payload for the current bit combination
			for (int bit = 0; bit < n; bit++) {
				if ((mask & (1 << bit)) != 0) {
					p.add(experiments.get(bit));
				}
			}
			
			if(!combinedSavings1.isEmpty() && p.items.contains(combinedSavings1.getFirst()) && p.items.contains(combinedSavings1.getLast()))
			{
				Payload pWithMassCredit = new Payload();
				pWithMassCredit.addConstraints(exclusiveConstraint1, combinedSavings1, savings1, preReqConstraint1);
				massCreditApplied = true;
				
				for (int bit = 0; bit < n; bit++) {
					if ((mask & (1 << bit)) != 0) {
						pWithMassCredit.add(experimentsWithMassCredit.get(bit));
					}
				}
				p = pWithMassCredit;
			}
				
			p.interactionEffects(massCreditApplied); //if the payload is invalid or has a mass credit, this call will fix that
			
			// Adds payload to list of valid payloads if its within maxWeight
			// Check payload change request requirements here
			if (p.totalWeight <= maxWeight && p.validPayload) {
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
		
		exclusiveConstraint1 = new ArrayList<>(List.of(
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