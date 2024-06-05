package net;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.optifine.DynamicLights;

public class um {
   public static void b(RenderGlobal var0) {
      DynamicLights.removeLights(var0);
   }

   public static int a(Entity var0) {
      return DynamicLights.getLightLevel(var0);
   }

   public static int a() {
      return DynamicLights.getCount();
   }

   public static int a(Entity var0, int var1) {
      return DynamicLights.getCombinedLight(var0, var1);
   }

   public static void b() {
      DynamicLights.clear();
   }

   public static void a(RenderGlobal var0) {
      DynamicLights.update(var0);
   }

   public static void b(Entity var0, RenderGlobal var1) {
      DynamicLights.entityAdded(var0, var1);
   }

   public static void a(Entity var0, RenderGlobal var1) {
      DynamicLights.entityRemoved(var0, var1);
   }
}
