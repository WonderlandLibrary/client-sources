package net;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.RandomMobs;

public class r5 {
   public static void a() {
      RandomMobs.resetTextures();
   }

   public static ResourceLocation b(ResourceLocation var0) {
      return RandomMobs.getMcpatcherLocation(var0);
   }

   public static ResourceLocation a(ResourceLocation var0, int var1) {
      return RandomMobs.getLocationIndexed(var0, var1);
   }

   public static ResourceLocation a(ResourceLocation var0) {
      return RandomMobs.getTextureLocation(var0);
   }

   public static void a(World var0, World var1) {
      RandomMobs.worldChanged(var0, var1);
   }

   public static void a(Entity var0, World var1) {
      RandomMobs.entityLoaded(var0, var1);
   }
}
