/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2EventAdapter;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.internal.ObjectUtil;

public class InboundHttp2ToHttpAdapter
extends Http2EventAdapter {
    private static final ImmediateSendDetector DEFAULT_SEND_DETECTOR = new ImmediateSendDetector(){

        @Override
        public boolean mustSendImmediately(FullHttpMessage fullHttpMessage) {
            if (fullHttpMessage instanceof FullHttpResponse) {
                return ((FullHttpResponse)fullHttpMessage).status().codeClass() == HttpStatusClass.INFORMATIONAL;
            }
            if (fullHttpMessage instanceof FullHttpRequest) {
                return fullHttpMessage.headers().contains(HttpHeaderNames.EXPECT);
            }
            return true;
        }

        @Override
        public FullHttpMessage copyIfNeeded(FullHttpMessage fullHttpMessage) {
            if (fullHttpMessage instanceof FullHttpRequest) {
                FullHttpRequest fullHttpRequest = ((FullHttpRequest)fullHttpMessage).replace(Unpooled.buffer(0));
                fullHttpRequest.headers().remove(HttpHeaderNames.EXPECT);
                return fullHttpRequest;
            }
            return null;
        }
    };
    private final int maxContentLength;
    private final ImmediateSendDetector sendDetector;
    private final Http2Connection.PropertyKey messageKey;
    private final boolean propagateSettings;
    protected final Http2Connection connection;
    protected final boolean validateHttpHeaders;

    protected InboundHttp2ToHttpAdapter(Http2Connection http2Connection, int n, boolean bl, boolean bl2) {
        ObjectUtil.checkNotNull(http2Connection, "connection");
        if (n <= 0) {
            throw new IllegalArgumentException("maxContentLength: " + n + " (expected: > 0)");
        }
        this.connection = http2Connection;
        this.maxContentLength = n;
        this.validateHttpHeaders = bl;
        this.propagateSettings = bl2;
        this.sendDetector = DEFAULT_SEND_DETECTOR;
        this.messageKey = http2Connection.newKey();
    }

    protected final void removeMessage(Http2Stream http2Stream, boolean bl) {
        FullHttpMessage fullHttpMessage = (FullHttpMessage)http2Stream.removeProperty(this.messageKey);
        if (bl && fullHttpMessage != null) {
            fullHttpMessage.release();
        }
    }

    protected final FullHttpMessage getMessage(Http2Stream http2Stream) {
        return (FullHttpMessage)http2Stream.getProperty(this.messageKey);
    }

    protected final void putMessage(Http2Stream http2Stream, FullHttpMessage fullHttpMessage) {
        FullHttpMessage fullHttpMessage2 = http2Stream.setProperty(this.messageKey, fullHttpMessage);
        if (fullHttpMessage2 != fullHttpMessage && fullHttpMessage2 != null) {
            fullHttpMessage2.release();
        }
    }

    @Override
    public void onStreamRemoved(Http2Stream http2Stream) {
        this.removeMessage(http2Stream, false);
    }

    protected void fireChannelRead(ChannelHandlerContext channelHandlerContext, FullHttpMessage fullHttpMessage, boolean bl, Http2Stream http2Stream) {
        this.removeMessage(http2Stream, bl);
        HttpUtil.setContentLength(fullHttpMessage, fullHttpMessage.content().readableBytes());
        channelHandlerContext.fireChannelRead(fullHttpMessage);
    }

    protected FullHttpMessage newMessage(Http2Stream http2Stream, Http2Headers http2Headers, boolean bl, ByteBufAllocator byteBufAllocator) throws Http2Exception {
        return this.connection.isServer() ? HttpConversionUtil.toFullHttpRequest(http2Stream.id(), http2Headers, byteBufAllocator, bl) : HttpConversionUtil.toFullHttpResponse(http2Stream.id(), http2Headers, byteBufAllocator, bl);
    }

    protected FullHttpMessage processHeadersBegin(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, Http2Headers http2Headers, boolean bl, boolean bl2, boolean bl3) throws Http2Exception {
        FullHttpMessage fullHttpMessage = this.getMessage(http2Stream);
        boolean bl4 = true;
        if (fullHttpMessage == null) {
            fullHttpMessage = this.newMessage(http2Stream, http2Headers, this.validateHttpHeaders, channelHandlerContext.alloc());
        } else if (bl2) {
            bl4 = false;
            HttpConversionUtil.addHttp2ToHttpHeaders(http2Stream.id(), http2Headers, fullHttpMessage, bl3);
        } else {
            bl4 = false;
            fullHttpMessage = null;
        }
        if (this.sendDetector.mustSendImmediately(fullHttpMessage)) {
            FullHttpMessage fullHttpMessage2 = bl ? null : this.sendDetector.copyIfNeeded(fullHttpMessage);
            this.fireChannelRead(channelHandlerContext, fullHttpMessage, bl4, http2Stream);
            return fullHttpMessage2;
        }
        return fullHttpMessage;
    }

    private void processHeadersEnd(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, FullHttpMessage fullHttpMessage, boolean bl) {
        if (bl) {
            this.fireChannelRead(channelHandlerContext, fullHttpMessage, this.getMessage(http2Stream) != fullHttpMessage, http2Stream);
        } else {
            this.putMessage(http2Stream, fullHttpMessage);
        }
    }

    @Override
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
        Http2Stream http2Stream = this.connection.stream(n);
        FullHttpMessage fullHttpMessage = this.getMessage(http2Stream);
        if (fullHttpMessage == null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Data Frame received for unknown stream id %d", n);
        }
        ByteBuf byteBuf2 = fullHttpMessage.content();
        int n3 = byteBuf.readableBytes();
        if (byteBuf2.readableBytes() > this.maxContentLength - n3) {
            throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Content length exceeded max of %d for stream id %d", this.maxContentLength, n);
        }
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n3);
        if (bl) {
            this.fireChannelRead(channelHandlerContext, fullHttpMessage, false, http2Stream);
        }
        return n3 + n2;
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
        Http2Stream http2Stream = this.connection.stream(n);
        FullHttpMessage fullHttpMessage = this.processHeadersBegin(channelHandlerContext, http2Stream, http2Headers, bl, true, false);
        if (fullHttpMessage != null) {
            this.processHeadersEnd(channelHandlerContext, http2Stream, fullHttpMessage, bl);
        }
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
        Http2Stream http2Stream = this.connection.stream(n);
        FullHttpMessage fullHttpMessage = this.processHeadersBegin(channelHandlerContext, http2Stream, http2Headers, bl2, true, false);
        if (fullHttpMessage != null) {
            if (n2 != 0) {
                fullHttpMessage.headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), n2);
            }
            fullHttpMessage.headers().setShort(HttpConversionUtil.ExtensionHeaderNames.STREAM_WEIGHT.text(), s);
            this.processHeadersEnd(channelHandlerContext, http2Stream, fullHttpMessage, bl2);
        }
    }

    @Override
    public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
        Http2Stream http2Stream = this.connection.stream(n);
        FullHttpMessage fullHttpMessage = this.getMessage(http2Stream);
        if (fullHttpMessage != null) {
            this.onRstStreamRead(http2Stream, fullHttpMessage);
        }
        channelHandlerContext.fireExceptionCaught(Http2Exception.streamError(n, Http2Error.valueOf(l), "HTTP/2 to HTTP layer caught stream reset", new Object[0]));
    }

    @Override
    public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
        FullHttpMessage fullHttpMessage;
        Http2Stream http2Stream = this.connection.stream(n2);
        if (http2Headers.status() == null) {
            http2Headers.status(HttpResponseStatus.OK.codeAsText());
        }
        if ((fullHttpMessage = this.processHeadersBegin(channelHandlerContext, http2Stream, http2Headers, false, false, true)) == null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Push Promise Frame received for pre-existing stream id %d", n2);
        }
        fullHttpMessage.headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_PROMISE_ID.text(), n);
        fullHttpMessage.headers().setShort(HttpConversionUtil.ExtensionHeaderNames.STREAM_WEIGHT.text(), (short)16);
        this.processHeadersEnd(channelHandlerContext, http2Stream, fullHttpMessage, false);
    }

    @Override
    public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
        if (this.propagateSettings) {
            channelHandlerContext.fireChannelRead(http2Settings);
        }
    }

    protected void onRstStreamRead(Http2Stream http2Stream, FullHttpMessage fullHttpMessage) {
        this.removeMessage(http2Stream, false);
    }

    private static interface ImmediateSendDetector {
        public boolean mustSendImmediately(FullHttpMessage var1);

        public FullHttpMessage copyIfNeeded(FullHttpMessage var1);
    }
}

