package net;

import java.util.List;
import java.util.Map;
import net.minecraft.world.gen.FlatGeneratorInfo;

public class a4f {
   public static FlatGeneratorInfo a(String var0) {
      return FlatGeneratorInfo.a(var0);
   }

   public static int a(FlatGeneratorInfo var0) {
      return var0.getBiome();
   }

   public static FlatGeneratorInfo a() {
      return FlatGeneratorInfo.getDefaultFlatGenerator();
   }

   public static void b(FlatGeneratorInfo var0) {
      var0.func_82645_d();
   }

   public static List d(FlatGeneratorInfo var0) {
      return var0.getFlatLayers();
   }

   public static Map c(FlatGeneratorInfo var0) {
      return var0.getWorldFeatures();
   }

   public static void a(FlatGeneratorInfo var0, int var1) {
      var0.setBiome(var1);
   }
}
