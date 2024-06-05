package hawk.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

public final class AltLoginThread extends Thread {
   private String status;
   private final String username;
   private final String password;
   private Minecraft mc = Minecraft.getMinecraft();

   public void run() {
      if (this.password.equals("")) {
         this.mc.session = new Session(this.username, "", "", "mojang");
         this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GREEN).append("Logged in. (").append(this.username).append(" - offline name)"));
      } else {
         this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append("Logging in..."));
         Session var1 = this.createSession(this.username, this.password);
         if (var1 == null) {
            this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Login failed!"));
         } else {
            this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GREEN).append("Logged in. (").append(var1.getUsername()).append(")"));
            this.mc.session = var1;
         }

      }
   }

   public AltLoginThread(String var1, String var2) {
      super("Alt Login Thread");
      this.username = var1;
      this.password = var2;
      this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append("Waiting..."));
   }

   private Session createSession(String var1, String var2) {
      YggdrasilAuthenticationService var3 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
      YggdrasilUserAuthentication var4 = (YggdrasilUserAuthentication)var3.createUserAuthentication(Agent.MINECRAFT);
      var4.setUsername(var1);
      var4.setPassword(var2);

      try {
         var4.logIn();
         return new Session(var4.getSelectedProfile().getName(), var4.getSelectedProfile().getId().toString(), var4.getAuthenticatedToken(), "mojang");
      } catch (AuthenticationException var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public void setStatus(String var1) {
      this.status = var1;
   }

   public String getStatus() {
      return this.status;
   }
}
