package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class NettyCompressionDecoder extends ByteToMessageDecoder {
   private int treshold;
   private static final String __OBFID = "CL_00002314";
   private final Inflater inflater;

   public void setCompressionTreshold(int var1) {
      this.treshold = var1;
   }

   public NettyCompressionDecoder(int var1) {
      this.treshold = var1;
      this.inflater = new Inflater();
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws DataFormatException {
      if (var2.readableBytes() != 0) {
         PacketBuffer var4 = new PacketBuffer(var2);
         int var5 = var4.readVarIntFromBuffer();
         if (var5 == 0) {
            var3.add(var4.readBytes(var4.readableBytes()));
         } else {
            if (var5 < this.treshold) {
               throw new DecoderException(String.valueOf((new StringBuilder("Badly compressed packet - size of ")).append(var5).append(" is below server threshold of ").append(this.treshold)));
            }

            if (var5 > 2097152) {
               throw new DecoderException(String.valueOf((new StringBuilder("Badly compressed packet - size of ")).append(var5).append(" is larger than protocol maximum of ").append(2097152)));
            }

            byte[] var6 = new byte[var4.readableBytes()];
            var4.readBytes(var6);
            this.inflater.setInput(var6);
            byte[] var7 = new byte[var5];
            this.inflater.inflate(var7);
            var3.add(Unpooled.wrappedBuffer(var7));
            this.inflater.reset();
         }
      }

   }
}
