package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Int2ObjectMap<String> statisticMappings = new Int2ObjectOpenHashMap<>();
   private final Map<String, String> translateMappings = new HashMap<>();

   public BackwardsMappings() {
      super("1.13", "1.12", Protocol1_13To1_12_2.class);
   }

   @Override
   public void loadExtras(CompoundTag data) {
      super.loadExtras(data);

      for (Entry<String, Integer> entry : StatisticMappings.CUSTOM_STATS.entrySet()) {
         this.statisticMappings.put(entry.getValue().intValue(), entry.getKey());
      }

      for (Entry<String, String> entry : Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet()) {
         this.translateMappings.put(entry.getValue(), entry.getKey());
      }
   }

   @Override
   public int getNewBlockStateId(int id) {
      if (id >= 5635 && id <= 5650) {
         if (id < 5639) {
            id += 4;
         } else if (id < 5643) {
            id -= 4;
         } else if (id < 5647) {
            id += 4;
         } else {
            id -= 4;
         }
      }

      int mappedId = super.getNewBlockStateId(id);
      switch (mappedId) {
         case 1595:
         case 1596:
         case 1597:
            return 1584;
         case 1598:
         case 1599:
         case 1600:
         case 1601:
         case 1602:
         case 1603:
         case 1604:
         case 1605:
         case 1606:
         case 1607:
         case 1608:
         case 1609:
         case 1610:
         default:
            return mappedId;
         case 1611:
         case 1612:
         case 1613:
            return 1600;
      }
   }

   @Override
   protected int checkValidity(int id, int mappedId, String type) {
      return mappedId;
   }

   public Int2ObjectMap<String> getStatisticMappings() {
      return this.statisticMappings;
   }

   public Map<String, String> getTranslateMappings() {
      return this.translateMappings;
   }
}
