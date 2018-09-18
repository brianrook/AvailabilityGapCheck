package com.brianrook.availability.service.impl

import org.joda.time.DateTime
import spock.lang.Specification
import spock.lang.Unroll

class GapCheckSpec extends Specification
{

   def "test is available success"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), true)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def available = gapCheck.isBooked(testBookings, DateTime.now().withTimeAtStartOfDay().minusDays(2), 1)

      then:

      assert available == true

   }

   def "test is available failure"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), true)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def isBooked = gapCheck.isBooked(testBookings, DateTime.now().withTimeAtStartOfDay().minusDays(3), 1)

      then:

      assert isBooked == true

   }
   def "test is available failure at end"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), true)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def isBooked = gapCheck.isBooked(testBookings, DateTime.now().withTimeAtStartOfDay().minusDays(2), 1)

      then:

      assert isBooked == true

   }

   def "test is available failure at end goes over"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), true)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def isBooked = gapCheck.isBooked(testBookings, DateTime.now().withTimeAtStartOfDay().minusDays(2), 2)

      then:

      assert isBooked == true

   }

   def "test that start gap works on abut"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), true)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def available = gapCheck.goodStartGap(testBookings, DateTime.now().withTimeAtStartOfDay(), 2)

      then:

      assert available == true


   }

   @Unroll
   def "test that start gap works on with #desc"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().minusDays(1), false)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def available = gapCheck.goodStartGap(testBookings, DateTime.now().withTimeAtStartOfDay(), gapConfig)

      then:

      assert available == outcome

      where:
      desc              |gapConfig              |outcome
      "abut"            |1                      |true
      "gap 2"           |2                      |true
      "gap 3"           |3                      |false
      "gap 4"           |4                      |false
   }

   @Unroll
   def "test that end gap works on with #desc"()
   {
      given:
      Map<DateTime, Boolean> testBookings = new HashMap<>()
      testBookings.put(DateTime.now().withTimeAtStartOfDay().plusDays(3), true)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().plusDays(2), false)
      testBookings.put(DateTime.now().withTimeAtStartOfDay().plusDays(1), false)

      AvailabilityGapCheck gapCheck = new AvailabilityGapCheck()

      when:
      def available = gapCheck.goodEndGap(testBookings, DateTime.now().withTimeAtStartOfDay(), gapConfig)

      then:

      assert available == outcome

      where:
      desc              |gapConfig              |outcome
      "abut"            |1                      |true
      //since the booking is at the end of the day, we are actually covering day 1 here.
      "gap 2"           |2                      |false
      "gap 3"           |3                      |false
      "gap 4"           |4                      |false
   }


}
