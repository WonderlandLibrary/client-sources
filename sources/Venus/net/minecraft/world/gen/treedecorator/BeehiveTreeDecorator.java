/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class BeehiveTreeDecorator
extends TreeDecorator {
    public static final Codec<BeehiveTreeDecorator> field_236863_a_ = ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).xmap(BeehiveTreeDecorator::new, BeehiveTreeDecorator::lambda$static$0).codec();
    private final float probability;

    public BeehiveTreeDecorator(float f) {
        this.probability = f;
    }

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TreeDecoratorType.BEEHIVE;
    }

    @Override
    public void func_225576_a_(ISeedReader iSeedReader, Random random2, List<BlockPos> list, List<BlockPos> list2, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        if (!(random2.nextFloat() >= this.probability)) {
            BlockPos blockPos;
            BlockPos blockPos2;
            Direction direction = BeehiveBlock.getGenerationDirection(random2);
            int n = !list2.isEmpty() ? Math.max(list2.get(0).getY() - 1, list.get(0).getY()) : Math.min(list.get(0).getY() + 1 + random2.nextInt(3), list.get(list.size() - 1).getY());
            List list3 = list.stream().filter(arg_0 -> BeehiveTreeDecorator.lambda$func_225576_a_$1(n, arg_0)).collect(Collectors.toList());
            if (!list3.isEmpty() && Feature.isAirAt(iSeedReader, blockPos2 = (blockPos = (BlockPos)list3.get(random2.nextInt(list3.size()))).offset(direction)) && Feature.isAirAt(iSeedReader, blockPos2.offset(Direction.SOUTH))) {
                BlockState blockState = (BlockState)Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, Direction.SOUTH);
                this.func_227423_a_(iSeedReader, blockPos2, blockState, set, mutableBoundingBox);
                TileEntity tileEntity = iSeedReader.getTileEntity(blockPos2);
                if (tileEntity instanceof BeehiveTileEntity) {
                    BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
                    int n2 = 2 + random2.nextInt(2);
                    for (int i = 0; i < n2; ++i) {
                        BeeEntity beeEntity = new BeeEntity((EntityType<? extends BeeEntity>)EntityType.BEE, (World)iSeedReader.getWorld());
                        beehiveTileEntity.tryEnterHive(beeEntity, false, random2.nextInt(599));
                    }
                }
            }
        }
    }

    private static boolean lambda$func_225576_a_$1(int n, BlockPos blockPos) {
        return blockPos.getY() == n;
    }

    private static Float lambda$static$0(BeehiveTreeDecorator beehiveTreeDecorator) {
        return Float.valueOf(beehiveTreeDecorator.probability);
    }
}

