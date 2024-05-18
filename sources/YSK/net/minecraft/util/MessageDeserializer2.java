package net.minecraft.util;

import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import io.netty.handler.codec.*;

public class MessageDeserializer2 extends ByteToMessageDecoder
{
    private static final String[] I;
    
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        final byte[] array = new byte["   ".length()];
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < array.length) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }
            array[i] = byteBuf.readByte();
            if (array[i] >= 0) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(array));
                try {
                    final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
                    if (byteBuf.readableBytes() >= varIntFromBuffer) {
                        list.add(byteBuf.readBytes(varIntFromBuffer));
                        return;
                    }
                    byteBuf.resetReaderIndex();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                finally {
                    packetBuffer.release();
                }
                packetBuffer.release();
                return;
            }
            ++i;
        }
        throw new CorruptedFrameException(MessageDeserializer2.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*=>\"\u0012.x',\u0002#*p1\u000e'6pwWk:91", "FXPEf");
    }
}
