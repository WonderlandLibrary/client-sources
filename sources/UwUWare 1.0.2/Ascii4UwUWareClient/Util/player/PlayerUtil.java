package Ascii4UwUWareClient.Util.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtil {
    private final static Minecraft mc = Minecraft.getMinecraft();


    /**
     * @Kinda shitty check for my Phase, does work but does not look that good. sry for bad code
     */
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(
                mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(
                    mc.thePlayer.boundingBox.minY + 1.0D); y < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY)
                    + 2; ++y) {
                for (int z = MathHelper.floor_double(
                        mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ)
                        + 1; ++z) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
                                mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }

                        if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Shitty Liquid Check, checks if player is in water / lava
     */
    public static boolean isInLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return false;
        }
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }


    /**
     * Same shit for Player being above water / lava
     *
     * @return
     */
    public static boolean isOnLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper
                .floor_double(boundingBox.maxX + 1.0D); x++) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper
                    .floor_double(boundingBox.maxZ + 1.0D); z++) {
                Block block = mc.theWorld.getBlockState((new BlockPos(x, y, z))).getBlock();
                if (block != Blocks.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    /**
     * Checks for player is holding an sword
     * i made it here for the future to dont need to make it 500 times
     * @return
     */
    public static boolean isHoldingSword() {
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }




}
