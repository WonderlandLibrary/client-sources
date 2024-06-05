package net;

import java.util.List;
import net.rF;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Rotations;

public class Sv {
   private static int[] b;

   public static void a(rF var0, int var1, Object var2) {
      var0.b(var1, var2);
   }

   public static void b(rF var0, int var1, Object var2) {
      var0.a(var1, var2);
   }

   public static int h(rF var0, int var1) {
      return var0.c(var1);
   }

   public static float a(rF var0, int var1) {
      return var0.b(var1);
   }

   public static void a(rF var0, int var1, int var2) {
      var0.a(var1, var2);
   }

   public static ItemStack e(rF var0, int var1) {
      return var0.d(var1);
   }

   public static byte g(rF var0, int var1) {
      return var0.g(var1);
   }

   public static List d(rF var0) {
      return var0.c();
   }

   public static void a(rF var0, List var1) {
      var0.a(var1);
   }

   public static short d(rF var0, int var1) {
      return var0.i(var1);
   }

   public static List a(rF var0) {
      return var0.b();
   }

   public static List a(PacketBuffer var0) {
      return rF.a(var0);
   }

   public static void a(List var0, PacketBuffer var1) {
      rF.a(var0, var1);
   }

   public static String c(rF var0, int var1) {
      return var0.a(var1);
   }

   public static void f(rF var0, int var1) {
      var0.f(var1);
   }

   public static boolean c(rF var0) {
      return var0.a();
   }

   public static boolean e(rF var0) {
      return var0.e();
   }

   public static void b(rF var0) {
      var0.d();
   }

   public static void a(rF var0, PacketBuffer var1) {
      var0.b(var1);
   }

   public static Rotations b(rF var0, int var1) {
      return var0.e(var1);
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
