package com.entlogics.hotelbookingsystem_jpa.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;
import com.entlogics.hotelbookingsystem_jpa.service.HotelServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class HotelControllerTest {

	// mocking hotel service implementation
	@Mock
	HotelServiceImpl hotelService;

	// injecting mock to Hotel Controller
	@InjectMocks
	HotelController hotelController;

	@Captor
	ArgumentCaptor<Object> stringArgumentCaptor;

	// MockMvc instance
	private MockMvc mockMvc;

	// Setup before each test
	@Before
	public void setup() {
		// building mockMvc instance using MockMvcBuilders - standaloneSetup & injected
		// hotelController
		this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Testing if the response status for getListofHotels is 200 OK
	@Test
	public void getListofHotels_TestResponseStatusIs200Ok() {

		// Create 2 hoteldto objects
		HotelDTO hotel1 = new HotelDTO();
		hotel1.setHotel_id(101);
		hotel1.setHotel_name("Taj");
		hotel1.setHotel_location("Mumbai");
		hotel1.setHotel_phone("9876543210");
		hotel1.setHotel_rating(4);
		hotel1.setHotel_email("taj@gmail.com");
		hotel1.setPet_friendly(true);

		HotelDTO hotel2 = new HotelDTO();
		hotel2.setHotel_id(102);
		hotel2.setHotel_name("Taj Mahal");
		hotel2.setHotel_location("Mumbai");
		hotel2.setHotel_phone("9876543210");
		hotel2.setHotel_rating(4);
		hotel2.setHotel_email("tajmahal@gmail.com");
		hotel2.setPet_friendly(true);

		// Create a list of type hoteldto & add the 2 hoteldto object
		List<HotelDTO> hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);

		// when hotelService's getAllHotels is called then return hotelList
		when(hotelService.getAllHotels()).thenReturn(hotelList);

		try {
			// calling perform() to send GET request & test if status is 200 OK
			mockMvc.perform(get("/hotels")).andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response content type for getListofHotels is "Application/JSON"
	@Test
	public void getListofHotels_TestResponseContentType() {

		// Create 2 hoteldto objects
		HotelDTO hotel1 = new HotelDTO();
		hotel1.setHotel_id(101);
		hotel1.setHotel_name("Taj");
		hotel1.setHotel_location("Mumbai");
		hotel1.setHotel_phone("9876543210");
		hotel1.setHotel_rating(4);
		hotel1.setHotel_email("taj@gmail.com");
		hotel1.setPet_friendly(true);

		HotelDTO hotel2 = new HotelDTO();
		hotel2.setHotel_id(102);
		hotel2.setHotel_name("Taj Mahal");
		hotel2.setHotel_location("Mumbai");
		hotel2.setHotel_phone("9876543210");
		hotel2.setHotel_rating(4);
		hotel2.setHotel_email("tajmahal@gmail.com");
		hotel2.setPet_friendly(true);

		// Create a list of type hoteldto & add the 2 hoteldto object
		List<HotelDTO> hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);

		// when hotelService's getAllHotels is called then return hotelList
		when(hotelService.getAllHotels()).thenReturn(hotelList);

		try {
			// Calling perform() to send GET request & test if content type is
			// application/JSON
			mockMvc.perform(get("/hotels")).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response contains valid list
	@Test
	public void getListofHotels_ResponseListContainsAllHotelIds() {

		// Create 2 hoteldto objects
		HotelDTO hotel1 = new HotelDTO();
		hotel1.setHotel_id(101);
		hotel1.setHotel_name("Taj");
		hotel1.setHotel_location("Mumbai");
		hotel1.setHotel_phone("9876543210");
		hotel1.setHotel_rating(4);
		hotel1.setHotel_email("taj@gmail.com");
		hotel1.setPet_friendly(true);

		HotelDTO hotel2 = new HotelDTO();
		hotel2.setHotel_id(102);
		hotel2.setHotel_name("Taj Mahal");
		hotel2.setHotel_location("Mumbai");
		hotel2.setHotel_phone("9876543210");
		hotel2.setHotel_rating(4);
		hotel2.setHotel_email("tajmahal@gmail.com");
		hotel2.setPet_friendly(true);

		// Create a list of type hoteldto & add the 2 hoteldto object
		List<HotelDTO> hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);

		// when hotelService's getAllHotels is called then return hotelList
		when(hotelService.getAllHotels()).thenReturn(hotelList);

		try {
			// Calling perform() to send GET request & test if it contains non empty hotel
			// ids for all hotels in list
			mockMvc.perform(get("/hotels")).andExpect(jsonPath("$.[*].hotel_id").isNotEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response contains empty list
	@Test
	public void getListofHotels_NoHotels_ResponseContainsEmptyList() {
		// Create a list of type hoteldto
		List<HotelDTO> hotelList = new ArrayList<>();

		// when hotelService's getAllHotels is called then return hotelList
		when(hotelService.getAllHotels()).thenReturn(hotelList);

		try {
			// Calling perform() to send GET request & test if all members of JSON structure
			// is empty
			mockMvc.perform(get("/hotels")).andExpect(jsonPath("$..*").isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if hotel is added, response status is OK
	@Test
	public void addNewHotel_TestResponseStatusIs200Ok() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		when(hotelService.addNewHotel(any(Hotel.class))).thenReturn(true);

		try {
			// Calling perform() to send POST request & test the response status
			mockMvc.perform(post("/hotels").contentType(MediaType.APPLICATION_JSON).content(asJsonString(hotel))
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(status().is(200));

			/*
			 * mockMvc.perform(post("/hotels").contentType(MediaType.APPLICATION_JSON).
			 * content(asJsonString(hotel))
			 * .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.*").isNotEmpty());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

//		verify(hotelService, times(1)).addNewHotel(hotel);

		// Then - Define argument captor on specific method call
//		then(hotelService).should().addNewHotel((Hotel) stringArgumentCaptor.capture());

		// Capture the argument
//		assertThat(stringArgumentCaptor.getValue(), is(hotel));
	}

	@Test
	public void addNewHotelSuccess_TestTrueIsReturned() throws Exception {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

//		System.out.println("JSON STRING: " + asJsonString(hotel));

		when(hotelService.addNewHotel(any(Hotel.class))).thenReturn(true);

		// Calling perform() to send POST request
		MvcResult resultActions = mockMvc.perform(post("/hotels").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotel)).accept(MediaType.APPLICATION_JSON)).andReturn();

		verify(hotelService, times(1)).addNewHotel(any(Hotel.class));

		// converting the response to String
		String responseString = resultActions.getResponse().getContentAsString();

		// asserting that the response String received is "TRUE"
		assertThat(responseString, is("true"));
	}

	@Test
	public void addNewHotel_NoRequestBody_UnsupportedMediaType() {
		try {
			// calling mockMvc perform() to send POST request without request body
			mockMvc.perform(post("/hotels")).andExpect(status().isUnsupportedMediaType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addNewHotel_BadRequest() {
		try {
			// calling mockMvc perform() to send POST request with NULL req body
			mockMvc.perform(post("/hotels").contentType(MediaType.APPLICATION_JSON).content(asJsonString(null))
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response content type for getOneHotel is "Application/JSON"
	@Test
	public void getOneHotel_TestResponseContentType() {

		// Create HotelInformationDTO object
		HotelInformationDTO hotel = new HotelInformationDTO();
		hotel.setHotel_id(101);
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// when hotelService's getHotelInformation is called then return hotel
		when(hotelService.getHotelInformation(anyInt())).thenReturn(hotel);

		try {
			// Calling perform() to send GET request & test if content type is
			// application/JSON
			mockMvc.perform(get("/hotels/101")).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response status is 200/OK for getOneHotel
	@Test
	public void getOneHotel_TestResponseStatusIs200Ok() {

		// Create HotelInformationDTO object
		HotelInformationDTO hotel = new HotelInformationDTO();
		hotel.setHotel_id(101);
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// when hotelService's getHotelInformation is called then return hotel
		when(hotelService.getHotelInformation(anyInt())).thenReturn(hotel);

		try {
			// Calling perform() to send GET request & test if response status is 200/OK
			mockMvc.perform(get("/hotels/101")).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getOneHotel_InvalidHotel_TestResponseContainsEmptyObject() {

		// Create empty HotelInformationDTO object
		HotelInformationDTO hotel = new HotelInformationDTO();

		// when hotelService's getHotelInformation is called then return hotel
		when(hotelService.getHotelInformation(anyInt())).thenReturn(hotel);

		// Using ObjectMapper to convert hotel to JSON String
		ObjectMapper mapper = new ObjectMapper();

		try {
			String hotelResult = mapper.writeValueAsString(hotel);

			// Calling perform() to send GET request
			MvcResult result = mockMvc.perform(get("/hotels/101"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

			String responseString = result.getResponse().getContentAsString();

			// asserting that hotelResult is same as responseString
			assertThat(hotelResult, is(responseString));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if getOneHotel returns valid hotel
	@Test
	public void getOneHotel_TestResponseContainsValidHotel() {

		// Create empty HotelInformationDTO object
		HotelInformationDTO hotel = new HotelInformationDTO();
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// when hotelService's getHotelInformation is called then return hotel
		when(hotelService.getHotelInformation(anyInt())).thenReturn(hotel);

		// Using ObjectMapper to convert hotel to JSON String
		ObjectMapper mapper = new ObjectMapper();

		try {
			String hotelResult = mapper.writeValueAsString(hotel);

			// Calling perform() to send GET request
			MvcResult result = mockMvc.perform(get("/hotels/101")).andReturn();

			String responseString = result.getResponse().getContentAsString();

			// asserting that hotelResult is same as responseString
			assertThat(hotelResult, is(responseString));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if hotel is updated, response status is OK
	@Test
	public void editNewHotel_TestResponseStatusIs200Ok() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		when(hotelService.editAHotel(anyInt(), any())).thenReturn(true);

		try {
			// Calling perform() to send PUT request & test the response status
			mockMvc.perform(
					put("/hotels/{hotel-id}", 101).contentType(MediaType.APPLICATION_JSON).content(asJsonString(hotel)))
					.andExpect(status().isOk()).andExpect(status().is(200));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editNewHotelSuccess_TestTrueIsReturned() throws Exception {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		when(hotelService.editAHotel(anyInt(), any(Hotel.class))).thenReturn(true);

		// Calling perform() to send PUT request
		MvcResult resultActions = mockMvc.perform(put("/hotels/{hotel-id}", 101).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotel)).accept(MediaType.APPLICATION_JSON)).andReturn();

		verify(hotelService, times(1)).editAHotel(anyInt(), any(Hotel.class));

		// converting the response to String
		String responseString = resultActions.getResponse().getContentAsString();

		// asserting that the response String received is "TRUE"
		assertThat(responseString, is("true"));
	}

	@Test
	public void editNewHotel_TestBadRequest() {
		try {
			// calling mockMvc perform() to send PUT request with NULL req body
			mockMvc.perform(put("/hotels/{hotel-id}", 101).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(null)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void editNewHotel_NoRequestBody_UnsupportedMediaType() {
		try {
			// calling mockMvc perform() to send PUT request without request body
			mockMvc.perform(put("/hotels/{hotel-id}", 101)).andExpect(status().isUnsupportedMediaType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getOneCustomerOfOneHotel_TestResponseContentType() {

		// Create CustomerDTO object
		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
		// setting the customer properties
		customerInfoDTO.setCustomer_name("John");
		customerInfoDTO.setCustomer_address("Delhi");
		customerInfoDTO.setCustomer_phone("9876543810");
		customerInfoDTO.setCustomer_email("john@gmail.com");
		customerInfoDTO.setPreferences("King Size Bed");
		customerInfoDTO.setSpecial_needs(null);

		when(hotelService.getCustomerInformation(anyInt(), anyInt())).thenReturn(customerInfoDTO);

		try {
			mockMvc.perform(get("/hotels/{hotel_id}/customers/{customer_id}", anyInt(), anyInt()))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getOneCustomerOfOneHotel_TestResponseStatus() {

		// Create CustomerDTO object
		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
		// setting the customer properties
		customerInfoDTO.setCustomer_name("John");
		customerInfoDTO.setCustomer_address("Delhi");
		customerInfoDTO.setCustomer_phone("9876543810");
		customerInfoDTO.setCustomer_email("john@gmail.com");
		customerInfoDTO.setPreferences("King Size Bed");
		customerInfoDTO.setSpecial_needs(null);

		when(hotelService.getCustomerInformation(anyInt(), anyInt())).thenReturn(customerInfoDTO);

		try {
			mockMvc.perform(get("/hotels/{hotel_id}/customers/{customer_id}", anyInt(), anyInt()))
					.andExpect(status().isOk()).andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if getOneCustomerOfOneHotel returns valid customer
	@Test
	public void getOneCustomerOfOneHotel_CustomerObjectHasValidCustomerName() {

		// Create CustomerDTO object
		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
		// setting the customer properties
		customerInfoDTO.setCustomer_id(301);
		customerInfoDTO.setCustomer_name("John");
		customerInfoDTO.setCustomer_address("Delhi");
		customerInfoDTO.setCustomer_phone("9876543810");
		customerInfoDTO.setCustomer_email("john@gmail.com");
		customerInfoDTO.setPreferences("King Size Bed");
		customerInfoDTO.setSpecial_needs(null);

		// when hotelService's getCustomerInformation is called then return
		// customerInfoDTO
		when(hotelService.getCustomerInformation(anyInt(), anyInt())).thenReturn(customerInfoDTO);

		try {
			// Calling perform() to send GET request
			mockMvc.perform(get("/hotels/{hotel_id}/customers/{customer_id}", anyInt(), anyInt()))
					.andExpect(jsonPath("$", notNullValue())).andExpect(jsonPath("$.customer_name", is("John")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if getOneCustomerOfOneHotel returns valid customer
	@Test
	public void getOneCustomerOfOneHotel_TestValidCustomerReceived() {

		// Create CustomerDTO object
		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
		// setting the customer properties
		customerInfoDTO.setCustomer_id(301);
		customerInfoDTO.setCustomer_name("John");
		customerInfoDTO.setCustomer_address("Delhi");
		customerInfoDTO.setCustomer_phone("9876543810");
		customerInfoDTO.setCustomer_email("john@gmail.com");
		customerInfoDTO.setPreferences("King Size Bed");
		customerInfoDTO.setSpecial_needs(null);

		// when hotelService's getCustomerInformation is called then return
		// customerInfoDTO
		when(hotelService.getCustomerInformation(anyInt(), anyInt())).thenReturn(customerInfoDTO);

		// Using ObjectMapper to convert customerInfoDTO to JSON String
		ObjectMapper mapper = new ObjectMapper();

		try {
			String customerInfoResult = mapper.writeValueAsString(customerInfoDTO);

			// Calling perform() to send GET request
			MvcResult result = mockMvc.perform(get("/hotels/{hotel_id}/customers/{customer_id}", anyInt(), anyInt()))
					.andReturn();

			String responseString = result.getResponse().getContentAsString();

			// asserting that customerInfoResult is same as responseString
			assertThat(customerInfoResult, is(responseString));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if the response status for getCustomersOfOneHotel is 200 OK
	@Test
	public void getCustomersOfOneHotel_TestResponseStatusIsOk() {

		// Create 2 CustomerDTO objects
		HotelCustomerDTO customerDto1 = new HotelCustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		HotelCustomerDTO customerDto2 = new HotelCustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list of type HotelCustomerDTO & add the 2 HotelCustomerDTO object
		List<HotelCustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when hotelService's getCustomersOfAHotel is called then return
		// customerDtoList
		when(hotelService.getCustomersOfAHotel(anyInt())).thenReturn(customerDtoList);

		try {
			// calling perform() to send GET request & test if status is 200 OK
			mockMvc.perform(get("/hotels/{hotel_id}/customers", anyInt())).andExpect(status().isOk())
					.andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if the Content Type for getCustomersOfOneHotel is application/json
	@Test
	public void getCustomersOfOneHotel_TestResponseContentType() {

		// Create 2 CustomerDTO objects
		HotelCustomerDTO customerDto1 = new HotelCustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		HotelCustomerDTO customerDto2 = new HotelCustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list of type HotelCustomerDTO & add the 2 HotelCustomerDTO object
		List<HotelCustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when hotelService's getCustomersOfAHotel is called then return
		// customerDtoList
		when(hotelService.getCustomersOfAHotel(anyInt())).thenReturn(customerDtoList);

		try {
			// calling perform() to send GET request & test if content type is
			// Application_JSON
			mockMvc.perform(get("/hotels/{hotel_id}/customers", anyInt()))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if response contains empty list
	@Test
	public void getCustomersOfOneHotel_NoCustomers_ResponseContainsEmptyList() {

		// Create a list of type HotelCustomerDTO
		List<HotelCustomerDTO> customerDtoList = new ArrayList<>();

		// when hotelService's getCustomersOfAHotel is called then return
		// customerDtoList
		when(hotelService.getCustomersOfAHotel(anyInt())).thenReturn(customerDtoList);

		try {
			// Calling perform() to send GET request & test if all members of JSON structure
			// is empty
			mockMvc.perform(get("/hotels/{hotel_id}/customers", anyInt())).andExpect(jsonPath("$..*").isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if getCustomersOfOneHotel's response contains valid list
	@Test
	public void getCustomersOfOneHotel_ResponseContainsValidList() {

		// Create 2 CustomerDTO objects
		HotelCustomerDTO customerDto1 = new HotelCustomerDTO();
		customerDto1.setCustomer_id(301);
		customerDto1.setCustomer_name("John");
		customerDto1.setCustomer_address("Delhi");
		customerDto1.setCustomer_phone("9876543810");
		customerDto1.setCustomer_email("john@gmail.com");
		customerDto1.setPreferences("King Size Bed");
		customerDto1.setSpecial_needs(null);

		HotelCustomerDTO customerDto2 = new HotelCustomerDTO();
		customerDto2.setCustomer_id(102);
		customerDto2.setCustomer_name("Mary");
		customerDto2.setCustomer_address("Mumbai");
		customerDto2.setCustomer_phone("9876543210");
		customerDto2.setCustomer_email("mary@gmail.com");
		customerDto2.setPreferences("Sea View Room");
		customerDto2.setSpecial_needs("Early check in");

		// Create a list of type HotelCustomerDTO & add the 2 HotelCustomerDTO object
		List<HotelCustomerDTO> customerDtoList = new ArrayList<>();
		customerDtoList.add(customerDto1);
		customerDtoList.add(customerDto2);

		// when hotelService's getCustomersOfAHotel is called then return
		// customerDtoList
		when(hotelService.getCustomersOfAHotel(anyInt())).thenReturn(customerDtoList);

		// Using ObjectMapper to convert hotel to JSON String
		ObjectMapper mapper = new ObjectMapper();

		try {

			String customerResult = mapper.writeValueAsString(customerDtoList);

			// Calling perform() to send GET request
			MvcResult result = mockMvc.perform(get("/hotels/{hotel_id}/customers", anyInt())).andReturn();

			String responseString = result.getResponse().getContentAsString();

			// asserting that hotelResult is same as responseString
			assertThat(customerResult, is(responseString));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if hotel is deleted, response status is OK
	@Test
	public void deleteOneHotel_TestResponseStatusIs200Ok() {
		try {
			// Calling perform() to send DELETE request & test the response status
			mockMvc.perform(delete("/hotels/{hotel-id}", 101)).andExpect(status().isOk()).andExpect(status().is(200));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Testing if hotel is deleted, response is "TRUE"
	@Test
	public void deleteOneHotel_TestTrueIsReturned() {

		when(hotelService.deleteAHotel(anyInt())).thenReturn(true);

		try {
			// Calling perform() to send DELETE request
			MvcResult resultActions = mockMvc.perform(delete("/hotels/{hotel-id}", 101)).andReturn();

			// converting the response to String
			String responseString = resultActions.getResponse().getContentAsString();

			// asserting that the response String received is "TRUE"
			assertThat(responseString, is("true"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
