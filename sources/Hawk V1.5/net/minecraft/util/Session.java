package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Map;
import java.util.UUID;

public class Session {
   private final String playerID;
   private final String token;
   private final Session.Type sessionType;
   private static final String __OBFID = "CL_00000659";
   private final String username;

   public String getSessionID() {
      return String.valueOf((new StringBuilder("token:")).append(this.token).append(":").append(this.playerID));
   }

   public String getUsername() {
      return this.username;
   }

   public Session.Type getSessionType() {
      return this.sessionType;
   }

   public String getToken() {
      return this.token;
   }

   public String getPlayerID() {
      return this.playerID;
   }

   public Session(String var1, String var2, String var3, String var4) {
      this.username = var1;
      this.playerID = var2;
      this.token = var3;
      this.sessionType = Session.Type.setSessionType(var4);
   }

   public GameProfile getProfile() {
      try {
         UUID var1 = UUIDTypeAdapter.fromString(this.getPlayerID());
         return new GameProfile(var1, this.getUsername());
      } catch (IllegalArgumentException var2) {
         return new GameProfile((UUID)null, this.getUsername());
      }
   }

   public static enum Type {
      private static final String __OBFID = "CL_00001851";
      private static final Session.Type[] ENUM$VALUES = new Session.Type[]{LEGACY, MOJANG};
      MOJANG("MOJANG", 1, "mojang");

      private static final Session.Type[] $VALUES = new Session.Type[]{LEGACY, MOJANG};
      LEGACY("LEGACY", 0, "legacy");

      private static final Map field_152425_c = Maps.newHashMap();
      private final String sessionType;

      private Type(String var3, int var4, String var5) {
         this.sessionType = var5;
      }

      static {
         Session.Type[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Session.Type var3 = var0[var2];
            field_152425_c.put(var3.sessionType, var3);
         }

      }

      public static Session.Type setSessionType(String var0) {
         return (Session.Type)field_152425_c.get(var0.toLowerCase());
      }
   }
}
