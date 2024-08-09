/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.FailedChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.util.AbstractConstant;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
implements Cloneable {
    volatile EventLoopGroup group;
    private volatile ChannelFactory<? extends C> channelFactory;
    private volatile SocketAddress localAddress;
    private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();
    private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
    private volatile ChannelHandler handler;

    AbstractBootstrap() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    AbstractBootstrap(AbstractBootstrap<B, C> abstractBootstrap) {
        this.group = abstractBootstrap.group;
        this.channelFactory = abstractBootstrap.channelFactory;
        this.handler = abstractBootstrap.handler;
        this.localAddress = abstractBootstrap.localAddress;
        Map<AbstractConstant, Object> map = abstractBootstrap.options;
        synchronized (map) {
            this.options.putAll(abstractBootstrap.options);
        }
        map = abstractBootstrap.attrs;
        synchronized (map) {
            this.attrs.putAll(abstractBootstrap.attrs);
        }
    }

    public B group(EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup == null) {
            throw new NullPointerException("group");
        }
        if (this.group != null) {
            throw new IllegalStateException("group set already");
        }
        this.group = eventLoopGroup;
        return this.self();
    }

    private B self() {
        return (B)this;
    }

    public B channel(Class<? extends C> clazz) {
        if (clazz == null) {
            throw new NullPointerException("channelClass");
        }
        return this.channelFactory((io.netty.channel.ChannelFactory<? extends C>)new ReflectiveChannelFactory<C>(clazz));
    }

    @Deprecated
    public B channelFactory(ChannelFactory<? extends C> channelFactory) {
        if (channelFactory == null) {
            throw new NullPointerException("channelFactory");
        }
        if (this.channelFactory != null) {
            throw new IllegalStateException("channelFactory set already");
        }
        this.channelFactory = channelFactory;
        return this.self();
    }

    public B channelFactory(io.netty.channel.ChannelFactory<? extends C> channelFactory) {
        return this.channelFactory((ChannelFactory<? extends C>)channelFactory);
    }

    public B localAddress(SocketAddress socketAddress) {
        this.localAddress = socketAddress;
        return this.self();
    }

    public B localAddress(int n) {
        return this.localAddress(new InetSocketAddress(n));
    }

    public B localAddress(String string, int n) {
        return this.localAddress(SocketUtils.socketAddress(string, n));
    }

    public B localAddress(InetAddress inetAddress, int n) {
        return this.localAddress(new InetSocketAddress(inetAddress, n));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> B option(ChannelOption<T> channelOption, T t) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        if (t == null) {
            Map<ChannelOption<?>, Object> map = this.options;
            synchronized (map) {
                this.options.remove(channelOption);
            }
        }
        Map<ChannelOption<?>, Object> map = this.options;
        synchronized (map) {
            this.options.put(channelOption, t);
        }
        return this.self();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> B attr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        if (t == null) {
            Map<AttributeKey<?>, Object> map = this.attrs;
            synchronized (map) {
                this.attrs.remove(attributeKey);
            }
        }
        Map<AttributeKey<?>, Object> map = this.attrs;
        synchronized (map) {
            this.attrs.put(attributeKey, t);
        }
        return this.self();
    }

    public B validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        }
        if (this.channelFactory == null) {
            throw new IllegalStateException("channel or channelFactory not set");
        }
        return this.self();
    }

    public abstract B clone();

    public ChannelFuture register() {
        this.validate();
        return this.initAndRegister();
    }

    public ChannelFuture bind() {
        this.validate();
        SocketAddress socketAddress = this.localAddress;
        if (socketAddress == null) {
            throw new IllegalStateException("localAddress not set");
        }
        return this.doBind(socketAddress);
    }

    public ChannelFuture bind(int n) {
        return this.bind(new InetSocketAddress(n));
    }

    public ChannelFuture bind(String string, int n) {
        return this.bind(SocketUtils.socketAddress(string, n));
    }

    public ChannelFuture bind(InetAddress inetAddress, int n) {
        return this.bind(new InetSocketAddress(inetAddress, n));
    }

    public ChannelFuture bind(SocketAddress socketAddress) {
        this.validate();
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        }
        return this.doBind(socketAddress);
    }

    private ChannelFuture doBind(SocketAddress socketAddress) {
        ChannelFuture channelFuture = this.initAndRegister();
        Channel channel = channelFuture.channel();
        if (channelFuture.cause() != null) {
            return channelFuture;
        }
        if (channelFuture.isDone()) {
            ChannelPromise channelPromise = channel.newPromise();
            AbstractBootstrap.doBind0(channelFuture, channel, socketAddress, channelPromise);
            return channelPromise;
        }
        PendingRegistrationPromise pendingRegistrationPromise = new PendingRegistrationPromise(channel);
        channelFuture.addListener(new ChannelFutureListener(this, pendingRegistrationPromise, channelFuture, channel, socketAddress){
            final PendingRegistrationPromise val$promise;
            final ChannelFuture val$regFuture;
            final Channel val$channel;
            final SocketAddress val$localAddress;
            final AbstractBootstrap this$0;
            {
                this.this$0 = abstractBootstrap;
                this.val$promise = pendingRegistrationPromise;
                this.val$regFuture = channelFuture;
                this.val$channel = channel;
                this.val$localAddress = socketAddress;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable throwable = channelFuture.cause();
                if (throwable != null) {
                    this.val$promise.setFailure(throwable);
                } else {
                    this.val$promise.registered();
                    AbstractBootstrap.access$000(this.val$regFuture, this.val$channel, this.val$localAddress, this.val$promise);
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        return pendingRegistrationPromise;
    }

    final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = (Channel)this.channelFactory.newChannel();
            this.init(channel);
        } catch (Throwable throwable) {
            if (channel != null) {
                channel.unsafe().closeForcibly();
                return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(throwable);
            }
            return new DefaultChannelPromise(new FailedChannel(), GlobalEventExecutor.INSTANCE).setFailure(throwable);
        }
        ChannelFuture channelFuture = this.config().group().register(channel);
        if (channelFuture.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            } else {
                channel.unsafe().closeForcibly();
            }
        }
        return channelFuture;
    }

    abstract void init(Channel var1) throws Exception;

    private static void doBind0(ChannelFuture channelFuture, Channel channel, SocketAddress socketAddress, ChannelPromise channelPromise) {
        channel.eventLoop().execute(new Runnable(channelFuture, channel, socketAddress, channelPromise){
            final ChannelFuture val$regFuture;
            final Channel val$channel;
            final SocketAddress val$localAddress;
            final ChannelPromise val$promise;
            {
                this.val$regFuture = channelFuture;
                this.val$channel = channel;
                this.val$localAddress = socketAddress;
                this.val$promise = channelPromise;
            }

            @Override
            public void run() {
                if (this.val$regFuture.isSuccess()) {
                    this.val$channel.bind(this.val$localAddress, this.val$promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    this.val$promise.setFailure(this.val$regFuture.cause());
                }
            }
        });
    }

    public B handler(ChannelHandler channelHandler) {
        if (channelHandler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = channelHandler;
        return this.self();
    }

    @Deprecated
    public final EventLoopGroup group() {
        return this.group;
    }

    public abstract AbstractBootstrapConfig<B, C> config();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static <K, V> Map<K, V> copiedMap(Map<K, V> map) {
        LinkedHashMap<K, V> linkedHashMap;
        Map<K, V> map2 = map;
        synchronized (map2) {
            if (map.isEmpty()) {
                return Collections.emptyMap();
            }
            linkedHashMap = new LinkedHashMap<K, V>(map);
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    final Map<ChannelOption<?>, Object> options0() {
        return this.options;
    }

    final Map<AttributeKey<?>, Object> attrs0() {
        return this.attrs;
    }

    final SocketAddress localAddress() {
        return this.localAddress;
    }

    final ChannelFactory<? extends C> channelFactory() {
        return this.channelFactory;
    }

    final ChannelHandler handler() {
        return this.handler;
    }

    final Map<ChannelOption<?>, Object> options() {
        return AbstractBootstrap.copiedMap(this.options);
    }

    final Map<AttributeKey<?>, Object> attrs() {
        return AbstractBootstrap.copiedMap(this.attrs);
    }

    static void setChannelOptions(Channel channel, Map<ChannelOption<?>, Object> map, InternalLogger internalLogger) {
        for (Map.Entry<ChannelOption<?>, Object> entry : map.entrySet()) {
            AbstractBootstrap.setChannelOption(channel, entry.getKey(), entry.getValue(), internalLogger);
        }
    }

    static void setChannelOptions(Channel channel, Map.Entry<ChannelOption<?>, Object>[] entryArray, InternalLogger internalLogger) {
        for (Map.Entry<ChannelOption<?>, Object> entry : entryArray) {
            AbstractBootstrap.setChannelOption(channel, entry.getKey(), entry.getValue(), internalLogger);
        }
    }

    private static void setChannelOption(Channel channel, ChannelOption<?> channelOption, Object object, InternalLogger internalLogger) {
        try {
            if (!channel.config().setOption(channelOption, object)) {
                internalLogger.warn("Unknown channel option '{}' for channel '{}'", (Object)channelOption, (Object)channel);
            }
        } catch (Throwable throwable) {
            internalLogger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", channelOption, object, channel, throwable);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append('(').append(this.config()).append(')');
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static void access$000(ChannelFuture channelFuture, Channel channel, SocketAddress socketAddress, ChannelPromise channelPromise) {
        AbstractBootstrap.doBind0(channelFuture, channel, socketAddress, channelPromise);
    }

    static final class PendingRegistrationPromise
    extends DefaultChannelPromise {
        private volatile boolean registered;

        PendingRegistrationPromise(Channel channel) {
            super(channel);
        }

        void registered() {
            this.registered = true;
        }

        @Override
        protected EventExecutor executor() {
            if (this.registered) {
                return super.executor();
            }
            return GlobalEventExecutor.INSTANCE;
        }
    }
}

