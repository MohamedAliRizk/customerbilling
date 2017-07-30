/*package com.vodafone.customer;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.model.Address;
import com.vodafone.model.FullName;

//@RunWith(value = SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerRestControllerTests extends AbstractMvcTest {

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
	public void setup() throws UnsupportedEncodingException, Exception {

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
		// admin role
		headers.set("Authorization", extractToken(login("tom", "abc123").andReturn()));

		// mockMvc =
		// MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();

	}

	@Test
	public void givenValidCustomer_whenupdateCustomer_thenUpdateSuccessfully() {

		//Map<String, Long> urlVariables = new HashMap<>();
		//urlVariables.put("id", 1l);

		// user role
		// headers.set("Authorization", "Basic dG9tOmFiYzEyMw==");
		httpEntity = new HttpEntity<CustomerUpdateDTO>(customerUpdateDTO, headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange(
				"/customers/customer/update", HttpMethod.PUT, httpEntity, CustomerUpdateRepresentation.class);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(age, response.getBody().getAge());
		assertEquals(country, response.getBody().getAddress().getCountry());
	}

	@Test
	public void givenInvalidCustomer_whenupdateCustomer_thenExpectBadRequestStatus() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 2l);

		// user role
		// headers.set("Authorization", "Basic dG9tOmFiYzEyMw==");
		httpEntity = new HttpEntity<CustomerUpdateDTO>(customerUpdateDTO, headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange(
				"/customers/customer/update", HttpMethod.PUT, httpEntity, CustomerUpdateRepresentation.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}

	@Test
	public void givenValidCustomer_whendeleteCustomer_thendeleteSuccessfully() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 1l);

		httpEntity = new HttpEntity<>(headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange(
				"/customers/customer/{id}/delete", HttpMethod.DELETE, httpEntity, CustomerUpdateRepresentation.class,
				urlVariables);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	public void givenInvalidCustomer_whendeleteCustomer_thenExpectNothing() {

		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", 2l);

		httpEntity = new HttpEntity<>(headers);

		ResponseEntity<CustomerUpdateRepresentation> response = this.restTemplate.exchange(
				"/customers/customer/{id}/delete", HttpMethod.DELETE, httpEntity, CustomerUpdateRepresentation.class,
				urlVariables);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}*/