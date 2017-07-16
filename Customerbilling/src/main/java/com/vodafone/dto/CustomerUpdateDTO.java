package com.vodafone.dto;

import com.vodafone.model.Address;
import com.vodafone.model.FullName;

import lombok.Data;

@Data
public class CustomerUpdateDTO {

	private FullName fullName;
	private int age;
	private Address address;
	private String mobileNumber;
}
