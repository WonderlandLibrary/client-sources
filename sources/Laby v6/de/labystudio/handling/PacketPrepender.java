package de.labystudio.handling;

import de.labystudio.packets.PacketBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class PacketPrepender extends ByteToMessageDecoder
{
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> objects)
    {
        buffer.markReaderIndex();
        byte[] abyte = new byte[3];

        for (int i = 0; i < abyte.length; ++i)
        {
            if (!buffer.isReadable())
            {
                buffer.resetReaderIndex();
                return;
            }

            abyte[i] = buffer.readByte();

            if (abyte[i] >= 0)
            {
                PacketBuf packetbuf = new PacketBuf(Unpooled.wrappedBuffer(abyte));

                try
                {
                    int j = packetbuf.readVarIntFromBuffer();

                    if (buffer.readableBytes() >= j)
                    {
                        objects.add(buffer.readBytes(j));
                        return;
                    }

                    buffer.resetReaderIndex();
                }
                finally
                {
                    packetbuf.release();
                }

                return;
            }
        }

        throw new RuntimeException("length wider than 21-bit");
    }
}
