package com.entlogics.hotelbookingsystem_jpa.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/hotelbookingsystemjpa-servlettest.xml")
public class CustomerDAOImplTest {

	@Autowired
	private ICustomerDAO customerDAO;

	// test whether the size of list of customers returned is 5 or not
	@Test
	public void getAllCustomers_SizeOfCustomerList_ReturnListOfFiveCustomers() {

		// Create list of type customer & get all customers
		List<CustomerDTO> customerList = customerDAO.getAllCustomers();

		// Asserting that the size of list returned is 5
		assertThat(customerList.size(), is(5));
	}

	// Testing if the list of customers has all the customer names in the list
	@Test
	public void getAllCustomers_ContainsAllCustomerNamesInList_ReturnListOfCustomers() {

		// create a list of customers & get all customers
		List<CustomerDTO> customerList = customerDAO.getAllCustomers();

		// asserting that if the list has property customer_name & all the customers are
		// present in list
		assertThat(customerList,
				containsInAnyOrder(hasProperty("customer_name", is("John")), hasProperty("customer_name", is("Mary")),
						hasProperty("customer_name", is("Emily")), hasProperty("customer_name", is("Lily")),
						hasProperty("customer_name", is("Jack"))));
	}

	// test whether new customer is successfully added - return True
	@Test
	@Transactional
//	@Rollback(true) - no Rollback(true) required since by default the spring framework's transaction manager will rollback all transactions
	public void addNewCustomer_CustomerIsAdded_ReturnTrue() {

		// Create a new customer object
		Customer customer = new Customer();

		// set the customer name of new customer
		customer.setCustomer_name("Johnson");

		// add the customer to db
		Boolean customerAdded = customerDAO.addNewCustomer(customer);

		// Asserting that the customer is added & returns true
		assertThat(customerAdded, is(true));
	}

	// If no transaction available - return TransactionRequiredException
	@Test(expected = TransactionRequiredException.class)
	public void addNewCustomer_NoTransactionToAddCustomer_ReturnTransactionRequiredException() {

		// Create a new customer object
		Customer customer = new Customer();

		// set the customer name of new customer
		customer.setCustomer_name("Johnson");

		// add the customer to db
		customerDAO.addNewCustomer(customer);
	}

	// Setting duplicate customer id & adding it to db will return
	// PersistenceException
	@Test(expected = PersistenceException.class)
	@Transactional
	public void addNewCustomer_DuplicateCustomerId_ReturnPersistenceException() {

		// Create a new customer object
		Customer customer = new Customer();

		// set the customer ID (Duplicate id - which already exists)
		customer.setCustomer_id(301);

		// add the customer to db
		customerDAO.addNewCustomer(customer);
	}

	// Trying to add null customer object to db - return IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void addNewCustomer_NullCustomerEntityAdded_ReturnIllegalArgumentException() {

		// Create a new customer object & assign it null
		Customer customer = null;

		// add the customer to db
		customerDAO.addNewCustomer(customer);
	}

	// testing if getting customer info by checking the customer id of the returned
	// customer obj
	@Test
	@Transactional
	public void getCustomerInformation_CustomerdIsCorrect_ReturnCustomerInformation() {

		// getting customer info & storing it in customer obj
		Customer customer = customerDAO.getCustomerInformation(301);

		// asserting that the customer id is 301
		assertThat(customer.getCustomer_id(), is(301));
	}

	// When no transaction is provided to get customer info - return
	// LazyInitializationException when trying to get bookings of customer
	@Test(expected = LazyInitializationException.class)
	public void getCustomerInformation_NoTransaction_ReturnLazyInitializationException() {

		// getting customer information by calling dao method
		customerDAO.getCustomerInformation(301);
	}

	// Incorrect customer id passed (does not exist in db) Return
	// NullPointerException
	@Test(expected = NullPointerException.class)
	@Transactional
	public void getCustomerInformation_CustomerIdIsWrong_ReturnNullPointerException() {

		// getting customer information by calling dao method
		customerDAO.getCustomerInformation(312);
	}

	// Test whether customer has bookings & is not empty
	@Test
	@Transactional
	public void getCustomerInformation_TestCustomerHasBookings() {

		// getting customer info & storing it in customer obj
		Customer customer = customerDAO.getCustomerInformation(301);

		// asserting that list is empty
		assertThat(customer.getBookings().isEmpty(), is(false));
	}

	// Test whether customer has zero bookings
	@Test
	@Transactional
	public void getCustomerInformation_TestCustomerHasNoBookings() {

		// getting customer info & storing it in customer obj
		Customer customer = customerDAO.getCustomerInformation(303);

		// asserting that list is empty
		assertThat(customer.getBookings().isEmpty(), is(true));
	}

	// Update the email of customer & assert if updated object has that updated
	// email or not
	@Test
	@Transactional
	public void editACustomer_CustomerNameUpdated_ReturnUpdatedCustomerName() {

		// getting hotel with id 301
		Customer customer = customerDAO.getCustomerInformation(301);

		// set the customer email (edit)
		customer.setCustomer_email("johnupdated@gmail.com");

		customerDAO.editACustomer(301, customer);

		// get the updated customer
		Customer updatedCustomer = customerDAO.getCustomerInformation(301);

		// check if customer is updated
		assertEquals("johnupdated@gmail.com", updatedCustomer.getCustomer_email());
	}

	// Test whether edit customer method throws Null pointer exception if wrong
	// customer id is passed for update
	@Test(expected = NullPointerException.class)
	@Transactional
	public void editACustomer_CustomerNameUpdatedForWrongCustomerId_ReturnNullPointerException() {

		// Getting Customer with id 301
		Customer customer = customerDAO.getCustomerInformation(301);

		// set the customer email(edit)
		customer.setCustomer_email("johnupdated@gmail.com");

		customerDAO.editACustomer(350, customer);
	}
}
