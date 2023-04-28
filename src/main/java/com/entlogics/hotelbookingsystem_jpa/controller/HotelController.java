package com.entlogics.hotelbookingsystem_jpa.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelCustomerInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystem_jpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystem_jpa.entity.Hotel;
import com.entlogics.hotelbookingsystem_jpa.service.IHotelService;

// crossOrigin annotation declares origin as * that means any origin can access the resources on this origin
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class HotelController {

	// create logger object
	private static final Logger logger = LogManager.getLogger(HotelController.class);

	// injecting hotel service dependency using field injection
	@Autowired
	private IHotelService hotelService;

	// this method gets list of hotels - returning JSON output
	@RequestMapping(value = "/hotels", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public List<HotelDTO> getListofHotels(@RequestParam(value = "hotel_name", required = false) String hotel_name) {

		logger.info("Inside HotelController - getListofHotels()");

		logger.info("Inside HotelController - getListofHotels() HotelName Outside IF" + hotel_name);

		List<HotelDTO> hotelList;

		// checking whether RequestParam is empty or null
		if (hotel_name == " " || hotel_name == null) {

			// calling Service method to get list of hotels
			hotelList = hotelService.getAllHotels();

		} else {
			logger.info("Inside HotelController - getListofHotels() HotelName Inside IF" + hotel_name);

			// calling Service method to get list of hotels with search
			hotelList = hotelService.getAllHotels(hotel_name);
		}

		logger.info("Inside HotelController - getListofHotels() hotelDTO " + hotelList);

		// returning the hotelList
		return hotelList;
	}

	// this method maps the request for adding new hotel - /hotels Taking JSON input
	@RequestMapping(value = "/hotels", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public String addNewHotel(@RequestBody Hotel hotel) {

		logger.info("Inside HotelController - addNewHotel()");

		logger.info("Printing hotel object in the HotelController addNewHotel(): " + hotel);

		// call Service method addNewHotel()
		boolean isAdded = hotelService.addNewHotel(hotel);

		String added = String.valueOf(isAdded);

		logger.info("Printing hotel object in the HotelController addNewHotel() Status String: " + added);
//		logger.info("Printing hotel object in the HotelController addNewHotel() Status Boolean: " + isAdded);

		return added;
	}

	// this method gets details of one Hotel - returning JSON
	@RequestMapping(value = "/hotels/{hotel-id}", method = RequestMethod.GET)
	@ResponseBody
	public HotelInformationDTO getOneHotel(@PathVariable("hotel-id") int hotelId) {

		logger.info("Inside HotelController - getOneHotel()");

		// using Service object to call DAO method to get details of a single hotel
		HotelInformationDTO hotelInfoDTO = hotelService.getHotelInformation(hotelId);

		logger.info("Inside HotelController - getOneHotel() Hotel object : " + hotelInfoDTO);

		return hotelInfoDTO;
	}

	// this method edits a hotel record
	@RequestMapping(value = "/hotels/{hotel-id}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseBody
	public String editNewHotel(@PathVariable("hotel-id") int hotelId, @RequestBody Hotel hotel) {

		logger.info("Inside HotelController - editNewHotel() " + hotelId + " Hotel: " + hotel);

		// getting the request body & creating Hotel object
		Hotel h = hotel;

		// creating boolean variable & passing hotelId & hotel object to HotelService
		// method
		boolean isEdited = hotelService.editAHotel(hotelId, h);

		// converting value of isEdited (boolean) to String & passing it to "edited"
		String edited = String.valueOf(isEdited);

		logger.info("Inside HotelController - editNewHotel() " + h + " " + edited);

		return edited;
	}

	// this method deletes a hotel record
	@RequestMapping(value = "/hotels/{hotel_id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteOneHotel(@PathVariable("hotel_id") int hotelId) {

		logger.info("Inside HotelController - deleteOneHotel() Hotel ID: " + hotelId);

		// using Service object to call DAO method to delete a hotel
		boolean isDeleted = hotelService.deleteAHotel(hotelId);

		// getting boolean result and converting it to string to store it in string
		// variable
		String deletedHotel = String.valueOf(isDeleted);

		logger.info("Inside HotelController - deleteOneHotel() Status: " + deletedHotel);

		// returning deletedHotel (true or false)
		return deletedHotel;
	}

	// this method gets one customer of a hotel - GET request
	@RequestMapping(value = "/hotels/{hotel_id}/customers/{customer_id}", method = RequestMethod.GET)
	@ResponseBody
	public HotelCustomerInformationDTO getOneCustomerOfOneHotel(@PathVariable("hotel_id") int hotelId,
			@PathVariable("customer_id") int customerId) {

		logger.info("Inside HotelController - getOneCustomerOfOneHotel()");

		// using Service object to call DAO method
		HotelCustomerInformationDTO customerInfoDTO = hotelService.getCustomerInformation(hotelId, customerId);

		logger.info("Inside HotelController - getOneCustomerOfOneHotel() " + customerInfoDTO);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Invalid ID. Please provide valid ID");
		return customerInfoDTO;
	}

	// this method gets list of customers of a hotel
	@RequestMapping(value = "/hotels/{hotel_id}/customers", method = RequestMethod.GET, produces = {
			"application/json" })
	@ResponseBody
	public List<HotelCustomerDTO> getCustomersOfOneHotel(@PathVariable("hotel_id") int hotelId) {

		logger.info("Inside HotelController - getCustomersOfOneHotel() , hotel id: " + hotelId);

		// using Service object to call DAO method to get list of customers
		List<HotelCustomerDTO> customerList = hotelService.getCustomersOfAHotel(hotelId);

		return customerList;
	}
}
