package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {

    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String, Booking> bookingDb = new HashMap<>();

    HashMap<Integer, List<Booking>> userBookingDb = new HashMap<>();
    public String addHotel(Hotel hotel) {
        //You need to add a hotel to the database
        //In case the hotelName is null or the hotel Object is null return an empty a FAILURE
        //In case somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.

        if(hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }else if(hotelDb.containsKey(hotel.getHotelName())) {
            return "FAILURE";
        }

        String name = hotel.getHotelName();

        hotelDb.put(name, hotel);

        return "SUCCESS";
    }

    public Integer addUser(User user) {
        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadhar CardNo of the user
        //String name = user.getName();
        userDb.put(user.getaadharCardNo(), user);

        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

        String res = "";
        int cnt = 1;

        for(String hotels : hotelDb.keySet()) {
            Hotel hotel = hotelDb.get(hotels);
            char ch = hotels.charAt(0);
            List<Facility> facilities = hotel.getFacilities();
            int size = facilities.size();

            if(cnt <= size) {
                if(cnt == size) {
                    if(res.equals("")) {
                        res = hotels;
                    } else {
                        res = res.charAt(0) > ch ? hotels : res;
                    }
                } else {
                    res = hotels;
                    cnt = size;
                }
            }
        }
        return res;
    }

    public int bookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there aren't enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        UUID uuid = UUID.randomUUID();
        booking.setBookingId(String.valueOf(uuid));

        int pricePerRoom = hotelDb.get(booking.getHotelName()).getPricePerNight();
        int noOfRooms = booking.getNoOfRooms();

        if(noOfRooms > hotelDb.get(booking.getHotelName()).getAvailableRooms()) {
            return -1;
        }

        booking.setAmountToBePaid(pricePerRoom * noOfRooms);
        List<Booking> list = userBookingDb.getOrDefault(booking.getBookingAadharCard(), new ArrayList<>());
        list.add(booking);
        userBookingDb.put(booking.getBookingAadharCard(), list);
        bookingDb.put(String.valueOf(uuid), booking);

        return booking.getAmountToBePaid();

    }

    public int getBookins(Integer aadharCard) {
        List<Booking> bookings = userBookingDb.get(aadharCard);

        return bookings.size();
    }

    public Hotel update(List<Facility> newFacilities, String hotelName) {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible

        Hotel hotel = hotelDb.get(hotelName);

        List<Facility> facilityList = hotel.getFacilities();

        HashSet<Facility> facilities = new HashSet<>(facilityList);

        facilities.addAll(newFacilities);

        List<Facility> finalList = new ArrayList<>(facilities);

        hotel.setFacilities(finalList);

        hotelDb.put(hotelName, hotel);

        return hotel;

    }
}