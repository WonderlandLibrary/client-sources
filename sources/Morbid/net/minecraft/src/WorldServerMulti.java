package net.minecraft.src;

import net.minecraft.server.*;

public class WorldServerMulti extends WorldServer
{
    public WorldServerMulti(final MinecraftServer par1MinecraftServer, final ISaveHandler par2ISaveHandler, final String par3Str, final int par4, final WorldSettings par5WorldSettings, final WorldServer par6WorldServer, final Profiler par7Profiler, final ILogAgent par8ILogAgent) {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, par5WorldSettings, par7Profiler, par8ILogAgent);
        this.mapStorage = par6WorldServer.mapStorage;
        this.worldScoreboard = par6WorldServer.getScoreboard();
        this.worldInfo = new DerivedWorldInfo(par6WorldServer.getWorldInfo());
    }
    
    @Override
    protected void saveLevel() throws MinecraftException {
    }
}
