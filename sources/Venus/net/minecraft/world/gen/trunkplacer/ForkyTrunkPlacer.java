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
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class ForkyTrunkPlacer
extends AbstractTrunkPlacer {
    public static final Codec<ForkyTrunkPlacer> field_236896_a_ = RecordCodecBuilder.create(ForkyTrunkPlacer::lambda$static$0);

    public ForkyTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.FORKING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        int n2;
        ForkyTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos.down());
        ArrayList<FoliagePlacer.Foliage> arrayList = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.random(random2);
        int n3 = n - random2.nextInt(4) - 1;
        int n4 = 3 - random2.nextInt(3);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n5 = blockPos.getX();
        int n6 = blockPos.getZ();
        int n7 = 0;
        for (int i = 0; i < n; ++i) {
            n2 = blockPos.getY() + i;
            if (i >= n3 && n4 > 0) {
                n5 += direction.getXOffset();
                n6 += direction.getZOffset();
                --n4;
            }
            if (!ForkyTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, mutable.setPos(n5, n2, n6), set, mutableBoundingBox, baseTreeFeatureConfig)) continue;
            n7 = n2 + 1;
        }
        arrayList.add(new FoliagePlacer.Foliage(new BlockPos(n5, n7, n6), 1, false));
        n5 = blockPos.getX();
        n6 = blockPos.getZ();
        Direction direction2 = Direction.Plane.HORIZONTAL.random(random2);
        if (direction2 != direction) {
            n2 = n3 - random2.nextInt(2) - 1;
            int n8 = 1 + random2.nextInt(3);
            n7 = 0;
            for (int i = n2; i < n && n8 > 0; ++i, --n8) {
                if (i < 1) continue;
                int n9 = blockPos.getY() + i;
                if (!ForkyTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, mutable.setPos(n5 += direction2.getXOffset(), n9, n6 += direction2.getZOffset()), set, mutableBoundingBox, baseTreeFeatureConfig)) continue;
                n7 = n9 + 1;
            }
            if (n7 > 1) {
                arrayList.add(new FoliagePlacer.Foliage(new BlockPos(n5, n7, n6), 0, false));
            }
        }
        return arrayList;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return ForkyTrunkPlacer.func_236915_a_(instance).apply(instance, ForkyTrunkPlacer::new);
    }
}

