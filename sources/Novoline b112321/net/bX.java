package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.api.FontRenderer;
import java.awt.Color;

public class bX {
   private static int[] b;

   public static int m(HUD var0) {
      return var0.getHUDColor();
   }

   public static StringProperty o(HUD var0) {
      return var0.getSuffixType();
   }

   public static float a(HUD var0, AbstractModule var1) {
      return var0.getHeight(var1);
   }

   public static int a(HUD var0, int var1, int var2) {
      return var0.b(var1, var2);
   }

   public static void a(HUD var0, FontRenderer var1, String var2, float var3, float var4) {
      var0.a(var1, var2, var3, var4);
   }

   public static Color c(HUD var0) {
      return var0.getColor();
   }

   public static ColorProperty j(HUD var0) {
      return var0.f();
   }

   public static void a(HUD var0, FontRenderer var1, String var2, float var3, float var4, float var5) {
      var0.a(var1, var2, var3, var4, var5);
   }

   public static int a(HUD var0, int var1, float var2, float var3, int var4) {
      return var0.b(var1, var2, var3, var4);
   }

   public static BooleanProperty l(HUD var0) {
      return var0.getnSounds();
   }

   public static boolean f(HUD var0) {
      return var0.isEnabled();
   }

   public static ListProperty k(HUD var0) {
      return var0.getHudElements();
   }

   public static String b(HUD var0) {
      return var0.C();
   }

   public static int a(HUD var0, int var1, float var2, int var3) {
      return var0.a(var1, var2, var3);
   }

   public static IntProperty n(HUD var0) {
      return var0.b();
   }

   public static IntProperty i(HUD var0) {
      return var0.E();
   }

   public static IntProperty g(HUD var0) {
      return var0.t();
   }

   public static IntProperty p(HUD var0) {
      return var0.m();
   }

   public static IntProperty a(HUD var0) {
      return var0.y();
   }

   public static IntProperty q(HUD var0) {
      return var0.v();
   }

   public static void h(HUD var0) {
      var0.renderTargetsList();
   }

   public static double[] d(HUD var0) {
      return var0.getTargetListWidthAndHeight();
   }

   public static void a(HUD var0, String var1, int var2) {
      var0.a(var1, var2);
   }

   public static IntProperty e(HUD var0) {
      return var0.getScoreboard();
   }

   public static void a(HUD var0, String var1, float var2, float var3) {
      var0.a(var1, var2, var3);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
