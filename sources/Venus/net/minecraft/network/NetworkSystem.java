/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.network.handshake.ClientHandshakeNetHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.network.LegacyPingHandler;
import net.minecraft.network.NettyPacketDecoder;
import net.minecraft.network.NettyPacketEncoder;
import net.minecraft.network.NettyVarint21FrameDecoder;
import net.minecraft.network.NettyVarint21FrameEncoder;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.RateLimitedNetworkManager;
import net.minecraft.network.handshake.ServerHandshakeNetHandler;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final LazyValue<NioEventLoopGroup> SERVER_NIO_EVENTLOOP = new LazyValue<NioEventLoopGroup>(NetworkSystem::lambda$static$0);
    public static final LazyValue<EpollEventLoopGroup> SERVER_EPOLL_EVENTLOOP = new LazyValue<EpollEventLoopGroup>(NetworkSystem::lambda$static$1);
    private final MinecraftServer server;
    public volatile boolean isAlive;
    private final List<ChannelFuture> endpoints = Collections.synchronizedList(Lists.newArrayList());
    private final List<NetworkManager> networkManagers = Collections.synchronizedList(Lists.newArrayList());

    public NetworkSystem(MinecraftServer minecraftServer) {
        this.server = minecraftServer;
        this.isAlive = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addEndpoint(@Nullable InetAddress inetAddress, int n) throws IOException {
        List<ChannelFuture> list = this.endpoints;
        synchronized (list) {
            LazyValue<MultithreadEventLoopGroup> lazyValue;
            Class clazz;
            if (Epoll.isAvailable() && this.server.shouldUseNativeTransport()) {
                clazz = EpollServerSocketChannel.class;
                lazyValue = SERVER_EPOLL_EVENTLOOP;
                LOGGER.info("Using epoll channel type");
            } else {
                clazz = NioServerSocketChannel.class;
                lazyValue = SERVER_NIO_EVENTLOOP;
                LOGGER.info("Using default channel type");
            }
            this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(clazz)).childHandler(new ChannelInitializer<Channel>(this){
                final NetworkSystem this$0;
                {
                    this.this$0 = networkSystem;
                }

                @Override
                protected void initChannel(Channel channel) throws Exception {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                    } catch (ChannelException channelException) {
                        // empty catch block
                    }
                    channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyPingHandler(this.this$0)).addLast("splitter", (ChannelHandler)new NettyVarint21FrameDecoder()).addLast("decoder", (ChannelHandler)new NettyPacketDecoder(PacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new NettyVarint21FrameEncoder()).addLast("encoder", (ChannelHandler)new NettyPacketEncoder(PacketDirection.CLIENTBOUND));
                    int n = this.this$0.server.func_241871_k();
                    NetworkManager networkManager = n > 0 ? new RateLimitedNetworkManager(n) : new NetworkManager(PacketDirection.SERVERBOUND);
                    this.this$0.networkManagers.add(networkManager);
                    channel.pipeline().addLast("packet_handler", (ChannelHandler)networkManager);
                    networkManager.setNetHandler(new ServerHandshakeNetHandler(this.this$0.server, networkManager));
                }
            }).group(lazyValue.getValue()).localAddress(inetAddress, n)).bind().syncUninterruptibly());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SocketAddress addLocalEndpoint() {
        ChannelFuture channelFuture;
        List<ChannelFuture> list = this.endpoints;
        synchronized (list) {
            channelFuture = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer<Channel>(this){
                final NetworkSystem this$0;
                {
                    this.this$0 = networkSystem;
                }

                @Override
                protected void initChannel(Channel channel) throws Exception {
                    NetworkManager networkManager = new NetworkManager(PacketDirection.SERVERBOUND);
                    networkManager.setNetHandler(new ClientHandshakeNetHandler(this.this$0.server, networkManager));
                    this.this$0.networkManagers.add(networkManager);
                    channel.pipeline().addLast("packet_handler", (ChannelHandler)networkManager);
                }
            }).group(SERVER_NIO_EVENTLOOP.getValue()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
            this.endpoints.add(channelFuture);
        }
        return channelFuture.channel().localAddress();
    }

    public void terminateEndpoints() {
        this.isAlive = false;
        for (ChannelFuture channelFuture : this.endpoints) {
            try {
                channelFuture.channel().close().sync();
            } catch (InterruptedException interruptedException) {
                LOGGER.error("Interrupted whilst closing channel");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void tick() {
        List<NetworkManager> list = this.networkManagers;
        synchronized (list) {
            Iterator<NetworkManager> iterator2 = this.networkManagers.iterator();
            while (iterator2.hasNext()) {
                NetworkManager networkManager = iterator2.next();
                if (networkManager.hasNoChannel()) continue;
                if (networkManager.isChannelOpen()) {
                    try {
                        networkManager.tick();
                    } catch (Exception exception) {
                        if (networkManager.isLocalChannel()) {
                            throw new ReportedException(CrashReport.makeCrashReport(exception, "Ticking memory connection"));
                        }
                        LOGGER.warn("Failed to handle packet for {}", (Object)networkManager.getRemoteAddress(), (Object)exception);
                        StringTextComponent stringTextComponent = new StringTextComponent("Internal server error");
                        networkManager.sendPacket(new SDisconnectPacket(stringTextComponent), arg_0 -> NetworkSystem.lambda$tick$2(networkManager, stringTextComponent, arg_0));
                        networkManager.disableAutoRead();
                    }
                    continue;
                }
                iterator2.remove();
                networkManager.handleDisconnection();
            }
        }
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    private static void lambda$tick$2(NetworkManager networkManager, ITextComponent iTextComponent, Future future) throws Exception {
        networkManager.closeChannel(iTextComponent);
    }

    private static EpollEventLoopGroup lambda$static$1() {
        return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(false).build());
    }

    private static NioEventLoopGroup lambda$static$0() {
        return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(false).build());
    }
}

