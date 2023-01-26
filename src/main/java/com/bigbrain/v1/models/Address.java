package com.bigbrain.v1.models;

@Entity
public class Address {
	
	private int userIDPK;
	private String addressIDPK;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String stateCodeIDFK;
	private int zipCode;

}
