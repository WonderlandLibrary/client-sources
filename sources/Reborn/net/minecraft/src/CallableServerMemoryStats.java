package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.server.*;

public class CallableServerMemoryStats implements Callable
{
    final MinecraftServer mcServer;
    
    public CallableServerMemoryStats(final MinecraftServer par1) {
        this.mcServer = par1;
    }
    
    public String callServerMemoryStats() {
        return String.valueOf(MinecraftServer.getServerConfigurationManager(this.mcServer).getCurrentPlayerCount()) + " / " + MinecraftServer.getServerConfigurationManager(this.mcServer).getMaxPlayers() + "; " + MinecraftServer.getServerConfigurationManager(this.mcServer).playerEntityList;
    }
    
    @Override
    public Object call() {
        return this.callServerMemoryStats();
    }
}
