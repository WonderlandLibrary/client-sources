package hawk.util;

import net.minecraft.client.Minecraft;

public class PlayerUtil {
   public static Minecraft mc = Minecraft.getMinecraft();

   public static double getCurrentMotion() {
      double var0 = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
      return var0;
   }

   public static float getDirection() {
      float var0 = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward < 0.0F) {
         var0 += 180.0F;
      }

      float var1 = 1.0F;
      if (mc.thePlayer.moveForward < 0.0F) {
         var1 = -0.5F;
      } else if (mc.thePlayer.moveForward > 0.0F) {
         var1 = 0.5F;
      }

      if (mc.thePlayer.moveStrafing > 0.0F) {
         var0 -= 90.0F * var1;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         var0 += 90.0F * var1;
      }

      var0 *= 0.017453292F;
      return var0;
   }

   public static void setSpeed(double var0) {
      mc.thePlayer.motionX = -Math.sin((double)getDirection()) * var0;
      mc.thePlayer.motionZ = Math.cos((double)getDirection()) * var0;
   }
}
