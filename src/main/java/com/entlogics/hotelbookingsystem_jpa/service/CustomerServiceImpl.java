package com.entlogics.hotelbookingsystem_jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entlogics.hotelbookingsystem_jpa.dao.ICustomerDAO;
import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Booking;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel_Customer;

//this class implements Customer Service Interface & has implementation for all Customer api endpoints

@Service
public class CustomerServiceImpl implements ICustomerService {

	// create logger object
	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

	// injecting Customer DAO using field injection
	@Autowired
	private ICustomerDAO customerDAO;

	@Override
	@Transactional(readOnly = true)
	public List<CustomerDTO> getAllCustomers() {

		logger.info("Inside Customer Service getAllCustomers()");

		// creating list of type
		List<CustomerDTO> customerList = customerDAO.getAllCustomers();

		return customerList;
	}

	// this method will create a transaction & return add customer method to DAO
	@Override
	@Transactional
	public boolean addNewCustomer(Customer c) {

		logger.info("Inside Customer Service addNewCustomer() " + c + " Boolean: " +customerDAO.addNewCustomer(c));

		return customerDAO.addNewCustomer(c);
	}

	// this method will create a transaction & return edit customer method to DAO
	@Override
	@Transactional
	public boolean editACustomer(int customerId, Customer c) {

		logger.info("Inside Customer Service editACustomer() " + customerId + " " + c);

		return customerDAO.editACustomer(customerId, c);
	}

	@Override
	@Transactional
	public CustomerDTO getCustomerInformation(int customerId) {

		logger.info("Inside Customer Service getCustomerInformation() " + customerId);

		// create a customer object & get one customer
		Customer customer = customerDAO.getCustomerInformation(customerId);

		// create obj of CustomerDTO
		CustomerDTO customerDTO = new CustomerDTO();

		// set the customer obj properties
		customerDTO.setCustomer_id(customer.getCustomer_id());
		customerDTO.setCustomer_name(customer.getCustomer_name());
		customerDTO.setCustomer_address(customer.getCustomer_address());
		customerDTO.setCustomer_phone(customer.getCustomer_phone());
		customerDTO.setCustomer_email(customer.getCustomer_email());
		customerDTO.setPreferences(customer.getPreferences());
		customerDTO.setSpecial_needs(customer.getSpecial_needs());

		// creating list of Integer for booking ids & iterating through getBookings to
		// add Id to the list
		List<Integer> bookingIdsList = new ArrayList<>();

		for (Booking b : customer.getBookings()) {
			bookingIdsList.add(b.getBooking_id());
		}

		// creating list of Integer for bill ids & iterating through getBookings to add
		// Id to the list
		List<Integer> billIdsList = new ArrayList<>();

		for (Booking b : customer.getBookings()) {
			billIdsList.add(b.getBill().getBill_id());
		}

		// creating list of Integer for hotel ids & iterating through getHotels to add
		// Id to the list
		List<Integer> hotelIdsList = new ArrayList<>();

		for (Hotel_Customer h : customer.getHotels()) {
			hotelIdsList.add(h.getHotel().getHotel_id());
		}

		// set list of Integer (IDs) for booking,bill & hotel
		customerDTO.setBookings(bookingIdsList);
		customerDTO.setBills(billIdsList);
		customerDTO.setHotels(hotelIdsList);

		logger.info("Inside Customer Service getCustomerInformation() " + customerDTO);

		return customerDTO;
	}

}
