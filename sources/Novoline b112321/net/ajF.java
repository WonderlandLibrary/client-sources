package net;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.optifine.RandomMobsProperties;

public class ajF {
   public static ResourceLocation a(RandomMobsProperties var0, ResourceLocation var1, EntityLiving var2) {
      return var0.getTextureLocation(var1, var2);
   }

   public static boolean a(RandomMobsProperties var0, String var1) {
      return var0.a(var1);
   }
}
