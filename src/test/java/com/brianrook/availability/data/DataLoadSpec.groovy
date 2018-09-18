package com.brianrook.availability.data

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.joda.time.Interval
import spock.lang.Specification

class DataLoadSpec extends Specification
{
//
//   def "load campsite data"()
//   {
//      given:
//      ObjectMapper mapper = new ObjectMapper();
//
//      ClassLoader classLoader = getClass().getClassLoader();
//      File testFile = new File(classLoader.getResource("test-case.json").getFile());
//      JsonNode root = mapper.readTree(testFile);
//
//      DataLoad dataload = new DataLoad()
//
//      when:
//      Map<Integer, Campsite> campsiteList = dataload.loadCampsites(root)
//
//      then:
//      assert campsiteList!=null
//      assert campsiteList.size()==5
//   }
//
//   def "load booking data"()
//   {
//      given:
//      DataLoad dataload = new DataLoad()
//
//      when:
//      Map<Integer, Campsite> campsiteList = dataload.loadBookings()
//
//      then:
//      assert campsiteList!=null
//      assert campsiteList.size()==5
//      assert campsiteList.get(1).calendar.size()==6
//      assert campsiteList.get(2).calendar.size()==6
//      assert campsiteList.get(3).calendar.size()==4
//      assert campsiteList.get(4).calendar.size()==4
//      assert campsiteList.get(5).calendar.isEmpty()==true
//   }
//
//   def "load query data"()
//   {
//      given:
//      DataLoad dataLoad = new DataLoad()
//
//      when:
//      Interval query = dataLoad.getQuery()
//
//      then:
//      assert query.getStart().getYear()==2018
//      assert query.getStart().getMonthOfYear()==6
//      assert query.getStart().getDayOfMonth()==4
//
//      assert query.getEnd().getYear()==2018
//      assert query.getEnd().getMonthOfYear()==6
//      assert query.getEnd().getDayOfMonth()==6
//   }
}
