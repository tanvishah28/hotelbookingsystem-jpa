package com.entlogics.hotelbookingsystem_jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;

//interface for Hotel Service layer having method declaration for all hotel api endpoints

public interface IHotelService {

//	public List<HotelDTO> getAllHotels(Boolean isSearch, String hotel_name);
//	public List<HotelDTO> getAllHotels(String hotel_name);
	
	public List<HotelDTO> getAllHotels(String... args);

	public boolean addNewHotel(Hotel h);

	public HotelInformationDTO getHotelInformation(int hotelId);

	public boolean deleteAHotel(int hotelId);

	public boolean editAHotel(int hotelId, Hotel h);
	
	public HotelCustomerInformationDTO getCustomerInformation(int hotelId, int customerId);
	
	public List<HotelCustomerDTO> getCustomersOfAHotel(int hotelId);
}
