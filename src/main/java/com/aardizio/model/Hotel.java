package com.aardizio.model;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "hotel", name = "hotel")
public class Hotel {

	@PartitionKey
	private String uuid;
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "pois")
	private Set<String> pois;

	/**
	 * @return the id
	 */
	public String getId() {
		return uuid;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.uuid = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the pointsOfInterest
	 */
	public Set<String> getPointsOfInterest() {
		return pois;
	}

	/**
	 * @param pointsOfInterest the pointsOfInterest to set
	 */
	public void setPointsOfInterest(Set<String> pointsOfInterest) {
		this.pois = pointsOfInterest;
	}

	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return uuid;
	}

}
