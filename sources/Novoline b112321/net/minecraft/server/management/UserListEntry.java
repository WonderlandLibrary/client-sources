package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry {
   private final Object value;

   public UserListEntry(Object var1) {
      this.value = var1;
   }

   protected UserListEntry(Object var1, JsonObject var2) {
      this.value = var1;
   }

   Object getValue() {
      return this.value;
   }

   boolean hasBanExpired() {
      return false;
   }

   protected void onSerialization(JsonObject var1) {
   }
}
