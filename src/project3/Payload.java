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
	
	void add(Experiment e) {
		items.add(e);
		totalWeight += e.weight;
		totalRating += e.rating;
	}
}
