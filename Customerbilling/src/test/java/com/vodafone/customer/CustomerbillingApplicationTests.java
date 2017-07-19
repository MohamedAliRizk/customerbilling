package com.vodafone.customer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;

public class CustomerbillingApplicationTests extends AbstractMvcTest {
	
	private String getAccessToken(String username, String password) throws Exception {
		String authorization = "Basic " + new String(Base64Utils.encode("clientapp:123456".getBytes()));
		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

		String content = mockMvc
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
		return content.substring(17, 53);
	}
	
	@Test
	public void testListAllCustomers_Authorized() throws Exception {
		final String token = extractToken(login("bill", "abc123").andReturn());
		mockMvc.perform(get("/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)));
	}
	
	@Test
	public void testListAllCustomers_UnauthorizedInvalidPassword() throws Exception {
		final String token = extractToken(login("bill", "abc1234").andReturn());
		assertNull(token);
		// can't perform request as the token will be null and we can't put null in header
		/*mockMvc.perform(get("/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isUnauthorized());*/
	}
	
	@Test
	public void testGetCustomer_Authorized() throws Exception {
		final String token = extractToken(login("bill", "abc123").andReturn());
		mockMvc.perform(get("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)));
	}
	
	@Ignore("To configure roles in JWT attemptauthentication")
	@Test
	public void testGetCustomer_UnauthorizedRole() throws Exception {
		final String token = extractToken(login("tom", "abc123").andReturn());
		mockMvc.perform(get("/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void contextLoads() {
	}

}
