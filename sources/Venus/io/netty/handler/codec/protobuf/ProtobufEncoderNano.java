/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.google.protobuf.nano.CodedOutputByteBufferNano
 *  com.google.protobuf.nano.MessageNano
 */
package io.netty.handler.codec.protobuf;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public class ProtobufEncoderNano
extends MessageToMessageEncoder<MessageNano> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageNano messageNano, List<Object> list) throws Exception {
        int n = messageNano.getSerializedSize();
        ByteBuf byteBuf = channelHandlerContext.alloc().heapBuffer(n, n);
        byte[] byArray = byteBuf.array();
        CodedOutputByteBufferNano codedOutputByteBufferNano = CodedOutputByteBufferNano.newInstance((byte[])byArray, (int)byteBuf.arrayOffset(), (int)byteBuf.capacity());
        messageNano.writeTo(codedOutputByteBufferNano);
        byteBuf.writerIndex(n);
        list.add(byteBuf);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (MessageNano)object, (List<Object>)list);
    }
}

