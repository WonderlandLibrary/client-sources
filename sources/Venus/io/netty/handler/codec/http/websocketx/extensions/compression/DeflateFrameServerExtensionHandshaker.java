/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerFrameDeflateDecoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerFrameDeflateEncoder;
import java.util.Collections;

public final class DeflateFrameServerExtensionHandshaker
implements WebSocketServerExtensionHandshaker {
    static final String X_WEBKIT_DEFLATE_FRAME_EXTENSION = "x-webkit-deflate-frame";
    static final String DEFLATE_FRAME_EXTENSION = "deflate-frame";
    private final int compressionLevel;

    public DeflateFrameServerExtensionHandshaker() {
        this(6);
    }

    public DeflateFrameServerExtensionHandshaker(int n) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        this.compressionLevel = n;
    }

    @Override
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (!X_WEBKIT_DEFLATE_FRAME_EXTENSION.equals(webSocketExtensionData.name()) && !DEFLATE_FRAME_EXTENSION.equals(webSocketExtensionData.name())) {
            return null;
        }
        if (webSocketExtensionData.parameters().isEmpty()) {
            return new DeflateFrameServerExtension(this.compressionLevel, webSocketExtensionData.name());
        }
        return null;
    }

    private static class DeflateFrameServerExtension
    implements WebSocketServerExtension {
        private final String extensionName;
        private final int compressionLevel;

        public DeflateFrameServerExtension(int n, String string) {
            this.extensionName = string;
            this.compressionLevel = n;
        }

        @Override
        public int rsv() {
            return 1;
        }

        @Override
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new PerFrameDeflateEncoder(this.compressionLevel, 15, false);
        }

        @Override
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new PerFrameDeflateDecoder(false);
        }

        @Override
        public WebSocketExtensionData newReponseData() {
            return new WebSocketExtensionData(this.extensionName, Collections.<String, String>emptyMap());
        }
    }
}

