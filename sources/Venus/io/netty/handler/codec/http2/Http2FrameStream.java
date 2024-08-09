/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Stream;

public interface Http2FrameStream {
    public int id();

    public Http2Stream.State state();
}

