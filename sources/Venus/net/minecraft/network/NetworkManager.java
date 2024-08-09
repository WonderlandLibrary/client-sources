/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.venusfr;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.NettyEncryptingDecoder;
import net.minecraft.network.NettyEncryptingEncoder;
import net.minecraft.network.NettyPacketDecoder;
import net.minecraft.network.NettyPacketEncoder;
import net.minecraft.network.NettyVarint21FrameDecoder;
import net.minecraft.network.NettyVarint21FrameEncoder;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.SkipableEncoderException;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import via.ViaLoadingBase;
import via.netty.handler.ViaDecoder;
import via.netty.handler.ViaEncoder;

public class NetworkManager
extends SimpleChannelInboundHandler<IPacket<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Marker NETWORK_MARKER = MarkerManager.getMarker("NETWORK");
    public static final Marker NETWORK_PACKETS_MARKER = MarkerManager.getMarker("NETWORK_PACKETS", NETWORK_MARKER);
    public static final AttributeKey<ProtocolType> PROTOCOL_ATTRIBUTE_KEY = AttributeKey.valueOf("protocol");
    public static final LazyValue<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyValue<NioEventLoopGroup>(NetworkManager::lambda$static$0);
    public static final LazyValue<EpollEventLoopGroup> CLIENT_EPOLL_EVENTLOOP = new LazyValue<EpollEventLoopGroup>(NetworkManager::lambda$static$1);
    public static final LazyValue<DefaultEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyValue<DefaultEventLoopGroup>(NetworkManager::lambda$static$2);
    private final PacketDirection direction;
    private final Queue<QueuedPacket> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
    private Channel channel;
    private SocketAddress socketAddress;
    private INetHandler packetListener;
    private ITextComponent terminationReason;
    private boolean isEncrypted;
    private boolean disconnected;
    private int field_211394_q;
    private int field_211395_r;
    private float field_211396_s;
    private float field_211397_t;
    private int ticks;
    private boolean field_211399_v;
    EventPacket receive = new EventPacket(null, EventPacket.Type.RECEIVE);
    EventPacket send = new EventPacket(null, EventPacket.Type.SEND);

    public NetworkManager(PacketDirection packetDirection) {
        this.direction = packetDirection;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.channel = channelHandlerContext.channel();
        this.socketAddress = this.channel.remoteAddress();
        try {
            this.setConnectionState(ProtocolType.HANDSHAKING);
        } catch (Throwable throwable) {
            LOGGER.fatal(throwable);
        }
    }

    public void sendPacketWithoutEvent(IPacket<?> iPacket) {
        this.sendPacketWithoutEvent(iPacket, null);
    }

    public void sendPacketWithoutEvent(IPacket<?> iPacket, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(iPacket, genericFutureListener);
        } else {
            this.outboundPacketsQueue.add(new QueuedPacket(iPacket, genericFutureListener));
        }
    }

    public void setConnectionState(ProtocolType protocolType) {
        this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).set(protocolType);
        this.channel.config().setAutoRead(true);
        LOGGER.debug("Enabled auto read");
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.closeChannel(new TranslationTextComponent("disconnect.endOfStream"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        if (throwable instanceof SkipableEncoderException) {
            LOGGER.debug("Skipping packet due to errors", throwable.getCause());
        } else {
            boolean bl = !this.field_211399_v;
            this.field_211399_v = true;
            if (this.channel.isOpen()) {
                if (throwable instanceof TimeoutException) {
                    LOGGER.debug("Timeout", throwable);
                    this.closeChannel(new TranslationTextComponent("disconnect.timeout"));
                } else {
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent("disconnect.genericReason", "Internal Exception: " + throwable);
                    if (bl) {
                        LOGGER.debug("Failed to sent packet", throwable);
                        this.sendPacket(new SDisconnectPacket(translationTextComponent), arg_0 -> this.lambda$exceptionCaught$3(translationTextComponent, arg_0));
                        this.disableAutoRead();
                    } else {
                        LOGGER.debug("Double fault", throwable);
                        this.closeChannel(translationTextComponent);
                    }
                }
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IPacket<?> iPacket) throws Exception {
        if (iPacket instanceof SUpdateBossInfoPacket) {
            SUpdateBossInfoPacket sUpdateBossInfoPacket = (SUpdateBossInfoPacket)iPacket;
            ClientUtil.updateBossInfo(sUpdateBossInfoPacket);
        }
        if (this.channel.isOpen()) {
            this.receive.setPacket(iPacket);
            venusfr.getInstance().getEventBus().post(this.receive);
            if (this.receive.isCancel()) {
                this.receive.open();
                return;
            }
            try {
                NetworkManager.processPacket(this.receive.getPacket(), this.packetListener);
            } catch (ThreadQuickExitException threadQuickExitException) {
                // empty catch block
            }
            ++this.field_211394_q;
        }
    }

    public static <T extends INetHandler> void processPacket(IPacket<T> iPacket, INetHandler iNetHandler) {
        iPacket.processPacket(iNetHandler);
    }

    public void setNetHandler(INetHandler iNetHandler) {
        Validate.notNull(iNetHandler, "packetListener", new Object[0]);
        this.packetListener = iNetHandler;
    }

    public void sendPacket(IPacket<?> iPacket) {
        this.sendPacket(iPacket, null);
    }

    public void sendPacket(IPacket<?> iPacket, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        this.send.setPacket(iPacket);
        venusfr.getInstance().getEventBus().post(this.send);
        if (this.send.isCancel()) {
            this.send.open();
            return;
        }
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(this.send.getPacket(), genericFutureListener);
        } else {
            this.outboundPacketsQueue.add(new QueuedPacket(this.send.getPacket(), genericFutureListener));
        }
    }

    private void dispatchPacket(IPacket<?> iPacket, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        ProtocolType protocolType = ProtocolType.getFromPacket(iPacket);
        ProtocolType protocolType2 = this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).get();
        ++this.field_211395_r;
        if (protocolType2 != protocolType) {
            LOGGER.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (protocolType != protocolType2) {
                this.setConnectionState(protocolType);
            }
            ChannelFuture channelFuture = this.channel.writeAndFlush(iPacket);
            if (genericFutureListener != null) {
                channelFuture.addListener(genericFutureListener);
            }
            channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            this.channel.eventLoop().execute(() -> this.lambda$dispatchPacket$4(protocolType, protocolType2, iPacket, genericFutureListener));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void flushOutboundQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            Queue<QueuedPacket> queue = this.outboundPacketsQueue;
            synchronized (queue) {
                QueuedPacket queuedPacket;
                while ((queuedPacket = this.outboundPacketsQueue.poll()) != null) {
                    this.dispatchPacket(queuedPacket.packet, queuedPacket.field_201049_b);
                }
            }
        }
    }

    public void tick() {
        this.flushOutboundQueue();
        if (this.packetListener instanceof ServerLoginNetHandler) {
            ((ServerLoginNetHandler)this.packetListener).tick();
        }
        if (this.packetListener instanceof ServerPlayNetHandler) {
            ((ServerPlayNetHandler)this.packetListener).tick();
        }
        if (this.channel != null) {
            this.channel.flush();
        }
        if (this.ticks++ % 20 == 0) {
            this.func_241877_b();
        }
    }

    protected void func_241877_b() {
        this.field_211397_t = MathHelper.lerp(0.75f, this.field_211395_r, this.field_211397_t);
        this.field_211396_s = MathHelper.lerp(0.75f, this.field_211394_q, this.field_211396_s);
        this.field_211395_r = 0;
        this.field_211394_q = 0;
    }

    public SocketAddress getRemoteAddress() {
        return this.socketAddress;
    }

    public void closeChannel(ITextComponent iTextComponent) {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
            this.terminationReason = iTextComponent;
        }
    }

    public boolean isLocalChannel() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }

    public static NetworkManager createNetworkManagerAndConnect(InetAddress inetAddress, int n, boolean bl) {
        LazyValue<MultithreadEventLoopGroup> lazyValue;
        Class clazz;
        NetworkManager networkManager = new NetworkManager(PacketDirection.CLIENTBOUND);
        if (Epoll.isAvailable() && bl) {
            clazz = EpollSocketChannel.class;
            lazyValue = CLIENT_EPOLL_EVENTLOOP;
        } else {
            clazz = NioSocketChannel.class;
            lazyValue = CLIENT_NIO_EVENTLOOP;
        }
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(lazyValue.getValue())).handler(new ChannelInitializer<Channel>(networkManager){
            final NetworkManager val$networkmanager;
            {
                this.val$networkmanager = networkManager;
            }

            @Override
            protected void initChannel(Channel channel) {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException channelException) {
                    // empty catch block
                }
                channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new NettyVarint21FrameDecoder()).addLast("decoder", (ChannelHandler)new NettyPacketDecoder(PacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new NettyVarint21FrameEncoder()).addLast("encoder", (ChannelHandler)new NettyPacketEncoder(PacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)this.val$networkmanager);
                if (channel instanceof SocketChannel) {
                    UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel, true);
                    new ProtocolPipelineImpl(userConnectionImpl);
                    if (ViaLoadingBase.getInstance().getTargetVersion().getVersion() != venusfr.getInstance().getViaMCP().getNATIVE_VERSION()) {
                        channel.pipeline().addBefore("encoder", "via-encoder", new ViaEncoder(userConnectionImpl));
                        channel.pipeline().addBefore("decoder", "via-decoder", new ViaDecoder(userConnectionImpl));
                    }
                }
            }
        })).channel(clazz)).connect(inetAddress, n).syncUninterruptibly();
        return networkManager;
    }

    public static NetworkManager provideLocalClient(SocketAddress socketAddress) {
        NetworkManager networkManager = new NetworkManager(PacketDirection.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new ChannelInitializer<Channel>(networkManager){
            final NetworkManager val$networkmanager;
            {
                this.val$networkmanager = networkManager;
            }

            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast("packet_handler", (ChannelHandler)this.val$networkmanager);
            }
        })).channel(LocalChannel.class)).connect(socketAddress).syncUninterruptibly();
        return networkManager;
    }

    public void func_244777_a(Cipher cipher, Cipher cipher2) {
        this.isEncrypted = true;
        this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(cipher));
        this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(cipher2));
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public boolean isChannelOpen() {
        return this.channel != null && this.channel.isOpen();
    }

    public boolean hasNoChannel() {
        return this.channel == null;
    }

    public INetHandler getNetHandler() {
        return this.packetListener;
    }

    @Nullable
    public ITextComponent getExitMessage() {
        return this.terminationReason;
    }

    public void disableAutoRead() {
        this.channel.config().setAutoRead(false);
    }

    public void setCompressionThreshold(int n) {
        if (n >= 0) {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionThreshold(n);
            } else {
                this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(n));
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.channel.pipeline().get("compress")).setCompressionThreshold(n);
            } else {
                this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(n));
            }
        } else {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.channel.pipeline().remove("decompress");
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.channel.pipeline().remove("compress");
            }
        }
    }

    public void handleDisconnection() {
        if (this.channel != null && !this.channel.isOpen()) {
            if (this.disconnected) {
                LOGGER.warn("handleDisconnection() called twice");
            } else {
                this.disconnected = true;
                if (this.getExitMessage() != null) {
                    this.getNetHandler().onDisconnect(this.getExitMessage());
                } else if (this.getNetHandler() != null) {
                    this.getNetHandler().onDisconnect(new TranslationTextComponent("multiplayer.disconnect.generic"));
                }
            }
        }
    }

    public float getPacketsReceived() {
        return this.field_211396_s;
    }

    public float getPacketsSent() {
        return this.field_211397_t;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.channelRead0(channelHandlerContext, (IPacket)object);
    }

    private void lambda$dispatchPacket$4(ProtocolType protocolType, ProtocolType protocolType2, IPacket iPacket, GenericFutureListener genericFutureListener) {
        if (protocolType != protocolType2) {
            this.setConnectionState(protocolType);
        }
        ChannelFuture channelFuture = this.channel.writeAndFlush(iPacket);
        if (genericFutureListener != null) {
            channelFuture.addListener(genericFutureListener);
        }
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    private void lambda$exceptionCaught$3(ITextComponent iTextComponent, Future future) throws Exception {
        this.closeChannel(iTextComponent);
    }

    private static DefaultEventLoopGroup lambda$static$2() {
        return new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(false).build());
    }

    private static EpollEventLoopGroup lambda$static$1() {
        return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(false).build());
    }

    private static NioEventLoopGroup lambda$static$0() {
        return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(false).build());
    }

    static class QueuedPacket {
        private final IPacket<?> packet;
        @Nullable
        private final GenericFutureListener<? extends Future<? super Void>> field_201049_b;

        public QueuedPacket(IPacket<?> iPacket, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
            this.packet = iPacket;
            this.field_201049_b = genericFutureListener;
        }
    }
}

