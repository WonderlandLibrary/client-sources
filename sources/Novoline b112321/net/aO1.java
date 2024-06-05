package net;

import net.aOG;
import net.acE;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class aO1 {
   public static double[] a(NoiseGeneratorOctaves var0, double[] var1, int var2, int var3, int var4, int var5, double var6, double var8, double var10) {
      return var0.generateNoiseOctaves(var1, var2, var3, var4, var5, var6, var8, var10);
   }

   public static double[] a(NoiseGeneratorOctaves var0, double[] var1, int var2, int var3, int var4, int var5, int var6, int var7, double var8, double var10, double var12) {
      acE[] var14 = aOG.b();
      double[] var10000 = var0.a(var1, var2, var3, var4, var5, var6, var7, var8, var10, var12);
      if(acE.b() == null) {
         aOG.b(new acE[5]);
      }

      return var10000;
   }
}
