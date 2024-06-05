package net;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class abG {
   public static boolean a(TileEntitySpecialRenderer var0) {
      return var0.func_181055_a();
   }

   public static void a(TileEntitySpecialRenderer var0, TileEntityRendererDispatcher var1) {
      var0.setRendererDispatcher(var1);
   }
}
