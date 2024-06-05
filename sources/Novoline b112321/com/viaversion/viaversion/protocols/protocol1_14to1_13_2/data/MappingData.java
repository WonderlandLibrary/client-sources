package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.aiL;
import net.amx;

public class MappingData extends aiL {
   private IntSet b;
   private IntSet m;

   public MappingData() {
      super("1.13.2", "1.14");
   }

   public void a(JsonObject var1, JsonObject var2, JsonObject var3) {
      amx.a();
      JsonObject var5 = var2.getAsJsonObject("blockstates");
      HashMap var6 = new HashMap(var5.entrySet().size());
      Iterator var7 = var5.entrySet().iterator();
      if(var7.hasNext()) {
         Entry var8 = (Entry)var7.next();
         var6.put(((JsonElement)var8.getValue()).getAsString(), Integer.valueOf(Integer.parseInt((String)var8.getKey())));
      }

      JsonObject var13 = MappingDataLoader.b("heightMapData-1.14.json");
      JsonArray var14 = var13.getAsJsonArray("MOTION_BLOCKING");
      this.b = new IntOpenHashSet(var14.size(), 1.0F);
      Iterator var9 = var14.iterator();
      if(var9.hasNext()) {
         JsonElement var10 = (JsonElement)var9.next();
         String var11 = var10.getAsString();
         Integer var12 = (Integer)var6.get(var11);
         if(var12 == null) {
            Via.d().getLogger().warning("Unknown blockstate " + var11 + " :(");
         }

         this.b.add(var12.intValue());
      }

      if(Via.c().x()) {
         this.m = new IntOpenHashSet(1611, 1.0F);
         var9 = var1.getAsJsonObject("blockstates").entrySet().iterator();
         if(var9.hasNext()) {
            Entry var18 = (Entry)var9.next();
            String var19 = ((JsonElement)var18.getValue()).getAsString();
            if(var19.contains("_slab") || var19.contains("_stairs") || var19.contains("_wall[")) {
               this.m.add(this.d.a(Integer.parseInt((String)var18.getKey())));
            }
         }

         this.m.add(this.d.a(8163));
         int var16 = 3060;
         if(var16 <= 3067) {
            this.m.add(this.d.a(var16));
            ++var16;
         }
      }

   }

   public IntSet a() {
      return this.b;
   }

   public IntSet b() {
      return this.m;
   }
}
