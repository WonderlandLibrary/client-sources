package net.minecraft.network;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.game.network.EventEncodePacket;

public class NettyPacketEncoder extends MessageToByteEncoder<Packet<?>> {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
	private final EnumPacketDirection direction;

	public NettyPacketEncoder(EnumPacketDirection direction) {
		this.direction = direction;
	}

    protected void encode(ChannelHandlerContext p_encode_1_, Packet<?> p_encode_2_, ByteBuf p_encode_3_) throws IOException, Exception
    {
        Integer integer = ((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacketId(this.direction, p_encode_2_);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[] {p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, p_encode_2_.getClass().getName()});
        }

        if (integer == null)
        {
            throw new IOException("Can\'t serialize unregistered packet");
        }
        else
        {
            PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
//          TODO: Direkt: EventEncodePacket
          final EventEncodePacket event = new EventEncodePacket(p_encode_2_, p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, packetbuffer);
          Direkt.getInstance().getEventManager().call(event);
            packetbuffer.writeVarIntToBuffer(event.getPacketID());

            try
            {
                p_encode_2_.writePacketData(event.getPacketBuffer());
            }
            catch (Throwable throwable)
            {
                LOGGER.error((Object)throwable);
            }
        }
    }
}
