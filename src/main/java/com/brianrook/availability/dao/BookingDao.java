package com.brianrook.availability.dao;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.Map;

public interface BookingDao
{
   void addBooking(int campsiteId, Interval bookingDates);

   Map<DateTime, Boolean> getBookingsForCampsite(int campsiteId);
}
