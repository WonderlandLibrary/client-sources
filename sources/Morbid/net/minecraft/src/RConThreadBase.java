package net.minecraft.src;

import java.net.*;
import java.io.*;
import java.util.*;

public abstract class RConThreadBase implements Runnable
{
    protected boolean running;
    protected IServer server;
    protected Thread rconThread;
    protected int field_72615_d;
    protected List socketList;
    protected List serverSocketList;
    
    RConThreadBase(final IServer par1IServer) {
        this.running = false;
        this.field_72615_d = 5;
        this.socketList = new ArrayList();
        this.serverSocketList = new ArrayList();
        this.server = par1IServer;
        if (this.server.isDebuggingEnabled()) {
            this.logWarning("Debugging is enabled, performance maybe reduced!");
        }
    }
    
    public synchronized void startThread() {
        (this.rconThread = new Thread(this)).start();
        this.running = true;
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    protected void logDebug(final String par1Str) {
        this.server.logDebug(par1Str);
    }
    
    protected void logInfo(final String par1Str) {
        this.server.logInfo(par1Str);
    }
    
    protected void logWarning(final String par1Str) {
        this.server.logWarning(par1Str);
    }
    
    protected void logSevere(final String par1Str) {
        this.server.logSevere(par1Str);
    }
    
    protected int getNumberOfPlayers() {
        return this.server.getCurrentPlayerCount();
    }
    
    protected void registerSocket(final DatagramSocket par1DatagramSocket) {
        this.logDebug("registerSocket: " + par1DatagramSocket);
        this.socketList.add(par1DatagramSocket);
    }
    
    protected boolean closeSocket(final DatagramSocket par1DatagramSocket, final boolean par2) {
        this.logDebug("closeSocket: " + par1DatagramSocket);
        if (par1DatagramSocket == null) {
            return false;
        }
        boolean var3 = false;
        if (!par1DatagramSocket.isClosed()) {
            par1DatagramSocket.close();
            var3 = true;
        }
        if (par2) {
            this.socketList.remove(par1DatagramSocket);
        }
        return var3;
    }
    
    protected boolean closeServerSocket(final ServerSocket par1ServerSocket) {
        return this.closeServerSocket_do(par1ServerSocket, true);
    }
    
    protected boolean closeServerSocket_do(final ServerSocket par1ServerSocket, final boolean par2) {
        this.logDebug("closeSocket: " + par1ServerSocket);
        if (par1ServerSocket == null) {
            return false;
        }
        boolean var3 = false;
        try {
            if (!par1ServerSocket.isClosed()) {
                par1ServerSocket.close();
                var3 = true;
            }
        }
        catch (IOException var4) {
            this.logWarning("IO: " + var4.getMessage());
        }
        if (par2) {
            this.serverSocketList.remove(par1ServerSocket);
        }
        return var3;
    }
    
    protected void closeAllSockets() {
        this.closeAllSockets_do(false);
    }
    
    protected void closeAllSockets_do(final boolean par1) {
        int var2 = 0;
        for (final DatagramSocket var4 : this.socketList) {
            if (this.closeSocket(var4, false)) {
                ++var2;
            }
        }
        this.socketList.clear();
        for (final ServerSocket var5 : this.serverSocketList) {
            if (this.closeServerSocket_do(var5, false)) {
                ++var2;
            }
        }
        this.serverSocketList.clear();
        if (par1 && var2 > 0) {
            this.logWarning("Force closed " + var2 + " sockets");
        }
    }
}
