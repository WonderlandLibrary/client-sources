package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.M2;
import net.Mz;
import net.VV;
import net.aRP;
import net.aci;
import org.jetbrains.annotations.Nullable;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Int2ObjectMap p = new Int2ObjectOpenHashMap();
   private final Map translateMappings = new HashMap();
   private M2 q;

   public BackwardsMappings() {
      super("1.13", "1.12", aRP.class, true);
   }

   public void a(JsonObject var1, JsonObject var2) {
      aci.b();
      this.q = new Mz(var1.getAsJsonObject("enchantments"), var2.getAsJsonObject("enchantments"), false);
      Iterator var4 = StatisticMappings.CUSTOM_STATS.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         this.p.put(((Integer)var5.getValue()).intValue(), var5.getKey());
      }

      var4 = aRP.m.getTranslateMapping().entrySet().iterator();
      if(var4.hasNext()) {
         Entry var7 = (Entry)var4.next();
         this.translateMappings.put(var7.getValue(), var7.getKey());
      }

   }

   private static void a(short[] var0, JsonObject var1, JsonObject var2, JsonObject var3) {
      aci.b();
      Object2IntMap var5 = MappingDataLoader.a(var2);

      for(Entry var7 : var1.entrySet()) {
         String var8 = ((JsonElement)var7.getValue()).getAsString();
         int var9 = var5.getInt(var8);
         short var10 = -1;
         if(var9 == -1) {
            JsonPrimitive var11 = var3.getAsJsonPrimitive(var8);
            int var12;
            if(var11 == null && (var12 = var8.indexOf(91)) != -1) {
               var11 = var3.getAsJsonPrimitive(var8.substring(0, var12));
            }

            if(var11 != null) {
               if(var11.getAsString().startsWith("id:")) {
                  String var13 = var11.getAsString().replace("id:", "");
                  var10 = Short.parseShort(var13);
                  var5.getInt(var2.getAsJsonPrimitive(var13).getAsString());
               }

               var9 = var5.getInt(var11.getAsString());
            }

            if(var9 == -1) {
               if(Via.c().j() && !Via.b().c()) {
                  continue;
               }

               VV.d().a().warning("No key for " + var7.getValue() + "/" + var11.getAsString() + " :( ");
               VV.d().a().warning("No key for " + var7.getValue() + " :( ");
            }
         }

         var0[Integer.parseInt((String)var7.getKey())] = var10 != -1?var10:(short)var9;
         break;
      }

   }

   @Nullable
   protected M2 a(JsonObject var1, JsonObject var2, @Nullable JsonObject var3, String var4) {
      String[] var5 = aci.b();
      if(var4.equals("blockstates")) {
         short[] var6 = new short[8582];
         Arrays.fill(var6, (short)-1);
         a(var6, var1.getAsJsonObject("blockstates"), var2.getAsJsonObject("blocks"), var3.getAsJsonObject("blockstates"));
         return new M2(var6);
      } else {
         return super.a(var1, var2, var3, var4);
      }
   }

   protected int checkValidity(int var1, int var2, String var3) {
      return var2;
   }

   public Int2ObjectMap b() {
      return this.p;
   }

   public Map getTranslateMappings() {
      return this.translateMappings;
   }

   public M2 a() {
      return this.q;
   }
}
