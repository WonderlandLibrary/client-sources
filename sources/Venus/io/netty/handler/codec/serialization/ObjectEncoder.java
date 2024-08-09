/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.serialization.CompactObjectOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@ChannelHandler.Sharable
public class ObjectEncoder
extends MessageToByteEncoder<Serializable> {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
        int n = byteBuf.writerIndex();
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf);
        ObjectOutputStream objectOutputStream = null;
        try {
            byteBufOutputStream.write(LENGTH_PLACEHOLDER);
            objectOutputStream = new CompactObjectOutputStream(byteBufOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            } else {
                byteBufOutputStream.close();
            }
        }
        int n2 = byteBuf.writerIndex();
        byteBuf.setInt(n, n2 - n - 4);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Serializable)object, byteBuf);
    }
}

