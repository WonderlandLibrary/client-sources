package net;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;

public class _I {
   public static ClippingHelper a() {
      return ClippingHelperImpl.getInstance();
   }

   public static void a(ClippingHelperImpl var0) {
      var0.init();
   }
}
