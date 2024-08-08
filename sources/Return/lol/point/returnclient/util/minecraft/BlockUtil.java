package lol.point.returnclient.util.minecraft;

import lol.point.returnclient.util.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.stream.Stream;

public class BlockUtil implements MinecraftInstance {

    public static final Block[] IRREGULAR_BLOCKS = {
            Blocks.air,
            Blocks.water,
            Blocks.lava,
            Blocks.flowing_water,
            Blocks.flowing_lava,
            Blocks.command_block,
            Blocks.chest,
            Blocks.crafting_table,
            Blocks.enchanting_table,
            Blocks.furnace,
            Blocks.noteblock,
            Blocks.torch,
            Blocks.redstone_torch,
            Blocks.web,
            Blocks.carpet,
            Blocks.nether_brick_fence,
            Blocks.oak_fence,
            Blocks.acacia_fence,
            Blocks.birch_fence,
            Blocks.jungle_fence,
            Blocks.dark_oak_fence,
            Blocks.spruce_fence,
            Blocks.oak_fence_gate,
            Blocks.acacia_fence_gate,
            Blocks.birch_fence_gate,
            Blocks.jungle_fence_gate,
            Blocks.dark_oak_fence_gate,
            Blocks.spruce_fence_gate,
            Blocks.torch,
            Blocks.redstone_torch,
            Blocks.stone_slab,
            Blocks.stone_slab2,
            Blocks.wooden_slab,
            Blocks.snow_layer,
            Blocks.ladder,
            Blocks.sapling,
            Blocks.vine,
            Blocks.tallgrass,
            Blocks.waterlily,
            Blocks.deadbush,
            Blocks.redstone_wire,
            Blocks.chest,
            Blocks.ender_chest,
            Blocks.trapped_chest,
            Blocks.double_plant,
            Blocks.flower_pot,
            Blocks.red_flower,
            Blocks.yellow_flower,
            Blocks.skull,
            Blocks.farmland,
            Blocks.standing_sign,
            Blocks.wall_sign,
    };

    public static Block getBlock(BlockPos blockPos) {
        return getBlockState(blockPos).getBlock();
    }

    public static IBlockState getBlockState(BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos);
    }

    public static Block getBlock(double x, double y, double z) {
        return getBlock(new BlockPos(x, y, z));
    }

    public static Block block(double x, double y, double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static boolean canPlaceOnBlock(final Block block) {
        return Stream.of(IRREGULAR_BLOCKS).noneMatch(block::equals);
    }

    public static Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return block(mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ);
    }

}
