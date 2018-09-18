package com.brianrook.availability.dao.impl;

import com.brianrook.availability.dao.CampsiteDao;
import com.brianrook.availability.data.Campsite;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository("campsiteFile")
public class CampsiteDaoFileImpl implements CampsiteDao
{
   private static final String CAMPSITE_ELEMENT = "campsites";
   private static final String CAMPSITE_ID_FIELD = "id";
   private static final String CAMPSITE_NAME_FIELD = "name";

   private static final String TEST_FILE = "/test-case.json";

   private Map<Integer, Campsite> campsites = new HashMap<>();

   @Override
   public Campsite getCampsite(int campsiteId)
   {
      return campsites.get(campsiteId);
   }

   @Override
   public Set<Campsite> getAllCampsites()
   {
      Set<Campsite> campsiteSet = new HashSet<>();
      campsiteSet.addAll(campsites.values());
      return campsiteSet;
   }

   @PostConstruct
   public void loadCampsites() throws IOException
   {

      ObjectMapper mapper = new ObjectMapper();

      InputStream in = getClass().getResourceAsStream(TEST_FILE);
      JsonNode rootNode = mapper.readTree(in);

      JsonNode campsiteNode = rootNode.get(CAMPSITE_ELEMENT);

      Map<Integer, Campsite> campsiteMap = new HashMap();

      if (campsiteNode.isArray())
      {
         for (final JsonNode objNode : campsiteNode)
         {
            Campsite thisCampsite = new Campsite();
            thisCampsite.setId(objNode.get(CAMPSITE_ID_FIELD).intValue());
            thisCampsite.setName(objNode.get(CAMPSITE_NAME_FIELD).textValue());
            campsites.put(thisCampsite.getId(), thisCampsite);
         }
      }
   }
}
