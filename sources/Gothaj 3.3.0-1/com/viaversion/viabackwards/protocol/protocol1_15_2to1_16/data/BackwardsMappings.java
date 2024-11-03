package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Map<String, String> attributeMappings = new HashMap<>();

   public BackwardsMappings() {
      super("1.16", "1.15", Protocol1_16To1_15_2.class);
   }

   @Override
   protected void loadExtras(CompoundTag data) {
      super.loadExtras(data);

      for (Entry<String, String> entry : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().entrySet()) {
         this.attributeMappings.put(entry.getValue(), entry.getKey());
      }
   }

   public Map<String, String> getAttributeMappings() {
      return this.attributeMappings;
   }
}
