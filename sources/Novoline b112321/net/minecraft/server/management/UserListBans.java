package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.server.management.UserListEntry;

public class UserListBans extends UserList {
   public UserListBans(File var1) {
      super(var1);
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListBansEntry(var1);
   }

   public boolean isBanned(GameProfile var1) {
      return this.hasEntry(var1);
   }

   public String[] getKeys() {
      String[] var1 = new String[this.getValues().size()];
      int var2 = 0;

      for(UserListBansEntry var4 : this.getValues().values()) {
         var1[var2++] = ((GameProfile)var4.getValue()).getName();
      }

      return var1;
   }

   protected String getObjectKey(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile isUsernameBanned(String var1) {
      for(UserListBansEntry var3 : this.getValues().values()) {
         if(var1.equalsIgnoreCase(((GameProfile)var3.getValue()).getName())) {
            return (GameProfile)var3.getValue();
         }
      }

      return null;
   }
}
