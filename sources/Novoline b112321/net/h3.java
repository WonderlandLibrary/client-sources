package net;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

public class h3 {
   private static boolean b;

   public static MinecraftSessionService b(YggdrasilAuthenticationService var0) {
      return var0.createMinecraftSessionService();
   }

   public static UserAuthentication a(YggdrasilAuthenticationService var0, Agent var1) {
      return var0.createUserAuthentication(var1);
   }

   public static GameProfileRepository a(YggdrasilAuthenticationService var0) {
      return var0.createProfileRepository();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
