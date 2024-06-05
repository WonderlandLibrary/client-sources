package net;

import cc.novoline.utils.RotationUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class a8I {
   public static float[] a(EntityLivingBase var0) {
      return RotationUtil.b(var0);
   }

   public static float[] a(float[] var0, int var1) {
      return RotationUtil.a(var0, var1);
   }

   public static Vec3 d(float var0) {
      return RotationUtil.getPositionEyes(var0);
   }

   public static Vec3 a(float var0, float var1) {
      return RotationUtil.getVectorForRotation(var0, var1);
   }

   public static float b(float var0) {
      return RotationUtil.b(var0);
   }

   public static float c(float var0) {
      return RotationUtil.a(var0);
   }

   public static float a(double var0, double var2) {
      return RotationUtil.getYawToPoint(var0, var2);
   }

   public static float[] c(double var0, double var2, double var4) {
      return RotationUtil.getRotations(var0, var2, var4);
   }

   public static float[] a(double var0, double var2, double var4) {
      return RotationUtil.a(var0, var2, var4);
   }

   public static double[] b(double var0, double var2, double var4) {
      return RotationUtil.getDistance(var0, var2, var4);
   }

   public static float a(float var0) {
      return RotationUtil.c(var0);
   }

   public static float b(float var0, float var1) {
      return RotationUtil.getDistanceBetweenAngles(var0, var1);
   }
}
