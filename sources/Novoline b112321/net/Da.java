package net;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.nbt.NBTTagCompound;

public class Da {
   private static int[] b;

   public static boolean c(PlayerAbilities var0) {
      return var0.isCreative();
   }

   public static boolean f(PlayerAbilities var0) {
      return var0.isAllowFlying();
   }

   public static void c(PlayerAbilities var0, boolean var1) {
      var0.setFlying(var1);
   }

   public static boolean b(PlayerAbilities var0) {
      return var0.isDisabledDamage();
   }

   public static float e(PlayerAbilities var0) {
      return var0.getFlySpeed();
   }

   public static void b(PlayerAbilities var0, float var1) {
      var0.setFlySpeed(var1);
   }

   public static boolean d(PlayerAbilities var0) {
      return var0.isFlying();
   }

   public static boolean a(PlayerAbilities var0) {
      return var0.isAllowEdit();
   }

   public static float g(PlayerAbilities var0) {
      return var0.getWalkSpeed();
   }

   public static void a(PlayerAbilities var0, NBTTagCompound var1) {
      var0.readCapabilitiesFromNBT(var1);
   }

   public static void b(PlayerAbilities var0, NBTTagCompound var1) {
      var0.writeCapabilitiesToNBT(var1);
   }

   public static void e(PlayerAbilities var0, boolean var1) {
      var0.setCreative(var1);
   }

   public static void a(PlayerAbilities var0, boolean var1) {
      var0.setDisabledDamage(var1);
   }

   public static void d(PlayerAbilities var0, boolean var1) {
      var0.setAllowFlying(var1);
   }

   public static void a(PlayerAbilities var0, float var1) {
      var0.setWalkSpeed(var1);
   }

   public static void b(PlayerAbilities var0, boolean var1) {
      var0.setAllowEdit(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[5]);
      }

   }
}
