/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface ViaCodecHandler {
    public void transform(ByteBuf var1) throws Exception;

    public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception;
}

