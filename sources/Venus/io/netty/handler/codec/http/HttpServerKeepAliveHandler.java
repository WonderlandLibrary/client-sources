/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;

public class HttpServerKeepAliveHandler
extends ChannelDuplexHandler {
    private static final String MULTIPART_PREFIX = "multipart";
    private boolean persistentConnection = true;
    private int pendingResponses;

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)object;
            if (this.persistentConnection) {
                ++this.pendingResponses;
                this.persistentConnection = HttpUtil.isKeepAlive(httpRequest);
            }
        }
        super.channelRead(channelHandlerContext, object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (object instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse)object;
            this.trackResponse(httpResponse);
            if (!HttpUtil.isKeepAlive(httpResponse) || !HttpServerKeepAliveHandler.isSelfDefinedMessageLength(httpResponse)) {
                this.pendingResponses = 0;
                this.persistentConnection = false;
            }
            if (!this.shouldKeepAlive()) {
                HttpUtil.setKeepAlive(httpResponse, false);
            }
        }
        if (object instanceof LastHttpContent && !this.shouldKeepAlive()) {
            channelPromise = channelPromise.unvoid().addListener(ChannelFutureListener.CLOSE);
        }
        super.write(channelHandlerContext, object, channelPromise);
    }

    private void trackResponse(HttpResponse httpResponse) {
        if (!HttpServerKeepAliveHandler.isInformational(httpResponse)) {
            --this.pendingResponses;
        }
    }

    private boolean shouldKeepAlive() {
        return this.pendingResponses != 0 || this.persistentConnection;
    }

    private static boolean isSelfDefinedMessageLength(HttpResponse httpResponse) {
        return HttpUtil.isContentLengthSet(httpResponse) || HttpUtil.isTransferEncodingChunked(httpResponse) || HttpServerKeepAliveHandler.isMultipart(httpResponse) || HttpServerKeepAliveHandler.isInformational(httpResponse) || httpResponse.status().code() == HttpResponseStatus.NO_CONTENT.code();
    }

    private static boolean isInformational(HttpResponse httpResponse) {
        return httpResponse.status().codeClass() == HttpStatusClass.INFORMATIONAL;
    }

    private static boolean isMultipart(HttpResponse httpResponse) {
        String string = httpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE);
        return string != null && string.regionMatches(true, 0, MULTIPART_PREFIX, 0, 0);
    }
}

