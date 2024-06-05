package net;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.management.PlayerProfileCache;

public class hW {
   public static GameProfile a(PlayerProfileCache var0, String var1) {
      return var0.getGameProfileForUsername(var1);
   }

   public static String[] a(PlayerProfileCache var0) {
      return var0.getUsernames();
   }

   public static void a(PlayerProfileCache var0, GameProfile var1) {
      var0.addEntry(var1);
   }

   public static GameProfile a(PlayerProfileCache var0, UUID var1) {
      return var0.getProfileByUUID(var1);
   }
}
