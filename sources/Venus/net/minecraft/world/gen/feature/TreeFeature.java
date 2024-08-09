/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

public class TreeFeature
extends Feature<BaseTreeFeatureConfig> {
    public TreeFeature(Codec<BaseTreeFeatureConfig> codec) {
        super(codec);
    }

    public static boolean func_236410_c_(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return TreeFeature.isReplaceableAt(iWorldGenerationBaseReader, blockPos) || iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$func_236410_c_$0);
    }

    private static boolean func_236414_e_(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$func_236414_e_$1);
    }

    private static boolean isWaterAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$isWaterAt$2);
    }

    public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$isAirOrLeavesAt$3);
    }

    private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$isDirtOrFarmlandAt$4);
    }

    private static boolean isTallPlantAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, TreeFeature::lambda$isTallPlantAt$5);
    }

    public static void func_236408_b_(IWorldWriter iWorldWriter, BlockPos blockPos, BlockState blockState) {
        iWorldWriter.setBlockState(blockPos, blockState, 19);
    }

    public static boolean isReplaceableAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return TreeFeature.isAirOrLeavesAt(iWorldGenerationBaseReader, blockPos) || TreeFeature.isTallPlantAt(iWorldGenerationBaseReader, blockPos) || TreeFeature.isWaterAt(iWorldGenerationBaseReader, blockPos);
    }

    private boolean place(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set2, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        BlockPos blockPos2;
        int n;
        int n2 = baseTreeFeatureConfig.field_236678_g_.func_236917_a_(random2);
        int n3 = baseTreeFeatureConfig.field_236677_f_.func_230374_a_(random2, n2, baseTreeFeatureConfig);
        int n4 = n2 - n3;
        int n5 = baseTreeFeatureConfig.field_236677_f_.func_230376_a_(random2, n4);
        if (!baseTreeFeatureConfig.forcePlacement) {
            int n6 = iWorldGenerationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, blockPos).getY();
            n = iWorldGenerationReader.getHeight(Heightmap.Type.WORLD_SURFACE, blockPos).getY();
            if (n - n6 > baseTreeFeatureConfig.field_236680_i_) {
                return true;
            }
            int n7 = baseTreeFeatureConfig.field_236682_l_ == Heightmap.Type.OCEAN_FLOOR ? n6 : (baseTreeFeatureConfig.field_236682_l_ == Heightmap.Type.WORLD_SURFACE ? n : iWorldGenerationReader.getHeight(baseTreeFeatureConfig.field_236682_l_, blockPos).getY());
            blockPos2 = new BlockPos(blockPos.getX(), n7, blockPos.getZ());
        } else {
            blockPos2 = blockPos;
        }
        if (blockPos2.getY() >= 1 && blockPos2.getY() + n2 + 1 <= 256) {
            if (!TreeFeature.isDirtOrFarmlandAt(iWorldGenerationReader, blockPos2.down())) {
                return true;
            }
            OptionalInt optionalInt = baseTreeFeatureConfig.field_236679_h_.func_236710_c_();
            n = this.func_241521_a_(iWorldGenerationReader, n2, blockPos2, baseTreeFeatureConfig);
            if (n >= n2 || optionalInt.isPresent() && n >= optionalInt.getAsInt()) {
                List<FoliagePlacer.Foliage> list = baseTreeFeatureConfig.field_236678_g_.func_230382_a_(iWorldGenerationReader, random2, n, blockPos2, set, mutableBoundingBox, baseTreeFeatureConfig);
                list.forEach(arg_0 -> TreeFeature.lambda$place$6(baseTreeFeatureConfig, iWorldGenerationReader, random2, n, n3, n5, set2, mutableBoundingBox, arg_0));
                return false;
            }
            return true;
        }
        return true;
    }

    private int func_241521_a_(IWorldGenerationBaseReader iWorldGenerationBaseReader, int n, BlockPos blockPos, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i <= n + 1; ++i) {
            int n2 = baseTreeFeatureConfig.field_236679_h_.func_230369_a_(n, i);
            for (int j = -n2; j <= n2; ++j) {
                for (int k = -n2; k <= n2; ++k) {
                    mutable.setAndOffset(blockPos, j, i, k);
                    if (TreeFeature.func_236410_c_(iWorldGenerationBaseReader, mutable) && (baseTreeFeatureConfig.field_236681_j_ || !TreeFeature.func_236414_e_(iWorldGenerationBaseReader, mutable))) continue;
                    return i - 2;
                }
            }
        }
        return n;
    }

    @Override
    protected void setBlockState(IWorldWriter iWorldWriter, BlockPos blockPos, BlockState blockState) {
        TreeFeature.func_236408_b_(iWorldWriter, blockPos, blockState);
    }

    @Override
    public final boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        HashSet<BlockPos> hashSet = Sets.newHashSet();
        HashSet<BlockPos> hashSet2 = Sets.newHashSet();
        HashSet<BlockPos> hashSet3 = Sets.newHashSet();
        MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getNewBoundingBox();
        boolean bl = this.place(iSeedReader, random2, blockPos, hashSet, hashSet2, mutableBoundingBox, baseTreeFeatureConfig);
        if (mutableBoundingBox.minX <= mutableBoundingBox.maxX && bl && !hashSet.isEmpty()) {
            Object object;
            if (!baseTreeFeatureConfig.decorators.isEmpty()) {
                object = Lists.newArrayList(hashSet);
                ArrayList<BlockPos> arrayList = Lists.newArrayList(hashSet2);
                object.sort(Comparator.comparingInt(Vector3i::getY));
                arrayList.sort(Comparator.comparingInt(Vector3i::getY));
                baseTreeFeatureConfig.decorators.forEach(arg_0 -> TreeFeature.lambda$func_241855_a$7(iSeedReader, random2, (List)object, arrayList, hashSet3, mutableBoundingBox, arg_0));
            }
            object = this.func_236403_a_(iSeedReader, mutableBoundingBox, hashSet, hashSet3);
            Template.func_222857_a(iSeedReader, 3, (VoxelShapePart)object, mutableBoundingBox.minX, mutableBoundingBox.minY, mutableBoundingBox.minZ);
            return false;
        }
        return true;
    }

    private VoxelShapePart func_236403_a_(IWorld iWorld, MutableBoundingBox mutableBoundingBox, Set<BlockPos> set, Set<BlockPos> set2) {
        ArrayList arrayList = Lists.newArrayList();
        BitSetVoxelShapePart bitSetVoxelShapePart = new BitSetVoxelShapePart(mutableBoundingBox.getXSize(), mutableBoundingBox.getYSize(), mutableBoundingBox.getZSize());
        int n = 6;
        for (int i = 0; i < 6; ++i) {
            arrayList.add(Sets.newHashSet());
        }
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos object : Lists.newArrayList(set2)) {
            if (!mutableBoundingBox.isVecInside(object)) continue;
            ((VoxelShapePart)bitSetVoxelShapePart).setFilled(object.getX() - mutableBoundingBox.minX, object.getY() - mutableBoundingBox.minY, object.getZ() - mutableBoundingBox.minZ, true, false);
        }
        for (BlockPos blockPos : Lists.newArrayList(set)) {
            if (mutableBoundingBox.isVecInside(blockPos)) {
                ((VoxelShapePart)bitSetVoxelShapePart).setFilled(blockPos.getX() - mutableBoundingBox.minX, blockPos.getY() - mutableBoundingBox.minY, blockPos.getZ() - mutableBoundingBox.minZ, true, false);
            }
            for (Direction direction : Direction.values()) {
                BlockState blockState;
                mutable.setAndMove(blockPos, direction);
                if (set.contains(mutable) || !(blockState = iWorld.getBlockState(mutable)).hasProperty(BlockStateProperties.DISTANCE_1_7)) continue;
                ((Set)arrayList.get(0)).add(mutable.toImmutable());
                TreeFeature.func_236408_b_(iWorld, mutable, (BlockState)blockState.with(BlockStateProperties.DISTANCE_1_7, 1));
                if (!mutableBoundingBox.isVecInside(mutable)) continue;
                ((VoxelShapePart)bitSetVoxelShapePart).setFilled(mutable.getX() - mutableBoundingBox.minX, mutable.getY() - mutableBoundingBox.minY, mutable.getZ() - mutableBoundingBox.minZ, true, false);
            }
        }
        for (int i = 1; i < 6; ++i) {
            Set set3 = (Set)arrayList.get(i - 1);
            Set set4 = (Set)arrayList.get(i);
            for (BlockPos blockPos : set3) {
                if (mutableBoundingBox.isVecInside(blockPos)) {
                    ((VoxelShapePart)bitSetVoxelShapePart).setFilled(blockPos.getX() - mutableBoundingBox.minX, blockPos.getY() - mutableBoundingBox.minY, blockPos.getZ() - mutableBoundingBox.minZ, true, false);
                }
                for (Direction direction : Direction.values()) {
                    int n2;
                    BlockState blockState;
                    mutable.setAndMove(blockPos, direction);
                    if (set3.contains(mutable) || set4.contains(mutable) || !(blockState = iWorld.getBlockState(mutable)).hasProperty(BlockStateProperties.DISTANCE_1_7) || (n2 = blockState.get(BlockStateProperties.DISTANCE_1_7).intValue()) <= i + 1) continue;
                    BlockState blockState2 = (BlockState)blockState.with(BlockStateProperties.DISTANCE_1_7, i + 1);
                    TreeFeature.func_236408_b_(iWorld, mutable, blockState2);
                    if (mutableBoundingBox.isVecInside(mutable)) {
                        ((VoxelShapePart)bitSetVoxelShapePart).setFilled(mutable.getX() - mutableBoundingBox.minX, mutable.getY() - mutableBoundingBox.minY, mutable.getZ() - mutableBoundingBox.minZ, true, false);
                    }
                    set4.add(mutable.toImmutable());
                }
            }
        }
        return bitSetVoxelShapePart;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BaseTreeFeatureConfig)iFeatureConfig);
    }

    private static void lambda$func_241855_a$7(ISeedReader iSeedReader, Random random2, List list, List list2, Set set, MutableBoundingBox mutableBoundingBox, TreeDecorator treeDecorator) {
        treeDecorator.func_225576_a_(iSeedReader, random2, list, list2, set, mutableBoundingBox);
    }

    private static void lambda$place$6(BaseTreeFeatureConfig baseTreeFeatureConfig, IWorldGenerationReader iWorldGenerationReader, Random random2, int n, int n2, int n3, Set set, MutableBoundingBox mutableBoundingBox, FoliagePlacer.Foliage foliage) {
        baseTreeFeatureConfig.field_236677_f_.func_236752_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, n, foliage, n2, n3, set, mutableBoundingBox);
    }

    private static boolean lambda$isTallPlantAt$5(BlockState blockState) {
        Material material = blockState.getMaterial();
        return material == Material.TALL_PLANTS;
    }

    private static boolean lambda$isDirtOrFarmlandAt$4(BlockState blockState) {
        Block block = blockState.getBlock();
        return TreeFeature.isDirt(block) || block == Blocks.FARMLAND;
    }

    private static boolean lambda$isAirOrLeavesAt$3(BlockState blockState) {
        return blockState.isAir() || blockState.isIn(BlockTags.LEAVES);
    }

    private static boolean lambda$isWaterAt$2(BlockState blockState) {
        return blockState.isIn(Blocks.WATER);
    }

    private static boolean lambda$func_236414_e_$1(BlockState blockState) {
        return blockState.isIn(Blocks.VINE);
    }

    private static boolean lambda$func_236410_c_$0(BlockState blockState) {
        return blockState.isIn(BlockTags.LOGS);
    }
}

