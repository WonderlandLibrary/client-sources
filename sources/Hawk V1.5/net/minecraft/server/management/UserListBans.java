package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Iterator;

public class UserListBans extends UserList {
   private static final String __OBFID = "CL_00001873";

   public boolean isBanned(GameProfile var1) {
      return this.hasEntry(var1);
   }

   public GameProfile isUsernameBanned(String var1) {
      Iterator var2 = this.getValues().values().iterator();

      while(var2.hasNext()) {
         UserListBansEntry var3 = (UserListBansEntry)var2.next();
         if (var1.equalsIgnoreCase(((GameProfile)var3.getValue()).getName())) {
            return (GameProfile)var3.getValue();
         }
      }

      return null;
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListBansEntry(var1);
   }

   public String[] getKeys() {
      String[] var1 = new String[this.getValues().size()];
      int var2 = 0;

      UserListBansEntry var3;
      for(Iterator var4 = this.getValues().values().iterator(); var4.hasNext(); var1[var2++] = ((GameProfile)var3.getValue()).getName()) {
         var3 = (UserListBansEntry)var4.next();
      }

      return var1;
   }

   public UserListBans(File var1) {
      super(var1);
   }

   protected String getProfileId(GameProfile var1) {
      return var1.getId().toString();
   }

   protected String getObjectKey(Object var1) {
      return this.getProfileId((GameProfile)var1);
   }
}
