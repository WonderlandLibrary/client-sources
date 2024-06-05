package com.viaversion.viaversion.api.data;

import com.google.gson.JsonArray;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.M2;

public class ParticleMappings {
   private final M2 b;
   private final int blockId;
   private final int fallingDustId;
   private final int itemId;

   public ParticleMappings(JsonArray var1, M2 var2) {
      this.b = var2;
      Object2IntMap var3 = MappingDataLoader.a(var1);
      this.blockId = var3.getInt("block");
      this.fallingDustId = var3.getInt("falling_dust");
      this.itemId = var3.getInt("item");
   }

   public M2 a() {
      return this.b;
   }

   public int getBlockId() {
      return this.blockId;
   }

   public int getFallingDustId() {
      return this.fallingDustId;
   }

   public int getItemId() {
      return this.itemId;
   }
}
