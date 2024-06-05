package net.minecraft.src;

import java.io.*;

class DedicatedServerCommandThread extends Thread
{
    final DedicatedServer server;
    
    DedicatedServerCommandThread(final DedicatedServer par1DedicatedServer) {
        this.server = par1DedicatedServer;
    }
    
    @Override
    public void run() {
        final BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!this.server.isServerStopped() && this.server.isServerRunning()) {
                final String var2;
                if ((var2 = var1.readLine()) == null) {
                    break;
                }
                this.server.addPendingCommand(var2, this.server);
            }
        }
        catch (IOException var3) {
            var3.printStackTrace();
        }
    }
}
