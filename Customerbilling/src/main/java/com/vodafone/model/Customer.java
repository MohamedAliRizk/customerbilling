package com.vodafone.model;

import java.io.Serializable;

import javax.validation.Valid;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames = true)
@Data
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Gender {
		MALE, FEMALE
	}

	/*@NotNull(groups = { Update.class }, message = "id shouldn't be empty in case of Updating")
	@Null(groups = { Create.class }, message = "id should be empty in case of creating")*/
	private Long id;
	@Valid
	private FullName fullName;
	private int age;
	private Gender gender;

	private Address address;
	private String mobileNumber;

	public Customer() {

	}

	public Customer(FullName fullName, Long id) {
		this.fullName = fullName;
		this.id = id;
	}

}
