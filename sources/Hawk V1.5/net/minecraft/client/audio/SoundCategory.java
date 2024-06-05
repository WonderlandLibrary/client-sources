package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
   WEATHER("WEATHER", 3, "weather", 3);

   private final int categoryId;
   RECORDS("RECORDS", 2, "record", 2);

   private static final Map field_147169_k = Maps.newHashMap();
   AMBIENT("AMBIENT", 8, "ambient", 8),
   ANIMALS("ANIMALS", 6, "neutral", 6);

   private static final SoundCategory[] $VALUES = new SoundCategory[]{MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, MOBS, ANIMALS, PLAYERS, AMBIENT};
   MOBS("MOBS", 5, "hostile", 5),
   BLOCKS("BLOCKS", 4, "block", 4),
   MASTER("MASTER", 0, "master", 0),
   PLAYERS("PLAYERS", 7, "player", 7);

   private static final SoundCategory[] ENUM$VALUES = new SoundCategory[]{MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, MOBS, ANIMALS, PLAYERS, AMBIENT};
   private static final String __OBFID = "CL_00001686";
   private static final Map field_147168_j = Maps.newHashMap();
   MUSIC("MUSIC", 1, "music", 1);

   private final String categoryName;

   public static SoundCategory func_147154_a(String var0) {
      return (SoundCategory)field_147168_j.get(var0);
   }

   private SoundCategory(String var3, int var4, String var5, int var6) {
      this.categoryName = var5;
      this.categoryId = var6;
   }

   static {
      SoundCategory[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         SoundCategory var3 = var0[var2];
         if (field_147168_j.containsKey(var3.getCategoryName()) || field_147169_k.containsKey(var3.getCategoryId())) {
            throw new Error(String.valueOf((new StringBuilder("Clash in Sound Category ID & Name pools! Cannot insert ")).append(var3)));
         }

         field_147168_j.put(var3.getCategoryName(), var3);
         field_147169_k.put(var3.getCategoryId(), var3);
      }

   }

   public int getCategoryId() {
      return this.categoryId;
   }

   public String getCategoryName() {
      return this.categoryName;
   }
}
