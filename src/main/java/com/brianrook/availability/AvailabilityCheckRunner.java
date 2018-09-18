package com.brianrook.availability;

import com.brianrook.availability.data.Campsite;
import com.brianrook.availability.data.DataLoad;
import com.brianrook.availability.service.GapCheck;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class AvailabilityCheckRunner
{
   private final static Logger LOG = LoggerFactory.getLogger(AvailabilityCheckRunner.class);
   @Autowired
   GapCheck gapCheck;

   public static void main(String[] args)
   {
      try
      {
         LOG.info("starting up availability check");
         //get the query
         DataLoad data = new DataLoad();
         Interval query = data.getQuery();
         LOG.info("query : {}", query);

         //load up spring
         ApplicationContext ctx =
               new AnnotationConfigApplicationContext(AvailabilityCheckConfiguration.class); // Use annotated beans from the specified package

         //instantiate this bean
         AvailabilityCheckRunner main = ctx.getBean(AvailabilityCheckRunner.class);
         Set<Campsite> availableSites = main.gapCheck.checkAvailability(query);
         LOG.info("got available campsites : {}", availableSites);
         for (Campsite thisSite : availableSites)
         {
            System.out.println(thisSite.getName());
         }
      }
      catch (IOException e)
      {
         LOG.error("unable to load test data set, error: {}", e.getMessage(), e);
         System.out.println("unable to load test data set");
         System.exit(1);
      }
   }
}
