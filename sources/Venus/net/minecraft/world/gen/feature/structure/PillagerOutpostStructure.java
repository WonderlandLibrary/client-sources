/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class PillagerOutpostStructure
extends JigsawStructure {
    private static final List<MobSpawnInfo.Spawners> PILLAGE_OUTPOST_ENEMIES = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.PILLAGER, 1, 1, 1));

    public PillagerOutpostStructure(Codec<VillageConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return PILLAGE_OUTPOST_ENEMIES;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, VillageConfig villageConfig) {
        int n3 = n >> 4;
        int n4 = n2 >> 4;
        sharedSeedRandom.setSeed((long)(n3 ^ n4 << 4) ^ l);
        sharedSeedRandom.nextInt();
        if (sharedSeedRandom.nextInt(5) != 0) {
            return true;
        }
        return !this.func_242782_a(chunkGenerator, l, sharedSeedRandom, n, n2);
    }

    private boolean func_242782_a(ChunkGenerator chunkGenerator, long l, SharedSeedRandom sharedSeedRandom, int n, int n2) {
        StructureSeparationSettings structureSeparationSettings = chunkGenerator.func_235957_b_().func_236197_a_(Structure.field_236381_q_);
        if (structureSeparationSettings == null) {
            return true;
        }
        for (int i = n - 10; i <= n + 10; ++i) {
            for (int j = n2 - 10; j <= n2 + 10; ++j) {
                ChunkPos chunkPos = Structure.field_236381_q_.func_236392_a_(structureSeparationSettings, l, sharedSeedRandom, i, j);
                if (i != chunkPos.x || j != chunkPos.z) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, IFeatureConfig iFeatureConfig) {
        return this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, n, n2, biome, chunkPos, (VillageConfig)iFeatureConfig);
    }
}

