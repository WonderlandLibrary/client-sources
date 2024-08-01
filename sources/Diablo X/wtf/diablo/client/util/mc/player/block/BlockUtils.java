package wtf.diablo.client.util.mc.player.block;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import wtf.diablo.client.module.impl.player.scaffoldrecode.ScaffoldRecodeModule;

import java.util.Arrays;
import java.util.List;

public final class BlockUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final List<Block> invalidBlocks = Arrays.asList(Blocks.redstone_wire, Blocks.tallgrass, Blocks.redstone_torch, Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web, Blocks.gravel, Blocks.tnt);

    public static ScaffoldRecodeModule.AnchorData getBlockDataNew(final BlockPos pos) {
        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (!canBePlacedOn(pos.add(facing.getOpposite().getDirectionVec())))
                continue;
            return new ScaffoldRecodeModule.AnchorData(pos.add(facing.getOpposite().getDirectionVec()), facing);
        }

        for (final EnumFacing enumFacing : EnumFacing.VALUES) {
            final BlockPos currentPos = pos.add(enumFacing.getDirectionVec());

            for (final EnumFacing facing : EnumFacing.VALUES) {
                if (!canBePlacedOn(currentPos.add(facing.getOpposite().getDirectionVec())))
                    continue;
                return new ScaffoldRecodeModule.AnchorData(currentPos.add(facing.getOpposite().getDirectionVec()), facing);
            }
        }
        // return null if no possible anchors
        return null;
    }

    /**
     * Gets the specified block.
     * @param blockPos the position of the block
     * @return the block
     */
    public static Block getBlock(final BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos).getBlock();
    }

    /**
     * Checks if a block can be placed on
     * @param blockPos the position of the block to check
     * @return if the block can be placed on
     */
    public static boolean canBePlacedOn(final BlockPos blockPos) {
        return canBePlacedOn(getBlock(blockPos));
    }

    /**
     * Checks if a block can be placed on
     * @param block the block to check
     * @return if the block can be placed on
     */
    public static boolean canBePlacedOn(final Block block) {
        return !(block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockCactus || block instanceof BlockContainer);
    }

    private static EnumFacing getOppositeFacing(final EnumFacing facing) {
        EnumFacing returnValue = facing;
        switch (facing) {
            case NORTH:
                returnValue = EnumFacing.SOUTH;
                break;
            case SOUTH:
                returnValue = EnumFacing.NORTH;
                break;
            case EAST:
                returnValue = EnumFacing.WEST;
                break;
            case WEST:
                returnValue = EnumFacing.EAST;
                break;
            case UP:
                returnValue = EnumFacing.DOWN;
                break;
            case DOWN:
                returnValue = EnumFacing.UP;
                break;
        }
        return returnValue;
    }


    /**
     * From mint client. It was from atlas was a dev and idk if its skidded. i dont think it is, looks like spiny code lol.
     */
    public static int getBlockSlot() {
        for(int i = 36; i < 45; ++i) {
            if(Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i) == null)
                continue;
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock) {
                final ItemBlock iblock = (ItemBlock) stack.getItem();
                final Block block = iblock.getBlock();
                if(block instanceof BlockCactus || block instanceof BlockFence || block instanceof BlockContainer || block instanceof BlockSapling || block instanceof BlockWeb)
                    continue;

                if(!block.isFullBlock())
                    continue;

                return i - 36;
            }

        }
        return -1;
    }

    // from diablo 3 idk if skidded..
    // are u retarded
    public static boolean isBlockUnder() {
        if (!(Minecraft.getMinecraft().thePlayer.posY < 0.0D)) {
            for (int offset = 0; offset < (int) Minecraft.getMinecraft().thePlayer.posY + 2; offset += 2) {
                AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -offset, 0.0D);
                if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()) {
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean isOnEdgeOfBlock() {
        for (int i = 0; i < 4; i++) {
            if (getBlock(new BlockPos(mc.thePlayer.posX + ((i == 0) ? 0.3 : ((i == 1) ? -0.3 : 0)), mc.thePlayer.posY - 1, mc.thePlayer.posZ + ((i == 2) ? 0.3 : ((i == 3) ? -0.3 : 0)))) instanceof BlockAir) {
                return true;
            }
        }
        return false;
    }
}