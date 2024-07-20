/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.EpollSocketChannelConfig;
import io.netty.channel.epoll.Native;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.UnixChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.UnresolvedAddressException;

abstract class AbstractEpollChannel
extends AbstractChannel
implements UnixChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private final int readFlag;
    private final Socket fileDescriptor;
    protected int flags = Native.EPOLLET;
    boolean inputClosedSeenErrorOnRead;
    boolean epollInReadyRunnablePending;
    protected volatile boolean active;

    AbstractEpollChannel(Socket fd, int flag) {
        this(null, fd, flag, false);
    }

    AbstractEpollChannel(Channel parent, Socket fd, int flag, boolean active) {
        super(parent);
        this.fileDescriptor = ObjectUtil.checkNotNull(fd, "fd");
        this.readFlag = flag;
        this.flags |= flag;
        this.active = active;
    }

    static boolean isSoErrorZero(Socket fd) {
        try {
            return fd.getSoError() == 0;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    void setFlag(int flag) throws IOException {
        if (!this.isFlagSet(flag)) {
            this.flags |= flag;
            this.modifyEvents();
        }
    }

    void clearFlag(int flag) throws IOException {
        if (this.isFlagSet(flag)) {
            this.flags &= ~flag;
            this.modifyEvents();
        }
    }

    boolean isFlagSet(int flag) {
        return (this.flags & flag) != 0;
    }

    @Override
    public final Socket fd() {
        return this.fileDescriptor;
    }

    @Override
    public abstract EpollChannelConfig config();

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    protected void doClose() throws Exception {
        this.active = false;
        this.inputClosedSeenErrorOnRead = true;
        try {
            this.doDeregister();
        } finally {
            this.fileDescriptor.close();
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof EpollEventLoop;
    }

    @Override
    public boolean isOpen() {
        return this.fileDescriptor.isOpen();
    }

    @Override
    protected void doDeregister() throws Exception {
        ((EpollEventLoop)this.eventLoop()).remove(this);
    }

    @Override
    protected final void doBeginRead() throws Exception {
        AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)this.unsafe();
        unsafe.readPending = true;
        this.setFlag(this.readFlag);
        if (unsafe.maybeMoreDataToRead) {
            unsafe.executeEpollInReadyRunnable(this.config());
        }
    }

    final boolean shouldBreakEpollInReady(ChannelConfig config) {
        return this.fileDescriptor.isInputShutdown() && (this.inputClosedSeenErrorOnRead || !this.isAllowHalfClosure(config));
    }

    final boolean isAllowHalfClosure(ChannelConfig config) {
        return config instanceof EpollSocketChannelConfig && ((EpollSocketChannelConfig)config).isAllowHalfClosure();
    }

    final void clearEpollIn() {
        if (this.isRegistered()) {
            EventLoop loop = this.eventLoop();
            final AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)this.unsafe();
            if (loop.inEventLoop()) {
                unsafe.clearEpollIn0();
            } else {
                loop.execute(new Runnable(){

                    @Override
                    public void run() {
                        if (!unsafe.readPending && !AbstractEpollChannel.this.config().isAutoRead()) {
                            unsafe.clearEpollIn0();
                        }
                    }
                });
            }
        } else {
            this.flags &= ~this.readFlag;
        }
    }

    private void modifyEvents() throws IOException {
        if (this.isOpen() && this.isRegistered()) {
            ((EpollEventLoop)this.eventLoop()).modify(this);
        }
    }

    @Override
    protected void doRegister() throws Exception {
        this.epollInReadyRunnablePending = false;
        ((EpollEventLoop)this.eventLoop()).add(this);
    }

    @Override
    protected abstract AbstractEpollUnsafe newUnsafe();

    protected final ByteBuf newDirectBuffer(ByteBuf buf) {
        return this.newDirectBuffer(buf, buf);
    }

    protected final ByteBuf newDirectBuffer(Object holder, ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(holder);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = this.alloc();
        if (alloc.isDirectBufferPooled()) {
            return AbstractEpollChannel.newDirectBuffer0(holder, buf, alloc, readableBytes);
        }
        ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf == null) {
            return AbstractEpollChannel.newDirectBuffer0(holder, buf, alloc, readableBytes);
        }
        directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(holder);
        return directBuf;
    }

    private static ByteBuf newDirectBuffer0(Object holder, ByteBuf buf, ByteBufAllocator alloc, int capacity) {
        ByteBuf directBuf = alloc.directBuffer(capacity);
        directBuf.writeBytes(buf, buf.readerIndex(), capacity);
        ReferenceCountUtil.safeRelease(holder);
        return directBuf;
    }

    protected static void checkResolvable(InetSocketAddress addr) {
        if (addr.isUnresolved()) {
            throw new UnresolvedAddressException();
        }
    }

    protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
        int localReadAmount;
        int writerIndex = byteBuf.writerIndex();
        this.unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
        if (byteBuf.hasMemoryAddress()) {
            localReadAmount = this.fileDescriptor.readAddress(byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
        } else {
            ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
            localReadAmount = this.fileDescriptor.read(buf, buf.position(), buf.limit());
        }
        if (localReadAmount > 0) {
            byteBuf.writerIndex(writerIndex + localReadAmount);
        }
        return localReadAmount;
    }

    protected final int doWriteBytes(ByteBuf buf, int writeSpinCount) throws Exception {
        int readableBytes = buf.readableBytes();
        int writtenBytes = 0;
        if (buf.hasMemoryAddress()) {
            int localFlushedAmount;
            long memoryAddress = buf.memoryAddress();
            int readerIndex = buf.readerIndex();
            int writerIndex = buf.writerIndex();
            for (int i = writeSpinCount - 1; i >= 0 && (localFlushedAmount = this.fileDescriptor.writeAddress(memoryAddress, readerIndex, writerIndex)) > 0; --i) {
                if ((writtenBytes += localFlushedAmount) == readableBytes) {
                    return writtenBytes;
                }
                readerIndex += localFlushedAmount;
            }
        } else {
            int limit;
            int pos;
            int localFlushedAmount;
            ByteBuffer nioBuf = buf.nioBufferCount() == 1 ? buf.internalNioBuffer(buf.readerIndex(), buf.readableBytes()) : buf.nioBuffer();
            for (int i = writeSpinCount - 1; i >= 0 && (localFlushedAmount = this.fileDescriptor.write(nioBuf, pos = nioBuf.position(), limit = nioBuf.limit())) > 0; --i) {
                nioBuf.position(pos + localFlushedAmount);
                if ((writtenBytes += localFlushedAmount) != readableBytes) continue;
                return writtenBytes;
            }
        }
        if (writtenBytes < readableBytes) {
            this.setFlag(Native.EPOLLOUT);
        }
        return writtenBytes;
    }

    protected abstract class AbstractEpollUnsafe
    extends AbstractChannel.AbstractUnsafe {
        boolean readPending;
        boolean maybeMoreDataToRead;
        private EpollRecvByteAllocatorHandle allocHandle;
        private final Runnable epollInReadyRunnable = new Runnable(){

            @Override
            public void run() {
                AbstractEpollChannel.this.epollInReadyRunnablePending = false;
                AbstractEpollUnsafe.this.epollInReady();
            }
        };

        protected AbstractEpollUnsafe() {
        }

        abstract void epollInReady();

        final void epollInBefore() {
            this.maybeMoreDataToRead = false;
        }

        final void epollInFinally(ChannelConfig config) {
            boolean bl = this.maybeMoreDataToRead = this.allocHandle.isEdgeTriggered() && this.allocHandle.maybeMoreDataToRead();
            if (!this.readPending && !config.isAutoRead()) {
                AbstractEpollChannel.this.clearEpollIn();
            } else if (this.readPending && this.maybeMoreDataToRead) {
                this.executeEpollInReadyRunnable(config);
            }
        }

        final void executeEpollInReadyRunnable(ChannelConfig config) {
            if (AbstractEpollChannel.this.epollInReadyRunnablePending || !AbstractEpollChannel.this.isActive() || AbstractEpollChannel.this.shouldBreakEpollInReady(config)) {
                return;
            }
            AbstractEpollChannel.this.epollInReadyRunnablePending = true;
            AbstractEpollChannel.this.eventLoop().execute(this.epollInReadyRunnable);
        }

        final void epollRdHupReady() {
            this.recvBufAllocHandle().receivedRdHup();
            if (AbstractEpollChannel.this.isActive()) {
                this.epollInReady();
            } else {
                this.shutdownInput(true);
            }
            this.clearEpollRdHup();
        }

        private void clearEpollRdHup() {
            try {
                AbstractEpollChannel.this.clearFlag(Native.EPOLLRDHUP);
            } catch (IOException e) {
                AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
                this.close(this.voidPromise());
            }
        }

        void shutdownInput(boolean rdHup) {
            if (!AbstractEpollChannel.this.fd().isInputShutdown()) {
                if (AbstractEpollChannel.this.isAllowHalfClosure(AbstractEpollChannel.this.config())) {
                    try {
                        AbstractEpollChannel.this.fd().shutdown(true, false);
                    } catch (IOException ignored) {
                        this.fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    } catch (NotYetConnectedException ignore) {
                        this.fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    }
                    AbstractEpollChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                } else {
                    this.close(this.voidPromise());
                }
            } else if (!rdHup) {
                AbstractEpollChannel.this.inputClosedSeenErrorOnRead = true;
                AbstractEpollChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            }
        }

        private void fireEventAndClose(Object evt) {
            AbstractEpollChannel.this.pipeline().fireUserEventTriggered(evt);
            this.close(this.voidPromise());
        }

        @Override
        public EpollRecvByteAllocatorHandle recvBufAllocHandle() {
            if (this.allocHandle == null) {
                this.allocHandle = this.newEpollHandle((RecvByteBufAllocator.ExtendedHandle)super.recvBufAllocHandle());
            }
            return this.allocHandle;
        }

        EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle handle) {
            return new EpollRecvByteAllocatorHandle(handle);
        }

        @Override
        protected void flush0() {
            if (AbstractEpollChannel.this.isFlagSet(Native.EPOLLOUT)) {
                return;
            }
            super.flush0();
        }

        void epollOutReady() {
            if (AbstractEpollChannel.this.fd().isOutputShutdown()) {
                return;
            }
            super.flush0();
        }

        protected final void clearEpollIn0() {
            assert (AbstractEpollChannel.this.eventLoop().inEventLoop());
            try {
                this.readPending = false;
                AbstractEpollChannel.this.clearFlag(AbstractEpollChannel.this.readFlag);
            } catch (IOException e) {
                AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
                AbstractEpollChannel.this.unsafe().close(AbstractEpollChannel.this.unsafe().voidPromise());
            }
        }
    }
}

