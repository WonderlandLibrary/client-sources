/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioChannel;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

public abstract class AbstractOioByteChannel
extends AbstractOioChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';

    protected AbstractOioByteChannel(Channel channel) {
        super(channel);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected abstract boolean isInputShutdown();

    protected abstract ChannelFuture shutdownInput();

    private void closeOnRead(ChannelPipeline channelPipeline) {
        if (this.isOpen()) {
            if (Boolean.TRUE.equals(this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                this.shutdownInput();
                channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            } else {
                this.unsafe().close(this.unsafe().voidPromise());
            }
            channelPipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
        }
    }

    private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable throwable, boolean bl, RecvByteBufAllocator.Handle handle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.readPending = false;
                channelPipeline.fireChannelRead(byteBuf);
            } else {
                byteBuf.release();
            }
        }
        handle.readComplete();
        channelPipeline.fireChannelReadComplete();
        channelPipeline.fireExceptionCaught(throwable);
        if (bl || throwable instanceof IOException) {
            this.closeOnRead(channelPipeline);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doRead() {
        ChannelConfig channelConfig = this.config();
        if (this.isInputShutdown() || !this.readPending) {
            return;
        }
        this.readPending = false;
        ChannelPipeline channelPipeline = this.pipeline();
        ByteBufAllocator byteBufAllocator = channelConfig.getAllocator();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.reset(channelConfig);
        ByteBuf byteBuf = null;
        boolean bl = false;
        boolean bl2 = false;
        try {
            byteBuf = handle.allocate(byteBufAllocator);
            do {
                int n;
                handle.lastBytesRead(this.doReadBytes(byteBuf));
                if (handle.lastBytesRead() <= 0) {
                    if (byteBuf.isReadable()) break;
                    byteBuf.release();
                    byteBuf = null;
                    boolean bl3 = bl = handle.lastBytesRead() < 0;
                    if (!bl) break;
                    this.readPending = false;
                    break;
                }
                bl2 = true;
                int n2 = this.available();
                if (n2 <= 0) break;
                if (byteBuf.isWritable()) continue;
                int n3 = byteBuf.capacity();
                if (n3 == (n = byteBuf.maxCapacity())) {
                    handle.incMessagesRead(1);
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                    byteBuf = handle.allocate(byteBufAllocator);
                    continue;
                }
                int n4 = byteBuf.writerIndex();
                if (n4 + n2 > n) {
                    byteBuf.capacity(n);
                    continue;
                }
                byteBuf.ensureWritable(n2);
            } while (handle.continueReading());
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
                byteBuf = null;
            }
            if (bl2) {
                handle.readComplete();
                channelPipeline.fireChannelReadComplete();
            }
            if (bl) {
                this.closeOnRead(channelPipeline);
            }
        } catch (Throwable throwable) {
            this.handleReadException(channelPipeline, byteBuf, throwable, bl, handle);
        } finally {
            if (this.readPending || channelConfig.isAutoRead() || !bl2 && this.isActive()) {
                this.read();
            }
        }
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object;
        while ((object = channelOutboundBuffer.current()) != null) {
            ReferenceCounted referenceCounted;
            if (object instanceof ByteBuf) {
                referenceCounted = (ByteBuf)object;
                int n = ((ByteBuf)referenceCounted).readableBytes();
                while (n > 0) {
                    this.doWriteBytes((ByteBuf)referenceCounted);
                    int n2 = ((ByteBuf)referenceCounted).readableBytes();
                    channelOutboundBuffer.progress(n - n2);
                    n = n2;
                }
                channelOutboundBuffer.remove();
                continue;
            }
            if (object instanceof FileRegion) {
                referenceCounted = (FileRegion)object;
                long l = referenceCounted.transferred();
                this.doWriteFileRegion((FileRegion)referenceCounted);
                channelOutboundBuffer.progress(referenceCounted.transferred() - l);
                channelOutboundBuffer.remove();
                continue;
            }
            channelOutboundBuffer.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object)));
        }
    }

    @Override
    protected final Object filterOutboundMessage(Object object) throws Exception {
        if (object instanceof ByteBuf || object instanceof FileRegion) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    protected abstract int available();

    protected abstract int doReadBytes(ByteBuf var1) throws Exception;

    protected abstract void doWriteBytes(ByteBuf var1) throws Exception;

    protected abstract void doWriteFileRegion(FileRegion var1) throws Exception;
}

