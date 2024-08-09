/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.BlockLightEngine;
import net.minecraft.world.lighting.ILightListener;
import net.minecraft.world.lighting.IWorldLightListener;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.lighting.SkyLightEngine;

public class WorldLightManager
implements ILightListener {
    @Nullable
    private final LightEngine<?, ?> blockLight;
    @Nullable
    private final LightEngine<?, ?> skyLight;

    public WorldLightManager(IChunkLightProvider iChunkLightProvider, boolean bl, boolean bl2) {
        this.blockLight = bl ? new BlockLightEngine(iChunkLightProvider) : null;
        this.skyLight = bl2 ? new SkyLightEngine(iChunkLightProvider) : null;
    }

    public void checkBlock(BlockPos blockPos) {
        if (this.blockLight != null) {
            this.blockLight.checkLight(blockPos);
        }
        if (this.skyLight != null) {
            this.skyLight.checkLight(blockPos);
        }
    }

    public void onBlockEmissionIncrease(BlockPos blockPos, int n) {
        if (this.blockLight != null) {
            this.blockLight.func_215623_a(blockPos, n);
        }
    }

    public boolean hasLightWork() {
        if (this.skyLight != null && this.skyLight.func_215619_a()) {
            return false;
        }
        return this.blockLight != null && this.blockLight.func_215619_a();
    }

    public int tick(int n, boolean bl, boolean bl2) {
        if (this.blockLight != null && this.skyLight != null) {
            int n2 = n / 2;
            int n3 = this.blockLight.tick(n2, bl, bl2);
            int n4 = n - n2 + n3;
            int n5 = this.skyLight.tick(n4, bl, bl2);
            return n3 == 0 && n5 > 0 ? this.blockLight.tick(n5, bl, bl2) : n5;
        }
        if (this.blockLight != null) {
            return this.blockLight.tick(n, bl, bl2);
        }
        return this.skyLight != null ? this.skyLight.tick(n, bl, bl2) : n;
    }

    @Override
    public void updateSectionStatus(SectionPos sectionPos, boolean bl) {
        if (this.blockLight != null) {
            this.blockLight.updateSectionStatus(sectionPos, bl);
        }
        if (this.skyLight != null) {
            this.skyLight.updateSectionStatus(sectionPos, bl);
        }
    }

    public void enableLightSources(ChunkPos chunkPos, boolean bl) {
        if (this.blockLight != null) {
            this.blockLight.func_215620_a(chunkPos, bl);
        }
        if (this.skyLight != null) {
            this.skyLight.func_215620_a(chunkPos, bl);
        }
    }

    public IWorldLightListener getLightEngine(LightType lightType) {
        if (lightType == LightType.BLOCK) {
            return this.blockLight == null ? IWorldLightListener.Dummy.INSTANCE : this.blockLight;
        }
        return this.skyLight == null ? IWorldLightListener.Dummy.INSTANCE : this.skyLight;
    }

    public String getDebugInfo(LightType lightType, SectionPos sectionPos) {
        if (lightType == LightType.BLOCK) {
            if (this.blockLight != null) {
                return this.blockLight.getDebugString(sectionPos.asLong());
            }
        } else if (this.skyLight != null) {
            return this.skyLight.getDebugString(sectionPos.asLong());
        }
        return "n/a";
    }

    public void setData(LightType lightType, SectionPos sectionPos, @Nullable NibbleArray nibbleArray, boolean bl) {
        if (lightType == LightType.BLOCK) {
            if (this.blockLight != null) {
                this.blockLight.setData(sectionPos.asLong(), nibbleArray, bl);
            }
        } else if (this.skyLight != null) {
            this.skyLight.setData(sectionPos.asLong(), nibbleArray, bl);
        }
    }

    public void retainData(ChunkPos chunkPos, boolean bl) {
        if (this.blockLight != null) {
            this.blockLight.retainChunkData(chunkPos, bl);
        }
        if (this.skyLight != null) {
            this.skyLight.retainChunkData(chunkPos, bl);
        }
    }

    public int getLightSubtracted(BlockPos blockPos, int n) {
        int n2 = this.skyLight == null ? 0 : this.skyLight.getLightFor(blockPos) - n;
        int n3 = this.blockLight == null ? 0 : this.blockLight.getLightFor(blockPos);
        return Math.max(n3, n2);
    }
}

