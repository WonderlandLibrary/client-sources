/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2FrameStream;

public interface Http2FrameStreamVisitor {
    public boolean visit(Http2FrameStream var1);
}

