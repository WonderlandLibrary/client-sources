/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2StreamFrame;

public interface Http2HeadersFrame
extends Http2StreamFrame {
    public Http2Headers headers();

    public boolean isEndStream();

    public int padding();
}

