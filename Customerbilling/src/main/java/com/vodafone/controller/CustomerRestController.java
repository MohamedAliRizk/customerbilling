package com.vodafone.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.exception.ServiceException;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;

@RestController
public class CustomerRestController {

	private static final Logger LOGGER = Logger.getLogger(CustomerRestController.class);
	
	@Autowired
	CustomerService customerService;
	
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
	public @ResponseBody ResponseEntity<?> getCustomer(@PathVariable("id") long id) throws UserNotFoundException {
		LOGGER.info("Fetching Customer with id " + id);
		Customer customer = null;
			 customer = customerService.findById(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
}
