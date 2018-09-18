package com.brianrook.availability.service.impl;

import com.brianrook.availability.RunGapCheck;
import com.brianrook.availability.dao.BookingDao;
import com.brianrook.availability.dao.CampsiteDao;
import com.brianrook.availability.data.Campsite;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AvailabilityGapCheck implements com.brianrook.availability.service.GapCheck
{
   private final static Logger LOG = LoggerFactory.getLogger(RunGapCheck.class);

   @Autowired
   @Qualifier("campsiteFile")
   CampsiteDao campsiteDao;

   @Autowired
   @Qualifier("bookingFile")
   BookingDao bookingDao;

   @Override
   public Set<Campsite> checkAvailability(Interval query)
   {
      Set<Campsite> campsites = campsiteDao.getAllCampsites();
      Set<Campsite> resultSet = new HashSet<>();
      LOG.debug("got campsites : {}", resultSet);
      for (Campsite thisSite : campsites)
      {
         if (isAvailable(thisSite, query))
         {
            LOG.info("got available site {} for query {}", thisSite, query);
            resultSet.add(thisSite);
         }
      }

      LOG.info("all available campsites : {}", resultSet);
      return resultSet;
   }
   @Override
   public boolean isAvailable(Campsite campsite, Interval availabilityQuery)
   {
      int gapConfig = campsite.getGapAllowed();
      LOG.debug("cap config for {} is {}", campsite, gapConfig);

      Map<DateTime,Boolean> campsiteBookings =
            bookingDao.getBookingsForCampsite(campsite.getId());
      LOG.debug("got bookings : {}", campsiteBookings );

      //find start time and duration
      DateTime startDate = availabilityQuery.getStart();
      Duration queryDuration = new Duration(availabilityQuery);
      int durationDays = queryDuration.toStandardDays().getDays();
      LOG.info("booking duration is {} days", durationDays);

      //is the time already booked
      if (isBooked(campsiteBookings, startDate, durationDays))
      {
         LOG.info("this campsite {} has been booked for the days in question.  startDate {}, duration {}", campsite, startDate, durationDays);
         return false;
      }

      //check the gap
      if (goodStartGap(campsiteBookings, startDate, gapConfig) &&
            goodEndGap(campsiteBookings, availabilityQuery.getEnd(), gapConfig))
      {
         LOG.info("good gap check for campsite {}, it is available", campsite);
         return true;
      }
      else
      {
         LOG.info("this campsite {} failed the gap check", campsite);
         return false;
      }

   }

   private boolean goodStartGap(Map<DateTime,Boolean> calendar, DateTime startDate, int gapConfig)
   {
      if (calendar.get(startDate.minusDays(1))!=null && calendar.get(startDate.minusDays(1))==true)
      {
         LOG.debug("this start time abuts and existing booking");
         //the query abuts an existing booking at the end
         return true;
      }
      else
      {
         boolean goodGap = true;
         //check the gap, start at 2 because we already checked 1
         for (int i=2; i<=gapConfig; i++)
         {
            DateTime checkStart = startDate.minusDays(i);
            if (calendar.get(checkStart)!=null && calendar.get(checkStart)==true)
            {
               LOG.debug("we have a booking in the gap, return false");
               goodGap = false;
            }
         }
         LOG.info("returning start gap check {}", goodGap);
         return goodGap;
      }

   }

   private boolean goodEndGap(Map<DateTime,Boolean> calendar, DateTime endTime, int gapConfig)
   {
      if (calendar.get(endTime.plusDays(1))!=null && calendar.get(endTime.plusDays(1))==true)
      {
         LOG.debug("this end time abuts and existing booking");
         //the query abuts an existing booking at the end
         return true;
      }
      else
      {
         boolean goodGap=true;
         //check the gap, start at 2 because we already checked 1
         for (int i=2; i<=gapConfig+1; i++)
         {
            DateTime checkEnd = endTime.plusDays(i);
            if (calendar.get(checkEnd)!=null && calendar.get(checkEnd)==true)
            {
               LOG.debug("we have a booking in the gap, return false");
               //we have a booking in the gap, return false
               goodGap = false;
            }
         }
         LOG.info("returning end gap check {}", goodGap);
         return goodGap;
      }
   }


   private boolean isBooked(Map<DateTime, Boolean> bookings, DateTime startDate, int durationDays)
   {
      boolean isBooked = false;
      for (int i=0; i<=durationDays; i++)
      {
         DateTime checkDate = startDate.plusDays(i);
         if (bookings!=null && bookings.get(checkDate)!=null && (bookings.get(checkDate) == true))
         {
            //if any days are booked, we are not available
            isBooked = true;
         }
         LOG.debug("checked date: {}, result is {}", checkDate, isBooked);
      }
      return isBooked;
   }


}
