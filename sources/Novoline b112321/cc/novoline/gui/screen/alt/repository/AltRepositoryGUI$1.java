package cc.novoline.gui.screen.alt.repository;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.alt.login.AltType;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.alt.repository.GuiAddAlt;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.gui.screen.alt.repository.credential.AlteningAltCredential;
import cc.novoline.utils.notifications.NotificationType;
import com.thealtening.api.response.AccountDetails;
import net.Bv;
import net.CI;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

class AltRepositoryGUI$1 extends Bv {
   final AltCredential val$credentials;
   final GuiAddAlt val$gui;
   final AltRepositoryGUI this$0;

   AltRepositoryGUI$1(AltRepositoryGUI var1, AltCredential var2, GuiAddAlt var3) {
      this.this$0 = var1;
      this.val$credentials = var2;
      this.val$gui = var3;
   }

   public void onLoginSuccess(AltType var1, Session var2) {
      CI.b();
      super.onLoginSuccess(var1, var2);
      StringBuilder var4 = new StringBuilder("Logged in! Username: " + var2.getUsername());
      if(this.val$credentials instanceof AlteningAltCredential) {
         AlteningAltCredential var5 = (AlteningAltCredential)this.val$credentials;
         AccountDetails var6 = var5.getDetails();
         String var7 = var6.getHypixelRank();
         var4.append(" | ").append(var6.getHypixelLevel()).append(" Lvl").append(var7 != null?" | " + var7:"");
      }

      Novoline.getInstance().getNotificationManager().pop(var4.toString(), NotificationType.INFO);
      AltRepositoryGUI.access$000(this.this$0).displayGuiScreen(this.this$0);
   }

   public void onLoginFailed() {
      this.val$gui.getGroupAltInfo().updateStatus(EnumChatFormatting.RED + "Invalid credentials!");
   }
}
