/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.bootstrap;

import com.mojang.patchy.BlockedServers;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.BootstrapConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.DefaultAddressResolverGroup;
import io.netty.util.AbstractConstant;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Bootstrap
extends AbstractBootstrap<Bootstrap, Channel> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
    private static final AddressResolverGroup<?> DEFAULT_RESOLVER = DefaultAddressResolverGroup.INSTANCE;
    private final BootstrapConfig config = new BootstrapConfig(this);
    private volatile AddressResolverGroup<SocketAddress> resolver = DEFAULT_RESOLVER;
    private volatile SocketAddress remoteAddress;

    public Bootstrap() {
    }

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.resolver = bootstrap.resolver;
        this.remoteAddress = bootstrap.remoteAddress;
    }

    public Bootstrap resolver(AddressResolverGroup<?> addressResolverGroup) {
        this.resolver = addressResolverGroup == null ? DEFAULT_RESOLVER : addressResolverGroup;
        return this;
    }

    public Bootstrap remoteAddress(SocketAddress socketAddress) {
        this.remoteAddress = socketAddress;
        return this;
    }

    public Bootstrap remoteAddress(String string, int n) {
        this.remoteAddress = InetSocketAddress.createUnresolved(string, n);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetAddress, int n) {
        this.remoteAddress = new InetSocketAddress(inetAddress, n);
        return this;
    }

    public ChannelFuture connect() {
        this.validate();
        SocketAddress socketAddress = this.remoteAddress;
        if (socketAddress == null) {
            throw new IllegalStateException("remoteAddress not set");
        }
        return this.doResolveAndConnect(socketAddress, this.config.localAddress());
    }

    public ChannelFuture connect(String string, int n) {
        return this.connect(InetSocketAddress.createUnresolved(string, n));
    }

    public ChannelFuture connect(InetAddress inetAddress, int n) {
        return this.connect(new InetSocketAddress(inetAddress, n));
    }

    public ChannelFuture connect(SocketAddress socketAddress) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.validate();
        return this.doResolveAndConnect(socketAddress, this.config.localAddress());
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.validate();
        return this.doResolveAndConnect(socketAddress, socketAddress2);
    }

    private ChannelFuture doResolveAndConnect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        ChannelFuture channelFuture = this.checkAddress(socketAddress);
        if (channelFuture != null) {
            return channelFuture;
        }
        ChannelFuture channelFuture2 = this.initAndRegister();
        Channel channel = channelFuture2.channel();
        if (channelFuture2.isDone()) {
            if (!channelFuture2.isSuccess()) {
                return channelFuture2;
            }
            return this.doResolveAndConnect0(channel, socketAddress, socketAddress2, channel.newPromise());
        }
        AbstractBootstrap.PendingRegistrationPromise pendingRegistrationPromise = new AbstractBootstrap.PendingRegistrationPromise(channel);
        channelFuture2.addListener(new ChannelFutureListener(this, pendingRegistrationPromise, channel, socketAddress, socketAddress2){
            final AbstractBootstrap.PendingRegistrationPromise val$promise;
            final Channel val$channel;
            final SocketAddress val$remoteAddress;
            final SocketAddress val$localAddress;
            final Bootstrap this$0;
            {
                this.this$0 = bootstrap;
                this.val$promise = pendingRegistrationPromise;
                this.val$channel = channel;
                this.val$remoteAddress = socketAddress;
                this.val$localAddress = socketAddress2;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable throwable = channelFuture.cause();
                if (throwable != null) {
                    this.val$promise.setFailure(throwable);
                } else {
                    this.val$promise.registered();
                    Bootstrap.access$000(this.this$0, this.val$channel, this.val$remoteAddress, this.val$localAddress, this.val$promise);
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        return pendingRegistrationPromise;
    }

    private ChannelFuture doResolveAndConnect0(Channel channel, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        try {
            EventLoop eventLoop = channel.eventLoop();
            AddressResolver<SocketAddress> addressResolver = this.resolver.getResolver(eventLoop);
            if (!addressResolver.isSupported(socketAddress) || addressResolver.isResolved(socketAddress)) {
                Bootstrap.doConnect(socketAddress, socketAddress2, channelPromise);
                return channelPromise;
            }
            Future<SocketAddress> future = addressResolver.resolve(socketAddress);
            if (future.isDone()) {
                Throwable throwable = future.cause();
                if (throwable != null) {
                    channel.close();
                    channelPromise.setFailure(throwable);
                } else {
                    Bootstrap.doConnect(future.getNow(), socketAddress2, channelPromise);
                }
                return channelPromise;
            }
            future.addListener((GenericFutureListener<Future<SocketAddress>>)new FutureListener<SocketAddress>(this, channel, channelPromise, socketAddress2){
                final Channel val$channel;
                final ChannelPromise val$promise;
                final SocketAddress val$localAddress;
                final Bootstrap this$0;
                {
                    this.this$0 = bootstrap;
                    this.val$channel = channel;
                    this.val$promise = channelPromise;
                    this.val$localAddress = socketAddress;
                }

                @Override
                public void operationComplete(Future<SocketAddress> future) throws Exception {
                    if (future.cause() != null) {
                        this.val$channel.close();
                        this.val$promise.setFailure(future.cause());
                    } else {
                        Bootstrap.access$100(future.getNow(), this.val$localAddress, this.val$promise);
                    }
                }
            });
        } catch (Throwable throwable) {
            channelPromise.tryFailure(throwable);
        }
        return channelPromise;
    }

    private static void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        Channel channel = channelPromise.channel();
        channel.eventLoop().execute(new Runnable(socketAddress2, channel, socketAddress, channelPromise){
            final SocketAddress val$localAddress;
            final Channel val$channel;
            final SocketAddress val$remoteAddress;
            final ChannelPromise val$connectPromise;
            {
                this.val$localAddress = socketAddress;
                this.val$channel = channel;
                this.val$remoteAddress = socketAddress2;
                this.val$connectPromise = channelPromise;
            }

            @Override
            public void run() {
                if (this.val$localAddress == null) {
                    this.val$channel.connect(this.val$remoteAddress, this.val$connectPromise);
                } else {
                    this.val$channel.connect(this.val$remoteAddress, this.val$localAddress, this.val$connectPromise);
                }
                this.val$connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void init(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
        channelPipeline.addLast(this.config.handler());
        Map<ChannelOption<?>, Object> map = this.options0();
        Map<AbstractConstant, Object> map2 = map;
        synchronized (map2) {
            Bootstrap.setChannelOptions(channel, map, logger);
        }
        Map<AbstractConstant, Object> map3 = map2 = this.attrs0();
        synchronized (map3) {
            for (Map.Entry<AbstractConstant, Object> entry : map2.entrySet()) {
                channel.attr((AttributeKey)entry.getKey()).set(entry.getValue());
            }
        }
    }

    @Override
    public Bootstrap validate() {
        super.validate();
        if (this.config.handler() == null) {
            throw new IllegalStateException("handler not set");
        }
        return this;
    }

    @Override
    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap(this);
        bootstrap.group = eventLoopGroup;
        return bootstrap;
    }

    public final BootstrapConfig config() {
        return this.config;
    }

    final SocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    final AddressResolverGroup<?> resolver() {
        return this.resolver;
    }

    ChannelFuture checkAddress(SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            boolean bl;
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            InetAddress inetAddress = inetSocketAddress.getAddress();
            if (inetAddress == null) {
                bl = BlockedServers.isBlockedServer(inetSocketAddress.getHostName());
            } else {
                boolean bl2 = bl = BlockedServers.isBlockedServer(inetAddress.getHostAddress()) || BlockedServers.isBlockedServer(inetAddress.getHostName());
            }
            if (bl) {
                Object c = this.channelFactory().newChannel();
                c.unsafe().closeForcibly();
                SocketException socketException = new SocketException("Network is unreachable");
                socketException.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
                return new DefaultChannelPromise((Channel)c, GlobalEventExecutor.INSTANCE).setFailure(socketException);
            }
        }
        return null;
    }

    @Override
    public AbstractBootstrapConfig config() {
        return this.config();
    }

    @Override
    public AbstractBootstrap clone() {
        return this.clone();
    }

    @Override
    public AbstractBootstrap validate() {
        return this.validate();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static ChannelFuture access$000(Bootstrap bootstrap, Channel channel, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return bootstrap.doResolveAndConnect0(channel, socketAddress, socketAddress2, channelPromise);
    }

    static void access$100(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        Bootstrap.doConnect(socketAddress, socketAddress2, channelPromise);
    }
}

