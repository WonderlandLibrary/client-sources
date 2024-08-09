/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AdvancedLeakAwareByteBuf;
import io.netty.buffer.AdvancedLeakAwareCompositeByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.SimpleLeakAwareByteBuf;
import io.netty.buffer.SimpleLeakAwareCompositeByteBuf;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;

public abstract class AbstractByteBufAllocator
implements ByteBufAllocator {
    static final int DEFAULT_INITIAL_CAPACITY = 256;
    static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE;
    static final int DEFAULT_MAX_COMPONENTS = 16;
    static final int CALCULATE_THRESHOLD = 0x400000;
    private final boolean directByDefault;
    private final ByteBuf emptyBuf;

    protected static ByteBuf toLeakAwareBuffer(ByteBuf byteBuf) {
        switch (1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1: {
                ResourceLeakTracker<ByteBuf> resourceLeakTracker = AbstractByteBuf.leakDetector.track(byteBuf);
                if (resourceLeakTracker == null) break;
                byteBuf = new SimpleLeakAwareByteBuf(byteBuf, resourceLeakTracker);
                break;
            }
            case 2: 
            case 3: {
                ResourceLeakTracker<ByteBuf> resourceLeakTracker = AbstractByteBuf.leakDetector.track(byteBuf);
                if (resourceLeakTracker == null) break;
                byteBuf = new AdvancedLeakAwareByteBuf(byteBuf, resourceLeakTracker);
                break;
            }
        }
        return byteBuf;
    }

    protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf compositeByteBuf) {
        switch (1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1: {
                ResourceLeakTracker<ByteBuf> resourceLeakTracker = AbstractByteBuf.leakDetector.track(compositeByteBuf);
                if (resourceLeakTracker == null) break;
                compositeByteBuf = new SimpleLeakAwareCompositeByteBuf(compositeByteBuf, resourceLeakTracker);
                break;
            }
            case 2: 
            case 3: {
                ResourceLeakTracker<ByteBuf> resourceLeakTracker = AbstractByteBuf.leakDetector.track(compositeByteBuf);
                if (resourceLeakTracker == null) break;
                compositeByteBuf = new AdvancedLeakAwareCompositeByteBuf(compositeByteBuf, resourceLeakTracker);
                break;
            }
        }
        return compositeByteBuf;
    }

    protected AbstractByteBufAllocator() {
        this(false);
    }

    protected AbstractByteBufAllocator(boolean bl) {
        this.directByDefault = bl && PlatformDependent.hasUnsafe();
        this.emptyBuf = new EmptyByteBuf(this);
    }

    @Override
    public ByteBuf buffer() {
        if (this.directByDefault) {
            return this.directBuffer();
        }
        return this.heapBuffer();
    }

    @Override
    public ByteBuf buffer(int n) {
        if (this.directByDefault) {
            return this.directBuffer(n);
        }
        return this.heapBuffer(n);
    }

    @Override
    public ByteBuf buffer(int n, int n2) {
        if (this.directByDefault) {
            return this.directBuffer(n, n2);
        }
        return this.heapBuffer(n, n2);
    }

    @Override
    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(256);
        }
        return this.heapBuffer(256);
    }

    @Override
    public ByteBuf ioBuffer(int n) {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(n);
        }
        return this.heapBuffer(n);
    }

    @Override
    public ByteBuf ioBuffer(int n, int n2) {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(n, n2);
        }
        return this.heapBuffer(n, n2);
    }

    @Override
    public ByteBuf heapBuffer() {
        return this.heapBuffer(256, Integer.MAX_VALUE);
    }

    @Override
    public ByteBuf heapBuffer(int n) {
        return this.heapBuffer(n, Integer.MAX_VALUE);
    }

    @Override
    public ByteBuf heapBuffer(int n, int n2) {
        if (n == 0 && n2 == 0) {
            return this.emptyBuf;
        }
        AbstractByteBufAllocator.validate(n, n2);
        return this.newHeapBuffer(n, n2);
    }

    @Override
    public ByteBuf directBuffer() {
        return this.directBuffer(256, Integer.MAX_VALUE);
    }

    @Override
    public ByteBuf directBuffer(int n) {
        return this.directBuffer(n, Integer.MAX_VALUE);
    }

    @Override
    public ByteBuf directBuffer(int n, int n2) {
        if (n == 0 && n2 == 0) {
            return this.emptyBuf;
        }
        AbstractByteBufAllocator.validate(n, n2);
        return this.newDirectBuffer(n, n2);
    }

    @Override
    public CompositeByteBuf compositeBuffer() {
        if (this.directByDefault) {
            return this.compositeDirectBuffer();
        }
        return this.compositeHeapBuffer();
    }

    @Override
    public CompositeByteBuf compositeBuffer(int n) {
        if (this.directByDefault) {
            return this.compositeDirectBuffer(n);
        }
        return this.compositeHeapBuffer(n);
    }

    @Override
    public CompositeByteBuf compositeHeapBuffer() {
        return this.compositeHeapBuffer(16);
    }

    @Override
    public CompositeByteBuf compositeHeapBuffer(int n) {
        return AbstractByteBufAllocator.toLeakAwareBuffer(new CompositeByteBuf(this, false, n));
    }

    @Override
    public CompositeByteBuf compositeDirectBuffer() {
        return this.compositeDirectBuffer(16);
    }

    @Override
    public CompositeByteBuf compositeDirectBuffer(int n) {
        return AbstractByteBufAllocator.toLeakAwareBuffer(new CompositeByteBuf(this, true, n));
    }

    private static void validate(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity: " + n + " (expected: 0+)");
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", n, n2));
        }
    }

    protected abstract ByteBuf newHeapBuffer(int var1, int var2);

    protected abstract ByteBuf newDirectBuffer(int var1, int var2);

    public String toString() {
        return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
    }

    @Override
    public int calculateNewCapacity(int n, int n2) {
        int n3;
        if (n < 0) {
            throw new IllegalArgumentException("minNewCapacity: " + n + " (expected: 0+)");
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", n, n2));
        }
        int n4 = 0x400000;
        if (n == 0x400000) {
            return 1;
        }
        if (n > 0x400000) {
            int n5 = n / 0x400000 * 0x400000;
            n5 = n5 > n2 - 0x400000 ? n2 : (n5 += 0x400000);
            return n5;
        }
        for (n3 = 64; n3 < n; n3 <<= 1) {
        }
        return Math.min(n3, n2);
    }

    static {
        ResourceLeakDetector.addExclusions(AbstractByteBufAllocator.class, "toLeakAwareBuffer");
    }
}

