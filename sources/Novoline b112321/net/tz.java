package net;

import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.AxisAlignedBB;

public class tz {
   public static boolean a(ICamera var0, AxisAlignedBB var1) {
      return var0.isBoundingBoxInFrustum(var1);
   }
}
