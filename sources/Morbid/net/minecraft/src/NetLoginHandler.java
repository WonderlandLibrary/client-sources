package net.minecraft.src;

import net.minecraft.server.*;
import javax.crypto.*;
import java.security.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class NetLoginHandler extends NetHandler
{
    private static Random rand;
    private byte[] verifyToken;
    private final MinecraftServer mcServer;
    public final TcpConnection myTCPConnection;
    public boolean connectionComplete;
    private int connectionTimer;
    private String clientUsername;
    private volatile boolean field_72544_i;
    private String loginServerId;
    private boolean field_92079_k;
    private SecretKey sharedKey;
    
    static {
        NetLoginHandler.rand = new Random();
    }
    
    public NetLoginHandler(final MinecraftServer par1MinecraftServer, final Socket par2Socket, final String par3Str) throws IOException {
        this.connectionComplete = false;
        this.connectionTimer = 0;
        this.clientUsername = null;
        this.field_72544_i = false;
        this.loginServerId = "";
        this.field_92079_k = false;
        this.sharedKey = null;
        this.mcServer = par1MinecraftServer;
        this.myTCPConnection = new TcpConnection(par1MinecraftServer.getLogAgent(), par2Socket, par3Str, this, par1MinecraftServer.getKeyPair().getPrivate());
        this.myTCPConnection.field_74468_e = 0;
    }
    
    public void tryLogin() {
        if (this.field_72544_i) {
            this.initializePlayerConnection();
        }
        if (this.connectionTimer++ == 600) {
            this.raiseErrorAndDisconnect("Took too long to log in");
        }
        else {
            this.myTCPConnection.processReadPackets();
        }
    }
    
    public void raiseErrorAndDisconnect(final String par1Str) {
        try {
            this.mcServer.getLogAgent().logInfo("Disconnecting " + this.getUsernameAndAddress() + ": " + par1Str);
            this.myTCPConnection.addToSendQueue(new Packet255KickDisconnect(par1Str));
            this.myTCPConnection.serverShutdown();
            this.connectionComplete = true;
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
    }
    
    @Override
    public void handleClientProtocol(final Packet2ClientProtocol par1Packet2ClientProtocol) {
        this.clientUsername = par1Packet2ClientProtocol.getUsername();
        if (!this.clientUsername.equals(StringUtils.stripControlCodes(this.clientUsername))) {
            this.raiseErrorAndDisconnect("Invalid username!");
        }
        else {
            final PublicKey var2 = this.mcServer.getKeyPair().getPublic();
            if (par1Packet2ClientProtocol.getProtocolVersion() != 61) {
                if (par1Packet2ClientProtocol.getProtocolVersion() > 61) {
                    this.raiseErrorAndDisconnect("Outdated server!");
                }
                else {
                    this.raiseErrorAndDisconnect("Outdated client!");
                }
            }
            else {
                this.loginServerId = (this.mcServer.isServerInOnlineMode() ? Long.toString(NetLoginHandler.rand.nextLong(), 16) : "-");
                this.verifyToken = new byte[4];
                NetLoginHandler.rand.nextBytes(this.verifyToken);
                this.myTCPConnection.addToSendQueue(new Packet253ServerAuthData(this.loginServerId, var2, this.verifyToken));
            }
        }
    }
    
    @Override
    public void handleSharedKey(final Packet252SharedKey par1Packet252SharedKey) {
        final PrivateKey var2 = this.mcServer.getKeyPair().getPrivate();
        this.sharedKey = par1Packet252SharedKey.getSharedKey(var2);
        if (!Arrays.equals(this.verifyToken, par1Packet252SharedKey.getVerifyToken(var2))) {
            this.raiseErrorAndDisconnect("Invalid client reply");
        }
        this.myTCPConnection.addToSendQueue(new Packet252SharedKey());
    }
    
    @Override
    public void handleClientCommand(final Packet205ClientCommand par1Packet205ClientCommand) {
        if (par1Packet205ClientCommand.forceRespawn == 0) {
            if (this.field_92079_k) {
                this.raiseErrorAndDisconnect("Duplicate login");
                return;
            }
            this.field_92079_k = true;
            if (this.mcServer.isServerInOnlineMode()) {
                new ThreadLoginVerifier(this).start();
            }
            else {
                this.field_72544_i = true;
            }
        }
    }
    
    @Override
    public void handleLogin(final Packet1Login par1Packet1Login) {
    }
    
    public void initializePlayerConnection() {
        final String var1 = this.mcServer.getConfigurationManager().allowUserToConnect(this.myTCPConnection.getSocketAddress(), this.clientUsername);
        if (var1 != null) {
            this.raiseErrorAndDisconnect(var1);
        }
        else {
            final EntityPlayerMP var2 = this.mcServer.getConfigurationManager().createPlayerForUser(this.clientUsername);
            if (var2 != null) {
                this.mcServer.getConfigurationManager().initializeConnectionToPlayer(this.myTCPConnection, var2);
            }
        }
        this.connectionComplete = true;
    }
    
    @Override
    public void handleErrorMessage(final String par1Str, final Object[] par2ArrayOfObj) {
        this.mcServer.getLogAgent().logInfo(String.valueOf(this.getUsernameAndAddress()) + " lost connection");
        this.connectionComplete = true;
    }
    
    @Override
    public void handleServerPing(final Packet254ServerPing par1Packet254ServerPing) {
        try {
            final ServerConfigurationManager var2 = this.mcServer.getConfigurationManager();
            String var3 = null;
            if (par1Packet254ServerPing.readSuccessfully == 1) {
                final List var4 = Arrays.asList(1, 61, this.mcServer.getMinecraftVersion(), this.mcServer.getMOTD(), var2.getCurrentPlayerCount(), var2.getMaxPlayers());
                for (final Object var6 : var4) {
                    if (var3 == null) {
                        var3 = "§";
                    }
                    else {
                        var3 = String.valueOf(var3) + "\u0000";
                    }
                    var3 = String.valueOf(var3) + var6.toString().replaceAll("\u0000", "");
                }
            }
            else {
                var3 = String.valueOf(this.mcServer.getMOTD()) + "§" + var2.getCurrentPlayerCount() + "§" + var2.getMaxPlayers();
            }
            InetAddress var7 = null;
            if (this.myTCPConnection.getSocket() != null) {
                var7 = this.myTCPConnection.getSocket().getInetAddress();
            }
            this.myTCPConnection.addToSendQueue(new Packet255KickDisconnect(var3));
            this.myTCPConnection.serverShutdown();
            if (var7 != null && this.mcServer.getNetworkThread() instanceof DedicatedServerListenThread) {
                ((DedicatedServerListenThread)this.mcServer.getNetworkThread()).func_71761_a(var7);
            }
            this.connectionComplete = true;
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
    }
    
    @Override
    public void unexpectedPacket(final Packet par1Packet) {
        this.raiseErrorAndDisconnect("Protocol error");
    }
    
    public String getUsernameAndAddress() {
        return (this.clientUsername != null) ? (String.valueOf(this.clientUsername) + " [" + this.myTCPConnection.getSocketAddress().toString() + "]") : this.myTCPConnection.getSocketAddress().toString();
    }
    
    @Override
    public boolean isServerHandler() {
        return true;
    }
    
    static String getServerId(final NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.loginServerId;
    }
    
    static MinecraftServer getLoginMinecraftServer(final NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.mcServer;
    }
    
    static SecretKey getSharedKey(final NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.sharedKey;
    }
    
    static String getClientUsername(final NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.clientUsername;
    }
    
    static boolean func_72531_a(final NetLoginHandler par0NetLoginHandler, final boolean par1) {
        return par0NetLoginHandler.field_72544_i = par1;
    }
}
