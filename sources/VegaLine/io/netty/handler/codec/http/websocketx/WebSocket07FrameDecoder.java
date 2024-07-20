/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder;

public class WebSocket07FrameDecoder
extends WebSocket08FrameDecoder {
    public WebSocket07FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength) {
        this(expectMaskedFrames, allowExtensions, maxFramePayloadLength, false);
    }

    public WebSocket07FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
        super(expectMaskedFrames, allowExtensions, maxFramePayloadLength, allowMaskMismatch);
    }
}

