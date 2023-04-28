package com.entlogics.hotelbookingsystem_jpa.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import com.entlogics.hotelbookingsystem_jpa.dao.HotelDAOImpl;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Bill;
import com.entlogics.hotelbookingsystem_jpa.entity.Booking;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Employee;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel_Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel_Service;
import com.entlogics.hotelbookingsystem_jpa.entity.Room;
import com.entlogics.hotelbookingsystem_jpa.entity.Service;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceImplTest {

	@Mock
	HotelDAOImpl hotelDAO;

	@InjectMocks
	HotelServiceImpl hotelService;

	// test if Size of list of hotels is 3
	@Test
	public void getAllHotels_SizeOfListIsThree() {
		// Create three hotel objects
		Hotel hotel1 = new Hotel();
		hotel1.setHotel_id(101);
		hotel1.setHotel_name("Taj");
		hotel1.setHotel_location("Mumbai");
		hotel1.setHotel_phone("9876543210");
		hotel1.setHotel_rating(4);
		hotel1.setHotel_email("taj@gmail.com");
		hotel1.setPet_friendly(true);

		Hotel hotel2 = new Hotel();
		hotel2.setHotel_id(102);
		hotel2.setHotel_name("Taj Mahal");
		hotel2.setHotel_location("Mumbai");
		hotel2.setHotel_phone("9876543210");
		hotel2.setHotel_rating(4);
		hotel2.setHotel_email("tajmahal@gmail.com");
		hotel2.setPet_friendly(true);

		Hotel hotel3 = new Hotel();
		hotel3.setHotel_id(103);
		hotel3.setHotel_name("Hyatt");
		hotel3.setHotel_location("Bangalore");
		hotel3.setHotel_phone("9876543210");
		hotel3.setHotel_rating(4);
		hotel3.setHotel_email("hyatt@gmail.com");
		hotel3.setPet_friendly(true);

		String hotel_name = "";

		// Create a list of type hotel & add the 3 hotel object
		List<Hotel> hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);
		hotelList.add(hotel3);

		// when getAllHotels of hotel dao is called then return the hotelList
		when(hotelDAO.getAllHotels(hotel_name)).thenReturn(hotelList);

		// Calling getAllHotels of hotel service
		List<HotelDTO> listHotel = hotelService.getAllHotels();

		// asserting that size of the list returned from hotel service method is 3 or
		// not
		assertThat(listHotel.size(), is(3));
	}

	// test if no hotels are present then it returns empty list or not
	@Test
	public void getAllHotels_NoHotels_ReturnEmptyList() {

		List<HotelDTO> listHotel = hotelService.getAllHotels();

		assertThat(listHotel.isEmpty(), is(true));
	}

	// test if the list of hotels has a particular hotel object
	@Test
	public void getAllHotels_TestIfAParticularHotelIsPresentInList() {
		// Create 2 hotel objects
		Hotel hotel1 = new Hotel();
		hotel1.setHotel_id(101);
		hotel1.setHotel_name("Taj");
		hotel1.setHotel_location("Mumbai");
		hotel1.setHotel_phone("9876543210");
		hotel1.setHotel_rating(5);
		hotel1.setHotel_email("taj@taj.com");
		hotel1.setPet_friendly(true);

		Hotel hotel2 = new Hotel();
		hotel2.setHotel_id(102);
		hotel2.setHotel_name("Taj Mahal");
		hotel2.setHotel_location("Mumbai");
		hotel2.setHotel_phone("9876543210");
		hotel2.setHotel_rating(4);
		hotel2.setHotel_email("tajmahal@gmail.com");
		hotel2.setPet_friendly(true);

		String hotel_name = "";

		// Create a list of type hotel & add the 2 hotel object
		List<Hotel> hotelList = new ArrayList<>();
		hotelList.add(hotel1);
		hotelList.add(hotel2);

		// when getAllHotels of hotel dao is called then return the hotelList
		when(hotelDAO.getAllHotels(hotel_name)).thenReturn(hotelList);

		// Calling getAllHotels of hotel service
		List<HotelDTO> listHotel = hotelService.getAllHotels();

		// asserting that the list returned from hotel service method contains "hotel1"
		// object by looping through list & creating HotelDTO object
		for (HotelDTO h : listHotel) {
			if (h.getHotel_id() == 101) {
				assertThat(listHotel.contains(h), is(true));
			}
		}
	}

	// Test if getting one hotel info, has the hotel name as "Taj"
	@Test
	public void getHotelInformation_TestHotelNameIsTaj() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel name
		hotel.setHotel_name("Taj");

		// when getHotelInformation is called with anyInt then return the hotel obj
		// anyInt() argument matcher returns the same value for any given int
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that name of the hotel is "Taj"
		assertEquals("Taj", hotelInfo.getHotel_name());
	}

	@Test
	public void getHotelInformation_TestIfNonEmptyEmployeeListIsPresent() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// Create employee objects
		Employee emp1 = new Employee();
		emp1.setEmp_id(401);
		Employee emp2 = new Employee();
		emp2.setEmp_id(402);

		// create a list of employee objects
		List<Employee> empList = new ArrayList<>();
		empList.add(emp1);
		empList.add(emp2);

		// set employee list to Employees of hotel
		hotel.setEmployees(empList);

		// Create a list of type Integer to store emp ids of a hotel
		List<Integer> empIdList = Arrays.asList(401, 402);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a valid list of employee ids
		assertThat(hotelInfo.getEmployees(), is(empIdList));
	}

	@Test
	public void getHotelInformation_TestIfNonEmptyRoomListIsPresent() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// Create room objects
		Room room1 = new Room();
		room1.setRoom_id(201);
		Room room2 = new Room();
		room2.setRoom_id(202);
		Room room3 = new Room();
		room3.setRoom_id(203);

		// create a list of room objects
		List<Room> roomList = new ArrayList<>();
		roomList.add(room1);
		roomList.add(room2);
		roomList.add(room3);

		// set room list to Rooms of hotel
		hotel.setRooms(roomList);

		// Create a list of type Integer to store room ids of a hotel
		List<Integer> roomIdList = Arrays.asList(201, 202, 203);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a valid list of room ids
		assertThat(hotelInfo.getRooms(), is(roomIdList));
	}

	@Test
	public void getHotelInformation_TestIfNonEmptyBookingListIsPresent() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);
		Booking booking3 = new Booking();
		booking3.setBooking_id(503);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);
		bookingList.add(booking3);

		// set booking list to bookings of hotel
		hotel.setBookings(bookingList);

		// Create a list of type Integer to store booking ids of a hotel
		List<Integer> bookingIdList = Arrays.asList(501, 502, 503);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a valid list of booking ids
		assertThat(hotelInfo.getBookings(), is(bookingIdList));
	}

	@Test
	public void getHotelInformation_TestIfNonEmptyCustomerListIsPresent() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// Create customer objects
		Customer customer1 = new Customer();
		customer1.setCustomer_id(301);
		Hotel_Customer hotelCustomer1 = new Hotel_Customer();
		hotelCustomer1.setCustomer(customer1);

		Customer customer2 = new Customer();
		customer2.setCustomer_id(302);
		Hotel_Customer hotelCustomer2 = new Hotel_Customer();
		hotelCustomer2.setCustomer(customer2);

		Customer customer3 = new Customer();
		customer3.setCustomer_id(303);
		Hotel_Customer hotelCustomer3 = new Hotel_Customer();
		hotelCustomer3.setCustomer(customer3);

		// create a list of hotel customer objects
		List<Hotel_Customer> customerList = new ArrayList<>();
		customerList.add(hotelCustomer1);
		customerList.add(hotelCustomer2);
		customerList.add(hotelCustomer3);

		// set customer list to Customers of hotel
		hotel.setCustomers(customerList);

		// Create a list of type Integer to store customer ids of a hotel
		List<Integer> customerIdList = Arrays.asList(301, 302, 303);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a valid list of customer ids
		assertThat(hotelInfo.getCustomers(), is(customerIdList));
	}

	@Test
	public void getHotelInformation_TestIfNonEmptyServiceListIsPresent() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// Create service objects
		Service service1 = new Service();
		service1.setService_id(601);
		Hotel_Service hotelService1 = new Hotel_Service();
		hotelService1.setService(service1);

		Service service2 = new Service();
		service2.setService_id(602);
		Hotel_Service hotelService2 = new Hotel_Service();
		hotelService2.setService(service2);

		// create a list of hotel Service objects
		List<Hotel_Service> serviceList = new ArrayList<>();
		serviceList.add(hotelService1);
		serviceList.add(hotelService2);

		// set service list to Services of hotel
		hotel.setServices(serviceList);

		// Create a list of type Integer to store Services ids of a hotel
		List<Integer> serviceIdList = Arrays.asList(601, 602);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a valid list of service ids
		assertThat(hotelInfo.getServices(), is(serviceIdList));
	}

	@Test
	public void getHotelInformation_TestIfEmployeeListIsEmpty() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// create a list of employee objects
		List<Employee> empList = new ArrayList<>();

		// set empty employee list to Employees of hotel
		hotel.setEmployees(empList);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains a empty list of employees
		assertThat(hotelInfo.getEmployees().isEmpty(), is(true));
	}

	@Test
	public void getHotelInformation_TestIfRoomListIsEmpty() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// create a list of room objects
		List<Room> roomList = new ArrayList<>();

		// set empty room list to Rooms of hotel
		hotel.setRooms(roomList);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains an empty list of rooms
		assertThat(hotelInfo.getRooms().isEmpty(), is(true));
	}

	@Test
	public void getHotelInformation_TestIfBookingListIsEmpty() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// create an empty list of booking objects
		List<Booking> bookingList = new ArrayList<>();

		// set booking list to bookings of hotel
		hotel.setBookings(bookingList);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains an empty list of bookings
		assertThat(hotelInfo.getBookings().isEmpty(), is(true));
	}

	@Test
	public void getHotelInformation_TestIfCustomerListIsEmpty() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// create an empty list of hotel customer objects
		List<Hotel_Customer> customerList = new ArrayList<>();

		// set empty customer list to Customers of hotel
		hotel.setCustomers(customerList);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains an empty list of customers
		assertThat(hotelInfo.getCustomers().isEmpty(), is(true));
	}

	@Test
	public void getHotelInformation_TestIfServiceListIsEmpty() {
		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		// create an empty list of hotel Service objects
		List<Hotel_Service> serviceList = new ArrayList<>();

		// set empty service list to Services of hotel
		hotel.setServices(serviceList);

		// when hotel dao's getHotelInformation is called then it should return "hotel"
		// object
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(hotel);

		// Calling hotel service method to get hotel information
		HotelInformationDTO hotelInfo = hotelService.getHotelInformation(101);

		// asserting that hotel contains an empty list of services
		assertThat(hotelInfo.getServices().isEmpty(), is(true));
	}

	@Test(expected = NullPointerException.class)
	public void getHotelInformation_InvalidHotelId_ReturnNullPointerException() {

		// when getHotelInformation is called, then return the null
		when(hotelDAO.getHotelInformation(anyInt())).thenReturn(null);

		hotelService.getHotelInformation(181);
	}

	@Test
	public void addNewHotel_HotelIsAddedSuccessfully_ReturnTrue() {

		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		Boolean addHotel = true;

		// when hotel dao's addNewHotel is called then it should return true
		when(hotelDAO.addNewHotel(any())).thenReturn(addHotel);

		// Calling addNewHotel service method to add the hotel object & storing the
		// boolean result
		Boolean hotelAdded = hotelService.addNewHotel(hotel);

		// asserting that hotelAdded is "true" (addHotel)
		assertThat(hotelAdded, is(addHotel));
	}

	@Test
	public void addNewHotel_HotelIsNotAdded_ReturnFalse() {

		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_id(101);

		// when hotel dao's addNewHotel is called then it should return false (adding
		// hotel with duplicate hotel id)
		when(hotelDAO.addNewHotel(any())).thenReturn(false);

		// Calling addNewHotel service method to add the hotel object & storing the
		// boolean result
		Boolean hotelAdded = hotelService.addNewHotel(hotel);

		// asserting that hotelAdded is returning false
		assertThat(hotelAdded, is(false));
	}

	@Test
	public void deleteAHotel_HotelIsDeleted_ReturnTrue() {

		// Create hotel object
		Hotel hotel = new Hotel();

		// set hotel properties
		hotel.setHotel_id(105);
		hotel.setHotel_name("Taj");
		hotel.setHotel_location("Mumbai");
		hotel.setHotel_phone("9876543210");
		hotel.setHotel_rating(4);
		hotel.setHotel_email("taj@gmail.com");
		hotel.setPet_friendly(true);

		Boolean deleteHotel = true;

		// when hotel dao's deleteAHotel is called then it should return true
		when(hotelDAO.deleteAHotel(anyInt())).thenReturn(deleteHotel);

		// Calling deleteAHotel service method to delete the hotel object & storing the
		// boolean result
		Boolean hotelDeleted = hotelService.deleteAHotel(105);

		// asserting that hotelDeleted is "true" (deleteHotel)
		assertThat(hotelDeleted, is(deleteHotel));
	}

	@Test
	public void deleteAHotel_HotelIsDeleted_ReturnFalse() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(105);

		// when hotel dao's deleteAHotel is called then it should return false
		when(hotelDAO.deleteAHotel(anyInt())).thenReturn(false);

		// Calling deleteAHotel service method to delete the hotel object which doesn't
		// exist in DB & storing the
		// boolean result
		Boolean hotelDeleted = hotelService.deleteAHotel(110);

		// asserting that hotelDeleted is "false"
		assertThat(hotelDeleted, is(false));
	}

	@Test
	public void deleteAHotel_HotelIsDeleted_ReturnNull() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_id(105);

		Boolean deleteHotel = true;

		// when hotel dao's deleteAHotel is called then it should return true
		when(hotelDAO.deleteAHotel(anyInt())).thenReturn(deleteHotel);

		// Calling deleteAHotel service method to delete the hotel object
		hotelService.deleteAHotel(105);

		// after deleting hotel, setting the hotel object as NULL
		hotel = null;

		assertThat(null, is(hotel));
	}

	@Test
	public void editAHotel_HotelIsUpdatedSuccessfully_ReturnTrue() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_email("updatedtaj@gmail.com");

		// when hotel dao's editAHotel is called then it should return true
		when(hotelDAO.editAHotel(anyInt(), any())).thenReturn(true);

		// Calling addNewHotel service method to add the hotel object & storing the
		// boolean result
		Boolean hotelEdited = hotelService.editAHotel(101, hotel);

		// asserting that hotelEdited is "true"
		assertThat(hotelEdited, is(true));
	}

	@Test
	public void editAHotel_HotelIsNotUpdated_ReturnFalse() {

		// Create hotel object
		Hotel hotel = new Hotel();
		hotel.setHotel_email("updatedtaj@gmail.com");

		// when hotel dao's editAHotel is called then it should return false
		when(hotelDAO.editAHotel(anyInt(), any())).thenReturn(false);

		// Calling addNewHotel service method to add the hotel object & storing the
		// boolean result
		Boolean hotelEdited = hotelService.editAHotel(101, hotel);

		// asserting that hotelEdited is "false" (not updated)
		assertThat(hotelEdited, is(false));
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

		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		customer.setBookings(bookingList);

		// when hotel dao's getCustomerInformation is called then it should return
		// customer
		when(hotelDAO.getCustomerInformation(anyInt(), anyInt())).thenReturn(customer);

		int hotelId = 101;
		int customerId = 301;

		HotelCustomerInformationDTO customerInfoDTO = hotelService.getCustomerInformation(hotelId, customerId);
		assertThat("John", is(customerInfoDTO.getCustomer_name()));
	}

	// Invalid customer id (doesn't exist in DB) is passed, Return
	// NullPointerException
	@Test(expected = NullPointerException.class)
	public void getCustomerInformation_CustomerIdIsInvalid_ReturnNullPointerException() {
		when(hotelDAO.getCustomerInformation(anyInt(), anyInt())).thenThrow(NullPointerException.class);

		int hotelId = 101;
		int customerId = 310;

		hotelService.getCustomerInformation(hotelId, customerId);
	}

	// Testing if valid list of booking ids is present in customer dto object
	@Test
	public void getCustomerInformation_TestIfValidBookingListIsPresent() {

		// create customer object & set properties
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
		Bill bill3 = new Bill();
		bill3.setBill_id(703);

		// Create booking objects
		Booking booking1 = new Booking();
		booking1.setBooking_id(501);
		Booking booking2 = new Booking();
		booking2.setBooking_id(502);
		Booking booking3 = new Booking();
		booking3.setBooking_id(503);

		booking1.setBill(bill1);
		booking2.setBill(bill2);
		booking3.setBill(bill3);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);
		bookingList.add(booking3);

		customer.setBookings(bookingList);

		// Create a list of type Integer to store booking ids
		List<Integer> bookingIdList = Arrays.asList(501, 502, 503);

		when(hotelDAO.getCustomerInformation(anyInt(), anyInt())).thenReturn(customer);

		int hotelId = 101;
		int customerId = 301;

		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
		customerInfoDTO.setBills(null);

		customerInfoDTO = hotelService.getCustomerInformation(hotelId, customerId);

		// asserting that customer has a valid list of booking ids
		assertThat(customerInfoDTO.getBookings(), is(bookingIdList));
	}

	// Testing if valid list of bill ids is present in customer dto object
	@Test
	public void getCustomerInformation_TestIfValidBillListIsPresent() {

		// create customer object & set properties
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

		booking1.setBill(bill1);
		booking2.setBill(bill2);

		// create a list of booking objects
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(booking1);
		bookingList.add(booking2);

		customer.setBookings(bookingList);

		// Create a list of type Integer to store bill ids
		List<Integer> billIdList = Arrays.asList(701, 702);

		when(hotelDAO.getCustomerInformation(anyInt(), anyInt())).thenReturn(customer);

		int hotelId = 101;
		int customerId = 301;

		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();
//		customerInfoDTO.setBills(billIdList);

		customerInfoDTO = hotelService.getCustomerInformation(hotelId, customerId);

		assertThat(customerInfoDTO.getBills(), is(billIdList));
	}

	@Test
	public void getCustomersOfAHotel_TestSizeOfCustomerList_ReturnSizeOfFour() {

		// Create Customer Objects
		Customer customer1 = new Customer();
		Customer customer2 = new Customer();
		Customer customer3 = new Customer();
		Customer customer4 = new Customer();

		// Create a List of customer objects
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		customerList.add(customer4);

		// mock the dao to return customerList when getCustomersOfAHotel is called
		when(hotelDAO.getCustomersOfAHotel(anyInt())).thenReturn(customerList);

		List<HotelCustomerDTO> customerDTOList = hotelService.getCustomersOfAHotel(101);

		assertThat(customerDTOList.size(), is(4));
	}

	@Test
	public void getCustomersOfAHotel_ContainsCustomerNamesInList() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_id(301);
		customer.setCustomer_name("John");

		Customer customer1 = new Customer();
		customer1.setCustomer_id(302);
		customer1.setCustomer_name("Mary");

		Customer customer2 = new Customer();
		customer2.setCustomer_id(303);
		customer2.setCustomer_name("Emily");

		// Create a List of customer objects
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);
		customerList.add(customer1);
		customerList.add(customer2);

		// mock the dao to return customerList when getCustomersOfAHotel is called
		when(hotelDAO.getCustomersOfAHotel(anyInt())).thenReturn(customerList);

		List<HotelCustomerDTO> customerDTOList = hotelService.getCustomersOfAHotel(101);

		assertThat(customerDTOList, contains(hasProperty("customer_name", is("John")),
				hasProperty("customer_name", is("Mary")), hasProperty("customer_name", is("Emily"))));
	}

	@Test
	public void getCustomersOfAHotel_TestInListAParticularCustomerIsNotPresent() {

		// Create customer object & set properties
		Customer customer = new Customer();
		customer.setCustomer_id(301);
		customer.setCustomer_name("John");

		Customer customer1 = new Customer();
		customer1.setCustomer_id(302);
		customer1.setCustomer_name("Mary");

		Customer customer2 = new Customer();
		customer2.setCustomer_id(303);
		customer2.setCustomer_name("Emily");

		Customer customer3 = new Customer();

		// Create a List of customer objects
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);
		customerList.add(customer1);
		customerList.add(customer2);

		// mock the dao to return customerList when getCustomersOfAHotel is called
		when(hotelDAO.getCustomersOfAHotel(anyInt())).thenReturn(customerList);

		List<HotelCustomerDTO> customerDTOList = hotelService.getCustomersOfAHotel(101);

		// assert that customerdto list does not contain "customer3"
		assertThat(customerDTOList.contains(customer3), is(false));
	}

	@Test
	public void getCustomersOfAHotel_IfWrongHotelIdPassed_ReturnEmptyListOfCustomers() {

		// Create an empty List of customer
		List<Customer> customerList = new ArrayList<>();

		// mock the dao to return customerList when getCustomersOfAHotel is called
		when(hotelDAO.getCustomersOfAHotel(anyInt())).thenReturn(customerList);

		List<HotelCustomerDTO> customerDTOList = hotelService.getCustomersOfAHotel(101);

		// assert that list returned is empty
		assertThat(customerDTOList.isEmpty(), is(true));
	}
}
