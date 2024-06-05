package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingDecoder extends MessageToMessageDecoder {
   private static final String __OBFID = "CL_00001238";
   private final NettyEncryptionTranslator decryptionCodec;

   protected void decode(ChannelHandlerContext var1, Object var2, List var3) throws ShortBufferException {
      this.decode(var1, (ByteBuf)var2, var3);
   }

   public NettyEncryptingDecoder(Cipher var1) {
      this.decryptionCodec = new NettyEncryptionTranslator(var1);
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws ShortBufferException {
      var3.add(this.decryptionCodec.decipher(var1, var2));
   }
}
