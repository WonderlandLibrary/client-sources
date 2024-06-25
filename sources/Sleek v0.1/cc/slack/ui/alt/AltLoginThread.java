package cc.slack.ui.alt;

import cc.slack.utils.client.mc;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatFormatting;
import net.minecraft.util.Session;

public final class AltLoginThread extends Thread {
   private final String password;
   private String status;
   private final String username;
   private final Minecraft mc = Minecraft.getMinecraft();

   public AltLoginThread(String username, String password) {
      super("Alt Login Thread");
      this.username = username;
      this.password = password;
      this.status = ChatFormatting.GRAY + "Waiting...";
   }

   private Session createSession(String username, String password) {
      try {
         MicrosoftAuthResult result = (new MicrosoftAuthenticator()).loginWithCredentials(username, password);
         return new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft");
      } catch (MicrosoftAuthenticationException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public String getStatus() {
      return this.status;
   }

   public void run() {
      if (this.password.equals("")) {
         this.mc.session = new Session(this.username, "", "", "mojang");
         this.status = ChatFormatting.GREEN + "Logged in. (" + this.username + " - offline name)";
      } else {
         this.status = ChatFormatting.YELLOW + "Logging in...";
         Session auth = this.createSession(this.username, this.password);
         if (auth == null) {
            this.status = ChatFormatting.RED + "Login failed!";
         } else {
            this.status = ChatFormatting.GREEN + "Logged in as " + auth.getUsername();
            cc.slack.utils.client.mc.getMinecraft().session = auth;
         }

      }
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
