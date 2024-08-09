/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class DatagramPacketEncoder<M>
extends MessageToMessageEncoder<AddressedEnvelope<M, InetSocketAddress>> {
    private final MessageToMessageEncoder<? super M> encoder;
    static final boolean $assertionsDisabled = !DatagramPacketEncoder.class.desiredAssertionStatus();

    public DatagramPacketEncoder(MessageToMessageEncoder<? super M> messageToMessageEncoder) {
        this.encoder = ObjectUtil.checkNotNull(messageToMessageEncoder, "encoder");
    }

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        if (super.acceptOutboundMessage(object)) {
            AddressedEnvelope addressedEnvelope = (AddressedEnvelope)object;
            return this.encoder.acceptOutboundMessage(addressedEnvelope.content()) && addressedEnvelope.sender() instanceof InetSocketAddress && addressedEnvelope.recipient() instanceof InetSocketAddress;
        }
        return true;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<M, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        if (!$assertionsDisabled && !list.isEmpty()) {
            throw new AssertionError();
        }
        this.encoder.encode(channelHandlerContext, addressedEnvelope.content(), list);
        if (list.size() != 1) {
            throw new EncoderException(StringUtil.simpleClassName(this.encoder) + " must produce only one message.");
        }
        Object object = list.get(0);
        if (!(object instanceof ByteBuf)) {
            throw new EncoderException(StringUtil.simpleClassName(this.encoder) + " must produce only ByteBuf.");
        }
        list.set(0, new DatagramPacket((ByteBuf)object, addressedEnvelope.recipient(), addressedEnvelope.sender()));
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        this.encoder.bind(channelHandlerContext, socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        this.encoder.connect(channelHandlerContext, socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.disconnect(channelHandlerContext, channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.close(channelHandlerContext, channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.deregister(channelHandlerContext, channelPromise);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.read(channelHandlerContext);
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.flush(channelHandlerContext);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.handlerAdded(channelHandlerContext);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.handlerRemoved(channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        this.encoder.exceptionCaught(channelHandlerContext, throwable);
    }

    @Override
    public boolean isSharable() {
        return this.encoder.isSharable();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (AddressedEnvelope)object, (List<Object>)list);
    }
}

