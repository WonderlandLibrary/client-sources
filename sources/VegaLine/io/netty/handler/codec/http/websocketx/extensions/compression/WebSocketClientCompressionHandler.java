/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateFrameClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateClientExtensionHandshaker;

@ChannelHandler.Sharable
public final class WebSocketClientCompressionHandler
extends WebSocketClientExtensionHandler {
    public static final WebSocketClientCompressionHandler INSTANCE = new WebSocketClientCompressionHandler();

    private WebSocketClientCompressionHandler() {
        super(new PerMessageDeflateClientExtensionHandshaker(), new DeflateFrameClientExtensionHandshaker(false), new DeflateFrameClientExtensionHandshaker(true));
    }
}

