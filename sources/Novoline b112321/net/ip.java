package net;

import net.rF;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class ip {
   private static boolean b;

   public static void f(EntityItem var0) {
      var0.setDefaultPickupDelay();
   }

   public static ItemStack c(EntityItem var0) {
      return var0.getEntityItem();
   }

   public static void j(EntityItem var0) {
      var0.setAgeToCreativeDespawnTime();
   }

   public static void k(EntityItem var0) {
      var0.func_174870_v();
   }

   public static void e(EntityItem var0) {
      var0.setNoPickupDelay();
   }

   public static void b(EntityItem var0, String var1) {
      var0.setOwner(var1);
   }

   public static void i(EntityItem var0) {
      var0.setNoDespawn();
   }

   public static void a(EntityItem var0, int var1) {
      var0.setPickupDelay(var1);
   }

   public static void a(EntityItem var0, String var1) {
      var0.setThrower(var1);
   }

   public static int b(EntityItem var0) {
      return var0.getAge();
   }

   public static boolean h(EntityItem var0) {
      return var0.cannotPickup();
   }

   public static String d(EntityItem var0) {
      return var0.getThrower();
   }

   public static rF g(EntityItem var0) {
      return var0.k();
   }

   public static void a(EntityItem var0, ItemStack var1) {
      var0.setEntityItemStack(var1);
   }

   public static void a(EntityItem var0) {
      var0.setDead();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
