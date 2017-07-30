package com.vodafone.service;

import java.util.List;

import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.exception.DatabaseException;
import com.vodafone.exception.ServiceException;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;

public interface CustomerService {

	Customer findById(long id) throws UserNotFoundException;

	Customer findByName(String name);

	void saveCustomer(Customer customer);

	public CustomerUpdateRepresentation updateCustomer(CustomerUpdateDTO customer) throws DatabaseException, UserNotFoundException;

	public void deleteCustomerById(Long id) throws ServiceException, DatabaseException;

	List<Customer> findAllCustomers();

	void deleteAllCustomers();

	public boolean isCustomerExist(Customer customer);
}