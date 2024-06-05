package net.minecraft.realms;

import com.google.common.collect.*;
import net.minecraft.network.status.*;
import net.minecraft.network.status.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class RealmsServerStatusPinger
{
    private static final Logger LOGGER;
    private final List connections;
    
    public RealmsServerStatusPinger() {
        this.connections = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void pingServer(final String p_pingServer_1_, final RealmsServerPing p_pingServer_2_) throws UnknownHostException {
        if (p_pingServer_1_ != null && !p_pingServer_1_.startsWith("0.0.0.0") && !p_pingServer_1_.isEmpty()) {
            final RealmsServerAddress var3 = RealmsServerAddress.parseString(p_pingServer_1_);
            final NetworkManager var4 = NetworkManager.provideLanClient(InetAddress.getByName(var3.getHost()), var3.getPort());
            this.connections.add(var4);
            var4.setNetHandler(new INetHandlerStatusClient() {
                private boolean field_154345_e = false;
                
                @Override
                public void handleServerInfo(final S00PacketServerInfo packetIn) {
                    final ServerStatusResponse var2 = packetIn.func_149294_c();
                    if (var2.getPlayerCountData() != null) {
                        p_pingServer_2_.nrOfPlayers = String.valueOf(var2.getPlayerCountData().getOnlinePlayerCount());
                    }
                    var4.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
                    this.field_154345_e = true;
                }
                
                @Override
                public void handlePong(final S01PacketPong packetIn) {
                    var4.closeChannel(new ChatComponentText("Finished"));
                }
                
                @Override
                public void onDisconnect(final IChatComponent reason) {
                    if (!this.field_154345_e) {
                        RealmsServerStatusPinger.LOGGER.error("Can't ping " + p_pingServer_1_ + ": " + reason.getUnformattedText());
                    }
                }
            });
            try {
                var4.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, var3.getHost(), var3.getPort(), EnumConnectionState.STATUS));
                var4.sendPacket(new C00PacketServerQuery());
            }
            catch (Throwable var5) {
                RealmsServerStatusPinger.LOGGER.error((Object)var5);
            }
        }
    }
    
    public void tick() {
        final List var1 = this.connections;
        synchronized (this.connections) {
            final Iterator var2 = this.connections.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var3.processReceivedPackets();
                }
                else {
                    var2.remove();
                    var3.checkDisconnected();
                }
            }
        }
    }
    
    public void removeAll() {
        final List var1 = this.connections;
        synchronized (this.connections) {
            final Iterator var2 = this.connections.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var2.remove();
                    var3.closeChannel(new ChatComponentText("Cancelled"));
                }
            }
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
