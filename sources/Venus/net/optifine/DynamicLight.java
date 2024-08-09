/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.DynamicLights;

public class DynamicLight {
    private Entity entity = null;
    private double offsetY = 0.0;
    private double lastPosX = -2.147483648E9;
    private double lastPosY = -2.147483648E9;
    private double lastPosZ = -2.147483648E9;
    private int lastLightLevel = 0;
    private long timeCheckMs = 0L;
    private Set<BlockPos> setLitChunkPos = new HashSet<BlockPos>();
    private BlockPos.Mutable blockPosMutable = new BlockPos.Mutable();

    public DynamicLight(Entity entity2) {
        this.entity = entity2;
        this.offsetY = entity2.getEyeHeight();
    }

    public void update(WorldRenderer worldRenderer) {
        if (Config.isDynamicLightsFast()) {
            long l = System.currentTimeMillis();
            if (l < this.timeCheckMs + 500L) {
                return;
            }
            this.timeCheckMs = l;
        }
        double d = this.entity.getPosX() - 0.5;
        double d2 = this.entity.getPosY() - 0.5 + this.offsetY;
        double d3 = this.entity.getPosZ() - 0.5;
        int n = DynamicLights.getLightLevel(this.entity);
        double d4 = d - this.lastPosX;
        double d5 = d2 - this.lastPosY;
        double d6 = d3 - this.lastPosZ;
        double d7 = 0.1;
        if (!(Math.abs(d4) <= d7 && Math.abs(d5) <= d7 && Math.abs(d6) <= d7 && this.lastLightLevel == n)) {
            this.lastPosX = d;
            this.lastPosY = d2;
            this.lastPosZ = d3;
            this.lastLightLevel = n;
            HashSet<BlockPos> hashSet = new HashSet<BlockPos>();
            if (n > 0) {
                Direction direction = (MathHelper.floor(d) & 0xF) >= 8 ? Direction.EAST : Direction.WEST;
                Direction direction2 = (MathHelper.floor(d2) & 0xF) >= 8 ? Direction.UP : Direction.DOWN;
                Direction direction3 = (MathHelper.floor(d3) & 0xF) >= 8 ? Direction.SOUTH : Direction.NORTH;
                BlockPos blockPos = new BlockPos(d, d2, d3);
                ChunkRenderDispatcher.ChunkRender chunkRender = worldRenderer.getRenderChunk(blockPos);
                BlockPos blockPos2 = this.getChunkPos(chunkRender, blockPos, direction);
                ChunkRenderDispatcher.ChunkRender chunkRender2 = worldRenderer.getRenderChunk(blockPos2);
                BlockPos blockPos3 = this.getChunkPos(chunkRender, blockPos, direction3);
                ChunkRenderDispatcher.ChunkRender chunkRender3 = worldRenderer.getRenderChunk(blockPos3);
                BlockPos blockPos4 = this.getChunkPos(chunkRender2, blockPos2, direction3);
                ChunkRenderDispatcher.ChunkRender chunkRender4 = worldRenderer.getRenderChunk(blockPos4);
                BlockPos blockPos5 = this.getChunkPos(chunkRender, blockPos, direction2);
                ChunkRenderDispatcher.ChunkRender chunkRender5 = worldRenderer.getRenderChunk(blockPos5);
                BlockPos blockPos6 = this.getChunkPos(chunkRender5, blockPos5, direction);
                ChunkRenderDispatcher.ChunkRender chunkRender6 = worldRenderer.getRenderChunk(blockPos6);
                BlockPos blockPos7 = this.getChunkPos(chunkRender5, blockPos5, direction3);
                ChunkRenderDispatcher.ChunkRender chunkRender7 = worldRenderer.getRenderChunk(blockPos7);
                BlockPos blockPos8 = this.getChunkPos(chunkRender6, blockPos6, direction3);
                ChunkRenderDispatcher.ChunkRender chunkRender8 = worldRenderer.getRenderChunk(blockPos8);
                this.updateChunkLight(chunkRender, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender2, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender3, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender4, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender5, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender6, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender7, this.setLitChunkPos, hashSet);
                this.updateChunkLight(chunkRender8, this.setLitChunkPos, hashSet);
            }
            this.updateLitChunks(worldRenderer);
            this.setLitChunkPos = hashSet;
        }
    }

    private BlockPos getChunkPos(ChunkRenderDispatcher.ChunkRender chunkRender, BlockPos blockPos, Direction direction) {
        return chunkRender != null ? chunkRender.getBlockPosOffset16(direction) : blockPos.offset(direction, 16);
    }

    private void updateChunkLight(ChunkRenderDispatcher.ChunkRender chunkRender, Set<BlockPos> set, Set<BlockPos> set2) {
        if (chunkRender != null) {
            ChunkRenderDispatcher.CompiledChunk compiledChunk = chunkRender.getCompiledChunk();
            if (compiledChunk != null && !compiledChunk.isEmpty()) {
                chunkRender.setNeedsUpdate(true);
            }
            BlockPos blockPos = chunkRender.getPosition().toImmutable();
            if (set != null) {
                set.remove(blockPos);
            }
            if (set2 != null) {
                set2.add(blockPos);
            }
        }
    }

    public void updateLitChunks(WorldRenderer worldRenderer) {
        for (BlockPos blockPos : this.setLitChunkPos) {
            ChunkRenderDispatcher.ChunkRender chunkRender = worldRenderer.getRenderChunk(blockPos);
            this.updateChunkLight(chunkRender, null, null);
        }
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getLastPosX() {
        return this.lastPosX;
    }

    public double getLastPosY() {
        return this.lastPosY;
    }

    public double getLastPosZ() {
        return this.lastPosZ;
    }

    public int getLastLightLevel() {
        return this.lastLightLevel;
    }

    public double getOffsetY() {
        return this.offsetY;
    }

    public String toString() {
        return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
    }
}

