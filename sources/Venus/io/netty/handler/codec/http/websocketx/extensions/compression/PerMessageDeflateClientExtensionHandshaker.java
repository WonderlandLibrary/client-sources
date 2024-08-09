/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateDecoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class PerMessageDeflateClientExtensionHandshaker
implements WebSocketClientExtensionHandshaker {
    private final int compressionLevel;
    private final boolean allowClientWindowSize;
    private final int requestedServerWindowSize;
    private final boolean allowClientNoContext;
    private final boolean requestedServerNoContext;

    public PerMessageDeflateClientExtensionHandshaker() {
        this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false);
    }

    public PerMessageDeflateClientExtensionHandshaker(int n, boolean bl, int n2, boolean bl2, boolean bl3) {
        if (n2 > 15 || n2 < 8) {
            throw new IllegalArgumentException("requestedServerWindowSize: " + n2 + " (expected: 8-15)");
        }
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        this.compressionLevel = n;
        this.allowClientWindowSize = bl;
        this.requestedServerWindowSize = n2;
        this.allowClientNoContext = bl2;
        this.requestedServerNoContext = bl3;
    }

    @Override
    public WebSocketExtensionData newRequestData() {
        HashMap<String, String> hashMap = new HashMap<String, String>(4);
        if (this.requestedServerWindowSize != 15) {
            hashMap.put("server_no_context_takeover", null);
        }
        if (this.allowClientNoContext) {
            hashMap.put("client_no_context_takeover", null);
        }
        if (this.requestedServerWindowSize != 15) {
            hashMap.put("server_max_window_bits", Integer.toString(this.requestedServerWindowSize));
        }
        if (this.allowClientWindowSize) {
            hashMap.put("client_max_window_bits", null);
        }
        return new WebSocketExtensionData("permessage-deflate", hashMap);
    }

    @Override
    public WebSocketClientExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (!"permessage-deflate".equals(webSocketExtensionData.name())) {
            return null;
        }
        boolean bl = true;
        int n = 15;
        int n2 = 15;
        boolean bl2 = false;
        boolean bl3 = false;
        Iterator<Map.Entry<String, String>> iterator2 = webSocketExtensionData.parameters().entrySet().iterator();
        while (bl && iterator2.hasNext()) {
            Map.Entry<String, String> entry = iterator2.next();
            if ("client_max_window_bits".equalsIgnoreCase(entry.getKey())) {
                if (this.allowClientWindowSize) {
                    n = Integer.parseInt(entry.getValue());
                    continue;
                }
                bl = false;
                continue;
            }
            if ("server_max_window_bits".equalsIgnoreCase(entry.getKey())) {
                n2 = Integer.parseInt(entry.getValue());
                if (n <= 15 && n >= 8) continue;
                bl = false;
                continue;
            }
            if ("client_no_context_takeover".equalsIgnoreCase(entry.getKey())) {
                if (this.allowClientNoContext) {
                    bl3 = true;
                    continue;
                }
                bl = false;
                continue;
            }
            if ("server_no_context_takeover".equalsIgnoreCase(entry.getKey())) {
                if (this.requestedServerNoContext) {
                    bl2 = true;
                    continue;
                }
                bl = false;
                continue;
            }
            bl = false;
        }
        if (this.requestedServerNoContext && !bl2 || this.requestedServerWindowSize != n2) {
            bl = false;
        }
        if (bl) {
            return new PermessageDeflateExtension(this, bl2, n2, bl3, n);
        }
        return null;
    }

    static int access$000(PerMessageDeflateClientExtensionHandshaker perMessageDeflateClientExtensionHandshaker) {
        return perMessageDeflateClientExtensionHandshaker.compressionLevel;
    }

    private final class PermessageDeflateExtension
    implements WebSocketClientExtension {
        private final boolean serverNoContext;
        private final int serverWindowSize;
        private final boolean clientNoContext;
        private final int clientWindowSize;
        final PerMessageDeflateClientExtensionHandshaker this$0;

        @Override
        public int rsv() {
            return 1;
        }

        public PermessageDeflateExtension(PerMessageDeflateClientExtensionHandshaker perMessageDeflateClientExtensionHandshaker, boolean bl, int n, boolean bl2, int n2) {
            this.this$0 = perMessageDeflateClientExtensionHandshaker;
            this.serverNoContext = bl;
            this.serverWindowSize = n;
            this.clientNoContext = bl2;
            this.clientWindowSize = n2;
        }

        @Override
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new PerMessageDeflateEncoder(PerMessageDeflateClientExtensionHandshaker.access$000(this.this$0), this.serverWindowSize, this.serverNoContext);
        }

        @Override
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new PerMessageDeflateDecoder(this.clientNoContext);
        }
    }
}

