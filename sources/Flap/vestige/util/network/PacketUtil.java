package vestige.util.network;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import vestige.Flap;
import vestige.util.IMinecraft;

public class PacketUtil implements IMinecraft {
   public static List<Packet<?>> skipSendEvent = new ArrayList();

   public static void sendPacket(Packet packet) {
      mc.getNetHandler().getNetworkManager().sendPacket(packet);
   }

   public static void sendPacketFinal(Packet packet) {
      mc.getNetHandler().getNetworkManager().sendPacketFinal(packet);
   }

   public static void sendPacketNoEvent(Packet packet) {
      mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
   }

   public static void receivePacket(Packet<INetHandlerPlayClient> packet) {
      try {
         packet.processPacket(Flap.mc.getNetHandler());
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static void sendPacketNoEvent2(Packet<?> packet) {
      if (packet != null && !packet.getClass().getSimpleName().startsWith("S")) {
         skipSendEvent.add(packet);
         mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
      }
   }

   public static void sendBlocking(boolean callEvent, boolean place) {
      mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
      C08PacketPlayerBlockPlacement packet = place ? new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F) : new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem());
      if (callEvent) {
         sendPacket(packet);
      } else {
         sendPacketNoEvent(packet);
      }

   }

   public static void releaseUseItem(boolean callEvent) {
      C07PacketPlayerDigging packet = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
      if (callEvent) {
         sendPacket(packet);
      } else {
         sendPacketNoEvent(packet);
      }

   }

   public static boolean shouldIgnorePacket(Packet packet) {
      return packet instanceof C00PacketLoginStart || packet instanceof C01PacketEncryptionResponse || packet instanceof C00Handshake || packet instanceof C00PacketServerQuery || packet instanceof C01PacketPing;
   }
}
