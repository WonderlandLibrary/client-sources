/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyFrame;

public interface SpdyStreamFrame
extends SpdyFrame {
    public int streamId();

    public SpdyStreamFrame setStreamId(int var1);

    public boolean isLast();

    public SpdyStreamFrame setLast(boolean var1);
}

