/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.sctp.oio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OioSctpChannel
extends AbstractOioMessageChannel
implements SctpChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')';
    private final com.sun.nio.sctp.SctpChannel ch;
    private final SctpChannelConfig config;
    private final Selector readSelector;
    private final Selector writeSelector;
    private final Selector connectSelector;
    private final NotificationHandler<?> notificationHandler;

    private static com.sun.nio.sctp.SctpChannel openChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a sctp channel.", iOException);
        }
    }

    public OioSctpChannel() {
        this(OioSctpChannel.openChannel());
    }

    public OioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }

    public OioSctpChannel(Channel channel, com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(channel);
        this.ch = sctpChannel;
        boolean bl = false;
        try {
            sctpChannel.configureBlocking(true);
            this.readSelector = Selector.open();
            this.writeSelector = Selector.open();
            this.connectSelector = Selector.open();
            sctpChannel.register(this.readSelector, 1);
            sctpChannel.register(this.writeSelector, 4);
            sctpChannel.register(this.connectSelector, 8);
            this.config = new OioSctpChannelConfig(this, this, sctpChannel, null);
            this.notificationHandler = new SctpNotificationHandler(this);
            bl = true;
        } catch (Exception exception) {
            throw new ChannelException("failed to initialize a sctp channel", exception);
        } finally {
            if (!bl) {
                try {
                    sctpChannel.close();
                } catch (IOException iOException) {
                    logger.warn("Failed to close a sctp channel.", iOException);
                }
            }
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
    public SctpChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        boolean bl;
        if (!this.readSelector.isOpen()) {
            return 1;
        }
        int n = 0;
        int n2 = this.readSelector.select(1000L);
        boolean bl2 = bl = n2 > 0;
        if (!bl) {
            return n;
        }
        this.readSelector.selectedKeys().clear();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        ByteBuf byteBuf = handle.allocate(this.config().getAllocator());
        boolean bl3 = true;
        try {
            ByteBuffer byteBuffer = byteBuf.nioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes());
            MessageInfo messageInfo = this.ch.receive(byteBuffer, null, this.notificationHandler);
            if (messageInfo == null) {
                int n3 = n;
                return n3;
            }
            byteBuffer.flip();
            handle.lastBytesRead(byteBuffer.remaining());
            list.add(new SctpMessage(messageInfo, byteBuf.writerIndex(byteBuf.writerIndex() + handle.lastBytesRead())));
            bl3 = false;
            ++n;
        } catch (Throwable throwable) {
            PlatformDependent.throwException(throwable);
        } finally {
            if (bl3) {
                byteBuf.release();
            }
        }
        return n;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (!this.writeSelector.isOpen()) {
            return;
        }
        int n = channelOutboundBuffer.size();
        int n2 = this.writeSelector.select(1000L);
        if (n2 > 0) {
            Set<SelectionKey> set = this.writeSelector.selectedKeys();
            if (set.isEmpty()) {
                return;
            }
            Iterator<SelectionKey> iterator2 = set.iterator();
            int n3 = 0;
            do {
                ByteBuffer byteBuffer;
                if (n3 == n) {
                    return;
                }
                iterator2.next();
                iterator2.remove();
                SctpMessage sctpMessage = (SctpMessage)channelOutboundBuffer.current();
                if (sctpMessage == null) {
                    return;
                }
                ByteBuf byteBuf = sctpMessage.content();
                int n4 = byteBuf.readableBytes();
                if (byteBuf.nioBufferCount() != -1) {
                    byteBuffer = byteBuf.nioBuffer();
                } else {
                    byteBuffer = ByteBuffer.allocate(n4);
                    byteBuf.getBytes(byteBuf.readerIndex(), byteBuffer);
                    byteBuffer.flip();
                }
                MessageInfo messageInfo = MessageInfo.createOutgoing(this.association(), null, sctpMessage.streamIdentifier());
                messageInfo.payloadProtocolID(sctpMessage.protocolIdentifier());
                messageInfo.streamNumber(sctpMessage.streamIdentifier());
                messageInfo.unordered(sctpMessage.isUnordered());
                this.ch.send(byteBuffer, messageInfo);
                ++n3;
                channelOutboundBuffer.remove();
            } while (iterator2.hasNext());
            return;
        }
    }

    @Override
    protected Object filterOutboundMessage(Object object) throws Exception {
        if (object instanceof SctpMessage) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPE);
    }

    @Override
    public Association association() {
        try {
            return this.ch.association();
        } catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public boolean isActive() {
        return this.isOpen() && this.association() != null;
    }

    @Override
    protected SocketAddress localAddress0() {
        try {
            Iterator<SocketAddress> iterator2 = this.ch.getAllLocalAddresses().iterator();
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> set = this.ch.getAllLocalAddresses();
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
    protected SocketAddress remoteAddress0() {
        try {
            Iterator<SocketAddress> iterator2 = this.ch.getRemoteAddresses().iterator();
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> set = this.ch.getRemoteAddresses();
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
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.ch.bind(socketAddress);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.ch.bind(socketAddress2);
        }
        boolean bl = false;
        try {
            this.ch.connect(socketAddress);
            boolean bl2 = false;
            while (!bl2) {
                if (this.connectSelector.select(1000L) < 0) continue;
                Set<SelectionKey> set = this.connectSelector.selectedKeys();
                for (SelectionKey selectionKey : set) {
                    if (!selectionKey.isConnectable()) continue;
                    set.clear();
                    bl2 = true;
                    break;
                }
                set.clear();
            }
            bl = this.ch.finishConnect();
        } finally {
            if (!bl) {
                this.doClose();
            }
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doClose() throws Exception {
        OioSctpChannel.closeSelector("read", this.readSelector);
        OioSctpChannel.closeSelector("write", this.writeSelector);
        OioSctpChannel.closeSelector("connect", this.connectSelector);
        this.ch.close();
    }

    private static void closeSelector(String string, Selector selector) {
        try {
            selector.close();
        } catch (IOException iOException) {
            logger.warn("Failed to close a " + string + " selector.", iOException);
        }
    }

    @Override
    public ChannelFuture bindAddress(InetAddress inetAddress) {
        return this.bindAddress(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.ch.bindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else {
            this.eventLoop().execute(new Runnable(this, inetAddress, channelPromise){
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpChannel this$0;
                {
                    this.this$0 = oioSctpChannel;
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
                this.ch.unbindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else {
            this.eventLoop().execute(new Runnable(this, inetAddress, channelPromise){
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpChannel this$0;
                {
                    this.this$0 = oioSctpChannel;
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

    static void access$100(OioSctpChannel oioSctpChannel) {
        oioSctpChannel.clearReadPending();
    }

    private final class OioSctpChannelConfig
    extends DefaultSctpChannelConfig {
        final OioSctpChannel this$0;

        private OioSctpChannelConfig(OioSctpChannel oioSctpChannel, OioSctpChannel oioSctpChannel2, com.sun.nio.sctp.SctpChannel sctpChannel) {
            this.this$0 = oioSctpChannel;
            super(oioSctpChannel2, sctpChannel);
        }

        @Override
        protected void autoReadCleared() {
            OioSctpChannel.access$100(this.this$0);
        }

        OioSctpChannelConfig(OioSctpChannel oioSctpChannel, OioSctpChannel oioSctpChannel2, com.sun.nio.sctp.SctpChannel sctpChannel, 1 var4_4) {
            this(oioSctpChannel, oioSctpChannel2, sctpChannel);
        }
    }
}

