package com.brianrook.availability.dao.impl

import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class BookingDaoFileImplSpec extends Specification
{
   @Shared
   BookingDaoFileImpl bookingDaoFile = new BookingDaoFileImpl()

   def setup() {
      bookingDaoFile.loadData()
   }

   @Unroll
   def "test bookings for campsite #testName"()
   {
      when:
      Map<DateTime, Boolean> campsiteList = bookingDaoFile.getBookingsForCampsite(campsiteId)

      then:
      assert campsiteList.size()==size

      where:
      testName             |campsiteId                |size
      1                    |1                         |6
      2                    |2                         |6
      3                    |3                         |4
      4                    |4                         |4
      5                    |5                         |0
   }
}
