/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.color;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;

public class BlockColors {
    private final ObjectIntIdentityMap<IBlockColor> colors = new ObjectIntIdentityMap(32);
    private final Map<Block, Set<Property<?>>> colorStates = Maps.newHashMap();

    public static BlockColors init() {
        BlockColors blockColors = new BlockColors();
        blockColors.register(BlockColors::lambda$init$0, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockColors.addColorState(DoublePlantBlock.HALF, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockColors.register(BlockColors::lambda$init$1, Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.GRASS, Blocks.POTTED_FERN);
        blockColors.register(BlockColors::lambda$init$2, Blocks.SPRUCE_LEAVES);
        blockColors.register(BlockColors::lambda$init$3, Blocks.BIRCH_LEAVES);
        blockColors.register(BlockColors::lambda$init$4, Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE);
        blockColors.register(BlockColors::lambda$init$5, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.CAULDRON);
        blockColors.register(BlockColors::lambda$init$6, Blocks.REDSTONE_WIRE);
        blockColors.addColorState(RedstoneWireBlock.POWER, Blocks.REDSTONE_WIRE);
        blockColors.register(BlockColors::lambda$init$7, Blocks.SUGAR_CANE);
        blockColors.register(BlockColors::lambda$init$8, Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
        blockColors.register(BlockColors::lambda$init$9, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.addColorState(StemBlock.AGE, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.register(BlockColors::lambda$init$10, Blocks.LILY_PAD);
        return blockColors;
    }

    public int getColorOrMaterialColor(BlockState blockState, World world, BlockPos blockPos) {
        IBlockColor iBlockColor = this.colors.getByValue(Registry.BLOCK.getId(blockState.getBlock()));
        if (iBlockColor != null) {
            return iBlockColor.getColor(blockState, null, null, 0);
        }
        MaterialColor materialColor = blockState.getMaterialColor(world, blockPos);
        return materialColor != null ? materialColor.colorValue : -1;
    }

    public int getColor(BlockState blockState, @Nullable IBlockDisplayReader iBlockDisplayReader, @Nullable BlockPos blockPos, int n) {
        IBlockColor iBlockColor = this.colors.getByValue(Registry.BLOCK.getId(blockState.getBlock()));
        return iBlockColor == null ? -1 : iBlockColor.getColor(blockState, iBlockDisplayReader, blockPos, n);
    }

    public void register(IBlockColor iBlockColor, Block ... blockArray) {
        for (Block block : blockArray) {
            this.colors.put(iBlockColor, Registry.BLOCK.getId(block));
        }
    }

    private void addColorStates(Set<Property<?>> set, Block ... blockArray) {
        for (Block block : blockArray) {
            this.colorStates.put(block, set);
        }
    }

    private void addColorState(Property<?> property, Block ... blockArray) {
        this.addColorStates(ImmutableSet.of(property), blockArray);
    }

    public Set<Property<?>> getColorProperties(Block block) {
        return this.colorStates.getOrDefault(block, ImmutableSet.of());
    }

    private static int lambda$init$10(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? 2129968 : 7455580;
    }

    private static int lambda$init$9(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        int n2 = blockState.get(StemBlock.AGE);
        int n3 = n2 * 32;
        int n4 = 255 - n2 * 8;
        int n5 = n2 * 4;
        return n3 << 16 | n4 << 8 | n5;
    }

    private static int lambda$init$8(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return 1;
    }

    private static int lambda$init$7(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? BiomeColors.getGrassColor(iBlockDisplayReader, blockPos) : -1;
    }

    private static int lambda$init$6(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return RedstoneWireBlock.getRGBByPower(blockState.get(RedstoneWireBlock.POWER));
    }

    private static int lambda$init$5(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? BiomeColors.getWaterColor(iBlockDisplayReader, blockPos) : -1;
    }

    private static int lambda$init$4(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? BiomeColors.getFoliageColor(iBlockDisplayReader, blockPos) : FoliageColors.getDefault();
    }

    private static int lambda$init$3(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return FoliageColors.getBirch();
    }

    private static int lambda$init$2(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return FoliageColors.getSpruce();
    }

    private static int lambda$init$1(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? BiomeColors.getGrassColor(iBlockDisplayReader, blockPos) : GrassColors.get(0.5, 1.0);
    }

    private static int lambda$init$0(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, int n) {
        return iBlockDisplayReader != null && blockPos != null ? BiomeColors.getGrassColor(iBlockDisplayReader, blockState.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? blockPos.down() : blockPos) : -1;
    }
}

