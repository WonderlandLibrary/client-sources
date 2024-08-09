/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import net.minecraft.network.PacketBuffer;

public class NettyVarint21FrameDecoder
extends ByteToMessageDecoder {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte[] byArray = new byte[3];
        for (int i = 0; i < byArray.length; ++i) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }
            byArray[i] = byteBuf.readByte();
            if (byArray[i] < 0) continue;
            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(byArray));
            try {
                int n = packetBuffer.readVarInt();
                if (byteBuf.readableBytes() >= n) {
                    list.add(byteBuf.readBytes(n));
                    return;
                }
                byteBuf.resetReaderIndex();
            } finally {
                packetBuffer.release();
            }
            return;
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}

