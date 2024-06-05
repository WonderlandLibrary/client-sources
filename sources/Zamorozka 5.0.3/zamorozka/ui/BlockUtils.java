package zamorozka.ui;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.main.Zamorozka;

public class BlockUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isOnLiquid() {
        try {
            boolean onLiquid = false;
            int y = (int) (Minecraft.player.boundingBox.minY - 0.01D);
            for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x <
                    MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; x++) {
                for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z <
                        MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; z++) {
                    Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        onLiquid = true;
                    }
                }
            }
            return onLiquid;
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean placeBlock(BlockPos pos) {
        float dir = Minecraft.player.rotationYaw + ((Minecraft.player.moveForward < 0) ? 180 : 0) + ((Minecraft.player.moveStrafing > 0) ? (-90F * ((Minecraft.player.moveForward < 0) ? -.5F : ((Minecraft.player.moveForward > 0) ? .4F : 1F))) : 0);
        float xDir = (float) Math.cos((dir + 90F) * Math.PI / 180);
        float zDir = (float) Math.sin((dir + 90F) * Math.PI / 180);
        Vec3d eyesPos = new Vec3d(Minecraft.player.posX, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
        EnumFacing[] values;
        int length = (values = EnumFacing.values()).length;

        for (int i = 0; i < length; i++) {
            EnumFacing side = values[i];
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if ((eyesPos.squareDistanceTo(new Vec3d(pos).addVector(0.5D, 0.5D, 0.5D)) < eyesPos.squareDistanceTo(new Vec3d(neighbor).addVector(0.5D, 0.5D, 0.5D))) &&
                    (canBeClicked(neighbor))) {
                Vec3d hitVec = new Vec3d(neighbor).addVector(0.1D, 0.1D, 0.1D).add(new Vec3d(side2.getDirectionVec()).scale(0.5D));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625D) {
                    if (Zamorozka.settingsManager.getSettingByName("BlockFase3").getValBoolean()) {
                        EntityUtils1.faceVectorPacketInstant(hitVec);
                    }
                    Minecraft.player.swingArm(EnumHand.MAIN_HAND);

                    Zamorozka.mc().playerController.processRightClickBlock(Minecraft.player, mc.world, neighbor, side2, Vec3d.ZERO, EnumHand.MAIN_HAND);

                    return true;
                }
            }
        }
        return false;
    }

    public static boolean placeBlockScaffold(BlockPos pos) {

        Vec3d eyesPos = new Vec3d(Minecraft.player.posX, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
        EnumFacing[] values;
        int length = (values = EnumFacing.values()).length;
        for (int i = 0; i < length; i++) {
            EnumFacing side = values[i];

            BlockPos neighbor = pos.offset(side);

            EnumFacing side2 = side.getOpposite();
            if ((eyesPos.squareDistanceTo(new Vec3d(pos).addVector(0.5D, 0.5D, 0.5D)) < eyesPos.squareDistanceTo(new Vec3d(neighbor).addVector(0.5D, 0.5D, 0.5D))) &&
                    (canBeClicked(neighbor))) {
                Vec3d hitVec = new Vec3d(neighbor).addVector(0.1D, 0.1D, 0.1D).add(new Vec3d(side2.getDirectionVec()).scale(0.5D));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625D) {
                    if (Zamorozka.settingsManager.getSettingByName("BlockFase3").getValBoolean()) {
                        EntityUtils1.faceVectorPacketInstant(hitVec);
                    }

                    if (Zamorozka.settingsManager.getSettingByName("BlockFase2").getValBoolean()) {
                        faceVectorPacketInstant(hitVec);
                    }
                    Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                    mc.playerController.processRightClickBlock(Minecraft.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    return true;
                }
            }
        }
        return false;
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = getNeededRotations2(vec);

        Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], false));

        Minecraft.player.prevRenderYawOffset = rotations[0];
        Minecraft.player.prevRenderArmPitch = rotations[1];
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{Minecraft.player.rotationYaw + MathHelper.wrapDegrees(yaw), Minecraft.player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch)};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(Minecraft.player.posX,
                Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock().canCollideCheck(mc.world.getBlockState(pos), false);
    }

    public static void faceBlock(BlockPos blockPos) {
        double X = blockPos.getX() + 0.5D - Minecraft.player.posX;
        double Y = blockPos.getY() + 0.5D - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
        double Z = blockPos.getZ() + 0.5D - Minecraft.player.posZ;
        double dist = MathHelper.sqrt(X * X + Z * Z);
        Minecraft.player.renderYawOffset = (float) (Math.atan2(Z, X) * 90) - 90.0F;
        Minecraft.player.renderArmPitch = (float) -(Math.atan2(Y, dist) * 90);
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation((float) ((Math.atan2(Z, X) * 180.0D / 3.141592653589793D) - 90.0F), (float) -(Math.atan2(Y, dist) * 180.0D / 4.141592653589793D), true));

        // mc.player.rotationYaw=(float) ((Math.atan2(X, Z) * 90) - 90.0F);
        //mc.player.rotationPitch=(float)-(Math.atan2(Y, dist) * 90);
    }

    public static boolean isInLiquid() {
        boolean inLiquid = false;
        int y = (int) Minecraft.player.boundingBox.minY;
        for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x <
                MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z <
                    MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; z++) {
                Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

    public static boolean isOnIce() {
        boolean onIce = false;
        int y = (int) (Minecraft.player.boundingBox.minY - 1.0D);
        for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x <
                MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z <
                    MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; z++) {
                Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir)) && (
                        ((block instanceof BlockPackedIce)) || ((block instanceof BlockIce)))) {
                    onIce = true;
                }
            }
        }
        return onIce;
    }

    public static boolean isOnLadder() {
        boolean onLadder = false;
        int y = (int) (Minecraft.player.boundingBox.minY - 1.0D);
        for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x <
                MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z <
                    MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; z++) {
                Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLadder)) {
                        return false;
                    }
                    onLadder = true;
                }
            }
        }
        return (onLadder) || (Minecraft.player.isOnLadder());
    }

    public static boolean checkForNeighbours(BlockPos blockPos) {
        // check if we don't have a block adjacent to blockpos
        if (!hasNeighbour(blockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if (!Wrapper.getWorld().getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }

    public static IBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static Material getMaterial(BlockPos pos) {
        return getState(pos).getMaterial();
    }

}
