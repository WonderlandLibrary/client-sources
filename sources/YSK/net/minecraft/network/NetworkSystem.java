package net.minecraft.network;

import io.netty.channel.nio.*;
import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import io.netty.bootstrap.*;
import net.minecraft.client.network.*;
import io.netty.channel.local.*;
import com.google.common.collect.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.network.play.server.*;
import io.netty.util.concurrent.*;
import java.util.*;
import java.net.*;
import io.netty.channel.epoll.*;
import io.netty.channel.socket.nio.*;
import io.netty.channel.*;
import io.netty.handler.timeout.*;
import net.minecraft.util.*;
import net.minecraft.server.network.*;
import java.io.*;

public class NetworkSystem
{
    public static final LazyLoadBase<NioEventLoopGroup> eventLoops;
    private static final Logger logger;
    public boolean isAlive;
    private static final String[] I;
    private final List<NetworkManager> networkManagers;
    private final MinecraftServer mcServer;
    private final List<ChannelFuture> endpoints;
    public static final LazyLoadBase<EpollEventLoopGroup> field_181141_b;
    public static final LazyLoadBase<LocalEventLoopGroup> SERVER_LOCAL_EVENTLOOP;
    
    public MinecraftServer getServer() {
        return this.mcServer;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        eventLoops = new LazyLoadBase<NioEventLoopGroup>() {
            private static final String[] I;
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u000f003\u0013a\u0006!5\u001c$'d\u000e%ava#", "AUDGj");
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected NioEventLoopGroup load() {
                return new NioEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkSystem$1.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
            }
            
            @Override
            protected Object load() {
                return this.load();
            }
        };
        field_181141_b = new LazyLoadBase<EpollEventLoopGroup>() {
            private static final String[] I;
            
            static {
                I();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected Object load() {
                return this.load();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("7\u0015\u0006\u0016\bY5\u0002\r\u001d\u0015P!\u0007\u0003\u000f\u0015\u0000B86PQG\u0015", "yprbq");
            }
            
            @Override
            protected EpollEventLoopGroup load() {
                return new EpollEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkSystem$2.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
            }
        };
        SERVER_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
            private static final String[] I;
            
            @Override
            protected Object load() {
                return this.load();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
            
            @Override
            protected LocalEventLoopGroup load() {
                return new LocalEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkSystem$3.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u000b/\u0018\u001b5e\u0006\u0003\f-)j?\n>3/\u001eO\u0005\njOJ(", "EJloL");
            }
        };
    }
    
    static MinecraftServer access$1(final NetworkSystem networkSystem) {
        return networkSystem.mcServer;
    }
    
    public SocketAddress addLocalEndpoint() {
        final ChannelFuture syncUninterruptibly;
        synchronized (this.endpoints) {
            syncUninterruptibly = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>(this) {
                private static final String[] I;
                final NetworkSystem this$0;
                
                static {
                    I();
                }
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I(" '\u000e\u001b\u0003$\u0019\u0005\u0011\b4*\b\u0002", "PFmpf");
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                protected void initChannel(final Channel channel) throws Exception {
                    final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                    networkManager.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$1(this.this$0), networkManager));
                    NetworkSystem.access$0(this.this$0).add(networkManager);
                    channel.pipeline().addLast(NetworkSystem$5.I["".length()], (ChannelHandler)networkManager);
                }
            }).group((EventLoopGroup)NetworkSystem.eventLoops.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
            this.endpoints.add(syncUninterruptibly);
            // monitorexit(this.endpoints)
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        return syncUninterruptibly.channel().localAddress();
    }
    
    public NetworkSystem(final MinecraftServer mcServer) {
        this.endpoints = Collections.synchronizedList((List<ChannelFuture>)Lists.newArrayList());
        this.networkManagers = Collections.synchronizedList((List<NetworkManager>)Lists.newArrayList());
        this.mcServer = mcServer;
        this.isAlive = (" ".length() != 0);
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x41])["".length()] = I("-\u0018>)%X\u000e'(.\u0014K4/#\u0016\u00052+b\f\u0012'\"", "xkWGB");
        NetworkSystem.I[" ".length()] = I("\u0012) $\u001fg>,,\u001926=j\u001b/;'$\u001d+z=3\b\"", "GZIJx");
        NetworkSystem.I["  ".length()] = I("\u0007\u0014>\u0017\u001d<\u000f:\u0006\n*Z=\u001a\u0006\"\t>R\f\"\u00159\u001b\u0001)Z)\u001a\u000e \u0014/\u001e", "NzJro");
        NetworkSystem.I["   ".length()] = I("\f\u0004-\u0012,6\nn\u0014 5\u0002<\u0000e;\u0002 \u0017 ;\u0019'\u0016+", "XmNyE");
        NetworkSystem.I[0x44 ^ 0x40] = I("\u0006/$!!<!g)'<(\")<;))", "RFGJH");
        NetworkSystem.I[0xB6 ^ 0xB3] = I("\u0005\u0018<\r!%\u0003;\f*", "FwRcD");
        NetworkSystem.I[0x76 ^ 0x70] = I("\u000e,:)*,m'*o ,=!#-m#$,#('e)'?s", "HMSEO");
        NetworkSystem.I[0x84 ^ 0x83] = I("\u00054%\f(\";=I))('\f(l?#\u001b5>", "LZQiZ");
    }
    
    public void networkTick() {
        synchronized (this.networkManagers) {
            final Iterator<NetworkManager> iterator = this.networkManagers.iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkManager networkManager = iterator.next();
                if (!networkManager.hasNoChannel()) {
                    if (!networkManager.isChannelOpen()) {
                        iterator.remove();
                        networkManager.checkDisconnected();
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        try {
                            networkManager.processReceivedPackets();
                            "".length();
                            if (3 <= 1) {
                                throw null;
                            }
                            continue;
                        }
                        catch (Exception ex) {
                            if (networkManager.isLocalChannel()) {
                                final CrashReport crashReport = CrashReport.makeCrashReport(ex, NetworkSystem.I["   ".length()]);
                                crashReport.makeCategory(NetworkSystem.I[0x49 ^ 0x4D]).addCrashSectionCallable(NetworkSystem.I[0x9C ^ 0x99], new Callable<String>(this, networkManager) {
                                    private final NetworkManager val$networkmanager;
                                    final NetworkSystem this$0;
                                    
                                    @Override
                                    public String call() throws Exception {
                                        return this.val$networkmanager.toString();
                                    }
                                    
                                    @Override
                                    public Object call() throws Exception {
                                        return this.call();
                                    }
                                    
                                    private static String I(final String s, final String s2) {
                                        final StringBuilder sb = new StringBuilder();
                                        final char[] charArray = s2.toCharArray();
                                        int length = "".length();
                                        final char[] charArray2 = s.toCharArray();
                                        final int length2 = charArray2.length;
                                        int i = "".length();
                                        while (i < length2) {
                                            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                                            ++length;
                                            ++i;
                                            "".length();
                                            if (0 < 0) {
                                                throw null;
                                            }
                                        }
                                        return sb.toString();
                                    }
                                });
                                throw new ReportedException(crashReport);
                            }
                            NetworkSystem.logger.warn(NetworkSystem.I[0x91 ^ 0x97] + networkManager.getRemoteAddress(), (Throwable)ex);
                            final ChatComponentText chatComponentText = new ChatComponentText(NetworkSystem.I[0x18 ^ 0x1F]);
                            networkManager.sendPacket(new S40PacketDisconnect(chatComponentText), (GenericFutureListener<? extends Future<? super Void>>)new GenericFutureListener<Future<? super Void>>(this, networkManager, chatComponentText) {
                                final NetworkSystem this$0;
                                private final NetworkManager val$networkmanager;
                                private final ChatComponentText val$chatcomponenttext;
                                
                                public void operationComplete(final Future<? super Void> future) throws Exception {
                                    this.val$networkmanager.closeChannel(this.val$chatcomponenttext);
                                }
                                
                                private static String I(final String s, final String s2) {
                                    final StringBuilder sb = new StringBuilder();
                                    final char[] charArray = s2.toCharArray();
                                    int length = "".length();
                                    final char[] charArray2 = s.toCharArray();
                                    final int length2 = charArray2.length;
                                    int i = "".length();
                                    while (i < length2) {
                                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                                        ++length;
                                        ++i;
                                        "".length();
                                        if (true != true) {
                                            throw null;
                                        }
                                    }
                                    return sb.toString();
                                }
                            }, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener["".length()]);
                            networkManager.disableAutoRead();
                        }
                    }
                }
            }
            // monitorexit(this.networkManagers)
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void addLanEndpoint(final InetAddress inetAddress, final int n) throws IOException {
        synchronized (this.endpoints) {
            Serializable s;
            Object o;
            if (Epoll.isAvailable() && this.mcServer.func_181035_ah()) {
                s = EpollServerSocketChannel.class;
                o = NetworkSystem.field_181141_b;
                NetworkSystem.logger.info(NetworkSystem.I["".length()]);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                s = NioServerSocketChannel.class;
                o = NetworkSystem.eventLoops;
                NetworkSystem.logger.info(NetworkSystem.I[" ".length()]);
            }
            this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)s)).childHandler((ChannelHandler)new ChannelInitializer<Channel>(this) {
                final NetworkSystem this$0;
                private static final String[] I;
                
                private static void I() {
                    (I = new String[0x77 ^ 0x70])["".length()] = I("1?\u0003,\"0\"", "EVnIM");
                    NetworkSystem$4.I[" ".length()] = I("\u0005\f2\u0012\u001b\u00106$\u0006\u001d\u001b\u0010", "iiUsx");
                    NetworkSystem$4.I["  ".length()] = I("%= :9\"(>", "VMLSM");
                    NetworkSystem$4.I["   ".length()] = I("\u001e\u001c\u0007\u0019\"\u001f\u000b", "zydvF");
                    NetworkSystem$4.I[0x42 ^ 0x46] = I("?6631! 61", "ODSCT");
                    NetworkSystem$4.I[0xD ^ 0x8] = I(" ?2<6 #", "EQQSR");
                    NetworkSystem$4.I[0x31 ^ 0x37] = I("\u0017\u0005\"$\u000f\u0013;).\u0004\u0003\b$=", "gdAOj");
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                static {
                    I();
                }
                
                protected void initChannel(final Channel channel) throws Exception {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)(boolean)(" ".length() != 0));
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    catch (ChannelException ex) {}
                    channel.pipeline().addLast(NetworkSystem$4.I["".length()], (ChannelHandler)new ReadTimeoutHandler(0x5F ^ 0x41)).addLast(NetworkSystem$4.I[" ".length()], (ChannelHandler)new PingResponseHandler(this.this$0)).addLast(NetworkSystem$4.I["  ".length()], (ChannelHandler)new MessageDeserializer2()).addLast(NetworkSystem$4.I["   ".length()], (ChannelHandler)new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast(NetworkSystem$4.I[0x3F ^ 0x3B], (ChannelHandler)new MessageSerializer2()).addLast(NetworkSystem$4.I[0x68 ^ 0x6D], (ChannelHandler)new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
                    final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                    NetworkSystem.access$0(this.this$0).add(networkManager);
                    channel.pipeline().addLast(NetworkSystem$4.I[0x4 ^ 0x2], (ChannelHandler)networkManager);
                    networkManager.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$1(this.this$0), networkManager));
                }
            }).group((EventLoopGroup)((LazyLoadBase<EventLoopGroup>)o).getValue()).localAddress(inetAddress, n)).bind().syncUninterruptibly());
            // monitorexit(this.endpoints)
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
    }
    
    public void terminateEndpoints() {
        this.isAlive = ("".length() != 0);
        final Iterator<ChannelFuture> iterator = this.endpoints.iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ChannelFuture channelFuture = iterator.next();
            try {
                channelFuture.channel().close().sync();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                continue;
            }
            catch (InterruptedException ex) {
                NetworkSystem.logger.error(NetworkSystem.I["  ".length()]);
            }
        }
    }
    
    static List access$0(final NetworkSystem networkSystem) {
        return networkSystem.networkManagers;
    }
}
