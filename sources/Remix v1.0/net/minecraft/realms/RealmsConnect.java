package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import java.net.*;
import org.apache.logging.log4j.*;

public class RealmsConnect
{
    private static final Logger LOGGER;
    private final RealmsScreen onlineScreen;
    private volatile boolean aborted;
    private NetworkManager connection;
    
    public RealmsConnect(final RealmsScreen p_i1079_1_) {
        this.aborted = false;
        this.onlineScreen = p_i1079_1_;
    }
    
    public void connect(final String p_connect_1_, final int p_connect_2_) {
        new Thread("Realms-connect-task") {
            @Override
            public void run() {
                InetAddress var1 = null;
                try {
                    var1 = InetAddress.getByName(p_connect_1_);
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection = NetworkManager.provideLanClient(var1, p_connect_2_);
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.setNetHandler(new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.getMinecraft(), RealmsConnect.this.onlineScreen.getProxy()));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.sendPacket(new C00Handshake(47, p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
                }
                catch (UnknownHostException var2) {
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)var2);
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + p_connect_1_ + "'" })));
                }
                catch (Exception var3) {
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)var3);
                    String var4 = var3.toString();
                    if (var1 != null) {
                        final String var5 = var1.toString() + ":" + p_connect_2_;
                        var4 = var4.replaceAll(var5, "");
                    }
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
                }
            }
        }.start();
    }
    
    public void abort() {
        this.aborted = true;
    }
    
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isChannelOpen()) {
                this.connection.processReceivedPackets();
            }
            else {
                this.connection.checkDisconnected();
            }
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
