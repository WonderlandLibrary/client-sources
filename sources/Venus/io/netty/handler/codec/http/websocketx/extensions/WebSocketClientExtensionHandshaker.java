/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;

public interface WebSocketClientExtensionHandshaker {
    public WebSocketExtensionData newRequestData();

    public WebSocketClientExtension handshakeExtension(WebSocketExtensionData var1);
}

