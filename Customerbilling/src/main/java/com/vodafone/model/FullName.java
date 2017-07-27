package com.vodafone.model;



import org.hibernate.validator.constraints.NotEmpty;

import com.vodafone.groups.Update;

import lombok.Data;
import lombok.ToString;

@ToString(includeFieldNames=true)
@Data
public class FullName {
	@NotEmpty(groups={Update.class},message="first Name is mandatory") 
	private  String  firstName;
	
	private  String middleName; 
	@NotEmpty(groups={Update.class},message="last name is mandatory ")
	private  String lastName;

	
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