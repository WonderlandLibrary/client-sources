/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.epoll.AbstractEpollServerChannel;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollDomainSocketChannel;
import io.netty.channel.epoll.EpollServerChannelConfig;
import io.netty.channel.epoll.LinuxSocket;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.ServerDomainSocketChannel;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EpollServerDomainSocketChannel
extends AbstractEpollServerChannel
implements ServerDomainSocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollServerDomainSocketChannel.class);
    private final EpollServerChannelConfig config = new EpollServerChannelConfig(this);
    private volatile DomainSocketAddress local;

    public EpollServerDomainSocketChannel() {
        super(LinuxSocket.newSocketDomain(), false);
    }

    public EpollServerDomainSocketChannel(int n) {
        super(n);
    }

    EpollServerDomainSocketChannel(LinuxSocket linuxSocket) {
        super(linuxSocket);
    }

    EpollServerDomainSocketChannel(LinuxSocket linuxSocket, boolean bl) {
        super(linuxSocket, bl);
    }

    @Override
    protected Channel newChildChannel(int n, byte[] byArray, int n2, int n3) throws Exception {
        return new EpollDomainSocketChannel((Channel)this, new Socket(n));
    }

    @Override
    protected DomainSocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.socket.bind(socketAddress);
        this.socket.listen(this.config.getBacklog());
        this.local = (DomainSocketAddress)socketAddress;
        this.active = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doClose() throws Exception {
        try {
            super.doClose();
        } finally {
            File file;
            boolean bl;
            DomainSocketAddress domainSocketAddress = this.local;
            if (domainSocketAddress != null && !(bl = (file = new File(domainSocketAddress.path())).delete()) && logger.isDebugEnabled()) {
                logger.debug("Failed to delete a domain socket file: {}", (Object)domainSocketAddress.path());
            }
        }
    }

    @Override
    public EpollServerChannelConfig config() {
        return this.config;
    }

    @Override
    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress)super.remoteAddress();
    }

    @Override
    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress)super.localAddress();
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

    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress0();
    }

    @Override
    public EpollChannelConfig config() {
        return this.config();
    }
}

