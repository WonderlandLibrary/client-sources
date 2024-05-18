package net.minecraft.src;

import net.minecraft.server.*;

public class DemoWorldServer extends WorldServer
{
    private static final long demoWorldSeed;
    public static final WorldSettings demoWorldSettings;
    
    static {
        demoWorldSeed = "North Carolina".hashCode();
        demoWorldSettings = new WorldSettings(DemoWorldServer.demoWorldSeed, EnumGameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
    }
    
    public DemoWorldServer(final MinecraftServer par1MinecraftServer, final ISaveHandler par2ISaveHandler, final String par3Str, final int par4, final Profiler par5Profiler, final ILogAgent par6ILogAgent) {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, DemoWorldServer.demoWorldSettings, par5Profiler, par6ILogAgent);
    }
}
