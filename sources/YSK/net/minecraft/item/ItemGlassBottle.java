package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;

public class ItemGlassBottle extends Item
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, " ".length() != 0);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (!entityPlayer.canPlayerEdit(blockPos.offset(movingObjectPositionFromPlayer.sideHit), movingObjectPositionFromPlayer.sideHit, itemStack)) {
                return itemStack;
            }
            if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
                itemStack.stackSize -= " ".length();
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                if (itemStack.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, " ".length(), "".length()), "".length() != 0);
                }
            }
        }
        return itemStack;
    }
    
    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
}
