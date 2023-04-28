package com.entlogics.hotelbookingsystem_jpa.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.entlogics.hotelbookingsystem_jpa.dao.CustomerDAOImpl;
import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Bill;
import com.entlogics.hotelbookingsystem_jpa.entity.Booking;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel_Customer;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

	@Mock
	CustomerDAOImpl customerDAO;

	@InjectMocks
	CustomerServiceImpl customerService;

	// test if customer list size is 3
	@Test
	public void getAllCustomers_TestSizeOfListIsThree() {

		// Create 3 customer DTO objects & set properties
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setCustomer_id(301);
		customer1.setCustomer_name("John");
		customer1.setCustomer_address("Delhi");
		customer1.setCustomer_phone("9876543810");
		customer1.setCustomer_email("john@gmail.com");
		customer1.setPreferences("King Size Bed");
		customer1.setSpecial_needs(null);

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setCustomer_id(302);
		customer2.setCustomer_name("Mary");
		customer2.setCustomer_address("Bangalore");
		customer2.setCustomer_phone("9876543212");
		customer2.setCustomer_email("mary@gmail.com");
		customer2.setPreferences(null);
		customer2.setSpecial_needs("wheelchair");

		CustomerDTO customer3 = new CustomerDTO();
		customer3.setCustomer_id(303);
		customer3.setCustomer_name("Emily");
		customer3.setCustomer_address("Nainital");
		customer3.setCustomer_phone("9876543540");
		customer3.setCustomer_email("emily@gmail.com");
		customer3.setPreferences("King Size Bed");
		customer3.setSpecial_needs(null);

		// Create a list of type CustomerDTO & add customerDTO objects
		List<CustomerDTO> customerList = new ArrayList<>();
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);

		// when getAllCustomers() is called then return customerList
		when(customerDAO.getAllCustomers()).thenReturn(customerList);

		// getting list of customers by calling customer service method
		List<CustomerDTO> listCustomer = customerService.getAllCustomers();

		// asserting that size of customer list is 3
		assertThat(listCustomer.size(), is(3));
	}

	// test if no customers are present then list is empty
	@Test
	public void getAllCustomers_NoCustomers_ReturnEmptyList() {

		// calling getAllCustomers() of customer service
		List<CustomerDTO> customerList = customerService.getAllCustomers();

		// asserting that customer list is empty
		assertThat(customerList.isEmpty(), is(true));
	}

	// test if a particular customer obj is present in list
	@Test
	public void getAllCustomers_TestIfAParticularCustomerIsPresentInList() {

		// Create 2 customer DTO objects & set properties
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setCustomer_id(301);
		customer1.setCustomer_name("John");
		customer1.setCustomer_address("Delhi");
		customer1.setCustomer_phone("9876543810");
		customer1.setCustomer_email("john@gmail.com");
		customer1.setPreferences("King Size Bed");
		customer1.setSpecial_needs(null);

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setCustomer_id(302);
		customer2.setCustomer_name("Mary");
		customer2.setCustomer_address("Bangalore");
		customer2.setCustomer_phone("9876543212");
		customer2.setCustomer_email("mary@gmail.com");
		customer2.setPreferences(null);
		customer2.setSpecial_needs("wheelchair");

		// Create a list of type CustomerDTO & add customerDTO objects
		List<CustomerDTO> customerList = new ArrayList<>();
		customerList.add(customer1);
		customerList.add(customer2);

		// when getAllCustomers() is called then return customerList
		when(customerDAO.getAllCustomers()).thenReturn(customerList);

		// getting list of customers by calling customer service method
		List<CustomerDTO> listCustomer = customerService.getAllCustomers();

		// asserting that customer list contains "customer2" object
		assertThat(listCustomer.contains(customer2), is(true));
	}

	// test if customer is added then it returns boolean TRUE
	@Test
	public void addNewCustomer_CustomerIsAddedSuccessfully_ReturnTrue() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// when addNewCustomer() of customerDAO is called then return "true"
		when(customerDAO.addNewCustomer(any())).thenReturn(true);

		// assert that addNewCustomer() of customerService returns true
		assertThat(customerService.addNewCustomer(customer), is(true));
	}

	// test if customer is not added then it returns boolean FALSE
	@Test
	public void addNewCustomer_CustomerIsNotAdded_ReturnFalse() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// when addNewCustomer() of customerDAO is called then return "false"
		when(customerDAO.addNewCustomer(any())).thenReturn(false);

		// assert that addNewCustomer() of customerService returns false
		assertThat(customerService.addNewCustomer(customer), is(false));
	}

	// test if customer is edited successfully then it returns boolean TRUE
	@Test
	public void editACustomer_CustomerIsUpdatedSuccessfully_ReturnTrue() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("Johnson");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// when editACustomer is called then return "true"
		when(customerDAO.editACustomer(anyInt(), any())).thenReturn(true);

		// assert that editACustomer() of customerService returns true
		assertThat(customerService.editACustomer(301, customer), is(true));
	}

	// test if customer is not edited successfully then it returns boolean FALSE
	@Test
	public void editACustomer_CustomerIsNotUpdated_ReturnFalse() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("Johnson");

		// when editACustomer is called then return "false"
		when(customerDAO.editACustomer(anyInt(), any())).thenReturn(false);

		// assert that editACustomer() of customerService returns false
		assertThat(customerService.editACustomer(301, customer), is(false));
	}

	@Test
	public void getCustomerInformation_CustomerIdIsCorrect_TestCustomerName() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// Create bill objects
		Bill bill1 = new Bill();
		bill1.setBill_id(701);
		Bill bill2 = new Bill();
		bill2.setBill_id(702);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);

		// set bills of the bookings of the customer
		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(101);

		// create hotel_customer objects
		Hotel_Customer hotelCustomer = new Hotel_Customer();
		hotelCustomer.setHotel(hotel);
		hotelCustomer.setCustomer(customer);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		// set customer's bookings
		customer.setBookings(bookingList);

		// create a list of hotel customer object
		List<Hotel_Customer> hotelCustomerList = new ArrayList<>();
		hotelCustomerList.add(hotelCustomer);

		// set customer's hotels
		customer.setHotels(hotelCustomerList);

		// when customer dao's getCustomerInformation is called then it should return
		// customer
		when(customerDAO.getCustomerInformation(anyInt())).thenReturn(customer);

		// call customerService's getCustomerInformation
		CustomerDTO customerDTO = customerService.getCustomerInformation(301);

		// assert that customer object received has customer name "John"
		assertThat("John", is(customerDTO.getCustomer_name()));
	}

	// test if invalid customer id is passed, returns NullPointerException
	@Test(expected = NullPointerException.class)
	public void getCustomerInformation_CustomerIdIsInvalid_ReturnNullPointerException() {

		customerService.getCustomerInformation(310);
	}

	// test customer has valid list of booking ids
	@Test
	public void getCustomerInformation_TestIfValidBookingListIsPresent() {
		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// Create bill objects
		Bill bill1 = new Bill();
		bill1.setBill_id(701);
		Bill bill2 = new Bill();
		bill2.setBill_id(702);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);

		// set bills of the bookings of the customer
		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(101);

		// create hotel_customer objects
		Hotel_Customer hotelCustomer = new Hotel_Customer();
		hotelCustomer.setHotel(hotel);
		hotelCustomer.setCustomer(customer);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		// set customer's bookings
		customer.setBookings(bookingList);

		// create a list of hotel customer object
		List<Hotel_Customer> hotelCustomerList = new ArrayList<>();
		hotelCustomerList.add(hotelCustomer);

		// set customer's hotels
		customer.setHotels(hotelCustomerList);

		// Create a list of type Integer to store booking ids
		List<Integer> bookingIdList = Arrays.asList(501, 502);

		// when customer dao's getCustomerInformation is called then it should return
		// customer
		when(customerDAO.getCustomerInformation(anyInt())).thenReturn(customer);

		// call customerService's getCustomerInformation
		CustomerDTO customerDTO = customerService.getCustomerInformation(301);

		// assert that bookings of customer DTO is same as bookingIdList
		assertThat(customerDTO.getBookings(), is(bookingIdList));
	}

	// test customer has valid list of bill ids
	@Test
	public void getCustomerInformation_TestIfValidBillListIsPresent() {
		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// Create bill objects
		Bill bill1 = new Bill();
		bill1.setBill_id(701);
		Bill bill2 = new Bill();
		bill2.setBill_id(702);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);

		// set bills of the bookings of the customer
		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(101);

		// create hotel_customer objects
		Hotel_Customer hotelCustomer = new Hotel_Customer();
		hotelCustomer.setHotel(hotel);
		hotelCustomer.setCustomer(customer);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		// set customer's bookings
		customer.setBookings(bookingList);

		// create a list of hotel customer object
		List<Hotel_Customer> hotelCustomerList = new ArrayList<>();
		hotelCustomerList.add(hotelCustomer);

		// set customer's hotels
		customer.setHotels(hotelCustomerList);

		// Create a list of type Integer to store bill ids
		List<Integer> billIdList = Arrays.asList(701, 702);

		// when customer dao's getCustomerInformation is called then it should return
		// customer
		when(customerDAO.getCustomerInformation(anyInt())).thenReturn(customer);

		// call customerService's getCustomerInformation
		CustomerDTO customerDTO = customerService.getCustomerInformation(301);

		// assert that bills of customer DTO is same as billIdList
		assertThat(customerDTO.getBills(), is(billIdList));
	}

	// test customer has valid list of hotel ids
	@Test
	public void getCustomerInformation_TestIfValidHotelListIsPresent() {
		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_name("John");
		customer.setCustomer_address("Delhi");
		customer.setCustomer_phone("9876543810");
		customer.setCustomer_email("john@gmail.com");
		customer.setPreferences("King Size Bed");
		customer.setSpecial_needs(null);

		// Create bill objects
		Bill bill1 = new Bill();
		bill1.setBill_id(701);
		Bill bill2 = new Bill();
		bill2.setBill_id(702);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);

		// set bills of the bookings of the customer
		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(101);

		// create hotel_customer objects
		Hotel_Customer hotelCustomer = new Hotel_Customer();
		hotelCustomer.setHotel(hotel);
		hotelCustomer.setCustomer(customer);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		// set customer's bookings
		customer.setBookings(bookingList);

		// create a list of hotel customer object
		List<Hotel_Customer> hotelCustomerList = new ArrayList<>();
		hotelCustomerList.add(hotelCustomer);

		// set customer's hotels
		customer.setHotels(hotelCustomerList);

		// Create a list of type Integer to store hotel ids
		List<Integer> hotelIdList = Arrays.asList(101);

		// when customer dao's getCustomerInformation is called then it should return
		// customer
		when(customerDAO.getCustomerInformation(anyInt())).thenReturn(customer);

		// call customerService's getCustomerInformation
		CustomerDTO customerDTO = customerService.getCustomerInformation(301);

		// assert that hotels of customer DTO is same as hotelIdList
		assertThat(customerDTO.getHotels(), is(hotelIdList));
	}
}
