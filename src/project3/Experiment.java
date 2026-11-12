package project3;

import java.util.Objects;

/**
 * TODO
 * 
 * @team_Name Rocket Escape
 * @team_Members BraydenBigelow, NathanJohnson
 * @course_and_Section CS-2430, section 001
 * @project_Name Programming Project 3 - Fall 2026
 * @author BraydenBigelow
 */
public class Experiment {

	final String name;
	final int weight;
	final int rating;
	
	public Experiment(String name, int weight, int rating) {
		this.name = name;
		this.weight = weight;
		this.rating = rating;
	}
	
	double ratio() {
		return (double) rating / weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, rating, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Experiment other = (Experiment) obj;
		return Objects.equals(name, other.name) && rating == other.rating && weight == other.weight;
	}
}
