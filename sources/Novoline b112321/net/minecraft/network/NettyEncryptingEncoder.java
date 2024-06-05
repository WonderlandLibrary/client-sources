package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import net.minecraft.network.NettyEncryptionTranslator;

public class NettyEncryptingEncoder extends MessageToByteEncoder {
   private final NettyEncryptionTranslator encryptionCodec;

   public NettyEncryptingEncoder(Cipher var1) {
      this.encryptionCodec = new NettyEncryptionTranslator(var1);
   }

   protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws ShortBufferException, Exception {
      this.encryptionCodec.cipher(var2, var3);
   }
}
