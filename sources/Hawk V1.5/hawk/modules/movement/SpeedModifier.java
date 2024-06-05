package hawk.modules.movement;

import net.minecraft.client.Minecraft;

public class SpeedModifier {
   public static void setSpeed(float var0) {
      double var1 = (double)Minecraft.getMinecraft().thePlayer.rotationYaw;
      boolean var3 = Minecraft.getMinecraft().thePlayer.moveForward != 0.0F || Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0F;
      boolean var4 = Minecraft.getMinecraft().thePlayer.moveForward > 0.0F;
      boolean var5 = Minecraft.getMinecraft().thePlayer.moveForward < 0.0F;
      boolean var6 = Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0F;
      boolean var7 = Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0F;
      boolean var8 = var7 || var6;
      boolean var9 = var4 || var5;
      if (var3) {
         if (var4 && !var8) {
            var1 += 0.0D;
         } else if (var5 && !var8) {
            var1 += 180.0D;
         } else if (var4 && var7) {
            var1 += 45.0D;
         } else if (var4) {
            var1 -= 45.0D;
         } else if (!var9 && var7) {
            var1 += 90.0D;
         } else if (!var9 && var6) {
            var1 -= 90.0D;
         } else if (var5 && var7) {
            var1 += 135.0D;
         } else if (var5) {
            var1 -= 135.0D;
         }

         var1 = Math.toRadians(var1);
         Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(var1) * (double)var0;
         Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(var1) * (double)var0;
      }

   }
}
