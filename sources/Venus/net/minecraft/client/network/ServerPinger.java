/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.status.IClientStatusNetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.status.client.CPingPacket;
import net.minecraft.network.status.client.CServerQueryPacket;
import net.minecraft.network.status.server.SPongPacket;
import net.minecraft.network.status.server.SServerInfoPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPinger {
    private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on('\u0000').limit(6);
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());

    public void ping(ServerData serverData, Runnable runnable) throws UnknownHostException {
        ServerAddress serverAddress = ServerAddress.fromString(serverData.serverIP);
        NetworkManager networkManager = NetworkManager.createNetworkManagerAndConnect(this.resolveViaFabricAddr(serverAddress.getIP()), serverAddress.getPort(), false);
        this.pingDestinations.add(networkManager);
        serverData.serverMOTD = new TranslationTextComponent("multiplayer.status.pinging");
        serverData.pingToServer = -1L;
        serverData.playerList = null;
        networkManager.setNetHandler(new IClientStatusNetHandler(){
            private boolean successful;
            private boolean receivedStatus;
            private long pingSentAt;
            final NetworkManager val$networkmanager;
            final ServerData val$server;
            final Runnable val$p_147224_2_;
            final ServerPinger this$0;
            {
                this.this$0 = serverPinger;
                this.val$networkmanager = networkManager;
                this.val$server = serverData;
                this.val$p_147224_2_ = runnable;
            }

            @Override
            public void handleServerInfo(SServerInfoPacket sServerInfoPacket) {
                if (this.receivedStatus) {
                    this.val$networkmanager.closeChannel(new TranslationTextComponent("multiplayer.status.unrequested"));
                } else {
                    Object object;
                    this.receivedStatus = true;
                    ServerStatusResponse serverStatusResponse = sServerInfoPacket.getResponse();
                    this.val$server.serverMOTD = serverStatusResponse.getServerDescription() != null ? serverStatusResponse.getServerDescription() : StringTextComponent.EMPTY;
                    if (serverStatusResponse.getVersion() != null) {
                        this.val$server.gameVersion = new StringTextComponent(serverStatusResponse.getVersion().getName());
                        this.val$server.version = serverStatusResponse.getVersion().getProtocol();
                    } else {
                        this.val$server.gameVersion = new TranslationTextComponent("multiplayer.status.old");
                        this.val$server.version = 0;
                    }
                    if (serverStatusResponse.getPlayers() != null) {
                        this.val$server.populationInfo = ServerPinger.func_239171_b_(serverStatusResponse.getPlayers().getOnlinePlayerCount(), serverStatusResponse.getPlayers().getMaxPlayers());
                        object = Lists.newArrayList();
                        if (ArrayUtils.isNotEmpty(serverStatusResponse.getPlayers().getPlayers())) {
                            for (GameProfile gameProfile : serverStatusResponse.getPlayers().getPlayers()) {
                                object.add(new StringTextComponent(gameProfile.getName()));
                            }
                            if (serverStatusResponse.getPlayers().getPlayers().length < serverStatusResponse.getPlayers().getOnlinePlayerCount()) {
                                object.add(new TranslationTextComponent("multiplayer.status.and_more", serverStatusResponse.getPlayers().getOnlinePlayerCount() - serverStatusResponse.getPlayers().getPlayers().length));
                            }
                            this.val$server.playerList = object;
                        }
                    } else {
                        this.val$server.populationInfo = new TranslationTextComponent("multiplayer.status.unknown").mergeStyle(TextFormatting.DARK_GRAY);
                    }
                    object = null;
                    if (serverStatusResponse.getFavicon() != null) {
                        GameProfile[] gameProfileArray = serverStatusResponse.getFavicon();
                        if (gameProfileArray.startsWith("data:image/png;base64,")) {
                            object = gameProfileArray.substring(22);
                        } else {
                            LOGGER.error("Invalid server icon (unknown format)");
                        }
                    }
                    if (!Objects.equals(object, this.val$server.getBase64EncodedIconData())) {
                        this.val$server.setBase64EncodedIconData((String)object);
                        this.val$p_147224_2_.run();
                    }
                    this.pingSentAt = Util.milliTime();
                    this.val$networkmanager.sendPacket(new CPingPacket(this.pingSentAt));
                    this.successful = true;
                }
            }

            @Override
            public void handlePong(SPongPacket sPongPacket) {
                long l = this.pingSentAt;
                long l2 = Util.milliTime();
                this.val$server.pingToServer = l2 - l;
                this.val$networkmanager.closeChannel(new TranslationTextComponent("multiplayer.status.finished"));
            }

            @Override
            public void onDisconnect(ITextComponent iTextComponent) {
                if (!this.successful) {
                    LOGGER.error("Can't ping {}: {}", (Object)this.val$server.serverIP, (Object)iTextComponent.getString());
                    this.val$server.serverMOTD = new TranslationTextComponent("multiplayer.status.cannot_connect").mergeStyle(TextFormatting.DARK_RED);
                    this.val$server.populationInfo = StringTextComponent.EMPTY;
                    this.this$0.tryCompatibilityPing(this.val$server);
                }
            }

            @Override
            public NetworkManager getNetworkManager() {
                return this.val$networkmanager;
            }
        });
        try {
            networkManager.sendPacket(new CHandshakePacket(serverAddress.getIP(), serverAddress.getPort(), ProtocolType.STATUS));
            networkManager.sendPacket(new CServerQueryPacket());
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
        }
    }

    private InetAddress resolveViaFabricAddr(String string) throws UnknownHostException {
        return InetAddress.getByName(string);
    }

    private void tryCompatibilityPing(ServerData serverData) {
        ServerAddress serverAddress = ServerAddress.fromString(serverData.serverIP);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer<Channel>(this, serverAddress, serverData){
            final ServerAddress val$serveraddress;
            final ServerData val$server;
            final ServerPinger this$0;
            {
                this.this$0 = serverPinger;
                this.val$serveraddress = serverAddress;
                this.val$server = serverData;
            }

            @Override
            protected void initChannel(Channel channel) throws Exception {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException channelException) {
                    // empty catch block
                }
                channel.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>(this){
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                    }

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    @Override
                    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        super.channelActive(channelHandlerContext);
                        ByteBuf byteBuf = Unpooled.buffer();
                        try {
                            byteBuf.writeByte(254);
                            byteBuf.writeByte(1);
                            byteBuf.writeByte(250);
                            char[] cArray = "MC|PingHost".toCharArray();
                            byteBuf.writeShort(cArray.length);
                            for (char c : cArray) {
                                byteBuf.writeChar(c);
                            }
                            byteBuf.writeShort(7 + 2 * this.this$1.val$serveraddress.getIP().length());
                            byteBuf.writeByte(127);
                            cArray = this.this$1.val$serveraddress.getIP().toCharArray();
                            byteBuf.writeShort(cArray.length);
                            for (char c : cArray) {
                                byteBuf.writeChar(c);
                            }
                            byteBuf.writeInt(this.this$1.val$serveraddress.getPort());
                            channelHandlerContext.channel().writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        } finally {
                            byteBuf.release();
                        }
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        String string;
                        String[] stringArray;
                        short s = byteBuf.readUnsignedByte();
                        if (s == 255 && "\u00a71".equals((stringArray = Iterables.toArray(PING_RESPONSE_SPLITTER.split(string = new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), StandardCharsets.UTF_16BE)), String.class))[0])) {
                            int n = MathHelper.getInt(stringArray[5], 0);
                            String string2 = stringArray[5];
                            String string3 = stringArray[5];
                            int n2 = MathHelper.getInt(stringArray[5], -1);
                            int n3 = MathHelper.getInt(stringArray[5], -1);
                            this.this$1.val$server.version = -1;
                            this.this$1.val$server.gameVersion = new StringTextComponent(string2);
                            this.this$1.val$server.serverMOTD = new StringTextComponent(string3);
                            this.this$1.val$server.populationInfo = ServerPinger.func_239171_b_(n2, n3);
                        }
                        channelHandlerContext.close();
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                        channelHandlerContext.close();
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                        this.channelRead0(channelHandlerContext, (ByteBuf)object);
                    }
                });
            }
        })).channel(NioSocketChannel.class)).connect(serverAddress.getIP(), serverAddress.getPort());
    }

    private static ITextComponent func_239171_b_(int n, int n2) {
        return new StringTextComponent(Integer.toString(n)).append(new StringTextComponent("/").mergeStyle(TextFormatting.DARK_GRAY)).appendString(Integer.toString(n2)).mergeStyle(TextFormatting.GRAY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pingPendingNetworks() {
        List<NetworkManager> list = this.pingDestinations;
        synchronized (list) {
            Iterator<NetworkManager> iterator2 = this.pingDestinations.iterator();
            while (iterator2.hasNext()) {
                NetworkManager networkManager = iterator2.next();
                if (networkManager.isChannelOpen()) {
                    networkManager.tick();
                    continue;
                }
                iterator2.remove();
                networkManager.handleDisconnection();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearPendingNetworks() {
        List<NetworkManager> list = this.pingDestinations;
        synchronized (list) {
            Iterator<NetworkManager> iterator2 = this.pingDestinations.iterator();
            while (iterator2.hasNext()) {
                NetworkManager networkManager = iterator2.next();
                if (!networkManager.isChannelOpen()) continue;
                iterator2.remove();
                networkManager.closeChannel(new TranslationTextComponent("multiplayer.status.cancelled"));
            }
        }
    }
}

