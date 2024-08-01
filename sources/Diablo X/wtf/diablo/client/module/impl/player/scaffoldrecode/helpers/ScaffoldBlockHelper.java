package wtf.diablo.client.module.impl.player.scaffoldrecode.helpers;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import wtf.diablo.client.module.impl.player.scaffoldrecode.ScaffoldRecodeModule;

public final class ScaffoldBlockHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private ScaffoldBlockHelper() {}

    public static ScaffoldRecodeModule.AnchorData getBlockAnchorData(final BlockPos pos) {
        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (canBePlacedOn(pos.add(facing.getOpposite().getDirectionVec())))
                return new ScaffoldRecodeModule.AnchorData(pos.add(facing.getOpposite().getDirectionVec()), facing);
        }

        for (final EnumFacing enumFacing : EnumFacing.VALUES) {
            final BlockPos currentPos = pos.add(enumFacing.getDirectionVec());

            for (final EnumFacing facing : EnumFacing.VALUES) {
                if (canBePlacedOn(currentPos.add(facing.getOpposite().getDirectionVec())))
                    return new ScaffoldRecodeModule.AnchorData(currentPos.add(facing.getOpposite().getDirectionVec()), facing);
            }
        }
        // return null if no possible anchors
        return null;
    }

    /**
     *
     * @param blockPos - coordinate in world to check
     * @return Returns true if the block is solid and does not block movement
     */
    public static boolean canBePlacedOn(final BlockPos blockPos) {
        if (collidesWithPlayer(blockPos))
            return false;

        final Material material = mc.theWorld.getBlockState(blockPos).getBlock().getMaterial();

        return (material.blocksMovement() && material.isSolid());
    }

    public static boolean collidesWithPlayer(final double minX, final double minY, final double minZ,
                                             final double maxX, final double maxY, final double maxZ) {
        return mc.thePlayer.posX > minX && mc.thePlayer.posX < maxX
                && mc.thePlayer.posY > minY && mc.thePlayer.posY < maxY
                && mc.thePlayer.posZ > minZ && mc.thePlayer.posZ < maxZ;
    }

    public static boolean collidesWithPlayer(final BlockPos pos) {
        return collidesWithPlayer(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    // friend sent me this idk remember who tho
    public static Vec3 getCorrectHitVector(final ScaffoldRecodeModule.AnchorData blockData) {
        final BlockPos blockPos = blockData.getBlockPos();
        final EnumFacing facing = blockData.getEnumFacing();
        double x = blockPos.getX() + 0.5D;
        double y = blockPos.getY() + 0.5D;
        double z = blockPos.getZ() + 0.5D;

        // if facing doesnt equal up and facing doesnt equal down then increment by 5 but if it does equal increment x and z by 0.3
        if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
            // increment y by 0.5
            y += 0.5;
        } else {
            // increment x
            x += 0.3;
            // increment y
            z += 0.3;
        }

        switch (facing) {
            case SOUTH:
            case NORTH:
                x += 0.15;
                break;
            case WEST:
            case EAST:
                // increment z by 0.25
                z += 0.15;
                break;

        }
        // return a new vec3 with corrected x, y, z positions.
        return new Vec3(x, y, z);
    }

}
