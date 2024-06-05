package cc.novoline.gui.screen.alt.repository;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.alt.login.AltType;
import cc.novoline.gui.screen.alt.repository.Alt;
import cc.novoline.utils.notifications.NotificationType;
import cc.novoline.utils.web.JsonObtainer;
import java.util.function.Consumer;
import net.Bv;
import net.CI;
import net.minecraft.util.Session;
import org.json.JSONObject;

class Alt$1 extends Bv {
   final Alt this$0;

   Alt$1(Alt var1) {
      this.this$0 = var1;
   }

   public void onLoginSuccess(AltType var1, Session var2) {
      CI.b();
      super.onLoginSuccess(var1, var2);
      Alt.access$000(this.this$0).getAlts().forEach(Alt::resetLogged);
      Alt.access$000(this.this$0).setCurrentAlt(this.this$0);
      this.this$0.setGameProfile(var2.getProfile());
      Alt.access$100(this.this$0, true);
      this.this$0.setInvalid(false);
      if(Alt.access$200(this.this$0) != null) {
         Novoline.getInstance().getNotificationManager().pop("Logged in! " + this.this$0.toString(), NotificationType.INFO);
      }

      try {
         JSONObject var4 = JsonObtainer.obtainJson("https://api.sk1er.club/player/" + var2.getUsername());
         if(!var4.optBoolean("success")) {
            if(var4.optString("cause").equals("non-player")) {
               Alt.access$302(this.this$0, "Never logged in");
            }

            Alt.access$302(this.this$0, "API DOWN");
            Alt.access$402(this.this$0, 1.0D);
         }

         JSONObject var5 = var4.optJSONObject("player");
         Alt.access$402(this.this$0, var5.optDouble("networkLevel"));
         Alt.access$302(this.this$0, var5.optString("rank_for_mod"));
      } catch (Exception var6) {
         ;
      }

   }

   public void onLoginFailed() {
      Alt.access$100(this.this$0, false);
      this.this$0.setInvalid(true);
      Novoline.getInstance().getNotificationManager().pop("Invalid credentials!", NotificationType.ERROR);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
