package com.vodafone.service;

import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static List<Customer> customers;
	private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	static{
		customers= populateDummyCustomers();
	}
	
	@Override
	public Customer findById(long id) throws UserNotFoundException {
		customers.forEach(System.out::println);
		Customer customerFound =  customers.stream().filter(customer -> id == customer.getId().longValue()).findAny().orElse(null);
		if(customerFound == null)
		{	
			logger.error("user not found");
			throw new UserNotFoundException();	
		}
		else 
			return customerFound;
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
//		//
//		Customer customer2 = new Customer();
//		customer2.setId(2l);
//		Customer.FullName fullname2 = customer2.new FullName();
//		fullname2.setFirstName("Bothinah");
//		fullname2.setMiddleName("Mostafa");
//		fullname2.setLastName("Youssef");
//		customer2.setFullName(fullname2);
//		customers.add(customer2);
//		//
//		Customer customer3 = new Customer();
//		customer3.setId(3l);
//		Customer.FullName fullname3 = customer3.new FullName();
//		fullname3.setFirstName("Dina");
//		fullname3.setMiddleName("Ashraf");
//		fullname3.setLastName("El-sayed");
//		customer3.setFullName(fullname3);
//		customers.add(customer3);
//		//
		return customers;
	}

}
