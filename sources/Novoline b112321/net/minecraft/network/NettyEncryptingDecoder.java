package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import net.minecraft.network.NettyEncryptionTranslator;

public class NettyEncryptingDecoder extends MessageToMessageDecoder {
   private final NettyEncryptionTranslator decryptionCodec;

   public NettyEncryptingDecoder(Cipher var1) {
      this.decryptionCodec = new NettyEncryptionTranslator(var1);
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws ShortBufferException, Exception {
      var3.add(this.decryptionCodec.decipher(var1, var2));
   }
}
