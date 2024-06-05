package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;

public class Eu {
   private static int b;

   public static EventState g(MotionUpdateEvent var0) {
      return var0.getState();
   }

   public static void b(MotionUpdateEvent var0, float var1) {
      var0.setYaw(var1);
   }

   public static void a(MotionUpdateEvent var0, float var1) {
      var0.setPitch(var1);
   }

   public static void a(MotionUpdateEvent var0, boolean var1) {
      var0.setOnGround(var1);
   }

   public static double c(MotionUpdateEvent var0) {
      return var0.getY();
   }

   public static void c(MotionUpdateEvent var0, double var1) {
      var0.setY(var1);
   }

   public static double b(MotionUpdateEvent var0) {
      return var0.getX();
   }

   public static double d(MotionUpdateEvent var0) {
      return var0.getZ();
   }

   public static boolean e(MotionUpdateEvent var0) {
      return var0.isOnGround();
   }

   public static float a(MotionUpdateEvent var0) {
      return var0.getYaw();
   }

   public static float f(MotionUpdateEvent var0) {
      return var0.getPitch();
   }

   public static void a(MotionUpdateEvent var0, double var1) {
      var0.setX(var1);
   }

   public static void b(MotionUpdateEvent var0, double var1) {
      var0.setZ(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 50;
   }

   static {
      if(a() != 0) {
         b(1);
      }

   }
}
