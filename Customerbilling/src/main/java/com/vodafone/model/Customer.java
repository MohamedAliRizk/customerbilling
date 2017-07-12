package com.vodafone.model;

import java.io.Serializable;

import lombok.Data;

public @Data class Customer implements Serializable {

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

	public Customer(){
		
	}
	public Customer(FullName fullName , Long id){
		this.fullName = fullName;
		this.id = id;
	}
	
	@Data
	public class FullName {
		private String firstName, middleName, lastName;
		public FullName(String firstName , String middleName, String lastName){
			this.firstName = firstName;
			this.lastName = lastName;
			this.middleName = middleName;
		}
	}

	@Data class Address {
		private String street, city, country;
	}

}
