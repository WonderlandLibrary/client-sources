/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.kqueue.AbstractKQueueServerChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueDomainSocketChannel;
import io.netty.channel.kqueue.KQueueServerChannelConfig;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.ServerDomainSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class KQueueServerDomainSocketChannel
extends AbstractKQueueServerChannel
implements ServerDomainSocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(KQueueServerDomainSocketChannel.class);
    private final KQueueServerChannelConfig config = new KQueueServerChannelConfig(this);
    private volatile DomainSocketAddress local;

    public KQueueServerDomainSocketChannel() {
        super(BsdSocket.newSocketDomain(), false);
    }

    public KQueueServerDomainSocketChannel(int n) {
        this(new BsdSocket(n), false);
    }

    KQueueServerDomainSocketChannel(BsdSocket bsdSocket, boolean bl) {
        super(bsdSocket, bl);
    }

    @Override
    protected Channel newChildChannel(int n, byte[] byArray, int n2, int n3) throws Exception {
        return new KQueueDomainSocketChannel((Channel)this, new BsdSocket(n));
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
    public KQueueServerChannelConfig config() {
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
    public KQueueChannelConfig config() {
        return this.config();
    }
}

