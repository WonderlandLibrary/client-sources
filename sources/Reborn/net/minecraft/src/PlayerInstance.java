package net.minecraft.src;

import java.util.*;

class PlayerInstance
{
    private final List playersInChunk;
    private final ChunkCoordIntPair chunkLocation;
    private short[] locationOfBlockChange;
    private int numberOfTilesToUpdate;
    private int field_73260_f;
    final PlayerManager thePlayerManager;
    public boolean chunkLoaded;
    
    public PlayerInstance(final PlayerManager par1PlayerManager, final int par2, final int par3) {
        this(par1PlayerManager, par2, par3, false);
    }
    
    public PlayerInstance(final PlayerManager var1, final int var2, final int var3, final boolean var4) {
        this.chunkLoaded = false;
        this.thePlayerManager = var1;
        this.playersInChunk = new ArrayList();
        this.locationOfBlockChange = new short[64];
        this.numberOfTilesToUpdate = 0;
        this.chunkLocation = new ChunkCoordIntPair(var2, var3);
        final boolean var5 = var4 && Config.isLazyChunkLoading();
        if (var5 && !var1.getWorldServer().theChunkProviderServer.chunkExists(var2, var3)) {
            this.thePlayerManager.chunkCoordsNotLoaded.add(this.chunkLocation);
            this.chunkLoaded = false;
        }
        else {
            var1.getWorldServer().theChunkProviderServer.loadChunk(var2, var3);
            this.chunkLoaded = true;
        }
    }
    
    public void addPlayerToChunkWatchingList(final EntityPlayerMP par1EntityPlayerMP) {
        if (this.playersInChunk.contains(par1EntityPlayerMP)) {
            throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + this.chunkLocation.chunkXPos + ", " + this.chunkLocation.chunkZPos);
        }
        this.playersInChunk.add(par1EntityPlayerMP);
        par1EntityPlayerMP.loadedChunks.add(this.chunkLocation);
    }
    
    public void sendThisChunkToPlayer(final EntityPlayerMP par1EntityPlayerMP) {
        this.sendThisChunkToPlayer(par1EntityPlayerMP, true);
    }
    
    public void sendThisChunkToPlayer(final EntityPlayerMP var1, final boolean var2) {
        if (this.playersInChunk.contains(var1)) {
            if (var2) {
                var1.playerNetServerHandler.sendPacketToPlayer(new Packet51MapChunk(PlayerManager.getWorldServer(this.thePlayerManager).getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), true, 0));
            }
            this.playersInChunk.remove(var1);
            var1.loadedChunks.remove(this.chunkLocation);
            if (Reflector.EventBus.exists()) {
                Reflector.postForgeBusEvent(Reflector.ChunkWatchEvent_UnWatch_Constructor, this.chunkLocation, var1);
            }
            if (this.playersInChunk.isEmpty()) {
                final long var3 = this.chunkLocation.chunkXPos + 2147483647L | this.chunkLocation.chunkZPos + 2147483647L << 32;
                PlayerManager.getChunkWatchers(this.thePlayerManager).remove(var3);
                if (this.numberOfTilesToUpdate > 0) {
                    PlayerManager.getChunkWatchersWithPlayers(this.thePlayerManager).remove(this);
                }
                if (this.chunkLoaded) {
                    this.thePlayerManager.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                }
            }
        }
    }
    
    public void flagChunkForUpdate(final int par1, final int par2, final int par3) {
        if (this.numberOfTilesToUpdate == 0) {
            PlayerManager.getChunkWatchersWithPlayers(this.thePlayerManager).add(this);
        }
        this.field_73260_f |= 1 << (par2 >> 4);
        if (this.numberOfTilesToUpdate < 64) {
            final short var4 = (short)(par1 << 12 | par3 << 8 | par2);
            for (int var5 = 0; var5 < this.numberOfTilesToUpdate; ++var5) {
                if (this.locationOfBlockChange[var5] == var4) {
                    return;
                }
            }
            this.locationOfBlockChange[this.numberOfTilesToUpdate++] = var4;
        }
    }
    
    public void sendToAllPlayersWatchingChunk(final Packet par1Packet) {
        for (int var2 = 0; var2 < this.playersInChunk.size(); ++var2) {
            final EntityPlayerMP var3 = this.playersInChunk.get(var2);
            if (!var3.loadedChunks.contains(this.chunkLocation)) {
                var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
            }
        }
    }
    
    public void sendChunkUpdate() {
        if (this.numberOfTilesToUpdate != 0) {
            if (this.numberOfTilesToUpdate == 1) {
                final int var1 = this.chunkLocation.chunkXPos * 16 + (this.locationOfBlockChange[0] >> 12 & 0xF);
                final int var2 = this.locationOfBlockChange[0] & 0xFF;
                final int var3 = this.chunkLocation.chunkZPos * 16 + (this.locationOfBlockChange[0] >> 8 & 0xF);
                this.sendToAllPlayersWatchingChunk(new Packet53BlockChange(var1, var2, var3, PlayerManager.getWorldServer(this.thePlayerManager)));
                if (PlayerManager.getWorldServer(this.thePlayerManager).blockHasTileEntity(var1, var2, var3)) {
                    this.sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(this.thePlayerManager).getBlockTileEntity(var1, var2, var3));
                }
            }
            else if (this.numberOfTilesToUpdate == 64) {
                final int var1 = this.chunkLocation.chunkXPos * 16;
                final int var2 = this.chunkLocation.chunkZPos * 16;
                this.sendToAllPlayersWatchingChunk(new Packet51MapChunk(PlayerManager.getWorldServer(this.thePlayerManager).getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), false, this.field_73260_f));
                for (int var3 = 0; var3 < 16; ++var3) {
                    if ((this.field_73260_f & 1 << var3) != 0x0) {
                        final int var4 = var3 << 4;
                        final List var5 = PlayerManager.getWorldServer(this.thePlayerManager).getAllTileEntityInBox(var1, var4, var2, var1 + 16, var4 + 16, var2 + 15);
                        for (int var6 = 0; var6 < var5.size(); ++var6) {
                            this.sendTileToAllPlayersWatchingChunk(var5.get(var6));
                        }
                    }
                }
            }
            else {
                this.sendToAllPlayersWatchingChunk(new Packet52MultiBlockChange(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos, this.locationOfBlockChange, this.numberOfTilesToUpdate, PlayerManager.getWorldServer(this.thePlayerManager)));
                for (int var1 = 0; var1 < this.numberOfTilesToUpdate; ++var1) {
                    final int var2 = this.chunkLocation.chunkXPos * 16 + (this.locationOfBlockChange[var1] >> 12 & 0xF);
                    final int var3 = this.locationOfBlockChange[var1] & 0xFF;
                    final int var4 = this.chunkLocation.chunkZPos * 16 + (this.locationOfBlockChange[var1] >> 8 & 0xF);
                    if (PlayerManager.getWorldServer(this.thePlayerManager).blockHasTileEntity(var2, var3, var4)) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(this.thePlayerManager).getBlockTileEntity(var2, var3, var4));
                    }
                }
            }
            this.numberOfTilesToUpdate = 0;
            this.field_73260_f = 0;
        }
    }
    
    private void sendTileToAllPlayersWatchingChunk(final TileEntity par1TileEntity) {
        if (par1TileEntity != null) {
            final Packet var2 = par1TileEntity.getDescriptionPacket();
            if (var2 != null) {
                this.sendToAllPlayersWatchingChunk(var2);
            }
        }
    }
    
    static ChunkCoordIntPair getChunkLocation(final PlayerInstance par0PlayerInstance) {
        return par0PlayerInstance.chunkLocation;
    }
    
    static List getPlayersInChunk(final PlayerInstance par0PlayerInstance) {
        return par0PlayerInstance.playersInChunk;
    }
    
    public void sendThisChunkToAllPlayers() {
        for (int var1 = 0; var1 < this.playersInChunk.size(); ++var1) {
            final EntityPlayerMP var2 = this.playersInChunk.get(var1);
            final Chunk var3 = PlayerManager.getWorldServer(this.thePlayerManager).getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
            final ArrayList var4 = new ArrayList(1);
            var4.add(var3);
            var2.playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var4));
        }
    }
}
