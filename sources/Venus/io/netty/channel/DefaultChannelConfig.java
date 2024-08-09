/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultMessageSizeEstimator;
import io.netty.channel.MaxMessagesRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.internal.ObjectUtil;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultChannelConfig
implements ChannelConfig {
    private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(DefaultChannelConfig.class, "autoRead");
    private static final AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> WATERMARK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelConfig.class, WriteBufferWaterMark.class, "writeBufferWaterMark");
    protected final Channel channel;
    private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
    private volatile RecvByteBufAllocator rcvBufAllocator;
    private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
    private volatile int connectTimeoutMillis = 30000;
    private volatile int writeSpinCount = 16;
    private volatile int autoRead = 1;
    private volatile boolean autoClose = true;
    private volatile WriteBufferWaterMark writeBufferWaterMark = WriteBufferWaterMark.DEFAULT;
    private volatile boolean pinEventExecutor = true;

    public DefaultChannelConfig(Channel channel) {
        this(channel, new AdaptiveRecvByteBufAllocator());
    }

    protected DefaultChannelConfig(Channel channel, RecvByteBufAllocator recvByteBufAllocator) {
        this.setRecvByteBufAllocator(recvByteBufAllocator, channel.metadata());
        this.channel = channel;
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.WRITE_BUFFER_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR, ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
    }

    protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> map, ChannelOption<?> ... channelOptionArray) {
        if (map == null) {
            map = new IdentityHashMap();
        }
        for (ChannelOption<?> channelOption : channelOptionArray) {
            map.put(channelOption, this.getOption(channelOption));
        }
        return map;
    }

    @Override
    public boolean setOptions(Map<ChannelOption<?>, ?> map) {
        if (map == null) {
            throw new NullPointerException("options");
        }
        boolean bl = true;
        for (Map.Entry<ChannelOption<?>, ?> entry : map.entrySet()) {
            if (this.setOption(entry.getKey(), entry.getValue())) continue;
            bl = false;
        }
        return bl;
    }

    @Override
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return (T)Integer.valueOf(this.getConnectTimeoutMillis());
        }
        if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            return (T)Integer.valueOf(this.getMaxMessagesPerRead());
        }
        if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            return (T)Integer.valueOf(this.getWriteSpinCount());
        }
        if (channelOption == ChannelOption.ALLOCATOR) {
            return (T)this.getAllocator();
        }
        if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            return this.getRecvByteBufAllocator();
        }
        if (channelOption == ChannelOption.AUTO_READ) {
            return (T)Boolean.valueOf(this.isAutoRead());
        }
        if (channelOption == ChannelOption.AUTO_CLOSE) {
            return (T)Boolean.valueOf(this.isAutoClose());
        }
        if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            return (T)Integer.valueOf(this.getWriteBufferHighWaterMark());
        }
        if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            return (T)Integer.valueOf(this.getWriteBufferLowWaterMark());
        }
        if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
            return (T)this.getWriteBufferWaterMark();
        }
        if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            return (T)this.getMessageSizeEstimator();
        }
        if (channelOption == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
            return (T)Boolean.valueOf(this.getPinEventExecutorPerGroup());
        }
        return null;
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        this.validate(channelOption, t);
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            this.setConnectTimeoutMillis((Integer)t);
        } else if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            this.setMaxMessagesPerRead((Integer)t);
        } else if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            this.setWriteSpinCount((Integer)t);
        } else if (channelOption == ChannelOption.ALLOCATOR) {
            this.setAllocator((ByteBufAllocator)t);
        } else if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            this.setRecvByteBufAllocator((RecvByteBufAllocator)t);
        } else if (channelOption == ChannelOption.AUTO_READ) {
            this.setAutoRead((Boolean)t);
        } else if (channelOption == ChannelOption.AUTO_CLOSE) {
            this.setAutoClose((Boolean)t);
        } else if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            this.setWriteBufferHighWaterMark((Integer)t);
        } else if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            this.setWriteBufferLowWaterMark((Integer)t);
        } else if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
            this.setWriteBufferWaterMark((WriteBufferWaterMark)t);
        } else if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            this.setMessageSizeEstimator((MessageSizeEstimator)t);
        } else if (channelOption == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
            this.setPinEventExecutorPerGroup((Boolean)t);
        } else {
            return true;
        }
        return false;
    }

    protected <T> void validate(ChannelOption<T> channelOption, T t) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        channelOption.validate(t);
    }

    @Override
    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", n));
        }
        this.connectTimeoutMillis = n;
        return this;
    }

    @Override
    @Deprecated
    public int getMaxMessagesPerRead() {
        try {
            MaxMessagesRecvByteBufAllocator maxMessagesRecvByteBufAllocator = (MaxMessagesRecvByteBufAllocator)this.getRecvByteBufAllocator();
            return maxMessagesRecvByteBufAllocator.maxMessagesPerRead();
        } catch (ClassCastException classCastException) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", classCastException);
        }
    }

    @Override
    @Deprecated
    public ChannelConfig setMaxMessagesPerRead(int n) {
        try {
            MaxMessagesRecvByteBufAllocator maxMessagesRecvByteBufAllocator = (MaxMessagesRecvByteBufAllocator)this.getRecvByteBufAllocator();
            maxMessagesRecvByteBufAllocator.maxMessagesPerRead(n);
            return this;
        } catch (ClassCastException classCastException) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", classCastException);
        }
    }

    @Override
    public int getWriteSpinCount() {
        return this.writeSpinCount;
    }

    @Override
    public ChannelConfig setWriteSpinCount(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
        }
        if (n == Integer.MAX_VALUE) {
            --n;
        }
        this.writeSpinCount = n;
        return this;
    }

    @Override
    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }

    @Override
    public ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        if (byteBufAllocator == null) {
            throw new NullPointerException("allocator");
        }
        this.allocator = byteBufAllocator;
        return this;
    }

    @Override
    public <T extends RecvByteBufAllocator> T getRecvByteBufAllocator() {
        return (T)this.rcvBufAllocator;
    }

    @Override
    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        this.rcvBufAllocator = ObjectUtil.checkNotNull(recvByteBufAllocator, "allocator");
        return this;
    }

    private void setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator, ChannelMetadata channelMetadata) {
        if (recvByteBufAllocator instanceof MaxMessagesRecvByteBufAllocator) {
            ((MaxMessagesRecvByteBufAllocator)recvByteBufAllocator).maxMessagesPerRead(channelMetadata.defaultMaxMessagesPerRead());
        } else if (recvByteBufAllocator == null) {
            throw new NullPointerException("allocator");
        }
        this.setRecvByteBufAllocator(recvByteBufAllocator);
    }

    @Override
    public boolean isAutoRead() {
        return this.autoRead == 1;
    }

    @Override
    public ChannelConfig setAutoRead(boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = AUTOREAD_UPDATER.getAndSet(this, bl ? 1 : 0) == 1;
        if (bl && !bl2) {
            this.channel.read();
        } else if (!bl && bl2) {
            this.autoReadCleared();
        }
        return this;
    }

    protected void autoReadCleared() {
    }

    @Override
    public boolean isAutoClose() {
        return this.autoClose;
    }

    @Override
    public ChannelConfig setAutoClose(boolean bl) {
        this.autoClose = bl;
        return this;
    }

    @Override
    public int getWriteBufferHighWaterMark() {
        return this.writeBufferWaterMark.high();
    }

    @Override
    public ChannelConfig setWriteBufferHighWaterMark(int n) {
        WriteBufferWaterMark writeBufferWaterMark;
        if (n < 0) {
            throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
        }
        do {
            if (n >= (writeBufferWaterMark = this.writeBufferWaterMark).low()) continue;
            throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + writeBufferWaterMark.low() + "): " + n);
        } while (!WATERMARK_UPDATER.compareAndSet(this, writeBufferWaterMark, new WriteBufferWaterMark(writeBufferWaterMark.low(), n, false)));
        return this;
    }

    @Override
    public int getWriteBufferLowWaterMark() {
        return this.writeBufferWaterMark.low();
    }

    @Override
    public ChannelConfig setWriteBufferLowWaterMark(int n) {
        WriteBufferWaterMark writeBufferWaterMark;
        if (n < 0) {
            throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
        }
        do {
            if (n <= (writeBufferWaterMark = this.writeBufferWaterMark).high()) continue;
            throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + writeBufferWaterMark.high() + "): " + n);
        } while (!WATERMARK_UPDATER.compareAndSet(this, writeBufferWaterMark, new WriteBufferWaterMark(n, writeBufferWaterMark.high(), false)));
        return this;
    }

    @Override
    public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        this.writeBufferWaterMark = ObjectUtil.checkNotNull(writeBufferWaterMark, "writeBufferWaterMark");
        return this;
    }

    @Override
    public WriteBufferWaterMark getWriteBufferWaterMark() {
        return this.writeBufferWaterMark;
    }

    @Override
    public MessageSizeEstimator getMessageSizeEstimator() {
        return this.msgSizeEstimator;
    }

    @Override
    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        if (messageSizeEstimator == null) {
            throw new NullPointerException("estimator");
        }
        this.msgSizeEstimator = messageSizeEstimator;
        return this;
    }

    private ChannelConfig setPinEventExecutorPerGroup(boolean bl) {
        this.pinEventExecutor = bl;
        return this;
    }

    private boolean getPinEventExecutorPerGroup() {
        return this.pinEventExecutor;
    }
}

