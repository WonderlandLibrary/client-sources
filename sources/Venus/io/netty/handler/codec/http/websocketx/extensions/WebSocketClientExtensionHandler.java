/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebSocketClientExtensionHandler
extends ChannelDuplexHandler {
    private final List<WebSocketClientExtensionHandshaker> extensionHandshakers;

    public WebSocketClientExtensionHandler(WebSocketClientExtensionHandshaker ... webSocketClientExtensionHandshakerArray) {
        if (webSocketClientExtensionHandshakerArray == null) {
            throw new NullPointerException("extensionHandshakers");
        }
        if (webSocketClientExtensionHandshakerArray.length == 0) {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        }
        this.extensionHandshakers = Arrays.asList(webSocketClientExtensionHandshakerArray);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (object instanceof HttpRequest && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpRequest)object).headers())) {
            HttpRequest httpRequest = (HttpRequest)object;
            String string = httpRequest.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            for (WebSocketClientExtensionHandshaker webSocketClientExtensionHandshaker : this.extensionHandshakers) {
                WebSocketExtensionData webSocketExtensionData = webSocketClientExtensionHandshaker.newRequestData();
                string = WebSocketExtensionUtil.appendExtension(string, webSocketExtensionData.name(), webSocketExtensionData.parameters());
            }
            httpRequest.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, (Object)string);
        }
        super.write(channelHandlerContext, object, channelPromise);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        HttpResponse httpResponse;
        if (object instanceof HttpResponse && WebSocketExtensionUtil.isWebsocketUpgrade((httpResponse = (HttpResponse)object).headers())) {
            String string = httpResponse.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            if (string != null) {
                Object object2;
                Object object3;
                List<WebSocketExtensionData> list = WebSocketExtensionUtil.extractExtensions(string);
                ArrayList<Object> arrayList = new ArrayList<Object>(list.size());
                int n = 0;
                for (WebSocketExtensionData object4 : list) {
                    object3 = this.extensionHandshakers.iterator();
                    object2 = null;
                    while (object2 == null && object3.hasNext()) {
                        WebSocketClientExtensionHandshaker webSocketClientExtensionHandshaker = (WebSocketClientExtensionHandshaker)object3.next();
                        object2 = webSocketClientExtensionHandshaker.handshakeExtension(object4);
                    }
                    if (object2 != null && (object2.rsv() & n) == 0) {
                        n |= object2.rsv();
                        arrayList.add(object2);
                        continue;
                    }
                    throw new CodecException("invalid WebSocket Extension handshake for \"" + string + '\"');
                }
                for (WebSocketClientExtension webSocketClientExtension : arrayList) {
                    object3 = webSocketClientExtension.newExtensionDecoder();
                    object2 = webSocketClientExtension.newExtensionEncoder();
                    channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), object3.getClass().getName(), (ChannelHandler)object3);
                    channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), object2.getClass().getName(), (ChannelHandler)object2);
                }
            }
            channelHandlerContext.pipeline().remove(channelHandlerContext.name());
        }
        super.channelRead(channelHandlerContext, object);
    }
}

