package com.brianrook.availability;

import com.brianrook.availability.data.Campsite;
import com.brianrook.availability.data.DataLoad;
import com.brianrook.availability.service.impl.GapCheck;
import org.joda.time.Interval;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class RunGapCheck
{

   public static void main(String[] args)
   {
      try
      {
         DataLoad data = new DataLoad();

         Map<Integer, Campsite> campsites = data.loadBookings();

         Interval query = data.getQuery();

         GapCheck gapCheck = new GapCheck();
         Set<Campsite> availableSites = gapCheck.checkAvailability(campsites, query);
         for (Campsite thisSite : availableSites)
         {
            System.out.println(thisSite.getName());
         }
      }
      catch (IOException e)
      {
         System.out.println("unable to load test data set");
         System.exit(1);
      }
   }
}
