package com.vodafone.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.commonExceptions.CustomerNotFoundException;
import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;

@RestController
public class CustomerRestController {

	private static final Logger LOGGER = Logger.getLogger(CustomerRestController.class);

	@Autowired
	private CustomerService customerService;

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

	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
		LOGGER.info("Fetching Customer with id " + id);
		Customer customer = customerService.findById(id);
		if (customer == null) {
			LOGGER.info("Customer with id " + id + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PutMapping(value = "/customer/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateDTO customerUpdateDTO, @PathVariable Long id) {

		LOGGER.info("An attempt to update data for customer with id : " + id);

		return new ResponseEntity<CustomerUpdateRepresentation>(customerService.updateCustomer(customerUpdateDTO, id),
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/customer/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

		LOGGER.info("An attempt to delete customer with id : " + id);

		customerService.deleteCustomerById(id);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Customer was not found")
	public void catchEx() {
	}
}