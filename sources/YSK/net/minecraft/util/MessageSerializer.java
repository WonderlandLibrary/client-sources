package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.*;
import java.io.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;

public class MessageSerializer extends MessageToByteEncoder<Packet>
{
    private final EnumPacketDirection direction;
    private static final Marker RECEIVED_PACKET_MARKER;
    private static final String[] I;
    private static final Logger logger;
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Packet packet, final ByteBuf byteBuf) throws IOException, Exception {
        final Integer packetId = ((EnumConnectionState)channelHandlerContext.channel().attr((AttributeKey)NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, packet);
        if (MessageSerializer.logger.isDebugEnabled()) {
            final Logger logger = MessageSerializer.logger;
            final Marker received_PACKET_MARKER = MessageSerializer.RECEIVED_PACKET_MARKER;
            final String s = MessageSerializer.I[" ".length()];
            final Object[] array = new Object["   ".length()];
            array["".length()] = channelHandlerContext.channel().attr((AttributeKey)NetworkManager.attrKeyConnectionState).get();
            array[" ".length()] = packetId;
            array["  ".length()] = packet.getClass().getName();
            logger.debug(received_PACKET_MARKER, s, array);
        }
        if (packetId == null) {
            throw new IOException(MessageSerializer.I["  ".length()]);
        }
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeVarIntToBuffer(packetId);
        try {
            packet.writePacketData(packetBuffer);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        catch (Throwable t) {
            MessageSerializer.logger.error((Object)t);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Packet)o, byteBuf);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("1\t19\u00175\u0017!7\u001c5", "aHrrR");
        MessageSerializer.I[" ".length()] = I("7\u0001?LA#/\u0016L\u001a\u0005\tK\r\u001c", "xTkva");
        MessageSerializer.I["  ".length()] = I("\u000e\u001b9P\u000em\t2\u0005\u0013,\u0016>\r\u001fm\u000f9\u0005\u001f*\u0013$\u0003\u001f?\u001f3W\n,\u0019<\u0012\u000e", "MzWwz");
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker(MessageSerializer.I["".length()], NetworkManager.logMarkerPackets);
    }
    
    public MessageSerializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
}
