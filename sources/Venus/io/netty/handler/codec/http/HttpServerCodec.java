/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http.HttpStatusClass;
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

    public HttpServerCodec(int n, int n2, int n3) {
        this.init(new HttpServerRequestDecoder(this, n, n2, n3), new HttpServerResponseEncoder(this, null));
    }

    public HttpServerCodec(int n, int n2, int n3, boolean bl) {
        this.init(new HttpServerRequestDecoder(this, n, n2, n3, bl), new HttpServerResponseEncoder(this, null));
    }

    public HttpServerCodec(int n, int n2, int n3, boolean bl, int n4) {
        this.init(new HttpServerRequestDecoder(this, n, n2, n3, bl, n4), new HttpServerResponseEncoder(this, null));
    }

    @Override
    public void upgradeFrom(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(this);
    }

    static Queue access$100(HttpServerCodec httpServerCodec) {
        return httpServerCodec.queue;
    }

    private final class HttpServerResponseEncoder
    extends HttpResponseEncoder {
        private HttpMethod method;
        final HttpServerCodec this$0;

        private HttpServerResponseEncoder(HttpServerCodec httpServerCodec) {
            this.this$0 = httpServerCodec;
        }

        @Override
        protected void sanitizeHeadersBeforeEncode(HttpResponse httpResponse, boolean bl) {
            if (!bl && this.method == HttpMethod.CONNECT && httpResponse.status().codeClass() == HttpStatusClass.SUCCESS) {
                httpResponse.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
                return;
            }
            super.sanitizeHeadersBeforeEncode(httpResponse, bl);
        }

        @Override
        protected boolean isContentAlwaysEmpty(HttpResponse httpResponse) {
            this.method = (HttpMethod)HttpServerCodec.access$100(this.this$0).poll();
            return HttpMethod.HEAD.equals(this.method) || super.isContentAlwaysEmpty(httpResponse);
        }

        @Override
        protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
            return this.isContentAlwaysEmpty((HttpResponse)httpMessage);
        }

        @Override
        protected void sanitizeHeadersBeforeEncode(HttpMessage httpMessage, boolean bl) {
            this.sanitizeHeadersBeforeEncode((HttpResponse)httpMessage, bl);
        }

        HttpServerResponseEncoder(HttpServerCodec httpServerCodec, 1 var2_2) {
            this(httpServerCodec);
        }
    }

    private final class HttpServerRequestDecoder
    extends HttpRequestDecoder {
        final HttpServerCodec this$0;

        public HttpServerRequestDecoder(HttpServerCodec httpServerCodec, int n, int n2, int n3) {
            this.this$0 = httpServerCodec;
            super(n, n2, n3);
        }

        public HttpServerRequestDecoder(HttpServerCodec httpServerCodec, int n, int n2, int n3, boolean bl) {
            this.this$0 = httpServerCodec;
            super(n, n2, n3, bl);
        }

        public HttpServerRequestDecoder(HttpServerCodec httpServerCodec, int n, int n2, int n3, boolean bl, int n4) {
            this.this$0 = httpServerCodec;
            super(n, n2, n3, bl, n4);
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            int n = list.size();
            super.decode(channelHandlerContext, byteBuf, list);
            int n2 = list.size();
            for (int i = n; i < n2; ++i) {
                Object object = list.get(i);
                if (!(object instanceof HttpRequest)) continue;
                HttpServerCodec.access$100(this.this$0).add(((HttpRequest)object).method());
            }
        }
    }
}

