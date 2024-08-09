/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.PendingBytesTracker;
import io.netty.channel.VoidChannelPromise;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class PendingWriteQueue {
    private static final InternalLogger logger;
    private static final int PENDING_WRITE_OVERHEAD;
    private final ChannelHandlerContext ctx;
    private final PendingBytesTracker tracker;
    private PendingWrite head;
    private PendingWrite tail;
    private int size;
    private long bytes;
    static final boolean $assertionsDisabled;

    public PendingWriteQueue(ChannelHandlerContext channelHandlerContext) {
        this.tracker = PendingBytesTracker.newTracker(channelHandlerContext.channel());
        this.ctx = channelHandlerContext;
    }

    public boolean isEmpty() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        return this.head == null;
    }

    public int size() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        return this.size;
    }

    public long bytes() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        return this.bytes;
    }

    private int size(Object object) {
        int n = this.tracker.size(object);
        if (n < 0) {
            n = 0;
        }
        return n + PENDING_WRITE_OVERHEAD;
    }

    public void add(Object object, ChannelPromise channelPromise) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        if (object == null) {
            throw new NullPointerException("msg");
        }
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        int n = this.size(object);
        PendingWrite pendingWrite = PendingWrite.newInstance(object, n, channelPromise);
        PendingWrite pendingWrite2 = this.tail;
        if (pendingWrite2 == null) {
            this.tail = this.head = pendingWrite;
        } else {
            PendingWrite.access$002(pendingWrite2, pendingWrite);
            this.tail = pendingWrite;
        }
        ++this.size;
        this.bytes += (long)n;
        this.tracker.incrementPendingOutboundBytes(PendingWrite.access$100(pendingWrite));
    }

    public ChannelFuture removeAndWriteAll() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        if (this.isEmpty()) {
            return null;
        }
        ChannelPromise channelPromise = this.ctx.newPromise();
        PromiseCombiner promiseCombiner = new PromiseCombiner();
        try {
            PendingWrite pendingWrite = this.head;
            while (pendingWrite != null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0L;
                while (pendingWrite != null) {
                    PendingWrite pendingWrite2 = PendingWrite.access$000(pendingWrite);
                    Object object = PendingWrite.access$200(pendingWrite);
                    ChannelPromise channelPromise2 = PendingWrite.access$300(pendingWrite);
                    this.recycle(pendingWrite, false);
                    if (!(channelPromise2 instanceof VoidChannelPromise)) {
                        promiseCombiner.add(channelPromise2);
                    }
                    this.ctx.write(object, channelPromise2);
                    pendingWrite = pendingWrite2;
                }
                pendingWrite = this.head;
            }
            promiseCombiner.finish(channelPromise);
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
        this.assertEmpty();
        return channelPromise;
    }

    public void removeAndFailAll(Throwable throwable) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        if (throwable == null) {
            throw new NullPointerException("cause");
        }
        PendingWrite pendingWrite = this.head;
        while (pendingWrite != null) {
            this.tail = null;
            this.head = null;
            this.size = 0;
            this.bytes = 0L;
            while (pendingWrite != null) {
                PendingWrite pendingWrite2 = PendingWrite.access$000(pendingWrite);
                ReferenceCountUtil.safeRelease(PendingWrite.access$200(pendingWrite));
                ChannelPromise channelPromise = PendingWrite.access$300(pendingWrite);
                this.recycle(pendingWrite, false);
                PendingWriteQueue.safeFail(channelPromise, throwable);
                pendingWrite = pendingWrite2;
            }
            pendingWrite = this.head;
        }
        this.assertEmpty();
    }

    public void removeAndFail(Throwable throwable) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        if (throwable == null) {
            throw new NullPointerException("cause");
        }
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return;
        }
        ReferenceCountUtil.safeRelease(PendingWrite.access$200(pendingWrite));
        ChannelPromise channelPromise = PendingWrite.access$300(pendingWrite);
        PendingWriteQueue.safeFail(channelPromise, throwable);
        this.recycle(pendingWrite, true);
    }

    private void assertEmpty() {
        if (!($assertionsDisabled || this.tail == null && this.head == null && this.size == 0)) {
            throw new AssertionError();
        }
    }

    public ChannelFuture removeAndWrite() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        Object object = PendingWrite.access$200(pendingWrite);
        ChannelPromise channelPromise = PendingWrite.access$300(pendingWrite);
        this.recycle(pendingWrite, true);
        return this.ctx.write(object, channelPromise);
    }

    public ChannelPromise remove() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        ChannelPromise channelPromise = PendingWrite.access$300(pendingWrite);
        ReferenceCountUtil.safeRelease(PendingWrite.access$200(pendingWrite));
        this.recycle(pendingWrite, true);
        return channelPromise;
    }

    public Object current() {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        return PendingWrite.access$200(pendingWrite);
    }

    private void recycle(PendingWrite pendingWrite, boolean bl) {
        PendingWrite pendingWrite2 = PendingWrite.access$000(pendingWrite);
        long l = PendingWrite.access$100(pendingWrite);
        if (bl) {
            if (pendingWrite2 == null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0L;
            } else {
                this.head = pendingWrite2;
                --this.size;
                this.bytes -= l;
                if (!($assertionsDisabled || this.size > 0 && this.bytes >= 0L)) {
                    throw new AssertionError();
                }
            }
        }
        PendingWrite.access$400(pendingWrite);
        this.tracker.decrementPendingOutboundBytes(l);
    }

    private static void safeFail(ChannelPromise channelPromise, Throwable throwable) {
        if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(throwable)) {
            logger.warn("Failed to mark a promise as failure because it's done already: {}", (Object)channelPromise, (Object)throwable);
        }
    }

    static {
        $assertionsDisabled = !PendingWriteQueue.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
        PENDING_WRITE_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.pendingWriteSizeOverhead", 64);
    }

    static final class PendingWrite {
        private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>(){

            @Override
            protected PendingWrite newObject(Recycler.Handle<PendingWrite> handle) {
                return new PendingWrite(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };
        private final Recycler.Handle<PendingWrite> handle;
        private PendingWrite next;
        private long size;
        private ChannelPromise promise;
        private Object msg;

        private PendingWrite(Recycler.Handle<PendingWrite> handle) {
            this.handle = handle;
        }

        static PendingWrite newInstance(Object object, int n, ChannelPromise channelPromise) {
            PendingWrite pendingWrite = RECYCLER.get();
            pendingWrite.size = n;
            pendingWrite.msg = object;
            pendingWrite.promise = channelPromise;
            return pendingWrite;
        }

        private void recycle() {
            this.size = 0L;
            this.next = null;
            this.msg = null;
            this.promise = null;
            this.handle.recycle(this);
        }

        static PendingWrite access$002(PendingWrite pendingWrite, PendingWrite pendingWrite2) {
            pendingWrite.next = pendingWrite2;
            return pendingWrite.next;
        }

        static long access$100(PendingWrite pendingWrite) {
            return pendingWrite.size;
        }

        static PendingWrite access$000(PendingWrite pendingWrite) {
            return pendingWrite.next;
        }

        static Object access$200(PendingWrite pendingWrite) {
            return pendingWrite.msg;
        }

        static ChannelPromise access$300(PendingWrite pendingWrite) {
            return pendingWrite.promise;
        }

        static void access$400(PendingWrite pendingWrite) {
            pendingWrite.recycle();
        }

        PendingWrite(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }
}

