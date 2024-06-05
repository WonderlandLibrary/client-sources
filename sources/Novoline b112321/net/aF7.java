package net;

import org.lwjgl.input.Mouse;

public class aF7 {
   private static boolean b;

   public static void a(boolean var0) {
      Mouse.setGrabbed(var0);
   }

   public static void a(int var0, int var1) {
      Mouse.setCursorPosition(var0, var1);
   }

   public static int n() {
      return Mouse.getDX();
   }

   public static int j() {
      return Mouse.getDY();
   }

   public static int k() {
      return Mouse.getDWheel();
   }

   public static int h() {
      return Mouse.getX();
   }

   public static int m() {
      return Mouse.getY();
   }

   public static boolean a() {
      return Mouse.next();
   }

   public static int l() {
      return Mouse.getEventButton();
   }

   public static boolean f() {
      return Mouse.getEventButtonState();
   }

   public static int b() {
      return Mouse.getEventDWheel();
   }

   public static boolean a(int var0) {
      return Mouse.isButtonDown(var0);
   }

   public static boolean g() {
      return Mouse.isInsideWindow();
   }

   public static boolean i() {
      return Mouse.isCreated();
   }

   public static int o() {
      return Mouse.getEventX();
   }

   public static int e() {
      return Mouse.getEventY();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean d() {
      return b;
   }

   public static boolean c() {
      boolean var0 = d();
      return true;
   }

   static {
      if(!d()) {
         b(true);
      }

   }
}
