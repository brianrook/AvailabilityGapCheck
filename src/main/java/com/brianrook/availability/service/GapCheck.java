package com.brianrook.availability.service;

import com.brianrook.availability.data.Campsite;
import org.joda.time.Interval;

import java.util.Set;

public interface GapCheck
{
   boolean isAvailable(Campsite campsite, Interval availabilityQuery);

   Set<Campsite> checkAvailability(Interval query);
}
