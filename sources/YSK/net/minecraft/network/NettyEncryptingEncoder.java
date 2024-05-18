package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder<ByteBuf>
{
    private final NettyEncryptionTranslator encryptionCodec;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws ShortBufferException, Exception {
        this.encryptionCodec.cipher(byteBuf, byteBuf2);
    }
}
