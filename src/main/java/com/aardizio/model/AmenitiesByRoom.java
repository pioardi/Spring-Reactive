package com.aardizio.model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "hotel", name = "amenities_by_room")
public class AmenitiesByRoom {

	@PartitionKey(0)
	@Column(name="hotel_id")
	private String hotelId;
	@PartitionKey(1)
	@Column(name = "room_number")
	private Short roomNumber;
	@ClusteringColumn(0)
	@Column(name = "amenity_name")
	private String amenityName;
	@Column(name = "description")
	private String description;

	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId
	 *            the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return the roomNumber
	 */
	public Short getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber
	 *            the roomNumber to set
	 */
	public void setRoomNumber(Short roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the amenityName
	 */
	public String getAmenityName() {
		return amenityName;
	}

	/**
	 * @param amenityName
	 *            the amenityName to set
	 */
	public void setAmenityName(String amenityName) {
		this.amenityName = amenityName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
