/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.handler.codec.http2.Http2WindowUpdateFrame;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultHttp2WindowUpdateFrame
extends AbstractHttp2StreamFrame
implements Http2WindowUpdateFrame {
    private final int windowUpdateIncrement;

    public DefaultHttp2WindowUpdateFrame(int n) {
        this.windowUpdateIncrement = n;
    }

    @Override
    public DefaultHttp2WindowUpdateFrame stream(Http2FrameStream http2FrameStream) {
        super.stream(http2FrameStream);
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

    @Override
    public AbstractHttp2StreamFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }

    @Override
    public Http2StreamFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }
}

