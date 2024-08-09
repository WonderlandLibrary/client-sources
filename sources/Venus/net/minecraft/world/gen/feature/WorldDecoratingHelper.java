/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import java.util.BitSet;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;

public class WorldDecoratingHelper {
    private final ISeedReader field_242889_a;
    private final ChunkGenerator field_242890_b;

    public WorldDecoratingHelper(ISeedReader iSeedReader, ChunkGenerator chunkGenerator) {
        this.field_242889_a = iSeedReader;
        this.field_242890_b = chunkGenerator;
    }

    public int func_242893_a(Heightmap.Type type, int n, int n2) {
        return this.field_242889_a.getHeight(type, n, n2);
    }

    public int func_242891_a() {
        return this.field_242890_b.func_230355_e_();
    }

    public int func_242895_b() {
        return this.field_242890_b.func_230356_f_();
    }

    public BitSet func_242892_a(ChunkPos chunkPos, GenerationStage.Carving carving) {
        return ((ChunkPrimer)this.field_242889_a.getChunk(chunkPos.x, chunkPos.z)).getOrAddCarvingMask(carving);
    }

    public BlockState func_242894_a(BlockPos blockPos) {
        return this.field_242889_a.getBlockState(blockPos);
    }
}

