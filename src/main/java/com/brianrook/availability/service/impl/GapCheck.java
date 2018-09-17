package com.brianrook.availability.service.impl;

import com.brianrook.availability.data.Campsite;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.time.YearMonth;
import java.util.Map;

public class GapCheck
{

   public boolean isAvailable(Campsite campsite, Interval availabilityQuery)
   {
      int gapConfig = campsite.getGapAllowed();

      //find start time and duration
      DateTime startDate = availabilityQuery.getStart();
      Duration queryDuration = new Duration(availabilityQuery);
      int durationDays = queryDuration.toStandardDays().getDays();

      //is the time already booked
      if (isBooked(campsite.getCalendar(), startDate, durationDays))
      {
         return false;
      }

      //check the gap
      if (goodStartGap(campsite.getCalendar(), startDate, gapConfig) && goodEndGap(campsite.getCalendar(), availabilityQuery.getEnd(), gapConfig))
      {
         return true;
      }
      else
      {
         return false;
      }

   }

   private boolean goodStartGap(Map<DateTime,Boolean> calendar, DateTime startDate, int gapConfig)
   {
      if (calendar.get(startDate.minusDays(1))!=null || calendar.get(startDate.minusDays(1))==true)
      {
         //the query abuts an existing booking at the end
         return true;
      }
      else
      {
         //check the gap, start at 2 because we already checked 1
         for (int i=2; i<=gapConfig; i++)
         {
            DateTime checkStart = startDate.minusDays(i);
            if (calendar.get(checkStart)!=null || calendar.get(checkStart)==true)
            {
               //we have a booking in the gap, return false
               return false;
            }
         }
      }
      return true;
   }

   private boolean goodEndGap(Map<DateTime,Boolean> calendar, DateTime endTime, int gapConfig)
   {
      if (calendar.get(endTime.plusDays(1))!=null || calendar.get(endTime.plusDays(1))==true)
      {
         //the query abuts an existing booking at the end
         return true;
      }
      else
      {
         //check the gap, start at 2 because we already checked 1
         for (int i=2; i<=gapConfig; i++)
         {
            DateTime checkEnd = endTime.plusDays(i);
            if (calendar.get(checkEnd)!=null || calendar.get(checkEnd)==true)
            {
               //we have a booking in the gap, return false
               return false;
            }
         }
      }
      return true;
   }


   private boolean isBooked(Map<DateTime, Boolean> bookings, DateTime startDate, int durationDays)
   {
      for (int i=0; i<=durationDays; i++)
      {
         if (bookings.get(startDate.plusDays(i)) == true)
         {
            //if any days are booked, we are not available
            return true;
         }
      }
      return false;
   }

}
