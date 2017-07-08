package com.vodafone.model;

import java.io.Serializable;

import lombok.Data;

public @Data class Customer implements Serializable {

	public enum Gender {
		MALE, FEMALE
	}

	private Long id;
	private FullName fullName;
	private int age;
	private Gender gender;
	private Address address;

	@Data class FullName {
		private String firstName, middleName, lastName;
	}

	@Data class Address {
		private String street, city, country;
	}

}
