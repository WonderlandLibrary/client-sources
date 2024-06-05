package net;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.net.Proxy;
import net.Bv;
import net.JP;
import net.Oj;
import net.a1x;
import net.aQ3;
import net.minecraft.util.Session;

public final class alO {
   private static final Oj a = Oj.c();
   public final JP b;
   public ViaPlatformLoader d;
   private String c;

   public alO(JP var1, ViaPlatformLoader var2) {
      Bv.b();
      this.b = var1;
      this.d = var2;
      if(var1.a().endsWith("@alt.com")) {
         a.a(aQ3.THEALTENING);
      }

      a.a(aQ3.MOJANG);
   }

   public alO(JP var1, String var2) {
      Bv.b();
      super();
      this.b = var1;
      this.c = var2;
      if(var1.a().endsWith("@alt.com")) {
         a.a(aQ3.THEALTENING);
      }

      a.a(aQ3.MOJANG);
      if(PacketRemapper.b() == null) {
         Bv.b("tOH0Fb");
      }

   }

   public Session a() {
      String var1 = Bv.b();
      String var2 = !this.b.a().endsWith("@alt.com")?this.b.c():"1";
      if(var2 == null) {
         Session var4 = new Session(this.b.a(), "", "", "mojang");
         if(this.d != null) {
            this.d.a(a1x.CRACKED, var4);
         }

         return var4;
      } else {
         Session var3 = a(this.b.a(), var2);
         if(this.d != null) {
            this.d.unload();
         }

         return null;
      }
   }

   public static Session a(String var0, String var1) {
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
