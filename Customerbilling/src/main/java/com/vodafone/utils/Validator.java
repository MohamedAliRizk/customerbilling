package com.vodafone.utils;

import com.vodafone.dto.CustomerUpdateDTO;

public class Validator {

	
	public static boolean isValidCustomerUpdateData(CustomerUpdateDTO customerUpdateDTO){
		  
		if(customerUpdateDTO==null){
			return false;
		}else if(customerUpdateDTO.getFullName()==null){
			return false;
		}else if(customerUpdateDTO.getAddress()==null){
			return false;
		}else if(customerUpdateDTO.getMobileNumber()==null){
			return false;
		}
		
		return true;
	}
}
