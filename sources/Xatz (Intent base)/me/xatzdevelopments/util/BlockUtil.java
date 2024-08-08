package me.xatzdevelopments.util;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockUtil
{
    private static Minecraft mc;
    public static final List<Block> BLOCK_BLACKLIST;
    
    static {
        BlockUtil.mc = Minecraft.getMinecraft();
        BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch);
    }
    
    public static float[] getDirectionToBlock(final int var0, final int var1, final int var2, final EnumFacing var3) {
        final EntityEgg var4 = new EntityEgg(BlockUtil.mc.theWorld);
        var4.posX = var0 + 0.5;
        var4.posY = var1 + 0.5;
        var4.posZ = var2 + 0.5;
        final EntityEgg entityEgg = var4;
        entityEgg.posX += var3.getDirectionVec().getX() * 0.25;
        final EntityEgg entityEgg2 = var4;
        entityEgg2.posY += var3.getDirectionVec().getY() * 0.25;
        final EntityEgg entityEgg3 = var4;
        entityEgg3.posZ += var3.getDirectionVec().getZ() * 0.25;
        return getDirectionToEntity(var4);
    }
    
    private static float[] getDirectionToEntity(final Entity var0) {
        return new float[] { getYaw(var0) + BlockUtil.mc.thePlayer.rotationYaw, getPitch(var0) + BlockUtil.mc.thePlayer.rotationPitch };
    }
    
    public static float[] getRotationNeededForBlock(final EntityPlayer paramEntityPlayer, final BlockPos pos) {
        final double d1 = pos.getX() - paramEntityPlayer.posX;
        final double d2 = pos.getY() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
        final double d3 = pos.getZ() - paramEntityPlayer.posZ;
        final double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        final float f1 = (float)(Math.atan2(d3, d1) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
        return new float[] { f1, f2 };
    }
    
    public static float getYaw(final Entity var0) {
        final double var = var0.posX - BlockUtil.mc.thePlayer.posX;
        final double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
        double var3;
        if (var2 < 0.0 && var < 0.0) {
            var3 = 90.0 + Math.toDegrees(Math.atan(var2 / var));
        }
        else if (var2 < 0.0 && var > 0.0) {
            var3 = -90.0 + Math.toDegrees(Math.atan(var2 / var));
        }
        else {
            var3 = Math.toDegrees(-Math.atan(var / var2));
        }
        return MathHelper.wrapAngleTo180_float(-(BlockUtil.mc.thePlayer.rotationYaw - (float)var3));
    }
    
    public static float getPitch(final Entity var0) {
        final double var = var0.posX - BlockUtil.mc.thePlayer.posX;
        final double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
        final double var3 = var0.posY - 1.6 + var0.getEyeHeight() - BlockUtil.mc.thePlayer.posY;
        final double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
        final double var5 = -Math.toDegrees(Math.atan(var3 / var4));
        return -MathHelper.wrapAngleTo180_float(BlockUtil.mc.thePlayer.rotationPitch - (float)var5);
    }
    
    public static int findAutoBlockBlock() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = BlockUtil.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
                final Block block = itemBlock.getBlock();
                if (block.isFullCube() && !BlockUtil.BLOCK_BLACKLIST.contains(block)) {
                    return i;
                }
            }
        }
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = BlockUtil.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
                final Block block = itemBlock.getBlock();
                if (!BlockUtil.BLOCK_BLACKLIST.contains(block)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
