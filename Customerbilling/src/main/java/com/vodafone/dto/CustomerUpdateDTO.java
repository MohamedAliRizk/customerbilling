package com.vodafone.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Range;

import com.vodafone.groups.Create;
import com.vodafone.groups.Update;
import com.vodafone.model.Address;
import com.vodafone.model.FullName;

import lombok.Data;

@Data
public class CustomerUpdateDTO {
	@NotNull(groups = { Update.class }, message = "id shouldn't be empty in case of Updating")
	@Range(groups = { Update.class }, min = 1, message = "please enter valid id")
	@Null(groups = { Create.class }, message = "id should be empty in case of creating")
	private Long id;
	@NotNull(groups = { Update.class }, message = "name shouldn't be empty in case of Updating")
	@Null(groups = { Create.class }, message = "name should be empty in case of creating")
	@Valid
	private FullName fullName;
	private int age;
	private Address address;
	private String mobileNumber;
}
