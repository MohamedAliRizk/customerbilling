package com.vodafone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vodafone.model.Customer;


@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static List<Customer> customers;
	
	static{
		customers= populateDummyCustomers();
	}
	
	@Override
	public Customer findById(long id) {
		customers.forEach(System.out::println);
		return customers.stream().filter(customer -> id == customer.getId().longValue()).findAny().orElse(null);
		/*for (Customer customer : customers) {
			if (customer.getId() == id) return customer;
		}*/
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
		return customers;
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
