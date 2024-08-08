// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class BurrowUtil implements Util
{
    public static final Minecraft mc;
    
    public static boolean placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BurrowUtil.mc.world.getBlockState(neighbour).getBlock();
        if (!BurrowUtil.mc.player.isSneaking()) {
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BurrowUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BurrowUtil.mc.player.setSneaking(true);
            sneaking = true;
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BurrowUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BurrowUtil.mc.rightClickDelayTimer = 4;
        return sneaking || isSneaking;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BurrowUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BurrowUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BurrowUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BurrowUtil.mc.player.posX, BurrowUtil.mc.player.posY + BurrowUtil.mc.player.getEyeHeight(), BurrowUtil.mc.player.posZ);
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BurrowUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BurrowUtil.mc.player.rotationYaw), BurrowUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BurrowUtil.mc.player.rotationPitch) };
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], BurrowUtil.mc.player.onGround));
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BurrowUtil.mc.playerController.processRightClickBlock(BurrowUtil.mc.player, BurrowUtil.mc.world, pos, direction, vec, hand);
        }
        BurrowUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BurrowUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = BurrowUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        BurrowUtil.mc.player.inventory.currentItem = slot;
        BurrowUtil.mc.playerController.updateController();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
