package de.labystudio.handling;

import de.labystudio.packets.PacketBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketSplitter extends MessageToByteEncoder<ByteBuf>
{
    protected void encode(ChannelHandlerContext ctx, ByteBuf buffer, ByteBuf byteBuf)
    {
        int i = buffer.readableBytes();
        int j = PacketBuf.getVarIntSize(i);

        if (j > 3)
        {
            throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
        }
        else
        {
            PacketBuf packetbuf = new PacketBuf(byteBuf);
            packetbuf.ensureWritable(j + i);
            packetbuf.writeVarIntToBuffer(i);
            packetbuf.writeBytes(buffer, buffer.readerIndex(), i);
        }
    }
}
