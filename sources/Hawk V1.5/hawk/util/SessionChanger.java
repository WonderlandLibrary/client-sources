package hawk.util;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionChanger {
   private static SessionChanger instance;
   private final UserAuthentication auth;

   public void setUserOffline(String var1) {
      this.auth.logOut();
      Session var2 = new Session(var1, var1, "0", "legacy");
      this.setSession(var2);
   }

   public void setUser(String var1, String var2) {
      if (!Minecraft.getMinecraft().getSession().getUsername().equals(var1) || Minecraft.getMinecraft().getSession().getToken().equals("0")) {
         this.auth.logOut();
         this.auth.setUsername(var1);
         this.auth.setPassword(var2);

         try {
            this.auth.logIn();
            Session var3 = new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(this.auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName());
            this.setSession(var3);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static SessionChanger getInstance() {
      if (instance == null) {
         instance = new SessionChanger();
      }

      return instance;
   }

   private SessionChanger() {
      UUID var1 = UUID.randomUUID();
      YggdrasilAuthenticationService var2 = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), var1.toString());
      this.auth = var2.createUserAuthentication(Agent.MINECRAFT);
      var2.createMinecraftSessionService();
   }

   private void setSession(Session var1) {
      Minecraft.getMinecraft().session = var1;
   }
}
