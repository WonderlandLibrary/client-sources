package dev.darkmoon.client.utility.move;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class BlockUtility implements Utility {
    public static boolean checkLiquid(float offset) {
        return mc.player != null && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - offset, mc.player.posZ)).getBlock() instanceof BlockLiquid;
    }

    public static boolean isBlockUnder(float under) {
        if (mc.player.posY < 0.0D) {
            return false;
        } else {
            AxisAlignedBB box = mc.player.getEntityBoundingBox().offset(0.0D, -under, 0.0D);
            return mc.world.getCollisionBoxes(mc.player, box).isEmpty();
        }
    }
}
