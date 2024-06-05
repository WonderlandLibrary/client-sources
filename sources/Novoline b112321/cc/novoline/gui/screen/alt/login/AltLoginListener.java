package cc.novoline.gui.screen.alt.login;

import cc.novoline.gui.screen.alt.login.AltType;
import net.minecraft.util.Session;

public interface AltLoginListener {
   void onLoginSuccess(AltType var1, Session var2);

   void onLoginFailed();
}
