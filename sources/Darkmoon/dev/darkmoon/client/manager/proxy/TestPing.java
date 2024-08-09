package dev.darkmoon.client.manager.proxy;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class TestPing {
    public String state = "";

    private long pingSentAt;
    private NetworkManager pingDestination = null;
    private Proxy proxy;
    private static final ThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());

    public void run(String ip, int port, Proxy proxy) {
        this.proxy = proxy;
        TestPing.EXECUTOR.submit(() -> ping(ip, port));
    }

    private void ping(String ip, int port) {
        state = "Соединяемся с " + ip + "...";
        NetworkManager networkManager;
        try {
            networkManager = createTestClientConnection(InetAddress.getByName(ip), port);
        } catch (UnknownHostException e) {
            state = ChatFormatting.RED + "Не удалось подключиться к прокси";
            return;
        } catch (Exception e) {
            state = ChatFormatting.RED + "Не удалось установить соединение с прокси-сервером";
            return;
        }
        pingDestination = networkManager;
        networkManager.setNetHandler(new INetHandlerStatusClient() {
            private boolean successful;

            @Override
            public void onDisconnect(ITextComponent reason) {
                pingDestination = null;
                if (!this.successful) {
                    state = ChatFormatting.RED + "Не удалось установить соединение с " + ip;
                }
            }

            @Override
            public void handleServerInfo(SPacketServerInfo packetIn) {
                pingSentAt = System.currentTimeMillis();
                networkManager.sendPacket(new CPacketPing(pingSentAt));
            }

            @Override
            public void handlePong(SPacketPong packetIn) {
                successful = true;
                pingDestination = null;
                long pingToServer = System.currentTimeMillis() - pingSentAt;
                state = "Пинг: " + pingToServer;
                networkManager.closeChannel(new TextComponentString("Finished"));
            }
        });

        try {
            networkManager.sendPacket(new C00Handshake(ip, port, EnumConnectionState.STATUS));
            networkManager.sendPacket(new CPacketServerQuery());
        } catch (Throwable throwable) {
            state = ChatFormatting.RED + "Не удалось установить соединение с " + ip;
        }
    }


    private NetworkManager createTestClientConnection(InetAddress address, int port) {
        final NetworkManager clientConnection = new NetworkManager(EnumPacketDirection.CLIENTBOUND);

        (new Bootstrap()).group(NetworkManager.CLIENT_NIO_EVENTLOOP.getValue()).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException ignored) {
                }

                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30))
                        .addLast("splitter", new NettyVarint21FrameDecoder())
                        .addLast("decoder", new NettyPacketDecoder(EnumPacketDirection.CLIENTBOUND))
                        .addLast("prepender", new NettyVarint21FrameEncoder())
                        .addLast("encoder", new NettyPacketEncoder(EnumPacketDirection.SERVERBOUND))
                        .addLast("packet_handler", clientConnection);

                if (proxy.type == Proxy.ProxyType.SOCKS5) {
                    channel.pipeline().addFirst(new Socks5ProxyHandler(new InetSocketAddress(proxy.getIp(), proxy.getPort()), proxy.username.isEmpty() ? null : proxy.username, proxy.password.isEmpty() ? null : proxy.password));
                } else {
                    channel.pipeline().addFirst(new Socks4ProxyHandler(new InetSocketAddress(proxy.getIp(), proxy.getPort()), proxy.username.isEmpty() ? null : proxy.username));
                }
            }
        }).channel(NioSocketChannel.class).connect(address, port).syncUninterruptibly();
        return clientConnection;
    }

    public void pingPendingNetworks() {
        if (pingDestination != null) {
            if (pingDestination.isChannelOpen()) {
                pingDestination.processReceivedPackets();
            } else {
                pingDestination.handleDisconnection();
            }
        }
    }
}
