/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public final class HttpClientCodec
extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder>
implements HttpClientUpgradeHandler.SourceCodec {
    private final Queue<HttpMethod> queue = new ArrayDeque<HttpMethod>();
    private final boolean parseHttpAfterConnectRequest;
    private boolean done;
    private final AtomicLong requestResponseCounter = new AtomicLong();
    private final boolean failOnMissingResponse;

    public HttpClientCodec() {
        this(4096, 8192, 8192, false);
    }

    public HttpClientCodec(int n, int n2, int n3) {
        this(n, n2, n3, false);
    }

    public HttpClientCodec(int n, int n2, int n3, boolean bl) {
        this(n, n2, n3, bl, true);
    }

    public HttpClientCodec(int n, int n2, int n3, boolean bl, boolean bl2) {
        this(n, n2, n3, bl, bl2, false);
    }

    public HttpClientCodec(int n, int n2, int n3, boolean bl, boolean bl2, boolean bl3) {
        this.init(new Decoder(this, n, n2, n3, bl2), new Encoder(this, null));
        this.failOnMissingResponse = bl;
        this.parseHttpAfterConnectRequest = bl3;
    }

    public HttpClientCodec(int n, int n2, int n3, boolean bl, boolean bl2, int n4) {
        this(n, n2, n3, bl, bl2, n4, false);
    }

    public HttpClientCodec(int n, int n2, int n3, boolean bl, boolean bl2, int n4, boolean bl3) {
        this.init(new Decoder(this, n, n2, n3, bl2, n4), new Encoder(this, null));
        this.parseHttpAfterConnectRequest = bl3;
        this.failOnMissingResponse = bl;
    }

    @Override
    public void prepareUpgradeFrom(ChannelHandlerContext channelHandlerContext) {
        ((Encoder)this.outboundHandler()).upgraded = true;
    }

    @Override
    public void upgradeFrom(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        channelPipeline.remove(this);
    }

    public void setSingleDecode(boolean bl) {
        ((HttpResponseDecoder)this.inboundHandler()).setSingleDecode(bl);
    }

    public boolean isSingleDecode() {
        return ((HttpResponseDecoder)this.inboundHandler()).isSingleDecode();
    }

    static boolean access$100(HttpClientCodec httpClientCodec) {
        return httpClientCodec.done;
    }

    static Queue access$200(HttpClientCodec httpClientCodec) {
        return httpClientCodec.queue;
    }

    static boolean access$300(HttpClientCodec httpClientCodec) {
        return httpClientCodec.failOnMissingResponse;
    }

    static AtomicLong access$400(HttpClientCodec httpClientCodec) {
        return httpClientCodec.requestResponseCounter;
    }

    static boolean access$500(HttpClientCodec httpClientCodec) {
        return httpClientCodec.parseHttpAfterConnectRequest;
    }

    static boolean access$102(HttpClientCodec httpClientCodec, boolean bl) {
        httpClientCodec.done = bl;
        return httpClientCodec.done;
    }

    private final class Decoder
    extends HttpResponseDecoder {
        final HttpClientCodec this$0;

        Decoder(HttpClientCodec httpClientCodec, int n, int n2, int n3, boolean bl) {
            this.this$0 = httpClientCodec;
            super(n, n2, n3, bl);
        }

        Decoder(HttpClientCodec httpClientCodec, int n, int n2, int n3, boolean bl, int n4) {
            this.this$0 = httpClientCodec;
            super(n, n2, n3, bl, n4);
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            if (HttpClientCodec.access$100(this.this$0)) {
                int n = this.actualReadableBytes();
                if (n == 0) {
                    return;
                }
                list.add(byteBuf.readBytes(n));
            } else {
                int n = list.size();
                super.decode(channelHandlerContext, byteBuf, list);
                if (HttpClientCodec.access$300(this.this$0)) {
                    int n2 = list.size();
                    for (int i = n; i < n2; ++i) {
                        this.decrement(list.get(i));
                    }
                }
            }
        }

        private void decrement(Object object) {
            if (object == null) {
                return;
            }
            if (object instanceof LastHttpContent) {
                HttpClientCodec.access$400(this.this$0).decrementAndGet();
            }
        }

        @Override
        protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
            int n = ((HttpResponse)httpMessage).status().code();
            if (n == 100 || n == 101) {
                return super.isContentAlwaysEmpty(httpMessage);
            }
            HttpMethod httpMethod = (HttpMethod)HttpClientCodec.access$200(this.this$0).poll();
            char c = httpMethod.name().charAt(0);
            switch (c) {
                case 'H': {
                    if (!HttpMethod.HEAD.equals(httpMethod)) break;
                    return false;
                }
                case 'C': {
                    if (n != 200 || !HttpMethod.CONNECT.equals(httpMethod)) break;
                    if (!HttpClientCodec.access$500(this.this$0)) {
                        HttpClientCodec.access$102(this.this$0, true);
                        HttpClientCodec.access$200(this.this$0).clear();
                    }
                    return false;
                }
            }
            return super.isContentAlwaysEmpty(httpMessage);
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            long l;
            super.channelInactive(channelHandlerContext);
            if (HttpClientCodec.access$300(this.this$0) && (l = HttpClientCodec.access$400(this.this$0).get()) > 0L) {
                channelHandlerContext.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + l + " missing response(s)"));
            }
        }
    }

    private final class Encoder
    extends HttpRequestEncoder {
        boolean upgraded;
        final HttpClientCodec this$0;

        private Encoder(HttpClientCodec httpClientCodec) {
            this.this$0 = httpClientCodec;
        }

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
            if (this.upgraded) {
                list.add(ReferenceCountUtil.retain(object));
                return;
            }
            if (object instanceof HttpRequest && !HttpClientCodec.access$100(this.this$0)) {
                HttpClientCodec.access$200(this.this$0).offer(((HttpRequest)object).method());
            }
            super.encode(channelHandlerContext, object, list);
            if (HttpClientCodec.access$300(this.this$0) && !HttpClientCodec.access$100(this.this$0) && object instanceof LastHttpContent) {
                HttpClientCodec.access$400(this.this$0).incrementAndGet();
            }
        }

        Encoder(HttpClientCodec httpClientCodec, 1 var2_2) {
            this(httpClientCodec);
        }
    }
}

