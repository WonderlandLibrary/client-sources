package net;

import cc.novoline.Novoline;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.property.object.KeyBindProperty;

public class a2Y {
   private static int b;

   public static void a(AbstractModule var0, ModuleKeybind var1) {
      var0.setKeyBind(var1);
   }

   public static KeyBindProperty j(AbstractModule var0) {
      return var0.getKeybind();
   }

   public static boolean g(AbstractModule var0) {
      return var0.isEnabled();
   }

   public static boolean f(AbstractModule var0) {
      return var0.toggle();
   }

   public static void c(AbstractModule var0, boolean var1) {
      var0.setEnabled(var1);
   }

   public static String e(AbstractModule var0) {
      return var0.getName();
   }

   public static EnumModuleType c(AbstractModule var0) {
      return var0.getType();
   }

   public static String k(AbstractModule var0) {
      return var0.getDescription();
   }

   public static Novoline d(AbstractModule var0) {
      return var0.getNovoline();
   }

   public static String i(AbstractModule var0) {
      return var0.getDisplayName();
   }

   public static boolean h(AbstractModule var0) {
      return var0.isHidden();
   }

   public static void a(AbstractModule var0, boolean var1) {
      var0.setHidden(var1);
   }

   public static int b(AbstractModule var0) {
      return var0.p();
   }

   public static void b(AbstractModule var0, boolean var1) {
      var0.c(var1);
   }

   public static String a(AbstractModule var0) {
      return var0.getVanillaDisplayName();
   }

   public static void a(AbstractModule var0, String var1) {
      var0.setDisplayNameProperty(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 33;
   }

   static {
      if(b() == 0) {
         b(108);
      }

   }
}
