package com.vodafone.customer;

import static org.mockito.Matchers.isNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
public class AllCustomerBillsTests {
	@Autowired
	WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
	}
	
	@Test
	public void tesGetAllCustomerBills_CustomerFound() throws Exception {
		mvc.perform(get("/customers/1/bills")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("bill", "abc123")))
				.andExpect(status().isOk());
			
	}
	//204
	@Test
	public void tesGetAllCustomerBills_CustomerNotFound() throws Exception {
		mvc.perform(get("/customers/2/bills")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("bill", "abc123")))
				.andExpect(status().isNotFound());
			
	}
}
