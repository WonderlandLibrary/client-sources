/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http2.EmptyHttp2Headers;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import java.util.Map;

public class HttpToHttp2ConnectionHandler
extends Http2ConnectionHandler {
    private final boolean validateHeaders;
    private int currentStreamId;

    protected HttpToHttp2ConnectionHandler(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings, boolean bl) {
        super(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
        this.validateHeaders = bl;
    }

    private int getStreamId(HttpHeaders httpHeaders) throws Exception {
        return httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), this.connection().local().incrementAndGetNextStreamId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
        if (!(object instanceof HttpMessage) && !(object instanceof HttpContent)) {
            channelHandlerContext.write(object, channelPromise);
            return;
        }
        boolean bl = true;
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        try {
            Iterable<Map.Entry<String, String>> iterable;
            Http2ConnectionEncoder http2ConnectionEncoder = this.encoder();
            boolean bl2 = false;
            if (object instanceof HttpMessage) {
                HttpMessage httpMessage = (HttpMessage)object;
                this.currentStreamId = this.getStreamId(httpMessage.headers());
                iterable = HttpConversionUtil.toHttp2Headers(httpMessage, this.validateHeaders);
                bl2 = object instanceof FullHttpMessage && !((FullHttpMessage)object).content().isReadable();
                HttpToHttp2ConnectionHandler.writeHeaders(channelHandlerContext, http2ConnectionEncoder, this.currentStreamId, httpMessage.headers(), (Http2Headers)iterable, bl2, simpleChannelPromiseAggregator);
            }
            if (!bl2 && object instanceof HttpContent) {
                ReferenceCounted referenceCounted;
                boolean bl3 = false;
                iterable = EmptyHttpHeaders.INSTANCE;
                Http2Headers http2Headers = EmptyHttp2Headers.INSTANCE;
                if (object instanceof LastHttpContent) {
                    bl3 = true;
                    referenceCounted = (LastHttpContent)object;
                    iterable = referenceCounted.trailingHeaders();
                    http2Headers = HttpConversionUtil.toHttp2Headers((HttpHeaders)iterable, this.validateHeaders);
                }
                referenceCounted = ((HttpContent)object).content();
                bl2 = bl3 && ((HttpHeaders)iterable).isEmpty();
                bl = false;
                http2ConnectionEncoder.writeData(channelHandlerContext, this.currentStreamId, (ByteBuf)referenceCounted, 0, bl2, simpleChannelPromiseAggregator.newPromise());
                if (!((HttpHeaders)iterable).isEmpty()) {
                    HttpToHttp2ConnectionHandler.writeHeaders(channelHandlerContext, http2ConnectionEncoder, this.currentStreamId, iterable, http2Headers, true, simpleChannelPromiseAggregator);
                }
            }
        } catch (Throwable throwable) {
            this.onError(channelHandlerContext, true, throwable);
            simpleChannelPromiseAggregator.setFailure(throwable);
        } finally {
            if (bl) {
                ReferenceCountUtil.release(object);
            }
            simpleChannelPromiseAggregator.doneAllocatingPromises();
        }
    }

    private static void writeHeaders(ChannelHandlerContext channelHandlerContext, Http2ConnectionEncoder http2ConnectionEncoder, int n, HttpHeaders httpHeaders, Http2Headers http2Headers, boolean bl, Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator) {
        int n2 = httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), 0);
        short s = httpHeaders.getShort(HttpConversionUtil.ExtensionHeaderNames.STREAM_WEIGHT.text(), (short)16);
        http2ConnectionEncoder.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, false, 0, bl, simpleChannelPromiseAggregator.newPromise());
    }
}

