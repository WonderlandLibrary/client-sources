package net;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.util.Map;

public class vc {
   private static int b;

   public static GameProfile a(MinecraftSessionService var0, GameProfile var1, boolean var2) {
      return var0.fillProfileProperties(var1, var2);
   }

   public static Map b(MinecraftSessionService var0, GameProfile var1, boolean var2) {
      return var0.getTextures(var1, var2);
   }

   public static void a(MinecraftSessionService var0, GameProfile var1, String var2, String var3) {
      var0.joinServer(var1, var2, var3);
   }

   public static GameProfile a(MinecraftSessionService var0, GameProfile var1, String var2) {
      return var0.hasJoinedServer(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 25;
   }

   static {
      if(a() != 0) {
         b(10);
      }

   }
}
