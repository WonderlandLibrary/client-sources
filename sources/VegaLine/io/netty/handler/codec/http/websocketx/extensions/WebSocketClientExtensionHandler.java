/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WebSocketClientExtensionHandler
extends ChannelDuplexHandler {
    private final List<WebSocketClientExtensionHandshaker> extensionHandshakers;

    public WebSocketClientExtensionHandler(WebSocketClientExtensionHandshaker ... extensionHandshakers) {
        if (extensionHandshakers == null) {
            throw new NullPointerException("extensionHandshakers");
        }
        if (extensionHandshakers.length == 0) {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        }
        this.extensionHandshakers = Arrays.asList(extensionHandshakers);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof HttpRequest && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpRequest)msg).headers())) {
            HttpRequest request = (HttpRequest)msg;
            String headerValue = request.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            for (WebSocketClientExtensionHandshaker extentionHandshaker : this.extensionHandshakers) {
                WebSocketExtensionData extensionData = extentionHandshaker.newRequestData();
                headerValue = WebSocketExtensionUtil.appendExtension(headerValue, extensionData.name(), extensionData.parameters());
            }
            request.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, (Object)headerValue);
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpResponse response;
        if (msg instanceof HttpResponse && WebSocketExtensionUtil.isWebsocketUpgrade((response = (HttpResponse)msg).headers())) {
            String extensionsHeader = response.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            if (extensionsHeader != null) {
                List<WebSocketExtensionData> extensions = WebSocketExtensionUtil.extractExtensions(extensionsHeader);
                ArrayList<WebSocketExtension> validExtensions = new ArrayList<WebSocketExtension>(extensions.size());
                int rsv = 0;
                for (WebSocketExtensionData webSocketExtensionData : extensions) {
                    Iterator<WebSocketClientExtensionHandshaker> extensionHandshakersIterator = this.extensionHandshakers.iterator();
                    WebSocketExtension validExtension = null;
                    while (validExtension == null && extensionHandshakersIterator.hasNext()) {
                        WebSocketClientExtensionHandshaker extensionHandshaker = extensionHandshakersIterator.next();
                        validExtension = extensionHandshaker.handshakeExtension(webSocketExtensionData);
                    }
                    if (validExtension != null && (validExtension.rsv() & rsv) == 0) {
                        rsv |= validExtension.rsv();
                        validExtensions.add(validExtension);
                        continue;
                    }
                    throw new CodecException("invalid WebSocket Extension handhshake for \"" + extensionsHeader + "\"");
                }
                for (WebSocketClientExtension webSocketClientExtension : validExtensions) {
                    WebSocketExtensionDecoder decoder = webSocketClientExtension.newExtensionDecoder();
                    WebSocketExtensionEncoder encoder = webSocketClientExtension.newExtensionEncoder();
                    ctx.pipeline().addAfter(ctx.name(), decoder.getClass().getName(), decoder);
                    ctx.pipeline().addAfter(ctx.name(), encoder.getClass().getName(), encoder);
                }
            }
            ctx.pipeline().remove(ctx.name());
        }
        super.channelRead(ctx, msg);
    }
}

