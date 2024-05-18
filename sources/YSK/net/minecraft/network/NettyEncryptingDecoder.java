package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import java.util.*;
import javax.crypto.*;

public class NettyEncryptingDecoder extends MessageToMessageDecoder<ByteBuf>
{
    private final NettyEncryptionTranslator decryptionCodec;
    
    public NettyEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, (List<Object>)list);
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws ShortBufferException, Exception {
        list.add(this.decryptionCodec.decipher(channelHandlerContext, byteBuf));
    }
}
