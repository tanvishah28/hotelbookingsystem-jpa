package com.entlogics.hotelbookingsystem_jpa.service;

import java.util.List;

import com.entlogics.hotelbookingsystem_jpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;

//interface for Customer Service layer having method declaration for all customer api endpoints
public interface ICustomerService {

	public List<CustomerDTO> getAllCustomers();

	public boolean addNewCustomer(Customer c);
	
	public boolean editACustomer(int customerId, Customer c);
	
	public CustomerDTO getCustomerInformation(int customerId);
}
