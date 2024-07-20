/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.util.AsciiString;

public final class SpdyHttpHeaders {
    private SpdyHttpHeaders() {
    }

    public static final class Names {
        public static final AsciiString STREAM_ID = new AsciiString("x-spdy-stream-id");
        public static final AsciiString ASSOCIATED_TO_STREAM_ID = new AsciiString("x-spdy-associated-to-stream-id");
        public static final AsciiString PRIORITY = new AsciiString("x-spdy-priority");
        public static final AsciiString SCHEME = new AsciiString("x-spdy-scheme");

        private Names() {
        }
    }
}

