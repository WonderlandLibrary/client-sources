/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FixedRecvByteBufAllocator
extends DefaultMaxMessagesRecvByteBufAllocator {
    private final int bufferSize;

    public FixedRecvByteBufAllocator(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("bufferSize must greater than 0: " + n);
        }
        this.bufferSize = n;
    }

    @Override
    public RecvByteBufAllocator.Handle newHandle() {
        return new HandleImpl(this, this.bufferSize);
    }

    @Override
    public FixedRecvByteBufAllocator respectMaybeMoreData(boolean bl) {
        super.respectMaybeMoreData(bl);
        return this;
    }

    @Override
    public DefaultMaxMessagesRecvByteBufAllocator respectMaybeMoreData(boolean bl) {
        return this.respectMaybeMoreData(bl);
    }

    private final class HandleImpl
    extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
        private final int bufferSize;
        final FixedRecvByteBufAllocator this$0;

        public HandleImpl(FixedRecvByteBufAllocator fixedRecvByteBufAllocator, int n) {
            this.this$0 = fixedRecvByteBufAllocator;
            super(fixedRecvByteBufAllocator);
            this.bufferSize = n;
        }

        @Override
        public int guess() {
            return this.bufferSize;
        }
    }
}

