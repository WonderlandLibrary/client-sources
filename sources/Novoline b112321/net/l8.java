package net;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;
import net.optifine.CustomSky;

public class l8 {
   public static void b() {
      CustomSky.reset();
   }

   public static void a() {
      CustomSky.update();
   }

   public static void a(World var0, TextureManager var1, float var2, float var3) {
      CustomSky.renderSky(var0, var1, var2, var3);
   }

   public static boolean a(World var0) {
      return CustomSky.hasSkyLayers(var0);
   }
}
