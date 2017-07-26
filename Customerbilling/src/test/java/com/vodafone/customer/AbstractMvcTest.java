package com.vodafone.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.dto.AccountCredentials;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AbstractMvcTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	protected static MockMvc mockMvc;

	private static Set<Class> inited = new HashSet<>();
	static final String HEADER_STRING = "Authorization";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
	}

	@Before
	public void init() throws Exception {
		if (!inited.contains(getClass())) {
			doInit();
			inited.add(getClass());
		}
	}

	protected void doInit() throws Exception {
	}

	protected static ResultActions login(String username, String password) throws Exception {
		final AccountCredentials credentials = new AccountCredentials();
		credentials.setUsername(username);
		credentials.setPassword(password);
		return mockMvc.perform(post("/login").content(json(credentials)).contentType(MediaType.APPLICATION_JSON));
	}

	protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
		// return JsonPath.read(result.getResponse().getContentAsString(),
		// "$.token");
		return result.getResponse().getHeader(HEADER_STRING);
	}

	protected static String json(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}

	@Test
	public void test() {
	}
}