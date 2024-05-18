package net.minecraft.client.network;

import net.minecraft.client.multiplayer.*;
import io.netty.bootstrap.*;
import io.netty.buffer.*;
import io.netty.util.concurrent.*;
import com.google.common.base.*;
import io.netty.channel.*;
import io.netty.channel.socket.nio.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.status.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import java.io.*;

public class OldServerPinger
{
    private static final Logger logger;
    private static final Splitter PING_RESPONSE_SPLITTER;
    private static final String[] I;
    private final List<NetworkManager> pingDestinations;
    
    private void tryCompatibilityPing(final ServerData serverData) {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>(this, func_78860_a, serverData) {
            private final ServerData val$server;
            final OldServerPinger this$0;
            private final ServerAddress val$serveraddress;
            
            protected void initChannel(final Channel channel) throws Exception {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)(boolean)(" ".length() != 0));
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                catch (ChannelException ex) {}
                final ChannelPipeline pipeline = channel.pipeline();
                final ChannelHandler[] array = new ChannelHandler[" ".length()];
                array["".length()] = (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>(this, this.val$serveraddress, this.val$server) {
                    private static final String[] I;
                    final OldServerPinger$2 this$1;
                    private final ServerAddress val$serveraddress;
                    private final ServerData val$server;
                    
                    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
                        super.channelActive(channelHandlerContext);
                        final ByteBuf buffer = Unpooled.buffer();
                        try {
                            buffer.writeByte(51 + 10 + 176 + 17);
                            buffer.writeByte(" ".length());
                            buffer.writeByte(36 + 221 - 120 + 113);
                            final char[] charArray = OldServerPinger$2$1.I["".length()].toCharArray();
                            buffer.writeShort(charArray.length);
                            final char[] array;
                            final int length = (array = charArray).length;
                            int i = "".length();
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                            while (i < length) {
                                buffer.writeChar((int)array[i]);
                                ++i;
                            }
                            buffer.writeShort((0x27 ^ 0x20) + "  ".length() * this.val$serveraddress.getIP().length());
                            buffer.writeByte(16 + 92 - 91 + 110);
                            final char[] charArray2 = this.val$serveraddress.getIP().toCharArray();
                            buffer.writeShort(charArray2.length);
                            final char[] array2;
                            final int length2 = (array2 = charArray2).length;
                            int j = "".length();
                            "".length();
                            if (2 == -1) {
                                throw null;
                            }
                            while (j < length2) {
                                buffer.writeChar((int)array2[j]);
                                ++j;
                            }
                            buffer.writeInt(this.val$serveraddress.getPort());
                            channelHandlerContext.channel().writeAndFlush((Object)buffer).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                            "".length();
                            if (4 == 2) {
                                throw null;
                            }
                        }
                        finally {
                            buffer.release();
                        }
                        buffer.release();
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
                            if (0 == -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    static {
                        I();
                    }
                    
                    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
                        channelHandlerContext.close();
                    }
                    
                    private static void I() {
                        (I = new String["   ".length()])["".length()] = I(" &\b\u0006\u0001\u0003\u0002<9\u001b\u0019", "metVh");
                        OldServerPinger$2$1.I[" ".length()] = I("\u00f1p", "VADAR");
                        OldServerPinger$2$1.I["  ".length()] = I("y", "VyWLZ");
                    }
                    
                    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
                        if (byteBuf.readUnsignedByte() == 220 + 8 + 24 + 3) {
                            final String[] array = (String[])Iterables.toArray(OldServerPinger.access$2().split((CharSequence)new String(byteBuf.readBytes(byteBuf.readShort() * "  ".length()).array(), Charsets.UTF_16BE)), (Class)String.class);
                            if (OldServerPinger$2$1.I[" ".length()].equals(array["".length()])) {
                                MathHelper.parseIntWithDefault(array[" ".length()], "".length());
                                final String gameVersion = array["  ".length()];
                                final String serverMOTD = array["   ".length()];
                                final int intWithDefault = MathHelper.parseIntWithDefault(array[0x56 ^ 0x52], -" ".length());
                                final int intWithDefault2 = MathHelper.parseIntWithDefault(array[0x35 ^ 0x30], -" ".length());
                                this.val$server.version = -" ".length();
                                this.val$server.gameVersion = gameVersion;
                                this.val$server.serverMOTD = serverMOTD;
                                this.val$server.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(intWithDefault).append(EnumChatFormatting.DARK_GRAY).append(OldServerPinger$2$1.I["  ".length()]).append(EnumChatFormatting.GRAY).append(intWithDefault2).toString();
                            }
                        }
                        channelHandlerContext.close();
                    }
                    
                    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
                        this.channelRead0(channelHandlerContext, (ByteBuf)o);
                    }
                };
                pipeline.addLast(array);
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
        })).channel((Class)NioSocketChannel.class)).connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("4= /1\n3`fv", "dTNHX");
        OldServerPinger.I[" ".length()] = I("\u0001+<\u0013\u001c.&7\u0014", "BJRpy");
    }
    
    static void access$1(final OldServerPinger oldServerPinger, final ServerData serverData) {
        oldServerPinger.tryCompatibilityPing(serverData);
    }
    
    public void pingPendingNetworks() {
        synchronized (this.pingDestinations) {
            final Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkManager networkManager = iterator.next();
                if (networkManager.isChannelOpen()) {
                    networkManager.processReceivedPackets();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    iterator.remove();
                    networkManager.checkDisconnected();
                }
            }
            // monitorexit(this.pingDestinations)
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
    }
    
    static Splitter access$2() {
        return OldServerPinger.PING_RESPONSE_SPLITTER;
    }
    
    static {
        I();
        PING_RESPONSE_SPLITTER = Splitter.on((char)"".length()).limit(0x68 ^ 0x6E);
        logger = LogManager.getLogger();
    }
    
    public OldServerPinger() {
        this.pingDestinations = Collections.synchronizedList((List<NetworkManager>)Lists.newArrayList());
    }
    
    public void clearPendingNetworks() {
        synchronized (this.pingDestinations) {
            final Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkManager networkManager = iterator.next();
                if (networkManager.isChannelOpen()) {
                    iterator.remove();
                    networkManager.closeChannel(new ChatComponentText(OldServerPinger.I[" ".length()]));
                }
            }
            // monitorexit(this.pingDestinations)
            "".length();
            if (3 < 0) {
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static Logger access$0() {
        return OldServerPinger.logger;
    }
    
    public void ping(final ServerData serverData) throws UnknownHostException {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        final NetworkManager func_181124_a = NetworkManager.func_181124_a(InetAddress.getByName(func_78860_a.getIP()), func_78860_a.getPort(), "".length() != 0);
        this.pingDestinations.add(func_181124_a);
        serverData.serverMOTD = OldServerPinger.I["".length()];
        serverData.pingToServer = -1L;
        serverData.playerList = null;
        func_181124_a.setNetHandler(new INetHandlerStatusClient(this, func_181124_a, serverData) {
            private final NetworkManager val$networkmanager;
            final OldServerPinger this$0;
            private final ServerData val$server;
            private boolean field_183009_e = "".length();
            private boolean field_147403_d = "".length();
            private static final String[] I;
            private long field_175092_e = 0L;
            
            private static void I() {
                (I = new String[0x95 ^ 0x84])["".length()] = I("\u0002\u0010+\u001f\u0001&\u0010,Z\u001d>\u0007-\u000b\u001d5\u0006<\u001f\fp\u0006<\u001b\u001c%\u0006", "PuHzh");
                OldServerPinger$1.I[" ".length()] = I("", "icSgQ");
                OldServerPinger$1.I["  ".length()] = I("\u0005\u000e<", "JbXyF");
                OldServerPinger$1.I["   ".length()] = I("Y", "vSONJ");
                OldServerPinger$1.I[0x47 ^ 0x43] = I("O", "EKlyY");
                OldServerPinger$1.I[0xC0 ^ 0xC5] = I("\u007f", "unbqR");
                OldServerPinger$1.I[0xBA ^ 0xBC] = I("^\u007f^w%\u001e5P", "pQpWD");
                OldServerPinger$1.I[0x82 ^ 0x85] = I("v,\u0015\u0006=voTZ", "VAztX");
                OldServerPinger$1.I[0xB1 ^ 0xB9] = I("yjE", "FUzYX");
                OldServerPinger$1.I[0xC9 ^ 0xC0] = I("(\u0016\u00038\u007f%\u001a\u0016> c\u0007\u0019>~.\u0016\u0004<sx[", "LwwYE");
                OldServerPinger$1.I[0x8F ^ 0x85] = I("-'\u0012'U +\u0007!\nf6\b!T+'\u0015#Y}j", "IFfFo");
                OldServerPinger$1.I[0x32 ^ 0x39] = I("\u000e\u00022'\u0007.\bd5\u000e5\u001a!4K.\u000f+(Ko\u0019*-\u0005(\u001b*f\r(\u001e)'\u001fn", "GlDFk");
                OldServerPinger$1.I[0x4D ^ 0x41] = I(",\u0013\u00063\u0000\u0002\u001f\f", "jzhZs");
                OldServerPinger$1.I[0x5E ^ 0x53] = I("\u000b\u0017\u0014~\u0004h\u0006\u00137\u0017h", "HvzYp");
                OldServerPinger$1.I[0xF ^ 0x1] = I("\\w", "fWrrp");
                OldServerPinger$1.I[0x4E ^ 0x41] = I("\u0000\t\u0017\u007f c\u000b\u00166:&\u000b\rx ,H\n=&5\r\u000bv", "ChyXT");
                OldServerPinger$1.I[0x34 ^ 0x24] = I("", "UiqSt");
            }
            
            static {
                I();
            }
            
            @Override
            public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                if (this.field_183009_e) {
                    this.val$networkmanager.closeChannel(new ChatComponentText(OldServerPinger$1.I["".length()]));
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    this.field_183009_e = (" ".length() != 0);
                    final ServerStatusResponse response = s00PacketServerInfo.getResponse();
                    if (response.getServerDescription() != null) {
                        this.val$server.serverMOTD = response.getServerDescription().getFormattedText();
                        "".length();
                        if (2 == -1) {
                            throw null;
                        }
                    }
                    else {
                        this.val$server.serverMOTD = OldServerPinger$1.I[" ".length()];
                    }
                    if (response.getProtocolVersionInfo() != null) {
                        this.val$server.gameVersion = response.getProtocolVersionInfo().getName();
                        this.val$server.version = response.getProtocolVersionInfo().getProtocol();
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        this.val$server.gameVersion = OldServerPinger$1.I["  ".length()];
                        this.val$server.version = "".length();
                    }
                    if (response.getPlayerCountData() != null) {
                        this.val$server.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(response.getPlayerCountData().getOnlinePlayerCount()).append(EnumChatFormatting.DARK_GRAY).append(OldServerPinger$1.I["   ".length()]).append(EnumChatFormatting.GRAY).append(response.getPlayerCountData().getMaxPlayers()).toString();
                        if (ArrayUtils.isNotEmpty((Object[])response.getPlayerCountData().getPlayers())) {
                            final StringBuilder sb = new StringBuilder();
                            final GameProfile[] players;
                            final int length = (players = response.getPlayerCountData().getPlayers()).length;
                            int i = "".length();
                            "".length();
                            if (3 == 1) {
                                throw null;
                            }
                            while (i < length) {
                                final GameProfile gameProfile = players[i];
                                if (sb.length() > 0) {
                                    sb.append(OldServerPinger$1.I[0x7E ^ 0x7A]);
                                }
                                sb.append(gameProfile.getName());
                                ++i;
                            }
                            if (response.getPlayerCountData().getPlayers().length < response.getPlayerCountData().getOnlinePlayerCount()) {
                                if (sb.length() > 0) {
                                    sb.append(OldServerPinger$1.I[0x77 ^ 0x72]);
                                }
                                sb.append(OldServerPinger$1.I[0x6C ^ 0x6A]).append(response.getPlayerCountData().getOnlinePlayerCount() - response.getPlayerCountData().getPlayers().length).append(OldServerPinger$1.I[0xA8 ^ 0xAF]);
                            }
                            this.val$server.playerList = sb.toString();
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.val$server.populationInfo = EnumChatFormatting.DARK_GRAY + OldServerPinger$1.I[0x96 ^ 0x9E];
                    }
                    if (response.getFavicon() != null) {
                        final String favicon = response.getFavicon();
                        if (favicon.startsWith(OldServerPinger$1.I[0xB7 ^ 0xBE])) {
                            this.val$server.setBase64EncodedIconData(favicon.substring(OldServerPinger$1.I[0xBA ^ 0xB0].length()));
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            OldServerPinger.access$0().error(OldServerPinger$1.I[0x43 ^ 0x48]);
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.val$server.setBase64EncodedIconData(null);
                    }
                    this.field_175092_e = Minecraft.getSystemTime();
                    this.val$networkmanager.sendPacket(new C01PacketPing(this.field_175092_e));
                    this.field_147403_d = (" ".length() != 0);
                }
            }
            
            @Override
            public void onDisconnect(final IChatComponent chatComponent) {
                if (!this.field_147403_d) {
                    OldServerPinger.access$0().error(OldServerPinger$1.I[0xE ^ 0x3] + this.val$server.serverIP + OldServerPinger$1.I[0xA4 ^ 0xAA] + chatComponent.getUnformattedText());
                    this.val$server.serverMOTD = EnumChatFormatting.DARK_RED + OldServerPinger$1.I[0x28 ^ 0x27];
                    this.val$server.populationInfo = OldServerPinger$1.I[0x28 ^ 0x38];
                    OldServerPinger.access$1(this.this$0, this.val$server);
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
                    if (0 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void handlePong(final S01PacketPong s01PacketPong) {
                this.val$server.pingToServer = Minecraft.getSystemTime() - this.field_175092_e;
                this.val$networkmanager.closeChannel(new ChatComponentText(OldServerPinger$1.I[0x7D ^ 0x71]));
            }
        });
        try {
            func_181124_a.sendPacket(new C00Handshake(0x4E ^ 0x61, func_78860_a.getIP(), func_78860_a.getPort(), EnumConnectionState.STATUS));
            func_181124_a.sendPacket(new C00PacketServerQuery());
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            OldServerPinger.logger.error((Object)t);
        }
    }
}
