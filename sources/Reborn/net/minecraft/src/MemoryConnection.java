package net.minecraft.src;

import java.net.*;
import java.util.*;

public class MemoryConnection implements INetworkManager
{
    private static final SocketAddress mySocketAddress;
    private final List readPacketCache;
    private final ILogAgent field_98214_c;
    private MemoryConnection pairedConnection;
    private NetHandler myNetHandler;
    private boolean shuttingDown;
    private String shutdownReason;
    private Object[] field_74439_g;
    private boolean gamePaused;
    
    static {
        mySocketAddress = new InetSocketAddress("127.0.0.1", 0);
    }
    
    public MemoryConnection(final ILogAgent par1ILogAgent, final NetHandler par2NetHandler) {
        this.readPacketCache = Collections.synchronizedList(new ArrayList<Object>());
        this.shuttingDown = false;
        this.shutdownReason = "";
        this.gamePaused = false;
        this.myNetHandler = par2NetHandler;
        this.field_98214_c = par1ILogAgent;
    }
    
    @Override
    public void setNetHandler(final NetHandler par1NetHandler) {
        this.myNetHandler = par1NetHandler;
    }
    
    @Override
    public void addToSendQueue(final Packet par1Packet) {
        if (!this.shuttingDown) {
            this.pairedConnection.processOrCachePacket(par1Packet);
        }
    }
    
    @Override
    public void closeConnections() {
        this.pairedConnection = null;
        this.myNetHandler = null;
    }
    
    public boolean isConnectionActive() {
        return !this.shuttingDown && this.pairedConnection != null;
    }
    
    @Override
    public void wakeThreads() {
    }
    
    @Override
    public void processReadPackets() {
        int var1 = 2500;
        while (var1-- >= 0 && !this.readPacketCache.isEmpty()) {
            final Packet var2 = this.readPacketCache.remove(0);
            var2.processPacket(this.myNetHandler);
        }
        if (this.readPacketCache.size() > var1) {
            this.field_98214_c.logWarning("Memory connection overburdened; after processing 2500 packets, we still have " + this.readPacketCache.size() + " to go!");
        }
        if (this.shuttingDown && this.readPacketCache.isEmpty()) {
            this.myNetHandler.handleErrorMessage(this.shutdownReason, this.field_74439_g);
        }
    }
    
    @Override
    public SocketAddress getSocketAddress() {
        return MemoryConnection.mySocketAddress;
    }
    
    @Override
    public void serverShutdown() {
        this.shuttingDown = true;
    }
    
    @Override
    public void networkShutdown(final String par1Str, final Object... par2ArrayOfObj) {
        this.shuttingDown = true;
        this.shutdownReason = par1Str;
        this.field_74439_g = par2ArrayOfObj;
    }
    
    @Override
    public int packetSize() {
        return 0;
    }
    
    public void pairWith(final MemoryConnection par1MemoryConnection) {
        this.pairedConnection = par1MemoryConnection;
        par1MemoryConnection.pairedConnection = this;
    }
    
    public boolean isGamePaused() {
        return this.gamePaused;
    }
    
    public void setGamePaused(final boolean par1) {
        this.gamePaused = par1;
    }
    
    public MemoryConnection getPairedConnection() {
        return this.pairedConnection;
    }
    
    public void processOrCachePacket(final Packet par1Packet) {
        if (par1Packet.canProcessAsync() && this.myNetHandler.canProcessPacketsAsync()) {
            par1Packet.processPacket(this.myNetHandler);
        }
        else {
            this.readPacketCache.add(par1Packet);
        }
    }
}
