package project3;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author BraydenBigelow
 */
public class Payload {

	final List<Experiment> items = new ArrayList<>();
	int totalWeight;
	int totalRating;
	boolean validPayload = true;
	
	List<Experiment> exclusiveConstraint  = new ArrayList<>();
	List<Experiment> combinedSavings  = new ArrayList<>();
	int weightSavings;
	List<Experiment> preReqConstraint  = new ArrayList<>();
	
	public Payload() {
	}
	
	public Payload(int totalWeight, int totalRating, boolean validPayload, List<Experiment> exclusiveConstraint,
			List<Experiment> combinedSavings, int weightSavings, List<Experiment> preReqConstraint) {
		super();
		this.totalWeight = totalWeight;
		this.totalRating = totalRating;
		this.validPayload = validPayload;
		this.exclusiveConstraint = exclusiveConstraint;
		this.combinedSavings = combinedSavings;
		this.weightSavings = weightSavings;
		this.preReqConstraint = preReqConstraint;
	}
	
	public Payload(Payload old) {
		super();
		this.totalWeight = old.totalWeight;
		this.totalRating = old.totalRating;
		this.validPayload = old.validPayload;
		this.exclusiveConstraint = old.exclusiveConstraint;
		this.combinedSavings = old.combinedSavings;
		this.weightSavings = old.weightSavings;
		this.preReqConstraint = old.preReqConstraint;
	}

	void add(Experiment e) {
		items.add(e);
		totalWeight += e.weight;
		totalRating += e.rating;
	}
	// if payload includes items.name.equals Solar Flares && Solar Power && Sun Spots, reject
	// if payload includes items.name.equals Seed Viability && Yeast Fermentation, total weight -= 7 
	// (check must happen before experiment gets rejected by totalWeight += weight <= maxWeight
	// if payload includes items.name.equals Cosmic Rays (Secondary Band) && !Cosmic Rays (Primary Band), reject
	
	public void addConstraints(List<Experiment> exclusiveConstraint1,
			List<Experiment> combinedSavings1, int savings1,
			List<Experiment> preReqConstraint1) {
		exclusiveConstraint = exclusiveConstraint1;
		combinedSavings = combinedSavings1;
		weightSavings = savings1;
		preReqConstraint = preReqConstraint1;
	}
	
	public void addexclusiveConstraint(List<Experiment> exclusiveConstraint1)
	{
		exclusiveConstraint = exclusiveConstraint1;
	}
	
	public void interactionEffects()
	{
		checkExclusive();
//		checkCombined();
//		checkPrereq()
	}
	
//	void checkExclusive(Experiment a, Experiment b, Experiment c) {
	void checkExclusive() {
//		if (items.contains(a) && items.contains(b) && items.contains(c)) {
//			validPayload = false;
//		}
		
		//use a test to make sure there's not a type issue
		//between items (experiment list) and checking if it contains a string
		for(Experiment item : exclusiveConstraint)
		{
			if(!items.contains(item))
				return;
		}
		
		validPayload = false;
	}
	
//	void checkCombined(Experiment a, Experiment b, int weightSavings) {
	void checkCombined() {
//		if (items.contains(a) && items.contains(b)) {
//			totalWeight -= weightSavings;
//		}
		
		if(items.contains(combinedSavings.getFirst()) && items.contains(combinedSavings.getLast()))
			totalWeight -= weightSavings;
		
	}
	
//	void checkPrereq(Experiment a, Experiment b) {
	void checkPrereq() {
//		if (items.contains(b) && !items.contains(a)) {
//			validPayload = false;
//		}
		
		if (items.contains(preReqConstraint.getLast()) && !items.contains(preReqConstraint.getFirst())) {
			validPayload = false;
		}
	}
}
