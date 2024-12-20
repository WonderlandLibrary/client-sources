// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.src;

import net.minecraft.block.BlockLever;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSnow;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.client.resources.model.IBakedModel;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer;
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178125_b(Blocks.snow_layer.getDefaultState());
    }
    
    public static IBakedModel getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static IBlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }
    
    public static boolean shouldRender(final IBlockAccess blockAccess, final Block block, final IBlockState blockState, final BlockPos blockPos) {
        return checkBlock(block, blockState) && hasSnowNeighbours(blockAccess, blockPos);
    }
    
    private static boolean hasSnowNeighbours(final IBlockAccess blockAccess, final BlockPos pos) {
        final Block blockSnow = Blocks.snow_layer;
        return (blockAccess.getBlockState(pos.offsetNorth()).getBlock() == blockSnow || blockAccess.getBlockState(pos.offsetSouth()).getBlock() == blockSnow || blockAccess.getBlockState(pos.offsetWest()).getBlock() == blockSnow || blockAccess.getBlockState(pos.offsetEast()).getBlock() == blockSnow) && blockAccess.getBlockState(pos.offsetDown()).getBlock().isOpaqueCube();
    }
    
    private static boolean checkBlock(final Block block, final IBlockState blockState) {
        if (block.isFullCube()) {
            return false;
        }
        if (block.isOpaqueCube()) {
            return false;
        }
        if (block instanceof BlockSnow) {
            return false;
        }
        if (block instanceof BlockBush && (block instanceof BlockDoublePlant || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockSapling || block instanceof BlockTallGrass)) {
            return true;
        }
        if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockFlowerPot || block instanceof BlockPane || block instanceof BlockReed || block instanceof BlockWall) {
            return true;
        }
        if (block instanceof BlockRedstoneTorch && blockState.getValue(BlockTorch.FACING_PROP) == EnumFacing.UP) {
            return true;
        }
        if (block instanceof BlockLever) {
            final Comparable orient = blockState.getValue(BlockLever.FACING);
            if (orient == BlockLever.EnumOrientation.UP_X || orient == BlockLever.EnumOrientation.UP_Z) {
                return true;
            }
        }
        return false;
    }
}
