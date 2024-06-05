package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.google.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.cV;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Map attributeMappings = new HashMap();

   public BackwardsMappings() {
      super("1.16", "1.15", Protocol1_16To1_15_2.class, true);
   }

   protected void a(JsonObject var1, JsonObject var2) {
      cV.b();
      Iterator var4 = Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         this.attributeMappings.put(var5.getValue(), var5.getKey());
      }

   }

   public Map getAttributeMappings() {
      return this.attributeMappings;
   }
}
