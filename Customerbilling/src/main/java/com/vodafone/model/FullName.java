package com.vodafone.model;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
@Data
public class FullName {
	private String firstName, middleName, lastName;

	
	public FullName(String firstName, String middleName, String lastName) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}


	public FullName() {
		super();
	}
	
	
}