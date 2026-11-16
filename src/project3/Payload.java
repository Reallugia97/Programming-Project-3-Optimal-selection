package project3;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author BraydenBigelow
 * Some constraints and constraint logic added by Nathan
 * 
 * Wraps a list of experiments to handle totalWeight, totalRating,
 * constraints and mass credit.
 */
public class Payload {

	List<Experiment> items = new ArrayList<>();
	int totalWeight;
	int totalRating;
	boolean validPayload = true; // this is for keeping track of constraints

	// Experiments are ONLY ADDED to payloads.
	List<Experiment> exclusiveConstraint = new ArrayList<>();
	List<Experiment> combinedSavings = new ArrayList<>();
	int weightSavings;
	List<Experiment> preReqConstraint = new ArrayList<>();
	boolean massCredit = false;
	// Once massCredit is true, the weightSavings are never realized again.
	// In the sense that false is 0 and true is 1,
	// massCredit is monotonically increasing.

	// This implementation supports ONE combinedSavings list.
	// With more than one, weightSavings and massCredit would have to be lists,
	// and the associated checks would have to be positional.

	/**
	 * 
	 * Creates an empty payload.
	 * Make sure you immediately call //.interactionEffects(massCreditApplied);
	 */
	public Payload() {
	}
	
	/**
	 * 
	 * @param items
	 * @param totalWeight
	 * @param totalRating
	 * @param validPayload
	 * @param exclusiveConstraint
	 * @param combinedSavings
	 * @param weightSavings
	 * @param preReqConstraint
	 * @param massCredit
	 * 
	 * Make sure you immediately call //.interactionEffects(massCreditApplied);
	 * 
	 * This constructor below was made as a reference while creating the constructor below it.
	 * It is not used.
	 * Instead payloads are created as copies (see constructor below this one)
	 * or created empty.
	 * Then experiments are added to the payload, and interactions are checked.
	 * If you decide to use this constructor, be careful.
	 */
	public Payload(List<Experiment> items, int totalWeight, int totalRating, boolean validPayload,
			List<Experiment> exclusiveConstraint,
			List<Experiment> combinedSavings, int weightSavings, List<Experiment> preReqConstraint, boolean massCredit) {
		super();
		this.items = new ArrayList<>(items); //changing the new object shouldn't change the old, so new list
		this.totalWeight = totalWeight;
		this.totalRating = totalRating;
		this.validPayload = validPayload;
		//this is not handled by interactionEffects below because massCredit is complex
		this.exclusiveConstraint = exclusiveConstraint; // Linking the lists for constraints is fine
		this.combinedSavings = combinedSavings;			// since the constraints are hardcoded.		
		this.weightSavings = weightSavings;				// Set initially, never changed after.
		this.preReqConstraint = preReqConstraint;
		this.massCredit = massCredit;
		
		checkPayloadValidity(); //replaces the obligatory call to //this.interactionEffects();
	}

	/**
	 * 
	 * @param old
	 * creates a new Payload with the same parameters as the old.
	 * Make sure you immediately call //.interactionEffects(massCreditApplied);
	 */
	public Payload(Payload old) {
		super();
		this.items = new ArrayList<>(old.items);
		this.totalWeight = old.totalWeight;
		this.totalRating = old.totalRating;
		this.validPayload = old.validPayload;
		//this is not handled by interactionEffects below because massCredit is complex
		this.exclusiveConstraint = old.exclusiveConstraint;
		this.combinedSavings = old.combinedSavings;
		this.weightSavings = old.weightSavings;
		this.preReqConstraint = old.preReqConstraint;
		this.massCredit = old.massCredit;  //mass credit is copied, so payloads can't get weight saving twice
		
		checkPayloadValidity(); //replaces the obligatory call to //this.interactionEffects();
	}

	void add(Experiment e) {
		items.add(e);
		totalWeight += e.weight;
		totalRating += e.rating;
		
		checkPayloadValidity();
		//If you want to know if you're always calling interactionEffects
		// on a payload right after you change it
		// You can comment this out and see if anything breaks
	}

	/**
	 * 
	 * @param exclusiveConstraint1
	 * @param combinedSavings1
	 * @param savings1
	 * @param preReqConstraint1
	 * 
	 * Adds all the constraints using the given constraints.
	 * Also notes what weightSavings will be for if the payload qualifies
	 */
	public void addConstraints(List<Experiment> exclusiveConstraint1,
			List<Experiment> combinedSavings1, int savings1,
			List<Experiment> preReqConstraint1) {
		exclusiveConstraint = exclusiveConstraint1;
		combinedSavings = combinedSavings1;
		weightSavings = savings1;
		preReqConstraint = preReqConstraint1;
	}

	/**
	 * 
	 * @param massCreditApplied
	 * checks constraints if they exist.
	 * 
	 * Checks if you can get that mass credit if it hasn't been applied already.
	 * Call every time you create or change a payload.
	 */
	public void interactionEffects(boolean massCreditApplied) {
		validPayload = true;

		if (exclusiveConstraint != null && !exclusiveConstraint.isEmpty())
			checkExclusive();

		if (combinedSavings != null && !combinedSavings.isEmpty() && weightSavings != 0)
			checkCombined(massCreditApplied);

		if (preReqConstraint != null && !preReqConstraint.isEmpty())
			checkPrereq();
	}
	
	/**
	 * 
	 * checks payload validity against the existing constraints.
	 * Does not check if there are weight savings from mass credit
	 */
	public void checkPayloadValidity() {
		validPayload = true;

		if (exclusiveConstraint != null && !exclusiveConstraint.isEmpty())
			checkExclusive();

		if (preReqConstraint != null && !preReqConstraint.isEmpty())
			checkPrereq();
	}

	/**
	 * 
	 * Checks the exclusive constraint:
	 * updates validPayload to false if ALL experiments in exclusiveConstraint are present.
	 */
	void checkExclusive() {

		for (Experiment item : exclusiveConstraint) {
			if (!items.contains(item))
				return;
		}

		validPayload = false;
	}

	/**
	 * 
	 * @param massCreditApplied
	 * If massCreditHasn't been applied (externally), run the internal check.
	 * Does nothing if massCredit has been applied.
	 */
	void checkCombined(boolean massCreditApplied) {
		
		if(massCreditApplied)
			return;
		
		if (massCredit)
			return;

		// If the Yeast Experiment is added, then Seed Viabilty
		// this fixes the total payload weight and sets (internal) massCredit to true.
		// There's logic in strategies that handles fixing the weight of the Yeast Experiment retroatively
		// which fires if massCredit is true.
		if (items.contains(combinedSavings.getFirst()) && items.contains(combinedSavings.getLast())) {
			totalWeight -= weightSavings;
			massCredit = true;
		}
	}

	/**
	 * 
	 * Sets validPayload to false if the last element of a preReqConstraint is present, but not the first.
	 */
	void checkPrereq() {
		if (items.contains(preReqConstraint.getLast()) && !items.contains(preReqConstraint.getFirst()))
			validPayload = false;
	}
}
