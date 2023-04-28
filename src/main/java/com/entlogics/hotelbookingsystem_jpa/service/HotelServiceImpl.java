package com.entlogics.hotelbookingsystem_jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entlogics.hotelbookingsystem_jpa.dao.IHotelDAO;
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

// this class implements Hotel Service Interface & has implementation for all hotel api endpoints

@Service
public class HotelServiceImpl implements IHotelService {

	// create logger object
	private static final Logger logger = LogManager.getLogger(HotelServiceImpl.class);

	// injecting Hotel DAO using field injection
	@Autowired
	private IHotelDAO hotelDAO;

	// this method will create transaction & return list of hotels method to DAO
	@Override
	@Transactional
	public List<HotelDTO> getAllHotels(String... args) {

		String hotel_name = "";
		for (String hotelName : args) {
			hotel_name = hotelName;
		}

		List<Hotel> hotelList;

		if (hotel_name == " " || hotel_name == null) {
			hotelList = hotelDAO.getAllHotels();
		} else {
			hotelList = hotelDAO.getAllHotels(hotel_name);
		}

		logger.info("Inside Hotel Service getAllHotels() :" + hotelList);

		List<HotelDTO> hotelDtoList = new ArrayList<>();

		for (Hotel h : hotelList) {
			HotelDTO hotelDto = new HotelDTO();

			logger.info("Inside Hotel Service getAllHotels() hotelDto before :" + hotelDto);

			// setting the values of each h[] to hoteldto properties
			hotelDto.setHotel_id(h.getHotel_id());
			hotelDto.setHotel_name(h.getHotel_name());
			hotelDto.setHotel_location(h.getHotel_location());
			hotelDto.setHotel_phone(h.getHotel_phone());
			hotelDto.setHotel_rating(h.getHotel_rating());
			hotelDto.setHotel_email(h.getHotel_email());
			hotelDto.setPet_friendly(h.isPet_friendly());

			logger.info("Inside Hotel Service getAllHotels() hotelDto after :" + hotelDto);

			hotelDtoList.add(hotelDto);
		}
		return hotelDtoList;
	}

	// this method will create transaction & return addNewHotel method to DAO
	@Override
	@Transactional
	public boolean addNewHotel(Hotel h) {

		logger.info("Inside Hotel Service addNewHotel(): "+ h);

		return hotelDAO.addNewHotel(h);
	}

	// this method will create transaction & return getHotelInformation method to
	// DAO
	@Override
	@Transactional
	public HotelInformationDTO getHotelInformation(int hotelId) {

		logger.info("Inside Hotel Service getHotelInformation()");

		// creating hotel object & getting a single hotel
		Hotel hotel = hotelDAO.getHotelInformation(hotelId);

		logger.info("Inside Hotel Service getHotelInformation() hotel: " + hotel);

		// Creating object of hotel information dto
		HotelInformationDTO hotelInfoDTO = new HotelInformationDTO();

		// setting the Hotel properties
		hotelInfoDTO.setHotel_id(hotel.getHotel_id());
		hotelInfoDTO.setHotel_name(hotel.getHotel_name());
		hotelInfoDTO.setHotel_location(hotel.getHotel_location());
		hotelInfoDTO.setHotel_phone(hotel.getHotel_phone());
		hotelInfoDTO.setHotel_rating(hotel.getHotel_rating());
		hotelInfoDTO.setHotel_email(hotel.getHotel_email());
		hotelInfoDTO.setPet_friendly(hotel.isPet_friendly());

		logger.info("Inside Hotel Service getHotelInformation() hotelInfoDTO: " + hotelInfoDTO);

		// get lists from hotel - room, emp, booking, customer, service
		List<Room> roomList = hotel.getRooms();
		List<Employee> empList = hotel.getEmployees();
		List<Hotel_Customer> customerList = hotel.getCustomers();
		List<Booking> bookingList = hotel.getBookings();
		List<Hotel_Service> serviceList = hotel.getServices();

		// creating list of Integer for room ids & iterating through getRooms to add Id
		// to the list
		List<Integer> roomIdsList = new ArrayList<>();

		if (roomList != null) {
			for (Room r : hotel.getRooms()) {
				roomIdsList.add(r.getRoom_id());
			}
		}

		// creating list of Integer for emp ids & iterating through getEmployees to add
		// Id to the list
		List<Integer> empIdsList = new ArrayList<>();

		if (empList != null) {
			for (Employee e : hotel.getEmployees()) {
				empIdsList.add(e.getEmp_id());
			}
		}

		// creating list of Integer for customer ids & iterating through getCustomers to
		// add Id to the list
		List<Integer> customerIdsList = new ArrayList<>();

		if (customerList != null) {
			for (Hotel_Customer c : hotel.getCustomers()) {
				customerIdsList.add(c.getCustomer().getCustomer_id());
			}
		}

		// creating list of Integer for booking ids & iterating through getBookings to
		// add Id to the list
		List<Integer> bookingIdsList = new ArrayList<>();

		if (bookingList != null) {
			for (Booking b : hotel.getBookings()) {
				bookingIdsList.add(b.getBooking_id());
			}
		}

		// creating list of Integer for service ids & iterating through getServices to
		// add Id to the list
		List<Integer> serviceIdsList = new ArrayList<>();

		if (serviceList != null) {
			for (Hotel_Service s : hotel.getServices()) {
				serviceIdsList.add(s.getService().getService_id());
			}
		}

		// setting list of integer (IDs) for each setter respectively - Rooms,
		// Employees, Customers, Bookings, Service
		hotelInfoDTO.setRooms(roomIdsList);
		hotelInfoDTO.setEmployees(empIdsList);
		hotelInfoDTO.setCustomers(customerIdsList);
		hotelInfoDTO.setBookings(bookingIdsList);
		hotelInfoDTO.setServices(serviceIdsList);

		logger.info("Inside Hotel Service getHotelInformation() hotelInfoDTO: " + hotelInfoDTO);

		// returning hotelInformationDTO object
		return hotelInfoDTO;
	}

	// this method will create transaction & return deleteAHotel method to DAO
	@Override
	@Transactional
	public boolean deleteAHotel(int hotelId) {

		logger.info("Inside Hotel Service deleteAHotel()");

		return hotelDAO.deleteAHotel(hotelId);
	}

	// this method will create transaction & return edit hotel method to DAO
	@Override
	@Transactional
	public boolean editAHotel(int hotelId, Hotel h) {

		logger.info("Inside Hotel Service editAHotel()");

		return hotelDAO.editAHotel(hotelId, h);
	}

	// this method will create transaction & return one customer of a hotel method
	// to DAO
	@Override
	@Transactional
	public HotelCustomerInformationDTO getCustomerInformation(int hotelId, int customerId) {

		logger.info("Inside Hotel Service getCustomerInformation()");

		// creating customer object & getting a single customer info
		Customer customer = hotelDAO.getCustomerInformation(hotelId, customerId);

		// Creating object of customer information dto
		HotelCustomerInformationDTO customerInfoDTO = new HotelCustomerInformationDTO();

		// setting the customer properties
		customerInfoDTO.setCustomer_id(customer.getCustomer_id());
		customerInfoDTO.setCustomer_name(customer.getCustomer_name());
		customerInfoDTO.setCustomer_address(customer.getCustomer_address());
		customerInfoDTO.setCustomer_phone(customer.getCustomer_phone());
		customerInfoDTO.setCustomer_email(customer.getCustomer_email());
		customerInfoDTO.setPreferences(customer.getPreferences());
		customerInfoDTO.setSpecial_needs(customer.getSpecial_needs());

		// get lists from customer - booking, bill
		List<Booking> bookingList = customer.getBookings();
		List<Bill> billList = new ArrayList<>();

		// creating list of Integer for booking ids & iterating through getBookings to
		// add Id to the list
		List<Integer> bookingIdsList = new ArrayList<>();

		if (bookingList != null) {
			for (Booking b : customer.getBookings()) {
				bookingIdsList.add(b.getBooking_id());
			}
		}
		logger.info("Inside Hotel Service getCustomerInformation() bookingIdsList: " + bookingIdsList);

		// creating list of Integer for bill ids & iterating through getBookings to add
		// Id to the list
		List<Integer> billIdsList = new ArrayList<>();

		if (billList != null) {
			for (Booking b : customer.getBookings()) {
				
				logger.info("Inside Hotel Service getCustomerInformation() bookings: " + customer.getBookings());
				logger.info("Inside Hotel Service getCustomerInformation() bills: " + b.getBill());
				billIdsList.add(b.getBill().getBill_id());
			}
		}
		logger.info("Inside Hotel Service getCustomerInformation() billIdsList: " + billIdsList);

		// setting list of integer (IDs) for each setter respectively - Bookings & Bills
		customerInfoDTO.setBookings(bookingIdsList);
		customerInfoDTO.setBills(billIdsList);

		logger.info("Inside Hotel Service getCustomerInformation() customerInfoDTO: " + customerInfoDTO);

		return customerInfoDTO;
	}

	// this method will create transaction & return list of customers of a hotel
	// method to DAO
	@Override
	@Transactional
	public List<HotelCustomerDTO> getCustomersOfAHotel(int hotelId) {

		logger.info("Inside Hotel Service getCustomersOfAHotel()");

		// creating list of type customer & getting customer details from DAO
		List<Customer> customerList = hotelDAO.getCustomersOfAHotel(hotelId);

		// creating list of type customer DTO
		List<HotelCustomerDTO> customerDTOList = new ArrayList<>();

		// iterating through list of customers & setting properties of customer in
		// customer dto
		for (Customer c : customerList) {
			// creating Object of CustomerDTO
			HotelCustomerDTO customerDto = new HotelCustomerDTO();

			// setting values of customerdto properties
			customerDto.setCustomer_id(c.getCustomer_id());
			customerDto.setCustomer_name(c.getCustomer_name());
			customerDto.setCustomer_address(c.getCustomer_address());
			customerDto.setCustomer_phone(c.getCustomer_phone());
			customerDto.setCustomer_email(c.getCustomer_email());
			customerDto.setPreferences(c.getPreferences());
			customerDto.setSpecial_needs(c.getSpecial_needs());

			// adding customerdto obj to customerDTO list
			customerDTOList.add(customerDto);
			logger.info("Inside Hotel Service getCustomersOfAHotel() customer hotels: " + c.getHotels());
		}
		logger.info("Inside Hotel Service getCustomersOfAHotel() customerDTO: " + customerDTOList);
		return customerDTOList;
	}
}
