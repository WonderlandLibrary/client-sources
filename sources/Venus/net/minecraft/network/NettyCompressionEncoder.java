/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;
import net.minecraft.network.PacketBuffer;

public class NettyCompressionEncoder
extends MessageToByteEncoder<ByteBuf> {
    private final byte[] buffer = new byte[8192];
    private final Deflater deflater;
    private int threshold;

    public NettyCompressionEncoder(int n) {
        this.threshold = n;
        this.deflater = new Deflater();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        if (n < this.threshold) {
            packetBuffer.writeVarInt(0);
            packetBuffer.writeBytes(byteBuf);
        } else {
            byte[] byArray = new byte[n];
            byteBuf.readBytes(byArray);
            packetBuffer.writeVarInt(byArray.length);
            this.deflater.setInput(byArray, 0, n);
            this.deflater.finish();
            while (!this.deflater.finished()) {
                int n2 = this.deflater.deflate(this.buffer);
                packetBuffer.writeBytes(this.buffer, 0, n2);
            }
            this.deflater.reset();
        }
    }

    public void setCompressionThreshold(int n) {
        this.threshold = n;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

