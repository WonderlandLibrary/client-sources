package net.minecraft.util;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import net.minecraft.util.Session$Type;

public class Session {
   private final String username;
   private final String playerID;
   private final String token;
   private final Session$Type sessionType;

   public Session(String var1, String var2, String var3, String var4) {
      this.username = var1;
      this.playerID = var2;
      this.token = var3;
      this.sessionType = Session$Type.setSessionType(var4);
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
         return new GameProfile(var1, this.username);
      } catch (IllegalArgumentException var2) {
         return new GameProfile((UUID)null, this.username);
      }
   }

   public Session$Type getSessionType() {
      return this.sessionType;
   }
}
