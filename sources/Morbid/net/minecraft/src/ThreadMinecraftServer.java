package net.minecraft.src;

import net.minecraft.server.*;

public class ThreadMinecraftServer extends Thread
{
    final MinecraftServer theServer;
    
    public ThreadMinecraftServer(final MinecraftServer par1MinecraftServer, final String par2Str) {
        super(par2Str);
        this.theServer = par1MinecraftServer;
    }
    
    @Override
    public void run() {
        this.theServer.run();
    }
}
