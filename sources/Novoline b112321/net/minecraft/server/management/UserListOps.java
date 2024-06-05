package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserListEntry;
import net.minecraft.server.management.UserListOpsEntry;

public class UserListOps extends UserList {
   public UserListOps(File var1) {
      super(var1);
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListOpsEntry(var1);
   }

   public String[] getKeys() {
      String[] var1 = new String[this.getValues().size()];
      int var2 = 0;

      for(UserListOpsEntry var4 : this.getValues().values()) {
         var1[var2++] = ((GameProfile)var4.getValue()).getName();
      }

      return var1;
   }

   public boolean func_183026_b(GameProfile var1) {
      UserListOpsEntry var2 = (UserListOpsEntry)this.getEntry(var1);
      return var2.func_183024_b();
   }

   protected String getObjectKey(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile getGameProfileFromName(String var1) {
      for(UserListOpsEntry var3 : this.getValues().values()) {
         if(var1.equalsIgnoreCase(((GameProfile)var3.getValue()).getName())) {
            return (GameProfile)var3.getValue();
         }
      }

      return null;
   }
}
