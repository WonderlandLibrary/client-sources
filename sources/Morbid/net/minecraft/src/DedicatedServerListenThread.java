package net.minecraft.src;

import net.minecraft.server.*;
import java.net.*;
import java.io.*;

public class DedicatedServerListenThread extends NetworkListenThread
{
    private final ServerListenThread theServerListenThread;
    
    public DedicatedServerListenThread(final MinecraftServer par1MinecraftServer, final InetAddress par2InetAddress, final int par3) throws IOException {
        super(par1MinecraftServer);
        (this.theServerListenThread = new ServerListenThread(this, par2InetAddress, par3)).start();
    }
    
    @Override
    public void stopListening() {
        super.stopListening();
        this.theServerListenThread.func_71768_b();
        this.theServerListenThread.interrupt();
    }
    
    @Override
    public void networkTick() {
        this.theServerListenThread.processPendingConnections();
        super.networkTick();
    }
    
    public DedicatedServer getDedicatedServer() {
        return (DedicatedServer)super.getServer();
    }
    
    public void func_71761_a(final InetAddress par1InetAddress) {
        this.theServerListenThread.func_71769_a(par1InetAddress);
    }
    
    @Override
    public MinecraftServer getServer() {
        return this.getDedicatedServer();
    }
}
