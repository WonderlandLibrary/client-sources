package net;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class at4 {
   public static void b(YggdrasilUserAuthentication var0, String var1) {
      var0.setUsername(var1);
   }

   public static void a(YggdrasilUserAuthentication var0, String var1) {
      var0.setPassword(var1);
   }

   public static void a(YggdrasilUserAuthentication var0) {
      var0.logIn();
   }

   public static GameProfile c(YggdrasilUserAuthentication var0) {
      return var0.getSelectedProfile();
   }

   public static String b(YggdrasilUserAuthentication var0) {
      return var0.getAuthenticatedToken();
   }
}
