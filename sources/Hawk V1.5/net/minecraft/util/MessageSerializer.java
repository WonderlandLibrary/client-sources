package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageSerializer extends MessageToByteEncoder {
   private final EnumPacketDirection direction;
   private static final String __OBFID = "CL_00001253";
   private static final Marker RECEIVED_PACKET_MARKER;
   private static final Logger logger = LogManager.getLogger();

   protected void encode(ChannelHandlerContext var1, Packet var2, ByteBuf var3) throws IOException {
      Integer var4 = ((EnumConnectionState)var1.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, var2);
      if (logger.isDebugEnabled()) {
         logger.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[]{var1.channel().attr(NetworkManager.attrKeyConnectionState).get(), var4, var2.getClass().getName()});
      }

      if (var4 == null) {
         throw new IOException("Can't serialize unregistered packet");
      } else {
         PacketBuffer var5 = new PacketBuffer(var3);
         var5.writeVarIntToBuffer(var4);

         try {
            if (var2 instanceof S0CPacketSpawnPlayer) {
               var2 = var2;
            }

            var2.writePacketData(var5);
         } catch (Throwable var7) {
            logger.error(var7);
         }

      }
   }

   static {
      RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
   }

   public MessageSerializer(EnumPacketDirection var1) {
      this.direction = var1;
   }

   protected void encode(ChannelHandlerContext var1, Object var2, ByteBuf var3) throws IOException {
      this.encode(var1, (Packet)var2, var3);
   }
}
