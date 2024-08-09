/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.optifine.Config;

public class BetterSnow {
    private static IBakedModel modelSnowLayer = null;

    public static void update() {
        modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModel(Blocks.SNOW.getDefaultState());
    }

    public static IBakedModel getModelSnowLayer() {
        return modelSnowLayer;
    }

    public static BlockState getStateSnowLayer() {
        return Blocks.SNOW.getDefaultState();
    }

    public static boolean shouldRender(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos) {
        if (!(iBlockDisplayReader instanceof IBlockReader)) {
            return true;
        }
        return !BetterSnow.checkBlock(iBlockDisplayReader, blockState, blockPos) ? false : BetterSnow.hasSnowNeighbours(iBlockDisplayReader, blockPos);
    }

    private static boolean hasSnowNeighbours(IBlockReader iBlockReader, BlockPos blockPos) {
        Block block = Blocks.SNOW;
        if (iBlockReader.getBlockState(blockPos.north()).getBlock() == block || iBlockReader.getBlockState(blockPos.south()).getBlock() == block || iBlockReader.getBlockState(blockPos.west()).getBlock() == block || iBlockReader.getBlockState(blockPos.east()).getBlock() == block) {
            BlockState blockState = iBlockReader.getBlockState(blockPos.down());
            if (blockState.isOpaqueCube(iBlockReader, blockPos)) {
                return false;
            }
            Block block2 = blockState.getBlock();
            if (block2 instanceof StairsBlock) {
                return blockState.get(StairsBlock.HALF) == Half.TOP;
            }
            if (block2 instanceof SlabBlock) {
                return blockState.get(SlabBlock.TYPE) == SlabType.TOP;
            }
        }
        return true;
    }

    private static boolean checkBlock(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        if (blockState.isOpaqueCube(iBlockReader, blockPos)) {
            return true;
        }
        Block block = blockState.getBlock();
        if (block == Blocks.SNOW_BLOCK) {
            return true;
        }
        if (!(block instanceof BushBlock && (block instanceof DoublePlantBlock || block instanceof FlowerBlock || block instanceof MushroomBlock || block instanceof SaplingBlock || block instanceof TallGrassBlock))) {
            if (!(block instanceof FenceBlock || block instanceof FenceGateBlock || block instanceof FlowerPotBlock || block instanceof FourWayBlock || block instanceof SugarCaneBlock || block instanceof WallBlock)) {
                if (block instanceof RedstoneTorchBlock) {
                    return false;
                }
                if (block instanceof StairsBlock) {
                    return blockState.get(StairsBlock.HALF) == Half.TOP;
                }
                if (block instanceof SlabBlock) {
                    return blockState.get(SlabBlock.TYPE) == SlabType.TOP;
                }
                if (block instanceof AbstractButtonBlock) {
                    return blockState.get(AbstractButtonBlock.FACE) != AttachFace.FLOOR;
                }
                if (block instanceof HopperBlock) {
                    return false;
                }
                if (block instanceof LadderBlock) {
                    return false;
                }
                if (block instanceof LeverBlock) {
                    return false;
                }
                if (block instanceof TurtleEggBlock) {
                    return false;
                }
                return block instanceof VineBlock;
            }
            return false;
        }
        return false;
    }
}

