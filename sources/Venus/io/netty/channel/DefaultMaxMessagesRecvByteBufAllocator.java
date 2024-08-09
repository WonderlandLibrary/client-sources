/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MaxMessagesRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.UncheckedBooleanSupplier;

public abstract class DefaultMaxMessagesRecvByteBufAllocator
implements MaxMessagesRecvByteBufAllocator {
    private volatile int maxMessagesPerRead;
    private volatile boolean respectMaybeMoreData = true;

    public DefaultMaxMessagesRecvByteBufAllocator() {
        this(1);
    }

    public DefaultMaxMessagesRecvByteBufAllocator(int n) {
        this.maxMessagesPerRead(n);
    }

    @Override
    public int maxMessagesPerRead() {
        return this.maxMessagesPerRead;
    }

    @Override
    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxMessagesPerRead: " + n + " (expected: > 0)");
        }
        this.maxMessagesPerRead = n;
        return this;
    }

    public DefaultMaxMessagesRecvByteBufAllocator respectMaybeMoreData(boolean bl) {
        this.respectMaybeMoreData = bl;
        return this;
    }

    public final boolean respectMaybeMoreData() {
        return this.respectMaybeMoreData;
    }

    static boolean access$000(DefaultMaxMessagesRecvByteBufAllocator defaultMaxMessagesRecvByteBufAllocator) {
        return defaultMaxMessagesRecvByteBufAllocator.respectMaybeMoreData;
    }

    public abstract class MaxMessageHandle
    implements RecvByteBufAllocator.ExtendedHandle {
        private ChannelConfig config;
        private int maxMessagePerRead;
        private int totalMessages;
        private int totalBytesRead;
        private int attemptedBytesRead;
        private int lastBytesRead;
        private final boolean respectMaybeMoreData;
        private final UncheckedBooleanSupplier defaultMaybeMoreSupplier;
        final DefaultMaxMessagesRecvByteBufAllocator this$0;

        public MaxMessageHandle(DefaultMaxMessagesRecvByteBufAllocator defaultMaxMessagesRecvByteBufAllocator) {
            this.this$0 = defaultMaxMessagesRecvByteBufAllocator;
            this.respectMaybeMoreData = DefaultMaxMessagesRecvByteBufAllocator.access$000(this.this$0);
            this.defaultMaybeMoreSupplier = new UncheckedBooleanSupplier(this){
                final MaxMessageHandle this$1;
                {
                    this.this$1 = maxMessageHandle;
                }

                @Override
                public boolean get() {
                    return MaxMessageHandle.access$100(this.this$1) == MaxMessageHandle.access$200(this.this$1);
                }
            };
        }

        @Override
        public void reset(ChannelConfig channelConfig) {
            this.config = channelConfig;
            this.maxMessagePerRead = this.this$0.maxMessagesPerRead();
            this.totalBytesRead = 0;
            this.totalMessages = 0;
        }

        @Override
        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(this.guess());
        }

        @Override
        public final void incMessagesRead(int n) {
            this.totalMessages += n;
        }

        @Override
        public void lastBytesRead(int n) {
            this.lastBytesRead = n;
            if (n > 0) {
                this.totalBytesRead += n;
            }
        }

        @Override
        public final int lastBytesRead() {
            return this.lastBytesRead;
        }

        @Override
        public boolean continueReading() {
            return this.continueReading(this.defaultMaybeMoreSupplier);
        }

        @Override
        public boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
            return this.config.isAutoRead() && (!this.respectMaybeMoreData || uncheckedBooleanSupplier.get()) && this.totalMessages < this.maxMessagePerRead && this.totalBytesRead > 0;
        }

        @Override
        public void readComplete() {
        }

        @Override
        public int attemptedBytesRead() {
            return this.attemptedBytesRead;
        }

        @Override
        public void attemptedBytesRead(int n) {
            this.attemptedBytesRead = n;
        }

        protected final int totalBytesRead() {
            return this.totalBytesRead < 0 ? Integer.MAX_VALUE : this.totalBytesRead;
        }

        static int access$100(MaxMessageHandle maxMessageHandle) {
            return maxMessageHandle.attemptedBytesRead;
        }

        static int access$200(MaxMessageHandle maxMessageHandle) {
            return maxMessageHandle.lastBytesRead;
        }
    }
}

