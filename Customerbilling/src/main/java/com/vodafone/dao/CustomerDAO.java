package com.vodafone.dao;

import java.util.List;

import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;

public interface CustomerDAO {

	public void save(Customer customer);

	public void delete(Customer customer);

	List<Customer> findAll();
}
