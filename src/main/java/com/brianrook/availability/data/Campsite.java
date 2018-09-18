package com.brianrook.availability.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to store campsite information, including calendar and availability configuration
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Campsite
{
   private int id;
   private String name;
   private int gapAllowed = 1;

   private Map<DateTime, Boolean> calendar = new HashMap<>();

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

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "Campsite{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", gapAllowed=" + gapAllowed +
            ", calendar=" + calendar +
            '}';
   }
}
