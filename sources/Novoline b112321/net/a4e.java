package net;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

public class a4e {
   public static float[] a(EnumDyeColor var0) {
      return EntitySheep.func_175513_a(var0);
   }

   public static float a(EntitySheep var0, float var1) {
      return var0.getHeadRotationPointY(var1);
   }

   public static float b(EntitySheep var0, float var1) {
      return var0.getHeadRotationAngleX(var1);
   }

   public static boolean c(EntitySheep var0) {
      return var0.getSheared();
   }

   public static boolean d(EntitySheep var0) {
      return var0.isInvisible();
   }

   public static boolean b(EntitySheep var0) {
      return var0.hasCustomName();
   }

   public static String a(EntitySheep var0) {
      return var0.getCustomNameTag();
   }

   public static int e(EntitySheep var0) {
      return var0.getEntityID();
   }

   public static EnumDyeColor f(EntitySheep var0) {
      return var0.getFleeceColor();
   }

   public static void a(EntitySheep var0, EnumDyeColor var1) {
      var0.setFleeceColor(var1);
   }
}
