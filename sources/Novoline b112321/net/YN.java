package net;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import net.skidunion.I;
import net.skidunion.Y;
import net.skidunion.a4;
import net.skidunion.an;
import net.skidunion.d;
import net.skidunion.o;

public class YN {
   private static String[] b;

   public static Y f(o var0) {
      return var0.c();
   }

   public static void a(o var0, String var1) {
      var0.f(var1);
   }

   public static void b(o var0, String var1) {
      var0.e(var1);
   }

   public static void a(o var0, d var1) {
      var0.a(var1);
   }

   public static boolean e(o var0) {
      return var0.connectBlocking();
   }

   public static CopyOnWriteArrayList c(o var0) {
      return var0.d();
   }

   public static a4 a(o var0) {
      return var0.a();
   }

   public static void a(o var0, I var1) {
      var0.a(var1);
   }

   public static CountDownLatch h(o var0) {
      return var0.n();
   }

   public static String g(o var0) {
      return var0.e();
   }

   public static void a(o var0, an var1) {
      var0.a(var1);
   }

   public static void a(o var0, net.skidunion.c var1) {
      var0.a(var1);
   }

   public static d b(o var0) {
      return var0.l();
   }

   public static boolean d(o var0) {
      return var0.j();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[4]);
      }

   }
}
