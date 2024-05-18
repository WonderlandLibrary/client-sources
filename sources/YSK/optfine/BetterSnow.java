package optfine;

import net.minecraft.client.resources.model.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
    
    public static IBakedModel getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
    }
    
    public static boolean shouldRender(final IBlockAccess blockAccess, final Block block, final IBlockState blockState, final BlockPos blockPos) {
        int n;
        if (!checkBlock(block, blockState)) {
            n = "".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = (hasSnowNeighbours(blockAccess, blockPos) ? 1 : 0);
        }
        return n != 0;
    }
    
    private static boolean hasSnowNeighbours(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block snow_layer = Blocks.snow_layer;
        int n;
        if (blockAccess.getBlockState(blockPos.north()).getBlock() != snow_layer && blockAccess.getBlockState(blockPos.south()).getBlock() != snow_layer && blockAccess.getBlockState(blockPos.west()).getBlock() != snow_layer && blockAccess.getBlockState(blockPos.east()).getBlock() != snow_layer) {
            n = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n = (blockAccess.getBlockState(blockPos.down()).getBlock().isOpaqueCube() ? 1 : 0);
        }
        return n != 0;
    }
    
    private static boolean checkBlock(final Block block, final IBlockState blockState) {
        if (block.isFullCube()) {
            return "".length() != 0;
        }
        if (block.isOpaqueCube()) {
            return "".length() != 0;
        }
        if (block instanceof BlockSnow) {
            return "".length() != 0;
        }
        if (block instanceof BlockBush && (block instanceof BlockDoublePlant || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockSapling || block instanceof BlockTallGrass)) {
            return " ".length() != 0;
        }
        if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockFlowerPot || block instanceof BlockPane || block instanceof BlockReed || block instanceof BlockWall) {
            return " ".length() != 0;
        }
        if (block instanceof BlockRedstoneTorch && blockState.getValue((IProperty<Comparable>)BlockTorch.FACING) == EnumFacing.UP) {
            return " ".length() != 0;
        }
        if (block instanceof BlockLever) {
            final BlockLever.EnumOrientation value = blockState.getValue(BlockLever.FACING);
            if (value == BlockLever.EnumOrientation.UP_X || value == BlockLever.EnumOrientation.UP_Z) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    public static IBlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }
}
