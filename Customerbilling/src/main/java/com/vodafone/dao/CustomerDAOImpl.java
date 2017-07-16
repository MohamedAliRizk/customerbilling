package com.vodafone.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.vodafone.commonExceptions.CustomerNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.model.FullName;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private static List<Customer> customers;

	static {
		customers = populateDummyCustomers();
	}

	private static List<Customer> populateDummyCustomers() {
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

	// Mainly cassandra repo save method
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
			throw new CustomerNotFoundException("Customer Not found", customer.getId() + "");
		}

	}

	@Override
	public void delete(Customer customer) {

		Customer possibleCustomer = customers.stream().filter(c -> customer.getId() == c.getId().longValue()).findAny()
				.orElse(null);

		if (possibleCustomer != null) {
			customers.remove(possibleCustomer);
		} else {
			throw new CustomerNotFoundException("Customer Not found", customer.getId() + "");
		}
	}

}