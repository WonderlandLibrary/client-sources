// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.world.demo;

import net.minecraft.profiler.Profiler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldServer;

public class DemoWorldServer extends WorldServer
{
    private static final long demoWorldSeed;
    public static final WorldSettings demoWorldSettings;
    private static final String __OBFID = "CL_00001428";
    
    static {
        demoWorldSeed = "North Carolina".hashCode();
        demoWorldSettings = new WorldSettings(DemoWorldServer.demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
    }
    
    public DemoWorldServer(final MinecraftServer server, final ISaveHandler saveHandlerIn, final WorldInfo worldInfoIn, final int dimensionId, final Profiler profilerIn) {
        super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
        this.worldInfo.populateFromWorldSettings(DemoWorldServer.demoWorldSettings);
    }
}
