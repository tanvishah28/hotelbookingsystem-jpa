package com.entlogics.hotelbookingsystem_jpa.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	// mocking Customer service implementation
	@Mock
	CustomerServiceImpl customerService;

	// injecting mock to Customer Controller
	@InjectMocks
	CustomerController customerController;

	private MockMvc mockMvc;

	// Setup before each test
	@Before
	public void setup() {
		// building mockMvc instance using MockMvcBuilders - standaloneSetup & injected
		// customerController
		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Testing if response status is "OK"
	@Test
	public void getListofCustomers_TestResponseStatusIsOk() {

		// Create 2 CustomerDTO objects
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		CustomerDTO customerDto2 = new CustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list of type CustomerDTO & add the 2 CustomerDTO object
		List<CustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when customerService's getAllCustomers is called then return customerDtoList
		when(customerService.getAllCustomers()).thenReturn(customerDtoList);

		try {
			// calling perform() to send GET request & test if status is 200 OK
			mockMvc.perform(get("/customers")).andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if the response received has content type Application/JSON
	@Test
	public void getListofCustomers_TestResponseContentType() {

		// Create CustomerDTO objects
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		CustomerDTO customerDto2 = new CustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list & add all customerDTO objects
		List<CustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when getAllCustomers is called then return customerDtoList
		when(customerService.getAllCustomers()).thenReturn(customerDtoList);

		try {
			// calling perform() to send GET request & test if content type of the response
			// is Application/JSON
			mockMvc.perform(get("/customers")).andExpect(content().contentType(MediaType.APPLICATION_JSON));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if no customers, response contains empty list
	@Test
	public void getListofCustomers_NoCustomers_ResponseContainsEmptyList() {

		// Create a list of customerDTO
		List<CustomerDTO> customerDtoList = new ArrayList<>();

		// when getAllCustomers is called then return customerDtoList
		when(customerService.getAllCustomers()).thenReturn(customerDtoList);

		try {
			mockMvc.perform(get("/customers")).andExpect(jsonPath("$..*").isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response List contains all customer IDs
	@Test
	public void getListofCustomers_ResponseListContainsValidCustomers() {

		// Create CustomerDTO objects
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		CustomerDTO customerDto2 = new CustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list & add all customerDTO objects
		List<CustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when getAllCustomers is called then return customerDtoList
		when(customerService.getAllCustomers()).thenReturn(customerDtoList);

		// calling perform() to send GET request & test if list has non empty customer
		// IDs
		try {
			// expecting that the customer ids in the list is not empty , 2nd object in the
			// list has "customer_name" MARY, Size of the list is 2
			mockMvc.perform(get("/customers")).andExpect(jsonPath("$.[*].customer_id").isNotEmpty())
					.andExpect(jsonPath("$[1].customer_name", is("Mary"))).andExpect(jsonPath("$", hasSize(2)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addNewCustomer_TestResponseStatusIsOk() {
		// Create Customer object
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		try {
			// Calling perform() to send POST request & test the response status
			mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer))
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addNewCustomerSuccess_TestTrueIsReturned() {

		// Create Customer object
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		when(customerService.addNewCustomer(any(Customer.class))).thenReturn(true);

		MvcResult resultActions;
		try {
			// Calling perform() to send POST request
			resultActions = mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customer)).accept(MediaType.APPLICATION_JSON)).andReturn();

			// converting the response to String
			String responseString = resultActions.getResponse().getContentAsString();

			// asserting that the response String received is "TRUE"
			assertThat(responseString, is("true"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(customerService, times(1)).addNewCustomer(any(Customer.class));
	}

	@Test
	public void addNewCustomer_BadRequest() {
		try {
			// calling mockMvc perform() to send POST request with NULL req body
			mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(asJsonString(null))
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addNewCustomer_NoRequestBody_UnsupportedMediaType() {
		try {
			// calling mockMvc perform() to send POST request without request body
			mockMvc.perform(post("/customers")).andExpect(status().isUnsupportedMediaType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response received has content type Application/JSON
	@Test
	public void getOneCustomer_TestResponseContentType() {

		// Create CustomerDTO object
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		// when getCustomerInformation is called then return customerDto1
		when(customerService.getCustomerInformation(anyInt())).thenReturn(customerDto1);

		try {
			// calling perform() to send GET request & test if content type of the response
			// is Application/JSON
			mockMvc.perform(get("/customers/{customer-id}", anyInt()))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response status is OK
	@Test
	public void getOneCustomer_TestResponseStatusIsOk() {

		// Create CustomerDTO object
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		// when getCustomerInformation is called then return customerDto1
		when(customerService.getCustomerInformation(anyInt())).thenReturn(customerDto1);

		try {
			// calling perform() to send GET request & test if response status is OK
			mockMvc.perform(get("/customers/{customer-id}", anyInt())).andExpect(status().isOk())
					.andExpect(status().is(200));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if getOneCustomer returns valid customer
	@Test
	public void getOneCustomer_TestValidCustomerReceived() {

		// Create CustomerDTO object
		CustomerDTO customerDto1 = new CustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		// when customerService's getCustomerInformation is called then return
		// customerDto1
		when(customerService.getCustomerInformation(anyInt())).thenReturn(customerDto1);

		// Using ObjectMapper to convert hotel to JSON String
		ObjectMapper mapper = new ObjectMapper();

		try {
			String customerInfoResult = mapper.writeValueAsString(customerDto1);

			// Calling perform() to send GET request
			MvcResult result = mockMvc.perform(get("/customers/{customer-id}", anyInt())).andReturn();

			String responseString = result.getResponse().getContentAsString();

			// asserting that customerInfoResult is same as responseString
			assertThat(customerInfoResult, is(responseString));

			System.out.println("CustomerInfo: " + customerInfoResult + " response: " + responseString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editCustomer_TestResponseStatusIsOk() {
		// Create Customer object
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		when(customerService.editACustomer(anyInt(), any())).thenReturn(true);

		try {
			// Calling perform() to send PUT request & test the response status
			mockMvc.perform(put("/customers/{customer-id}", 301).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customer))).andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editCustomerSuccess_TestTrueIsReturned() {
		// Create Customer object
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		when(customerService.editACustomer(anyInt(), any())).thenReturn(true);

		MvcResult resultActions;
		try {
			// Calling perform() to send PUT request
			resultActions = mockMvc.perform(put("/customers/{customer-id}", 301).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customer))).andReturn();

			// converting the response to String
			String responseString = resultActions.getResponse().getContentAsString();

			// asserting that the response String received is "TRUE"
			assertThat(responseString, is("true"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editCustomer_IsBadRequest() {
		try {
			// calling mockMvc perform() to send PUT request with NULL req body
			mockMvc.perform(put("/customers/{customer-id}", 301).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(null))).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editCustomer_NoRequestBody_UnsupportedMediaType() {
		try {
			// calling mockMvc perform() to send PUT request without request body
			mockMvc.perform(put("/customers/{customer-id}", 301)).andExpect(status().isUnsupportedMediaType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
