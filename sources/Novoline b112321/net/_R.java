package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.management.UserListEntry;
import net.minecraft.server.management.UserListWhitelist;

public class _R {
   public static GameProfile a(UserListWhitelist var0, String var1) {
      return var0.func_152706_a(var1);
   }

   public static void a(UserListWhitelist var0, UserListEntry var1) {
      var0.addEntry(var1);
   }

   public static void a(UserListWhitelist var0, Object var1) {
      var0.removeEntry(var1);
   }

   public static String[] a(UserListWhitelist var0) {
      return var0.getKeys();
   }
}
