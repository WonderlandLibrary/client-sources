/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractNioByteChannel
extends AbstractNioChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
    private final Runnable flushTask = new Runnable(this){
        final AbstractNioByteChannel this$0;
        {
            this.this$0 = abstractNioByteChannel;
        }

        @Override
        public void run() {
            ((AbstractNioChannel.AbstractNioUnsafe)this.this$0.unsafe()).flush0();
        }
    };
    private boolean inputClosedSeenErrorOnRead;

    protected AbstractNioByteChannel(Channel channel, SelectableChannel selectableChannel) {
        super(channel, selectableChannel, 1);
    }

    protected abstract ChannelFuture shutdownInput();

    protected boolean isInputShutdown0() {
        return true;
    }

    @Override
    protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe(this);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    final boolean shouldBreakReadReady(ChannelConfig channelConfig) {
        return this.isInputShutdown0() && (this.inputClosedSeenErrorOnRead || !AbstractNioByteChannel.isAllowHalfClosure(channelConfig));
    }

    private static boolean isAllowHalfClosure(ChannelConfig channelConfig) {
        return channelConfig instanceof SocketChannelConfig && ((SocketChannelConfig)channelConfig).isAllowHalfClosure();
    }

    protected final int doWrite0(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object = channelOutboundBuffer.current();
        if (object == null) {
            return 1;
        }
        return this.doWriteInternal(channelOutboundBuffer, channelOutboundBuffer.current());
    }

    private int doWriteInternal(ChannelOutboundBuffer channelOutboundBuffer, Object object) throws Exception {
        if (object instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)object;
            if (!byteBuf.isReadable()) {
                channelOutboundBuffer.remove();
                return 1;
            }
            int n = this.doWriteBytes(byteBuf);
            if (n > 0) {
                channelOutboundBuffer.progress(n);
                if (!byteBuf.isReadable()) {
                    channelOutboundBuffer.remove();
                }
                return 0;
            }
        } else if (object instanceof FileRegion) {
            FileRegion fileRegion = (FileRegion)object;
            if (fileRegion.transferred() >= fileRegion.count()) {
                channelOutboundBuffer.remove();
                return 1;
            }
            long l = this.doWriteFileRegion(fileRegion);
            if (l > 0L) {
                channelOutboundBuffer.progress(l);
                if (fileRegion.transferred() >= fileRegion.count()) {
                    channelOutboundBuffer.remove();
                }
                return 0;
            }
        } else {
            throw new Error();
        }
        return 0;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object;
        int n = this.config().getWriteSpinCount();
        do {
            if ((object = channelOutboundBuffer.current()) != null) continue;
            this.clearOpWrite();
            return;
        } while ((n -= this.doWriteInternal(channelOutboundBuffer, object)) > 0);
        this.incompleteWrite(n < 0);
    }

    @Override
    protected final Object filterOutboundMessage(Object object) {
        if (object instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)object;
            if (byteBuf.isDirect()) {
                return object;
            }
            return this.newDirectBuffer(byteBuf);
        }
        if (object instanceof FileRegion) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    protected final void incompleteWrite(boolean bl) {
        if (bl) {
            this.setOpWrite();
        } else {
            this.clearOpWrite();
            this.eventLoop().execute(this.flushTask);
        }
    }

    protected abstract long doWriteFileRegion(FileRegion var1) throws Exception;

    protected abstract int doReadBytes(ByteBuf var1) throws Exception;

    protected abstract int doWriteBytes(ByteBuf var1) throws Exception;

    protected final void setOpWrite() {
        SelectionKey selectionKey = this.selectionKey();
        if (!selectionKey.isValid()) {
            return;
        }
        int n = selectionKey.interestOps();
        if ((n & 4) == 0) {
            selectionKey.interestOps(n | 4);
        }
    }

    protected final void clearOpWrite() {
        SelectionKey selectionKey = this.selectionKey();
        if (!selectionKey.isValid()) {
            return;
        }
        int n = selectionKey.interestOps();
        if ((n & 4) != 0) {
            selectionKey.interestOps(n & 0xFFFFFFFB);
        }
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    static boolean access$000(ChannelConfig channelConfig) {
        return AbstractNioByteChannel.isAllowHalfClosure(channelConfig);
    }

    static boolean access$102(AbstractNioByteChannel abstractNioByteChannel, boolean bl) {
        abstractNioByteChannel.inputClosedSeenErrorOnRead = bl;
        return abstractNioByteChannel.inputClosedSeenErrorOnRead;
    }

    protected class NioByteUnsafe
    extends AbstractNioChannel.AbstractNioUnsafe {
        final AbstractNioByteChannel this$0;

        protected NioByteUnsafe(AbstractNioByteChannel abstractNioByteChannel) {
            this.this$0 = abstractNioByteChannel;
            super(abstractNioByteChannel);
        }

        private void closeOnRead(ChannelPipeline channelPipeline) {
            if (!this.this$0.isInputShutdown0()) {
                if (AbstractNioByteChannel.access$000(this.this$0.config())) {
                    this.this$0.shutdownInput();
                    channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                } else {
                    this.close(this.voidPromise());
                }
            } else {
                AbstractNioByteChannel.access$102(this.this$0, true);
                channelPipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            }
        }

        private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable throwable, boolean bl, RecvByteBufAllocator.Handle handle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.this$0.readPending = false;
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
        public final void read() {
            ChannelConfig channelConfig = this.this$0.config();
            if (this.this$0.shouldBreakReadReady(channelConfig)) {
                this.this$0.clearReadPending();
                return;
            }
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            ByteBufAllocator byteBufAllocator = channelConfig.getAllocator();
            RecvByteBufAllocator.Handle handle = this.recvBufAllocHandle();
            handle.reset(channelConfig);
            ByteBuf byteBuf = null;
            boolean bl = false;
            try {
                do {
                    byteBuf = handle.allocate(byteBufAllocator);
                    handle.lastBytesRead(this.this$0.doReadBytes(byteBuf));
                    if (handle.lastBytesRead() <= 0) {
                        byteBuf.release();
                        byteBuf = null;
                        boolean bl2 = bl = handle.lastBytesRead() < 0;
                        if (!bl) break;
                        this.this$0.readPending = false;
                        break;
                    }
                    handle.incMessagesRead(1);
                    this.this$0.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                    byteBuf = null;
                } while (handle.continueReading());
                handle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (bl) {
                    this.closeOnRead(channelPipeline);
                }
            } catch (Throwable throwable) {
                this.handleReadException(channelPipeline, byteBuf, throwable, bl, handle);
            } finally {
                if (!this.this$0.readPending && !channelConfig.isAutoRead()) {
                    this.removeReadOp();
                }
            }
        }
    }
}

