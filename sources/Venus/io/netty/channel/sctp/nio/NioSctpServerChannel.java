/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.SctpChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.channel.sctp.SctpServerChannelConfig;
import io.netty.channel.sctp.nio.NioSctpChannel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NioSctpServerChannel
extends AbstractNioMessageChannel
implements SctpServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private final SctpServerChannelConfig config = new NioSctpServerChannelConfig(this, this, this.javaChannel(), null);

    private static com.sun.nio.sctp.SctpServerChannel newSocket() {
        try {
            return com.sun.nio.sctp.SctpServerChannel.open();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a server socket.", iOException);
        }
    }

    public NioSctpServerChannel() {
        super(null, NioSctpServerChannel.newSocket(), 16);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
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
    public SctpServerChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isActive() {
        return this.isOpen() && !this.allLocalAddresses().isEmpty();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    protected com.sun.nio.sctp.SctpServerChannel javaChannel() {
        return (com.sun.nio.sctp.SctpServerChannel)super.javaChannel();
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
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.javaChannel().bind(socketAddress, this.config.getBacklog());
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        SctpChannel sctpChannel = this.javaChannel().accept();
        if (sctpChannel == null) {
            return 1;
        }
        list.add(new NioSctpChannel((Channel)this, sctpChannel));
        return 0;
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
                final NioSctpServerChannel this$0;
                {
                    this.this$0 = nioSctpServerChannel;
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
                final NioSctpServerChannel this$0;
                {
                    this.this$0 = nioSctpServerChannel;
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
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object filterOutboundMessage(Object object) throws Exception {
        throw new UnsupportedOperationException();
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
    public ChannelConfig config() {
        return this.config();
    }

    static void access$100(NioSctpServerChannel nioSctpServerChannel) {
        nioSctpServerChannel.clearReadPending();
    }

    private final class NioSctpServerChannelConfig
    extends DefaultSctpServerChannelConfig {
        final NioSctpServerChannel this$0;

        private NioSctpServerChannelConfig(NioSctpServerChannel nioSctpServerChannel, NioSctpServerChannel nioSctpServerChannel2, com.sun.nio.sctp.SctpServerChannel sctpServerChannel) {
            this.this$0 = nioSctpServerChannel;
            super(nioSctpServerChannel2, sctpServerChannel);
        }

        @Override
        protected void autoReadCleared() {
            NioSctpServerChannel.access$100(this.this$0);
        }

        NioSctpServerChannelConfig(NioSctpServerChannel nioSctpServerChannel, NioSctpServerChannel nioSctpServerChannel2, com.sun.nio.sctp.SctpServerChannel sctpServerChannel, 1 var4_4) {
            this(nioSctpServerChannel, nioSctpServerChannel2, sctpServerChannel);
        }
    }
}

