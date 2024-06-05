package net;

import cc.novoline.gui.screen.alt.login.AltLoginListener;
import cc.novoline.gui.screen.alt.login.AltType;
import net.minecraft.util.Session;

public class k_ {
   public static void a(AltLoginListener var0, AltType var1, Session var2) {
      var0.onLoginSuccess(var1, var2);
   }

   public static void a(AltLoginListener var0) {
      var0.onLoginFailed();
   }
}
