package net;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotations;

public class atm {
   public static void a(EntityArmorStand var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static boolean b(EntityArmorStand var0, NBTTagCompound var1) {
      return var0.writeToNBTOptional(var1);
   }

   public static void a(EntityArmorStand var0, NBTTagCompound var1) {
      var0.readFromNBT(var1);
   }

   public static Rotations i(EntityArmorStand var0) {
      return var0.getHeadRotation();
   }

   public static void a(EntityArmorStand var0, Rotations var1) {
      var0.setHeadRotation(var1);
   }

   public static Rotations f(EntityArmorStand var0) {
      return var0.getBodyRotation();
   }

   public static void b(EntityArmorStand var0, Rotations var1) {
      var0.setBodyRotation(var1);
   }

   public static boolean b(EntityArmorStand var0) {
      return var0.getShowArms();
   }

   public static boolean a(EntityArmorStand var0) {
      return var0.hasNoBasePlate();
   }

   public static Rotations c(EntityArmorStand var0) {
      return var0.getLeftLegRotation();
   }

   public static Rotations h(EntityArmorStand var0) {
      return var0.getRightLegRotation();
   }

   public static Rotations g(EntityArmorStand var0) {
      return var0.getLeftArmRotation();
   }

   public static Rotations d(EntityArmorStand var0) {
      return var0.getRightArmRotation();
   }

   public static boolean e(EntityArmorStand var0) {
      return var0.getAlwaysRenderNameTag();
   }
}
