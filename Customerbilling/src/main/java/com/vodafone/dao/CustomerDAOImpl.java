package com.vodafone.dao;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vodafone.model.Customer;
import com.vodafone.model.FullName;
import com.vodafone.service.CustomerServiceImpl;

@Component
public class CustomerDAOImpl implements CustomerDAO {

	private static List<Customer> customers;
	private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	static{
		customers= populateDummyCustomers();
	}
	
	
		@Override
		public void save(Customer customer) {

			Customer possibleCustomer = customers.stream().filter(c -> customer.getId() == c.getId().longValue()).findAny()
					.orElse(null);

			if (possibleCustomer != null) {

				customers.get(0).setAddress(customer.getAddress());
				customers.get(0).setAge(customer.getAge());
				customers.get(0).setFullName(customer.getFullName());
				customers.get(0).setMobileNumber(customer.getMobileNumber());
			} else {
				throw new UsernameNotFoundException("Customer with id "+ customer.getId()+" Not found ");
			}

		}

		@Override
		public void delete(Customer customer) {

			Customer possibleCustomer = customers.stream().filter(c -> customer.getId() == c.getId().longValue()).findAny()
					.orElse(null);

			if (possibleCustomer != null) {
				customers.remove(possibleCustomer);
			} else {
				throw new UsernameNotFoundException("Customer with id "+ customer.getId()+" Not found ");
			}
		}

	
	@Override
	public List<Customer> findAll() {
		return customers;
	}

	
	
	private static List<Customer> populateDummyCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer1 = new Customer();
		customer1.setId(1l);
		FullName fullname1 = new FullName();
		fullname1.setFirstName("Moh1");
	         	fullname1.setMiddleName("Ali1");
		fullname1.setLastName("Rizk1");
		customer1.setFullName(fullname1);
		customers.add(customer1);
		return customers;
	}
}