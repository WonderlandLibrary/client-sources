/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.render.VboRegion;

public class ViewFrustum {
    protected final WorldRenderer renderGlobal;
    protected final World world;
    protected int countChunksY;
    protected int countChunksX;
    protected int countChunksZ;
    public ChunkRenderDispatcher.ChunkRender[] renderChunks;
    private Map<ChunkPos, VboRegion[]> mapVboRegions = new HashMap<ChunkPos, VboRegion[]>();

    public ViewFrustum(ChunkRenderDispatcher chunkRenderDispatcher, World world, int n, WorldRenderer worldRenderer) {
        this.renderGlobal = worldRenderer;
        this.world = world;
        this.setCountChunksXYZ(n);
        this.createRenderChunks(chunkRenderDispatcher);
    }

    protected void createRenderChunks(ChunkRenderDispatcher chunkRenderDispatcher) {
        int n;
        int n2;
        int n3 = this.countChunksX * this.countChunksY * this.countChunksZ;
        this.renderChunks = new ChunkRenderDispatcher.ChunkRender[n3];
        for (n2 = 0; n2 < this.countChunksX; ++n2) {
            for (int i = 0; i < this.countChunksY; ++i) {
                for (n = 0; n < this.countChunksZ; ++n) {
                    int n4 = this.getIndex(n2, i, n);
                    this.renderChunks[n4] = new ChunkRenderDispatcher.ChunkRender(chunkRenderDispatcher);
                    this.renderChunks[n4].setPosition(n2 * 16, i * 16, n * 16);
                    if (!Config.isVbo() || !Config.isRenderRegions()) continue;
                    this.updateVboRegion(this.renderChunks[n4]);
                }
            }
        }
        for (n2 = 0; n2 < this.renderChunks.length; ++n2) {
            ChunkRenderDispatcher.ChunkRender chunkRender = this.renderChunks[n2];
            for (n = 0; n < Direction.VALUES.length; ++n) {
                Direction direction = Direction.VALUES[n];
                BlockPos blockPos = chunkRender.getBlockPosOffset16(direction);
                ChunkRenderDispatcher.ChunkRender chunkRender2 = this.getRenderChunk(blockPos);
                chunkRender.setRenderChunkNeighbour(direction, chunkRender2);
            }
        }
    }

    public void deleteGlResources() {
        for (ChunkRenderDispatcher.ChunkRender chunkRender : this.renderChunks) {
            chunkRender.deleteGlResources();
        }
        this.deleteVboRegions();
    }

    private int getIndex(int n, int n2, int n3) {
        return (n3 * this.countChunksY + n2) * this.countChunksX + n;
    }

    protected void setCountChunksXYZ(int n) {
        int n2;
        this.countChunksX = n2 = n * 2 + 1;
        this.countChunksY = 16;
        this.countChunksZ = n2;
    }

    public void updateChunkPositions(double d, double d2) {
        int n = MathHelper.floor(d);
        int n2 = MathHelper.floor(d2);
        for (int i = 0; i < this.countChunksX; ++i) {
            int n3 = this.countChunksX * 16;
            int n4 = n - 8 - n3 / 2;
            int n5 = n4 + Math.floorMod(i * 16 - n4, n3);
            for (int j = 0; j < this.countChunksZ; ++j) {
                int n6 = this.countChunksZ * 16;
                int n7 = n2 - 8 - n6 / 2;
                int n8 = n7 + Math.floorMod(j * 16 - n7, n6);
                for (int k = 0; k < this.countChunksY; ++k) {
                    int n9 = k * 16;
                    ChunkRenderDispatcher.ChunkRender chunkRender = this.renderChunks[this.getIndex(i, k, j)];
                    chunkRender.setPosition(n5, n9, n8);
                }
            }
        }
    }

    public void markForRerender(int n, int n2, int n3, boolean bl) {
        int n4 = Math.floorMod(n, this.countChunksX);
        int n5 = Math.floorMod(n2, this.countChunksY);
        int n6 = Math.floorMod(n3, this.countChunksZ);
        ChunkRenderDispatcher.ChunkRender chunkRender = this.renderChunks[this.getIndex(n4, n5, n6)];
        chunkRender.setNeedsUpdate(bl);
    }

    @Nullable
    public ChunkRenderDispatcher.ChunkRender getRenderChunk(BlockPos blockPos) {
        int n = blockPos.getX() >> 4;
        int n2 = blockPos.getY() >> 4;
        int n3 = blockPos.getZ() >> 4;
        if (n2 >= 0 && n2 < this.countChunksY) {
            n = MathHelper.normalizeAngle(n, this.countChunksX);
            n3 = MathHelper.normalizeAngle(n3, this.countChunksZ);
            return this.renderChunks[this.getIndex(n, n2, n3)];
        }
        return null;
    }

    private void updateVboRegion(ChunkRenderDispatcher.ChunkRender chunkRender) {
        int n;
        BlockPos blockPos = chunkRender.getPosition();
        int n2 = blockPos.getX() >> 8 << 8;
        int n3 = blockPos.getZ() >> 8 << 8;
        ChunkPos chunkPos = new ChunkPos(n2, n3);
        RenderType[] renderTypeArray = RenderType.CHUNK_RENDER_TYPES;
        VboRegion[] vboRegionArray = this.mapVboRegions.get(chunkPos);
        if (vboRegionArray == null) {
            vboRegionArray = new VboRegion[renderTypeArray.length];
            for (n = 0; n < renderTypeArray.length; ++n) {
                vboRegionArray[n] = new VboRegion(renderTypeArray[n]);
            }
            this.mapVboRegions.put(chunkPos, vboRegionArray);
        }
        for (n = 0; n < renderTypeArray.length; ++n) {
            RenderType renderType = renderTypeArray[n];
            VboRegion vboRegion = vboRegionArray[n];
            if (vboRegion == null) continue;
            chunkRender.getVertexBuffer(renderType).setVboRegion(vboRegion);
        }
    }

    public void deleteVboRegions() {
        for (ChunkPos chunkPos : this.mapVboRegions.keySet()) {
            VboRegion[] vboRegionArray = this.mapVboRegions.get(chunkPos);
            for (int i = 0; i < vboRegionArray.length; ++i) {
                VboRegion vboRegion = vboRegionArray[i];
                if (vboRegion != null) {
                    vboRegion.deleteGlBuffers();
                }
                vboRegionArray[i] = null;
            }
        }
        this.mapVboRegions.clear();
    }
}

