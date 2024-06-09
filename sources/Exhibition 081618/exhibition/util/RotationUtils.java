package exhibition.util;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtils {
   public static float[] getRotations(EntityLivingBase ent) {
      double x = ent.posX;
      double z = ent.posZ;
      double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
      return getRotationFromPosition(x, z, y);
   }

   public static float[] getAverageRotations(List targetList) {
      double posX = 0.0D;
      double posY = 0.0D;
      double posZ = 0.0D;

      Entity ent;
      for(Iterator var7 = targetList.iterator(); var7.hasNext(); posZ += ent.posZ) {
         ent = (Entity)var7.next();
         posX += ent.posX;
         posY += ent.boundingBox.maxY - 2.0D;
      }

      posX /= (double)targetList.size();
      posY /= (double)targetList.size();
      posZ /= (double)targetList.size();
      return new float[]{getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1]};
   }

   public static float[] getBowAngles(Entity entity) {
      double xDelta = entity.posX - entity.lastTickPosX;
      double zDelta = entity.posZ - entity.lastTickPosZ;
      double d = (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
      d -= d % 0.8D;
      double xMulti = 1.0D;
      double zMulti = 1.0D;
      boolean sprint = entity.isSprinting();
      xMulti = d / 0.8D * xDelta * (sprint ? 1.25D : 1.0D);
      zMulti = d / 0.8D * zDelta * (sprint ? 1.25D : 1.0D);
      double x = entity.posX + xMulti - Minecraft.getMinecraft().thePlayer.posX;
      double z = entity.posZ + zMulti - Minecraft.getMinecraft().thePlayer.posZ;
      double y = Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
      double dist = (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
      float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
      float pitch = (float)Math.toDegrees(Math.atan2(y, dist));
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationFromPosition(double x, double z, double y) {
      double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
      double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
      double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2D;
      double dist = (double)MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
      return new float[]{yaw, pitch};
   }

   public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
      float g = 0.006F;
      float sqrt = velocity * velocity * velocity * velocity - g * (g * d3 * d3 + 2.0F * d1 * velocity * velocity);
      return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt((double)sqrt)) / (double)(g * d3)));
   }

   public static float getYawChange(double posX, double posZ) {
      double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double yawToEntity;
      if (deltaZ < 0.0D && deltaX < 0.0D) {
         yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else if (deltaZ < 0.0D && deltaX > 0.0D) {
         yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else {
         yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }

      return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity));
   }

   public static float getYawChangeGiven(double posX, double posZ, float yaw) {
      double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double yawToEntity;
      if (deltaZ < 0.0D && deltaX < 0.0D) {
         yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else if (deltaZ < 0.0D && deltaX > 0.0D) {
         yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else {
         yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }

      return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
   }

   public static float getPitchChange(Entity entity, double posY) {
      double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double deltaY = posY - 2.2D + (double)entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
      double distanceXZ = (double)MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
      double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
      return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity) - 2.5F;
   }

   public static float getNewAngle(float angle) {
      angle %= 360.0F;
      if (angle >= 180.0F) {
         angle -= 360.0F;
      }

      if (angle < -180.0F) {
         angle += 360.0F;
      }

      return angle;
   }

   public static float getDistanceBetweenAngles(float angle1, float angle2) {
      float angle = Math.abs(angle1 - angle2) % 360.0F;
      if (angle > 180.0F) {
         angle = 360.0F - angle;
      }

      return angle;
   }
}
