package com.aardizio.model;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "hotel", name = "address")
public class Address {
	
	private String street;
	private String city;
	private String state_or_province;
	private String postal_code;
	private String country;
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state_or_province
	 */
	public String getState_or_province() {
		return state_or_province;
	}
	/**
	 * @param state_or_province the state_or_province to set
	 */
	public void setState_or_province(String state_or_province) {
		this.state_or_province = state_or_province;
	}
	/**
	 * @return the postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}
	/**
	 * @param postal_code the postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
