package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageDeserializer extends ByteToMessageDecoder {
   private final EnumPacketDirection direction;
   private static final String __OBFID = "CL_00001252";
   private static final Logger logger = LogManager.getLogger();
   private static final Marker RECEIVED_PACKET_MARKER;

   static {
      RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws IllegalAccessException, InstantiationException, IOException {
      if (var2.readableBytes() != 0) {
         PacketBuffer var4 = new PacketBuffer(var2);
         int var5 = var4.readVarIntFromBuffer();
         Packet var6 = ((EnumConnectionState)var1.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, var5);
         if (var6 == null) {
            throw new IOException(String.valueOf((new StringBuilder("Bad packet id ")).append(var5)));
         }

         var6.readPacketData(var4);
         if (var4.readableBytes() > 0) {
            throw new IOException(String.valueOf((new StringBuilder("Packet ")).append(((EnumConnectionState)var1.channel().attr(NetworkManager.attrKeyConnectionState).get()).getId()).append("/").append(var5).append(" (").append(var6.getClass().getSimpleName()).append(") was larger than I expected, found ").append(var4.readableBytes()).append(" bytes extra whilst reading packet ").append(var5)));
         }

         var3.add(var6);
         if (logger.isDebugEnabled()) {
            logger.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", new Object[]{var1.channel().attr(NetworkManager.attrKeyConnectionState).get(), var5, var6.getClass().getName()});
         }
      }

   }

   public MessageDeserializer(EnumPacketDirection var1) {
      this.direction = var1;
   }
}
