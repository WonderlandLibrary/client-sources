package net;

import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.world.EnumDifficulty;

public class Ex {
   public static EnumDifficulty a(S41PacketServerDifficulty var0) {
      return var0.getDifficulty();
   }

   public static boolean b(S41PacketServerDifficulty var0) {
      return var0.isDifficultyLocked();
   }
}
