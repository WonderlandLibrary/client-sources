/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyHeadersFrame;

public interface SpdySynReplyFrame
extends SpdyHeadersFrame {
    @Override
    public SpdySynReplyFrame setStreamId(int var1);

    @Override
    public SpdySynReplyFrame setLast(boolean var1);

    @Override
    public SpdySynReplyFrame setInvalid();
}

