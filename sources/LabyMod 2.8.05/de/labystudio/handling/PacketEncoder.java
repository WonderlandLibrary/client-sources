package de.labystudio.handling;

import de.labystudio.packets.Packet;
import de.labystudio.packets.PacketBuf;
import de.labystudio.packets.Protocol;
import de.labystudio.utils.Debug;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception
    {
        PacketBuf packetbuf = new PacketBuf(byteBuf);
        int i = Protocol.getProtocol().getPacketId(packet);

        if (i != 62 && i != 63)
        {
            Debug.debug("[OUT] " + i + " " + packet.getClass().getSimpleName());
        }

        packetbuf.writeVarIntToBuffer(Protocol.getProtocol().getPacketId(packet));
        packet.write(packetbuf);
    }
}
