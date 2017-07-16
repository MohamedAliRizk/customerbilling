package com.vodafone.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vodafone.commonExceptions.CustomerNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.model.FullName;

@Component
public class CustomerDAOImpl implements CustomerDAO {

	public static List<Customer> customers;
	public CustomerDAOImpl(){
		customers = new ArrayList<>();
		FullName fullName1 = new FullName("Bothinah" , "Mostafa" , "Youssef");
		FullName fullName2 = new FullName("Dina" , "Ashraf" , "Youssef");
		FullName fullName3 = new FullName("Hatem" , "Mostafa" , "Youssef");
		FullName fullName4 = new FullName("Tallat" , "Mostafa" , "Youssef");
		customers.add(new Customer(fullName1 , 1l));
		customers.add(new Customer(fullName2 , 2l));
		customers.add(new Customer(fullName3 , 3l));
		customers.add(new Customer(fullName4 , 4l));
	}
	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
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
