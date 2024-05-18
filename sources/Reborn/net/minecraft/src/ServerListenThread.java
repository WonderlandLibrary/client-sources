package net.minecraft.src;

import java.util.*;
import java.io.*;
import java.net.*;

public class ServerListenThread extends Thread
{
    private final List pendingConnections;
    private final HashMap recentConnections;
    private int connectionCounter;
    private final ServerSocket myServerSocket;
    private NetworkListenThread myNetworkListenThread;
    private final InetAddress myServerAddress;
    private final int myPort;
    
    public ServerListenThread(final NetworkListenThread par1NetworkListenThread, final InetAddress par2InetAddress, final int par3) throws IOException {
        super("Listen thread");
        this.pendingConnections = Collections.synchronizedList(new ArrayList<Object>());
        this.recentConnections = new HashMap();
        this.connectionCounter = 0;
        this.myNetworkListenThread = par1NetworkListenThread;
        this.myPort = par3;
        this.myServerSocket = new ServerSocket(par3, 0, par2InetAddress);
        this.myServerAddress = ((par2InetAddress == null) ? this.myServerSocket.getInetAddress() : par2InetAddress);
        this.myServerSocket.setPerformancePreferences(0, 2, 1);
    }
    
    public void processPendingConnections() {
        final List var1 = this.pendingConnections;
        synchronized (this.pendingConnections) {
            for (int var2 = 0; var2 < this.pendingConnections.size(); ++var2) {
                final NetLoginHandler var3 = this.pendingConnections.get(var2);
                try {
                    var3.tryLogin();
                }
                catch (Exception var4) {
                    var3.raiseErrorAndDisconnect("Internal server error");
                    this.myNetworkListenThread.getServer().getLogAgent().logWarningException("Failed to handle packet for " + var3.getUsernameAndAddress() + ": " + var4, var4);
                }
                if (var3.connectionComplete) {
                    this.pendingConnections.remove(var2--);
                }
                var3.myTCPConnection.wakeThreads();
            }
        }
        // monitorexit(this.pendingConnections)
    }
    
    @Override
    public void run() {
        while (this.myNetworkListenThread.isListening) {
            try {
                final Socket var1 = this.myServerSocket.accept();
                final NetLoginHandler var2 = new NetLoginHandler(this.myNetworkListenThread.getServer(), var1, "Connection #" + this.connectionCounter++);
                this.addPendingConnection(var2);
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        this.myNetworkListenThread.getServer().getLogAgent().logInfo("Closing listening thread");
    }
    
    private void addPendingConnection(final NetLoginHandler par1NetLoginHandler) {
        if (par1NetLoginHandler == null) {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }
        final List var2 = this.pendingConnections;
        synchronized (this.pendingConnections) {
            this.pendingConnections.add(par1NetLoginHandler);
        }
        // monitorexit(this.pendingConnections)
    }
    
    public void func_71769_a(final InetAddress par1InetAddress) {
        if (par1InetAddress != null) {
            final HashMap var2 = this.recentConnections;
            synchronized (this.recentConnections) {
                this.recentConnections.remove(par1InetAddress);
            }
            // monitorexit(this.recentConnections)
        }
    }
    
    public void func_71768_b() {
        try {
            this.myServerSocket.close();
        }
        catch (Throwable t) {}
    }
    
    public InetAddress getInetAddress() {
        return this.myServerAddress;
    }
    
    public int getMyPort() {
        return this.myPort;
    }
}
