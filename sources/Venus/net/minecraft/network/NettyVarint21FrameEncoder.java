/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketBuffer;

@ChannelHandler.Sharable
public class NettyVarint21FrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        int n2 = PacketBuffer.getVarIntSize(n);
        if (n2 > 3) {
            throw new IllegalArgumentException("unable to fit " + n + " into 3");
        }
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        packetBuffer.ensureWritable(n2 + n);
        packetBuffer.writeVarInt(n);
        packetBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), n);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

