// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import java.util.Arrays;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.client.Minecraft;

public class TestUtil
{
    private static final Minecraft mc;
    public static List<Block> emptyBlocks;
    public static List<Block> rightclickableBlocks;
    
    public static boolean canSeeBlock(final BlockPos p_Pos) {
        return TestUtil.mc.player != null && TestUtil.mc.world.rayTraceBlocks(new Vec3d(TestUtil.mc.player.posX, TestUtil.mc.player.posY + TestUtil.mc.player.getEyeHeight(), TestUtil.mc.player.posZ), new Vec3d((double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ()), false, true, false) == null;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
        final RayTraceResult result = TestUtil.mc.world.rayTraceBlocks(new Vec3d(TestUtil.mc.player.posX, TestUtil.mc.player.posY + TestUtil.mc.player.getEyeHeight(), TestUtil.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        TestUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || TestUtil.mc.world.rayTraceBlocks(new Vec3d(TestUtil.mc.player.posX, TestUtil.mc.player.posY + TestUtil.mc.player.getEyeHeight(), TestUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static void openBlock(final BlockPos pos) {
        final EnumFacing[] values;
        final EnumFacing[] facings = values = EnumFacing.values();
        for (final EnumFacing f : values) {
            final Block neighborBlock = TestUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
            if (TestUtil.emptyBlocks.contains(neighborBlock)) {
                TestUtil.mc.playerController.processRightClickBlock(TestUtil.mc.player, TestUtil.mc.world, pos, f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                return;
            }
        }
    }
    
    public static boolean placeBlock(final BlockPos pos) {
        if (isBlockEmpty(pos)) {
            final EnumFacing[] values;
            final EnumFacing[] facings = values = EnumFacing.values();
            for (final EnumFacing f : values) {
                final Block neighborBlock = TestUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5);
                if (!TestUtil.emptyBlocks.contains(neighborBlock) && TestUtil.mc.player.getPositionEyes(TestUtil.mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25) {
                    final float[] rot = { TestUtil.mc.player.rotationYaw, TestUtil.mc.player.rotationPitch };
                    if (TestUtil.rightclickableBlocks.contains(neighborBlock)) {
                        TestUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)TestUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    TestUtil.mc.playerController.processRightClickBlock(TestUtil.mc.player, TestUtil.mc.world, pos.offset(f), f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                    if (TestUtil.rightclickableBlocks.contains(neighborBlock)) {
                        TestUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)TestUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    TestUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isBlockEmpty(final BlockPos pos) {
        try {
            if (TestUtil.emptyBlocks.contains(TestUtil.mc.world.getBlockState(pos).getBlock())) {
                final AxisAlignedBB box = new AxisAlignedBB(pos);
                final Iterator entityIter = TestUtil.mc.world.loadedEntityList.iterator();
                while (entityIter.hasNext()) {
                    final Entity e;
                    if ((e = entityIter.next()) instanceof EntityLivingBase && box.intersects(e.getEntityBoundingBox())) {
                        return false;
                    }
                }
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static boolean canPlaceBlock(final BlockPos pos) {
        if (isBlockEmpty(pos)) {
            final EnumFacing[] values;
            final EnumFacing[] facings = values = EnumFacing.values();
            for (final EnumFacing f : values) {
                if (!TestUtil.emptyBlocks.contains(TestUtil.mc.world.getBlockState(pos.offset(f)).getBlock())) {
                    final Vec3d vec3d = new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5);
                    if (TestUtil.mc.player.getPositionEyes(TestUtil.mc.getRenderPartialTicks()).distanceTo(vec3d) <= 4.25) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        TestUtil.emptyBlocks = Arrays.asList(Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, (Block)Blocks.TALLGRASS, (Block)Blocks.FIRE);
        TestUtil.rightclickableBlocks = Arrays.asList((Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, (Block)Blocks.BEACON, Blocks.BED, Blocks.FURNACE, (Block)Blocks.OAK_DOOR, (Block)Blocks.SPRUCE_DOOR, (Block)Blocks.BIRCH_DOOR, (Block)Blocks.JUNGLE_DOOR, (Block)Blocks.ACACIA_DOOR, (Block)Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, (Block)Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }
}
