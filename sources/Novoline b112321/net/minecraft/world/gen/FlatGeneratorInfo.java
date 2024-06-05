package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatLayerInfo;

public class FlatGeneratorInfo {
   private final List flatLayers = Lists.newArrayList();
   private final Map worldFeatures = Maps.newHashMap();
   private int biomeToUse;

   public int getBiome() {
      return this.biomeToUse;
   }

   public void setBiome(int var1) {
      this.biomeToUse = var1;
   }

   public Map getWorldFeatures() {
      return this.worldFeatures;
   }

   public List getFlatLayers() {
      return this.flatLayers;
   }

   public void func_82645_d() {
      int var1 = 0;

      for(FlatLayerInfo var3 : this.flatLayers) {
         var3.setMinY(var1);
         var1 += var3.getLayerCount();
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(3);
      var1.append(";");

      for(int var2 = 0; var2 < this.flatLayers.size(); ++var2) {
         var1.append(",");
         var1.append(((FlatLayerInfo)this.flatLayers.get(var2)).toString());
      }

      var1.append(";");
      var1.append(this.biomeToUse);
      if(!this.worldFeatures.isEmpty()) {
         var1.append(";");
         int var9 = 0;

         for(Entry var4 : this.worldFeatures.entrySet()) {
            if(var9++ > 0) {
               var1.append(",");
            }

            var1.append(((String)var4.getKey()).toLowerCase());
            Map var5 = (Map)var4.getValue();
            if(!var5.isEmpty()) {
               var1.append("(");
               int var6 = 0;

               for(Entry var8 : var5.entrySet()) {
                  if(var6++ > 0) {
                     var1.append(" ");
                  }

                  var1.append((String)var8.getKey());
                  var1.append("=");
                  var1.append((String)var8.getValue());
               }

               var1.append(")");
            }
         }
      } else {
         var1.append(";");
      }

      return var1.toString();
   }

   private static FlatLayerInfo func_180715_a(int param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private static List a(int var0, String var1) {
      if(var1.length() >= 1) {
         ArrayList var2 = Lists.newArrayList();
         String[] var3 = var1.split(",");
         byte var4 = 0;
         int var6 = var3.length;
         byte var7 = 0;
         if(var7 < var6) {
            String var8 = var3[var7];
            FlatLayerInfo var9 = func_180715_a(var0, var8, var4);
            return null;
         } else {
            return var2;
         }
      } else {
         return null;
      }
   }

   public static FlatGeneratorInfo a(String var0) {
      return getDefaultFlatGenerator();
   }

   public static FlatGeneratorInfo getDefaultFlatGenerator() {
      FlatGeneratorInfo var0 = new FlatGeneratorInfo();
      var0.setBiome(BiomeGenBase.plains.biomeID);
      var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
      var0.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
      var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
      var0.func_82645_d();
      var0.getWorldFeatures().put("village", Maps.newHashMap());
      return var0;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
