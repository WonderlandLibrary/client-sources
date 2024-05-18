package net.minecraft.util;

import io.netty.handler.codec.*;
import org.apache.logging.log4j.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.util.*;
import java.io.*;
import net.minecraft.network.*;

public class MessageDeserializer extends ByteToMessageDecoder
{
    private static final String[] I;
    private final EnumPacketDirection direction;
    private static final Logger logger;
    private static final Marker RECEIVED_PACKET_MARKER;
    
    static {
        I();
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker(MessageDeserializer.I["".length()], NetworkManager.logMarkerPackets);
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws InstantiationException, Exception, IOException, IllegalAccessException {
        if (byteBuf.readableBytes() != 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            final Packet packet = ((EnumConnectionState)channelHandlerContext.channel().attr((AttributeKey)NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, varIntFromBuffer);
            if (packet == null) {
                throw new IOException(MessageDeserializer.I[" ".length()] + varIntFromBuffer);
            }
            packet.readPacketData(packetBuffer);
            if (packetBuffer.readableBytes() > 0) {
                throw new IOException(MessageDeserializer.I["  ".length()] + ((EnumConnectionState)channelHandlerContext.channel().attr((AttributeKey)NetworkManager.attrKeyConnectionState).get()).getId() + MessageDeserializer.I["   ".length()] + varIntFromBuffer + MessageDeserializer.I[0x6 ^ 0x2] + packet.getClass().getSimpleName() + MessageDeserializer.I[0x8E ^ 0x8B] + packetBuffer.readableBytes() + MessageDeserializer.I[0x77 ^ 0x71] + varIntFromBuffer);
            }
            list.add(packet);
            if (MessageDeserializer.logger.isDebugEnabled()) {
                final Logger logger = MessageDeserializer.logger;
                final Marker received_PACKET_MARKER = MessageDeserializer.RECEIVED_PACKET_MARKER;
                final String s = MessageDeserializer.I[0x9F ^ 0x98];
                final Object[] array = new Object["   ".length()];
                array["".length()] = channelHandlerContext.channel().attr((AttributeKey)NetworkManager.attrKeyConnectionState).get();
                array[" ".length()] = varIntFromBuffer;
                array["  ".length()] = packet.getClass().getName();
                logger.debug(received_PACKET_MARKER, s, array);
            }
        }
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MessageDeserializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    private static void I() {
        (I = new String[0x75 ^ 0x7D])["".length()] = I("\u0003\u00110*\u0001\u0007\u000f!$\u0007\u0016\u0019%$\u0000", "SPsaD");
        MessageDeserializer.I[" ".length()] = I("-\u00152P\u001c\u000e\u0017=\u0015\u0018O\u001d2P", "otVpl");
        MessageDeserializer.I["  ".length()] = I("\u0019\u000f\u001912=N", "InzZW");
        MessageDeserializer.I["   ".length()] = I("W", "xblJf");
        MessageDeserializer.I[0xB4 ^ 0xB0] = I("p|", "PTusQ");
        MessageDeserializer.I[0x70 ^ 0x75] = I("eC>'>l\u000f(4*)\u0011i2%-\ri\u000fm)\u001b9#.8\u0006-jm*\f<()l", "LcIFM");
        MessageDeserializer.I[0x90 ^ 0x96] = I("c:5\u0013\u000f0x)\u001f\u001e19l\u0010\u0002*4?\u0013J1=-\u0003\u0003-?l\u0017\u000b 3)\u0013J", "CXLgj");
        MessageDeserializer.I[0xC5 ^ 0xC2] = I("g\u001e*If\u001c,\u0019I=:\nD\b;", "GWdsF");
    }
}
