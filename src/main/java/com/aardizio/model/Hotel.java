package com.aardizio.model;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;
@Data
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

	
}
