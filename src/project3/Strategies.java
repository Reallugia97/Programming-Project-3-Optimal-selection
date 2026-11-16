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
 * @authors BraydenBigelow, NathanJohnson
 * 
 * Strategies. Because the best payloads don't organically emerge
 * from a list of experiments and constraints on their own.
 * 
 * Supports getting the top 3 from brute force search
 * as well as greedy (1 step) search to make a payload
 * using a comparator that works on experiments.
 * 
 * With 3 built in greedy searches ready to go.
 */
public class Strategies {
	
	private static List<Experiment> experiments;
	
	
	private static ArrayList<Experiment> exclusiveConstraint1  = new ArrayList<>();
	private static ArrayList<Experiment> combinedSavings1  = new ArrayList<>();
	private static int savings1;
	private static ArrayList<Experiment> preReqConstraint1  = new ArrayList<>();
	// In every context where you're initializing and using the constraints above
	// You're going to need one of these: //boolean massCreditApplied = false; 
	
	// Nathan's concessions to hard coding.
	// Everything changed when the change request hit.
	static Experiment y = new Experiment("Yeast Fermentation", 27, 4);
	static Experiment y2 = new Experiment("Yeast Fermentation", 20, 4);
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
	
	Strategies() {
		
	}
	
	/**
	 * 
	 * @param experiments
	 * Creates Strategies from the given list.
	 */
	Strategies(List<Experiment> experiments) {
		Strategies.experiments = experiments;
	}
	
	/**
	 * 
	 * @returns the experiments
	 */
	public static List<Experiment> getExperiments()	{
		return experiments;
	}
	
	/**
	 * 
	 * @returns the experiments after the adjustment for mass credit
	 */
	public static List<Experiment> getExperimentsWithMassCredit()	{
		return experimentsWithMassCredit;
	}
	
	/**
	 * 
	 * @param order
	 * @param maxWeight
	 * @param experiments
	 * @returns a payload assembled greedily (depth 1) based on the parameters.
	 */
	static Payload greedy(Comparator<Experiment> order, int maxWeight, List<Experiment> experiments) {
		List<Experiment> sorted = new ArrayList<>(experiments);
		sorted.sort(order); //our budget priority queue
		// todo list style.
		
		Payload payload = new Payload();
		payload.addConstraints(exclusiveConstraint1, combinedSavings1, savings1, preReqConstraint1);
		boolean massCreditApplied = false;
		
		for (int i = 0; i < sorted.size(); i=i+1)
		{
			Experiment e = sorted.get(i);
			
			// This is so we don't add items more than once.
			if(payload.items.contains(e))
				continue;
			
			// Redundancy for catching if there's an old version of the Yeast experiment
			// after massCredit is applied.
			if(massCreditApplied && e.equals(y))
				continue;
			
			Payload speculativePayload = new Payload(payload);
			speculativePayload.add(e);
			speculativePayload.interactionEffects(massCreditApplied);
			
			// This check is for handling mass credit
			// if we stumble into adding the Yeast experiment
			// followed by Seed viabilty experiment.
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
						// Normally we would set
						//massCreditApplied = true;
						// We don't do this because this payload is speculative
						// Iff the experiment is added below, then we do this
					}
				}
				sPWithMassCredit.checkPayloadValidity();
				speculativePayload = sPWithMassCredit;
			}
			
			if (speculativePayload.totalWeight <= maxWeight && speculativePayload.validPayload) {
				payload.add(e);
				i = -1; // In order to not miss options that were blocked by constraints
				// We go back to before the beginning after adding an experiment
				// Then at the end of the loop i is advanced to 0.
				
				if(payload.massCredit)
					massCreditApplied = true;
				//this is that step we didn't do earlier when the payload was speculative

				payload.interactionEffects(massCreditApplied);
				
				// This checks if we've applied the massCredit on this strategy.
				// If we haven't, but we can get the mass credit
				// because we have have the seed experiment
				// then it applies the mass credit on the Yeast experiment.
				// And updates the sorted order.
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
	
	/*
	 * 
	 * This uses greedy to get the highest rating first.
	 * The comparator sorts by rating, then weight. 
	 * 
	 * comparingInt sorts ascending order, so for highest first it has to be reversed after
	 * thenComparing is for breaking ties using weight
	 */
	static Payload highestRatingFirst(int maxWeight) {		
		return greedy(
				Comparator.comparingInt((Experiment e) -> e.rating).reversed().thenComparingInt(e -> e.weight),
				maxWeight,
				experiments);
	}
	
	/**
	 * 
	 * @param maxWeight
	 * @returns a payload assembled greedily: lightest experiment first.
	 * 
	 * The comparator sorts by lightest, then rating. 
	 * .reversed is used after sorting by rating
	 * to get highest rating first for breaking ties 
	 */
	static Payload lightestFirst(int maxWeight) {
		return greedy(
				Comparator.comparingInt((Experiment e) -> e.weight).thenComparing(Comparator.comparingInt((Experiment e) -> e.rating).reversed()),
				maxWeight,
				experiments);
	}
	
	/**
	 * 
	 * @param maxWeight
	 * @returns payload assembled greedily: highest ratio first.
	 * The comparator sorts by ratio (descending order), then breaks ties with weight (ascending order).
	 */
	static Payload highestRatioFirst(int maxWeight) {
		return greedy(
				Comparator.comparingDouble(Experiment::ratio).reversed().thenComparingInt(e -> e.weight),
				maxWeight,
				experiments);
	}
	
	/**
	 * 
	 * @param maxWeight
	 * @returns a list of the 2^n possible payloads that are valid and within the maxWeight limit.
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
	
	/*
	 * 
	 * Prints the given payload.
	 */
	static void printPayload(Payload p) {
		printDashes(45);
		System.out.printf("%-25s %5s %8s%n%n", "Experiment", "Weight", "Rating");
		for (Experiment e : p.items) {
			System.out.printf("%-25s %5d %8d%n", e.name, e.weight, e.rating);
		}
		printDashes(45);
		System.out.printf("Total weight: %d%nTotal Rating: %d%n%n", p.totalWeight, p.totalRating);
	}
	
	/*
	 * 
	 * Prints the top 3 items from the given list of payloads.
	 */
	static void printTopThree(List<Payload> payload) {
		List<Payload> sorted = new ArrayList<>(payload);
		sorted.sort(Comparator.comparingInt((Payload p) -> p.totalRating).reversed().thenComparingInt(p -> p.totalWeight));
		for(int i = 0; i < Math.min(3, sorted.size()); i++) {
				printDashes(45);
				System.out.printf("Brute Force #%d Solution: %n%n", (i + 1));
				printPayload(sorted.get(i));
		}
	}
	
	/*
	 * 
	 * Prints a line with a series of dashes of the given length.
	 */
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