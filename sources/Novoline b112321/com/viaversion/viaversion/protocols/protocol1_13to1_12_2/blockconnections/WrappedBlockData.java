package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.abi;
import net.af3;

public class WrappedBlockData {
   private final String minecraftKey;
   private final int savedBlockStateId;
   private final LinkedHashMap blockData = new LinkedHashMap();

   public static WrappedBlockData fromString(String var0) {
      abi.b();
      String[] var2 = var0.split("\\[");
      String var3 = var2[0];
      WrappedBlockData var4 = new WrappedBlockData(var3, af3.a(var0));
      if(var2.length > 1) {
         String var5 = var2[1];
         var5 = var5.replace("]", "");
         String[] var6 = var5.split(",");
         int var8 = var6.length;
         int var9 = 0;
         if(var9 < var8) {
            String var10 = var6[var9];
            String[] var11 = var10.split("=");
            var4.blockData.put(var11[0], var11[1]);
            ++var9;
         }
      }

      return var4;
   }

   public static WrappedBlockData fromStateId(int var0) {
      abi.b();
      String var2 = af3.b(var0);
      if(var2 != null) {
         return fromString(var2);
      } else {
         Via.d().getLogger().info("Unable to get blockdata from " + var0);
         return fromString("minecraft:air");
      }
   }

   private WrappedBlockData(String var1, int var2) {
      this.minecraftKey = var1;
      this.savedBlockStateId = var2;
   }

   public String toString() {
      abi.b();
      StringBuilder var2 = new StringBuilder(this.minecraftKey + "[");
      Iterator var3 = this.blockData.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var2.append((String)var4.getKey()).append('=').append((String)var4.getValue()).append(',');
      }

      return var2.substring(0, var2.length() - 1) + "]";
   }

   public String getMinecraftKey() {
      return this.minecraftKey;
   }

   public int getSavedBlockStateId() {
      return this.savedBlockStateId;
   }

   public int getBlockStateId() {
      return af3.a(this.toString());
   }

   public WrappedBlockData set(String var1, Object var2) {
      PacketRemapper[] var3 = abi.b();
      if(!this.hasData(var1)) {
         throw new UnsupportedOperationException("No blockdata found for " + var1 + " at " + this.minecraftKey);
      } else {
         this.blockData.put(var1, var2.toString());
         return this;
      }
   }

   public String getValue(String var1) {
      return (String)this.blockData.get(var1);
   }

   public boolean hasData(String var1) {
      return this.blockData.containsKey(var1);
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
