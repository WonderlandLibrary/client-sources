package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.game.network.EventDecodePacket;

import java.io.IOException;
import java.util.List;

public class NettyPacketDecoder extends ByteToMessageDecoder
{
    private static final Logger logger = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.NETWORK_PACKETS_MARKER);
    private final EnumPacketDirection direction;

    public NettyPacketDecoder(EnumPacketDirection direction)
    {
        this.direction = direction;
    }

    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception
    {
        if (p_decode_2_.readableBytes() != 0)
        {
            PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
            int i = packetbuffer.readVarIntFromBuffer();
            Packet<?> packet = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(this.direction, i);

//            TODO: Direkt: EventDecodePacket
            final EventDecodePacket event = new EventDecodePacket(packet, p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), packetbuffer);
            Direkt.getInstance().getEventManager().call(event);

            //System.out.println("State: " + event.getState().name() + ", ID: " + i + ", Packet: " + event.getPacket());

            if (event.isCancelled()) return;

            if (event.getPacket() == null)
            {
                //throw new IOException("Bad packet id " + i);
            }
            else
            {
                /*try {
                    event.getPacket().readPacketData(event.getPacketBuffer());
                } catch (Exception e) {
                    System.err.println("Failed to decode packet with id " + i);
                    e.printStackTrace();
                }*/
                event.getPacket().readPacketData(event.getPacketBuffer());

                if (event.getPacketBuffer().readableBytes() > 0)
                {
                    throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getId() + "/" + i + " (" + event.getPacket().getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + i);
                }
                else
                {
                    p_decode_3_.add(event.getPacket());

                    if (logger.isDebugEnabled())
                    {
                        logger.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", new Object[] {p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), Integer.valueOf(i), event.getPacket().getClass().getName()});
                    }
                }
            }
        }
    }
}
