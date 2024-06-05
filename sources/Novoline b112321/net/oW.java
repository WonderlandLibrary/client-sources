package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.management.UserListEntry;
import net.minecraft.server.management.UserListOps;

public class oW {
   public static boolean a(UserListOps var0, GameProfile var1) {
      return var0.func_183026_b(var1);
   }

   public static void a(UserListOps var0, UserListEntry var1) {
      var0.addEntry(var1);
   }

   public static void b(UserListOps var0, Object var1) {
      var0.removeEntry(var1);
   }

   public static String[] a(UserListOps var0) {
      return var0.getKeys();
   }

   public static UserListEntry a(UserListOps var0, Object var1) {
      return var0.getEntry(var1);
   }

   public static GameProfile a(UserListOps var0, String var1) {
      return var0.getGameProfileFromName(var1);
   }
}
