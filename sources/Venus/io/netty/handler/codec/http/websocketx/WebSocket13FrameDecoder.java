/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder;

public class WebSocket13FrameDecoder
extends WebSocket08FrameDecoder {
    public WebSocket13FrameDecoder(boolean bl, boolean bl2, int n) {
        this(bl, bl2, n, false);
    }

    public WebSocket13FrameDecoder(boolean bl, boolean bl2, int n, boolean bl3) {
        super(bl, bl2, n, bl3);
    }
}

