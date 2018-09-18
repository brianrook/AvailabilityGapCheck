package com.brianrook.availability.dao.impl;

import com.brianrook.availability.data.Campsite;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository("bookingFile")
public class BookingDaoFileImpl implements com.brianrook.availability.dao.BookingDao
{

   private static final String BOOKING_ELEMENT = "reservations";
   private static final String BOOKING_CAMPSITE_FIELD = "campsiteId";
   private static final String BOOKING_STARTDATE_FIELD = "startDate";
   private static final String BOOKING_ENDDATE_FIELD = "endDate";
   private static final String TEST_DATA = "test-case.json";


   private Map<Integer, Map<DateTime, Boolean>> campsiteBookings = new HashMap<>();

   @Override
   public void addBooking(int campsiteId, Interval bookingDates)
   {
      int days = Days.daysBetween(bookingDates.getStart().toLocalDate(),
            bookingDates.getEnd().toLocalDate()).getDays();
      for (int i = 0; i <= days; i++)
      {
         if (campsiteBookings.get(campsiteId)==null)
         {
            //initialize the campsite entry
            campsiteBookings.put(campsiteId, new HashMap<DateTime, Boolean>());
         }
         campsiteBookings.get(campsiteId).put(
               bookingDates.getStart().plusDays(i), true);
      }
   }

   @Override
   public Map<DateTime, Boolean> getBookingsForCampsite(int campsiteId)
   {
      if (null==campsiteBookings.get(campsiteId))
      {
         //if we get a campsite that is not initialized, initialize it
         campsiteBookings.put(campsiteId, new HashMap<DateTime, Boolean>());
      }
      return campsiteBookings.get(campsiteId);
   }


   @PostConstruct
   public void loadData() throws IOException
   {
      ObjectMapper mapper = new ObjectMapper();

      ClassLoader classLoader = getClass().getClassLoader();
      File testFile = new File(classLoader.getResource(TEST_DATA).getFile());
      JsonNode root = mapper.readTree(testFile);

      JsonNode reservations = root.get(BOOKING_ELEMENT);


      if (reservations.isArray())
      {
         for (final JsonNode objNode : reservations)
         {
            int campsiteId = objNode.get(BOOKING_CAMPSITE_FIELD).intValue();

            String startDateStr = objNode.get(BOOKING_STARTDATE_FIELD).textValue();
            DateTime startDate = ISODateTimeFormat.date().parseDateTime(startDateStr);

            String endDateStr = objNode.get(BOOKING_ENDDATE_FIELD).textValue();
            DateTime endDate = ISODateTimeFormat.date().parseDateTime(endDateStr);

            addBooking(campsiteId, new Interval(startDate, endDate));

         }
      }
   }

}
