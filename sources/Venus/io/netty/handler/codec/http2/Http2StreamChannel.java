/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.Channel;
import io.netty.handler.codec.http2.Http2FrameStream;

public interface Http2StreamChannel
extends Channel {
    public Http2FrameStream stream();
}

