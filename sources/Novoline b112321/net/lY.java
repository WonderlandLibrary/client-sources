package net;

import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;

public class lY {
   public static int d(S11PacketSpawnExperienceOrb var0) {
      return var0.getX();
   }

   public static int e(S11PacketSpawnExperienceOrb var0) {
      return var0.getY();
   }

   public static int a(S11PacketSpawnExperienceOrb var0) {
      return var0.getZ();
   }

   public static int b(S11PacketSpawnExperienceOrb var0) {
      return var0.getXPValue();
   }

   public static int c(S11PacketSpawnExperienceOrb var0) {
      return var0.getEntityID();
   }
}
