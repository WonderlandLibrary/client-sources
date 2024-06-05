package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.M2;
import net.aiL;
import net.amt;
import org.jetbrains.annotations.Nullable;

public class MappingData extends aiL {
   private final Map blockTags = new HashMap();
   private final Map itemTags = new HashMap();
   private final Map fluidTags = new HashMap();
   private final BiMap oldEnchantmentsIds = HashBiMap.create();
   private final Map translateMapping = new HashMap();
   private final Map mojangTranslation = new HashMap();
   private final BiMap channelMappings = HashBiMap.create();
   private M2 q;

   public MappingData() {
      super("1.12", "1.13");
   }

   public void a(JsonObject param1, JsonObject param2, JsonObject param3) {
      // $FF: Couldn't be decompiled
   }

   protected M2 a(JsonObject var1, JsonObject var2, @Nullable JsonObject var3, String var4) {
      return var4.equals("blocks")?new M2(4084, var1.getAsJsonObject("blocks"), var2.getAsJsonObject("blockstates")):super.a(var1, var2, var3, var4);
   }

   public static String validateNewChannel(String var0) {
      boolean var1 = amt.c();
      if(!isValid1_13Channel(var0)) {
         return null;
      } else {
         int var2 = var0.indexOf(58);
         if((var2 == -1 || var2 == 0) && var0.length() <= 10) {
            var0 = "minecraft:" + var0;
         }

         return var0;
      }
   }

   public static boolean isValid1_13Channel(String var0) {
      return var0.matches("([0-9a-z_.-]+):([0-9a-z_/.-]+)");
   }

   private void a(Map var1, JsonObject var2) {
      amt.c();
      Iterator var4 = var2.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         JsonArray var6 = ((JsonElement)var5.getValue()).getAsJsonArray();
         Integer[] var7 = new Integer[var6.size()];
         int var8 = 0;
         if(var8 < var6.size()) {
            var7[var8] = Integer.valueOf(var6.get(var8).getAsInt());
            ++var8;
         }

         var1.put(var5.getKey(), var7);
      }

   }

   private void b(Map var1, JsonObject var2) {
      amt.b();
      Iterator var4 = var2.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         var1.put(Short.valueOf(Short.parseShort((String)var5.getKey())), ((JsonElement)var5.getValue()).getAsString());
      }

   }

   public Map getBlockTags() {
      return this.blockTags;
   }

   public Map getItemTags() {
      return this.itemTags;
   }

   public Map getFluidTags() {
      return this.fluidTags;
   }

   public BiMap getOldEnchantmentsIds() {
      return this.oldEnchantmentsIds;
   }

   public Map getTranslateMapping() {
      return this.translateMapping;
   }

   public Map getMojangTranslation() {
      return this.mojangTranslation;
   }

   public BiMap getChannelMappings() {
      return this.channelMappings;
   }

   public M2 e() {
      return this.q;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
