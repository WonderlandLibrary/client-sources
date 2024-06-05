package net;

import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;
import java.util.List;
import net.minecraft.entity.Entity;

public class arw {
   private static boolean b;

   public static Entity x(KillAura var0) {
      return var0.getTarget();
   }

   public static void s(KillAura var0) {
      var0.u();
   }

   public static boolean l(KillAura var0) {
      return var0.shouldBlock();
   }

   public static boolean q(KillAura var0) {
      return var0.isEnabled();
   }

   public static boolean v(KillAura var0) {
      return var0.shouldAttack();
   }

   public static DoubleProperty j(KillAura var0) {
      return var0.getRange();
   }

   public static IntProperty h(KillAura var0) {
      return var0.getThx();
   }

   public static IntProperty m(KillAura var0) {
      return var0.getThy();
   }

   public static StringProperty c(KillAura var0) {
      return var0.B();
   }

   public static float e(KillAura var0) {
      return var0.getIYaw();
   }

   public static float o(KillAura var0) {
      return var0.getPrevIYaw();
   }

   public static float b(KillAura var0) {
      return var0.getPrevIPitch();
   }

   public static float i(KillAura var0) {
      return var0.getIPitch();
   }

   public static boolean a(KillAura var0, Entity var1) {
      return var0.isValidEntity(var1);
   }

   public static long r(KillAura var0) {
      return var0.p();
   }

   public static List t(KillAura var0) {
      return var0.getTargetsFromRange();
   }

   public static StringProperty d(KillAura var0) {
      return var0.w();
   }

   public static StringProperty f(KillAura var0) {
      return var0.g();
   }

   public static StringProperty w(KillAura var0) {
      return var0.i();
   }

   public static DoubleProperty u(KillAura var0) {
      return var0.getAps();
   }

   public static DoubleProperty k(KillAura var0) {
      return var0.R();
   }

   public static DoubleProperty p(KillAura var0) {
      return var0.getWallRange();
   }

   public static DoubleProperty y(KillAura var0) {
      return var0.getBlockRange();
   }

   public static void b(KillAura var0, Entity var1) {
      var0.setTarget(var1);
   }

   public static ListProperty n(KillAura var0) {
      return var0.getAutoDisable();
   }

   public static ListProperty a(KillAura var0) {
      return var0.getFilters();
   }

   public static BooleanProperty g(KillAura var0) {
      return var0.getTargetHud();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
