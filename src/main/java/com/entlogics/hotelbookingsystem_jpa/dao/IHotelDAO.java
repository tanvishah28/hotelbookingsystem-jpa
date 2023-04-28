package com.entlogics.hotelbookingsystem_jpa.dao;

import java.util.List;

import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Customer;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;

// interface for Hotel DAO having method declaration for all hotel api endpoints

public interface IHotelDAO {

//	public List<Object[]> getAllHotels(Boolean isSearch, String hotel_name);
//	public List<Hotel> getAllHotels(String hotel_name);
	
	public List<Hotel> getAllHotels(String... args);
	
	public boolean addNewHotel(Hotel h);
	
	public boolean editAHotel(int hotelId, Hotel h);

	public boolean deleteAHotel(int hotelId);
	
	public Hotel getHotelInformation(int hotelId);

	public Customer getCustomerInformation(int hotelId, int customerId);

	public List<Customer> getCustomersOfAHotel(int hotelId);
}
