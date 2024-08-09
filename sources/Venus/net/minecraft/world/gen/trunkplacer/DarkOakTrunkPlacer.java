/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class DarkOakTrunkPlacer
extends AbstractTrunkPlacer {
    public static final Codec<DarkOakTrunkPlacer> field_236882_a_ = RecordCodecBuilder.create(DarkOakTrunkPlacer::lambda$static$0);

    public DarkOakTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        int n2;
        int n3;
        ArrayList<FoliagePlacer.Foliage> arrayList = Lists.newArrayList();
        BlockPos blockPos2 = blockPos.down();
        DarkOakTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2);
        DarkOakTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.east());
        DarkOakTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.south());
        DarkOakTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.south().east());
        Direction direction = Direction.Plane.HORIZONTAL.random(random2);
        int n4 = n - random2.nextInt(4);
        int n5 = 2 - random2.nextInt(3);
        int n6 = blockPos.getX();
        int n7 = blockPos.getY();
        int n8 = blockPos.getZ();
        int n9 = n6;
        int n10 = n8;
        int n11 = n7 + n - 1;
        for (n3 = 0; n3 < n; ++n3) {
            BlockPos blockPos3;
            if (n3 >= n4 && n5 > 0) {
                n9 += direction.getXOffset();
                n10 += direction.getZOffset();
                --n5;
            }
            if (!TreeFeature.isAirOrLeavesAt(iWorldGenerationReader, blockPos3 = new BlockPos(n9, n2 = n7 + n3, n10))) continue;
            DarkOakTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos3, set, mutableBoundingBox, baseTreeFeatureConfig);
            DarkOakTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos3.east(), set, mutableBoundingBox, baseTreeFeatureConfig);
            DarkOakTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos3.south(), set, mutableBoundingBox, baseTreeFeatureConfig);
            DarkOakTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos3.east().south(), set, mutableBoundingBox, baseTreeFeatureConfig);
        }
        arrayList.add(new FoliagePlacer.Foliage(new BlockPos(n9, n11, n10), 0, true));
        for (n3 = -1; n3 <= 2; ++n3) {
            for (n2 = -1; n2 <= 2; ++n2) {
                if (n3 >= 0 && n3 <= 1 && n2 >= 0 && n2 <= 1 || random2.nextInt(3) > 0) continue;
                int n12 = random2.nextInt(3) + 2;
                for (int i = 0; i < n12; ++i) {
                    DarkOakTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, new BlockPos(n6 + n3, n11 - i - 1, n8 + n2), set, mutableBoundingBox, baseTreeFeatureConfig);
                }
                arrayList.add(new FoliagePlacer.Foliage(new BlockPos(n9 + n3, n11, n10 + n2), 0, false));
            }
        }
        return arrayList;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return DarkOakTrunkPlacer.func_236915_a_(instance).apply(instance, DarkOakTrunkPlacer::new);
    }
}

