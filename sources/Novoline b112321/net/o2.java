package net;

import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;

public class o2 {
   public static WorldSettings$GameType b(S01PacketJoinGame var0) {
      return var0.getGameType();
   }

   public static boolean a(S01PacketJoinGame var0) {
      return var0.isHardcoreMode();
   }

   public static WorldType c(S01PacketJoinGame var0) {
      return var0.getWorldType();
   }

   public static int e(S01PacketJoinGame var0) {
      return var0.getDimension();
   }

   public static EnumDifficulty g(S01PacketJoinGame var0) {
      return var0.getDifficulty();
   }

   public static int f(S01PacketJoinGame var0) {
      return var0.getEntityId();
   }

   public static int h(S01PacketJoinGame var0) {
      return var0.getMaxPlayers();
   }

   public static boolean d(S01PacketJoinGame var0) {
      return var0.isReducedDebugInfo();
   }
}
