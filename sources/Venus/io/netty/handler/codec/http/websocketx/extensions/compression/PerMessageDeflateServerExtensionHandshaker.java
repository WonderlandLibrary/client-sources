/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateDecoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class PerMessageDeflateServerExtensionHandshaker
implements WebSocketServerExtensionHandshaker {
    public static final int MIN_WINDOW_SIZE = 8;
    public static final int MAX_WINDOW_SIZE = 15;
    static final String PERMESSAGE_DEFLATE_EXTENSION = "permessage-deflate";
    static final String CLIENT_MAX_WINDOW = "client_max_window_bits";
    static final String SERVER_MAX_WINDOW = "server_max_window_bits";
    static final String CLIENT_NO_CONTEXT = "client_no_context_takeover";
    static final String SERVER_NO_CONTEXT = "server_no_context_takeover";
    private final int compressionLevel;
    private final boolean allowServerWindowSize;
    private final int preferredClientWindowSize;
    private final boolean allowServerNoContext;
    private final boolean preferredClientNoContext;

    public PerMessageDeflateServerExtensionHandshaker() {
        this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false);
    }

    public PerMessageDeflateServerExtensionHandshaker(int n, boolean bl, int n2, boolean bl2, boolean bl3) {
        if (n2 > 15 || n2 < 8) {
            throw new IllegalArgumentException("preferredServerWindowSize: " + n2 + " (expected: 8-15)");
        }
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        this.compressionLevel = n;
        this.allowServerWindowSize = bl;
        this.preferredClientWindowSize = n2;
        this.allowServerNoContext = bl2;
        this.preferredClientNoContext = bl3;
    }

    @Override
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (!PERMESSAGE_DEFLATE_EXTENSION.equals(webSocketExtensionData.name())) {
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
            if (CLIENT_MAX_WINDOW.equalsIgnoreCase(entry.getKey())) {
                n = this.preferredClientWindowSize;
                continue;
            }
            if (SERVER_MAX_WINDOW.equalsIgnoreCase(entry.getKey())) {
                if (this.allowServerWindowSize) {
                    n2 = Integer.parseInt(entry.getValue());
                    if (n2 <= 15 && n2 >= 8) continue;
                    bl = false;
                    continue;
                }
                bl = false;
                continue;
            }
            if (CLIENT_NO_CONTEXT.equalsIgnoreCase(entry.getKey())) {
                bl3 = this.preferredClientNoContext;
                continue;
            }
            if (SERVER_NO_CONTEXT.equalsIgnoreCase(entry.getKey())) {
                if (this.allowServerNoContext) {
                    bl2 = true;
                    continue;
                }
                bl = false;
                continue;
            }
            bl = false;
        }
        if (bl) {
            return new PermessageDeflateExtension(this.compressionLevel, bl2, n2, bl3, n);
        }
        return null;
    }

    private static class PermessageDeflateExtension
    implements WebSocketServerExtension {
        private final int compressionLevel;
        private final boolean serverNoContext;
        private final int serverWindowSize;
        private final boolean clientNoContext;
        private final int clientWindowSize;

        public PermessageDeflateExtension(int n, boolean bl, int n2, boolean bl2, int n3) {
            this.compressionLevel = n;
            this.serverNoContext = bl;
            this.serverWindowSize = n2;
            this.clientNoContext = bl2;
            this.clientWindowSize = n3;
        }

        @Override
        public int rsv() {
            return 1;
        }

        @Override
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new PerMessageDeflateEncoder(this.compressionLevel, this.clientWindowSize, this.clientNoContext);
        }

        @Override
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new PerMessageDeflateDecoder(this.serverNoContext);
        }

        @Override
        public WebSocketExtensionData newReponseData() {
            HashMap<String, String> hashMap = new HashMap<String, String>(4);
            if (this.serverNoContext) {
                hashMap.put(PerMessageDeflateServerExtensionHandshaker.SERVER_NO_CONTEXT, null);
            }
            if (this.clientNoContext) {
                hashMap.put(PerMessageDeflateServerExtensionHandshaker.CLIENT_NO_CONTEXT, null);
            }
            if (this.serverWindowSize != 15) {
                hashMap.put(PerMessageDeflateServerExtensionHandshaker.SERVER_MAX_WINDOW, Integer.toString(this.serverWindowSize));
            }
            if (this.clientWindowSize != 15) {
                hashMap.put(PerMessageDeflateServerExtensionHandshaker.CLIENT_MAX_WINDOW, Integer.toString(this.clientWindowSize));
            }
            return new WebSocketExtensionData(PerMessageDeflateServerExtensionHandshaker.PERMESSAGE_DEFLATE_EXTENSION, hashMap);
        }
    }
}

