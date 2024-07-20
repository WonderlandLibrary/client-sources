/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamStateEvent;
import io.netty.handler.codec.http2.Http2HeadersFrame;

public class Http2StreamActiveEvent
extends AbstractHttp2StreamStateEvent {
    private final Http2HeadersFrame headers;

    public Http2StreamActiveEvent(int streamId) {
        this(streamId, null);
    }

    public Http2StreamActiveEvent(int streamId, Http2HeadersFrame headers) {
        super(streamId);
        this.headers = headers;
    }

    public Http2HeadersFrame headers() {
        return this.headers;
    }
}

