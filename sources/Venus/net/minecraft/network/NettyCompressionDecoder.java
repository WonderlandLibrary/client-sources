/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;
import net.minecraft.network.PacketBuffer;

public class NettyCompressionDecoder
extends ByteToMessageDecoder {
    private final Inflater inflater;
    private int threshold;

    public NettyCompressionDecoder(int n) {
        this.threshold = n;
        this.inflater = new Inflater();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            int n = packetBuffer.readVarInt();
            if (n == 0) {
                list.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
            } else {
                if (n < this.threshold) {
                    throw new DecoderException("Badly compressed packet - size of " + n + " is below server threshold of " + this.threshold);
                }
                if (n > 0x200000) {
                    throw new DecoderException("Badly compressed packet - size of " + n + " is larger than protocol maximum of 2097152");
                }
                byte[] byArray = new byte[packetBuffer.readableBytes()];
                packetBuffer.readBytes(byArray);
                this.inflater.setInput(byArray);
                byte[] byArray2 = new byte[n];
                this.inflater.inflate(byArray2);
                list.add(Unpooled.wrappedBuffer(byArray2));
                this.inflater.reset();
            }
        }
    }

    public void setCompressionThreshold(int n) {
        this.threshold = n;
    }
}

