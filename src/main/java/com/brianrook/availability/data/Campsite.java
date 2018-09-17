package com.brianrook.availability.data;

import org.joda.time.DateTime;

import java.time.YearMonth;
import java.util.Map;

/**
 * Class used to store campsite information, including calendar and availability configuration
 */
public class Campsite
{
   private int gapAllowed;
   private Map<DateTime, Boolean> calendar;

   public int getGapAllowed()
   {
      return gapAllowed;
   }

   public void setGapAllowed(int gapAllowed)
   {
      this.gapAllowed = gapAllowed;
   }

   public Map<DateTime, Boolean> getCalendar()
   {
      return calendar;
   }

   public void setCalendar(Map<DateTime, Boolean> calendar)
   {
      this.calendar = calendar;
   }

   @Override
   public String toString()
   {
      return "Campsite{" +
            "gapAllowed=" + gapAllowed +
            ", calendar=" + calendar +
            '}';
   }
}
