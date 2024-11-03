package net.augustus.utils;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class SigmaBlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    private static List<Block> blacklistedBlocks = Arrays.asList(new Block[] {
            Blocks.air, (Block)Blocks.water, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars,
            Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, (Block)Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest,
            Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate,
            Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever });

    public static float[] getRotationsNeeded(BlockPos pos) {
        double diffX = pos.getX() + 0.5D - mc.thePlayer.posX;
        double diffY = pos.getY() + 0.5D - mc.thePlayer.posY + mc.thePlayer.height;
        double diffZ = pos.getZ() + 0.5D - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
    }

    public static float[] updateDirections(BlockPos pos) {
        float[] looks = getRotationsNeeded(pos);
        if (mc.thePlayer.isCollidedVertically)
            mc.thePlayer.sendQueue.addToSendQueueDirect((Packet)new C03PacketPlayer.C05PacketPlayerLook(looks[0], looks[1], mc.thePlayer.onGround));
        return looks;
    }

    public static void updateTool(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0F;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null)
                if (itemStack.getStrVsBlock(block) > strength) {
                    strength = itemStack.getStrVsBlock(block);
                    bestItemIndex = i;
                }
        }
        if (bestItemIndex != -1)
            mc.thePlayer.inventory.currentItem = bestItemIndex;
    }

    public static boolean isInLiquid() {
        if (mc.thePlayer.isInWater())
            return true;
        boolean inLiquid = false;
        int y = (int)(mc.thePlayer.getEntityBoundingBox()).minY;
        for (int x = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX); x < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1; x++) {
            for (int z = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ); z < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1; z++) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof net.minecraft.block.BlockLiquid))
                        return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

    public static boolean isOnLiquid() {
        if (mc.thePlayer == null)
            return false;
        boolean onLiquid = false;
        int y = (int)(mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D)).minY;
        for (int x = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX); x < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1; x++) {
            for (int z = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ); z < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1; z++) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof net.minecraft.block.BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    public static boolean isOnLiquid(double profondeur) {
        boolean onLiquid = false;
        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - profondeur, mc.thePlayer.posZ)).getBlock().getMaterial().isLiquid())
            onLiquid = true;
        return onLiquid;
    }

    public static boolean isTotalOnLiquid(double profondeur) {
        for (double x = mc.thePlayer.boundingBox.minX; x < mc.thePlayer.boundingBox.maxX; x += 0.009999999776482582D) {
            double z;
            for (z = mc.thePlayer.boundingBox.minZ; z < mc.thePlayer.boundingBox.maxZ; z += 0.009999999776482582D) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, mc.thePlayer.posY - profondeur, z)).getBlock();
                if (!(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraft.block.BlockAir))
                    return false;
            }
        }
        return true;
    }

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty())
            return true;
        return false;
    }

    public static List<Block> getBlacklistedBlocks() {
        return blacklistedBlocks;
    }
}
