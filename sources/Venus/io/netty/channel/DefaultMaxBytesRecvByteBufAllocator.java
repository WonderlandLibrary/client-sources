/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MaxBytesRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.UncheckedBooleanSupplier;
import java.util.AbstractMap;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultMaxBytesRecvByteBufAllocator
implements MaxBytesRecvByteBufAllocator {
    private volatile int maxBytesPerRead;
    private volatile int maxBytesPerIndividualRead;

    public DefaultMaxBytesRecvByteBufAllocator() {
        this(65536, 65536);
    }

    public DefaultMaxBytesRecvByteBufAllocator(int n, int n2) {
        DefaultMaxBytesRecvByteBufAllocator.checkMaxBytesPerReadPair(n, n2);
        this.maxBytesPerRead = n;
        this.maxBytesPerIndividualRead = n2;
    }

    @Override
    public RecvByteBufAllocator.Handle newHandle() {
        return new HandleImpl(this, null);
    }

    @Override
    public int maxBytesPerRead() {
        return this.maxBytesPerRead;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerRead(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxBytesPerRead: " + n + " (expected: > 0)");
        }
        DefaultMaxBytesRecvByteBufAllocator defaultMaxBytesRecvByteBufAllocator = this;
        synchronized (defaultMaxBytesRecvByteBufAllocator) {
            int n2 = this.maxBytesPerIndividualRead();
            if (n < n2) {
                throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + n2 + "): " + n);
            }
            this.maxBytesPerRead = n;
        }
        return this;
    }

    @Override
    public int maxBytesPerIndividualRead() {
        return this.maxBytesPerIndividualRead;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxBytesPerIndividualRead: " + n + " (expected: > 0)");
        }
        DefaultMaxBytesRecvByteBufAllocator defaultMaxBytesRecvByteBufAllocator = this;
        synchronized (defaultMaxBytesRecvByteBufAllocator) {
            int n2 = this.maxBytesPerRead();
            if (n > n2) {
                throw new IllegalArgumentException("maxBytesPerIndividualRead cannot be greater than maxBytesPerRead (" + n2 + "): " + n);
            }
            this.maxBytesPerIndividualRead = n;
        }
        return this;
    }

    @Override
    public synchronized Map.Entry<Integer, Integer> maxBytesPerReadPair() {
        return new AbstractMap.SimpleEntry<Integer, Integer>(this.maxBytesPerRead, this.maxBytesPerIndividualRead);
    }

    private static void checkMaxBytesPerReadPair(int n, int n2) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxBytesPerRead: " + n + " (expected: > 0)");
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxBytesPerIndividualRead: " + n2 + " (expected: > 0)");
        }
        if (n < n2) {
            throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + n2 + "): " + n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerReadPair(int n, int n2) {
        DefaultMaxBytesRecvByteBufAllocator.checkMaxBytesPerReadPair(n, n2);
        DefaultMaxBytesRecvByteBufAllocator defaultMaxBytesRecvByteBufAllocator = this;
        synchronized (defaultMaxBytesRecvByteBufAllocator) {
            this.maxBytesPerRead = n;
            this.maxBytesPerIndividualRead = n2;
        }
        return this;
    }

    @Override
    public MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int n, int n2) {
        return this.maxBytesPerReadPair(n, n2);
    }

    @Override
    public MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int n) {
        return this.maxBytesPerIndividualRead(n);
    }

    @Override
    public MaxBytesRecvByteBufAllocator maxBytesPerRead(int n) {
        return this.maxBytesPerRead(n);
    }

    private final class HandleImpl
    implements RecvByteBufAllocator.ExtendedHandle {
        private int individualReadMax;
        private int bytesToRead;
        private int lastBytesRead;
        private int attemptBytesRead;
        private final UncheckedBooleanSupplier defaultMaybeMoreSupplier;
        final DefaultMaxBytesRecvByteBufAllocator this$0;

        private HandleImpl(DefaultMaxBytesRecvByteBufAllocator defaultMaxBytesRecvByteBufAllocator) {
            this.this$0 = defaultMaxBytesRecvByteBufAllocator;
            this.defaultMaybeMoreSupplier = new UncheckedBooleanSupplier(this){
                final HandleImpl this$1;
                {
                    this.this$1 = handleImpl;
                }

                @Override
                public boolean get() {
                    return HandleImpl.access$000(this.this$1) == HandleImpl.access$100(this.this$1);
                }
            };
        }

        @Override
        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(this.guess());
        }

        @Override
        public int guess() {
            return Math.min(this.individualReadMax, this.bytesToRead);
        }

        @Override
        public void reset(ChannelConfig channelConfig) {
            this.bytesToRead = this.this$0.maxBytesPerRead();
            this.individualReadMax = this.this$0.maxBytesPerIndividualRead();
        }

        @Override
        public void incMessagesRead(int n) {
        }

        @Override
        public void lastBytesRead(int n) {
            this.lastBytesRead = n;
            this.bytesToRead -= n;
        }

        @Override
        public int lastBytesRead() {
            return this.lastBytesRead;
        }

        @Override
        public boolean continueReading() {
            return this.continueReading(this.defaultMaybeMoreSupplier);
        }

        @Override
        public boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
            return this.bytesToRead > 0 && uncheckedBooleanSupplier.get();
        }

        @Override
        public void readComplete() {
        }

        @Override
        public void attemptedBytesRead(int n) {
            this.attemptBytesRead = n;
        }

        @Override
        public int attemptedBytesRead() {
            return this.attemptBytesRead;
        }

        static int access$000(HandleImpl handleImpl) {
            return handleImpl.attemptBytesRead;
        }

        static int access$100(HandleImpl handleImpl) {
            return handleImpl.lastBytesRead;
        }

        HandleImpl(DefaultMaxBytesRecvByteBufAllocator defaultMaxBytesRecvByteBufAllocator, 1 var2_2) {
            this(defaultMaxBytesRecvByteBufAllocator);
        }
    }
}

