/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndSpikeFeature
extends Feature<EndSpikeFeatureConfig> {
    private static final LoadingCache<Long, List<EndSpike>> LOADING_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new EndSpikeCacheLoader());

    public EndSpikeFeature(Codec<EndSpikeFeatureConfig> codec) {
        super(codec);
    }

    public static List<EndSpike> func_236356_a_(ISeedReader iSeedReader) {
        Random random2 = new Random(iSeedReader.getSeed());
        long l = random2.nextLong() & 0xFFFFL;
        return LOADING_CACHE.getUnchecked(l);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, EndSpikeFeatureConfig endSpikeFeatureConfig) {
        List<EndSpike> list = endSpikeFeatureConfig.getSpikes();
        if (list.isEmpty()) {
            list = EndSpikeFeature.func_236356_a_(iSeedReader);
        }
        for (EndSpike endSpike : list) {
            if (!endSpike.doesStartInChunk(blockPos)) continue;
            this.placeSpike(iSeedReader, random2, endSpikeFeatureConfig, endSpike);
        }
        return false;
    }

    private void placeSpike(IServerWorld iServerWorld, Random random2, EndSpikeFeatureConfig endSpikeFeatureConfig, EndSpike endSpike) {
        int n = endSpike.getRadius();
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(new BlockPos(endSpike.getCenterX() - n, 0, endSpike.getCenterZ() - n), new BlockPos(endSpike.getCenterX() + n, endSpike.getHeight() + 10, endSpike.getCenterZ() + n))) {
            if (blockPos.distanceSq(endSpike.getCenterX(), blockPos.getY(), endSpike.getCenterZ(), true) <= (double)(n * n + 1) && blockPos.getY() < endSpike.getHeight()) {
                this.setBlockState(iServerWorld, blockPos, Blocks.OBSIDIAN.getDefaultState());
                continue;
            }
            if (blockPos.getY() <= 65) continue;
            this.setBlockState(iServerWorld, blockPos, Blocks.AIR.getDefaultState());
        }
        if (endSpike.isGuarded()) {
            int n2 = -2;
            int n3 = 2;
            int n4 = 3;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    for (int k = 0; k <= 3; ++k) {
                        boolean bl;
                        boolean bl2 = MathHelper.abs(i) == 2;
                        boolean bl3 = MathHelper.abs(j) == 2;
                        boolean bl4 = bl = k == 3;
                        if (!bl2 && !bl3 && !bl) continue;
                        boolean bl5 = i == -2 || i == 2 || bl;
                        boolean bl6 = j == -2 || j == 2 || bl;
                        BlockState blockState = (BlockState)((BlockState)((BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, bl5 && j != -2)).with(PaneBlock.SOUTH, bl5 && j != 2)).with(PaneBlock.WEST, bl6 && i != -2)).with(PaneBlock.EAST, bl6 && i != 2);
                        this.setBlockState(iServerWorld, mutable.setPos(endSpike.getCenterX() + i, endSpike.getHeight() + k, endSpike.getCenterZ() + j), blockState);
                    }
                }
            }
        }
        EnderCrystalEntity enderCrystalEntity = EntityType.END_CRYSTAL.create(iServerWorld.getWorld());
        enderCrystalEntity.setBeamTarget(endSpikeFeatureConfig.getCrystalBeamTarget());
        enderCrystalEntity.setInvulnerable(endSpikeFeatureConfig.isCrystalInvulnerable());
        enderCrystalEntity.setLocationAndAngles((double)endSpike.getCenterX() + 0.5, endSpike.getHeight() + 1, (double)endSpike.getCenterZ() + 0.5, random2.nextFloat() * 360.0f, 0.0f);
        iServerWorld.addEntity(enderCrystalEntity);
        this.setBlockState(iServerWorld, new BlockPos(endSpike.getCenterX(), endSpike.getHeight(), endSpike.getCenterZ()), Blocks.BEDROCK.getDefaultState());
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (EndSpikeFeatureConfig)iFeatureConfig);
    }

    public static class EndSpike {
        public static final Codec<EndSpike> field_236357_a_ = RecordCodecBuilder.create(EndSpike::lambda$static$5);
        private final int centerX;
        private final int centerZ;
        private final int radius;
        private final int height;
        private final boolean guarded;
        private final AxisAlignedBB topBoundingBox;

        public EndSpike(int n, int n2, int n3, int n4, boolean bl) {
            this.centerX = n;
            this.centerZ = n2;
            this.radius = n3;
            this.height = n4;
            this.guarded = bl;
            this.topBoundingBox = new AxisAlignedBB(n - n3, 0.0, n2 - n3, n + n3, 256.0, n2 + n3);
        }

        public boolean doesStartInChunk(BlockPos blockPos) {
            return blockPos.getX() >> 4 == this.centerX >> 4 && blockPos.getZ() >> 4 == this.centerZ >> 4;
        }

        public int getCenterX() {
            return this.centerX;
        }

        public int getCenterZ() {
            return this.centerZ;
        }

        public int getRadius() {
            return this.radius;
        }

        public int getHeight() {
            return this.height;
        }

        public boolean isGuarded() {
            return this.guarded;
        }

        public AxisAlignedBB getTopBoundingBox() {
            return this.topBoundingBox;
        }

        private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.INT.fieldOf("centerX")).orElse(0).forGetter(EndSpike::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("centerZ")).orElse(0).forGetter(EndSpike::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("radius")).orElse(0).forGetter(EndSpike::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("height")).orElse(0).forGetter(EndSpike::lambda$static$3), ((MapCodec)Codec.BOOL.fieldOf("guarded")).orElse(false).forGetter(EndSpike::lambda$static$4)).apply(instance, EndSpike::new);
        }

        private static Boolean lambda$static$4(EndSpike endSpike) {
            return endSpike.guarded;
        }

        private static Integer lambda$static$3(EndSpike endSpike) {
            return endSpike.height;
        }

        private static Integer lambda$static$2(EndSpike endSpike) {
            return endSpike.radius;
        }

        private static Integer lambda$static$1(EndSpike endSpike) {
            return endSpike.centerZ;
        }

        private static Integer lambda$static$0(EndSpike endSpike) {
            return endSpike.centerX;
        }
    }

    static class EndSpikeCacheLoader
    extends CacheLoader<Long, List<EndSpike>> {
        private EndSpikeCacheLoader() {
        }

        @Override
        public List<EndSpike> load(Long l) {
            List list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
            Collections.shuffle(list, new Random(l));
            ArrayList<EndSpike> arrayList = Lists.newArrayList();
            for (int i = 0; i < 10; ++i) {
                int n = MathHelper.floor(42.0 * Math.cos(2.0 * (-Math.PI + 0.3141592653589793 * (double)i)));
                int n2 = MathHelper.floor(42.0 * Math.sin(2.0 * (-Math.PI + 0.3141592653589793 * (double)i)));
                int n3 = (Integer)list.get(i);
                int n4 = 2 + n3 / 3;
                int n5 = 76 + n3 * 3;
                boolean bl = n3 == 1 || n3 == 2;
                arrayList.add(new EndSpike(n, n2, n4, n5, bl));
            }
            return arrayList;
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((Long)object);
        }
    }
}

