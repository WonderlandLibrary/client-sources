package net;

import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.ESP;
import net.minecraft.entity.Entity;

public class jl {
   public static IntProperty e(ESP var0) {
      return var0.a();
   }

   public static IntProperty h(ESP var0) {
      return var0.g();
   }

   public static StringProperty b(ESP var0) {
      return var0.c();
   }

   public static int[] d(ESP var0) {
      return var0.j();
   }

   public static int[] c(ESP var0) {
      return var0.f();
   }

   public static int[] a(ESP var0) {
      return var0.h();
   }

   public static boolean l(ESP var0) {
      return var0.isEnabled();
   }

   public static StringProperty i(ESP var0) {
      return var0.i();
   }

   public static boolean f(ESP var0) {
      return var0.b();
   }

   public static boolean a(ESP var0, Entity var1) {
      return var0.b(var1);
   }

   public static ListProperty j(ESP var0) {
      return var0.d();
   }

   public static ColorProperty g(ESP var0) {
      return var0.e();
   }

   public static StringProperty k(ESP var0) {
      return var0.k();
   }
}
