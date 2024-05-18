package net.minecraft.src;

import net.minecraft.server.*;
import java.io.*;
import java.net.*;

public class IntegratedServerListenThread extends NetworkListenThread
{
    private final MemoryConnection netMemoryConnection;
    private MemoryConnection theMemoryConnection;
    private String field_71759_e;
    private boolean field_71756_f;
    private ServerListenThread myServerListenThread;
    
    public IntegratedServerListenThread(final IntegratedServer par1IntegratedServer) throws IOException {
        super(par1IntegratedServer);
        this.field_71756_f = false;
        this.netMemoryConnection = new MemoryConnection(par1IntegratedServer.getLogAgent(), null);
    }
    
    public void func_71754_a(final MemoryConnection par1MemoryConnection, final String par2Str) {
        this.theMemoryConnection = par1MemoryConnection;
        this.field_71759_e = par2Str;
    }
    
    public String func_71755_c() throws IOException {
        if (this.myServerListenThread == null) {
            int var1 = -1;
            try {
                var1 = HttpUtil.func_76181_a();
            }
            catch (IOException ex) {}
            if (var1 <= 0) {
                var1 = 25564;
            }
            try {
                (this.myServerListenThread = new ServerListenThread(this, null, var1)).start();
            }
            catch (IOException var2) {
                throw var2;
            }
        }
        return String.valueOf(this.myServerListenThread.getInetAddress().getHostAddress()) + ":" + this.myServerListenThread.getMyPort();
    }
    
    @Override
    public void stopListening() {
        super.stopListening();
        if (this.myServerListenThread != null) {
            this.getIntegratedServer().getLogAgent().logInfo("Stopping server connection");
            this.myServerListenThread.func_71768_b();
            this.myServerListenThread.interrupt();
            this.myServerListenThread = null;
        }
    }
    
    @Override
    public void networkTick() {
        if (this.theMemoryConnection != null) {
            final EntityPlayerMP var1 = this.getIntegratedServer().getConfigurationManager().createPlayerForUser(this.field_71759_e);
            if (var1 != null) {
                this.netMemoryConnection.pairWith(this.theMemoryConnection);
                this.field_71756_f = true;
                this.getIntegratedServer().getConfigurationManager().initializeConnectionToPlayer(this.netMemoryConnection, var1);
            }
            this.theMemoryConnection = null;
            this.field_71759_e = null;
        }
        if (this.myServerListenThread != null) {
            this.myServerListenThread.processPendingConnections();
        }
        super.networkTick();
    }
    
    public IntegratedServer getIntegratedServer() {
        return (IntegratedServer)super.getServer();
    }
    
    public boolean isGamePaused() {
        return this.field_71756_f && this.netMemoryConnection.getPairedConnection().isConnectionActive() && this.netMemoryConnection.getPairedConnection().isGamePaused();
    }
    
    @Override
    public MinecraftServer getServer() {
        return this.getIntegratedServer();
    }
}
