/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
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
import io.netty.channel.epoll.IovArray;
import io.netty.channel.epoll.Native;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
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
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.WritableByteChannel;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractEpollStreamChannel
extends AbstractEpollChannel
implements DuplexChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "doClose()");
    private static final ClosedChannelException CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "clearSpliceQueue()");
    private static final ClosedChannelException SPLICE_TO_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "spliceTo(...)");
    private static final ClosedChannelException FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "failSpliceIfClosed(...)");
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    private Queue<SpliceInTask> spliceQueue;
    private FileDescriptor pipeIn;
    private FileDescriptor pipeOut;
    private WritableByteChannel byteChannel;

    @Deprecated
    protected AbstractEpollStreamChannel(Channel parent, int fd) {
        this(parent, new Socket(fd));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(int fd) {
        this(new Socket(fd));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(Socket fd) {
        this(fd, AbstractEpollStreamChannel.isSoErrorZero(fd));
    }

    protected AbstractEpollStreamChannel(Channel parent, Socket fd) {
        super(parent, fd, Native.EPOLLIN, true);
        this.flags |= Native.EPOLLRDHUP;
    }

    protected AbstractEpollStreamChannel(Socket fd, boolean active) {
        super(null, fd, Native.EPOLLIN, active);
        this.flags |= Native.EPOLLRDHUP;
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollStreamUnsafe();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len) {
        return this.spliceTo(ch, len, this.newPromise());
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
        if (ch.eventLoop() != this.eventLoop()) {
            throw new IllegalArgumentException("EventLoops are not the same.");
        }
        if (len < 0) {
            throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
        }
        if (ch.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED || this.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
            throw new IllegalStateException("spliceTo() supported only when using " + (Object)((Object)EpollMode.LEVEL_TRIGGERED));
        }
        ObjectUtil.checkNotNull(promise, "promise");
        if (!this.isOpen()) {
            promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
        } else {
            this.addToSpliceQueue(new SpliceInChannelTask(ch, len, promise));
            this.failSpliceIfClosed(promise);
        }
        return promise;
    }

    public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len) {
        return this.spliceTo(ch, offset, len, this.newPromise());
    }

    public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len, ChannelPromise promise) {
        if (len < 0) {
            throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset must be >= 0 but was " + offset);
        }
        if (this.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
            throw new IllegalStateException("spliceTo() supported only when using " + (Object)((Object)EpollMode.LEVEL_TRIGGERED));
        }
        ObjectUtil.checkNotNull(promise, "promise");
        if (!this.isOpen()) {
            promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
        } else {
            this.addToSpliceQueue(new SpliceFdTask(ch, offset, len, promise));
            this.failSpliceIfClosed(promise);
        }
        return promise;
    }

    private void failSpliceIfClosed(ChannelPromise promise) {
        if (!this.isOpen() && promise.tryFailure(FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION)) {
            this.eventLoop().execute(new Runnable(){

                @Override
                public void run() {
                    AbstractEpollStreamChannel.this.clearSpliceQueue();
                }
            });
        }
    }

    private boolean writeBytes(ChannelOutboundBuffer in, ByteBuf buf, int writeSpinCount) throws Exception {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            in.remove();
            return true;
        }
        if (buf.hasMemoryAddress() || buf.nioBufferCount() == 1) {
            int writtenBytes = this.doWriteBytes(buf, writeSpinCount);
            in.removeBytes(writtenBytes);
            return writtenBytes == readableBytes;
        }
        ByteBuffer[] nioBuffers = buf.nioBuffers();
        return this.writeBytesMultiple(in, nioBuffers, nioBuffers.length, readableBytes, writeSpinCount);
    }

    private boolean writeBytesMultiple(ChannelOutboundBuffer in, IovArray array, int writeSpinCount) throws IOException {
        long localWrittenBytes;
        long expectedWrittenBytes;
        long initialExpectedWrittenBytes = expectedWrittenBytes = array.size();
        int cnt = array.count();
        assert (expectedWrittenBytes != 0L);
        assert (cnt != 0);
        boolean done = false;
        int offset = 0;
        int end = offset + cnt;
        for (int i = writeSpinCount - 1; i >= 0 && (localWrittenBytes = this.fd().writevAddresses(array.memoryAddress(offset), cnt)) != 0L; --i) {
            long bytes;
            if ((expectedWrittenBytes -= localWrittenBytes) == 0L) {
                done = true;
                break;
            }
            while ((bytes = array.processWritten(offset, localWrittenBytes)) != -1L) {
                --cnt;
                if (++offset < end && (localWrittenBytes -= bytes) > 0L) continue;
            }
        }
        in.removeBytes(initialExpectedWrittenBytes - expectedWrittenBytes);
        return done;
    }

    private boolean writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes, int writeSpinCount) throws IOException {
        long localWrittenBytes;
        assert (expectedWrittenBytes != 0L);
        long initialExpectedWrittenBytes = expectedWrittenBytes;
        boolean done = false;
        int offset = 0;
        int end = offset + nioBufferCnt;
        block0: for (int i = writeSpinCount - 1; i >= 0 && (localWrittenBytes = this.fd().writev(nioBuffers, offset, nioBufferCnt)) != 0L; --i) {
            int bytes;
            if ((expectedWrittenBytes -= localWrittenBytes) == 0L) {
                done = true;
                break;
            }
            do {
                ByteBuffer buffer = nioBuffers[offset];
                int pos = buffer.position();
                bytes = buffer.limit() - pos;
                if ((long)bytes > localWrittenBytes) {
                    buffer.position(pos + (int)localWrittenBytes);
                    continue block0;
                }
                --nioBufferCnt;
            } while (++offset < end && (localWrittenBytes -= (long)bytes) > 0L);
        }
        in.removeBytes(initialExpectedWrittenBytes - expectedWrittenBytes);
        return done;
    }

    private boolean writeDefaultFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region, int writeSpinCount) throws Exception {
        long regionCount = region.count();
        if (region.transferred() >= regionCount) {
            in.remove();
            return true;
        }
        long baseOffset = region.position();
        boolean done = false;
        long flushedAmount = 0L;
        for (int i = writeSpinCount - 1; i >= 0; --i) {
            long offset = region.transferred();
            long localFlushedAmount = Native.sendfile(this.fd().intValue(), region, baseOffset, offset, regionCount - offset);
            if (localFlushedAmount == 0L) break;
            flushedAmount += localFlushedAmount;
            if (region.transferred() < regionCount) continue;
            done = true;
            break;
        }
        if (flushedAmount > 0L) {
            in.progress(flushedAmount);
        }
        if (done) {
            in.remove();
        }
        return done;
    }

    private boolean writeFileRegion(ChannelOutboundBuffer in, FileRegion region, int writeSpinCount) throws Exception {
        long localFlushedAmount;
        if (region.transferred() >= region.count()) {
            in.remove();
            return true;
        }
        boolean done = false;
        long flushedAmount = 0L;
        if (this.byteChannel == null) {
            this.byteChannel = new SocketWritableByteChannel();
        }
        for (int i = writeSpinCount - 1; i >= 0 && (localFlushedAmount = region.transferTo(this.byteChannel, region.transferred())) != 0L; --i) {
            flushedAmount += localFlushedAmount;
            if (region.transferred() < region.count()) continue;
            done = true;
            break;
        }
        if (flushedAmount > 0L) {
            in.progress(flushedAmount);
        }
        if (done) {
            in.remove();
        }
        return done;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        int msgCount;
        int writeSpinCount = this.config().getWriteSpinCount();
        do {
            if ((msgCount = in.size()) != 0) continue;
            this.clearFlag(Native.EPOLLOUT);
            return;
        } while (!(msgCount > 1 && in.current() instanceof ByteBuf ? !this.doWriteMultiple(in, writeSpinCount) : !this.doWriteSingle(in, writeSpinCount)));
        this.setFlag(Native.EPOLLOUT);
    }

    protected boolean doWriteSingle(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        Object msg = in.current();
        if (msg instanceof ByteBuf) {
            if (!this.writeBytes(in, (ByteBuf)msg, writeSpinCount)) {
                return false;
            }
        } else if (msg instanceof DefaultFileRegion) {
            if (!this.writeDefaultFileRegion(in, (DefaultFileRegion)msg, writeSpinCount)) {
                return false;
            }
        } else if (msg instanceof FileRegion) {
            if (!this.writeFileRegion(in, (FileRegion)msg, writeSpinCount)) {
                return false;
            }
        } else if (msg instanceof SpliceOutTask) {
            if (!((SpliceOutTask)msg).spliceOut()) {
                return false;
            }
            in.remove();
        } else {
            throw new Error();
        }
        return true;
    }

    private boolean doWriteMultiple(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        if (PlatformDependent.hasUnsafe()) {
            IovArray array = ((EpollEventLoop)this.eventLoop()).cleanArray();
            in.forEachFlushedMessage(array);
            int cnt = array.count();
            if (cnt >= 1) {
                if (!this.writeBytesMultiple(in, array, writeSpinCount)) {
                    return false;
                }
            } else {
                in.removeBytes(0L);
            }
        } else {
            ByteBuffer[] buffers = in.nioBuffers();
            int cnt = in.nioBufferCount();
            if (cnt >= 1) {
                if (!this.writeBytesMultiple(in, buffers, cnt, in.nioBufferSize(), writeSpinCount)) {
                    return false;
                }
            } else {
                in.removeBytes(0L);
            }
        }
        return true;
    }

    @Override
    protected Object filterOutboundMessage(Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf)msg;
            if (!(buf.hasMemoryAddress() || !PlatformDependent.hasUnsafe() && buf.isDirect())) {
                if (buf instanceof CompositeByteBuf) {
                    CompositeByteBuf comp = (CompositeByteBuf)buf;
                    if (!comp.isDirect() || comp.nioBufferCount() > Native.IOV_MAX) {
                        buf = this.newDirectBuffer(buf);
                        assert (buf.hasMemoryAddress());
                    }
                } else {
                    buf = this.newDirectBuffer(buf);
                    assert (buf.hasMemoryAddress());
                }
            }
            return buf;
        }
        if (msg instanceof FileRegion || msg instanceof SpliceOutTask) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    private void shutdownOutput0(ChannelPromise promise) {
        try {
            this.fd().shutdown(false, true);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    private void shutdownInput0(ChannelPromise promise) {
        try {
            this.fd().shutdown(true, false);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    private void shutdown0(ChannelPromise promise) {
        try {
            this.fd().shutdown(true, true);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    @Override
    public boolean isOutputShutdown() {
        return this.fd().isOutputShutdown();
    }

    @Override
    public boolean isInputShutdown() {
        return this.fd().isInputShutdown();
    }

    @Override
    public boolean isShutdown() {
        return this.fd().isShutdown();
    }

    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe)this.unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    AbstractEpollStreamChannel.this.shutdownOutput0(promise);
                }
            });
        } else {
            EventLoop loop = this.eventLoop();
            if (loop.inEventLoop()) {
                this.shutdownOutput0(promise);
            } else {
                loop.execute(new Runnable(){

                    @Override
                    public void run() {
                        AbstractEpollStreamChannel.this.shutdownOutput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    @Override
    public ChannelFuture shutdownInput() {
        return this.shutdownInput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownInput(final ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe)this.unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    AbstractEpollStreamChannel.this.shutdownInput0(promise);
                }
            });
        } else {
            EventLoop loop = this.eventLoop();
            if (loop.inEventLoop()) {
                this.shutdownInput0(promise);
            } else {
                loop.execute(new Runnable(){

                    @Override
                    public void run() {
                        AbstractEpollStreamChannel.this.shutdownInput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    @Override
    public ChannelFuture shutdown() {
        return this.shutdown(this.newPromise());
    }

    @Override
    public ChannelFuture shutdown(final ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe)this.unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    AbstractEpollStreamChannel.this.shutdown0(promise);
                }
            });
        } else {
            EventLoop loop = this.eventLoop();
            if (loop.inEventLoop()) {
                this.shutdown0(promise);
            } else {
                loop.execute(new Runnable(){

                    @Override
                    public void run() {
                        AbstractEpollStreamChannel.this.shutdown0(promise);
                    }
                });
            }
        }
        return promise;
    }

    @Override
    protected void doClose() throws Exception {
        try {
            ScheduledFuture<?> future;
            ChannelPromise promise = this.connectPromise;
            if (promise != null) {
                promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                this.connectPromise = null;
            }
            if ((future = this.connectTimeoutFuture) != null) {
                future.cancel(false);
                this.connectTimeoutFuture = null;
            }
            super.doClose();
        } finally {
            AbstractEpollStreamChannel.safeClosePipe(this.pipeIn);
            AbstractEpollStreamChannel.safeClosePipe(this.pipeOut);
            this.clearSpliceQueue();
        }
    }

    private void clearSpliceQueue() {
        SpliceInTask task;
        if (this.spliceQueue == null) {
            return;
        }
        while ((task = this.spliceQueue.poll()) != null) {
            task.promise.tryFailure(CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.fd().bind(localAddress);
        }
        boolean success = false;
        try {
            boolean connected = this.fd().connect(remoteAddress);
            if (!connected) {
                this.setFlag(Native.EPOLLOUT);
            }
            success = true;
            boolean bl = connected;
            return bl;
        } finally {
            if (!success) {
                this.doClose();
            }
        }
    }

    private static void safeClosePipe(FileDescriptor fd) {
        block3: {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    if (!logger.isWarnEnabled()) break block3;
                    logger.warn("Error while closing a pipe", e);
                }
            }
        }
    }

    private void addToSpliceQueue(final SpliceInTask task) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.addToSpliceQueue0(task);
        } else {
            eventLoop.execute(new Runnable(){

                @Override
                public void run() {
                    AbstractEpollStreamChannel.this.addToSpliceQueue0(task);
                }
            });
        }
    }

    private void addToSpliceQueue0(SpliceInTask task) {
        if (this.spliceQueue == null) {
            this.spliceQueue = PlatformDependent.newMpscQueue();
        }
        this.spliceQueue.add(task);
    }

    private final class SocketWritableByteChannel
    implements WritableByteChannel {
        private SocketWritableByteChannel() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int write(ByteBuffer src) throws IOException {
            int written;
            int position = src.position();
            int limit = src.limit();
            if (src.isDirect()) {
                written = AbstractEpollStreamChannel.this.fd().write(src, position, src.limit());
            } else {
                int readableBytes = limit - position;
                ByteBuf buffer = null;
                try {
                    if (readableBytes == 0) {
                        buffer = Unpooled.EMPTY_BUFFER;
                    } else {
                        ByteBufAllocator alloc = AbstractEpollStreamChannel.this.alloc();
                        if (alloc.isDirectBufferPooled()) {
                            buffer = alloc.directBuffer(readableBytes);
                        } else {
                            buffer = ByteBufUtil.threadLocalDirectBuffer();
                            if (buffer == null) {
                                buffer = Unpooled.directBuffer(readableBytes);
                            }
                        }
                    }
                    buffer.writeBytes(src.duplicate());
                    ByteBuffer nioBuffer = buffer.internalNioBuffer(buffer.readerIndex(), readableBytes);
                    written = AbstractEpollStreamChannel.this.fd().write(nioBuffer, nioBuffer.position(), nioBuffer.limit());
                } finally {
                    if (buffer != null) {
                        buffer.release();
                    }
                }
            }
            if (written > 0) {
                src.position(position + written);
            }
            return written;
        }

        @Override
        public boolean isOpen() {
            return AbstractEpollStreamChannel.this.fd().isOpen();
        }

        @Override
        public void close() throws IOException {
            AbstractEpollStreamChannel.this.fd().close();
        }
    }

    private final class SpliceFdTask
    extends SpliceInTask {
        private final FileDescriptor fd;
        private final ChannelPromise promise;
        private final int offset;

        SpliceFdTask(FileDescriptor fd, int offset, int len, ChannelPromise promise) {
            super(len, promise);
            this.fd = fd;
            this.promise = promise;
            this.offset = offset;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
            assert (AbstractEpollStreamChannel.this.eventLoop().inEventLoop());
            if (this.len == 0) {
                this.promise.setSuccess();
                return true;
            }
            try {
                FileDescriptor[] pipe = FileDescriptor.pipe();
                FileDescriptor pipeIn = pipe[0];
                FileDescriptor pipeOut = pipe[1];
                try {
                    boolean bl;
                    int splicedIn = this.spliceIn(pipeOut, handle);
                    if (splicedIn > 0) {
                        int splicedOut;
                        if (this.len != Integer.MAX_VALUE) {
                            this.len -= splicedIn;
                        }
                        while ((splicedIn -= (splicedOut = Native.splice(pipeIn.intValue(), -1L, this.fd.intValue(), this.offset, splicedIn))) > 0) {
                        }
                        if (this.len == 0) {
                            this.promise.setSuccess();
                            bl = true;
                            return bl;
                        }
                    }
                    bl = false;
                    return bl;
                } finally {
                    AbstractEpollStreamChannel.safeClosePipe(pipeIn);
                    AbstractEpollStreamChannel.safeClosePipe(pipeOut);
                }
            } catch (Throwable cause) {
                this.promise.setFailure(cause);
                return true;
            }
        }
    }

    private final class SpliceOutTask {
        private final AbstractEpollStreamChannel ch;
        private final boolean autoRead;
        private int len;

        SpliceOutTask(AbstractEpollStreamChannel ch, int len, boolean autoRead) {
            this.ch = ch;
            this.len = len;
            this.autoRead = autoRead;
        }

        public boolean spliceOut() throws Exception {
            assert (this.ch.eventLoop().inEventLoop());
            try {
                int splicedOut = Native.splice(this.ch.pipeIn.intValue(), -1L, this.ch.fd().intValue(), -1L, this.len);
                this.len -= splicedOut;
                if (this.len == 0) {
                    if (this.autoRead) {
                        AbstractEpollStreamChannel.this.config().setAutoRead(true);
                    }
                    return true;
                }
                return false;
            } catch (IOException e) {
                if (this.autoRead) {
                    AbstractEpollStreamChannel.this.config().setAutoRead(true);
                }
                throw e;
            }
        }
    }

    private final class SpliceInChannelTask
    extends SpliceInTask
    implements ChannelFutureListener {
        private final AbstractEpollStreamChannel ch;

        SpliceInChannelTask(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
            super(len, promise);
            this.ch = ch;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                this.promise.setFailure(future.cause());
            }
        }

        @Override
        public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
            assert (this.ch.eventLoop().inEventLoop());
            if (this.len == 0) {
                this.promise.setSuccess();
                return true;
            }
            try {
                int splicedIn;
                FileDescriptor pipeOut = this.ch.pipeOut;
                if (pipeOut == null) {
                    FileDescriptor[] pipe = FileDescriptor.pipe();
                    this.ch.pipeIn = pipe[0];
                    pipeOut = this.ch.pipeOut = pipe[1];
                }
                if ((splicedIn = this.spliceIn(pipeOut, handle)) > 0) {
                    if (this.len != Integer.MAX_VALUE) {
                        this.len -= splicedIn;
                    }
                    ChannelPromise splicePromise = this.len == 0 ? this.promise : this.ch.newPromise().addListener(this);
                    boolean autoRead = AbstractEpollStreamChannel.this.config().isAutoRead();
                    this.ch.unsafe().write(new SpliceOutTask(this.ch, splicedIn, autoRead), splicePromise);
                    this.ch.unsafe().flush();
                    if (autoRead && !splicePromise.isDone()) {
                        AbstractEpollStreamChannel.this.config().setAutoRead(false);
                    }
                }
                return this.len == 0;
            } catch (Throwable cause) {
                this.promise.setFailure(cause);
                return true;
            }
        }
    }

    protected abstract class SpliceInTask {
        final ChannelPromise promise;
        int len;

        protected SpliceInTask(int len, ChannelPromise promise) {
            this.promise = promise;
            this.len = len;
        }

        abstract boolean spliceIn(RecvByteBufAllocator.Handle var1);

        protected final int spliceIn(FileDescriptor pipeOut, RecvByteBufAllocator.Handle handle) throws IOException {
            int localSplicedIn;
            int length = Math.min(handle.guess(), this.len);
            int splicedIn = 0;
            while ((localSplicedIn = Native.splice(AbstractEpollStreamChannel.this.fd().intValue(), -1L, pipeOut.intValue(), -1L, length)) != 0) {
                splicedIn += localSplicedIn;
                length -= localSplicedIn;
            }
            return splicedIn;
        }
    }

    class EpollStreamUnsafe
    extends AbstractEpollChannel.AbstractEpollUnsafe {
        EpollStreamUnsafe() {
        }

        @Override
        protected Executor prepareToClose() {
            return super.prepareToClose();
        }

        private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, EpollRecvByteAllocatorHandle allocHandle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            allocHandle.readComplete();
            pipeline.fireChannelReadComplete();
            pipeline.fireExceptionCaught(cause);
            if (close || cause instanceof IOException) {
                this.shutdownInput(false);
            }
        }

        @Override
        public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            if (!promise.setUncancellable() || !this.ensureOpen(promise)) {
                return;
            }
            try {
                if (AbstractEpollStreamChannel.this.connectPromise != null) {
                    throw new ConnectionPendingException();
                }
                boolean wasActive = AbstractEpollStreamChannel.this.isActive();
                if (AbstractEpollStreamChannel.this.doConnect(remoteAddress, localAddress)) {
                    this.fulfillConnectPromise(promise, wasActive);
                } else {
                    AbstractEpollStreamChannel.this.connectPromise = promise;
                    AbstractEpollStreamChannel.this.requestedRemoteAddress = remoteAddress;
                    int connectTimeoutMillis = AbstractEpollStreamChannel.this.config().getConnectTimeoutMillis();
                    if (connectTimeoutMillis > 0) {
                        AbstractEpollStreamChannel.this.connectTimeoutFuture = AbstractEpollStreamChannel.this.eventLoop().schedule(new Runnable(){

                            @Override
                            public void run() {
                                ChannelPromise connectPromise = AbstractEpollStreamChannel.this.connectPromise;
                                ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                                if (connectPromise != null && connectPromise.tryFailure(cause)) {
                                    EpollStreamUnsafe.this.close(EpollStreamUnsafe.this.voidPromise());
                                }
                            }
                        }, (long)connectTimeoutMillis, TimeUnit.MILLISECONDS);
                    }
                    promise.addListener(new ChannelFutureListener(){

                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isCancelled()) {
                                if (AbstractEpollStreamChannel.this.connectTimeoutFuture != null) {
                                    AbstractEpollStreamChannel.this.connectTimeoutFuture.cancel(false);
                                }
                                AbstractEpollStreamChannel.this.connectPromise = null;
                                EpollStreamUnsafe.this.close(EpollStreamUnsafe.this.voidPromise());
                            }
                        }
                    });
                }
            } catch (Throwable t) {
                this.closeIfClosed();
                promise.tryFailure(this.annotateConnectException(t, remoteAddress));
            }
        }

        private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
            if (promise == null) {
                return;
            }
            AbstractEpollStreamChannel.this.active = true;
            boolean active = AbstractEpollStreamChannel.this.isActive();
            boolean promiseSet = promise.trySuccess();
            if (!wasActive && active) {
                AbstractEpollStreamChannel.this.pipeline().fireChannelActive();
            }
            if (!promiseSet) {
                this.close(this.voidPromise());
            }
        }

        private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
            if (promise == null) {
                return;
            }
            promise.tryFailure(cause);
            this.closeIfClosed();
        }

        private void finishConnect() {
            assert (AbstractEpollStreamChannel.this.eventLoop().inEventLoop());
            boolean connectStillInProgress = false;
            try {
                boolean wasActive = AbstractEpollStreamChannel.this.isActive();
                if (!this.doFinishConnect()) {
                    connectStillInProgress = true;
                    return;
                }
                this.fulfillConnectPromise(AbstractEpollStreamChannel.this.connectPromise, wasActive);
            } catch (Throwable t) {
                this.fulfillConnectPromise(AbstractEpollStreamChannel.this.connectPromise, this.annotateConnectException(t, AbstractEpollStreamChannel.this.requestedRemoteAddress));
            } finally {
                if (!connectStillInProgress) {
                    if (AbstractEpollStreamChannel.this.connectTimeoutFuture != null) {
                        AbstractEpollStreamChannel.this.connectTimeoutFuture.cancel(false);
                    }
                    AbstractEpollStreamChannel.this.connectPromise = null;
                }
            }
        }

        @Override
        void epollOutReady() {
            if (AbstractEpollStreamChannel.this.connectPromise != null) {
                this.finishConnect();
            } else {
                super.epollOutReady();
            }
        }

        boolean doFinishConnect() throws Exception {
            if (AbstractEpollStreamChannel.this.fd().finishConnect()) {
                AbstractEpollStreamChannel.this.clearFlag(Native.EPOLLOUT);
                return true;
            }
            AbstractEpollStreamChannel.this.setFlag(Native.EPOLLOUT);
            return false;
        }

        @Override
        EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle handle) {
            return new EpollRecvByteAllocatorStreamingHandle(handle);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void epollInReady() {
            EpollChannelConfig config = AbstractEpollStreamChannel.this.config();
            if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady(config)) {
                this.clearEpollIn0();
                return;
            }
            EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
            allocHandle.edgeTriggered(AbstractEpollStreamChannel.this.isFlagSet(Native.EPOLLET));
            ChannelPipeline pipeline = AbstractEpollStreamChannel.this.pipeline();
            ByteBufAllocator allocator = config.getAllocator();
            allocHandle.reset(config);
            this.epollInBefore();
            ByteBuf byteBuf = null;
            boolean close = false;
            try {
                do {
                    SpliceInTask spliceTask;
                    if (AbstractEpollStreamChannel.this.spliceQueue != null && (spliceTask = (SpliceInTask)AbstractEpollStreamChannel.this.spliceQueue.peek()) != null) {
                        if (!spliceTask.spliceIn(allocHandle)) break;
                        if (!AbstractEpollStreamChannel.this.isActive()) continue;
                        AbstractEpollStreamChannel.this.spliceQueue.remove();
                        continue;
                    }
                    byteBuf = allocHandle.allocate(allocator);
                    allocHandle.lastBytesRead(AbstractEpollStreamChannel.this.doReadBytes(byteBuf));
                    if (allocHandle.lastBytesRead() <= 0) {
                        byteBuf.release();
                        byteBuf = null;
                        close = allocHandle.lastBytesRead() < 0;
                        break;
                    }
                    allocHandle.incMessagesRead(1);
                    this.readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                    byteBuf = null;
                    if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady(config)) break;
                } while (allocHandle.continueReading());
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                if (close) {
                    this.shutdownInput(false);
                }
            } catch (Throwable t) {
                this.handleReadException(pipeline, byteBuf, t, close, allocHandle);
            } finally {
                this.epollInFinally(config);
            }
        }
    }
}

