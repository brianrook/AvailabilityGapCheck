package com.brianrook.availability.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.format.ISODateTimeFormat;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataLoad
{
   private static final String CAMPSITE_ELEMENT = "campsites";
   private static final String CAMPSITE_ID_FIELD = "id";
   private static final String CAMPSITE_NAME_FIELD = "name";

   private static final String BOOKING_ELEMENT = "reservations";
   private static final String BOOKING_CAMPSITE_FIELD = "campsiteId";
   private static final String BOOKING_STARTDATE_FIELD = "startDate";
   private static final String BOOKING_ENDDATE_FIELD = "endDate";

   private static final String SEARCH_ELEMENT = "search";
   private static final String SEARCH_STARTDATE_FIELD = "startDate";
   private static final String SEARCH_ENDDATE_FIELD = "endDate";


   public static final String BOOKING_DATE_FORMAT = "yyyy-MM-dd";


   public Map<Integer, Campsite> loadCampsites(JsonNode rootNode) throws IOException
   {

      JsonNode campsiteNode = rootNode.get(CAMPSITE_ELEMENT);

      Map<Integer, Campsite> campsiteMap = new HashMap();

      if (campsiteNode.isArray())
      {
         for (final JsonNode objNode : campsiteNode)
         {
            Campsite thisCampsite = new Campsite();
            thisCampsite.setId(objNode.get(CAMPSITE_ID_FIELD).intValue());
            thisCampsite.setName(objNode.get(CAMPSITE_NAME_FIELD).toString());
            campsiteMap.put(thisCampsite.getId(), thisCampsite);
         }
      }
      return campsiteMap;
   }

   public Map<Integer, Campsite> loadBookings() throws IOException
   {
      ObjectMapper mapper = new ObjectMapper();

      ClassLoader classLoader = getClass().getClassLoader();
      File testFile = new File(classLoader.getResource("test-case.json").getFile());
      JsonNode root = mapper.readTree(testFile);

      Map<Integer, Campsite> campsites = loadCampsites(root);

      JsonNode reservations = root.get(BOOKING_ELEMENT);


      if (reservations.isArray())
      {
         for (final JsonNode objNode : reservations)
         {
            int campsiteId = objNode.get(BOOKING_CAMPSITE_FIELD).intValue();
            Campsite thisSite = campsites.get(campsiteId);
            String startDateStr = objNode.get(BOOKING_STARTDATE_FIELD).textValue();
            DateTime startDate = ISODateTimeFormat.date().parseDateTime(startDateStr);

            String endDateStr = objNode.get(BOOKING_ENDDATE_FIELD).textValue();
            DateTime endDate = ISODateTimeFormat.date().parseDateTime(endDateStr);
            int days = Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate()).getDays();
            for (int i=0; i<=days; i++)
            {
               thisSite.getCalendar().put(startDate.plusDays(i), true);
            }
         }
      }
      return campsites;
   }

   public Interval getQuery() throws IOException
   {
      ObjectMapper mapper = new ObjectMapper();

      ClassLoader classLoader = getClass().getClassLoader();
      File testFile = new File(classLoader.getResource("test-case.json").getFile());
      JsonNode root = mapper.readTree(testFile);

      JsonNode queryElement = root.get(SEARCH_ELEMENT);
      String startDateStr = queryElement.get(BOOKING_STARTDATE_FIELD).textValue();
      DateTime startDate = ISODateTimeFormat.date().parseDateTime(startDateStr);

      String endDateStr = queryElement.get(BOOKING_ENDDATE_FIELD).textValue();
      DateTime endDate = ISODateTimeFormat.date().parseDateTime(endDateStr);

      return new Interval(startDate, endDate);
   }
}
