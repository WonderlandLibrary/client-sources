package hawk.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class ScaffoldHelper {
   public static float[] getAngles(Entity var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      return new float[]{getYawChangeToEntity(var0) + var1.thePlayer.rotationYaw, getPitchChangeToEntity(var0) + var1.thePlayer.rotationPitch};
   }

   public static float[] getBlockRotations(int var0, int var1, int var2, EnumFacing var3) {
      Minecraft var4 = Minecraft.getMinecraft();
      EntitySnowball var5 = new EntitySnowball(var4.theWorld);
      var5.posX = (double)var0 + 0.5D;
      var5.posY = (double)var1 + 0.5D;
      var5.posZ = (double)var2 + 0.5D;
      return getAngles(var5);
   }

   public static float getYawChangeToEntity(Entity var0) {
      Minecraft var3 = Minecraft.getMinecraft();
      double var4 = var0.posX - var3.thePlayer.posX;
      double var6 = var0.posZ - var3.thePlayer.posZ;
      double var1;
      if (var6 < 0.0D && var4 < 0.0D) {
         var1 = 90.0D + Math.toDegrees(Math.atan(var6 / var4));
      } else if (var6 < 0.0D && var4 > 0.0D) {
         var1 = -90.0D + Math.toDegrees(Math.atan(var6 / var4));
      } else {
         var1 = Math.toDegrees(-Math.atan(var4 / var6));
      }

      return MathHelper.wrapAngleTo180_float(-(var3.thePlayer.rotationYaw - (float)var1));
   }

   public static float getPitchChangeToEntity(Entity var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      double var2 = var0.posX - var1.thePlayer.posX;
      double var4 = var0.posZ - var1.thePlayer.posZ;
      double var6 = var0.posY - 1.6D + (double)var0.getEyeHeight() - 0.4D - var1.thePlayer.posY;
      double var8 = (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4);
      double var10 = -Math.toDegrees(Math.atan(var6 / var8));
      return -MathHelper.wrapAngleTo180_float(var1.thePlayer.rotationPitch - (float)var10);
   }

   public static float[] getRotations(Entity var0) {
      if (var0 == null) {
         return null;
      } else {
         double var3 = var0.posX - Minecraft.getMinecraft().thePlayer.posX;
         double var5 = var0.posZ - Minecraft.getMinecraft().thePlayer.posZ;
         double var1;
         if (var0 instanceof EntityLivingBase) {
            EntityLivingBase var7 = (EntityLivingBase)var0;
            var1 = var7.posY + (double)var7.getEyeHeight() - 0.4D - Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
         } else {
            var1 = (var0.boundingBox.minY + var0.boundingBox.maxY) / 2.0D - Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
         }

         double var11 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5);
         float var9 = (float)(Math.atan2(var5, var3) * 180.0D / 3.141592653589793D) - 90.0F;
         float var10 = (float)(-(Math.atan2(var1, var11) * 180.0D / 3.141592653589793D));
         return new float[]{var9, var10};
      }
   }
}
