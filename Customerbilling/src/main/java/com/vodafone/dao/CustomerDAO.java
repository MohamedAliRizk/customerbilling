package com.vodafone.dao;

import com.vodafone.model.Customer;

public interface CustomerDAO {
	
	public void save(Customer customer);
	
	public void delete(Customer customer);

}
