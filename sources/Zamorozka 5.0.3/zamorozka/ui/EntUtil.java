package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntUtil implements MCUtil {

    public static Block isColliding(double posX, double posY, double posZ) {
        Block block = null;
        if (Minecraft.player != null) {
            final AxisAlignedBB bb = Minecraft.player.getRidingEntity() != null ? Minecraft.player.getRidingEntity().getEntityBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ) : Minecraft.player.getEntityBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ);
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }
        }
        return block;
    }

    public static boolean isInLiquid() {
        if (Minecraft.player != null) {
            if (Minecraft.player.fallDistance >= 3.0f) {
                return false;
            }
            boolean inLiquid = false;
            final AxisAlignedBB bb = Minecraft.player.getRidingEntity() != null ? Minecraft.player.getRidingEntity().getEntityBoundingBox() : Minecraft.player.getEntityBoundingBox();
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    final Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (!(block instanceof BlockAir)) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        inLiquid = true;
                    }
                }
            }
            return inLiquid;
        }
        return false;
    }

    public static boolean isInBlock() {
        for (int x = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minX); x < MathHelper.floor(Minecraft.player.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minY); y < MathHelper.floor(Minecraft.player.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minZ); z < MathHelper.floor(Minecraft.player.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.world.getBlockState(new BlockPos(x, y, z)), mc.world, new BlockPos(x, y, z));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null) && (Minecraft.player.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}