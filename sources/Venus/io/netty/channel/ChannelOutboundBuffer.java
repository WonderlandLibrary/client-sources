/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.channel.VoidChannelPromise;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public final class ChannelOutboundBuffer {
    static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
    private static final InternalLogger logger;
    private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS;
    private final Channel channel;
    private Entry flushedEntry;
    private Entry unflushedEntry;
    private Entry tailEntry;
    private int flushed;
    private int nioBufferCount;
    private long nioBufferSize;
    private boolean inFail;
    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
    private volatile long totalPendingSize;
    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER;
    private volatile int unwritable;
    private volatile Runnable fireChannelWritabilityChangedTask;
    static final boolean $assertionsDisabled;

    ChannelOutboundBuffer(AbstractChannel abstractChannel) {
        this.channel = abstractChannel;
    }

    public void addMessage(Object object, int n, ChannelPromise channelPromise) {
        Entry entry = Entry.newInstance(object, n, ChannelOutboundBuffer.total(object), channelPromise);
        if (this.tailEntry == null) {
            this.flushedEntry = null;
        } else {
            Entry entry2 = this.tailEntry;
            entry2.next = entry;
        }
        this.tailEntry = entry;
        if (this.unflushedEntry == null) {
            this.unflushedEntry = entry;
        }
        this.incrementPendingOutboundBytes(entry.pendingSize, false);
    }

    public void addFlush() {
        Entry entry = this.unflushedEntry;
        if (entry != null) {
            if (this.flushedEntry == null) {
                this.flushedEntry = entry;
            }
            do {
                ++this.flushed;
                if (entry.promise.setUncancellable()) continue;
                int n = entry.cancel();
                this.decrementPendingOutboundBytes(n, false, true);
            } while ((entry = entry.next) != null);
            this.unflushedEntry = null;
        }
    }

    void incrementPendingOutboundBytes(long l) {
        this.incrementPendingOutboundBytes(l, true);
    }

    private void incrementPendingOutboundBytes(long l, boolean bl) {
        if (l == 0L) {
            return;
        }
        long l2 = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, l);
        if (l2 > (long)this.channel.config().getWriteBufferHighWaterMark()) {
            this.setUnwritable(bl);
        }
    }

    void decrementPendingOutboundBytes(long l) {
        this.decrementPendingOutboundBytes(l, true, true);
    }

    private void decrementPendingOutboundBytes(long l, boolean bl, boolean bl2) {
        if (l == 0L) {
            return;
        }
        long l2 = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -l);
        if (bl2 && l2 < (long)this.channel.config().getWriteBufferLowWaterMark()) {
            this.setWritable(bl);
        }
    }

    private static long total(Object object) {
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).readableBytes();
        }
        if (object instanceof FileRegion) {
            return ((FileRegion)object).count();
        }
        if (object instanceof ByteBufHolder) {
            return ((ByteBufHolder)object).content().readableBytes();
        }
        return -1L;
    }

    public Object current() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return null;
        }
        return entry.msg;
    }

    public void progress(long l) {
        Entry entry = this.flushedEntry;
        if (!$assertionsDisabled && entry == null) {
            throw new AssertionError();
        }
        ChannelPromise channelPromise = entry.promise;
        if (channelPromise instanceof ChannelProgressivePromise) {
            long l2;
            entry.progress = l2 = entry.progress + l;
            ((ChannelProgressivePromise)channelPromise).tryProgress(l2, entry.total);
        }
    }

    public boolean remove() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            this.clearNioBuffers();
            return true;
        }
        Object object = entry.msg;
        ChannelPromise channelPromise = entry.promise;
        int n = entry.pendingSize;
        this.removeEntry(entry);
        if (!entry.cancelled) {
            ReferenceCountUtil.safeRelease(object);
            ChannelOutboundBuffer.safeSuccess(channelPromise);
            this.decrementPendingOutboundBytes(n, false, true);
        }
        entry.recycle();
        return false;
    }

    public boolean remove(Throwable throwable) {
        return this.remove0(throwable, true);
    }

    private boolean remove0(Throwable throwable, boolean bl) {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            this.clearNioBuffers();
            return true;
        }
        Object object = entry.msg;
        ChannelPromise channelPromise = entry.promise;
        int n = entry.pendingSize;
        this.removeEntry(entry);
        if (!entry.cancelled) {
            ReferenceCountUtil.safeRelease(object);
            ChannelOutboundBuffer.safeFail(channelPromise, throwable);
            this.decrementPendingOutboundBytes(n, false, bl);
        }
        entry.recycle();
        return false;
    }

    private void removeEntry(Entry entry) {
        if (--this.flushed == 0) {
            this.flushedEntry = null;
            if (entry == this.tailEntry) {
                this.tailEntry = null;
                this.unflushedEntry = null;
            }
        } else {
            this.flushedEntry = entry.next;
        }
    }

    public void removeBytes(long l) {
        block5: {
            int n;
            ByteBuf byteBuf;
            while (true) {
                Object object;
                if (!((object = this.current()) instanceof ByteBuf)) {
                    if (!$assertionsDisabled && l != 0L) {
                        throw new AssertionError();
                    }
                    break block5;
                }
                byteBuf = (ByteBuf)object;
                n = byteBuf.readerIndex();
                int n2 = byteBuf.writerIndex() - n;
                if ((long)n2 > l) break;
                if (l != 0L) {
                    this.progress(n2);
                    l -= (long)n2;
                }
                this.remove();
            }
            if (l != 0L) {
                byteBuf.readerIndex(n + (int)l);
                this.progress(l);
            }
        }
        this.clearNioBuffers();
    }

    private void clearNioBuffers() {
        int n = this.nioBufferCount;
        if (n > 0) {
            this.nioBufferCount = 0;
            Arrays.fill(NIO_BUFFERS.get(), 0, n, null);
        }
    }

    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public ByteBuffer[] nioBuffers(int n, long l) {
        if (!$assertionsDisabled && n <= 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && l <= 0L) {
            throw new AssertionError();
        }
        long l2 = 0L;
        int n2 = 0;
        InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
        ByteBuffer[] byteBufferArray = NIO_BUFFERS.get(internalThreadLocalMap);
        Entry entry = this.flushedEntry;
        while (this.isFlushedEntry(entry) && entry.msg instanceof ByteBuf) {
            if (!entry.cancelled) {
                ByteBuf byteBuf = (ByteBuf)entry.msg;
                int n3 = byteBuf.readerIndex();
                int n4 = byteBuf.writerIndex() - n3;
                if (n4 > 0) {
                    Object object;
                    int n5;
                    if (l - (long)n4 < l2 && n2 != 0) break;
                    l2 += (long)n4;
                    int n6 = entry.count;
                    if (n6 == -1) {
                        entry.count = n6 = byteBuf.nioBufferCount();
                    }
                    if ((n5 = Math.min(n, n2 + n6)) > byteBufferArray.length) {
                        byteBufferArray = ChannelOutboundBuffer.expandNioBufferArray(byteBufferArray, n5, n2);
                        NIO_BUFFERS.set(internalThreadLocalMap, byteBufferArray);
                    }
                    if (n6 == 1) {
                        object = entry.buf;
                        if (object == null) {
                            object = byteBuf.internalNioBuffer(n3, n4);
                            entry.buf = object;
                        }
                        byteBufferArray[n2++] = object;
                    } else {
                        ByteBuffer byteBuffer;
                        object = entry.bufs;
                        if (object == null) {
                            entry.bufs = object = byteBuf.nioBuffers();
                        }
                        for (int i = 0; i < ((ByteBuffer[])object).length && n2 < n && (byteBuffer = object[i]) != null; ++i) {
                            if (!byteBuffer.hasRemaining()) continue;
                            byteBufferArray[n2++] = byteBuffer;
                        }
                    }
                    if (n2 == n) break;
                }
            }
            entry = entry.next;
        }
        this.nioBufferCount = n2;
        this.nioBufferSize = l2;
        return byteBufferArray;
    }

    private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] byteBufferArray, int n, int n2) {
        int n3 = byteBufferArray.length;
        do {
            if ((n3 <<= 1) >= 0) continue;
            throw new IllegalStateException();
        } while (n > n3);
        ByteBuffer[] byteBufferArray2 = new ByteBuffer[n3];
        System.arraycopy(byteBufferArray, 0, byteBufferArray2, 0, n2);
        return byteBufferArray2;
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public long nioBufferSize() {
        return this.nioBufferSize;
    }

    public boolean isWritable() {
        return this.unwritable == 0;
    }

    public boolean getUserDefinedWritability(int n) {
        return (this.unwritable & ChannelOutboundBuffer.writabilityMask(n)) == 0;
    }

    public void setUserDefinedWritability(int n, boolean bl) {
        if (bl) {
            this.setUserDefinedWritability(n);
        } else {
            this.clearUserDefinedWritability(n);
        }
    }

    private void setUserDefinedWritability(int n) {
        block1: {
            int n2;
            int n3;
            int n4 = ~ChannelOutboundBuffer.writabilityMask(n);
            while (!UNWRITABLE_UPDATER.compareAndSet(this, n3 = this.unwritable, n2 = n3 & n4)) {
            }
            if (n3 == 0 || n2 != 0) break block1;
            this.fireChannelWritabilityChanged(true);
        }
    }

    private void clearUserDefinedWritability(int n) {
        block1: {
            int n2;
            int n3;
            int n4 = ChannelOutboundBuffer.writabilityMask(n);
            while (!UNWRITABLE_UPDATER.compareAndSet(this, n3 = this.unwritable, n2 = n3 | n4)) {
            }
            if (n3 != 0 || n2 == 0) break block1;
            this.fireChannelWritabilityChanged(true);
        }
    }

    private static int writabilityMask(int n) {
        if (n < 1 || n > 31) {
            throw new IllegalArgumentException("index: " + n + " (expected: 1~31)");
        }
        return 1 << n;
    }

    private void setWritable(boolean bl) {
        block1: {
            int n;
            int n2;
            while (!UNWRITABLE_UPDATER.compareAndSet(this, n2 = this.unwritable, n = n2 & 0xFFFFFFFE)) {
            }
            if (n2 == 0 || n != 0) break block1;
            this.fireChannelWritabilityChanged(bl);
        }
    }

    private void setUnwritable(boolean bl) {
        block1: {
            int n;
            int n2;
            while (!UNWRITABLE_UPDATER.compareAndSet(this, n2 = this.unwritable, n = n2 | 1)) {
            }
            if (n2 != 0 || n == 0) break block1;
            this.fireChannelWritabilityChanged(bl);
        }
    }

    private void fireChannelWritabilityChanged(boolean bl) {
        ChannelPipeline channelPipeline = this.channel.pipeline();
        if (bl) {
            Runnable runnable = this.fireChannelWritabilityChangedTask;
            if (runnable == null) {
                this.fireChannelWritabilityChangedTask = runnable = new Runnable(this, channelPipeline){
                    final ChannelPipeline val$pipeline;
                    final ChannelOutboundBuffer this$0;
                    {
                        this.this$0 = channelOutboundBuffer;
                        this.val$pipeline = channelPipeline;
                    }

                    @Override
                    public void run() {
                        this.val$pipeline.fireChannelWritabilityChanged();
                    }
                };
            }
            this.channel.eventLoop().execute(runnable);
        } else {
            channelPipeline.fireChannelWritabilityChanged();
        }
    }

    public int size() {
        return this.flushed;
    }

    public boolean isEmpty() {
        return this.flushed == 0;
    }

    void failFlushed(Throwable throwable, boolean bl) {
        if (this.inFail) {
            return;
        }
        try {
            this.inFail = true;
            while (this.remove0(throwable, bl)) {
            }
        } finally {
            this.inFail = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void close(Throwable throwable, boolean bl) {
        if (this.inFail) {
            this.channel.eventLoop().execute(new Runnable(this, throwable, bl){
                final Throwable val$cause;
                final boolean val$allowChannelOpen;
                final ChannelOutboundBuffer this$0;
                {
                    this.this$0 = channelOutboundBuffer;
                    this.val$cause = throwable;
                    this.val$allowChannelOpen = bl;
                }

                @Override
                public void run() {
                    this.this$0.close(this.val$cause, this.val$allowChannelOpen);
                }
            });
            return;
        }
        this.inFail = true;
        if (!bl && this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        }
        if (!this.isEmpty()) {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
        try {
            for (Entry entry = this.unflushedEntry; entry != null; entry = entry.recycleAndGetNext()) {
                int n = entry.pendingSize;
                TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -n);
                if (entry.cancelled) continue;
                ReferenceCountUtil.safeRelease(entry.msg);
                ChannelOutboundBuffer.safeFail(entry.promise, throwable);
            }
        } finally {
            this.inFail = false;
        }
        this.clearNioBuffers();
    }

    void close(ClosedChannelException closedChannelException) {
        this.close(closedChannelException, true);
    }

    private static void safeSuccess(ChannelPromise channelPromise) {
        PromiseNotificationUtil.trySuccess(channelPromise, null, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    private static void safeFail(ChannelPromise channelPromise, Throwable throwable) {
        PromiseNotificationUtil.tryFailure(channelPromise, throwable, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    @Deprecated
    public void recycle() {
    }

    public long totalPendingWriteBytes() {
        return this.totalPendingSize;
    }

    public long bytesBeforeUnwritable() {
        long l = (long)this.channel.config().getWriteBufferHighWaterMark() - this.totalPendingSize;
        if (l > 0L) {
            return this.isWritable() ? l : 0L;
        }
        return 0L;
    }

    public long bytesBeforeWritable() {
        long l = this.totalPendingSize - (long)this.channel.config().getWriteBufferLowWaterMark();
        if (l > 0L) {
            return this.isWritable() ? 0L : l;
        }
        return 0L;
    }

    public void forEachFlushedMessage(MessageProcessor messageProcessor) throws Exception {
        if (messageProcessor == null) {
            throw new NullPointerException("processor");
        }
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return;
        }
        do {
            if (entry.cancelled || messageProcessor.processMessage(entry.msg)) continue;
            return;
        } while (this.isFlushedEntry(entry = entry.next));
    }

    private boolean isFlushedEntry(Entry entry) {
        return entry != null && entry != this.unflushedEntry;
    }

    static {
        $assertionsDisabled = !ChannelOutboundBuffer.class.desiredAssertionStatus();
        CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
        logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
        NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>(){

            @Override
            protected ByteBuffer[] initialValue() throws Exception {
                return new ByteBuffer[1024];
            }

            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
        TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "unwritable");
    }

    static final class Entry {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>(){

            @Override
            protected Entry newObject(Recycler.Handle<Entry> handle) {
                return new Entry(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };
        private final Recycler.Handle<Entry> handle;
        Entry next;
        Object msg;
        ByteBuffer[] bufs;
        ByteBuffer buf;
        ChannelPromise promise;
        long progress;
        long total;
        int pendingSize;
        int count = -1;
        boolean cancelled;

        private Entry(Recycler.Handle<Entry> handle) {
            this.handle = handle;
        }

        static Entry newInstance(Object object, int n, long l, ChannelPromise channelPromise) {
            Entry entry = RECYCLER.get();
            entry.msg = object;
            entry.pendingSize = n + CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
            entry.total = l;
            entry.promise = channelPromise;
            return entry;
        }

        int cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                int n = this.pendingSize;
                ReferenceCountUtil.safeRelease(this.msg);
                this.msg = Unpooled.EMPTY_BUFFER;
                this.pendingSize = 0;
                this.total = 0L;
                this.progress = 0L;
                this.bufs = null;
                this.buf = null;
                return n;
            }
            return 1;
        }

        void recycle() {
            this.next = null;
            this.bufs = null;
            this.buf = null;
            this.msg = null;
            this.promise = null;
            this.progress = 0L;
            this.total = 0L;
            this.pendingSize = 0;
            this.count = -1;
            this.cancelled = false;
            this.handle.recycle(this);
        }

        Entry recycleAndGetNext() {
            Entry entry = this.next;
            this.recycle();
            return entry;
        }

        Entry(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }

    public static interface MessageProcessor {
        public boolean processMessage(Object var1) throws Exception;
    }
}

