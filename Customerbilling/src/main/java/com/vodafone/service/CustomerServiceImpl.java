package com.vodafone.service;

import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.dao.CustomerDAO;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDAO customerDAO;
	
	@Override
	public Customer findById(long id) throws UserNotFoundException {
		return customerDAO.findById(id);
	}

	@Override
	public Customer findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Customer> findAllCustomers() {
		return customerDAO.findAllCustomers();
	}

	@Override
	public void deleteAllCustomers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCustomerExist(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static List<Customer> populateDummyCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer1 = new Customer();
		customer1.setId(1l);
		Customer.FullName fullname1 = customer1.new FullName();
		fullname1.setFirstName("Moh1");
		fullname1.setMiddleName("Ali1");
		fullname1.setLastName("Rizk1");
		customer1.setFullName(fullname1);
		customers.add(customer1);
		return customers;
	}

}
