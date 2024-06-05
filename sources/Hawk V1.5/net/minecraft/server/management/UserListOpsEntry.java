package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListOpsEntry extends UserListEntry {
   private static final String __OBFID = "CL_00001878";
   private final int field_152645_a;

   protected void onSerialization(JsonObject var1) {
      if (this.getValue() != null) {
         var1.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null ? "" : ((GameProfile)this.getValue()).getId().toString());
         var1.addProperty("name", ((GameProfile)this.getValue()).getName());
         super.onSerialization(var1);
         var1.addProperty("level", this.field_152645_a);
      }

   }

   public UserListOpsEntry(JsonObject var1) {
      super(func_152643_b(var1), var1);
      this.field_152645_a = var1.has("level") ? var1.get("level").getAsInt() : 0;
   }

   public UserListOpsEntry(GameProfile var1, int var2) {
      super(var1);
      this.field_152645_a = var2;
   }

   public int func_152644_a() {
      return this.field_152645_a;
   }

   private static GameProfile func_152643_b(JsonObject var0) {
      if (var0.has("uuid") && var0.has("name")) {
         String var1 = var0.get("uuid").getAsString();

         UUID var2;
         try {
            var2 = UUID.fromString(var1);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(var2, var0.get("name").getAsString());
      } else {
         return null;
      }
   }
}
