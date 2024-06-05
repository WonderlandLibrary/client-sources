package net;

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
import org.jetbrains.annotations.NotNull;

public class aw_ extends ByteToMessageDecoder {
   private static final Logger c = LogManager.getLogger();
   private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
   private final EnumPacketDirection a;

   public aw_(EnumPacketDirection var1) {
      this.a = var1;
   }

   protected void decode(@NotNull ChannelHandlerContext var1, @NotNull ByteBuf var2, @NotNull List var3) throws Exception {
      if(var2.readableBytes() != 0) {
         PacketBuffer var4 = new PacketBuffer(var2);
         int var5 = var4.readVarIntFromBuffer();
         Packet var6 = ((EnumConnectionState)var1.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.a, var5);
         throw new IOException("Bad packet id " + var5);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
