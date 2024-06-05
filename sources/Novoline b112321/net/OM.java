package net;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.world.chunk.Chunk;

public class OM {
   public static void a(EntityTracker var0, Entity var1) {
      var0.trackEntity(var1);
   }

   public static void b(EntityTracker var0, Entity var1) {
      var0.untrackEntity(var1);
   }

   public static void a(EntityTracker var0, Entity var1, Packet var2) {
      var0.sendToAllTrackingEntity(var1, var2);
   }

   public static void b(EntityTracker var0, Entity var1, Packet var2) {
      var0.func_151248_b(var1, var2);
   }

   public static void a(EntityTracker var0, EntityPlayerMP var1) {
      var0.removePlayerFromTrackers(var1);
   }

   public static void a(EntityTracker var0) {
      var0.updateTrackedEntities();
   }

   public static void a(EntityTracker var0, EntityPlayerMP var1, Chunk var2) {
      var0.func_85172_a(var1, var2);
   }

   public static void b(EntityTracker var0, EntityPlayerMP var1) {
      var0.func_180245_a(var1);
   }
}
