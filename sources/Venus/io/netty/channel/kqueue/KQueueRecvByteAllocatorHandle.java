/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.util.UncheckedBooleanSupplier;
import io.netty.util.internal.ObjectUtil;

final class KQueueRecvByteAllocatorHandle
implements RecvByteBufAllocator.ExtendedHandle {
    private final RecvByteBufAllocator.ExtendedHandle delegate;
    private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier(this){
        final KQueueRecvByteAllocatorHandle this$0;
        {
            this.this$0 = kQueueRecvByteAllocatorHandle;
        }

        @Override
        public boolean get() {
            return this.this$0.maybeMoreDataToRead();
        }
    };
    private boolean overrideGuess;
    private boolean readEOF;
    private long numberBytesPending;

    KQueueRecvByteAllocatorHandle(RecvByteBufAllocator.ExtendedHandle extendedHandle) {
        this.delegate = ObjectUtil.checkNotNull(extendedHandle, "handle");
    }

    @Override
    public int guess() {
        return this.overrideGuess ? this.guess0() : this.delegate.guess();
    }

    @Override
    public void reset(ChannelConfig channelConfig) {
        this.overrideGuess = ((KQueueChannelConfig)channelConfig).getRcvAllocTransportProvidesGuess();
        this.delegate.reset(channelConfig);
    }

    @Override
    public void incMessagesRead(int n) {
        this.delegate.incMessagesRead(n);
    }

    @Override
    public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
        return this.overrideGuess ? byteBufAllocator.ioBuffer(this.guess0()) : this.delegate.allocate(byteBufAllocator);
    }

    @Override
    public void lastBytesRead(int n) {
        this.numberBytesPending = n < 0 ? 0L : Math.max(0L, this.numberBytesPending - (long)n);
        this.delegate.lastBytesRead(n);
    }

    @Override
    public int lastBytesRead() {
        return this.delegate.lastBytesRead();
    }

    @Override
    public void attemptedBytesRead(int n) {
        this.delegate.attemptedBytesRead(n);
    }

    @Override
    public int attemptedBytesRead() {
        return this.delegate.attemptedBytesRead();
    }

    @Override
    public void readComplete() {
        this.delegate.readComplete();
    }

    @Override
    public boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
        return this.delegate.continueReading(uncheckedBooleanSupplier);
    }

    @Override
    public boolean continueReading() {
        return this.delegate.continueReading(this.defaultMaybeMoreDataSupplier);
    }

    void readEOF() {
        this.readEOF = true;
    }

    void numberBytesPending(long l) {
        this.numberBytesPending = l;
    }

    boolean maybeMoreDataToRead() {
        return this.numberBytesPending != 0L || this.readEOF;
    }

    private int guess0() {
        return (int)Math.min(this.numberBytesPending, Integer.MAX_VALUE);
    }
}

