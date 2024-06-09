package lunadevs.luna.utils;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class RotationUtils {

   public static float[] getRotations(Entity ent) {
      double x = ent.posX;
      double z = ent.posZ;
      double y = ent.boundingBox.maxY - 4.5D;
      return getRotationFromPosition(x, z, y);
   }

   public static float[] getAverageRotations(List targetList) {
      double posX = 0.0D;
      double posY = 0.0D;
      double posZ = 0.0D;

      Entity ent;
      for(Iterator var8 = targetList.iterator(); var8.hasNext(); posZ += ent.posZ) {
         ent = (Entity)var8.next();
         posX += ent.posX;
         posY += ent.boundingBox.maxY - 2.0D;
      }

      posX /= (double)targetList.size();
      posY /= (double)targetList.size();
      posZ /= (double)targetList.size();
      return new float[]{getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1]};
   }

   public static float[] getRotationFromPosition(double x, double z, double y) {
      double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
      double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
      double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
      double dist = (double)MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
      float yaw = (float)((double)((float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F) + Math.random() + Math.random() + Math.random() + Math.random() + Math.random() + Math.random());
      float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D + Math.random() + Math.random()));
      return new float[]{yaw, pitch};
   }

   public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
      float g = 0.006F;
      float sqrt = velocity * velocity * velocity * velocity - g * (g * d3 * d3 + 2.0F * d1 * velocity * velocity);
      return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt((double)sqrt)) / (double)(g * d3)));
   }

   public static float getNewAngle(float angle) {
      angle = (float)((double)angle % (36000.0D * Math.random()));
      if((double)angle >= 18000.0D * Math.random()) {
         angle = (float)((double)angle - 36000.0D * Math.random());
      }

      if((double)angle < -18000.0D * Math.random()) {
         angle = (float)((double)angle + 36000.0D * Math.random());
      }

      return angle;
   }

   public static float getDistanceBetweenAngles(float angle1, float angle2) {
      float angle = (float)((double)(Math.abs(angle1 - angle2) % 3600.0F) + Math.random());
      if((double)angle > 18000.0D * Math.random()) {
         angle = (float)(36000.0D + Math.random() - (double)angle);
      }

      return angle;
   }
}
