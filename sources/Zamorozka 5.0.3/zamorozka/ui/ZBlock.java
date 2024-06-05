package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class ZBlock {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static IBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getIdFromBlock(getBlock(pos));
    }

    public static ResourceLocation getName(Block block) {
        return Block.REGISTRY.getNameForObject(block);
    }

    public static Material getMaterial(BlockPos pos) {
        return getState(pos).getMaterial();
    }

    public static int getIntegerProperty(IBlockState state, PropertyInteger prop) {
        return state.getValue(prop).intValue();
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        return
                getState(pos).getBoundingBox(mc.world, pos).offset(pos);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return getState(pos).getPlayerRelativeBlockHardness(
                Minecraft.player, mc.world, pos);
    }

    public static boolean canFallThrough(BlockPos pos) {
        return BlockFalling.canFallThrough(getState(pos));
    }

    public static boolean isFullyOpaque(BlockPos pos) {
        return getState(pos).isFullyOpaque();
    }
}
