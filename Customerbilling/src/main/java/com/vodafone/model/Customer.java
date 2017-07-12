package com.vodafone.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
public class Customer implements Serializable {

	public enum Gender {
		MALE, FEMALE
	}

	private Long id;
	private FullName fullName;
	private int age;
	private Gender gender;
	private Address address;
	private String mobileNumber;

	@ToString(includeFieldNames=true)
	@Data
	public class FullName {
		private String firstName, middleName, lastName;
	}

	public @Data class Address {
		private String street, city, country;
	}

}
