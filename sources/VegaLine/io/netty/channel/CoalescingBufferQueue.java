/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

public final class CoalescingBufferQueue {
    private final Channel channel;
    private final ArrayDeque<Object> bufAndListenerPairs;
    private int readableBytes;

    public CoalescingBufferQueue(Channel channel) {
        this(channel, 4);
    }

    public CoalescingBufferQueue(Channel channel, int initSize) {
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
        this.bufAndListenerPairs = new ArrayDeque(initSize);
    }

    public void add(ByteBuf buf) {
        this.add(buf, (ChannelFutureListener)null);
    }

    public void add(ByteBuf buf, ChannelPromise promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        this.add(buf, promise.isVoid() ? null : new ChannelPromiseNotifier(promise));
    }

    public void add(ByteBuf buf, ChannelFutureListener listener) {
        ObjectUtil.checkNotNull(buf, "buf");
        if (this.readableBytes > Integer.MAX_VALUE - buf.readableBytes()) {
            throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + buf.readableBytes());
        }
        this.bufAndListenerPairs.add(buf);
        if (listener != null) {
            this.bufAndListenerPairs.add(listener);
        }
        this.readableBytes += buf.readableBytes();
    }

    public ByteBuf remove(int bytes, ChannelPromise aggregatePromise) {
        Object entry;
        if (bytes < 0) {
            throw new IllegalArgumentException("bytes (expected >= 0): " + bytes);
        }
        ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
        if (this.bufAndListenerPairs.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        bytes = Math.min(bytes, this.readableBytes);
        ByteBuf toReturn = null;
        int originalBytes = bytes;
        while ((entry = this.bufAndListenerPairs.poll()) != null) {
            if (entry instanceof ChannelFutureListener) {
                aggregatePromise.addListener((ChannelFutureListener)entry);
                continue;
            }
            ByteBuf entryBuffer = (ByteBuf)entry;
            if (entryBuffer.readableBytes() > bytes) {
                this.bufAndListenerPairs.addFirst(entryBuffer);
                if (bytes <= 0) break;
                toReturn = this.compose(toReturn, entryBuffer.readRetainedSlice(bytes));
                bytes = 0;
                break;
            }
            toReturn = this.compose(toReturn, entryBuffer);
            bytes -= entryBuffer.readableBytes();
        }
        this.readableBytes -= originalBytes - bytes;
        assert (this.readableBytes >= 0);
        return toReturn;
    }

    private ByteBuf compose(ByteBuf current, ByteBuf next) {
        if (current == null) {
            return next;
        }
        if (current instanceof CompositeByteBuf) {
            CompositeByteBuf composite = (CompositeByteBuf)current;
            composite.addComponent(true, next);
            return composite;
        }
        CompositeByteBuf composite = this.channel.alloc().compositeBuffer(this.bufAndListenerPairs.size() + 2);
        composite.addComponent(true, current);
        composite.addComponent(true, next);
        return composite;
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    public boolean isEmpty() {
        return this.bufAndListenerPairs.isEmpty();
    }

    public void releaseAndFailAll(Throwable cause) {
        this.releaseAndCompleteAll(this.channel.newFailedFuture(cause));
    }

    private void releaseAndCompleteAll(ChannelFuture future) {
        Object entry;
        this.readableBytes = 0;
        Throwable pending = null;
        while ((entry = this.bufAndListenerPairs.poll()) != null) {
            try {
                if (entry instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(entry);
                    continue;
                }
                ((ChannelFutureListener)entry).operationComplete(future);
            } catch (Throwable t) {
                pending = t;
            }
        }
        if (pending != null) {
            throw new IllegalStateException(pending);
        }
    }

    public void copyTo(CoalescingBufferQueue dest) {
        dest.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
        dest.readableBytes += this.readableBytes;
    }
}

