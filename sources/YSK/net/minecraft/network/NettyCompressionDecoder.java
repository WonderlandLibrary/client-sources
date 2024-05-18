package net.minecraft.network;

import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.zip.*;

public class NettyCompressionDecoder extends ByteToMessageDecoder
{
    private final Inflater inflater;
    private static final String[] I;
    private int treshold;
    
    private static void I() {
        (I = new String[0x78 ^ 0x7C])["".length()] = I("\u00181+*,z3 +%(5<50>p?'615;fxz#&<0z?)f", "ZPOFU");
        NettyCompressionDecoder.I[" ".length()] = I("k\u00025M\u0016.\u0007)\u001aT8\u000e4\u001b\u00119K2\u0005\u0006.\u0018.\u0002\u0018/K)\u000bT", "KkFmt");
        NettyCompressionDecoder.I["  ".length()] = I("\u0007\"0\r\u001be ;\f\u00127&'\u0012\u0007!c$\u0000\u0001.& AOe0=\u001b\u0007e,2A", "ECTab");
        NettyCompressionDecoder.I["   ".length()] = I("J3\"o\u001b\u000b(6*\u0005J.9.\u0019J*# \u0003\u00059>#W\u0007;)&\u001a\u001f7q \u0011J", "jZQOw");
    }
    
    public NettyCompressionDecoder(final int treshold) {
        this.treshold = treshold;
        this.inflater = new Inflater();
    }
    
    public void setCompressionTreshold(final int treshold) {
        this.treshold = treshold;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws DataFormatException, Exception {
        if (byteBuf.readableBytes() != 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            if (varIntFromBuffer == 0) {
                list.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                if (varIntFromBuffer < this.treshold) {
                    throw new DecoderException(NettyCompressionDecoder.I["".length()] + varIntFromBuffer + NettyCompressionDecoder.I[" ".length()] + this.treshold);
                }
                if (varIntFromBuffer > 1453188 + 93836 - 1205857 + 1755985) {
                    throw new DecoderException(NettyCompressionDecoder.I["  ".length()] + varIntFromBuffer + NettyCompressionDecoder.I["   ".length()] + (173294 + 481056 + 1362512 + 80290));
                }
                final byte[] input = new byte[packetBuffer.readableBytes()];
                packetBuffer.readBytes(input);
                this.inflater.setInput(input);
                final byte[] array = new byte[varIntFromBuffer];
                this.inflater.inflate(array);
                list.add(Unpooled.wrappedBuffer(array));
                this.inflater.reset();
            }
        }
    }
    
    static {
        I();
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
