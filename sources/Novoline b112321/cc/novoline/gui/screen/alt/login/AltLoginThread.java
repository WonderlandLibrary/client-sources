package cc.novoline.gui.screen.alt.login;

import cc.novoline.gui.screen.alt.login.AltLoginListener;
import cc.novoline.gui.screen.alt.login.AltType;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.utils.thealtening.TheAlteningAuthentication;
import cc.novoline.utils.thealtening.service.AlteningServiceType;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import net.Bv;
import net.acE;
import net.minecraft.util.Session;

public final class AltLoginThread {
   private static final TheAlteningAuthentication MOJANG = TheAlteningAuthentication.mojang();
   public final AltCredential credentials;
   public AltLoginListener handler;
   private String caller;

   public AltLoginThread(AltCredential var1, AltLoginListener var2) {
      Bv.b();
      this.credentials = var1;
      this.handler = var2;
      if(var1.getLogin().endsWith("@alt.com")) {
         MOJANG.updateService(AlteningServiceType.THEALTENING);
      }

      MOJANG.updateService(AlteningServiceType.MOJANG);
   }

   public AltLoginThread(AltCredential var1, String var2) {
      Bv.b();
      super();
      this.credentials = var1;
      this.caller = var2;
      if(var1.getLogin().endsWith("@alt.com")) {
         MOJANG.updateService(AlteningServiceType.THEALTENING);
      }

      MOJANG.updateService(AlteningServiceType.MOJANG);
      if(acE.b() == null) {
         Bv.b("tOH0Fb");
      }

   }

   public Session run() {
      String var1 = Bv.b();
      String var2 = !this.credentials.getLogin().endsWith("@alt.com")?this.credentials.getPassword():"1";
      if(var2 == null) {
         Session var4 = new Session(this.credentials.getLogin(), "", "", "mojang");
         if(this.handler != null) {
            this.handler.onLoginSuccess(AltType.CRACKED, var4);
         }

         return var4;
      } else {
         Session var3 = createSession(this.credentials.getLogin(), var2);
         if(this.handler != null) {
            this.handler.onLoginFailed();
         }

         return null;
      }
   }

   public static Session createSession(String var0, String var1) {
      YggdrasilAuthenticationService var2 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
      YggdrasilUserAuthentication var3 = (YggdrasilUserAuthentication)var2.createUserAuthentication(Agent.MINECRAFT);
      var3.setUsername(var0);
      var3.setPassword(var1);

      try {
         var3.logIn();
         GameProfile var4 = var3.getSelectedProfile();
         return new Session(var4.getName(), var4.getId().toString(), var3.getAuthenticatedToken(), "mojang");
      } catch (AuthenticationException var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
