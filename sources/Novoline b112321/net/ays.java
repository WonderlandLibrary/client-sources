package net;

import java.util.Set;
import net.minecraft.client.settings.KeyBinding;

public class ays {
   private static int b;

   public static boolean b(KeyBinding var0) {
      return var0.isKeyDown();
   }

   public static String c(KeyBinding var0) {
      return var0.getKeyDescription();
   }

   public static int f(KeyBinding var0) {
      return var0.getKeyCode();
   }

   public static int a(KeyBinding var0) {
      return var0.getKeyCodeDefault();
   }

   public static void c() {
      KeyBinding.resetKeyBindingArrayAndHash();
   }

   public static void e() {
      KeyBinding.unPressAllKeys();
   }

   public static void a(int var0, boolean var1) {
      KeyBinding.setKeyBindState(var0, var1);
   }

   public static void a(int var0) {
      KeyBinding.onTick(var0);
   }

   public static boolean d(KeyBinding var0) {
      return var0.isPressed();
   }

   public static void a(KeyBinding var0, int var1) {
      var0.setKeyCode(var1);
   }

   public static Set b() {
      return KeyBinding.getKeybinds();
   }

   public static String e(KeyBinding var0) {
      return var0.getKeyCategory();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int d() {
      return b;
   }

   public static int a() {
      int var0 = d();
      return 116;
   }

   static {
      if(d() == 0) {
         b(55);
      }

   }
}
