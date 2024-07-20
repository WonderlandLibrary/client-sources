/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Frame;

public interface Http2StreamFrame
extends Http2Frame {
    public Http2StreamFrame streamId(int var1);

    public int streamId();
}

