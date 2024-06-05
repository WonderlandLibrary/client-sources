package net;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class aMj {
   public static C02PacketUseEntity$Action a(C02PacketUseEntity var0) {
      return var0.getAction();
   }

   public static Entity a(C02PacketUseEntity var0, World var1) {
      return var0.getEntityFromWorld(var1);
   }

   public static Vec3 b(C02PacketUseEntity var0) {
      return var0.getHitVec();
   }
}
