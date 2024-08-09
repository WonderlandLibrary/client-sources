/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class SpawnLocationHelper {
    @Nullable
    protected static BlockPos func_241092_a_(ServerWorld serverWorld, int n, int n2, boolean bl) {
        int n3;
        BlockPos.Mutable mutable = new BlockPos.Mutable(n, 0, n2);
        Biome biome = serverWorld.getBiome(mutable);
        boolean bl2 = serverWorld.getDimensionType().getHasCeiling();
        BlockState blockState = biome.getGenerationSettings().getSurfaceBuilderConfig().getTop();
        if (bl && !blockState.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        }
        Chunk chunk = serverWorld.getChunk(n >> 4, n2 >> 4);
        int n4 = n3 = bl2 ? serverWorld.getChunkProvider().getChunkGenerator().getGroundHeight() : chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, n & 0xF, n2 & 0xF);
        if (n3 < 0) {
            return null;
        }
        int n5 = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, n & 0xF, n2 & 0xF);
        if (n5 <= n3 && n5 > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, n & 0xF, n2 & 0xF)) {
            return null;
        }
        for (int i = n3 + 1; i >= 0; --i) {
            mutable.setPos(n, i, n2);
            BlockState blockState2 = serverWorld.getBlockState(mutable);
            if (!blockState2.getFluidState().isEmpty()) break;
            if (!blockState2.equals(blockState)) continue;
            return ((BlockPos)mutable.up()).toImmutable();
        }
        return null;
    }

    @Nullable
    public static BlockPos func_241094_a_(ServerWorld serverWorld, ChunkPos chunkPos, boolean bl) {
        for (int i = chunkPos.getXStart(); i <= chunkPos.getXEnd(); ++i) {
            for (int j = chunkPos.getZStart(); j <= chunkPos.getZEnd(); ++j) {
                BlockPos blockPos = SpawnLocationHelper.func_241092_a_(serverWorld, i, j, bl);
                if (blockPos == null) continue;
                return blockPos;
            }
        }
        return null;
    }
}

