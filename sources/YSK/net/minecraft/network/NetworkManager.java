package net.minecraft.network;

import java.util.concurrent.locks.*;
import io.netty.channel.nio.*;
import io.netty.util.*;
import java.util.*;
import io.netty.channel.local.*;
import io.netty.util.concurrent.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import javax.crypto.*;
import java.security.*;
import io.netty.bootstrap.*;
import java.net.*;
import io.netty.channel.epoll.*;
import io.netty.channel.socket.nio.*;
import io.netty.channel.*;
import io.netty.handler.timeout.*;
import net.minecraft.util.*;
import java.io.*;

public class NetworkManager extends SimpleChannelInboundHandler<Packet>
{
    public static final Marker logMarkerNetwork;
    private final ReentrantReadWriteLock field_181680_j;
    private final EnumPacketDirection direction;
    public static final LazyLoadBase<EpollEventLoopGroup> field_181125_e;
    private boolean isEncrypted;
    private static final String[] I;
    private INetHandler packetListener;
    public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP;
    public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP;
    private boolean disconnected;
    private static final Logger logger;
    public static final Marker logMarkerPackets;
    private IChatComponent terminationReason;
    public static final AttributeKey<EnumConnectionState> attrKeyConnectionState;
    private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue;
    private Channel channel;
    private SocketAddress socketAddress;
    
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Packet packet) throws Exception {
        if (this.channel.isOpen()) {
            try {
                packet.processPacket(this.packetListener);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (ThreadQuickExitException ex) {}
        }
    }
    
    private static void I() {
        (I = new String[0x1F ^ 0x2])["".length()] = I("$6\u0001>\u000688", "jsUiI");
        NetworkManager.I[" ".length()] = I("?\u0012\u0000\u0005&#\u001c\u000b\u0002(2\u001c\u0011\u0006:", "qWTRi");
        NetworkManager.I["  ".length()] = I("47$#\"'*'", "DEKWM");
        NetworkManager.I["   ".length()] = I("\b**\u0007\u0003( k\u0004\u001a9+k\u0017\n, ", "MDKeo");
        NetworkManager.I[0x64 ^ 0x60] = I("\f\n\u001a%\u0006\u0006\r\f%\u001dF\u0006\u0007\"&\u000e0\u001d4\f\t\u000e", "hciFi");
        NetworkManager.I[0xA5 ^ 0xA0] = I("\u00139\u0004\f#\u0019>\u0012\f8Y$\u001e\u0002)\u0018%\u0003", "wPwoL");
        NetworkManager.I[0x1D ^ 0x1B] = I("\r\u001c?/\u0000\u0007\u001b)/\u001bG\u0012)\"\n\u001b\u001c/\u001e\n\b\u0006#\"", "iuLLo");
        NetworkManager.I[0x36 ^ 0x31] = I("0,\u0016\u0012\u0005\u0017#\u000eW2\u0001!\u0007\u0007\u0003\u0010-\fMW", "yBbww");
        NetworkManager.I[0x7A ^ 0x72] = I(":\t\r\u001b\u0016>$\u0007\u0003\u0007/\u0006\u000b\u0002", "Jhnps");
        NetworkManager.I[0x53 ^ 0x5A] = I("\"/>O\u0015\u00189>\n\u0017\u00148j\u0000\u001fQ17O\r\u001ej1\u0012", "qJJoy");
        NetworkManager.I[0x36 ^ 0x3C] = I("!;\u00027\u000f\t7\u0015v\f\u0010&\u001ev\u001f\u00003\u0015", "eRqVm");
        NetworkManager.I[0x4A ^ 0x41] = I("\u0001;\t<\u000e\u0006.\u0017", "rKeUz");
        NetworkManager.I[0x93 ^ 0x9F] = I("\u0016\u001c\u000f?*\u0002\r", "rylMS");
        NetworkManager.I[0x4B ^ 0x46] = I("\u001c0\u000f5\u0004\u0002&\u000f7", "lBjEa");
        NetworkManager.I[0x69 ^ 0x67] = I("\u0003#\t3\u0011\u00169", "fMjAh");
        NetworkManager.I[0x31 ^ 0x3E] = I("\u000f\u0011964\u001b\u0006?**", "ktZYY");
        NetworkManager.I[0x83 ^ 0x93] = I("\"*\u000b\u0004<6=\r\u0018\"", "FOhkQ");
        NetworkManager.I[0x57 ^ 0x46] = I("\t&\u0006\u001d'\b1", "mCerC");
        NetworkManager.I[0xBA ^ 0xA8] = I("%\f;\u001e\u001d1\u001b=\u0002\u0003", "AiXqp");
        NetworkManager.I[0x6D ^ 0x7E] = I(".\u001b7\n1(\u0007)", "MtZzC");
        NetworkManager.I[0x1C ^ 0x8] = I("\u0014\u0011/>8\u0000\u0006)\"&", "ptLQU");
        NetworkManager.I[0x55 ^ 0x40] = I("\" \u000b50\"<", "GNhZT");
        NetworkManager.I[0xAC ^ 0xBA] = I("\u0017\u0006\u0001%&\u0011\u001a\u001f", "tilUT");
        NetworkManager.I[0x30 ^ 0x27] = I(",+ .(8<&26", "HNCAE");
        NetworkManager.I[0x1D ^ 0x5] = I("\"\u00066\u001e<6\u00110\u0002\"", "FcUqQ");
        NetworkManager.I[0x5A ^ 0x43] = I("\u0016\u0017\b\u001a\u0016\u0010\u000b\u0016", "uxejd");
        NetworkManager.I[0x64 ^ 0x7E] = I("4\u0016\u001a\u001e\u00162\n\u0004", "Wywnd");
        NetworkManager.I[0xDB ^ 0xC0] = I("/=\u0017\u0005:\u0005:\u0001\u0005!\u000e0", "kTdfU");
        NetworkManager.I[0xA6 ^ 0xBA] = I("1\u0017'\u0005%<2 \u0012*6\u0018'\u0004*-\u001f&\u000fapV*\u0000%5\u0013-A=.\u001f*\u0004", "YvIaI");
    }
    
    public boolean isLocalChannel() {
        if (!(this.channel instanceof LocalChannel) && !(this.channel instanceof LocalServerChannel)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        ChatComponentTranslation chatComponentTranslation;
        if (t instanceof TimeoutException) {
            chatComponentTranslation = new ChatComponentTranslation(NetworkManager.I[0x7E ^ 0x7B], new Object["".length()]);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            final String s = NetworkManager.I[0xA0 ^ 0xA6];
            final Object[] array = new Object[" ".length()];
            array["".length()] = NetworkManager.I[0x4A ^ 0x4D] + t;
            chatComponentTranslation = new ChatComponentTranslation(s, array);
        }
        this.closeChannel(chatComponentTranslation);
    }
    
    public void setConnectionState(final EnumConnectionState enumConnectionState) {
        this.channel.attr((AttributeKey)NetworkManager.attrKeyConnectionState).set((Object)enumConnectionState);
        this.channel.config().setAutoRead((boolean)(" ".length() != 0));
        NetworkManager.logger.debug(NetworkManager.I["   ".length()]);
    }
    
    public void sendPacket(final Packet packet, final GenericFutureListener<? extends Future<? super Void>> genericFutureListener, final GenericFutureListener<? extends Future<? super Void>>... array) {
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])array, "".length(), (Object)genericFutureListener));
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            this.field_181680_j.writeLock().lock();
            try {
                this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packet, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])array, "".length(), (Object)genericFutureListener)));
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            finally {
                this.field_181680_j.writeLock().unlock();
            }
            this.field_181680_j.writeLock().unlock();
        }
    }
    
    public boolean isChannelOpen() {
        if (this.channel != null && this.channel.isOpen()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.closeChannel(new ChatComponentTranslation(NetworkManager.I[0x7F ^ 0x7B], new Object["".length()]));
    }
    
    public NetworkManager(final EnumPacketDirection direction) {
        this.outboundPacketsQueue = (Queue<InboundHandlerTuplePacketListener>)Queues.newConcurrentLinkedQueue();
        this.field_181680_j = new ReentrantReadWriteLock();
        this.direction = direction;
    }
    
    public IChatComponent getExitMessage() {
        return this.terminationReason;
    }
    
    public void setNetHandler(final INetHandler packetListener) {
        Validate.notNull((Object)packetListener, NetworkManager.I[0x40 ^ 0x48], new Object["".length()]);
        final Logger logger = NetworkManager.logger;
        final String s = NetworkManager.I[0x43 ^ 0x4A];
        final Object[] array = new Object["  ".length()];
        array["".length()] = this;
        array[" ".length()] = packetListener;
        logger.debug(s, array);
        this.packetListener = packetListener;
    }
    
    private void dispatchPacket(final Packet packet, final GenericFutureListener<? extends Future<? super Void>>[] array) {
        final EnumConnectionState fromPacket = EnumConnectionState.getFromPacket(packet);
        final EnumConnectionState enumConnectionState = (EnumConnectionState)this.channel.attr((AttributeKey)NetworkManager.attrKeyConnectionState).get();
        if (enumConnectionState != fromPacket) {
            NetworkManager.logger.debug(NetworkManager.I[0x56 ^ 0x5C]);
            this.channel.config().setAutoRead((boolean)("".length() != 0));
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (fromPacket != enumConnectionState) {
                this.setConnectionState(fromPacket);
            }
            final ChannelFuture writeAndFlush = this.channel.writeAndFlush((Object)packet);
            if (array != null) {
                writeAndFlush.addListeners((GenericFutureListener[])array);
            }
            writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.channel.eventLoop().execute((Runnable)new Runnable(this, fromPacket, enumConnectionState, packet, array) {
                private final GenericFutureListener[] val$futureListeners;
                private final EnumConnectionState val$enumconnectionstate;
                final NetworkManager this$0;
                private final EnumConnectionState val$enumconnectionstate1;
                private final Packet val$inPacket;
                
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
                        if (false) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void run() {
                    if (this.val$enumconnectionstate != this.val$enumconnectionstate1) {
                        this.this$0.setConnectionState(this.val$enumconnectionstate);
                    }
                    final ChannelFuture writeAndFlush = NetworkManager.access$1(this.this$0).writeAndFlush((Object)this.val$inPacket);
                    if (this.val$futureListeners != null) {
                        writeAndFlush.addListeners(this.val$futureListeners);
                    }
                    writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            });
        }
    }
    
    public void closeChannel(final IChatComponent terminationReason) {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
            this.terminationReason = terminationReason;
        }
    }
    
    public void processReceivedPackets() {
        this.flushOutboundQueue();
        if (this.packetListener instanceof ITickable) {
            ((ITickable)this.packetListener).update();
        }
        this.channel.flush();
    }
    
    public INetHandler getNetHandler() {
        return this.packetListener;
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.channel = channelHandlerContext.channel();
        this.socketAddress = this.channel.remoteAddress();
        try {
            this.setConnectionState(EnumConnectionState.HANDSHAKING);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (Throwable t) {
            NetworkManager.logger.fatal((Object)t);
        }
    }
    
    private void flushOutboundQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            this.field_181680_j.readLock().lock();
            try {
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
                while (!this.outboundPacketsQueue.isEmpty()) {
                    final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener = this.outboundPacketsQueue.poll();
                    this.dispatchPacket(InboundHandlerTuplePacketListener.access$0(inboundHandlerTuplePacketListener), (GenericFutureListener<? extends Future<? super Void>>[])InboundHandlerTuplePacketListener.access$1(inboundHandlerTuplePacketListener));
                }
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            finally {
                this.field_181680_j.readLock().unlock();
            }
            this.field_181680_j.readLock().unlock();
        }
    }
    
    public void checkDisconnected() {
        if (this.channel != null && !this.channel.isOpen()) {
            if (!this.disconnected) {
                this.disconnected = (" ".length() != 0);
                if (this.getExitMessage() != null) {
                    this.getNetHandler().onDisconnect(this.getExitMessage());
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else if (this.getNetHandler() != null) {
                    this.getNetHandler().onDisconnect(new ChatComponentText(NetworkManager.I[0x21 ^ 0x3A]));
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
            }
            else {
                NetworkManager.logger.warn(NetworkManager.I[0x1E ^ 0x2]);
            }
        }
    }
    
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.channelRead0(channelHandlerContext, (Packet)o);
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        logMarkerNetwork = MarkerManager.getMarker(NetworkManager.I["".length()]);
        logMarkerPackets = MarkerManager.getMarker(NetworkManager.I[" ".length()], NetworkManager.logMarkerNetwork);
        attrKeyConnectionState = AttributeKey.valueOf(NetworkManager.I["  ".length()]);
        CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>() {
            private static final String[] I;
            
            @Override
            protected Object load() {
                return this.load();
            }
            
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
                    if (0 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected NioEventLoopGroup load() {
                return new NioEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkManager$1.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\f6\u0003:.b\u0010\u001b'2,'W\u0007\u0018bpR*", "BSwNW");
            }
        };
        field_181125_e = new LazyLoadBase<EpollEventLoopGroup>() {
            private static final String[] I;
            
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected EpollEventLoopGroup load() {
                return new EpollEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkManager$2.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
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
                    if (4 <= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("6&\u001b\u0007.X\u0006\u001f\u001c;\u0014c,\u001f>\u001d-\u001bS\u001e7cLV3", "xCosW");
            }
            
            static {
                I();
            }
        };
        CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
            private static final String[] I;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u001e\t:\u0015\u0015p !\u0002\r<L\r\r\u00055\u0002:A%\u001fLmD\b", "PlNal");
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
                    if (4 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected LocalEventLoopGroup load() {
                return new LocalEventLoopGroup("".length(), new ThreadFactoryBuilder().setNameFormat(NetworkManager$3.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
            }
            
            static {
                I();
            }
            
            @Override
            protected Object load() {
                return this.load();
            }
        };
    }
    
    public void sendPacket(final Packet packet) {
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, null);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            this.field_181680_j.writeLock().lock();
            try {
                this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packet, (GenericFutureListener<? extends Future<? super Void>>[])null));
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            finally {
                this.field_181680_j.writeLock().unlock();
            }
            this.field_181680_j.writeLock().unlock();
        }
    }
    
    public boolean getIsencrypted() {
        return this.isEncrypted;
    }
    
    public void enableEncryption(final SecretKey secretKey) {
        this.isEncrypted = (" ".length() != 0);
        this.channel.pipeline().addBefore(NetworkManager.I[0x57 ^ 0x5C], NetworkManager.I[0x78 ^ 0x74], (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance("  ".length(), secretKey)));
        this.channel.pipeline().addBefore(NetworkManager.I[0xA8 ^ 0xA5], NetworkManager.I[0x42 ^ 0x4C], (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(" ".length(), secretKey)));
    }
    
    public SocketAddress getRemoteAddress() {
        return this.socketAddress;
    }
    
    public static NetworkManager provideLocalClient(final SocketAddress socketAddress) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>(networkManager) {
            private final NetworkManager val$networkmanager;
            private static final String[] I;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0007&\b\r\u0017\u0003\u0018\u0003\u0007\u001c\u0013+\u000e\u0014", "wGkfr");
            }
            
            protected void initChannel(final Channel channel) throws Exception {
                channel.pipeline().addLast(NetworkManager$6.I["".length()], (ChannelHandler)this.val$networkmanager);
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
                    if (0 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
        })).channel((Class)LocalChannel.class)).connect(socketAddress).syncUninterruptibly();
        return networkManager;
    }
    
    public void setCompressionTreshold(final int n) {
        if (n >= 0) {
            if (this.channel.pipeline().get(NetworkManager.I[0x3D ^ 0x32]) instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.channel.pipeline().get(NetworkManager.I[0x18 ^ 0x8])).setCompressionTreshold(n);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                this.channel.pipeline().addBefore(NetworkManager.I[0x24 ^ 0x35], NetworkManager.I[0x67 ^ 0x75], (ChannelHandler)new NettyCompressionDecoder(n));
            }
            if (this.channel.pipeline().get(NetworkManager.I[0x73 ^ 0x60]) instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.channel.pipeline().get(NetworkManager.I[0x10 ^ 0x4])).setCompressionTreshold(n);
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                this.channel.pipeline().addBefore(NetworkManager.I[0x21 ^ 0x34], NetworkManager.I[0x33 ^ 0x25], (ChannelHandler)new NettyCompressionEncoder(n));
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
        }
        else {
            if (this.channel.pipeline().get(NetworkManager.I[0xD4 ^ 0xC3]) instanceof NettyCompressionDecoder) {
                this.channel.pipeline().remove(NetworkManager.I[0x10 ^ 0x8]);
            }
            if (this.channel.pipeline().get(NetworkManager.I[0xAF ^ 0xB6]) instanceof NettyCompressionEncoder) {
                this.channel.pipeline().remove(NetworkManager.I[0x46 ^ 0x5C]);
            }
        }
    }
    
    public void disableAutoRead() {
        this.channel.config().setAutoRead((boolean)("".length() != 0));
    }
    
    public static NetworkManager func_181124_a(final InetAddress inetAddress, final int n, final boolean b) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        Serializable s;
        Object o;
        if (Epoll.isAvailable() && b) {
            s = EpollSocketChannel.class;
            o = NetworkManager.field_181125_e;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            s = NioSocketChannel.class;
            o = NetworkManager.CLIENT_NIO_EVENTLOOP;
        }
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)((LazyLoadBase<EventLoopGroup>)o).getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>(networkManager) {
            private final NetworkManager val$networkmanager;
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
                    if (4 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0xB1 ^ 0xB7])["".length()] = I("\u0005\u0006\u001f \u0000\u0004\u001b", "qorEo");
                NetworkManager$5.I[" ".length()] = I("\u0000?\u0019\u0000:\u0007*\u0007", "sOuiN");
                NetworkManager$5.I["  ".length()] = I("0\n&\u000771\u001d", "ToEhS");
                NetworkManager$5.I["   ".length()] = I("\u0012\u0011/\u0016\u0014\f\u0007/\u0014", "bcJfq");
                NetworkManager$5.I[0x95 ^ 0x91] = I("\u0007\u0005+'\u0015\u0007\u0019", "bkHHq");
                NetworkManager$5.I[0xC1 ^ 0xC4] = I("\u0019/\u0002#*\u001d\u0011\t)!\r\"\u0004:", "iNaHO");
            }
            
            protected void initChannel(final Channel channel) throws Exception {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)(boolean)(" ".length() != 0));
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                catch (ChannelException ex) {}
                channel.pipeline().addLast(NetworkManager$5.I["".length()], (ChannelHandler)new ReadTimeoutHandler(0x7C ^ 0x62)).addLast(NetworkManager$5.I[" ".length()], (ChannelHandler)new MessageDeserializer2()).addLast(NetworkManager$5.I["  ".length()], (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast(NetworkManager$5.I["   ".length()], (ChannelHandler)new MessageSerializer2()).addLast(NetworkManager$5.I[0xBF ^ 0xBB], (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast(NetworkManager$5.I[0x65 ^ 0x60], (ChannelHandler)this.val$networkmanager);
            }
        })).channel((Class)s)).connect(inetAddress, n).syncUninterruptibly();
        return networkManager;
    }
    
    public boolean hasNoChannel() {
        if (this.channel == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static Channel access$1(final NetworkManager networkManager) {
        return networkManager.channel;
    }
    
    static class InboundHandlerTuplePacketListener
    {
        private final Packet packet;
        private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
        
        static Packet access$0(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.packet;
        }
        
        public InboundHandlerTuplePacketListener(final Packet packet, final GenericFutureListener<? extends Future<? super Void>>... futureListeners) {
            this.packet = packet;
            this.futureListeners = futureListeners;
        }
        
        static GenericFutureListener[] access$1(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.futureListeners;
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
    }
}
