package net.minecraft.src;

import java.io.*;
import java.util.*;
import java.net.*;

public class RConThreadQuery extends RConThreadBase
{
    private long lastAuthCheckTime;
    private int queryPort;
    private int serverPort;
    private int maxPlayers;
    private String serverMotd;
    private String worldName;
    private DatagramSocket querySocket;
    private byte[] buffer;
    private DatagramPacket incomingPacket;
    private Map field_72644_p;
    private String queryHostname;
    private String serverHostname;
    private Map queryClients;
    private long time;
    private RConOutputStream output;
    private long lastQueryResponseTime;
    
    public RConThreadQuery(final IServer par1IServer) {
        super(par1IServer);
        this.querySocket = null;
        this.buffer = new byte[1460];
        this.incomingPacket = null;
        this.queryPort = par1IServer.getIntProperty("query.port", 0);
        this.serverHostname = par1IServer.getHostname();
        this.serverPort = par1IServer.getPort();
        this.serverMotd = par1IServer.getServerMOTD();
        this.maxPlayers = par1IServer.getMaxPlayers();
        this.worldName = par1IServer.getFolderName();
        this.lastQueryResponseTime = 0L;
        this.queryHostname = "0.0.0.0";
        if (this.serverHostname.length() != 0 && !this.queryHostname.equals(this.serverHostname)) {
            this.queryHostname = this.serverHostname;
        }
        else {
            this.serverHostname = "0.0.0.0";
            try {
                final InetAddress var2 = InetAddress.getLocalHost();
                this.queryHostname = var2.getHostAddress();
            }
            catch (UnknownHostException var3) {
                this.logWarning("Unable to determine local host IP, please set server-ip in '" + par1IServer.getSettingsFilename() + "' : " + var3.getMessage());
            }
        }
        if (this.queryPort == 0) {
            this.queryPort = this.serverPort;
            this.logInfo("Setting default query port to " + this.queryPort);
            par1IServer.setProperty("query.port", this.queryPort);
            par1IServer.setProperty("debug", false);
            par1IServer.saveProperties();
        }
        this.field_72644_p = new HashMap();
        this.output = new RConOutputStream(1460);
        this.queryClients = new HashMap();
        this.time = new Date().getTime();
    }
    
    private void sendResponsePacket(final byte[] par1ArrayOfByte, final DatagramPacket par2DatagramPacket) throws IOException {
        this.querySocket.send(new DatagramPacket(par1ArrayOfByte, par1ArrayOfByte.length, par2DatagramPacket.getSocketAddress()));
    }
    
    private boolean parseIncomingPacket(final DatagramPacket par1DatagramPacket) throws IOException {
        final byte[] var2 = par1DatagramPacket.getData();
        final int var3 = par1DatagramPacket.getLength();
        final SocketAddress var4 = par1DatagramPacket.getSocketAddress();
        this.logDebug("Packet len " + var3 + " [" + var4 + "]");
        if (3 > var3 || -2 != var2[0] || -3 != var2[1]) {
            this.logDebug("Invalid packet [" + var4 + "]");
            return false;
        }
        this.logDebug("Packet '" + RConUtils.getByteAsHexString(var2[2]) + "' [" + var4 + "]");
        Label_0362: {
            switch (var2[2]) {
                case 0: {
                    if (!this.verifyClientAuth(par1DatagramPacket)) {
                        this.logDebug("Invalid challenge [" + var4 + "]");
                        return false;
                    }
                    if (15 == var3) {
                        this.sendResponsePacket(this.createQueryResponse(par1DatagramPacket), par1DatagramPacket);
                        this.logDebug("Rules [" + var4 + "]");
                        break Label_0362;
                    }
                    final RConOutputStream var5 = new RConOutputStream(1460);
                    var5.writeInt(0);
                    var5.writeByteArray(this.getRequestID(par1DatagramPacket.getSocketAddress()));
                    var5.writeString(this.serverMotd);
                    var5.writeString("SMP");
                    var5.writeString(this.worldName);
                    var5.writeString(Integer.toString(this.getNumberOfPlayers()));
                    var5.writeString(Integer.toString(this.maxPlayers));
                    var5.writeShort((short)this.serverPort);
                    var5.writeString(this.queryHostname);
                    this.sendResponsePacket(var5.toByteArray(), par1DatagramPacket);
                    this.logDebug("Status [" + var4 + "]");
                    break Label_0362;
                }
                case 9: {
                    this.sendAuthChallenge(par1DatagramPacket);
                    this.logDebug("Challenge [" + var4 + "]");
                    return true;
                }
                default: {
                    return true;
                }
            }
        }
    }
    
    private byte[] createQueryResponse(final DatagramPacket par1DatagramPacket) throws IOException {
        final long var2 = System.currentTimeMillis();
        if (var2 < this.lastQueryResponseTime + 5000L) {
            final byte[] var3 = this.output.toByteArray();
            final byte[] var4 = this.getRequestID(par1DatagramPacket.getSocketAddress());
            var3[1] = var4[0];
            var3[2] = var4[1];
            var3[3] = var4[2];
            var3[4] = var4[3];
            return var3;
        }
        this.lastQueryResponseTime = var2;
        this.output.reset();
        this.output.writeInt(0);
        this.output.writeByteArray(this.getRequestID(par1DatagramPacket.getSocketAddress()));
        this.output.writeString("splitnum");
        this.output.writeInt(128);
        this.output.writeInt(0);
        this.output.writeString("hostname");
        this.output.writeString(this.serverMotd);
        this.output.writeString("gametype");
        this.output.writeString("SMP");
        this.output.writeString("game_id");
        this.output.writeString("MINECRAFT");
        this.output.writeString("version");
        this.output.writeString(this.server.getMinecraftVersion());
        this.output.writeString("plugins");
        this.output.writeString(this.server.getPlugins());
        this.output.writeString("map");
        this.output.writeString(this.worldName);
        this.output.writeString("numplayers");
        this.output.writeString(new StringBuilder().append(this.getNumberOfPlayers()).toString());
        this.output.writeString("maxplayers");
        this.output.writeString(new StringBuilder().append(this.maxPlayers).toString());
        this.output.writeString("hostport");
        this.output.writeString(new StringBuilder().append(this.serverPort).toString());
        this.output.writeString("hostip");
        this.output.writeString(this.queryHostname);
        this.output.writeInt(0);
        this.output.writeInt(1);
        this.output.writeString("player_");
        this.output.writeInt(0);
        final String[] var5 = this.server.getAllUsernames();
        final byte var6 = (byte)var5.length;
        for (byte var7 = (byte)(var6 - 1); var7 >= 0; --var7) {
            this.output.writeString(var5[var7]);
        }
        this.output.writeInt(0);
        return this.output.toByteArray();
    }
    
    private byte[] getRequestID(final SocketAddress par1SocketAddress) {
        return this.queryClients.get(par1SocketAddress).getRequestId();
    }
    
    private Boolean verifyClientAuth(final DatagramPacket par1DatagramPacket) {
        final SocketAddress var2 = par1DatagramPacket.getSocketAddress();
        if (!this.queryClients.containsKey(var2)) {
            return false;
        }
        final byte[] var3 = par1DatagramPacket.getData();
        return (this.queryClients.get(var2).getRandomChallenge() != RConUtils.getBytesAsBEint(var3, 7, par1DatagramPacket.getLength())) ? false : true;
    }
    
    private void sendAuthChallenge(final DatagramPacket par1DatagramPacket) throws IOException {
        final RConThreadQueryAuth var2 = new RConThreadQueryAuth(this, par1DatagramPacket);
        this.queryClients.put(par1DatagramPacket.getSocketAddress(), var2);
        this.sendResponsePacket(var2.getChallengeValue(), par1DatagramPacket);
    }
    
    private void cleanQueryClientsMap() {
        if (this.running) {
            final long var1 = System.currentTimeMillis();
            if (var1 >= this.lastAuthCheckTime + 30000L) {
                this.lastAuthCheckTime = var1;
                final Iterator var2 = this.queryClients.entrySet().iterator();
                while (var2.hasNext()) {
                    final Map.Entry var3 = var2.next();
                    if (var3.getValue().hasExpired(var1)) {
                        var2.remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void run() {
        this.logInfo("Query running on " + this.serverHostname + ":" + this.queryPort);
        this.lastAuthCheckTime = System.currentTimeMillis();
        this.incomingPacket = new DatagramPacket(this.buffer, this.buffer.length);
        try {
            while (this.running) {
                try {
                    this.querySocket.receive(this.incomingPacket);
                    this.cleanQueryClientsMap();
                    this.parseIncomingPacket(this.incomingPacket);
                }
                catch (SocketTimeoutException var10) {
                    this.cleanQueryClientsMap();
                }
                catch (PortUnreachableException ex) {}
                catch (IOException var9) {
                    this.stopWithException(var9);
                }
            }
        }
        finally {
            this.closeAllSockets();
        }
        this.closeAllSockets();
    }
    
    @Override
    public void startThread() {
        if (!this.running) {
            if (this.queryPort > 0 && 65535 >= this.queryPort) {
                if (this.initQuerySystem()) {
                    super.startThread();
                }
            }
            else {
                this.logWarning("Invalid query port " + this.queryPort + " found in '" + this.server.getSettingsFilename() + "' (queries disabled)");
            }
        }
    }
    
    private void stopWithException(final Exception par1Exception) {
        if (this.running) {
            this.logWarning("Unexpected exception, buggy JRE? (" + par1Exception.toString() + ")");
            if (!this.initQuerySystem()) {
                this.logSevere("Failed to recover from buggy JRE, shutting down!");
                this.running = false;
            }
        }
    }
    
    private boolean initQuerySystem() {
        try {
            this.registerSocket(this.querySocket = new DatagramSocket(this.queryPort, InetAddress.getByName(this.serverHostname)));
            this.querySocket.setSoTimeout(500);
            return true;
        }
        catch (SocketException var2) {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (Socket): " + var2.getMessage());
        }
        catch (UnknownHostException var3) {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (Unknown Host): " + var3.getMessage());
        }
        catch (Exception var4) {
            this.logWarning("Unable to initialise query system on " + this.serverHostname + ":" + this.queryPort + " (E): " + var4.getMessage());
        }
        return false;
    }
}
