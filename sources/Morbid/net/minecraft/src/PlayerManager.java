package net.minecraft.src;

import java.util.*;

public class PlayerManager
{
    private final WorldServer theWorldServer;
    private final List players;
    private final LongHashMap playerInstances;
    private List chunkWatcherWithPlayers;
    public CompactArrayList chunkCoordsNotLoaded;
    private int playerViewRadius;
    private final int[][] xzDirectionsConst;
    
    public PlayerManager(final WorldServer par1WorldServer, final int par2) {
        this.players = new ArrayList();
        this.playerInstances = new LongHashMap();
        this.chunkWatcherWithPlayers = new ArrayList();
        this.chunkCoordsNotLoaded = new CompactArrayList(100, 0.8f);
        this.xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        if (par2 > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        }
        if (par2 < 3) {
            throw new IllegalArgumentException("Too small view radius!");
        }
        this.playerViewRadius = Config.getChunkViewDistance();
        Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (constructor)");
        this.theWorldServer = par1WorldServer;
    }
    
    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }
    
    public void updatePlayerInstances() {
        for (int var1 = 0; var1 < this.chunkWatcherWithPlayers.size(); ++var1) {
            this.chunkWatcherWithPlayers.get(var1).sendChunkUpdate();
        }
        this.chunkWatcherWithPlayers.clear();
        if (this.players.isEmpty()) {
            final WorldProvider var2 = this.theWorldServer.provider;
            if (!var2.canRespawnHere()) {
                this.theWorldServer.theChunkProviderServer.unloadAllChunks();
            }
        }
        if (this.playerViewRadius != Config.getChunkViewDistance()) {
            this.setPlayerViewRadius(Config.getChunkViewDistance());
        }
        if (this.chunkCoordsNotLoaded.size() > 0) {
            for (int var1 = 0; var1 < this.players.size(); ++var1) {
                final EntityPlayerMP var3 = this.players.get(var1);
                final int var4 = var3.chunkCoordX;
                final int var5 = var3.chunkCoordZ;
                final int var6 = this.playerViewRadius + 1;
                final int var7 = var6 / 2;
                int var9;
                final int var8 = var9 = var6 * var6 + var7 * var7;
                int var10 = -1;
                PlayerInstance var11 = null;
                ChunkCoordIntPair var12 = null;
                for (int var13 = 0; var13 < this.chunkCoordsNotLoaded.size(); ++var13) {
                    final ChunkCoordIntPair var14 = (ChunkCoordIntPair)this.chunkCoordsNotLoaded.get(var13);
                    if (var14 != null) {
                        final PlayerInstance var15 = this.getOrCreateChunkWatcher(var14.chunkXPos, var14.chunkZPos, false);
                        if (var15 != null && !var15.chunkLoaded) {
                            final int var16 = var4 - var14.chunkXPos;
                            final int var17 = var5 - var14.chunkZPos;
                            final int var18 = var16 * var16 + var17 * var17;
                            if (var18 < var9) {
                                var9 = var18;
                                var10 = var13;
                                var11 = var15;
                                var12 = var14;
                            }
                        }
                        else {
                            this.chunkCoordsNotLoaded.set(var13, null);
                        }
                    }
                }
                if (var10 >= 0) {
                    this.chunkCoordsNotLoaded.set(var10, null);
                }
                if (var11 != null) {
                    var11.chunkLoaded = true;
                    this.getWorldServer().theChunkProviderServer.loadChunk(var12.chunkXPos, var12.chunkZPos);
                    var11.sendThisChunkToAllPlayers();
                    break;
                }
            }
            this.chunkCoordsNotLoaded.compact();
        }
    }
    
    private PlayerInstance getOrCreateChunkWatcher(final int par1, final int par2, final boolean par3) {
        return this.getOrCreateChunkWatcher(par1, par2, par3, false);
    }
    
    private PlayerInstance getOrCreateChunkWatcher(final int var1, final int var2, final boolean var3, final boolean var4) {
        final long var5 = var1 + 2147483647L | var2 + 2147483647L << 32;
        PlayerInstance var6 = (PlayerInstance)this.playerInstances.getValueByKey(var5);
        if (var6 == null && var3) {
            var6 = new PlayerInstance(this, var1, var2, var4);
            this.playerInstances.add(var5, var6);
        }
        return var6;
    }
    
    public void flagChunkForUpdate(final int par1, final int par2, final int par3) {
        final int var4 = par1 >> 4;
        final int var5 = par3 >> 4;
        final PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
        if (var6 != null) {
            var6.flagChunkForUpdate(par1 & 0xF, par2, par3 & 0xF);
        }
    }
    
    public void addPlayer(final EntityPlayerMP par1EntityPlayerMP) {
        final int var2 = (int)par1EntityPlayerMP.posX >> 4;
        final int var3 = (int)par1EntityPlayerMP.posZ >> 4;
        par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
        final ArrayList var4 = new ArrayList(1);
        for (int var5 = var2 - this.playerViewRadius; var5 <= var2 + this.playerViewRadius; ++var5) {
            for (int var6 = var3 - this.playerViewRadius; var6 <= var3 + this.playerViewRadius; ++var6) {
                this.getOrCreateChunkWatcher(var5, var6, true).addPlayerToChunkWatchingList(par1EntityPlayerMP);
                if (var5 >= var2 - 1 && var5 <= var2 + 1 && var6 >= var3 - 1 && var6 <= var3 + 1) {
                    final Chunk var7 = this.getWorldServer().theChunkProviderServer.loadChunk(var5, var6);
                    var4.add(var7);
                }
            }
        }
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var4));
        this.players.add(par1EntityPlayerMP);
        this.filterChunkLoadQueue(par1EntityPlayerMP);
    }
    
    public void filterChunkLoadQueue(final EntityPlayerMP par1EntityPlayerMP) {
        final ArrayList var2 = new ArrayList(par1EntityPlayerMP.loadedChunks);
        int var3 = 0;
        final int var4 = this.playerViewRadius;
        final int var5 = (int)par1EntityPlayerMP.posX >> 4;
        final int var6 = (int)par1EntityPlayerMP.posZ >> 4;
        int var7 = 0;
        int var8 = 0;
        ChunkCoordIntPair var9 = PlayerInstance.getChunkLocation(this.getOrCreateChunkWatcher(var5, var6, true));
        par1EntityPlayerMP.loadedChunks.clear();
        if (var2.contains(var9)) {
            par1EntityPlayerMP.loadedChunks.add(var9);
        }
        for (int var10 = 1; var10 <= var4 * 2; ++var10) {
            for (int var11 = 0; var11 < 2; ++var11) {
                final int[] var12 = this.xzDirectionsConst[var3++ % 4];
                for (int var13 = 0; var13 < var10; ++var13) {
                    var7 += var12[0];
                    var8 += var12[1];
                    var9 = PlayerInstance.getChunkLocation(this.getOrCreateChunkWatcher(var5 + var7, var6 + var8, true));
                    if (var2.contains(var9)) {
                        par1EntityPlayerMP.loadedChunks.add(var9);
                    }
                }
            }
        }
        var3 %= 4;
        for (int var10 = 0; var10 < var4 * 2; ++var10) {
            var7 += this.xzDirectionsConst[var3][0];
            var8 += this.xzDirectionsConst[var3][1];
            var9 = PlayerInstance.getChunkLocation(this.getOrCreateChunkWatcher(var5 + var7, var6 + var8, true));
            if (var2.contains(var9)) {
                par1EntityPlayerMP.loadedChunks.add(var9);
            }
        }
    }
    
    public void removePlayer(final EntityPlayerMP par1EntityPlayerMP) {
        final int var2 = (int)par1EntityPlayerMP.managedPosX >> 4;
        final int var3 = (int)par1EntityPlayerMP.managedPosZ >> 4;
        for (int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
            for (int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
                final PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
                if (var6 != null) {
                    var6.sendThisChunkToPlayer(par1EntityPlayerMP, false);
                }
            }
        }
        this.players.remove(par1EntityPlayerMP);
    }
    
    private boolean func_72684_a(final int par1, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1 - par3;
        final int var7 = par2 - par4;
        return var6 >= -par5 && var6 <= par5 && (var7 >= -par5 && var7 <= par5);
    }
    
    public void updateMountedMovingPlayer(final EntityPlayerMP par1EntityPlayerMP) {
        final int var2 = (int)par1EntityPlayerMP.posX >> 4;
        final int var3 = (int)par1EntityPlayerMP.posZ >> 4;
        final double var4 = par1EntityPlayerMP.managedPosX - par1EntityPlayerMP.posX;
        final double var5 = par1EntityPlayerMP.managedPosZ - par1EntityPlayerMP.posZ;
        final double var6 = var4 * var4 + var5 * var5;
        if (var6 >= 64.0) {
            final int var7 = (int)par1EntityPlayerMP.managedPosX >> 4;
            final int var8 = (int)par1EntityPlayerMP.managedPosZ >> 4;
            final int var9 = this.playerViewRadius;
            final int var10 = var2 - var7;
            final int var11 = var3 - var8;
            if (var10 != 0 || var11 != 0) {
                for (int var12 = var2 - var9; var12 <= var2 + var9; ++var12) {
                    for (int var13 = var3 - var9; var13 <= var3 + var9; ++var13) {
                        if (!this.func_72684_a(var12, var13, var7, var8, var9)) {
                            this.getOrCreateChunkWatcher(var12, var13, true, true).addPlayerToChunkWatchingList(par1EntityPlayerMP);
                        }
                        if (!this.func_72684_a(var12 - var10, var13 - var11, var2, var3, var9)) {
                            final PlayerInstance var14 = this.getOrCreateChunkWatcher(var12 - var10, var13 - var11, false);
                            if (var14 != null) {
                                var14.sendThisChunkToPlayer(par1EntityPlayerMP);
                            }
                        }
                    }
                }
                this.filterChunkLoadQueue(par1EntityPlayerMP);
                par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
                par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
            }
        }
    }
    
    public boolean isPlayerWatchingChunk(final EntityPlayerMP par1EntityPlayerMP, final int par2, final int par3) {
        final PlayerInstance var4 = this.getOrCreateChunkWatcher(par2, par3, false);
        return var4 != null && (PlayerInstance.getPlayersInChunk(var4).contains(par1EntityPlayerMP) && !par1EntityPlayerMP.loadedChunks.contains(PlayerInstance.getChunkLocation(var4)));
    }
    
    public static int getFurthestViewableBlock(final int par0) {
        return par0 * 16 - 16;
    }
    
    static WorldServer getWorldServer(final PlayerManager par0PlayerManager) {
        return par0PlayerManager.theWorldServer;
    }
    
    static LongHashMap getChunkWatchers(final PlayerManager par0PlayerManager) {
        return par0PlayerManager.playerInstances;
    }
    
    static List getChunkWatchersWithPlayers(final PlayerManager par0PlayerManager) {
        return par0PlayerManager.chunkWatcherWithPlayers;
    }
    
    private void setPlayerViewRadius(final int var1) {
        if (this.playerViewRadius != var1) {
            final EntityPlayerMP[] var2 = this.players.toArray(new EntityPlayerMP[this.players.size()]);
            for (int var3 = 0; var3 < var2.length; ++var3) {
                final EntityPlayerMP var4 = var2[var3];
                this.removePlayer(var4);
            }
            this.playerViewRadius = var1;
            for (int var3 = 0; var3 < var2.length; ++var3) {
                final EntityPlayerMP var4 = var2[var3];
                this.addPlayer(var4);
            }
            Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (detect)");
        }
    }
}
