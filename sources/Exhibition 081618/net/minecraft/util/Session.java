package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import java.util.Map;
import java.util.UUID;

public class Session {
   private final String username;
   private final String playerID;
   private final String token;
   private final Session.Type sessionType;

   public Session(String p_i1098_1_, String p_i1098_2_, String p_i1098_3_, String p_i1098_4_) {
      this.username = p_i1098_1_;
      this.playerID = p_i1098_2_;
      this.token = p_i1098_3_;
      this.sessionType = Session.Type.setSessionType(p_i1098_4_);
   }

   public String getSessionID() {
      return "token:" + this.token + ":" + this.playerID;
   }

   public String getPlayerID() {
      return this.playerID;
   }

   public String getUsername() {
      return this.username;
   }

   public String getToken() {
      return this.token;
   }

   public GameProfile getProfile() {
      try {
         UUID var1 = UUIDTypeAdapter.fromString(this.getPlayerID());
         return new GameProfile(var1, this.getUsername());
      } catch (IllegalArgumentException var2) {
         return new GameProfile((UUID)null, this.getUsername());
      }
   }

   public Session.Type getSessionType() {
      return this.sessionType;
   }

   public static Session loginPassword(String username, String password, Proxy p) {
      if (username != null && username.length() > 0 && password != null && password.length() > 0) {
         YggdrasilAuthenticationService a = new YggdrasilAuthenticationService(p, "");
         YggdrasilUserAuthentication b = (YggdrasilUserAuthentication)a.createUserAuthentication(Agent.MINECRAFT);
         b.setUsername(username);
         b.setPassword(password);

         try {
            b.logIn();
            return new Session(b.getSelectedProfile().getName(), b.getSelectedProfile().getId().toString(), b.getAuthenticatedToken(), "legacy");
         } catch (InvalidCredentialsException var6) {
            var6.printStackTrace();
         } catch (AuthenticationException var7) {
            var7.printStackTrace();
         } catch (Exception var8) {
            var8.printStackTrace();
         }

         return null;
      } else {
         return null;
      }
   }

   public static enum Type {
      LEGACY("LEGACY", 0, "legacy"),
      MOJANG("MOJANG", 1, "mojang");

      private static final Map field_152425_c = Maps.newHashMap();
      private final String sessionType;
      private static final Session.Type[] $VALUES = new Session.Type[]{LEGACY, MOJANG};

      private Type(String p_i1096_1_, int p_i1096_2_, String p_i1096_3_) {
         this.sessionType = p_i1096_3_;
      }

      public static Session.Type setSessionType(String p_152421_0_) {
         return (Session.Type)field_152425_c.get(p_152421_0_.toLowerCase());
      }

      static {
         Session.Type[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Session.Type var3 = var0[var2];
            field_152425_c.put(var3.sessionType, var3);
         }

      }
   }
}
