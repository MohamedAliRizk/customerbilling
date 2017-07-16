package com.vodafone.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.commonExceptions.CustomerNotFoundException;
import com.vodafone.commonExceptions.InternalServerError;
import com.vodafone.controller.CustomerRestController;
import com.vodafone.dao.CustomerDAO;
import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.model.FullName;
import com.vodafone.utils.Validator;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static List<Customer> customers;

	@Autowired
	private CustomerDAO customerDAOImpl;
	

	private static final Logger LOGGER = Logger.getLogger(CustomerRestController.class);

	static {
		customers = populateDummyCustomers();
	}

	@Override
	public Customer findById(long id) throws UserNotFoundException {
		customers.forEach(System.out::println);
		Customer customerFound =  customers.stream().filter(customer -> id == customer.getId().longValue()).findAny().orElse(null);
		if(customerFound == null)
		{	
			LOGGER.error("user not found");
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

	@Override
	public CustomerUpdateRepresentation updateCustomer(CustomerUpdateDTO customerUpdateDTO, Long id) {

		if (!Validator.isValidCustomerUpdateData(customerUpdateDTO) || id == null || id.longValue() <= 0) {

			//TODO throw invalidrequest exception 
		}

		Customer customer = null;

		try {

			customer = findById(id);

		} catch (Exception ex) {
			// TODO add possible logic here
			LOGGER.error("Error while finding customer with id " + id + " : " + ex.getMessage());
			
			throw new InternalServerError("Error finding customer id", id+"", ex.getMessage());
		}

		if(customer==null){
			//TODO throw dina one
			throw new CustomerNotFoundException("Customer was not found", id+"");
		}
		customer.setAddress(customerUpdateDTO.getAddress());
		customer.setFullName(customerUpdateDTO.getFullName());
		customer.setAge(customerUpdateDTO.getAge());
		customer.setMobileNumber(customerUpdateDTO.getMobileNumber());
		customerDAOImpl.save(customer);

		CustomerUpdateRepresentation customerUpdateRepresentation = new CustomerUpdateRepresentation();
		
		if(customer.getAddress()!=null){
			customerUpdateRepresentation.setAddress(customer.getAddress());
		}
		if(customer.getFullName()!=null){
			customerUpdateRepresentation.setFullName(customer.getFullName());
		}
		customerUpdateRepresentation.setAge(customer.getAge());
		customerUpdateRepresentation.setId(customer.getId());
		customerUpdateRepresentation.setMobileNumber(customer.getMobileNumber());
		return customerUpdateRepresentation;

	}

	@Override
	public void deleteCustomerById(Long id) {

		Customer customer = null;
		try {
			customer = findById(id);
		} catch (Exception ex) {
			LOGGER.error("error finding customer with id " + id + " : " + ex.getMessage());
			throw new InternalServerError("Error finding customer id", id+"", ex.getMessage());
		}

		if (customer == null) {
			LOGGER.warn("No customer found with id " + id);
			
			//TODO throw customernotfound
		} else {
			// a repo delete attempt would be called here.
			try{
				customerDAOImpl.delete(customer);
			}catch(Exception ex){
				//TODO proper error message
				LOGGER.error("");
			}
			LOGGER.info("Customer with id " + id + " is now gone");
		}
	}
}