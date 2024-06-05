package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.minecraft.network.PacketBuffer;

public class x_ extends ByteToMessageDecoder {
   private final Inflater b;
   private int a;

   public x_(int var1) {
      this.a = var1;
      this.b = new Inflater();
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws DataFormatException, Exception {
      if(var2.readableBytes() != 0) {
         PacketBuffer var4 = new PacketBuffer(var2);
         int var5 = var4.readVarIntFromBuffer();
         var3.add(var4.readBytes(var4.readableBytes()));
      }

   }

   public void a(int var1) {
      this.a = var1;
   }

   private static DataFormatException a(DataFormatException var0) {
      return var0;
   }
}
