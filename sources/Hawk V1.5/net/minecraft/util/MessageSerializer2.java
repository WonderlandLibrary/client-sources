package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketBuffer;

public class MessageSerializer2 extends MessageToByteEncoder {
   private static final String __OBFID = "CL_00001256";

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) {
      int var4 = var2.readableBytes();
      int var5 = PacketBuffer.getVarIntSize(var4);
      if (var5 > 3) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("unable to fit ")).append(var4).append(" into ").append(3)));
      } else {
         PacketBuffer var6 = new PacketBuffer(var3);
         var6.ensureWritable(var5 + var4);
         var6.writeVarIntToBuffer(var4);
         var6.writeBytes(var2, var2.readerIndex(), var4);
      }
   }

   protected void encode(ChannelHandlerContext var1, Object var2, ByteBuf var3) {
      this.encode(var1, (ByteBuf)var2, var3);
   }
}
