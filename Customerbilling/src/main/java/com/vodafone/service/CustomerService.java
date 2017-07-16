package com.vodafone.service;

import java.util.List;

import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.model.Customer;

public interface CustomerService {

	Customer findById(long id);

	Customer findByName(String name);

	void saveCustomer(Customer customer);

	public CustomerUpdateRepresentation updateCustomer(CustomerUpdateDTO customer,Long id);

	public void deleteCustomerById(Long id);

	List<Customer> findAllCustomers();

	void deleteAllCustomers();

	public boolean isCustomerExist(Customer customer);
}