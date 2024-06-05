package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.UUID;
import net.minecraft.server.management.BanEntry;

public class UserListBansEntry extends BanEntry {
   public UserListBansEntry(GameProfile var1) {
      this(var1, (Date)null, (String)null, (Date)null, (String)null);
   }

   public UserListBansEntry(GameProfile var1, Date var2, String var3, Date var4, String var5) {
      super(var1, var4, var3, var4, var5);
   }

   public UserListBansEntry(JsonObject var1) {
      super(func_152648_b(var1), var1);
   }

   protected void onSerialization(JsonObject var1) {
      if(this.getValue() != null) {
         var1.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null?"":((GameProfile)this.getValue()).getId().toString());
         var1.addProperty("name", ((GameProfile)this.getValue()).getName());
         super.onSerialization(var1);
      }

   }

   private static GameProfile func_152648_b(JsonObject var0) {
      if(var0.has("uuid") && var0.has("name")) {
         String var1 = var0.get("uuid").getAsString();
         String var10000 = var1;

         UUID var2;
         try {
            var2 = UUID.fromString(var10000);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(var2, var0.get("name").getAsString());
      } else {
         return null;
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
