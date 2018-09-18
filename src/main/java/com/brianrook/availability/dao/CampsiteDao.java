package com.brianrook.availability.dao;

import com.brianrook.availability.data.Campsite;

import java.util.Set;

public interface CampsiteDao
{
   Campsite getCampsite(int campsiteId);

   Set<Campsite> getAllCampsites();
}
