/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

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
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.EpollRecvByteAllocatorStreamingHandle;
import io.netty.channel.epoll.LinuxSocket;
import io.netty.channel.epoll.Native;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.SocketWritableByteChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import java.util.Queue;
import java.util.concurrent.Executor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractEpollStreamChannel
extends AbstractEpollChannel
implements DuplexChannel {
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private static final InternalLogger logger;
    private static final ClosedChannelException CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException SPLICE_TO_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION;
    private final Runnable flushTask = new Runnable(this){
        final AbstractEpollStreamChannel this$0;
        {
            this.this$0 = abstractEpollStreamChannel;
        }

        @Override
        public void run() {
            ((AbstractEpollChannel.AbstractEpollUnsafe)this.this$0.unsafe()).flush0();
        }
    };
    private Queue<SpliceInTask> spliceQueue;
    private FileDescriptor pipeIn;
    private FileDescriptor pipeOut;
    private WritableByteChannel byteChannel;
    static final boolean $assertionsDisabled;

    protected AbstractEpollStreamChannel(Channel channel, int n) {
        this(channel, new LinuxSocket(n));
    }

    protected AbstractEpollStreamChannel(int n) {
        this(new LinuxSocket(n));
    }

    AbstractEpollStreamChannel(LinuxSocket linuxSocket) {
        this(linuxSocket, AbstractEpollStreamChannel.isSoErrorZero(linuxSocket));
    }

    AbstractEpollStreamChannel(Channel channel, LinuxSocket linuxSocket) {
        super(channel, linuxSocket, Native.EPOLLIN, true);
        this.flags |= Native.EPOLLRDHUP;
    }

    AbstractEpollStreamChannel(Channel channel, LinuxSocket linuxSocket, SocketAddress socketAddress) {
        super(channel, linuxSocket, Native.EPOLLIN, socketAddress);
        this.flags |= Native.EPOLLRDHUP;
    }

    protected AbstractEpollStreamChannel(LinuxSocket linuxSocket, boolean bl) {
        super(null, linuxSocket, Native.EPOLLIN, bl);
        this.flags |= Native.EPOLLRDHUP;
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollStreamUnsafe(this);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel abstractEpollStreamChannel, int n) {
        return this.spliceTo(abstractEpollStreamChannel, n, this.newPromise());
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel abstractEpollStreamChannel, int n, ChannelPromise channelPromise) {
        if (abstractEpollStreamChannel.eventLoop() != this.eventLoop()) {
            throw new IllegalArgumentException("EventLoops are not the same.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("len: " + n + " (expected: >= 0)");
        }
        if (abstractEpollStreamChannel.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED || this.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
            throw new IllegalStateException("spliceTo() supported only when using " + (Object)((Object)EpollMode.LEVEL_TRIGGERED));
        }
        ObjectUtil.checkNotNull(channelPromise, "promise");
        if (!this.isOpen()) {
            channelPromise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
        } else {
            this.addToSpliceQueue(new SpliceInChannelTask(this, abstractEpollStreamChannel, n, channelPromise));
            this.failSpliceIfClosed(channelPromise);
        }
        return channelPromise;
    }

    public final ChannelFuture spliceTo(FileDescriptor fileDescriptor, int n, int n2) {
        return this.spliceTo(fileDescriptor, n, n2, this.newPromise());
    }

    public final ChannelFuture spliceTo(FileDescriptor fileDescriptor, int n, int n2, ChannelPromise channelPromise) {
        if (n2 < 0) {
            throw new IllegalArgumentException("len: " + n2 + " (expected: >= 0)");
        }
        if (n < 0) {
            throw new IllegalArgumentException("offset must be >= 0 but was " + n);
        }
        if (this.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
            throw new IllegalStateException("spliceTo() supported only when using " + (Object)((Object)EpollMode.LEVEL_TRIGGERED));
        }
        ObjectUtil.checkNotNull(channelPromise, "promise");
        if (!this.isOpen()) {
            channelPromise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
        } else {
            this.addToSpliceQueue(new SpliceFdTask(this, fileDescriptor, n, n2, channelPromise));
            this.failSpliceIfClosed(channelPromise);
        }
        return channelPromise;
    }

    private void failSpliceIfClosed(ChannelPromise channelPromise) {
        if (!this.isOpen() && channelPromise.tryFailure(FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION)) {
            this.eventLoop().execute(new Runnable(this){
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
                }

                @Override
                public void run() {
                    AbstractEpollStreamChannel.access$000(this.this$0);
                }
            });
        }
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
            this.byteChannel = new EpollSocketWritableByteChannel(this);
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
                this.clearFlag(Native.EPOLLOUT);
                return;
            }
            n -= this.doWriteSingle(channelOutboundBuffer);
        } while (n > 0);
        if (n == 0) {
            this.clearFlag(Native.EPOLLOUT);
            this.eventLoop().execute(this.flushTask);
        } else {
            this.setFlag(Native.EPOLLOUT);
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
        if (object instanceof SpliceOutTask) {
            if (!((SpliceOutTask)object).spliceOut()) {
                return 0;
            }
            channelOutboundBuffer.remove();
            return 0;
        }
        throw new Error();
    }

    private int doWriteMultiple(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        long l = this.config().getMaxBytesPerGatheringWrite();
        if (PlatformDependent.hasUnsafe()) {
            IovArray iovArray = ((EpollEventLoop)this.eventLoop()).cleanArray();
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
        if (object instanceof FileRegion || object instanceof SpliceOutTask) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    @Override
    protected final void doShutdownOutput() throws Exception {
        this.socket.shutdown(false, false);
    }

    private void shutdownInput0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdown(true, true);
            channelPromise.setSuccess();
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
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
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
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
        Executor executor = ((EpollStreamUnsafe)this.unsafe()).prepareToClose();
        if (executor != null) {
            executor.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractEpollStreamChannel.access$100(this.this$0, this.val$promise);
                }
            });
        } else {
            EventLoop eventLoop = this.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.shutdownInput0(channelPromise);
            } else {
                eventLoop.execute(new Runnable(this, channelPromise){
                    final ChannelPromise val$promise;
                    final AbstractEpollStreamChannel this$0;
                    {
                        this.this$0 = abstractEpollStreamChannel;
                        this.val$promise = channelPromise;
                    }

                    @Override
                    public void run() {
                        AbstractEpollStreamChannel.access$100(this.this$0, this.val$promise);
                    }
                });
            }
        }
        return channelPromise;
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
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    AbstractEpollStreamChannel.access$200(this.this$0, channelFuture, this.val$promise);
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
            AbstractEpollStreamChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
        } else {
            channelFuture2.addListener(new ChannelFutureListener(this, channelFuture, channelPromise){
                final ChannelFuture val$shutdownOutputFuture;
                final ChannelPromise val$promise;
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
                    this.val$shutdownOutputFuture = channelFuture;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    AbstractEpollStreamChannel.access$300(this.val$shutdownOutputFuture, channelFuture, this.val$promise);
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
    protected void doClose() throws Exception {
        try {
            super.doClose();
        } finally {
            AbstractEpollStreamChannel.safeClosePipe(this.pipeIn);
            AbstractEpollStreamChannel.safeClosePipe(this.pipeOut);
            this.clearSpliceQueue();
        }
    }

    private void clearSpliceQueue() {
        SpliceInTask spliceInTask;
        if (this.spliceQueue == null) {
            return;
        }
        while ((spliceInTask = this.spliceQueue.poll()) != null) {
            spliceInTask.promise.tryFailure(CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION);
        }
    }

    private static void safeClosePipe(FileDescriptor fileDescriptor) {
        block3: {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (IOException iOException) {
                    if (!logger.isWarnEnabled()) break block3;
                    logger.warn("Error while closing a pipe", iOException);
                }
            }
        }
    }

    private void addToSpliceQueue(SpliceInTask spliceInTask) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.addToSpliceQueue0(spliceInTask);
        } else {
            eventLoop.execute(new Runnable(this, spliceInTask){
                final SpliceInTask val$task;
                final AbstractEpollStreamChannel this$0;
                {
                    this.this$0 = abstractEpollStreamChannel;
                    this.val$task = spliceInTask;
                }

                @Override
                public void run() {
                    AbstractEpollStreamChannel.access$500(this.this$0, this.val$task);
                }
            });
        }
    }

    private void addToSpliceQueue0(SpliceInTask spliceInTask) {
        if (this.spliceQueue == null) {
            this.spliceQueue = PlatformDependent.newMpscQueue();
        }
        this.spliceQueue.add(spliceInTask);
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

    static void access$000(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        abstractEpollStreamChannel.clearSpliceQueue();
    }

    static void access$100(AbstractEpollStreamChannel abstractEpollStreamChannel, ChannelPromise channelPromise) {
        abstractEpollStreamChannel.shutdownInput0(channelPromise);
    }

    static void access$200(AbstractEpollStreamChannel abstractEpollStreamChannel, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        abstractEpollStreamChannel.shutdownOutputDone(channelFuture, channelPromise);
    }

    static void access$300(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        AbstractEpollStreamChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
    }

    static Queue access$400(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        return abstractEpollStreamChannel.spliceQueue;
    }

    static void access$500(AbstractEpollStreamChannel abstractEpollStreamChannel, SpliceInTask spliceInTask) {
        abstractEpollStreamChannel.addToSpliceQueue0(spliceInTask);
    }

    static FileDescriptor access$600(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        return abstractEpollStreamChannel.pipeOut;
    }

    static FileDescriptor access$702(AbstractEpollStreamChannel abstractEpollStreamChannel, FileDescriptor fileDescriptor) {
        abstractEpollStreamChannel.pipeIn = fileDescriptor;
        return abstractEpollStreamChannel.pipeIn;
    }

    static FileDescriptor access$602(AbstractEpollStreamChannel abstractEpollStreamChannel, FileDescriptor fileDescriptor) {
        abstractEpollStreamChannel.pipeOut = fileDescriptor;
        return abstractEpollStreamChannel.pipeOut;
    }

    static FileDescriptor access$700(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        return abstractEpollStreamChannel.pipeIn;
    }

    static void access$800(FileDescriptor fileDescriptor) {
        AbstractEpollStreamChannel.safeClosePipe(fileDescriptor);
    }

    static {
        $assertionsDisabled = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        METADATA = new ChannelMetadata(false, 16);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
        logger = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
        CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "clearSpliceQueue()");
        SPLICE_TO_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "spliceTo(...)");
        FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "failSpliceIfClosed(...)");
    }

    private final class EpollSocketWritableByteChannel
    extends SocketWritableByteChannel {
        final AbstractEpollStreamChannel this$0;

        EpollSocketWritableByteChannel(AbstractEpollStreamChannel abstractEpollStreamChannel) {
            this.this$0 = abstractEpollStreamChannel;
            super(abstractEpollStreamChannel.socket);
        }

        @Override
        protected ByteBufAllocator alloc() {
            return this.this$0.alloc();
        }
    }

    private final class SpliceFdTask
    extends SpliceInTask {
        private final FileDescriptor fd;
        private final ChannelPromise promise;
        private final int offset;
        static final boolean $assertionsDisabled = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        final AbstractEpollStreamChannel this$0;

        SpliceFdTask(AbstractEpollStreamChannel abstractEpollStreamChannel, FileDescriptor fileDescriptor, int n, int n2, ChannelPromise channelPromise) {
            this.this$0 = abstractEpollStreamChannel;
            super(abstractEpollStreamChannel, n2, channelPromise);
            this.fd = fileDescriptor;
            this.promise = channelPromise;
            this.offset = n;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            if (this.len == 0) {
                this.promise.setSuccess();
                return false;
            }
            try {
                FileDescriptor[] fileDescriptorArray = FileDescriptor.pipe();
                FileDescriptor fileDescriptor2 = fileDescriptorArray[0];
                FileDescriptor fileDescriptor = fileDescriptorArray[5];
                try {
                    int n;
                    int n2 = this.spliceIn(fileDescriptor, handle);
                    if (n2 > 0) {
                        if (this.len != Integer.MAX_VALUE) {
                            this.len -= n2;
                        }
                        while ((n2 -= (n = Native.splice(fileDescriptor2.intValue(), -1L, this.fd.intValue(), this.offset, n2))) > 0) {
                        }
                        if (this.len == 0) {
                            this.promise.setSuccess();
                            n = 1;
                            return n != 0;
                        }
                    }
                    n = 0;
                    return n != 0;
                } finally {
                    AbstractEpollStreamChannel.access$800(fileDescriptor2);
                    AbstractEpollStreamChannel.access$800(fileDescriptor);
                }
            } catch (Throwable throwable2) {
                this.promise.setFailure(throwable2);
                return false;
            }
        }
    }

    private final class SpliceOutTask {
        private final AbstractEpollStreamChannel ch;
        private final boolean autoRead;
        private int len;
        static final boolean $assertionsDisabled = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        final AbstractEpollStreamChannel this$0;

        SpliceOutTask(AbstractEpollStreamChannel abstractEpollStreamChannel, AbstractEpollStreamChannel abstractEpollStreamChannel2, int n, boolean bl) {
            this.this$0 = abstractEpollStreamChannel;
            this.ch = abstractEpollStreamChannel2;
            this.len = n;
            this.autoRead = bl;
        }

        public boolean spliceOut() throws Exception {
            if (!$assertionsDisabled && !this.ch.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            try {
                int n = Native.splice(AbstractEpollStreamChannel.access$700(this.ch).intValue(), -1L, this.ch.socket.intValue(), -1L, this.len);
                this.len -= n;
                if (this.len == 0) {
                    if (this.autoRead) {
                        this.this$0.config().setAutoRead(false);
                    }
                    return true;
                }
                return false;
            } catch (IOException iOException) {
                if (this.autoRead) {
                    this.this$0.config().setAutoRead(false);
                }
                throw iOException;
            }
        }
    }

    private final class SpliceInChannelTask
    extends SpliceInTask
    implements ChannelFutureListener {
        private final AbstractEpollStreamChannel ch;
        static final boolean $assertionsDisabled = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        final AbstractEpollStreamChannel this$0;

        SpliceInChannelTask(AbstractEpollStreamChannel abstractEpollStreamChannel, AbstractEpollStreamChannel abstractEpollStreamChannel2, int n, ChannelPromise channelPromise) {
            this.this$0 = abstractEpollStreamChannel;
            super(abstractEpollStreamChannel, n, channelPromise);
            this.ch = abstractEpollStreamChannel2;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                this.promise.setFailure(channelFuture.cause());
            }
        }

        @Override
        public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
            if (!$assertionsDisabled && !this.ch.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            if (this.len == 0) {
                this.promise.setSuccess();
                return false;
            }
            try {
                int n;
                FileDescriptor fileDescriptor = AbstractEpollStreamChannel.access$600(this.ch);
                if (fileDescriptor == null) {
                    FileDescriptor[] fileDescriptorArray = FileDescriptor.pipe();
                    AbstractEpollStreamChannel.access$702(this.ch, fileDescriptorArray[0]);
                    fileDescriptor = AbstractEpollStreamChannel.access$602(this.ch, fileDescriptorArray[5]);
                }
                if ((n = this.spliceIn(fileDescriptor, handle)) > 0) {
                    if (this.len != Integer.MAX_VALUE) {
                        this.len -= n;
                    }
                    ChannelPromise channelPromise = this.len == 0 ? this.promise : this.ch.newPromise().addListener(this);
                    boolean bl = this.this$0.config().isAutoRead();
                    this.ch.unsafe().write(new SpliceOutTask(this.this$0, this.ch, n, bl), channelPromise);
                    this.ch.unsafe().flush();
                    if (bl && !channelPromise.isDone()) {
                        this.this$0.config().setAutoRead(true);
                    }
                }
                return this.len == 0;
            } catch (Throwable throwable) {
                this.promise.setFailure(throwable);
                return false;
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    }

    protected abstract class SpliceInTask {
        final ChannelPromise promise;
        int len;
        final AbstractEpollStreamChannel this$0;

        protected SpliceInTask(AbstractEpollStreamChannel abstractEpollStreamChannel, int n, ChannelPromise channelPromise) {
            this.this$0 = abstractEpollStreamChannel;
            this.promise = channelPromise;
            this.len = n;
        }

        abstract boolean spliceIn(RecvByteBufAllocator.Handle var1);

        protected final int spliceIn(FileDescriptor fileDescriptor, RecvByteBufAllocator.Handle handle) throws IOException {
            int n;
            int n2 = Math.min(handle.guess(), this.len);
            int n3 = 0;
            while ((n = Native.splice(this.this$0.socket.intValue(), -1L, fileDescriptor.intValue(), -1L, n2)) != 0) {
                n3 += n;
                n2 -= n;
            }
            return n3;
        }
    }

    class EpollStreamUnsafe
    extends AbstractEpollChannel.AbstractEpollUnsafe {
        final AbstractEpollStreamChannel this$0;

        EpollStreamUnsafe(AbstractEpollStreamChannel abstractEpollStreamChannel) {
            this.this$0 = abstractEpollStreamChannel;
            super(abstractEpollStreamChannel);
        }

        @Override
        protected Executor prepareToClose() {
            return super.prepareToClose();
        }

        private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable throwable, boolean bl, EpollRecvByteAllocatorHandle epollRecvByteAllocatorHandle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            epollRecvByteAllocatorHandle.readComplete();
            channelPipeline.fireChannelReadComplete();
            channelPipeline.fireExceptionCaught(throwable);
            if (bl || throwable instanceof IOException) {
                this.shutdownInput(true);
            }
        }

        @Override
        EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle extendedHandle) {
            return new EpollRecvByteAllocatorStreamingHandle(extendedHandle);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void epollInReady() {
            EpollChannelConfig epollChannelConfig = this.this$0.config();
            if (this.this$0.shouldBreakEpollInReady(epollChannelConfig)) {
                this.clearEpollIn0();
                return;
            }
            EpollRecvByteAllocatorHandle epollRecvByteAllocatorHandle = this.recvBufAllocHandle();
            epollRecvByteAllocatorHandle.edgeTriggered(this.this$0.isFlagSet(Native.EPOLLET));
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            ByteBufAllocator byteBufAllocator = epollChannelConfig.getAllocator();
            epollRecvByteAllocatorHandle.reset(epollChannelConfig);
            this.epollInBefore();
            ByteBuf byteBuf = null;
            boolean bl = false;
            try {
                do {
                    SpliceInTask spliceInTask;
                    if (AbstractEpollStreamChannel.access$400(this.this$0) != null && (spliceInTask = (SpliceInTask)AbstractEpollStreamChannel.access$400(this.this$0).peek()) != null) {
                        if (!spliceInTask.spliceIn(epollRecvByteAllocatorHandle)) break;
                        if (!this.this$0.isActive()) continue;
                        AbstractEpollStreamChannel.access$400(this.this$0).remove();
                        continue;
                    }
                    byteBuf = epollRecvByteAllocatorHandle.allocate(byteBufAllocator);
                    epollRecvByteAllocatorHandle.lastBytesRead(this.this$0.doReadBytes(byteBuf));
                    if (epollRecvByteAllocatorHandle.lastBytesRead() <= 0) {
                        byteBuf.release();
                        byteBuf = null;
                        boolean bl2 = bl = epollRecvByteAllocatorHandle.lastBytesRead() < 0;
                        if (!bl) break;
                        this.readPending = false;
                        break;
                    }
                    epollRecvByteAllocatorHandle.incMessagesRead(1);
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                    byteBuf = null;
                    if (this.this$0.shouldBreakEpollInReady(epollChannelConfig)) break;
                } while (epollRecvByteAllocatorHandle.continueReading());
                epollRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (bl) {
                    this.shutdownInput(true);
                }
            } catch (Throwable throwable) {
                this.handleReadException(channelPipeline, byteBuf, throwable, bl, epollRecvByteAllocatorHandle);
            } finally {
                this.epollInFinally(epollChannelConfig);
            }
        }
    }
}

