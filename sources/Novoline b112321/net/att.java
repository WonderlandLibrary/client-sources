package net;

import cc.novoline.gui.screen.alt.repository.PasswordTextField;

public class att {
   public static void a(PasswordTextField var0) {
      var0.drawTextBox();
   }

   public static String c(PasswordTextField var0) {
      return var0.getText();
   }

   public static boolean b(PasswordTextField var0) {
      return var0.isFocused();
   }

   public static void a(PasswordTextField var0, String var1) {
      var0.setText(var1);
   }

   public static void a(PasswordTextField var0, boolean var1) {
      var0.setFocused(var1);
   }

   public static boolean a(PasswordTextField var0, char var1, int var2) {
      return var0.textboxKeyTyped(var1, var2);
   }
}
