package com.entlogics.hotelbookingsystem_jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Booking;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;

//this class implements the CustomerDAO interface & has the implementation methods of all customer api endpoints - JPA

@Repository
public class CustomerDAOImpl implements ICustomerDAO {

	// inject the session factory - field injection
	@Autowired
	private SessionFactory sessionFactory;

	// injecting EntityManager
	@PersistenceContext
	private EntityManager entityManager;

	// create logger object
	private static final Logger logger = LogManager.getLogger(CustomerDAOImpl.class);

	// gets list of all customers
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDTO> getAllCustomers() {

		logger.info("Inside getAllCustomers() in CustomerDAO");

		// creating list of arrays of Object and getting the result of query as a list
		List<Object[]> listCustomer = new ArrayList<>();

		// creating list of type CustomerDTO
		List<CustomerDTO> customerDtoList = new ArrayList<>();

		// creating list of Integer for booking ids
		List<Integer> bookingIdsList = new ArrayList<>();

		// creating list of Integer for bill ids
		List<Integer> billIdsList = new ArrayList<>();

		// creating list of Integer for hotel ids
		List<Integer> hotelIdsList = new ArrayList<>();

		// create UnTypedQuery for returning list of customers
		Query customerQuery = entityManager.createQuery(
				"SELECT distinct c.customer_id, c.customer_name, c.customer_address,c.customer_phone,c.customer_email,c.preferences,c.special_needs \r\n"
						+ "FROM Customer c");

		listCustomer = customerQuery.getResultList();

		for (Object[] c : listCustomer) {
			CustomerDTO customerDto = new CustomerDTO();

			logger.info("Inside CustomerService getAllCustomers " + c[0]);
			// if(!customerDtoList.contains((int) c[0])) {
			// set the values of each c[] to customerDto
			customerDto.setCustomer_id((int) c[0]);
			customerDto.setCustomer_name((String) c[1]);
			customerDto.setCustomer_address((String) c[2]);
			customerDto.setCustomer_phone((String) c[3]);
			customerDto.setCustomer_email((String) c[4]);
			customerDto.setPreferences((String) c[5]);
			customerDto.setSpecial_needs((String) c[6]);

			Query hotelQuery = entityManager.createQuery(
					"SELECT h.hotel_id FROM Hotel_Customer hc JOIN hc.hotel h WHERE hc.customer.customer_id =" + c[0]);

			hotelIdsList = hotelQuery.getResultList();

			Query bookingQuery = entityManager
					.createQuery("SELECT bo.booking_id FROM Booking bo WHERE bo.customer.customer_id =" + c[0]);

			bookingIdsList = bookingQuery.getResultList();

			Query billQuery = entityManager.createQuery(
					"SELECT b.bill_id FROM Booking bo JOIN bo.bill b WHERE bo.customer.customer_id =" + c[0]);

			billIdsList = billQuery.getResultList();

			customerDto.setHotels(hotelIdsList);
			customerDto.setBookings(bookingIdsList);
			customerDto.setBills(billIdsList);

			customerDtoList.add(customerDto);
		}
		logger.info("Inside getAllCustomers() in CustomerDAO " + listCustomer);

		// returning list of customer - listCustomer
		return customerDtoList;
	}

	// adds a new customer object
	@Override
	public boolean addNewCustomer(Customer c) {

		logger.info("Inside addNewCustomer in CustomerDAO " + c);

		// a boolean obj indicating success or failure of adding a customer
		boolean isSuccess = false;

		// using persist method to save new customer obj to DB
		entityManager.persist(c);

		// set isSuccess to true after successfully saving customer
		isSuccess = true;

		// send success msg back to controller
		return isSuccess;
	}

	// edits a customer object in DB
	@Override
	public boolean editACustomer(int customerId, Customer c) {

		logger.info("Inside editACustomer in CustomerDAO " + customerId);

		// creating boolean variable & setting it to false
		boolean isSuccess = false;

		// creating customer variable to store customer info for edit
		Customer customer = entityManager.find(Customer.class, customerId);

		// setting the values of customer - from customer object
		customer.setCustomer_name(c.getCustomer_name());
		customer.setCustomer_address(c.getCustomer_address());
		customer.setCustomer_phone(c.getCustomer_phone());
		customer.setCustomer_email(c.getCustomer_email());
		customer.setPreferences(c.getPreferences());
		customer.setSpecial_needs(c.getSpecial_needs());

		logger.info("Inside CustomerDAO editACustomer " + customer);

		// setting isSuccess to true after successfully editing customer object
		isSuccess = true;

		return isSuccess;
	}

	// this method will return information of a single customer with its booking,
	// bill & hotel details
	@Override
	public Customer getCustomerInformation(int customerId) {

		logger.info("Inside getCustomerInformation in CustomerDAO " + customerId);

		// Create customer object
		Customer customer;

		// get customer info using entityManager find()
		customer = entityManager.find(Customer.class, customerId);

		logger.info("Inside getCustomerInformation in CustomerDAO " + customerId);

		// create a list of bookings and get list of bookings of a customer using
		// getBookings
		List<Booking> listBooking = customer.getBookings();

		// iterate over the list of bookings to lazy load the booking object
		for (Booking b : listBooking) {

			// print booking id using booking object
			logger.info("Inside CustomerDAO getCustomerInformation() Booking id: " + b.getBooking_id());

			// using booking object "b" to get bill id of the customer
			b.getBill().getBill_id();

			// print bill id
			logger.info("Inside CustomerDAO getCustomerInformation() Bill id: " + b.getBill().getBill_id());
		}
		return customer;
	}

}
