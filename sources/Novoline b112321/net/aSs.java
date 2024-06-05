package net;

import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;

public class aSs {
   public static boolean c(PlayerAbilities var0) {
      return var0.isAllowFly();
   }

   public static void e(PlayerAbilities var0, boolean var1) {
      var0.setFlying(var1);
   }

   public static float a(PlayerAbilities var0) {
      return var0.getFlySpeed();
   }

   public static void b(PlayerAbilities var0, boolean var1) {
      var0.setSprinting(var1);
   }

   public static byte b(PlayerAbilities var0) {
      return var0.getFlags();
   }

   public static boolean f(PlayerAbilities var0) {
      return var0.isSprinting();
   }

   public static float e(PlayerAbilities var0) {
      return var0.getWalkSpeed();
   }

   public static void c(PlayerAbilities var0, boolean var1) {
      var0.setInvincible(var1);
   }

   public static void a(PlayerAbilities var0, boolean var1) {
      var0.setAllowFly(var1);
   }

   public static void d(PlayerAbilities var0, boolean var1) {
      var0.setCreative(var1);
   }

   public static void b(PlayerAbilities var0, float var1) {
      var0.setFlySpeed(var1);
   }

   public static void a(PlayerAbilities var0, float var1) {
      var0.setWalkSpeed(var1);
   }

   public static boolean d(PlayerAbilities var0) {
      return var0.isFlying();
   }
}
