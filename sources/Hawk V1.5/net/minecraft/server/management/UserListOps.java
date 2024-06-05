package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Iterator;

public class UserListOps extends UserList {
   private static final String __OBFID = "CL_00001879";

   protected String func_152699_b(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile getGameProfileFromName(String var1) {
      Iterator var2 = this.getValues().values().iterator();

      while(var2.hasNext()) {
         UserListOpsEntry var3 = (UserListOpsEntry)var2.next();
         if (var1.equalsIgnoreCase(((GameProfile)var3.getValue()).getName())) {
            return (GameProfile)var3.getValue();
         }
      }

      return null;
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListOpsEntry(var1);
   }

   protected String getObjectKey(Object var1) {
      return this.func_152699_b((GameProfile)var1);
   }

   public String[] getKeys() {
      String[] var1 = new String[this.getValues().size()];
      int var2 = 0;

      UserListOpsEntry var3;
      for(Iterator var4 = this.getValues().values().iterator(); var4.hasNext(); var1[var2++] = ((GameProfile)var3.getValue()).getName()) {
         var3 = (UserListOpsEntry)var4.next();
      }

      return var1;
   }

   public UserListOps(File var1) {
      super(var1);
   }
}
