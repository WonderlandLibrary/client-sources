package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import net.minecraft.network.*;

public class MessageSerializer2 extends MessageToByteEncoder<ByteBuf>
{
    private static final String[] I;
    
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("-\u0007\u00160\u0007=I\u0003=K>\u0000\u0003r", "XiwRk");
        MessageSerializer2.I[" ".length()] = I("Y\u0007\r2\fY", "yncFc");
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        final int varIntSize = PacketBuffer.getVarIntSize(readableBytes);
        if (varIntSize > "   ".length()) {
            throw new IllegalArgumentException(MessageSerializer2.I["".length()] + readableBytes + MessageSerializer2.I[" ".length()] + "   ".length());
        }
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        packetBuffer.ensureWritable(varIntSize + readableBytes);
        packetBuffer.writeVarIntToBuffer(readableBytes);
        packetBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
    }
}
