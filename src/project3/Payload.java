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
	
	void add(Experiment e) {
		items.add(e);
		totalWeight += e.weight;
		totalRating += e.rating;
	}
	// if payload includes items.name.equals Solar Flares && Solar Power && Sun Spots, reject
	// if payload includes items.name.equals Seed Viability && Yeast Fermentation, total weight -= 7 
	// (check must happen before experiment gets rejected by totalWeight += weight <= maxWeight
	// if payload includes items.name.equals Cosmic Rays (Secondary Band) && !Cosmic Rays (Primary Band), reject
	
	void checkExclusive(Experiment a, Experiment b, Experiment c) {
		if (items.contains(a) && items.contains(b) && items.contains(c)) {
			validPayload = false;
		}
	}
	
	void checkCombined(Experiment a, Experiment b, int weightSavings) {
		if (items.contains(a) && items.contains(b)) {
			totalWeight -= weightSavings;
		}
	}
	
	void checkPrereq(Experiment a, Experiment b) {
		if (items.contains(b) && !items.contains(a)) {
			validPayload = false;
		}
	}
}
