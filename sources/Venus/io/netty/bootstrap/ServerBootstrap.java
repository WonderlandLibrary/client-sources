/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.ServerBootstrapConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AbstractConstant;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ServerBootstrap
extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap();
    private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap();
    private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);
    private volatile EventLoopGroup childGroup;
    private volatile ChannelHandler childHandler;

    public ServerBootstrap() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ServerBootstrap(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
        this.childGroup = serverBootstrap.childGroup;
        this.childHandler = serverBootstrap.childHandler;
        Map<AbstractConstant, Object> map = serverBootstrap.childOptions;
        synchronized (map) {
            this.childOptions.putAll(serverBootstrap.childOptions);
        }
        map = serverBootstrap.childAttrs;
        synchronized (map) {
            this.childAttrs.putAll(serverBootstrap.childAttrs);
        }
    }

    @Override
    public ServerBootstrap group(EventLoopGroup eventLoopGroup) {
        return this.group(eventLoopGroup, eventLoopGroup);
    }

    public ServerBootstrap group(EventLoopGroup eventLoopGroup, EventLoopGroup eventLoopGroup2) {
        super.group(eventLoopGroup);
        if (eventLoopGroup2 == null) {
            throw new NullPointerException("childGroup");
        }
        if (this.childGroup != null) {
            throw new IllegalStateException("childGroup set already");
        }
        this.childGroup = eventLoopGroup2;
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> ServerBootstrap childOption(ChannelOption<T> channelOption, T t) {
        if (channelOption == null) {
            throw new NullPointerException("childOption");
        }
        if (t == null) {
            Map<ChannelOption<?>, Object> map = this.childOptions;
            synchronized (map) {
                this.childOptions.remove(channelOption);
            }
        }
        Map<ChannelOption<?>, Object> map = this.childOptions;
        synchronized (map) {
            this.childOptions.put(channelOption, t);
        }
        return this;
    }

    public <T> ServerBootstrap childAttr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey == null) {
            throw new NullPointerException("childKey");
        }
        if (t == null) {
            this.childAttrs.remove(attributeKey);
        } else {
            this.childAttrs.put(attributeKey, t);
        }
        return this;
    }

    public ServerBootstrap childHandler(ChannelHandler channelHandler) {
        if (channelHandler == null) {
            throw new NullPointerException("childHandler");
        }
        this.childHandler = channelHandler;
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void init(Channel channel) throws Exception {
        Map.Entry[] entryArray;
        Map.Entry[] entryArray2;
        Map<ChannelOption<?>, Object> map = this.options0();
        Map<AbstractConstant, Object> map2 = map;
        synchronized (map2) {
            ServerBootstrap.setChannelOptions(channel, map, logger);
        }
        map2 = this.attrs0();
        Object object2 = map2;
        synchronized (object2) {
            for (Map.Entry<AbstractConstant, Object> object3 : map2.entrySet()) {
                entryArray2 = (Map.Entry[])object3.getKey();
                channel.attr(entryArray2).set(object3.getValue());
            }
        }
        object2 = channel.pipeline();
        EventLoopGroup eventLoopGroup = this.childGroup;
        ChannelHandler channelHandler = this.childHandler;
        Map<AbstractConstant, Object> map3 = this.childOptions;
        synchronized (map3) {
            entryArray2 = this.childOptions.entrySet().toArray(ServerBootstrap.newOptionArray(this.childOptions.size()));
        }
        map3 = this.childAttrs;
        synchronized (map3) {
            entryArray = this.childAttrs.entrySet().toArray(ServerBootstrap.newAttrArray(this.childAttrs.size()));
        }
        object2.addLast(new ChannelInitializer<Channel>(this, eventLoopGroup, channelHandler, entryArray2, entryArray){
            final EventLoopGroup val$currentChildGroup;
            final ChannelHandler val$currentChildHandler;
            final Map.Entry[] val$currentChildOptions;
            final Map.Entry[] val$currentChildAttrs;
            final ServerBootstrap this$0;
            {
                this.this$0 = serverBootstrap;
                this.val$currentChildGroup = eventLoopGroup;
                this.val$currentChildHandler = channelHandler;
                this.val$currentChildOptions = entryArray;
                this.val$currentChildAttrs = entryArray2;
            }

            @Override
            public void initChannel(Channel channel) throws Exception {
                ChannelPipeline channelPipeline = channel.pipeline();
                ChannelHandler channelHandler = ServerBootstrap.access$000(this.this$0).handler();
                if (channelHandler != null) {
                    channelPipeline.addLast(channelHandler);
                }
                channel.eventLoop().execute(new Runnable(this, channelPipeline, channel){
                    final ChannelPipeline val$pipeline;
                    final Channel val$ch;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$pipeline = channelPipeline;
                        this.val$ch = channel;
                    }

                    @Override
                    public void run() {
                        this.val$pipeline.addLast(new ServerBootstrapAcceptor(this.val$ch, this.this$1.val$currentChildGroup, this.this$1.val$currentChildHandler, this.this$1.val$currentChildOptions, this.this$1.val$currentChildAttrs));
                    }
                });
            }
        });
    }

    @Override
    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler == null) {
            throw new IllegalStateException("childHandler not set");
        }
        if (this.childGroup == null) {
            logger.warn("childGroup is not set. Using parentGroup instead.");
            this.childGroup = this.config.group();
        }
        return this;
    }

    private static Map.Entry<AttributeKey<?>, Object>[] newAttrArray(int n) {
        return new Map.Entry[n];
    }

    private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(int n) {
        return new Map.Entry[n];
    }

    @Override
    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    @Deprecated
    public EventLoopGroup childGroup() {
        return this.childGroup;
    }

    final ChannelHandler childHandler() {
        return this.childHandler;
    }

    final Map<ChannelOption<?>, Object> childOptions() {
        return ServerBootstrap.copiedMap(this.childOptions);
    }

    final Map<AttributeKey<?>, Object> childAttrs() {
        return ServerBootstrap.copiedMap(this.childAttrs);
    }

    public final ServerBootstrapConfig config() {
        return this.config;
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
    public AbstractBootstrap group(EventLoopGroup eventLoopGroup) {
        return this.group(eventLoopGroup);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static ServerBootstrapConfig access$000(ServerBootstrap serverBootstrap) {
        return serverBootstrap.config;
    }

    static InternalLogger access$100() {
        return logger;
    }

    private static class ServerBootstrapAcceptor
    extends ChannelInboundHandlerAdapter {
        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
        private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
        private final Runnable enableAutoReadTask;

        ServerBootstrapAcceptor(Channel channel, EventLoopGroup eventLoopGroup, ChannelHandler channelHandler, Map.Entry<ChannelOption<?>, Object>[] entryArray, Map.Entry<AttributeKey<?>, Object>[] entryArray2) {
            this.childGroup = eventLoopGroup;
            this.childHandler = channelHandler;
            this.childOptions = entryArray;
            this.childAttrs = entryArray2;
            this.enableAutoReadTask = new Runnable(this, channel){
                final Channel val$channel;
                final ServerBootstrapAcceptor this$0;
                {
                    this.this$0 = serverBootstrapAcceptor;
                    this.val$channel = channel;
                }

                @Override
                public void run() {
                    this.val$channel.config().setAutoRead(true);
                }
            };
        }

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) {
            Channel channel = (Channel)object;
            channel.pipeline().addLast(this.childHandler);
            AbstractBootstrap.setChannelOptions(channel, this.childOptions, ServerBootstrap.access$100());
            for (Map.Entry<AttributeKey<?>, Object> entry : this.childAttrs) {
                channel.attr(entry.getKey()).set(entry.getValue());
            }
            try {
                this.childGroup.register(channel).addListener(new ChannelFutureListener(this, channel){
                    final Channel val$child;
                    final ServerBootstrapAcceptor this$0;
                    {
                        this.this$0 = serverBootstrapAcceptor;
                        this.val$child = channel;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            ServerBootstrapAcceptor.access$200(this.val$child, channelFuture.cause());
                        }
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            } catch (Throwable throwable) {
                ServerBootstrapAcceptor.forceClose(channel, throwable);
            }
        }

        private static void forceClose(Channel channel, Throwable throwable) {
            channel.unsafe().closeForcibly();
            ServerBootstrap.access$100().warn("Failed to register an accepted channel: {}", (Object)channel, (Object)throwable);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
            ChannelConfig channelConfig = channelHandlerContext.channel().config();
            if (channelConfig.isAutoRead()) {
                channelConfig.setAutoRead(false);
                channelHandlerContext.channel().eventLoop().schedule(this.enableAutoReadTask, 1L, TimeUnit.SECONDS);
            }
            channelHandlerContext.fireExceptionCaught(throwable);
        }

        static void access$200(Channel channel, Throwable throwable) {
            ServerBootstrapAcceptor.forceClose(channel, throwable);
        }
    }
}

