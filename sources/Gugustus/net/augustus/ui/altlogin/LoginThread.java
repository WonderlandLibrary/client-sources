package net.augustus.ui.altlogin;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.awt.Color;
import java.net.Proxy;
import java.util.List;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.augustus.Augustus;
import net.augustus.ui.augustusmanager.AugustusSounds;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.sound.SoundUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class LoginThread extends Thread implements MC {
   private final boolean isBrowser;
   private String username;
   private String password;
   private Color color = Color.green;
   private String status = "";
   private boolean gen;

   public LoginThread(String username, String password, boolean isGen, boolean isBrowser) {
      super("Login Thread");
      this.username = username;
      this.password = password;
      this.gen = isGen;
      this.isBrowser = isBrowser;
   }

   public Session loginSession(String username, String password) {
      /*
      YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
      YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
      auth.setUsername(username);
      auth.setPassword(password);

      try {
         auth.logIn();
         return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
      } catch (AuthenticationException var6) {
         var6.printStackTrace();
         return null;
      }
       */
      try {
         MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
         MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
         // Or using a webview: authenticator.loginWithWebView();
         // Or using refresh token: authenticator.loginWithRefreshToken("refresh token");
         // Or using your own way: authenticator.loginWithTokens("access token", "refresh token");
         return new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft");
         //System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public void run() {
      super.run();
      if(isBrowser) {
         this.status = "Logging in...";
         MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
         MicrosoftAuthResult result;
         try {
            result = authenticator.loginWithWebview();
         } catch (MicrosoftAuthenticationException e) {e.printStackTrace(); return;}
         mc.setSession(new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft"));
         this.status = "Logged in! (" + mc.getSession().getUsername() + ")";
         return;
      }
      if(gen) {
         this.status = "Generated cracked alt - (" + this.username + ")";
         this.loginSuccessSound();
         mc.setSession(new Session(this.username, "", "", "mojang"));
         return;
      }
      if (this.password.equals("")) {
         if (this.username.contains(":")) {
            String[] s = this.username.split(":");
            this.username = s[0];
            this.password = s[1];
         } else if (!this.username.equals("")) {
            this.status = "Logged in - (" + this.username + ") Cracked Login";
            this.loginSuccessSound();
            mc.setSession(new Session(this.username, "", "", "mojang"));
            return;
         }
      }

      try {
         this.status = "Logging in...";
         this.color = Color.green;
         Session session = this.loginSession(this.username, this.password);
         if (session != null) {
            mc.setSession(session);
            this.status = "Logged in! (" + session.getUsername() + ")";
            this.color = Color.green;
            this.loginSuccessSound();
            List<String> lastAlts = Augustus.getInstance().getLastAlts();
            if (!lastAlts.contains(this.username + ":" + this.password)) {
               lastAlts.add(this.username + ":" + this.password);
               Augustus.getInstance().setLastAlts(lastAlts);
            }
         } else {
            this.color = Color.red;
            this.status = "Login Failed!";
            this.loginFailedSound();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public Color getColor() {
      return this.color;
   }

   public String getStatus() {
      return this.status;
   }

   private void loginSuccessSound() {
      String var1 = AugustusSounds.currentSound;
      switch(var1) {
         case "Vanilla":
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            break;
         case "Sigma":
            SoundUtil.play(SoundUtil.loginSuccessful);
      }
   }

   private void loginFailedSound() {
      String var1 = AugustusSounds.currentSound;
      switch(var1) {
         case "Vanilla":
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            break;
         case "Sigma":
            SoundUtil.play(SoundUtil.loginFailed);
      }
   }
}
