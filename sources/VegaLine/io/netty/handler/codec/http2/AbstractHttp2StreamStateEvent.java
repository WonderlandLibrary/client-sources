/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2StreamStateEvent;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractHttp2StreamStateEvent
implements Http2StreamStateEvent {
    private final int streamId;

    protected AbstractHttp2StreamStateEvent(int streamId) {
        this.streamId = ObjectUtil.checkPositiveOrZero(streamId, "streamId");
    }

    @Override
    public int streamId() {
        return this.streamId;
    }
}

