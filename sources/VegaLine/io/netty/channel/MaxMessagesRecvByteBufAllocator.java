/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.RecvByteBufAllocator;

public interface MaxMessagesRecvByteBufAllocator
extends RecvByteBufAllocator {
    public int maxMessagesPerRead();

    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int var1);
}

