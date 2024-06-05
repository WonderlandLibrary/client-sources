package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
   MASTER("master", 0),
   MUSIC("music", 1),
   RECORDS("record", 2),
   WEATHER("weather", 3),
   BLOCKS("block", 4),
   MOBS("hostile", 5),
   ANIMALS("neutral", 6),
   PLAYERS("player", 7),
   AMBIENT("ambient", 8);

   private static final Map NAME_CATEGORY_MAP = Maps.newHashMap();
   private static final Map ID_CATEGORY_MAP = Maps.newHashMap();
   private final String categoryName;
   private final int categoryId;

   private SoundCategory(String var3, int var4) {
      this.categoryName = var3;
      this.categoryId = var4;
   }

   public String getCategoryName() {
      return this.categoryName;
   }

   public int getCategoryId() {
      return this.categoryId;
   }

   public static SoundCategory getCategory(String var0) {
      return (SoundCategory)NAME_CATEGORY_MAP.get(var0);
   }

   static {
      for(SoundCategory var11 : values()) {
         if(NAME_CATEGORY_MAP.containsKey(var11.getCategoryName()) || ID_CATEGORY_MAP.containsKey(Integer.valueOf(var11.getCategoryId()))) {
            throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var11);
         }

         NAME_CATEGORY_MAP.put(var11.getCategoryName(), var11);
         ID_CATEGORY_MAP.put(Integer.valueOf(var11.getCategoryId()), var11);
      }

   }
}
