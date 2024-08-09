/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractCoalescingBufferQueue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.ObjectUtil;

public final class CoalescingBufferQueue
extends AbstractCoalescingBufferQueue {
    private final Channel channel;

    public CoalescingBufferQueue(Channel channel) {
        this(channel, 4);
    }

    public CoalescingBufferQueue(Channel channel, int n) {
        this(channel, n, false);
    }

    public CoalescingBufferQueue(Channel channel, int n, boolean bl) {
        super(bl ? channel : null, n);
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
    }

    public ByteBuf remove(int n, ChannelPromise channelPromise) {
        return this.remove(this.channel.alloc(), n, channelPromise);
    }

    public void releaseAndFailAll(Throwable throwable) {
        this.releaseAndFailAll(this.channel, throwable);
    }

    @Override
    protected ByteBuf compose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf instanceof CompositeByteBuf) {
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf)byteBuf;
            compositeByteBuf.addComponent(true, byteBuf2);
            return compositeByteBuf;
        }
        return this.composeIntoComposite(byteBufAllocator, byteBuf, byteBuf2);
    }

    @Override
    protected ByteBuf removeEmptyValue() {
        return Unpooled.EMPTY_BUFFER;
    }
}

