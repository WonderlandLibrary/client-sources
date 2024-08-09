/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.ByteBufAllocatorMetricProvider;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.buffer.UnpooledUnsafeHeapByteBuf;
import io.netty.buffer.UnpooledUnsafeNoCleanerDirectByteBuf;
import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;

public final class UnpooledByteBufAllocator
extends AbstractByteBufAllocator
implements ByteBufAllocatorMetricProvider {
    private final UnpooledByteBufAllocatorMetric metric = new UnpooledByteBufAllocatorMetric(null);
    private final boolean disableLeakDetector;
    private final boolean noCleaner;
    public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());

    public UnpooledByteBufAllocator(boolean bl) {
        this(bl, false);
    }

    public UnpooledByteBufAllocator(boolean bl, boolean bl2) {
        this(bl, bl2, PlatformDependent.useDirectBufferNoCleaner());
    }

    public UnpooledByteBufAllocator(boolean bl, boolean bl2, boolean bl3) {
        super(bl);
        this.disableLeakDetector = bl2;
        this.noCleaner = bl3 && PlatformDependent.hasUnsafe() && PlatformDependent.hasDirectBufferNoCleanerConstructor();
    }

    @Override
    protected ByteBuf newHeapBuffer(int n, int n2) {
        return PlatformDependent.hasUnsafe() ? new InstrumentedUnpooledUnsafeHeapByteBuf(this, n, n2) : new InstrumentedUnpooledHeapByteBuf(this, n, n2);
    }

    @Override
    protected ByteBuf newDirectBuffer(int n, int n2) {
        AbstractReferenceCountedByteBuf abstractReferenceCountedByteBuf = PlatformDependent.hasUnsafe() ? (this.noCleaner ? new InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(this, n, n2) : new InstrumentedUnpooledUnsafeDirectByteBuf(this, n, n2)) : new InstrumentedUnpooledDirectByteBuf(this, n, n2);
        return this.disableLeakDetector ? abstractReferenceCountedByteBuf : UnpooledByteBufAllocator.toLeakAwareBuffer(abstractReferenceCountedByteBuf);
    }

    @Override
    public CompositeByteBuf compositeHeapBuffer(int n) {
        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(this, false, n);
        return this.disableLeakDetector ? compositeByteBuf : UnpooledByteBufAllocator.toLeakAwareBuffer(compositeByteBuf);
    }

    @Override
    public CompositeByteBuf compositeDirectBuffer(int n) {
        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(this, true, n);
        return this.disableLeakDetector ? compositeByteBuf : UnpooledByteBufAllocator.toLeakAwareBuffer(compositeByteBuf);
    }

    @Override
    public boolean isDirectBufferPooled() {
        return true;
    }

    @Override
    public ByteBufAllocatorMetric metric() {
        return this.metric;
    }

    void incrementDirect(int n) {
        this.metric.directCounter.add(n);
    }

    void decrementDirect(int n) {
        this.metric.directCounter.add(-n);
    }

    void incrementHeap(int n) {
        this.metric.heapCounter.add(n);
    }

    void decrementHeap(int n) {
        this.metric.heapCounter.add(-n);
    }

    private static final class UnpooledByteBufAllocatorMetric
    implements ByteBufAllocatorMetric {
        final LongCounter directCounter = PlatformDependent.newLongCounter();
        final LongCounter heapCounter = PlatformDependent.newLongCounter();

        private UnpooledByteBufAllocatorMetric() {
        }

        @Override
        public long usedHeapMemory() {
            return this.heapCounter.value();
        }

        @Override
        public long usedDirectMemory() {
            return this.directCounter.value();
        }

        public String toString() {
            return StringUtil.simpleClassName(this) + "(usedHeapMemory: " + this.usedHeapMemory() + "; usedDirectMemory: " + this.usedDirectMemory() + ')';
        }

        UnpooledByteBufAllocatorMetric(1 var1_1) {
            this();
        }
    }

    private static final class InstrumentedUnpooledDirectByteBuf
    extends UnpooledDirectByteBuf {
        InstrumentedUnpooledDirectByteBuf(UnpooledByteBufAllocator unpooledByteBufAllocator, int n, int n2) {
            super((ByteBufAllocator)unpooledByteBufAllocator, n, n2);
        }

        @Override
        protected ByteBuffer allocateDirect(int n) {
            ByteBuffer byteBuffer = super.allocateDirect(n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(byteBuffer.capacity());
            return byteBuffer;
        }

        @Override
        protected void freeDirect(ByteBuffer byteBuffer) {
            int n = byteBuffer.capacity();
            super.freeDirect(byteBuffer);
            ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(n);
        }
    }

    private static final class InstrumentedUnpooledUnsafeDirectByteBuf
    extends UnpooledUnsafeDirectByteBuf {
        InstrumentedUnpooledUnsafeDirectByteBuf(UnpooledByteBufAllocator unpooledByteBufAllocator, int n, int n2) {
            super((ByteBufAllocator)unpooledByteBufAllocator, n, n2);
        }

        @Override
        protected ByteBuffer allocateDirect(int n) {
            ByteBuffer byteBuffer = super.allocateDirect(n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(byteBuffer.capacity());
            return byteBuffer;
        }

        @Override
        protected void freeDirect(ByteBuffer byteBuffer) {
            int n = byteBuffer.capacity();
            super.freeDirect(byteBuffer);
            ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(n);
        }
    }

    private static final class InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf
    extends UnpooledUnsafeNoCleanerDirectByteBuf {
        InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(UnpooledByteBufAllocator unpooledByteBufAllocator, int n, int n2) {
            super((ByteBufAllocator)unpooledByteBufAllocator, n, n2);
        }

        @Override
        protected ByteBuffer allocateDirect(int n) {
            ByteBuffer byteBuffer = super.allocateDirect(n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(byteBuffer.capacity());
            return byteBuffer;
        }

        @Override
        ByteBuffer reallocateDirect(ByteBuffer byteBuffer, int n) {
            int n2 = byteBuffer.capacity();
            ByteBuffer byteBuffer2 = super.reallocateDirect(byteBuffer, n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(byteBuffer2.capacity() - n2);
            return byteBuffer2;
        }

        @Override
        protected void freeDirect(ByteBuffer byteBuffer) {
            int n = byteBuffer.capacity();
            super.freeDirect(byteBuffer);
            ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(n);
        }
    }

    private static final class InstrumentedUnpooledHeapByteBuf
    extends UnpooledHeapByteBuf {
        InstrumentedUnpooledHeapByteBuf(UnpooledByteBufAllocator unpooledByteBufAllocator, int n, int n2) {
            super((ByteBufAllocator)unpooledByteBufAllocator, n, n2);
        }

        @Override
        byte[] allocateArray(int n) {
            byte[] byArray = super.allocateArray(n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementHeap(byArray.length);
            return byArray;
        }

        @Override
        void freeArray(byte[] byArray) {
            int n = byArray.length;
            super.freeArray(byArray);
            ((UnpooledByteBufAllocator)this.alloc()).decrementHeap(n);
        }
    }

    private static final class InstrumentedUnpooledUnsafeHeapByteBuf
    extends UnpooledUnsafeHeapByteBuf {
        InstrumentedUnpooledUnsafeHeapByteBuf(UnpooledByteBufAllocator unpooledByteBufAllocator, int n, int n2) {
            super((ByteBufAllocator)unpooledByteBufAllocator, n, n2);
        }

        @Override
        byte[] allocateArray(int n) {
            byte[] byArray = super.allocateArray(n);
            ((UnpooledByteBufAllocator)this.alloc()).incrementHeap(byArray.length);
            return byArray;
        }

        @Override
        void freeArray(byte[] byArray) {
            int n = byArray.length;
            super.freeArray(byArray);
            ((UnpooledByteBufAllocator)this.alloc()).decrementHeap(n);
        }
    }
}

