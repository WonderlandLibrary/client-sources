package net;

import java.util.List;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public class aap {
   public static void c(EntityTrackerEntry var0, EntityPlayerMP var1) {
      var0.updatePlayerEntity(var1);
   }

   public static void a(EntityTrackerEntry var0, List var1) {
      var0.updatePlayerEntities(var1);
   }

   public static void a(EntityTrackerEntry var0, EntityPlayerMP var1) {
      var0.removeFromTrackedPlayers(var1);
   }

   public static void a(EntityTrackerEntry var0) {
      var0.sendDestroyEntityPacketToTrackedPlayers();
   }

   public static void b(EntityTrackerEntry var0, List var1) {
      var0.updatePlayerList(var1);
   }

   public static void a(EntityTrackerEntry var0, Packet var1) {
      var0.sendPacketToTrackedPlayers(var1);
   }

   public static void b(EntityTrackerEntry var0, Packet var1) {
      var0.func_151261_b(var1);
   }

   public static void b(EntityTrackerEntry var0, EntityPlayerMP var1) {
      var0.removeTrackedPlayerSymmetric(var1);
   }
}
