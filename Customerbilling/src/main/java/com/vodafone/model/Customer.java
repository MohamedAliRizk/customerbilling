package com.vodafone.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Gender {
		MALE, FEMALE
	}

	private Long id;
	private FullName fullName;
	private int age;
	private Gender gender;
	private Address address;
	private String mobileNumber;

	public Customer(){
		
	}
	public Customer(FullName fullName , Long id){
		this.fullName = fullName;
		this.id = id;
	}

	@ToString(includeFieldNames=true)
	@Data
	public class FullName {
		private String firstName, middleName, lastName;
	}

	public @Data class Address {
		private String street, city, country;
	}

}
