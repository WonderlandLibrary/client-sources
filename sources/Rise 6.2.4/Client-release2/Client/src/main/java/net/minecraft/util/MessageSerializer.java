package net.minecraft.util;

import com.alan.clients.util.packet.custom.RawPacket;
import de.florianmichael.vialoadingbase.netty.handler.VLBViaEncodeHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;

public class MessageSerializer extends MessageToByteEncoder<Packet> {
    private static final Logger logger = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    private final EnumPacketDirection direction;

    public MessageSerializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }

    protected void encode(final ChannelHandlerContext p_encode_1_, final Packet p_encode_2_, final ByteBuf p_encode_3_) throws Exception {
        Integer integer;

        EnumConnectionState enumConnectionState = p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get();

        if (p_encode_2_ instanceof RawPacket) {
            integer = ((RawPacket) p_encode_2_).getPacketID();
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeVarIntToBuffer(integer);
            p_encode_2_.writePacketData(packetbuffer);
            p_encode_1_.channel().pipeline().context(VLBViaEncodeHandler.class).writeAndFlush(packetbuffer);
            return;
        } else {
            integer = enumConnectionState.getPacketId(this.direction, p_encode_2_);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[]{p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), integer, p_encode_2_.getClass().getName()});
        }

        if (integer == null) {
            throw new IOException("Can't serialize unregistered packet");
        } else {
            final PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
            packetbuffer.writeVarIntToBuffer(integer.intValue());

            try {
                p_encode_2_.writePacketData(packetbuffer);
            } catch (final Throwable throwable) {
                logger.error(throwable);
            }
        }
    }
}
