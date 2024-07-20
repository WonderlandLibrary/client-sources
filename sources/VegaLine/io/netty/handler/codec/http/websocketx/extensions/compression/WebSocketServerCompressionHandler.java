/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateFrameServerExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateServerExtensionHandshaker;

public class WebSocketServerCompressionHandler
extends WebSocketServerExtensionHandler {
    public WebSocketServerCompressionHandler() {
        super(new PerMessageDeflateServerExtensionHandshaker(), new DeflateFrameServerExtensionHandshaker());
    }
}

