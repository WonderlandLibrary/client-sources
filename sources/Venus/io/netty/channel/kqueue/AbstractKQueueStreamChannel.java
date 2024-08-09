/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoop;
import io.netty.channel.FileRegion;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueRecvByteAllocatorHandle;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.SocketWritableByteChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractKQueueStreamChannel
extends AbstractKQueueChannel
implements DuplexChannel {
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private WritableByteChannel byteChannel;
    private final Runnable flushTask = new Runnable(this){
        final AbstractKQueueStreamChannel this$0;
        {
            this.this$0 = abstractKQueueStreamChannel;
        }

        @Override
        public void run() {
            ((AbstractKQueueChannel.AbstractKQueueUnsafe)this.this$0.unsafe()).flush0();
        }
    };
    static final boolean $assertionsDisabled;

    AbstractKQueueStreamChannel(Channel channel, BsdSocket bsdSocket, boolean bl) {
        super(channel, bsdSocket, bl);
    }

    AbstractKQueueStreamChannel(Channel channel, BsdSocket bsdSocket, SocketAddress socketAddress) {
        super(channel, bsdSocket, socketAddress);
    }

    AbstractKQueueStreamChannel(BsdSocket bsdSocket) {
        this(null, bsdSocket, AbstractKQueueStreamChannel.isSoErrorZero(bsdSocket));
    }

    @Override
    protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
        return new KQueueStreamUnsafe(this);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    private int writeBytes(ChannelOutboundBuffer channelOutboundBuffer, ByteBuf byteBuf) throws Exception {
        int n = byteBuf.readableBytes();
        if (n == 0) {
            channelOutboundBuffer.remove();
            return 1;
        }
        if (byteBuf.hasMemoryAddress() || byteBuf.nioBufferCount() == 1) {
            return this.doWriteBytes(channelOutboundBuffer, byteBuf);
        }
        ByteBuffer[] byteBufferArray = byteBuf.nioBuffers();
        return this.writeBytesMultiple(channelOutboundBuffer, byteBufferArray, byteBufferArray.length, n, this.config().getMaxBytesPerGatheringWrite());
    }

    private void adjustMaxBytesPerGatheringWrite(long l, long l2, long l3) {
        if (l == l2) {
            if (l << 1 > l3) {
                this.config().setMaxBytesPerGatheringWrite(l << 1);
            }
        } else if (l > 4096L && l2 < l >>> 1) {
            this.config().setMaxBytesPerGatheringWrite(l >>> 1);
        }
    }

    private int writeBytesMultiple(ChannelOutboundBuffer channelOutboundBuffer, IovArray iovArray) throws IOException {
        long l = iovArray.size();
        if (!$assertionsDisabled && l == 0L) {
            throw new AssertionError();
        }
        int n = iovArray.count();
        if (!$assertionsDisabled && n == 0) {
            throw new AssertionError();
        }
        long l2 = this.socket.writevAddresses(iovArray.memoryAddress(0), n);
        if (l2 > 0L) {
            this.adjustMaxBytesPerGatheringWrite(l, l2, iovArray.maxBytes());
            channelOutboundBuffer.removeBytes(l2);
            return 0;
        }
        return 0;
    }

    private int writeBytesMultiple(ChannelOutboundBuffer channelOutboundBuffer, ByteBuffer[] byteBufferArray, int n, long l, long l2) throws IOException {
        long l3;
        if (!$assertionsDisabled && l == 0L) {
            throw new AssertionError();
        }
        if (l > l2) {
            l = l2;
        }
        if ((l3 = this.socket.writev(byteBufferArray, 0, n, l)) > 0L) {
            this.adjustMaxBytesPerGatheringWrite(l, l3, l2);
            channelOutboundBuffer.removeBytes(l3);
            return 0;
        }
        return 0;
    }

    private int writeDefaultFileRegion(ChannelOutboundBuffer channelOutboundBuffer, DefaultFileRegion defaultFileRegion) throws Exception {
        long l = defaultFileRegion.count();
        if (defaultFileRegion.transferred() >= l) {
            channelOutboundBuffer.remove();
            return 1;
        }
        long l2 = defaultFileRegion.transferred();
        long l3 = this.socket.sendFile(defaultFileRegion, defaultFileRegion.position(), l2, l - l2);
        if (l3 > 0L) {
            channelOutboundBuffer.progress(l3);
            if (defaultFileRegion.transferred() >= l) {
                channelOutboundBuffer.remove();
            }
            return 0;
        }
        return 0;
    }

    private int writeFileRegion(ChannelOutboundBuffer channelOutboundBuffer, FileRegion fileRegion) throws Exception {
        long l;
        if (fileRegion.transferred() >= fileRegion.count()) {
            channelOutboundBuffer.remove();
            return 1;
        }
        if (this.byteChannel == null) {
            this.byteChannel = new KQueueSocketWritableByteChannel(this);
        }
        if ((l = fileRegion.transferTo(this.byteChannel, fileRegion.transferred())) > 0L) {
            channelOutboundBuffer.progress(l);
            if (fileRegion.transferred() >= fileRegion.count()) {
                channelOutboundBuffer.remove();
            }
            return 0;
        }
        return 0;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        int n = this.config().getWriteSpinCount();
        do {
            int n2;
            if ((n2 = channelOutboundBuffer.size()) > 1 && channelOutboundBuffer.current() instanceof ByteBuf) {
                n -= this.doWriteMultiple(channelOutboundBuffer);
                continue;
            }
            if (n2 == 0) {
                this.writeFilter(true);
                return;
            }
            n -= this.doWriteSingle(channelOutboundBuffer);
        } while (n > 0);
        if (n == 0) {
            this.writeFilter(true);
            this.eventLoop().execute(this.flushTask);
        } else {
            this.writeFilter(false);
        }
    }

    protected int doWriteSingle(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object = channelOutboundBuffer.current();
        if (object instanceof ByteBuf) {
            return this.writeBytes(channelOutboundBuffer, (ByteBuf)object);
        }
        if (object instanceof DefaultFileRegion) {
            return this.writeDefaultFileRegion(channelOutboundBuffer, (DefaultFileRegion)object);
        }
        if (object instanceof FileRegion) {
            return this.writeFileRegion(channelOutboundBuffer, (FileRegion)object);
        }
        throw new Error();
    }

    private int doWriteMultiple(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        long l = this.config().getMaxBytesPerGatheringWrite();
        if (PlatformDependent.hasUnsafe()) {
            IovArray iovArray = ((KQueueEventLoop)this.eventLoop()).cleanArray();
            iovArray.maxBytes(l);
            channelOutboundBuffer.forEachFlushedMessage(iovArray);
            if (iovArray.count() >= 1) {
                return this.writeBytesMultiple(channelOutboundBuffer, iovArray);
            }
        } else {
            ByteBuffer[] byteBufferArray = channelOutboundBuffer.nioBuffers();
            int n = channelOutboundBuffer.nioBufferCount();
            if (n >= 1) {
                return this.writeBytesMultiple(channelOutboundBuffer, byteBufferArray, n, channelOutboundBuffer.nioBufferSize(), l);
            }
        }
        channelOutboundBuffer.removeBytes(0L);
        return 1;
    }

    @Override
    protected Object filterOutboundMessage(Object object) {
        if (object instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)object;
            return UnixChannelUtil.isBufferCopyNeededForWrite(byteBuf) ? this.newDirectBuffer(byteBuf) : byteBuf;
        }
        if (object instanceof FileRegion) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    @Override
    protected final void doShutdownOutput() throws Exception {
        this.socket.shutdown(false, false);
    }

    @Override
    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown();
    }

    @Override
    public boolean isInputShutdown() {
        return this.socket.isInputShutdown();
    }

    @Override
    public boolean isShutdown() {
        return this.socket.isShutdown();
    }

    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownOutput(ChannelPromise channelPromise) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            ((AbstractChannel.AbstractUnsafe)this.unsafe()).shutdownOutput(channelPromise);
        } else {
            eventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final AbstractKQueueStreamChannel this$0;
                {
                    this.this$0 = abstractKQueueStreamChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    ((AbstractChannel.AbstractUnsafe)this.this$0.unsafe()).shutdownOutput(this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture shutdownInput() {
        return this.shutdownInput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownInput(ChannelPromise channelPromise) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.shutdownInput0(channelPromise);
        } else {
            eventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final AbstractKQueueStreamChannel this$0;
                {
                    this.this$0 = abstractKQueueStreamChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractKQueueStreamChannel.access$000(this.this$0, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    private void shutdownInput0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdown(true, true);
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
            return;
        }
        channelPromise.setSuccess();
    }

    @Override
    public ChannelFuture shutdown() {
        return this.shutdown(this.newPromise());
    }

    @Override
    public ChannelFuture shutdown(ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.shutdownOutput();
        if (channelFuture.isDone()) {
            this.shutdownOutputDone(channelFuture, channelPromise);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelPromise){
                final ChannelPromise val$promise;
                final AbstractKQueueStreamChannel this$0;
                {
                    this.this$0 = abstractKQueueStreamChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    AbstractKQueueStreamChannel.access$100(this.this$0, channelFuture, this.val$promise);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return channelPromise;
    }

    private void shutdownOutputDone(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        ChannelFuture channelFuture2 = this.shutdownInput();
        if (channelFuture2.isDone()) {
            AbstractKQueueStreamChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
        } else {
            channelFuture2.addListener(new ChannelFutureListener(this, channelFuture, channelPromise){
                final ChannelFuture val$shutdownOutputFuture;
                final ChannelPromise val$promise;
                final AbstractKQueueStreamChannel this$0;
                {
                    this.this$0 = abstractKQueueStreamChannel;
                    this.val$shutdownOutputFuture = channelFuture;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    AbstractKQueueStreamChannel.access$200(this.val$shutdownOutputFuture, channelFuture, this.val$promise);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
    }

    private static void shutdownDone(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        Throwable throwable = channelFuture.cause();
        Throwable throwable2 = channelFuture2.cause();
        if (throwable != null) {
            if (throwable2 != null) {
                logger.debug("Exception suppressed because a previous exception occurred.", throwable2);
            }
            channelPromise.setFailure(throwable);
        } else if (throwable2 != null) {
            channelPromise.setFailure(throwable2);
        } else {
            channelPromise.setSuccess();
        }
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    static void access$000(AbstractKQueueStreamChannel abstractKQueueStreamChannel, ChannelPromise channelPromise) {
        abstractKQueueStreamChannel.shutdownInput0(channelPromise);
    }

    static void access$100(AbstractKQueueStreamChannel abstractKQueueStreamChannel, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        abstractKQueueStreamChannel.shutdownOutputDone(channelFuture, channelPromise);
    }

    static void access$200(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        AbstractKQueueStreamChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
    }

    static {
        $assertionsDisabled = !AbstractKQueueStreamChannel.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(AbstractKQueueStreamChannel.class);
        METADATA = new ChannelMetadata(false, 16);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
    }

    private final class KQueueSocketWritableByteChannel
    extends SocketWritableByteChannel {
        final AbstractKQueueStreamChannel this$0;

        KQueueSocketWritableByteChannel(AbstractKQueueStreamChannel abstractKQueueStreamChannel) {
            this.this$0 = abstractKQueueStreamChannel;
            super(abstractKQueueStreamChannel.socket);
        }

        @Override
        protected ByteBufAllocator alloc() {
            return this.this$0.alloc();
        }
    }

    class KQueueStreamUnsafe
    extends AbstractKQueueChannel.AbstractKQueueUnsafe {
        final AbstractKQueueStreamChannel this$0;

        KQueueStreamUnsafe(AbstractKQueueStreamChannel abstractKQueueStreamChannel) {
            this.this$0 = abstractKQueueStreamChannel;
            super(abstractKQueueStreamChannel);
        }

        @Override
        protected Executor prepareToClose() {
            return super.prepareToClose();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void readReady(KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle) {
            KQueueChannelConfig kQueueChannelConfig = this.this$0.config();
            if (this.this$0.shouldBreakReadReady(kQueueChannelConfig)) {
                this.clearReadFilter0();
                return;
            }
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            ByteBufAllocator byteBufAllocator = kQueueChannelConfig.getAllocator();
            kQueueRecvByteAllocatorHandle.reset(kQueueChannelConfig);
            this.readReadyBefore();
            ByteBuf byteBuf = null;
            boolean bl = false;
            try {
                do {
                    byteBuf = kQueueRecvByteAllocatorHandle.allocate(byteBufAllocator);
                    kQueueRecvByteAllocatorHandle.lastBytesRead(this.this$0.doReadBytes(byteBuf));
                    if (kQueueRecvByteAllocatorHandle.lastBytesRead() <= 0) {
                        byteBuf.release();
                        byteBuf = null;
                        boolean bl2 = bl = kQueueRecvByteAllocatorHandle.lastBytesRead() < 0;
                        if (!bl) break;
                        this.readPending = false;
                        break;
                    }
                    kQueueRecvByteAllocatorHandle.incMessagesRead(1);
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                    byteBuf = null;
                } while (!this.this$0.shouldBreakReadReady(kQueueChannelConfig) && kQueueRecvByteAllocatorHandle.continueReading());
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (bl) {
                    this.shutdownInput(true);
                }
            } catch (Throwable throwable) {
                this.handleReadException(channelPipeline, byteBuf, throwable, bl, kQueueRecvByteAllocatorHandle);
            } finally {
                this.readReadyFinally(kQueueChannelConfig);
            }
        }

        private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable throwable, boolean bl, KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            if (!this.failConnectPromise(throwable)) {
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                channelPipeline.fireExceptionCaught(throwable);
                if (bl || throwable instanceof IOException) {
                    this.shutdownInput(true);
                }
            }
        }
    }
}

