package rina.turok.bope.bopemod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BopeUtilMath {
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static double[] calcule_look_at(double px, double py, double pz, EntityPlayer player) {
      double diff_x = player.posX - px;
      double diff_y = player.posY - py;
      double diff_z = player.posZ - pz;
      Math.sqrt(diff_x * diff_x + diff_y * diff_y + diff_z * diff_z);
      double pitch = Math.asin(diff_y);
      double yaw = Math.atan2(diff_z, diff_x);
      pitch = pitch * 180.0D / 3.141592653589793D;
      yaw = yaw * 180.0D / 3.141592653589793D;
      yaw += 90.0D;
      return new double[]{yaw, pitch};
   }

   public static float[] legit_rotation(Vec3d pos) {
      Vec3d eye_pos = get_eye_pos();
      double diff_x = pos.x - eye_pos.x;
      double diff_y = pos.y - eye_pos.y;
      double diff_z = pos.z - eye_pos.z;
      double diff_x_z = Math.sqrt(diff_x * diff_x + diff_z * diff_z);
      float player_yaw = (float)Math.toDegrees(Math.atan2(diff_z, diff_x)) - 90.0F;
      float player_pitch = (float)(-Math.toDegrees(Math.atan2(diff_y, diff_x_z)));
      return new float[]{mc.player.rotationYaw + MathHelper.wrapDegrees(player_yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(player_pitch - mc.player.rotationPitch)};
   }

   public static Vec3d get_eye_pos() {
      return new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
   }

   public static double[] movement_speed(double speed) {
      float forward = mc.player.movementInput.moveForward;
      float side = mc.player.movementInput.moveStrafe;
      float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
      if (forward != 0.0F) {
         if (side > 0.0F) {
            yaw += (float)(forward > 0.0F ? -45 : 45);
         } else if (side < 0.0F) {
            yaw += (float)(forward > 0.0F ? 45 : -45);
         }

         side = 0.0F;
         if (forward > 0.0F) {
            forward = 1.0F;
         } else if (forward < 0.0F) {
            forward = -1.0F;
         }
      }

      double sin = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      double cos = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double pos_x = (double)forward * speed * cos + (double)side * speed * sin;
      double pos_z = (double)forward * speed * sin - (double)side * speed * cos;
      return new double[]{pos_x, pos_z};
   }
}
