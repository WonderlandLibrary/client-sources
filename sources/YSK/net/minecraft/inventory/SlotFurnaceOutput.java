package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class SlotFurnaceOutput extends Slot
{
    private int field_75228_b;
    private EntityPlayer thePlayer;
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
        if (!this.thePlayer.worldObj.isRemote) {
            int i = this.field_75228_b;
            final float smeltingExperience = FurnaceRecipes.instance().getSmeltingExperience(itemStack);
            if (smeltingExperience == 0.0f) {
                i = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else if (smeltingExperience < 1.0f) {
                int floor_float = MathHelper.floor_float(i * smeltingExperience);
                if (floor_float < MathHelper.ceiling_float_int(i * smeltingExperience) && Math.random() < i * smeltingExperience - floor_float) {
                    ++floor_float;
                }
                i = floor_float;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5, this.thePlayer.posZ + 0.5, xpSplit));
            }
        }
        this.field_75228_b = "".length();
        if (itemStack.getItem() == Items.iron_ingot) {
            this.thePlayer.triggerAchievement(AchievementList.acquireIron);
        }
        if (itemStack.getItem() == Items.cooked_fish) {
            this.thePlayer.triggerAchievement(AchievementList.cookFish);
        }
    }
    
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.field_75228_b += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
    }
    
    public SlotFurnaceOutput(final EntityPlayer thePlayer, final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
        this.thePlayer = thePlayer;
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return "".length() != 0;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onCrafting(itemStack);
        super.onPickupFromSlot(entityPlayer, itemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.field_75228_b += n;
        this.onCrafting(itemStack);
    }
}
