package net;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$AxisDirection;
import net.minecraft.util.Vec3i;

public class z_ {
   private static int[] b;

   public static int j(EnumFacing var0) {
      return var0.getFrontOffsetX();
   }

   public static int h(EnumFacing var0) {
      return var0.getFrontOffsetY();
   }

   public static int f(EnumFacing var0) {
      return var0.getFrontOffsetZ();
   }

   public static EnumFacing i(EnumFacing var0) {
      return var0.getOpposite();
   }

   public static EnumFacing b(int var0) {
      return EnumFacing.getFront(var0);
   }

   public static EnumFacing$Axis l(EnumFacing var0) {
      return var0.getAxis();
   }

   public static int b(EnumFacing var0) {
      return var0.getIndex();
   }

   public static Vec3i k(EnumFacing var0) {
      return var0.getDirectionVec();
   }

   public static EnumFacing e(EnumFacing var0) {
      return var0.rotateYCCW();
   }

   public static EnumFacing d(EnumFacing var0) {
      return var0.rotateY();
   }

   public static int g(EnumFacing var0) {
      return var0.getHorizontalIndex();
   }

   public static EnumFacing a(int var0) {
      return EnumFacing.getHorizontal(var0);
   }

   public static String c(EnumFacing var0) {
      return var0.getName();
   }

   public static EnumFacing a(String var0) {
      return EnumFacing.a(var0);
   }

   public static EnumFacing a(double var0) {
      return EnumFacing.fromAngle(var0);
   }

   public static EnumFacing a(Random var0) {
      return EnumFacing.random(var0);
   }

   public static EnumFacing$AxisDirection a(EnumFacing var0) {
      return var0.getAxisDirection();
   }

   public static EnumFacing a(EnumFacing var0, EnumFacing$Axis var1) {
      return var0.rotateAround(var1);
   }

   public static EnumFacing a(float var0, float var1, float var2) {
      return EnumFacing.getFacingFromVector(var0, var1, var2);
   }

   public static EnumFacing a(EnumFacing$AxisDirection var0, EnumFacing$Axis var1) {
      return EnumFacing.func_181076_a(var0, var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
