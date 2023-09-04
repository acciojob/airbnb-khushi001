package com.driver.Service;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;

public class HotelManagementService {

    private HotelManagementRepository hotelManagementRepository= new HotelManagementRepository();

    public String addHotel(Hotel hotel) {

        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {

        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {

        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookins(aadharCard);
    }

    public Hotel uddate(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.update(newFacilities, hotelName);
    }
}