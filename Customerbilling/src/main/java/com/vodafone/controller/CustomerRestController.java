package com.vodafone.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;
import com.vodafone.service.CustomerServiceImpl;

@RequestMapping("/Customers")
@RestController
@Secured("ACTUATOR")
public class CustomerRestController {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(method=RequestMethod.GET , produces="application/json")
	@PreAuthorize("hasRole('ACTUATOR')")
	public List<Customer> getAllCustomers(){
		System.out.println("Inside getAllCustomers.");
		return customerService.findAllCustomers();
	}

	private static final Logger LOGGER = Logger.getLogger(CustomerRestController.class);
	
	
	
	@RequestMapping(value = "/customers/", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> listAllCustomers() {
		LOGGER.debug("Start listAllCustomers Method.");
		List<Customer> customers = customerService.findAllCustomers();
		LOGGER.debug("Getting Customer List from Customer Service.");
		if (customers.isEmpty()) {
			LOGGER.info("Customer List is empty.");
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
		LOGGER.info("Fetching Customer with id " + id);
		Customer customer = customerService.findById(id);
		if (customer == null) {
			LOGGER.info("Customer with id " + id + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

}
