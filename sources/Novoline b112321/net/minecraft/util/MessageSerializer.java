package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MessageSerializer extends MessageToByteEncoder {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
   private final EnumPacketDirection direction;

   @Contract(
      pure = true
   )
   public MessageSerializer(@NotNull EnumPacketDirection var1) {
      this.direction = var1;
   }

   protected void encode(@NotNull ChannelHandlerContext var1, @NotNull Packet var2, @NotNull ByteBuf var3) throws Exception {
      EnumConnectionState var4 = (EnumConnectionState)var1.channel().attr(NetworkManager.attrKeyConnectionState).get();
      Integer var5 = var4.getPacketId(this.direction, var2);
      if(LOGGER.isDebugEnabled()) {
         LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[]{var4, var5, var2.getClass().getName()});
      }

      throw new IOException("Can\'t serialize unregistered packet");
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
