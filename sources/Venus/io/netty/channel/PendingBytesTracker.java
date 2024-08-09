/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.MessageSizeEstimator;
import io.netty.util.internal.ObjectUtil;

abstract class PendingBytesTracker
implements MessageSizeEstimator.Handle {
    private final MessageSizeEstimator.Handle estimatorHandle;

    private PendingBytesTracker(MessageSizeEstimator.Handle handle) {
        this.estimatorHandle = ObjectUtil.checkNotNull(handle, "estimatorHandle");
    }

    @Override
    public final int size(Object object) {
        return this.estimatorHandle.size(object);
    }

    public abstract void incrementPendingOutboundBytes(long var1);

    public abstract void decrementPendingOutboundBytes(long var1);

    static PendingBytesTracker newTracker(Channel channel) {
        if (channel.pipeline() instanceof DefaultChannelPipeline) {
            return new DefaultChannelPipelinePendingBytesTracker((DefaultChannelPipeline)channel.pipeline());
        }
        ChannelOutboundBuffer channelOutboundBuffer = channel.unsafe().outboundBuffer();
        MessageSizeEstimator.Handle handle = channel.config().getMessageSizeEstimator().newHandle();
        return channelOutboundBuffer == null ? new NoopPendingBytesTracker(handle) : new ChannelOutboundBufferPendingBytesTracker(channelOutboundBuffer, handle);
    }

    PendingBytesTracker(MessageSizeEstimator.Handle handle, 1 var2_2) {
        this(handle);
    }

    private static final class NoopPendingBytesTracker
    extends PendingBytesTracker {
        NoopPendingBytesTracker(MessageSizeEstimator.Handle handle) {
            super(handle, null);
        }

        @Override
        public void incrementPendingOutboundBytes(long l) {
        }

        @Override
        public void decrementPendingOutboundBytes(long l) {
        }
    }

    private static final class ChannelOutboundBufferPendingBytesTracker
    extends PendingBytesTracker {
        private final ChannelOutboundBuffer buffer;

        ChannelOutboundBufferPendingBytesTracker(ChannelOutboundBuffer channelOutboundBuffer, MessageSizeEstimator.Handle handle) {
            super(handle, null);
            this.buffer = channelOutboundBuffer;
        }

        @Override
        public void incrementPendingOutboundBytes(long l) {
            this.buffer.incrementPendingOutboundBytes(l);
        }

        @Override
        public void decrementPendingOutboundBytes(long l) {
            this.buffer.decrementPendingOutboundBytes(l);
        }
    }

    private static final class DefaultChannelPipelinePendingBytesTracker
    extends PendingBytesTracker {
        private final DefaultChannelPipeline pipeline;

        DefaultChannelPipelinePendingBytesTracker(DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline.estimatorHandle(), null);
            this.pipeline = defaultChannelPipeline;
        }

        @Override
        public void incrementPendingOutboundBytes(long l) {
            this.pipeline.incrementPendingOutboundBytes(l);
        }

        @Override
        public void decrementPendingOutboundBytes(long l) {
            this.pipeline.decrementPendingOutboundBytes(l);
        }
    }
}

