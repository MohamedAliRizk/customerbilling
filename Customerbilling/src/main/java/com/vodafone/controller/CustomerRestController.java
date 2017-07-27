package com.vodafone.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.exception.DatabaseException;
import com.vodafone.exception.ServiceException;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.groups.Update;
import com.vodafone.model.Bill;
import com.vodafone.model.Customer;
import com.vodafone.service.BillService;
import com.vodafone.service.CustomerService;

@RequestMapping("/customers")
@RestController
// @Secured("ACTUATOR")
public class CustomerRestController {

	private static final Logger LOGGER = Logger.getLogger(CustomerRestController.class);

	@Autowired
	private CustomerService customerService;
	@Autowired
	private BillService billService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		LOGGER.debug("Inside getAllCustomers.");
		List<Customer> customers = customerService.findAllCustomers();
		if (customers.isEmpty()) {
			LOGGER.info("Customer List is empty.");
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
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

	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public @ResponseBody ResponseEntity<?> getCustomer(@PathVariable long id) throws UserNotFoundException {
		LOGGER.info("Fetching Customer with id " + id);
		Customer customer = null;
		customer = customerService.findById(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/customer/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCustomer(
			@Validated({ Update.class }) @RequestBody CustomerUpdateDTO customerUpdateDTO)
			throws UserNotFoundException, DatabaseException{

		LOGGER.info("An attempt to update data for customer with id : " + customerUpdateDTO.getId());

		return new ResponseEntity<CustomerUpdateRepresentation>(customerService.updateCustomer(customerUpdateDTO),
				HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/customer/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws ServiceException, DatabaseException {

		LOGGER.info("An attempt to delete customer with id : " + id);

		customerService.deleteCustomerById(id);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "{customerID}/bills", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	// @PreAuthorize("hasRole('ADMIN')")
	public @ResponseBody ResponseEntity<?> getCustomerBills(@PathVariable("customerID") long customerID)
			throws UserNotFoundException {
		LOGGER.info("Fetching Customer Bills with id " + customerID);
		List<Bill> bills = null;
		bills = billService.findAllBills(customerID);
		if (bills.isEmpty()) {
			LOGGER.info("Bills List is empty.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
	}
}