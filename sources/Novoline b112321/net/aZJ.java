package net;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

public class aZJ {
   public static WorldServer a(PlayerManager var0) {
      return var0.getWorldServer();
   }

   public static void b(PlayerManager var0) {
      var0.updatePlayerInstances();
   }

   public static boolean a(PlayerManager var0, int var1, int var2) {
      return var0.hasPlayerInstance(var1, var2);
   }

   public static boolean a(PlayerManager var0, EntityPlayerMP var1, int var2, int var3) {
      return var0.isPlayerWatchingChunk(var1, var2, var3);
   }

   public static void b(PlayerManager var0, EntityPlayerMP var1) {
      var0.removePlayer(var1);
   }

   public static void c(PlayerManager var0, EntityPlayerMP var1) {
      var0.addPlayer(var1);
   }

   public static int a(int var0) {
      return PlayerManager.getFurthestViewableBlock(var0);
   }

   public static void a(PlayerManager var0, EntityPlayerMP var1) {
      var0.updateMountedMovingPlayer(var1);
   }

   public static void a(PlayerManager var0, int var1) {
      var0.setPlayerViewRadius(var1);
   }
}
