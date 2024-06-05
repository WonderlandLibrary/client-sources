package net;

import cc.novoline.gui.screen.alt.login.AltLoginListener;
import cc.novoline.gui.screen.alt.login.AltType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public abstract class Bv implements AltLoginListener {
   private static String b;

   public void onLoginSuccess(AltType var1, Session var2) {
      this.a(var2);
   }

   private void a(Session var1) {
      Minecraft.getInstance().session = var1;
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("jyD9kb");
      }

   }
}
