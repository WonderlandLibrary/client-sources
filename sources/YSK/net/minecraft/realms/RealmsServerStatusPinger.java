package net.minecraft.realms;

import net.minecraft.network.status.*;
import net.minecraft.util.*;
import net.minecraft.network.status.server.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;
import java.io.*;

public class RealmsServerStatusPinger
{
    private static final Logger LOGGER;
    private static final String[] I;
    private final List<NetworkManager> connections;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("s_x`BmA", "CqHNr");
        RealmsServerStatusPinger.I[" ".length()] = I("\u00003\u0003\r\u001c/>\b\n", "CRmny");
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
    
    public void pingServer(final String s, final RealmsServerPing realmsServerPing) throws UnknownHostException {
        if (s != null && !s.startsWith(RealmsServerStatusPinger.I["".length()]) && !s.isEmpty()) {
            final RealmsServerAddress string = RealmsServerAddress.parseString(s);
            final NetworkManager func_181124_a = NetworkManager.func_181124_a(InetAddress.getByName(string.getHost()), string.getPort(), "".length() != 0);
            this.connections.add(func_181124_a);
            func_181124_a.setNetHandler(new INetHandlerStatusClient(this, realmsServerPing, func_181124_a, s) {
                final RealmsServerStatusPinger this$0;
                private static final String[] I;
                private boolean field_154345_e = "".length();
                private final NetworkManager val$networkmanager;
                private final RealmsServerPing val$p_pingServer_2_;
                private final String val$p_pingServer_1_;
                
                @Override
                public void handlePong(final S01PacketPong s01PacketPong) {
                    this.val$networkmanager.closeChannel(new ChatComponentText(RealmsServerStatusPinger$1.I[0x49 ^ 0x4C]));
                }
                
                private static void I() {
                    (I = new String[0xC ^ 0x4])["".length()] = I("K", "AGMLu");
                    RealmsServerStatusPinger$1.I[" ".length()] = I("d", "nLdUV");
                    RealmsServerStatusPinger$1.I["  ".length()] = I("JlwE&\n&y", "dBYeG");
                    RealmsServerStatusPinger$1.I["   ".length()] = I("O\u0002\u0016\u000b\fOAWW", "ooyyi");
                    RealmsServerStatusPinger$1.I[0x3 ^ 0x7] = I("", "whKiD");
                    RealmsServerStatusPinger$1.I[0x7F ^ 0x7A] = I("\u0011<=\u000f\u0006?07", "WUSfu");
                    RealmsServerStatusPinger$1.I[0xA9 ^ 0xAF] = I("\u0002\u0007&u\u0004a\u0016!<\u0017a", "AfHRp");
                    RealmsServerStatusPinger$1.I[0x7B ^ 0x7C] = I("xo", "BOuIJ");
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
                
                static {
                    I();
                }
                
                @Override
                public void onDisconnect(final IChatComponent chatComponent) {
                    if (!this.field_154345_e) {
                        RealmsServerStatusPinger.access$0().error(RealmsServerStatusPinger$1.I[0x35 ^ 0x33] + this.val$p_pingServer_1_ + RealmsServerStatusPinger$1.I[0x5B ^ 0x5C] + chatComponent.getUnformattedText());
                    }
                }
                
                @Override
                public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                    final ServerStatusResponse response = s00PacketServerInfo.getResponse();
                    if (response.getPlayerCountData() != null) {
                        this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(response.getPlayerCountData().getOnlinePlayerCount());
                        if (ArrayUtils.isNotEmpty((Object[])response.getPlayerCountData().getPlayers())) {
                            final StringBuilder sb = new StringBuilder();
                            final GameProfile[] players;
                            final int length = (players = response.getPlayerCountData().getPlayers()).length;
                            int i = "".length();
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                            while (i < length) {
                                final GameProfile gameProfile = players[i];
                                if (sb.length() > 0) {
                                    sb.append(RealmsServerStatusPinger$1.I["".length()]);
                                }
                                sb.append(gameProfile.getName());
                                ++i;
                            }
                            if (response.getPlayerCountData().getPlayers().length < response.getPlayerCountData().getOnlinePlayerCount()) {
                                if (sb.length() > 0) {
                                    sb.append(RealmsServerStatusPinger$1.I[" ".length()]);
                                }
                                sb.append(RealmsServerStatusPinger$1.I["  ".length()]).append(response.getPlayerCountData().getOnlinePlayerCount() - response.getPlayerCountData().getPlayers().length).append(RealmsServerStatusPinger$1.I["   ".length()]);
                            }
                            this.val$p_pingServer_2_.playerList = sb.toString();
                            "".length();
                            if (2 == 1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.val$p_pingServer_2_.playerList = RealmsServerStatusPinger$1.I[0x97 ^ 0x93];
                    }
                    this.val$networkmanager.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
                    this.field_154345_e = (" ".length() != 0);
                }
            });
            try {
                func_181124_a.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, string.getHost(), string.getPort(), EnumConnectionState.STATUS));
                func_181124_a.sendPacket(new C00PacketServerQuery());
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            catch (Throwable t) {
                RealmsServerStatusPinger.LOGGER.error((Object)t);
            }
        }
    }
    
    static Logger access$0() {
        return RealmsServerStatusPinger.LOGGER;
    }
    
    public void tick() {
        synchronized (this.connections) {
            final Iterator<NetworkManager> iterator = this.connections.iterator();
            "".length();
            if (-1 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkManager networkManager = iterator.next();
                if (networkManager.isChannelOpen()) {
                    networkManager.processReceivedPackets();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    iterator.remove();
                    networkManager.checkDisconnected();
                }
            }
            // monitorexit(this.connections)
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
    }
    
    public RealmsServerStatusPinger() {
        this.connections = Collections.synchronizedList((List<NetworkManager>)Lists.newArrayList());
    }
    
    public void removeAll() {
        synchronized (this.connections) {
            final Iterator<NetworkManager> iterator = this.connections.iterator();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkManager networkManager = iterator.next();
                if (networkManager.isChannelOpen()) {
                    iterator.remove();
                    networkManager.closeChannel(new ChatComponentText(RealmsServerStatusPinger.I[" ".length()]));
                }
            }
            // monitorexit(this.connections)
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
}
