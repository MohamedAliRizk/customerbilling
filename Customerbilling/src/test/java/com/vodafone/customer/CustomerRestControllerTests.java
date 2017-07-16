package com.vodafone.customer;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.model.Address;
import com.vodafone.model.FullName;

@RunWith(value = SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerRestControllerTests {

	private String country = "Sweden";

	private String mobileNumber = "01099749369";

	private int age = 25;

	@Autowired
	private TestRestTemplate restTemplate;

	private Address address;

	private FullName fullName;

	private CustomerUpdateDTO customerUpdateDTO;

	private HttpEntity<CustomerUpdateDTO> httpEntity;

	private HttpHeaders headers;

	@Before
	public void setup() {

		address = new Address();
		address.setCity("Stockholm");
		address.setCountry(country);
		address.setStreet("4, 1155 Square");

		fullName = new FullName();
		fullName.setFirstName("Ahmed");
		fullName.setMiddleName("Saeed");
		fullName.setLastName("Muhammed");

		customerUpdateDTO = new CustomerUpdateDTO();
		customerUpdateDTO.setAddress(address);
		customerUpdateDTO.setFullName(fullName);
		customerUpdateDTO.setAge(age);
		customerUpdateDTO.setMobileNumber(mobileNumber);

		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

	}

	@Test
	public void givenValidCustomer_whenupdateCustomer_thenUpdateSuccessfully() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 1l);

		httpEntity = new HttpEntity<CustomerUpdateDTO>(customerUpdateDTO, headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange("/customer/{id}/update",
				HttpMethod.PUT, httpEntity, CustomerUpdateRepresentation.class, urlVariables);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(age, response.getBody().getAge());
		assertEquals(country, response.getBody().getAddress().getCountry());
	}

	@Test
	public void givenInvalidCustomer_whenupdateCustomer_thenExpectBadRequestStatus() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 2l);

		httpEntity = new HttpEntity<CustomerUpdateDTO>(customerUpdateDTO, headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange("/customer/{id}/update",
				HttpMethod.PUT, httpEntity, CustomerUpdateRepresentation.class, urlVariables);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void givenValidCustomer_whendeleteCustomer_thendeleteSuccessfully() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 1l);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange("/customer/{id}/delete",
				HttpMethod.DELETE, null, CustomerUpdateRepresentation.class, urlVariables);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	public void givenInvalidCustomer_whendeleteCustomer_thenExpectNothing() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 2l);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange("/customer/{id}/delete",
				HttpMethod.DELETE, null, CustomerUpdateRepresentation.class, urlVariables);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}
}