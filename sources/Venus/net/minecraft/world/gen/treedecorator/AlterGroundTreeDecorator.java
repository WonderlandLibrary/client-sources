/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class AlterGroundTreeDecorator
extends TreeDecorator {
    public static final Codec<AlterGroundTreeDecorator> field_236859_a_ = ((MapCodec)BlockStateProvider.CODEC.fieldOf("provider")).xmap(AlterGroundTreeDecorator::new, AlterGroundTreeDecorator::lambda$static$0).codec();
    private final BlockStateProvider field_227410_b_;

    public AlterGroundTreeDecorator(BlockStateProvider blockStateProvider) {
        this.field_227410_b_ = blockStateProvider;
    }

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TreeDecoratorType.ALTER_GROUND;
    }

    @Override
    public void func_225576_a_(ISeedReader iSeedReader, Random random2, List<BlockPos> list, List<BlockPos> list2, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        int n = list.get(0).getY();
        list.stream().filter(arg_0 -> AlterGroundTreeDecorator.lambda$func_225576_a_$1(n, arg_0)).forEach(arg_0 -> this.lambda$func_225576_a_$2(iSeedReader, random2, arg_0));
    }

    private void func_227413_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos blockPos) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) == 2 && Math.abs(j) == 2) continue;
                this.func_227414_b_(iWorldGenerationReader, random2, blockPos.add(i, 0, j));
            }
        }
    }

    private void func_227414_b_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos blockPos) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockPos2 = blockPos.up(i);
            if (Feature.isDirtAt(iWorldGenerationReader, blockPos2)) {
                iWorldGenerationReader.setBlockState(blockPos2, this.field_227410_b_.getBlockState(random2, blockPos), 19);
                break;
            }
            if (!Feature.isAirAt(iWorldGenerationReader, blockPos2) && i < 0) break;
        }
    }

    private void lambda$func_225576_a_$2(ISeedReader iSeedReader, Random random2, BlockPos blockPos) {
        this.func_227413_a_(iSeedReader, random2, blockPos.west().north());
        this.func_227413_a_(iSeedReader, random2, blockPos.east(2).north());
        this.func_227413_a_(iSeedReader, random2, blockPos.west().south(2));
        this.func_227413_a_(iSeedReader, random2, blockPos.east(2).south(2));
        for (int i = 0; i < 5; ++i) {
            int n = random2.nextInt(64);
            int n2 = n % 8;
            int n3 = n / 8;
            if (n2 != 0 && n2 != 7 && n3 != 0 && n3 != 7) continue;
            this.func_227413_a_(iSeedReader, random2, blockPos.add(-3 + n2, 0, -3 + n3));
        }
    }

    private static boolean lambda$func_225576_a_$1(int n, BlockPos blockPos) {
        return blockPos.getY() == n;
    }

    private static BlockStateProvider lambda$static$0(AlterGroundTreeDecorator alterGroundTreeDecorator) {
        return alterGroundTreeDecorator.field_227410_b_;
    }
}

