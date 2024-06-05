package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry {
   private final Object value;
   private static final String __OBFID = "CL_00001877";

   public UserListEntry(Object var1) {
      this.value = var1;
   }

   boolean hasBanExpired() {
      return false;
   }

   protected void onSerialization(JsonObject var1) {
   }

   Object getValue() {
      return this.value;
   }

   protected UserListEntry(Object var1, JsonObject var2) {
      this.value = var1;
   }
}
