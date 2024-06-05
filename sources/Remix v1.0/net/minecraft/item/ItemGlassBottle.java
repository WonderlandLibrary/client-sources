package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class ItemGlassBottle extends Item
{
    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos var5 = var4.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.func_175151_a(var5.offset(var4.sideHit), var4.sideHit, itemStackIn)) {
                return itemStackIn;
            }
            if (worldIn.getBlockState(var5).getBlock().getMaterial() == Material.water) {
                --itemStackIn.stackSize;
                playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                if (itemStackIn.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    playerIn.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }
        return itemStackIn;
    }
}
