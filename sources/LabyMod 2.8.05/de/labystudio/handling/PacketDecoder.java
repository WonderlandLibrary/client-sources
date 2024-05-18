package de.labystudio.handling;

import de.labystudio.packets.Packet;
import de.labystudio.packets.PacketBuf;
import de.labystudio.packets.Protocol;
import de.labystudio.utils.Debug;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder
{
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception
    {
        PacketBuf packetbuf = new PacketBuf(byteBuf);

        if (packetbuf.readableBytes() >= 1)
        {
            int i = packetbuf.readVarIntFromBuffer();
            Packet packet = Protocol.getProtocol().getPacket(i);

            if (i != 62 && i != 63)
            {
                Debug.debug("[IN] " + i + " " + packet.getClass().getSimpleName());
            }

            packet.read(packetbuf);

            if (packetbuf.readableBytes() > 0)
            {
                throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuf.readableBytes() + " bytes extra whilst reading packet " + packet);
            }
            else
            {
                objects.add(packet);
            }
        }
    }
}
