/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.util.internal.ObjectUtil;

public final class Http2FrameStreamException
extends Exception {
    private static final long serialVersionUID = -4407186173493887044L;
    private final Http2Error error;
    private final Http2FrameStream stream;

    public Http2FrameStreamException(Http2FrameStream http2FrameStream, Http2Error http2Error, Throwable throwable) {
        super(throwable.getMessage(), throwable);
        this.stream = ObjectUtil.checkNotNull(http2FrameStream, "stream");
        this.error = ObjectUtil.checkNotNull(http2Error, "error");
    }

    public Http2Error error() {
        return this.error;
    }

    public Http2FrameStream stream() {
        return this.stream;
    }
}

