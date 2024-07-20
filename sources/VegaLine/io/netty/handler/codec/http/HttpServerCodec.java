/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public final class HttpServerCodec
extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
implements HttpServerUpgradeHandler.SourceCodec {
    private final Queue<HttpMethod> queue = new ArrayDeque<HttpMethod>();

    public HttpServerCodec() {
        this(4096, 8192, 8192);
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
        this.init(new HttpServerRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), new HttpServerResponseEncoder());
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
        this.init(new HttpServerRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), new HttpServerResponseEncoder());
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
        this.init(new HttpServerRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders, initialBufferSize), new HttpServerResponseEncoder());
    }

    @Override
    public void upgradeFrom(ChannelHandlerContext ctx) {
        ctx.pipeline().remove(this);
    }

    private final class HttpServerResponseEncoder
    extends HttpResponseEncoder {
        private HttpServerResponseEncoder() {
        }

        @Override
        boolean isContentAlwaysEmpty(HttpResponse msg) {
            return HttpMethod.HEAD.equals(HttpServerCodec.this.queue.poll());
        }
    }

    private final class HttpServerRequestDecoder
    extends HttpRequestDecoder {
        public HttpServerRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize);
        }

        public HttpServerRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders);
        }

        public HttpServerRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders, initialBufferSize);
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
            int oldSize = out.size();
            super.decode(ctx, buffer, out);
            int size = out.size();
            for (int i = oldSize; i < size; ++i) {
                Object obj = out.get(i);
                if (!(obj instanceof HttpRequest)) continue;
                HttpServerCodec.this.queue.add(((HttpRequest)obj).method());
            }
        }
    }
}

