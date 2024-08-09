/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2FrameStream;

public final class Http2FrameStreamEvent {
    private final Http2FrameStream stream;
    private final Type type;

    private Http2FrameStreamEvent(Http2FrameStream http2FrameStream, Type type) {
        this.stream = http2FrameStream;
        this.type = type;
    }

    public Http2FrameStream stream() {
        return this.stream;
    }

    public Type type() {
        return this.type;
    }

    static Http2FrameStreamEvent stateChanged(Http2FrameStream http2FrameStream) {
        return new Http2FrameStreamEvent(http2FrameStream, Type.State);
    }

    static Http2FrameStreamEvent writabilityChanged(Http2FrameStream http2FrameStream) {
        return new Http2FrameStreamEvent(http2FrameStream, Type.Writability);
    }

    static enum Type {
        State,
        Writability;

    }
}

