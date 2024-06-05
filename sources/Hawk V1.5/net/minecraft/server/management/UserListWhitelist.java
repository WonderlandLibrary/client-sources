package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Iterator;

public class UserListWhitelist extends UserList {
   private static final String __OBFID = "CL_00001871";

   public UserListWhitelist(File var1) {
      super(var1);
   }

   protected String func_152704_b(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile func_152706_a(String var1) {
      Iterator var2 = this.getValues().values().iterator();

      while(var2.hasNext()) {
         UserListWhitelistEntry var3 = (UserListWhitelistEntry)var2.next();
         if (var1.equalsIgnoreCase(((GameProfile)var3.getValue()).getName())) {
            return (GameProfile)var3.getValue();
         }
      }

      return null;
   }

   protected String getObjectKey(Object var1) {
      return this.func_152704_b((GameProfile)var1);
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListWhitelistEntry(var1);
   }

   public String[] getKeys() {
      String[] var1 = new String[this.getValues().size()];
      int var2 = 0;

      UserListWhitelistEntry var3;
      for(Iterator var4 = this.getValues().values().iterator(); var4.hasNext(); var1[var2++] = ((GameProfile)var3.getValue()).getName()) {
         var3 = (UserListWhitelistEntry)var4.next();
      }

      return var1;
   }
}
