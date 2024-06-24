package cc.slack.utils.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.status.server.S00PacketServerInfo;

public class BlinkUtil extends mc {
   public static boolean isEnabled = false;
   public static boolean BLINK_INBOUND = false;
   public static boolean BLINK_OUTBOUND = false;
   private static final CopyOnWriteArrayList<Packet> clientPackets = new CopyOnWriteArrayList();
   private static final CopyOnWriteArrayList<Packet> serverPackets = new CopyOnWriteArrayList();

   public static boolean isBlinking() {
      return isEnabled;
   }

   public static void setConfig(boolean inboundState, boolean outboundState) {
      BLINK_INBOUND = inboundState;
      BLINK_OUTBOUND = outboundState;
   }

   public static void enable() {
      enable(false, true);
   }

   public static void enable(boolean inboundState, boolean outboundState) {
      setConfig(inboundState, outboundState);
      isEnabled = true;
      clientPackets.clear();
      serverPackets.clear();
   }

   public static void disable() {
      disable(true);
   }

   public static void disable(boolean releasePackets) {
      if (releasePackets) {
         releasePackets();
      }

      isEnabled = false;
   }

   public static void clearPackets() {
      clearPackets(true, true);
   }

   public static void clearPackets(boolean clearInbound, boolean clearOutbound) {
      if (clearInbound) {
         serverPackets.clear();
      }

      if (clearOutbound) {
         clientPackets.clear();
      }

   }

   public static void releasePackets() {
      releasePackets(true, true);
   }

   public static void releasePackets(boolean releaseInbound, boolean releaseOutgoing) {
      if (releaseInbound) {
         serverPackets.forEach((packet) -> {
            packet.processPacket(mc.getMinecraft().getNetHandler().getNetworkManager().getNetHandler());
            serverPackets.remove(packet);
         });
      }

      if (releaseOutgoing) {
         clientPackets.forEach((packet) -> {
            PacketUtil.sendNoEvent(packet);
            clientPackets.remove(packet);
         });
      }

      clearPackets();
   }

   public static int getSize() {
      return getSize(true, true);
   }

   public static int getSize(boolean sizeInbound, boolean sizeOutgoing) {
      int size = 0;
      if (sizeInbound) {
         size += serverPackets.size();
      }

      if (sizeOutgoing) {
         size += clientPackets.size();
      }

      return size;
   }

   public static boolean handlePacket(PacketEvent event) {
      Packet packet = event.getPacket();
      if (isBlinking()) {
         if (event.getDirection() == PacketDirection.INCOMING && BLINK_INBOUND) {
            if (!(packet instanceof S00PacketDisconnect) && !(packet instanceof S00PacketServerInfo) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S19PacketEntityStatus) && !(packet instanceof S02PacketChat) && !(packet instanceof S3BPacketScoreboardObjective)) {
               serverPackets.add(packet);
               return true;
            }
         } else if (event.getDirection() == PacketDirection.OUTGOING && BLINK_OUTBOUND && !(packet instanceof C00PacketKeepAlive) && !(packet instanceof C00Handshake) && !(packet instanceof C00PacketLoginStart)) {
            clientPackets.add(packet);
            return true;
         }
      }

      return false;
   }
}
