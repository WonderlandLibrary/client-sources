/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DelegatingChannelPromiseNotifier;
import io.netty.channel.PendingBytesTracker;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;

public abstract class AbstractCoalescingBufferQueue {
    private static final InternalLogger logger;
    private final ArrayDeque<Object> bufAndListenerPairs;
    private final PendingBytesTracker tracker;
    private int readableBytes;
    static final boolean $assertionsDisabled;

    protected AbstractCoalescingBufferQueue(Channel channel, int n) {
        this.bufAndListenerPairs = new ArrayDeque(n);
        this.tracker = channel == null ? null : PendingBytesTracker.newTracker(channel);
    }

    public final void addFirst(ByteBuf byteBuf, ChannelPromise channelPromise) {
        this.addFirst(byteBuf, AbstractCoalescingBufferQueue.toChannelFutureListener(channelPromise));
    }

    private void addFirst(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        if (channelFutureListener != null) {
            this.bufAndListenerPairs.addFirst(channelFutureListener);
        }
        this.bufAndListenerPairs.addFirst(byteBuf);
        this.incrementReadableBytes(byteBuf.readableBytes());
    }

    public final void add(ByteBuf byteBuf) {
        this.add(byteBuf, (ChannelFutureListener)null);
    }

    public final void add(ByteBuf byteBuf, ChannelPromise channelPromise) {
        this.add(byteBuf, AbstractCoalescingBufferQueue.toChannelFutureListener(channelPromise));
    }

    public final void add(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        this.bufAndListenerPairs.add(byteBuf);
        if (channelFutureListener != null) {
            this.bufAndListenerPairs.add(channelFutureListener);
        }
        this.incrementReadableBytes(byteBuf.readableBytes());
    }

    public final ByteBuf removeFirst(ChannelPromise channelPromise) {
        Object object = this.bufAndListenerPairs.poll();
        if (object == null) {
            return null;
        }
        if (!$assertionsDisabled && !(object instanceof ByteBuf)) {
            throw new AssertionError();
        }
        ByteBuf byteBuf = (ByteBuf)object;
        this.decrementReadableBytes(byteBuf.readableBytes());
        object = this.bufAndListenerPairs.peek();
        if (object instanceof ChannelFutureListener) {
            channelPromise.addListener((ChannelFutureListener)object);
            this.bufAndListenerPairs.poll();
        }
        return byteBuf;
    }

    public final ByteBuf remove(ByteBufAllocator byteBufAllocator, int n, ChannelPromise channelPromise) {
        ObjectUtil.checkPositiveOrZero(n, "bytes");
        ObjectUtil.checkNotNull(channelPromise, "aggregatePromise");
        if (this.bufAndListenerPairs.isEmpty()) {
            return this.removeEmptyValue();
        }
        n = Math.min(n, this.readableBytes);
        ByteBuf byteBuf = null;
        ByteBuf byteBuf2 = null;
        int n2 = n;
        try {
            Object object;
            while ((object = this.bufAndListenerPairs.poll()) != null) {
                if (object instanceof ChannelFutureListener) {
                    channelPromise.addListener((ChannelFutureListener)object);
                    continue;
                }
                byteBuf2 = (ByteBuf)object;
                if (byteBuf2.readableBytes() > n) {
                    this.bufAndListenerPairs.addFirst(byteBuf2);
                    if (n > 0) {
                        byteBuf2 = byteBuf2.readRetainedSlice(n);
                        byteBuf = byteBuf == null ? this.composeFirst(byteBufAllocator, byteBuf2) : this.compose(byteBufAllocator, byteBuf, byteBuf2);
                        n = 0;
                    }
                    break;
                }
                n -= byteBuf2.readableBytes();
                byteBuf = byteBuf == null ? this.composeFirst(byteBufAllocator, byteBuf2) : this.compose(byteBufAllocator, byteBuf, byteBuf2);
                byteBuf2 = null;
            }
        } catch (Throwable throwable) {
            ReferenceCountUtil.safeRelease(byteBuf2);
            ReferenceCountUtil.safeRelease(byteBuf);
            channelPromise.setFailure(throwable);
            PlatformDependent.throwException(throwable);
        }
        this.decrementReadableBytes(n2 - n);
        return byteBuf;
    }

    public final int readableBytes() {
        return this.readableBytes;
    }

    public final boolean isEmpty() {
        return this.bufAndListenerPairs.isEmpty();
    }

    public final void releaseAndFailAll(ChannelOutboundInvoker channelOutboundInvoker, Throwable throwable) {
        this.releaseAndCompleteAll(channelOutboundInvoker.newFailedFuture(throwable));
    }

    public final void copyTo(AbstractCoalescingBufferQueue abstractCoalescingBufferQueue) {
        abstractCoalescingBufferQueue.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
        abstractCoalescingBufferQueue.incrementReadableBytes(this.readableBytes);
    }

    public final void writeAndRemoveAll(ChannelHandlerContext channelHandlerContext) {
        this.decrementReadableBytes(this.readableBytes);
        Throwable throwable = null;
        ByteBuf byteBuf = null;
        while (true) {
            Object object = this.bufAndListenerPairs.poll();
            try {
                if (object == null) {
                    if (byteBuf == null) break;
                    channelHandlerContext.write(byteBuf, channelHandlerContext.voidPromise());
                    break;
                }
                if (object instanceof ByteBuf) {
                    if (byteBuf != null) {
                        channelHandlerContext.write(byteBuf, channelHandlerContext.voidPromise());
                    }
                    byteBuf = (ByteBuf)object;
                    continue;
                }
                if (object instanceof ChannelPromise) {
                    channelHandlerContext.write(byteBuf, (ChannelPromise)object);
                    byteBuf = null;
                    continue;
                }
                channelHandlerContext.write(byteBuf).addListener((ChannelFutureListener)object);
                byteBuf = null;
            } catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                    continue;
                }
                logger.info("Throwable being suppressed because Throwable {} is already pending", (Object)throwable, (Object)throwable2);
            }
        }
        if (throwable != null) {
            throw new IllegalStateException(throwable);
        }
    }

    protected abstract ByteBuf compose(ByteBufAllocator var1, ByteBuf var2, ByteBuf var3);

    protected final ByteBuf composeIntoComposite(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
        CompositeByteBuf compositeByteBuf = byteBufAllocator.compositeBuffer(this.size() + 2);
        try {
            compositeByteBuf.addComponent(true, byteBuf);
            compositeByteBuf.addComponent(true, byteBuf2);
        } catch (Throwable throwable) {
            compositeByteBuf.release();
            ReferenceCountUtil.safeRelease(byteBuf2);
            PlatformDependent.throwException(throwable);
        }
        return compositeByteBuf;
    }

    protected final ByteBuf copyAndCompose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
        ByteBuf byteBuf3 = byteBufAllocator.ioBuffer(byteBuf.readableBytes() + byteBuf2.readableBytes());
        try {
            byteBuf3.writeBytes(byteBuf).writeBytes(byteBuf2);
        } catch (Throwable throwable) {
            byteBuf3.release();
            ReferenceCountUtil.safeRelease(byteBuf2);
            PlatformDependent.throwException(throwable);
        }
        byteBuf.release();
        byteBuf2.release();
        return byteBuf3;
    }

    protected ByteBuf composeFirst(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
        return byteBuf;
    }

    protected abstract ByteBuf removeEmptyValue();

    protected final int size() {
        return this.bufAndListenerPairs.size();
    }

    private void releaseAndCompleteAll(ChannelFuture channelFuture) {
        Object object;
        this.decrementReadableBytes(this.readableBytes);
        Throwable throwable = null;
        while ((object = this.bufAndListenerPairs.poll()) != null) {
            try {
                if (object instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(object);
                    continue;
                }
                ((ChannelFutureListener)object).operationComplete(channelFuture);
            } catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                    continue;
                }
                logger.info("Throwable being suppressed because Throwable {} is already pending", (Object)throwable, (Object)throwable2);
            }
        }
        if (throwable != null) {
            throw new IllegalStateException(throwable);
        }
    }

    private void incrementReadableBytes(int n) {
        int n2 = this.readableBytes + n;
        if (n2 < this.readableBytes) {
            throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + n);
        }
        this.readableBytes = n2;
        if (this.tracker != null) {
            this.tracker.incrementPendingOutboundBytes(n);
        }
    }

    private void decrementReadableBytes(int n) {
        this.readableBytes -= n;
        if (!$assertionsDisabled && this.readableBytes < 0) {
            throw new AssertionError();
        }
        if (this.tracker != null) {
            this.tracker.decrementPendingOutboundBytes(n);
        }
    }

    private static ChannelFutureListener toChannelFutureListener(ChannelPromise channelPromise) {
        return channelPromise.isVoid() ? null : new DelegatingChannelPromiseNotifier(channelPromise);
    }

    static {
        $assertionsDisabled = !AbstractCoalescingBufferQueue.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(AbstractCoalescingBufferQueue.class);
    }
}

