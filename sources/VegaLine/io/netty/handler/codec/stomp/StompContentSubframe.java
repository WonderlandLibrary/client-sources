/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.stomp.StompSubframe;

public interface StompContentSubframe
extends ByteBufHolder,
StompSubframe {
    @Override
    public StompContentSubframe copy();

    @Override
    public StompContentSubframe duplicate();

    @Override
    public StompContentSubframe retainedDuplicate();

    @Override
    public StompContentSubframe replace(ByteBuf var1);

    @Override
    public StompContentSubframe retain();

    @Override
    public StompContentSubframe retain(int var1);

    @Override
    public StompContentSubframe touch();

    @Override
    public StompContentSubframe touch(Object var1);
}

