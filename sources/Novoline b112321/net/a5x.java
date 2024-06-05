package net;

import cc.novoline.gui.screen.login.textbox.UIDField;

public class a5x {
   private static boolean b;

   public static void b(UIDField var0, int var1) {
      var0.setColor(var1);
   }

   public static void a(UIDField var0, int var1) {
      var0.setTextColor(var1);
   }

   public static void a(UIDField var0, float var1, float var2) {
      var0.updateCoordinates(var1, var2);
   }

   public static void c(UIDField var0) {
      var0.drawTextBox();
   }

   public static boolean b(UIDField var0) {
      return var0.isFocused();
   }

   public static boolean a(UIDField var0, char var1, int var2) {
      return var0.textboxKeyTyped(var1, var2);
   }

   public static String a(UIDField var0) {
      return var0.getText();
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
      if(c()) {
         b(true);
      }

   }
}
