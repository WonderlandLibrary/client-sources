package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;
import java.io.*;

public abstract class NetworkListenThread
{
    private final MinecraftServer mcServer;
    private final List connections;
    public volatile boolean isListening;
    
    public NetworkListenThread(final MinecraftServer par1MinecraftServer) throws IOException {
        this.connections = Collections.synchronizedList(new ArrayList<Object>());
        this.isListening = false;
        this.mcServer = par1MinecraftServer;
        this.isListening = true;
    }
    
    public void addPlayer(final NetServerHandler par1NetServerHandler) {
        this.connections.add(par1NetServerHandler);
    }
    
    public void stopListening() {
        this.isListening = false;
    }
    
    public void networkTick() {
        for (int var1 = 0; var1 < this.connections.size(); ++var1) {
            final NetServerHandler var2 = this.connections.get(var1);
            try {
                var2.networkTick();
            }
            catch (Exception var4) {
                if (var2.netManager instanceof MemoryConnection) {
                    final CrashReport var3 = CrashReport.makeCrashReport(var4, "Ticking memory connection");
                    throw new ReportedException(var3);
                }
                this.mcServer.getLogAgent().logWarningException("Failed to handle packet for " + var2.playerEntity.getEntityName() + "/" + var2.playerEntity.getPlayerIP() + ": " + var4, var4);
                var2.kickPlayerFromServer("Internal server error");
            }
            if (var2.connectionClosed) {
                this.connections.remove(var1--);
            }
            var2.netManager.wakeThreads();
        }
    }
    
    public MinecraftServer getServer() {
        return this.mcServer;
    }
}
