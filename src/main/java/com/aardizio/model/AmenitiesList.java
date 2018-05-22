package com.aardizio.model;

import java.io.Serializable;
import java.util.List;

public class AmenitiesList  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<AmenitiesByRoom> amenities;
	private String previousCursor;
	private String nextCursor;

	/**
	 * @return the previousCursor
	 */
	public String getPreviousCursor() {
		return previousCursor;
	}

	/**
	 * @param previousCursor
	 *            the previousCursor to set
	 */
	public void setPreviousCursor(String previousCursor) {
		this.previousCursor = previousCursor;
	}

	/**
	 * @return the nextCursor
	 */
	public String getNextCursor() {
		return nextCursor;
	}

	/**
	 * @param nextCursor
	 *            the nextCursor to set
	 */
	public void setNextCursor(String nextCursor) {
		this.nextCursor = nextCursor;
	}

	/**
	 * @return the amenities
	 */
	public List<AmenitiesByRoom> getAmenities() {
		return amenities;
	}

	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(List<AmenitiesByRoom> amenities) {
		this.amenities = amenities;
	}

}
