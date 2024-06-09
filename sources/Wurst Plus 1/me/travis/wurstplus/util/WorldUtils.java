package me.travis.wurstplus.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class WorldUtils
{
    
    public static void placeBlockMainHand(final BlockPos pos) {
        placeBlock(EnumHand.MAIN_HAND, pos);
    }
    
    public static void placeBlock(final EnumHand hand, final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (Minecraft.getMinecraft().world.getBlockState(neighbor).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    final double diffX = hitVec.x - eyesPos.x;
                    final double diffY = hitVec.y - eyesPos.y;
                    final double diffZ = hitVec.z - eyesPos.z;
                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
                    final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
                    final float[] rotations = { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
                    Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Minecraft.getMinecraft().player.onGround));
                    Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
                    Minecraft.getMinecraft().playerController.processRightClickBlock(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, neighbor, side2, hitVec, hand);
                    Minecraft.getMinecraft().player.swingArm(hand);
                    Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
                    return;
                }
            }
        }
    }
    
    public static int findBlock(final Block block) {
        return findItem(new ItemStack(block).getItem());
    }
    
    public static int findItem(final Item item) {
        try {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
                if (item == stack.getItem()) {
                    return i;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static void rotate(final float yaw, final float pitch) {
        Minecraft.getMinecraft().player.rotationYaw = yaw;
        Minecraft.getMinecraft().player.rotationPitch = pitch;
    }
    
    public static void rotate(final double[] rotations) {
        Minecraft.getMinecraft().player.rotationYaw = (float)rotations[0];
        Minecraft.getMinecraft().player.rotationPitch = (float)rotations[1];
    }
    
    public static void lookAtBlock(final BlockPos blockToLookAt) {
        rotate(calculateLookAt(blockToLookAt.getX(), blockToLookAt.getY(), blockToLookAt.getZ(), (EntityPlayer)Minecraft.getMinecraft().player));
    }
    
    public static BlockPos getRelativeBlockPos(final EntityPlayer player, final int ChangeX, final int ChangeY, final int ChangeZ) {
        final int[] playerCoords = { (int)player.posX, (int)player.posY, (int)player.posZ };
        BlockPos pos;
        if (player.posX < 0.0 && player.posZ < 0.0) {
            pos = new BlockPos(playerCoords[0] + ChangeX - 1, playerCoords[1] + ChangeY, playerCoords[2] + ChangeZ - 1);
        }
        else if (player.posX < 0.0 && player.posZ > 0.0) {
            pos = new BlockPos(playerCoords[0] + ChangeX - 1, playerCoords[1] + ChangeY, playerCoords[2] + ChangeZ);
        }
        else if (player.posX > 0.0 && player.posZ < 0.0) {
            pos = new BlockPos(playerCoords[0] + ChangeX, playerCoords[1] + ChangeY, playerCoords[2] + ChangeZ - 1);
        }
        else {
            pos = new BlockPos(playerCoords[0] + ChangeX, playerCoords[1] + ChangeY, playerCoords[2] + ChangeZ);
        }
        return pos;
    }

    public static String vectorToString(final Vec3d vector, final boolean... includeY) {
        final boolean reallyIncludeY = includeY.length <= 0 || includeY[0];
        final StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append((int)Math.floor(vector.x));
        builder.append(", ");
        if (reallyIncludeY) {
            builder.append((int)Math.floor(vector.y));
            builder.append(", ");
        }
        builder.append((int)Math.floor(vector.z));
        builder.append(")");
        return builder.toString();
    }
    
    public static String vectorToString(final BlockPos pos) {
        return vectorToString(new Vec3d((Vec3i)pos), new boolean[0]);
    }
}
