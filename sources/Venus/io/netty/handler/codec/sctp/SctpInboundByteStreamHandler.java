/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.sctp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.sctp.SctpMessageCompletionHandler;
import java.util.List;

public class SctpInboundByteStreamHandler
extends MessageToMessageDecoder<SctpMessage> {
    private final int protocolIdentifier;
    private final int streamIdentifier;

    public SctpInboundByteStreamHandler(int n, int n2) {
        this.protocolIdentifier = n;
        this.streamIdentifier = n2;
    }

    @Override
    public final boolean acceptInboundMessage(Object object) throws Exception {
        if (super.acceptInboundMessage(object)) {
            return this.acceptInboundMessage((SctpMessage)object);
        }
        return true;
    }

    protected boolean acceptInboundMessage(SctpMessage sctpMessage) {
        return sctpMessage.protocolIdentifier() == this.protocolIdentifier && sctpMessage.streamIdentifier() == this.streamIdentifier;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, SctpMessage sctpMessage, List<Object> list) throws Exception {
        if (!sctpMessage.isComplete()) {
            throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", SctpMessageCompletionHandler.class.getSimpleName()));
        }
        list.add(sctpMessage.content().retain());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (SctpMessage)object, (List<Object>)list);
    }
}

