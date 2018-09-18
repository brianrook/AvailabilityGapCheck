package com.brianrook.availability.dao.impl

import com.brianrook.availability.data.Campsite
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CampsiteDaoFileImplSpec extends Specification
{

   @Shared
   CampsiteDaoFileImpl campsiteDaoFile = new CampsiteDaoFileImpl()

   def setup() {
      campsiteDaoFile.loadCampsites()
   }

   def "test that it was initialized correctly"()
   {
      when:
      Set<Campsite> campsites = campsiteDaoFile.allCampsites

      then:
      assert campsites.size()==5
   }


   @Unroll
   def "test get one campsite : #testName"()
   {
      when:
      Campsite campsites = campsiteDaoFile.getCampsite(campsiteId)

      then:
      assert campsites.name==campsiteName

      where:
      testName          |campsiteId                |campsiteName
      '1'               |1                         |'Cozy Cabin'
      '2'               |2                         |'Comfy Cabin'
      '3'               |3                         |'Rustic Cabin'
      '4'               |4                         |'Rickety Cabin'
      '5'               |5                         |'Cabin in the Woods'
   }

}
