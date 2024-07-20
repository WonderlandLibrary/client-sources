/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyFrame;

public interface SpdyPingFrame
extends SpdyFrame {
    public int id();

    public SpdyPingFrame setId(int var1);
}

