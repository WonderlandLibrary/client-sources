package net.minecraft.util;

import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import io.netty.handler.codec.*;

public class MessageDeserializer2 extends ByteToMessageDecoder
{
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) {
        p_decode_2_.markReaderIndex();
        final byte[] var4 = new byte[3];
        for (int var5 = 0; var5 < var4.length; ++var5) {
            if (!p_decode_2_.isReadable()) {
                p_decode_2_.resetReaderIndex();
                return;
            }
            var4[var5] = p_decode_2_.readByte();
            if (var4[var5] >= 0) {
                final PacketBuffer var6 = new PacketBuffer(Unpooled.wrappedBuffer(var4));
                try {
                    final int var7 = var6.readVarIntFromBuffer();
                    if (p_decode_2_.readableBytes() >= var7) {
                        p_decode_3_.add(p_decode_2_.readBytes(var7));
                        return;
                    }
                    p_decode_2_.resetReaderIndex();
                }
                finally {
                    var6.release();
                }
                return;
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}
