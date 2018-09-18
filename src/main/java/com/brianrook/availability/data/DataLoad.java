package com.brianrook.availability.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.ISODateTimeFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DataLoad
{
   private static final String SEARCH_ELEMENT = "search";
   private static final String SEARCH_STARTDATE_FIELD = "startDate";
   private static final String SEARCH_ENDDATE_FIELD = "endDate";

   private static final String TEST_FILE = "/test-case.json";


   public Interval getQuery() throws IOException
   {
      ObjectMapper mapper = new ObjectMapper();

      InputStream in = getClass().getResourceAsStream(TEST_FILE);
      JsonNode root = mapper.readTree(in);

      JsonNode queryElement = root.get(SEARCH_ELEMENT);
      String startDateStr = queryElement.get(SEARCH_STARTDATE_FIELD).textValue();
      DateTime startDate = ISODateTimeFormat.date().parseDateTime(startDateStr);

      String endDateStr = queryElement.get(SEARCH_ENDDATE_FIELD).textValue();
      DateTime endDate = ISODateTimeFormat.date().parseDateTime(endDateStr);

      return new Interval(startDate, endDate);
   }
}
