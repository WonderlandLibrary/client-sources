/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.google.protobuf.nano.MessageNano
 */
package io.netty.handler.codec.protobuf;

import com.google.protobuf.nano.MessageNano;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@ChannelHandler.Sharable
public class ProtobufDecoderNano
extends MessageToMessageDecoder<ByteBuf> {
    private final Class<? extends MessageNano> clazz;

    public ProtobufDecoderNano(Class<? extends MessageNano> clazz) {
        this.clazz = ObjectUtil.checkNotNull(clazz, "You must provide a Class");
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n;
        byte[] byArray;
        int n2 = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            byArray = byteBuf.array();
            n = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            byArray = new byte[n2];
            byteBuf.getBytes(byteBuf.readerIndex(), byArray, 0, n2);
            n = 0;
        }
        MessageNano messageNano = this.clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        list.add(MessageNano.mergeFrom((MessageNano)messageNano, (byte[])byArray, (int)n, (int)n2));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

