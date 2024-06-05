package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import org.apache.logging.log4j.*;

public class MessageSerializer extends MessageToByteEncoder
{
    private static final Logger logger;
    private static final Marker RECEIVED_PACKET_MARKER;
    private final EnumPacketDirection direction;
    
    public MessageSerializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, Packet p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        final Integer var4 = ((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, p_encode_2_);
        if (MessageSerializer.logger.isDebugEnabled()) {
            MessageSerializer.logger.debug(MessageSerializer.RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[] { p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var4, p_encode_2_.getClass().getName() });
        }
        if (var4 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketBuffer var5 = new PacketBuffer(p_encode_3_);
        var5.writeVarIntToBuffer(var4);
        try {
            if (p_encode_2_ instanceof S0CPacketSpawnPlayer) {
                p_encode_2_ = p_encode_2_;
            }
            p_encode_2_.writePacketData(var5);
        }
        catch (Throwable var6) {
            MessageSerializer.logger.error((Object)var6);
        }
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        this.encode(p_encode_1_, (Packet)p_encode_2_, p_encode_3_);
    }
    
    static {
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    }
}
