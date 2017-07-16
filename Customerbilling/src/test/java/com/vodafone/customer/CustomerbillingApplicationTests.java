package com.vodafone.customer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerbillingApplicationTests {

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
	
	private String getAccessToken(String username, String password) throws Exception {
		String authorization = "Basic " + new String(Base64Utils.encode("clientapp:123456".getBytes()));
		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

		// @formatter:off
		String content = mvc
				.perform(
						post("/oauth/token")
								.header("Authorization", authorization)
								.contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.param("username", username)
								.param("password", password)
								.param("grant_type", "password")
								.param("scope", "read write")
								.param("client_id", "clientapp")
								.param("client_secret", "123456"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
				.andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
				.andExpect(jsonPath("$.scope", is(equalTo("read write"))))
				.andReturn().getResponse().getContentAsString();

		// @formatter:on

		return content.substring(17, 53);
	}
	
	@Test
	public void testListAllCustomers_Authorized() throws Exception {
		mvc.perform(get("/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("bill", "abc123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)));
	}
	
	@Test
	public void testListAllCustomers_UnauthorizedInvalidPassword() throws Exception {
		mvc.perform(get("/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("bill", "abc1234")))
				.andExpect(status().isUnauthorized());
	}
	
	public void testGetCustomer_Authorized() throws Exception {
		mvc.perform(post("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("bill", "abc123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.id", is(1)));
	}
	
	public void testGetCustomer_UnauthorizedRole() throws Exception {
		mvc.perform(post("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(httpBasic("tom", "abc123")))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void contextLoads() {
	}

}
