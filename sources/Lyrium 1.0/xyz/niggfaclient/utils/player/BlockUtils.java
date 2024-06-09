// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import xyz.niggfaclient.utils.Utils;

public class BlockUtils extends Utils
{
    public static List<Block> BLACKLISTED_BLOCKS;
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return BlockUtils.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtils.mc.theWorld.getBlockState(pos);
    }
    
    public static boolean isAirBlock(final Block block) {
        return block.getMaterial().isReplaceable() && (!(block instanceof BlockSnow) || block.getBlockBoundsMaxY() <= 0.125);
    }
    
    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final double x = posX - BlockUtils.mc.thePlayer.posX;
        final double y = posY - (BlockUtils.mc.thePlayer.posY + BlockUtils.mc.thePlayer.getEyeHeight());
        final double z = posZ - BlockUtils.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    static {
        BlockUtils.BLACKLISTED_BLOCKS = Arrays.asList(Blocks.furnace, Blocks.anvil, Blocks.wooden_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.sapling, Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.chest, Blocks.anvil, Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.gravel);
    }
}
