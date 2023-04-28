package com.entlogics.hotelbookingsystem_jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Booking;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;

// this class implements the HotelDAO interface & has the implementation methods of all hotel api endpoints - JPA

@Repository
public class HotelDAOImpl implements IHotelDAO {

	// injecting EntityManager
	@PersistenceContext
	private EntityManager entityManager;

	// create logger object
	private static final Logger logger = LogManager.getLogger(HotelDAOImpl.class);

	// returns a list of Hotel objects based on search parameter passed
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Hotel> getAllHotels(String... args) {

		String hotel_name = "";

		for (String hotelName : args) {
			hotel_name = hotelName;
		}

		logger.info("Inside getAllHotels() in DAO with arguments HOTEL_NAME : " + hotel_name);

		// creating list of arrays of Object and getting the result of query as a list
		List<Hotel> listHotel = new ArrayList<>();

		// step 1 : Use criteria builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);

		// Step 2 : Define roots for table which are involved in the query
		Root<Hotel> hotelRoot = cq.from(Hotel.class);

		cq.select(hotelRoot);

		// Step 3 : Define predicates using Criteria builder
		Predicate whereHotelName = cb.like(hotelRoot.<String>get("hotel_name"), "%" + hotel_name + "%");

		// Step 4 : Add predicate to criteria query
		cq.where(whereHotelName);

		// Step 5 : Build the query using entityManager & criteria query
		Query criteriaQuery = entityManager.createQuery(cq.select(hotelRoot));

		listHotel = criteriaQuery.getResultList();

		logger.info("Inside getAllHotels() in DAO Query Hotel: "
				+ criteriaQuery.unwrap(org.hibernate.query.Query.class));

		logger.info("Inside getAllHotels() in DAO :" + listHotel);

		// return the hotelList
		return listHotel;
	}

	// returns a list of hotels
	/*
	 * @Override public List<Hotel> getAllHotels() {
	 * logger.info("Inside getAllHotels() in DAO without arguments");
	 * 
	 * String hotel_name = ""; // return the hotelList return
	 * getAllHotels(hotel_name); }
	 */

	// adds a new hotel object to the db
	@Override
	public boolean addNewHotel(Hotel h) {

		logger.info("Inside HotelDAO addNewHotel()");

		// a boolean object indicating the success/failure of adding new hotel
		boolean isSuccess = false;

		logger.info("Printing hotel object in the HotelDAO addNewHotel()" + h + " " + isSuccess);

		// save the hotel using entityManager persist
		entityManager.persist(h);

		// set isSuccess to true after successfully saving hotel
		isSuccess = true;

		logger.info("Printing hotel object in the HotelDAO addNewHotel() After Persisting" + h + " " + isSuccess);

		// Send a success message back to Controller
		return isSuccess;
	}

	// edits a hotel object in the db
	@Override
	public boolean editAHotel(int hotelId, Hotel h) {

		logger.info("Inside HotelDAO editAHotel()");

		// declaring boolean variable to track the status of method execution
		boolean isSuccess = false;

		// Creating hotel variable & storing hotel information in it - using find()
		Hotel hotel = entityManager.find(Hotel.class, hotelId);

		// getting the hotel object from controller & setting the values of hotel
		hotel.setHotel_name(h.getHotel_name());
		hotel.setHotel_location(h.getHotel_location());
		hotel.setHotel_phone(h.getHotel_phone());
		hotel.setHotel_email(h.getHotel_email());
		hotel.setHotel_rating(h.getHotel_rating());
		hotel.setPet_friendly(h.isPet_friendly());

		logger.info("Hotel in editAHotel() DAO: " + hotel);

		isSuccess = true;

		// Send a success message back to Controller
		return isSuccess;
	}

	// deletes a hotel from the db
	@Override
	public boolean deleteAHotel(int hotelId) {

		logger.info("Inside HotelDAO deleteAHotel()" + hotelId);

		// declaring boolean variable to track the status of method execution
		boolean isSuccess = false;

		// get the hotel which you want to delete using getHotelInformation
		Hotel hotel = entityManager.find(Hotel.class, hotelId);

		// when hotel id is incorrect (not exist) the hotel object will be null &
		// isSuccess false
		logger.info("Inside HotelDAO deleteAHotel() before deleting " + hotel + " " + isSuccess);

		// delete the hotel using entityManager's remove()
		entityManager.remove(hotel);

		/*
		 * catch (IllegalArgumentException e) { throw new
		 * IllegalArgumentException("Invalid Hotel ID passed."); } catch
		 * (TransactionRequiredException ex) { throw new
		 * TransactionRequiredException("No Transaction available."); }
		 */

		// after successfully deleting hotel, making isSuccess boolean variable true
		isSuccess = true;

		logger.info("Inside HotelDAO deleteAHotel() after deleting " + hotel + " " + isSuccess);
		return isSuccess;
	}

	// gets one hotel object (single row of a table) from db
	@Override
	public Hotel getHotelInformation(int hotelId) {

		logger.info("Inside HotelDAO getHotelInformation(): " + hotelId);

		// creating hotel object (variable)
		Hotel hotel;

		// getting hotel information
		hotel = entityManager.find(Hotel.class, hotelId);

		logger.info("Inside getHotelInformation DAO, hotel object: " + hotel);

		// returning hotel object with bookings, services, customers, employees
		return hotel;
	}

	// this methods gets single customer of a hotel from db - not completed in JPA
	@Override
	public Customer getCustomerInformation(int hotelId, int customerId) {

		logger.info("Inside HotelDAO getCustomerInformation(), hotel id: " + hotelId + " customer id: " + customerId);

		Customer customer;

		// getting customer information - the customer obj is in "Persistent/Managed
		// State"
		customer = entityManager.find(Customer.class, customerId);

		logger.info("Inside getCustomerInformation DAO, customer object: " + customer);

		// create a list of bookings and get list of bookings of a customer using
		// getBookings
		List<Booking> listBooking = customer.getBookings();

		// iterate over the list of bookings to lazy load the booking object
		for (Booking b : listBooking) {

			// print booking id using booking object
			logger.info("Inside DAO getCustomerInformation() Booking id: " + b.getBooking_id());

			// using b get bill id of the customer
			b.getBill().getBill_id();

			// print bill id
			logger.info("Inside DAO getCustomerInformation() Bill id: " + b.getBill().getBill_id());
		}

		// returning customer object
		return customer;
	}

	// this method gets all customers of a hotel
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomersOfAHotel(int hotelId) {

		logger.info("Inside HotelDAO getCustomersOfAHotel(), hotel id: " + hotelId);

		// create list of Customer
		List<Customer> customerList = new ArrayList<>();

		// step 1 : Use criteria builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

		// Step 2 : Define roots for table which are involved in the query
		Root<Customer> customerRoot = cq.from(Customer.class);

		cq.select(customerRoot);

		// Step 3 : Define predicates using Criteria builder
		// Using Join to join customer with hotels
		Join<Object, Object> join = customerRoot.join("hotels");

		// Using "hotels" join to join "hotel"
		Join<Object, Object> joinHotel = join.join("hotel");

		Predicate whereHotelId = cb.equal(joinHotel.get("hotel_id"), hotelId);

		// Step 4 : Add predicate to criteria query
		cq.where(whereHotelId);

		// Step 5 : Build the query using entityManager & criteria query
		Query criteriaQuery = entityManager.createQuery(cq.select(customerRoot));

		customerList = criteriaQuery.getResultList();

//		String sql = SQLExtractor.from(criteriaQuery);
		logger.info("Inside HotelDAO getCustomersOfAHotel() Query Customer: "
				+ criteriaQuery.unwrap(org.hibernate.query.Query.class).getQueryString());

		logger.info("Inside HotelDAO getCustomersOfAHotel() CustomerList: " + customerList);

		return customerList;
	}
}
