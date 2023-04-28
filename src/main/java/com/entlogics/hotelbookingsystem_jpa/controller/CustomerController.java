package com.entlogics.hotelbookingsystem_jpa.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;
import com.entlogics.hotelbookingsystem_jpa.service.ICustomerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class CustomerController {

	// create logger object
	private static final Logger logger = LogManager.getLogger(CustomerController.class);

	// injecting customer service dependency using field injection
	@Autowired
	private ICustomerService customerService;

	// this method gets list of customers - returning JSON output
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public List<CustomerDTO> getListofCustomers() {

		logger.info("Inside CustomerController - getListofCustomers()");

		// calling Service method to get list of customers
		List<CustomerDTO> customerList = customerService.getAllCustomers();

		logger.info("Inside CustomerController - getListofCustomers() " + customerList);

		// returning the customerList
		return customerList;
	}

	// this method maps the request for adding new customer - /customers Taking JSON
	// input
	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public String addNewCustomer(@RequestBody Customer customer) {

		logger.info("Inside CustomerController - addNewCustomer()");

		logger.info("Printing customer object in the CustomerController addNewCustomer(): " + customer);

		// call Service method addNewCustomer()
		boolean isAdded = customerService.addNewCustomer(customer);
		String added = String.valueOf(isAdded);
		
		logger.info("Printing customer object in the CustomerController addNewCustomer(): " + added);

		return added;
	}

	// this method gets details of one Customer - returning JSON
	@RequestMapping(value = "/customers/{customer-id}", method = RequestMethod.GET)
	@ResponseBody
	public CustomerDTO getOneCustomer(@PathVariable("customer-id") int customerId) {

		logger.info("Inside CustomerController - getOneCustomer()");

		// using Service object to call DAO method to get details of a single hotel
		CustomerDTO customerDTO = customerService.getCustomerInformation(customerId);

		logger.info("Inside CustomerController - getOneCustomer() Customer object : " + customerDTO);

		return customerDTO;
	}

	// this method edits a customer record
	@RequestMapping(value = "/customers/{customer-id}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseBody
	public String editCustomer(@PathVariable("customer-id") int customerId, @RequestBody Customer customer) {

		logger.info("Inside CustomerController - editCustomer() " + customerId + " " + customer);

		// getting the request body & creating Customer object
		Customer c = customer;

		// creating boolean variable & passing customerId & customer object to customerService
		boolean isEdited = customerService.editACustomer(customerId, c);

		// converting value of isEdited (boolean) to String & passing it to "edited"
		String edited = String.valueOf(isEdited);
		
		logger.info("Inside CustomerController - editCustomer() edited: " + edited);

		return edited;
	}
}
