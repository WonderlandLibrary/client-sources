package net.minecraft.server.management;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.server.*;

class PlayerInstance
{
    private final List playersWatchingChunk;
    private final ChunkCoordIntPair currentChunk;
    private short[] locationOfBlockChange;
    private int numBlocksToUpdate;
    private int flagsYAreasToUpdate;
    private long previousWorldTime;
    
    public PlayerInstance(final int p_i1518_2_, final int p_i1518_3_) {
        this.playersWatchingChunk = Lists.newArrayList();
        this.locationOfBlockChange = new short[64];
        this.currentChunk = new ChunkCoordIntPair(p_i1518_2_, p_i1518_3_);
        PlayerManager.this.getMinecraftServer().theChunkProviderServer.loadChunk(p_i1518_2_, p_i1518_3_);
    }
    
    public void addPlayer(final EntityPlayerMP p_73255_1_) {
        if (this.playersWatchingChunk.contains(p_73255_1_)) {
            PlayerManager.access$200().debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { p_73255_1_, this.currentChunk.chunkXPos, this.currentChunk.chunkZPos });
        }
        else {
            if (this.playersWatchingChunk.isEmpty()) {
                this.previousWorldTime = PlayerManager.access$300(PlayerManager.this).getTotalWorldTime();
            }
            this.playersWatchingChunk.add(p_73255_1_);
            p_73255_1_.loadedChunks.add(this.currentChunk);
        }
    }
    
    public void removePlayer(final EntityPlayerMP p_73252_1_) {
        if (this.playersWatchingChunk.contains(p_73252_1_)) {
            final Chunk var2 = PlayerManager.access$300(PlayerManager.this).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            if (var2.isPopulated()) {
                p_73252_1_.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
            }
            this.playersWatchingChunk.remove(p_73252_1_);
            p_73252_1_.loadedChunks.remove(this.currentChunk);
            if (this.playersWatchingChunk.isEmpty()) {
                final long var3 = this.currentChunk.chunkXPos + 2147483647L | this.currentChunk.chunkZPos + 2147483647L << 32;
                this.increaseInhabitedTime(var2);
                PlayerManager.access$400(PlayerManager.this).remove(var3);
                PlayerManager.access$500(PlayerManager.this).remove(this);
                if (this.numBlocksToUpdate > 0) {
                    PlayerManager.access$600(PlayerManager.this).remove(this);
                }
                PlayerManager.this.getMinecraftServer().theChunkProviderServer.dropChunk(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            }
        }
    }
    
    public void processChunk() {
        this.increaseInhabitedTime(PlayerManager.access$300(PlayerManager.this).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos));
    }
    
    private void increaseInhabitedTime(final Chunk p_111196_1_) {
        p_111196_1_.setInhabitedTime(p_111196_1_.getInhabitedTime() + PlayerManager.access$300(PlayerManager.this).getTotalWorldTime() - this.previousWorldTime);
        this.previousWorldTime = PlayerManager.access$300(PlayerManager.this).getTotalWorldTime();
    }
    
    public void flagChunkForUpdate(final int p_151253_1_, final int p_151253_2_, final int p_151253_3_) {
        if (this.numBlocksToUpdate == 0) {
            PlayerManager.access$600(PlayerManager.this).add(this);
        }
        this.flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);
        if (this.numBlocksToUpdate < 64) {
            final short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
            for (int var5 = 0; var5 < this.numBlocksToUpdate; ++var5) {
                if (this.locationOfBlockChange[var5] == var4) {
                    return;
                }
            }
            this.locationOfBlockChange[this.numBlocksToUpdate++] = var4;
        }
    }
    
    public void sendToAllPlayersWatchingChunk(final Packet p_151251_1_) {
        for (int var2 = 0; var2 < this.playersWatchingChunk.size(); ++var2) {
            final EntityPlayerMP var3 = this.playersWatchingChunk.get(var2);
            if (!var3.loadedChunks.contains(this.currentChunk)) {
                var3.playerNetServerHandler.sendPacket(p_151251_1_);
            }
        }
    }
    
    public void onUpdate() {
        if (this.numBlocksToUpdate != 0) {
            if (this.numBlocksToUpdate == 1) {
                final int var1 = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.currentChunk.chunkXPos * 16;
                final int var2 = this.locationOfBlockChange[0] & 0xFF;
                final int var3 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.currentChunk.chunkZPos * 16;
                final BlockPos var4 = new BlockPos(var1, var2, var3);
                this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.access$300(PlayerManager.this), var4));
                if (PlayerManager.access$300(PlayerManager.this).getBlockState(var4).getBlock().hasTileEntity()) {
                    this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$300(PlayerManager.this).getTileEntity(var4));
                }
            }
            else if (this.numBlocksToUpdate == 64) {
                final int var1 = this.currentChunk.chunkXPos * 16;
                final int var2 = this.currentChunk.chunkZPos * 16;
                this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.access$300(PlayerManager.this).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos), false, this.flagsYAreasToUpdate));
                for (int var3 = 0; var3 < 16; ++var3) {
                    if ((this.flagsYAreasToUpdate & 1 << var3) != 0x0) {
                        final int var5 = var3 << 4;
                        final List var6 = PlayerManager.access$300(PlayerManager.this).func_147486_a(var1, var5, var2, var1 + 16, var5 + 16, var2 + 16);
                        for (int var7 = 0; var7 < var6.size(); ++var7) {
                            this.sendTileToAllPlayersWatchingChunk(var6.get(var7));
                        }
                    }
                }
            }
            else {
                this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.access$300(PlayerManager.this).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos)));
                for (int var1 = 0; var1 < this.numBlocksToUpdate; ++var1) {
                    final int var2 = (this.locationOfBlockChange[var1] >> 12 & 0xF) + this.currentChunk.chunkXPos * 16;
                    final int var3 = this.locationOfBlockChange[var1] & 0xFF;
                    final int var5 = (this.locationOfBlockChange[var1] >> 8 & 0xF) + this.currentChunk.chunkZPos * 16;
                    final BlockPos var8 = new BlockPos(var2, var3, var5);
                    if (PlayerManager.access$300(PlayerManager.this).getBlockState(var8).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$300(PlayerManager.this).getTileEntity(var8));
                    }
                }
            }
            this.numBlocksToUpdate = 0;
            this.flagsYAreasToUpdate = 0;
        }
    }
    
    private void sendTileToAllPlayersWatchingChunk(final TileEntity p_151252_1_) {
        if (p_151252_1_ != null) {
            final Packet var2 = p_151252_1_.getDescriptionPacket();
            if (var2 != null) {
                this.sendToAllPlayersWatchingChunk(var2);
            }
        }
    }
}
