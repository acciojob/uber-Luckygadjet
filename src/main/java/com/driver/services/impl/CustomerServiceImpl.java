package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function

		customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		Customer customer = customerRepository2.findById(customerId).get();

		List<Driver> Drivers = driverRepository2.findAll();

		Driver driver = null;

		for(Driver d : Drivers)
		{
			if(d.getCab().getAvailable() == true)
			{
				if(driver == null || driver.getDriverId() > d.getDriverId())
				{
					driver = d;
				}
			}
		}

		if(driver == null)
		{
			throw new Exception("No value present");
		}

		// If driver is Available

		TripBooking tripBooking = new TripBooking();

		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		driver.getCab().setAvailable(false);

		customer.getTripBookingList().add(tripBooking);
		driver.getTripBookingList().add(tripBooking);

		customerRepository2.save(customer);
		driverRepository2.save(driver);

		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setBill(0);
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.getDriver().getCab().setAvailable(true);

		tripBookingRepository2.save(tripBooking);



	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);
		int bill = tripBooking.getDriver().getCab().getPerKmRate() * tripBooking.getDistanceInKm();
		tripBooking.setBill(bill);

		tripBookingRepository2.save(tripBooking);


	}
}
