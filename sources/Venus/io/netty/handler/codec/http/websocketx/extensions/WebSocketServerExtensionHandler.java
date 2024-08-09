/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionUtil;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WebSocketServerExtensionHandler
extends ChannelDuplexHandler {
    private final List<WebSocketServerExtensionHandshaker> extensionHandshakers;
    private List<WebSocketServerExtension> validExtensions;

    public WebSocketServerExtensionHandler(WebSocketServerExtensionHandshaker ... webSocketServerExtensionHandshakerArray) {
        if (webSocketServerExtensionHandshakerArray == null) {
            throw new NullPointerException("extensionHandshakers");
        }
        if (webSocketServerExtensionHandshakerArray.length == 0) {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        }
        this.extensionHandshakers = Arrays.asList(webSocketServerExtensionHandshakerArray);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        String string;
        HttpRequest httpRequest;
        if (object instanceof HttpRequest && WebSocketExtensionUtil.isWebsocketUpgrade((httpRequest = (HttpRequest)object).headers()) && (string = httpRequest.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS)) != null) {
            List<WebSocketExtensionData> list = WebSocketExtensionUtil.extractExtensions(string);
            int n = 0;
            for (WebSocketExtensionData webSocketExtensionData : list) {
                Iterator<WebSocketServerExtensionHandshaker> iterator2 = this.extensionHandshakers.iterator();
                WebSocketExtension webSocketExtension = null;
                while (webSocketExtension == null && iterator2.hasNext()) {
                    WebSocketServerExtensionHandshaker webSocketServerExtensionHandshaker = iterator2.next();
                    webSocketExtension = webSocketServerExtensionHandshaker.handshakeExtension(webSocketExtensionData);
                }
                if (webSocketExtension == null || (webSocketExtension.rsv() & n) != 0) continue;
                if (this.validExtensions == null) {
                    this.validExtensions = new ArrayList<WebSocketServerExtension>(1);
                }
                n |= webSocketExtension.rsv();
                this.validExtensions.add((WebSocketServerExtension)webSocketExtension);
            }
        }
        super.channelRead(channelHandlerContext, object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (object instanceof HttpResponse && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpResponse)object).headers()) && this.validExtensions != null) {
            HttpResponse httpResponse = (HttpResponse)object;
            String string = httpResponse.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            for (WebSocketServerExtension webSocketServerExtension : this.validExtensions) {
                WebSocketExtensionData webSocketExtensionData = webSocketServerExtension.newReponseData();
                string = WebSocketExtensionUtil.appendExtension(string, webSocketExtensionData.name(), webSocketExtensionData.parameters());
            }
            channelPromise.addListener(new ChannelFutureListener(this, channelHandlerContext){
                final ChannelHandlerContext val$ctx;
                final WebSocketServerExtensionHandler this$0;
                {
                    this.this$0 = webSocketServerExtensionHandler;
                    this.val$ctx = channelHandlerContext;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        for (WebSocketServerExtension webSocketServerExtension : WebSocketServerExtensionHandler.access$000(this.this$0)) {
                            WebSocketExtensionDecoder webSocketExtensionDecoder = webSocketServerExtension.newExtensionDecoder();
                            WebSocketExtensionEncoder webSocketExtensionEncoder = webSocketServerExtension.newExtensionEncoder();
                            this.val$ctx.pipeline().addAfter(this.val$ctx.name(), webSocketExtensionDecoder.getClass().getName(), webSocketExtensionDecoder);
                            this.val$ctx.pipeline().addAfter(this.val$ctx.name(), webSocketExtensionEncoder.getClass().getName(), webSocketExtensionEncoder);
                        }
                    }
                    this.val$ctx.pipeline().remove(this.val$ctx.name());
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
            if (string != null) {
                httpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, (Object)string);
            }
        }
        super.write(channelHandlerContext, object, channelPromise);
    }

    static List access$000(WebSocketServerExtensionHandler webSocketServerExtensionHandler) {
        return webSocketServerExtensionHandler.validExtensions;
    }
}

