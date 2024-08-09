/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Frame;
import io.netty.handler.codec.http2.Http2Settings;

public interface Http2SettingsFrame
extends Http2Frame {
    public Http2Settings settings();

    @Override
    public String name();
}

