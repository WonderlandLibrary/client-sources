/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

public class HttpServerExpectContinueHandler
extends ChannelInboundHandlerAdapter {
    private static final FullHttpResponse EXPECTATION_FAILED = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse ACCEPT = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);

    protected HttpResponse acceptMessage(HttpRequest httpRequest) {
        return ACCEPT.retainedDuplicate();
    }

    protected HttpResponse rejectResponse(HttpRequest httpRequest) {
        return EXPECTATION_FAILED.retainedDuplicate();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        HttpRequest httpRequest;
        if (object instanceof HttpRequest && HttpUtil.is100ContinueExpected(httpRequest = (HttpRequest)object)) {
            HttpResponse httpResponse = this.acceptMessage(httpRequest);
            if (httpResponse == null) {
                HttpResponse httpResponse2 = this.rejectResponse(httpRequest);
                ReferenceCountUtil.release(object);
                channelHandlerContext.writeAndFlush(httpResponse2).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                return;
            }
            channelHandlerContext.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            httpRequest.headers().remove(HttpHeaderNames.EXPECT);
        }
        super.channelRead(channelHandlerContext, object);
    }

    static {
        EXPECTATION_FAILED.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
        ACCEPT.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
    }
}

