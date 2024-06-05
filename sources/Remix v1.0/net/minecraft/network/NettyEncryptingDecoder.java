package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import javax.crypto.*;

public class NettyEncryptingDecoder extends MessageToMessageDecoder
{
    private final NettyEncryptionTranslator decryptionCodec;
    
    public NettyEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        p_decode_3_.add(this.decryptionCodec.decipher(p_decode_1_, p_decode_2_));
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final Object p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        this.decode(p_decode_1_, (ByteBuf)p_decode_2_, p_decode_3_);
    }
}
