package net.minecraft.client.resources;

import com.google.gson.JsonParseException;
import java.io.File;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map resourceMap;

   public ResourceIndex(File param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public Map getResourceMap() {
      return this.resourceMap;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
