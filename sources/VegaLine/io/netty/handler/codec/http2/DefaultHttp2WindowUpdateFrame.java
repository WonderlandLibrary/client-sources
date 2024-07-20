/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2WindowUpdateFrame;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2WindowUpdateFrame
extends AbstractHttp2StreamFrame
implements Http2WindowUpdateFrame {
    private final int windowUpdateIncrement;

    public DefaultHttp2WindowUpdateFrame(int windowUpdateIncrement) {
        this.windowUpdateIncrement = ObjectUtil.checkPositive(windowUpdateIncrement, "windowUpdateIncrement");
    }

    @Override
    public DefaultHttp2WindowUpdateFrame streamId(int streamId) {
        super.streamId(streamId);
        return this;
    }

    @Override
    public String name() {
        return "WINDOW_UPDATE";
    }

    @Override
    public int windowSizeIncrement() {
        return this.windowUpdateIncrement;
    }
}

