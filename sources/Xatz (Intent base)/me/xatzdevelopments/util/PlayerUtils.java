package me.xatzdevelopments.util;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.*;

public final class PlayerUtils
{
    private static final Minecraft mc;
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static boolean isHoldingSword() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null && PlayerUtils.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
    
    public static boolean isHoldingFood() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null && PlayerUtils.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood;
    }
    
    public static boolean isHoldingGuiSelector() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null && PlayerUtils.mc.thePlayer.getCurrentEquippedItem().getItem() == Item.getItemById(345);
    }
    
    public static boolean isOnSameTeam(final EntityPlayer entity) {
        if (entity.getTeam() != null && PlayerUtils.mc.thePlayer.getTeam() != null) {
            final char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            final char c2 = PlayerUtils.mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }
    
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        final AxisAlignedBB playerBB = PlayerUtils.mc.thePlayer.getEntityBoundingBox();
        final WorldClient world = PlayerUtils.mc.theWorld;
        final int y = (int)playerBB.offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
                final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    public static boolean isInsideBlock() {
        final EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        final WorldClient world = PlayerUtils.mc.theWorld;
        final AxisAlignedBB bb = player.getEntityBoundingBox();
        for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
                    final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) != null && player.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
