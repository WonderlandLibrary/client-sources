package cc.slack.utils.network;

import cc.slack.utils.client.mc;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class PacketUtil extends mc {
   public static void send(Packet<?> packet) {
      getNetHandler().getNetworkManager().sendPacket(packet);
   }

   public static void send(Packet<?> packet, int iterations) {
      for(int i = 0; i < iterations; ++i) {
         send(packet);
      }

   }

   public static void sendNoEvent(Packet<?> packet) {
      getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
   }

   public static void sendNoEvent(Packet<?> packet, int iterations) {
      for(int i = 0; i < iterations; ++i) {
         sendNoEvent(packet);
      }

   }

   public static void sendBlocking(boolean callEvent, boolean place) {
      C08PacketPlayerBlockPlacement packet = place ? new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.getPlayer().getHeldItem(), 0.0F, 0.0F, 0.0F) : new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem());
      if (callEvent) {
         send(packet);
      } else {
         sendNoEvent(packet);
      }

   }

   public static void releaseUseItem(boolean callEvent) {
      C07PacketPlayerDigging packet = new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
      if (callEvent) {
         send(packet);
      } else {
         sendNoEvent(packet);
      }

   }
}
