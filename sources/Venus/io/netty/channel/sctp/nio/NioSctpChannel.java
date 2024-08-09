/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NioSctpChannel
extends AbstractNioMessageChannel
implements SctpChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
    private final SctpChannelConfig config;
    private final NotificationHandler<?> notificationHandler;

    private static com.sun.nio.sctp.SctpChannel newSctpChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a sctp channel.", iOException);
        }
    }

    public NioSctpChannel() {
        this(NioSctpChannel.newSctpChannel());
    }

    public NioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }

    public NioSctpChannel(Channel channel, com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(channel, sctpChannel, 1);
        try {
            sctpChannel.configureBlocking(true);
            this.config = new NioSctpChannelConfig(this, this, sctpChannel, null);
            this.notificationHandler = new SctpNotificationHandler(this);
        } catch (IOException iOException) {
            block4: {
                try {
                    sctpChannel.close();
                } catch (IOException iOException2) {
                    if (!logger.isWarnEnabled()) break block4;
                    logger.warn("Failed to close a partially initialized sctp channel.", iOException2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", iOException);
        }
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }

    @Override
    public SctpServerChannel parent() {
        return (SctpServerChannel)super.parent();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public Association association() {
        try {
            return this.javaChannel().association();
        } catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> set = this.javaChannel().getAllLocalAddresses();
            LinkedHashSet<InetSocketAddress> linkedHashSet = new LinkedHashSet<InetSocketAddress>(set.size());
            for (SocketAddress socketAddress : set) {
                linkedHashSet.add((InetSocketAddress)socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable throwable) {
            return Collections.emptySet();
        }
    }

    @Override
    public SctpChannelConfig config() {
        return this.config;
    }

    @Override
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> set = this.javaChannel().getRemoteAddresses();
            HashSet<InetSocketAddress> hashSet = new HashSet<InetSocketAddress>(set.size());
            for (SocketAddress socketAddress : set) {
                hashSet.add((InetSocketAddress)socketAddress);
            }
            return hashSet;
        } catch (Throwable throwable) {
            return Collections.emptySet();
        }
    }

    @Override
    protected com.sun.nio.sctp.SctpChannel javaChannel() {
        return (com.sun.nio.sctp.SctpChannel)super.javaChannel();
    }

    @Override
    public boolean isActive() {
        com.sun.nio.sctp.SctpChannel sctpChannel = this.javaChannel();
        return sctpChannel.isOpen() && this.association() != null;
    }

    @Override
    protected SocketAddress localAddress0() {
        try {
            Iterator<SocketAddress> iterator2 = this.javaChannel().getAllLocalAddresses().iterator();
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        try {
            Iterator<SocketAddress> iterator2 = this.javaChannel().getRemoteAddresses().iterator();
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.javaChannel().bind(socketAddress);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.javaChannel().bind(socketAddress2);
        }
        boolean bl = false;
        try {
            boolean bl2 = this.javaChannel().connect(socketAddress);
            if (!bl2) {
                this.selectionKey().interestOps(8);
            }
            bl = true;
            boolean bl3 = bl2;
            return bl3;
        } finally {
            if (!bl) {
                this.doClose();
            }
        }
    }

    @Override
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        int n;
        com.sun.nio.sctp.SctpChannel sctpChannel = this.javaChannel();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        ByteBuf byteBuf = handle.allocate(this.config().getAllocator());
        boolean bl = true;
        try {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes());
            n = byteBuffer.position();
            MessageInfo messageInfo = sctpChannel.receive(byteBuffer, null, this.notificationHandler);
            if (messageInfo == null) {
                int n2 = 0;
                return n2;
            }
            handle.lastBytesRead(byteBuffer.position() - n);
            list.add(new SctpMessage(messageInfo, byteBuf.writerIndex(byteBuf.writerIndex() + handle.lastBytesRead())));
            bl = false;
            int n3 = 1;
            return n3;
        } catch (Throwable throwable) {
            PlatformDependent.throwException(throwable);
            n = -1;
            return n;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    @Override
    protected boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        boolean bl;
        SctpMessage sctpMessage = (SctpMessage)object;
        ByteBuf byteBuf = sctpMessage.content();
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return false;
        }
        ByteBufAllocator byteBufAllocator = this.alloc();
        boolean bl2 = bl = byteBuf.nioBufferCount() != 1;
        if (!bl && !byteBuf.isDirect() && byteBufAllocator.isDirectBufferPooled()) {
            bl = true;
        }
        if (bl) {
            byteBuf = byteBufAllocator.directBuffer(n).writeBytes(byteBuf);
        }
        ByteBuffer byteBuffer = byteBuf.nioBuffer();
        MessageInfo messageInfo = MessageInfo.createOutgoing(this.association(), null, sctpMessage.streamIdentifier());
        messageInfo.payloadProtocolID(sctpMessage.protocolIdentifier());
        messageInfo.streamNumber(sctpMessage.streamIdentifier());
        messageInfo.unordered(sctpMessage.isUnordered());
        int n2 = this.javaChannel().send(byteBuffer, messageInfo);
        return n2 > 0;
    }

    @Override
    protected final Object filterOutboundMessage(Object object) throws Exception {
        if (object instanceof SctpMessage) {
            SctpMessage sctpMessage = (SctpMessage)object;
            ByteBuf byteBuf = sctpMessage.content();
            if (byteBuf.isDirect() && byteBuf.nioBufferCount() == 1) {
                return sctpMessage;
            }
            return new SctpMessage(sctpMessage.protocolIdentifier(), sctpMessage.streamIdentifier(), sctpMessage.isUnordered(), this.newDirectBuffer(sctpMessage, byteBuf));
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class));
    }

    @Override
    public ChannelFuture bindAddress(InetAddress inetAddress) {
        return this.bindAddress(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.javaChannel().bindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else {
            this.eventLoop().execute(new Runnable(this, inetAddress, channelPromise){
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final NioSctpChannel this$0;
                {
                    this.this$0 = nioSctpChannel;
                    this.val$localAddress = inetAddress;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    this.this$0.bindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture unbindAddress(InetAddress inetAddress) {
        return this.unbindAddress(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.javaChannel().unbindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else {
            this.eventLoop().execute(new Runnable(this, inetAddress, channelPromise){
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final NioSctpChannel this$0;
                {
                    this.this$0 = nioSctpChannel;
                    this.val$localAddress = inetAddress;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    this.this$0.unbindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    @Override
    protected SelectableChannel javaChannel() {
        return this.javaChannel();
    }

    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }

    @Override
    public Channel parent() {
        return this.parent();
    }

    @Override
    public ChannelConfig config() {
        return this.config();
    }

    static void access$100(NioSctpChannel nioSctpChannel) {
        nioSctpChannel.clearReadPending();
    }

    private final class NioSctpChannelConfig
    extends DefaultSctpChannelConfig {
        final NioSctpChannel this$0;

        private NioSctpChannelConfig(NioSctpChannel nioSctpChannel, NioSctpChannel nioSctpChannel2, com.sun.nio.sctp.SctpChannel sctpChannel) {
            this.this$0 = nioSctpChannel;
            super(nioSctpChannel2, sctpChannel);
        }

        @Override
        protected void autoReadCleared() {
            NioSctpChannel.access$100(this.this$0);
        }

        NioSctpChannelConfig(NioSctpChannel nioSctpChannel, NioSctpChannel nioSctpChannel2, com.sun.nio.sctp.SctpChannel sctpChannel, 1 var4_4) {
            this(nioSctpChannel, nioSctpChannel2, sctpChannel);
        }
    }
}

