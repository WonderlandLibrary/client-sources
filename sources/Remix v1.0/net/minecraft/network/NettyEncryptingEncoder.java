package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder
{
    private final NettyEncryptionTranslator encryptionCodec;
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.encryptionCodec.cipher(p_encode_2_, p_encode_3_);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.encode(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
    }
}
