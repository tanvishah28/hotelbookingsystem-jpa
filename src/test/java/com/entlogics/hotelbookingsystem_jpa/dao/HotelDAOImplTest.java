package com.entlogics.hotelbookingsystem_jpa.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/hotelbookingsystemjpa-servlettest.xml")
public class HotelDAOImplTest {

	@Autowired
	private IHotelDAO hotelDAO;

	// This will test whether the getAllHotels dao method returns a list of 5 hotel
	// objects
	@Test
	public void getAllHotels_SizeOfHotelList_ReturnListOfHotels() {

		// creating a list of array of objects and getting list of hotels
		List<Hotel> listHotel = hotelDAO.getAllHotels();

		// asserting that total no of hotels in list is 5
		assertThat(listHotel.size(), is(5));
	}

	// This will test whether the getAllHotels dao method returns the list with
	// hotel names present in it
	@Test
	public void getAllHotels_ContainsAllHotelNamesInList_ReturnListOfHotels() {

		// creating a list of array of objects and getting list of hotels
		List<Hotel> listHotel = hotelDAO.getAllHotels();

		assertThat(listHotel,
				contains(hasProperty("hotel_name", is("Taj")), hasProperty("hotel_name", is("Hyatt")),
						hasProperty("hotel_name", is("Leela")), hasProperty("hotel_name", is("Oberio")),
						hasProperty("hotel_name", is("Taj Mahal"))));
	}

	// This will test whether the getAllHotels dao method returns a hotel which is
	// passed in req param
	@Test
	public void getAllHotels_SizeOfHotelList_ReturnSearchedHotel() {

		// creating a list of array of objects and getting list of hotels
		List<Hotel> listHotel = hotelDAO.getAllHotels("T");

		// asserting that total no of hotels in list is 3
		assertThat(listHotel.size(), is(3));
	}

	// Test if searching for "T" returns all hotels which has "T" in their name
	@Test
	public void getAllHotels_ReturnSearchedHotel() {

		// creating a list of array of objects and getting list of hotels
		List<Hotel> listHotel = hotelDAO.getAllHotels("T");

		// asserting that list of hotels has property hotel_name's with taj, hyatt & taj
		// mahal in any order
		assertThat(listHotel, containsInAnyOrder(hasProperty("hotel_name", is("Taj Mahal")),
				hasProperty("hotel_name", is("Hyatt")), hasProperty("hotel_name", is("Taj"))));
	}

	// This test method will test when a wrong hotel id is passed to
	// getHotelInformation dao method, it returns null or not
	@Test
	public void getHotelInformation_HotelIdIsWrong_ReturnNull() {

		// getting a hotel object & getting hotel with id 110 (which does not exist)
		Hotel hotel = hotelDAO.getHotelInformation(110);

		// asserting that is the hotel object null since hotel with id 110 doesn't exist
		assertThat(null, is(hotel));
	}

	// This test method will test when a correct hotel id is passed to
	// getHotelInformation dao method, it returns the hotel
	@Test
	public void getHotelInformation_HotelIdIsCorrect_ReturnHotelInformation() {

		// getting a hotel object & getting hotel with id 101
		Hotel hotel = hotelDAO.getHotelInformation(101);

		// asserting that is the hotel object received has id 101
		assertThat(hotel.getHotel_id(), is(101));
	}

	// This method will test that new hotel object is
	// added in db by getting new hotel object and comparing with expected object
	@Test
	@Transactional
	@Rollback(true)
	public void addNewHotel_NewHotelObjectSameAsExpected() {

		// creating a hotel object
		Hotel hotel = new Hotel();

		// setting hotel properties of new hotel object
		hotel.setHotel_name("Grand Hyatt");
		hotel.setHotel_location("Pune");
		hotel.setHotel_phone("8974561230");
		hotel.setHotel_email("grandhyatt@gmail.com");
		hotel.setHotel_rating(4);
		hotel.setPet_friendly(false);

		// adding hotel by calling dao
		hotelDAO.addNewHotel(hotel);

		// getting a newHotel object by passing the hotel id
		Hotel newHotel = hotelDAO.getHotelInformation(hotel.getHotel_id());

		// asserting that hotel properties are actually what are expected
//		assertEquals(hotel,newHotel);
		assertThat(newHotel, samePropertyValuesAs(hotel));
	}

	// This method will test whether the new hotel is added successfully & "true" is
	// returned or not
	@Test
	@Transactional
	@Rollback(true)
	public void addNewHotel_HotelIsAdded_ReturnTrue() {

		// creating a hotel object
		Hotel hotel = new Hotel();

		// setting hotel name property of new hotel object
		hotel.setHotel_name("Grand Hyatt");

		// adding hotel by calling dao and storing the result in boolean variable
		Boolean hotelAdded = hotelDAO.addNewHotel(hotel);

		// assert that hotelAdded returns true
		assertThat(hotelAdded, is(true));
	}

	// Testing if null(illegal) hotel instance is added, it returns
	// IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void addNewHotel_NullHotelEntityIsAdded_ReturnIllegalArgumentException() {

		// creating a hotel object
		Hotel hotel = null;

		// adding hotel by calling dao
		hotelDAO.addNewHotel(hotel);
	}

	// While adding new hotel to db if no transaction is provided, throw
	// TransactionRequiredException
	@Test(expected = TransactionRequiredException.class)
//	@Rollback(true)
	public void addNewHotel_NoTransaction_ReturnTransactionRequiredException() {

		// creating a hotel object
		Hotel hotel = new Hotel();

		// setting hotel name property of new hotel object
		hotel.setHotel_name("Grand Hyatt");

		// adding hotel, will throw TransactionRequiredException -
		// entityManager.persist(h);
		hotelDAO.addNewHotel(hotel);
	}

	// Duplicate entity is persisted, therefore at commit time -
	// PersistenceException is thrown (detached entity) -RuntimeException
	@Test(expected = PersistenceException.class)
	@Transactional
	public void addNewHotel_DuplicateHotelId_HotelIsNotAdded() {

		// creating a hotel object
		Hotel hotel = new Hotel();

		// setting duplicate hotel id (PK) (which already exists in db)
		hotel.setHotel_id(102);

		// adding hotel by calling dao method
		hotelDAO.addNewHotel(hotel);
	}

	// This method will test whether edit hotel is successful by asserting the hotel
	// name with updated hotel name property
	@Test
	@Transactional
	public void editAHotel_HotelNameUpdated_ReturnUpdatedHotelName() {

		// creating a hotel object & getting hotel with id 103
		Hotel hotel = hotelDAO.getHotelInformation(103);

		// set the hotel name (edit)
		hotel.setHotel_name("Leela Palace");

		hotelDAO.editAHotel(103, hotel);

		// get the updated hotel
		Hotel updatedHotel = hotelDAO.getHotelInformation(103);

		// check if hotel is updated
		assertEquals("Leela Palace", updatedHotel.getHotel_name());
	}

	// This method will test whether edit hotel method throws Null pointer exception
	// if wrong hotel id is passed
	@Test(expected = NullPointerException.class)
	@Transactional
	public void editAHotel_HotelNameUpdatedForWrongHotelId_ReturnNullPointerException() {

		// creating a hotel object & getting hotel with id 111
		Hotel hotel = hotelDAO.getHotelInformation(111);

		// set the hotel name (edit)
		hotel.setHotel_name("Leela Palace");
	}

	// This method will test whether edit hotel method returns Null when hotel id
	// invalid
	@Test
	@Transactional
	public void editAHotel_HotelNameUpdatedForWrongHotelId_ReturnNull() {

		// creating a hotel object & getting hotel with id 115
		Hotel hotel = hotelDAO.getHotelInformation(115);

		// asserting that is the hotel object null since hotel with id 115 doesn't exist
		assertThat(null, is(hotel));
	}

	// This method will test whether if wrong hotel id is passed then it throws an
	// Illegal Argument Exception or not
	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void deleteAHotel_HotelIdIsWrong_ReturnIllegalArgumentException() {

		hotelDAO.deleteAHotel(150);
	}

	// Correct hotel id is passed & deleting a hotel is successful
	@Test
	@Transactional
	public void deleteAHotel_HotelIdIsCorrect_ReturnNull() {

		// calling delete hotel dao method to test if it deletes hotel with id 105
		hotelDAO.deleteAHotel(105);

		// using getHotelInformation dao method & storing the object in "hotel" object
		Hotel hotel = hotelDAO.getHotelInformation(105);

		// asserting that the hotel object is null & is deleted successfully
		assertThat(null, is(hotel));
	}

	// Correct hotel id is passed for deleting a hotel without any transaction -
	// return TransactionRequiredException
	@Test(expected = TransactionRequiredException.class)
	public void deleteAHotel_HotelIdIsCorrectNoTransaction_ReturnTransactionRequiredException() {

		// calling delete hotel dao method to test if it deletes hotel with id 105
		hotelDAO.deleteAHotel(105);
	}

	// This test method will test when correct hotel & customer ids are passed to
	// dao method, it returns the customer
	@Test
	@Transactional
	public void getCustomerInformation_CustomerIdIsCorrect_ReturnCustomerInformation() {

		// creating variables for hotel id & customer id
		int hotelId = 101;
		int customerId = 301;

		// getting customer using dao method
		Customer customer = hotelDAO.getCustomerInformation(hotelId, customerId);

		assertThat(customerId, is(customer.getCustomer_id()));
	}

	// This method will test if there is no transaction available than it should
	// return LazyInitializationException for failing to load bookings & hotels of a
	// customer
	@Test(expected = LazyInitializationException.class)
	public void getCustomerInformation_NoTransaction_ReturnLazyInitializationException() {

		// creating variables for hotel id & customer id
		int hotelId = 101;
		int customerId = 301;

		// Getting customer using dao method
		hotelDAO.getCustomerInformation(hotelId, customerId);
	}

	// This test method will test when incorrect customer id is passed to
	// dao method, it returns NullPointerException
	@Test(expected = NullPointerException.class)
	@Transactional
	public void getCustomerInformation_CustomerIdIsWrong_ReturnNullPointerException() {

		// creating variables for hotel id & customer id
		int hotelId = 105;
		int customerId = 310;

		// getting customer using dao method - passing incorrect customer id
		hotelDAO.getCustomerInformation(hotelId, customerId);
	}

	// This method will test whether the size of the list of customers of a hotel is
	// 4 or not
	@Test
	public void getCustomersOfAHotel_SizeOfCustomersList_ReturnListOfCustomersOfAHotel() {

		// create a list of customers
		List<Customer> listCustomer = hotelDAO.getCustomersOfAHotel(101);

		// asserting that total no of customers of hotel 101 in list is 4
		assertThat(listCustomer.size(), is(4));
	}

	// Testing if the list of customers has all the customer names in the list
	@Test
	public void getCustomersOfAHotel_ContainsCustomerNamesInList_ReturnListOfCustomers() {

		// create a list of customers
		List<Customer> listCustomer = hotelDAO.getCustomersOfAHotel(101);

		// asserting that if the list has property customer_name & all the customers are
		// present in list
		assertThat(listCustomer,
				contains(hasProperty("customer_name", is("John")), hasProperty("customer_name", is("Mary")),
						hasProperty("customer_name", is("Emily")), hasProperty("customer_name", is("Lily"))));
	}

	// This method will test whether the size of the list of customers of a hotel is
	// 0 when incorrect hotel id is passed
	@Test
	public void getCustomersOfAHotel_WrongHotelId_ReturnEmptyList() {

		// create a list of customers
		List<Customer> listCustomer = hotelDAO.getCustomersOfAHotel(112);

		// asserting that total no of customers of hotel 112 in list is 0
		assertThat(listCustomer.size(), is(0));
	}

}
