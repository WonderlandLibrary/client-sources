package net;

import cc.novoline.gui.screen.alt.repository.TokenField;

public class ae8 {
   public static String a(TokenField var0) {
      return var0.getText();
   }

   public static void a(TokenField var0, String var1) {
      var0.setText(var1);
   }

   public static void b(TokenField var0) {
      var0.drawTextBox();
   }

   public static boolean a(TokenField var0, char var1, int var2) {
      return var0.textboxKeyTyped(var1, var2);
   }

   public static boolean c(TokenField var0) {
      return var0.isEnabled();
   }
}
