package net.minecraft.src;

import net.minecraft.server.*;
import java.lang.reflect.*;
import java.util.*;

public class WorldServerOF extends WorldServer
{
    private NextTickHashSet nextTickHashSet;
    private TreeSet pendingTickList;
    
    public WorldServerOF(final MinecraftServer var1, final ISaveHandler var2, final String var3, final int var4, final WorldSettings var5, final Profiler var6, final ILogAgent var7) {
        super(var1, var2, var3, var4, var5, var6, var7);
        this.nextTickHashSet = null;
        this.pendingTickList = null;
        this.fixSetNextTicks();
    }
    
    private void fixSetNextTicks() {
        try {
            final Field[] var1 = WorldServer.class.getDeclaredFields();
            if (var1.length > 5) {
                final Field var2 = var1[3];
                var2.setAccessible(true);
                if (var2.getType() == Set.class) {
                    final Set var3 = (Set)var2.get(this);
                    final NextTickHashSet var4 = new NextTickHashSet(var3);
                    var2.set(this, var4);
                    final Field var5 = var1[4];
                    var5.setAccessible(true);
                    this.pendingTickList = (TreeSet)var5.get(this);
                    this.nextTickHashSet = var4;
                }
            }
        }
        catch (Exception var6) {
            Config.dbg("Error setting WorldServer.nextTickSet: " + var6.getMessage());
        }
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk var1, final boolean var2) {
        if (this.nextTickHashSet != null && this.pendingTickList != null) {
            ArrayList var3 = null;
            final ChunkCoordIntPair var4 = var1.getChunkCoordIntPair();
            final int var5 = var4.chunkXPos << 4;
            final int var6 = var5 + 16;
            final int var7 = var4.chunkZPos << 4;
            final int var8 = var7 + 16;
            final Iterator var9 = this.nextTickHashSet.getNextTickEntries(var4.chunkXPos, var4.chunkZPos);
            while (var9.hasNext()) {
                final NextTickListEntry var10 = var9.next();
                if (var10.xCoord >= var5 && var10.xCoord < var6 && var10.zCoord >= var7 && var10.zCoord < var8) {
                    if (var2) {
                        this.pendingTickList.remove(var10);
                        var9.remove();
                    }
                    if (var3 == null) {
                        var3 = new ArrayList();
                    }
                    var3.add(var10);
                }
                else {
                    Config.dbg("Not matching: " + var5 + "," + var7);
                }
            }
            return var3;
        }
        return super.getPendingBlockUpdates(var1, var2);
    }
    
    @Override
    protected void updateWeather() {
        if (Config.isWeatherEnabled()) {
            super.updateWeather();
        }
        else {
            this.fixWorldWeather();
        }
        if (!Config.isTimeDefault()) {
            this.fixWorldTime();
        }
    }
    
    private void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0f);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.getMinecraftServer().getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(2, 0));
        }
    }
    
    private void fixWorldTime() {
        if (this.worldInfo.getGameType().getID() == 1) {
            final long var1 = this.getWorldTime();
            final long var2 = var1 % 24000L;
            if (Config.isTimeDayOnly()) {
                if (var2 <= 1000L) {
                    this.setWorldTime(var1 - var2 + 1001L);
                }
                if (var2 >= 11000L) {
                    this.setWorldTime(var1 - var2 + 24001L);
                }
            }
            if (Config.isTimeNightOnly()) {
                if (var2 <= 14000L) {
                    this.setWorldTime(var1 - var2 + 14001L);
                }
                if (var2 >= 22000L) {
                    this.setWorldTime(var1 - var2 + 24000L + 14001L);
                }
            }
        }
    }
}
