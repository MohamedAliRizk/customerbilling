package com.vodafone.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class AllCustomerBillsTests extends AbstractMvcTest {
	
	@Test
	public void tesGetAllCustomerBills_CustomerFound() throws Exception {
		final String token = extractToken(login("bill", "abc123").andReturn());
		mockMvc.perform(get("/customers/1/bills")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void tesGetAllCustomerBills_CustomerNotFound() throws Exception {
		final String token = extractToken(login("bill", "abc123").andReturn());
		mockMvc.perform(get("/customers/2/bills")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isNotFound());
	}
}
