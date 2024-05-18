package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;

public class SlotCrafting extends Slot
{
    private int amountCrafted;
    private final InventoryCrafting craftMatrix;
    private final EntityPlayer thePlayer;
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.amountCrafted += n;
        this.onCrafting(itemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        if (this.amountCrafted > 0) {
            itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        }
        this.amountCrafted = "".length();
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
        }
        if (itemStack.getItem() instanceof ItemPickaxe) {
            this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
            this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
        }
        if (itemStack.getItem() instanceof ItemHoe) {
            this.thePlayer.triggerAchievement(AchievementList.buildHoe);
        }
        if (itemStack.getItem() == Items.bread) {
            this.thePlayer.triggerAchievement(AchievementList.makeBread);
        }
        if (itemStack.getItem() == Items.cake) {
            this.thePlayer.triggerAchievement(AchievementList.bakeCake);
        }
        if (itemStack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)itemStack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
            this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
        }
        if (itemStack.getItem() instanceof ItemSword) {
            this.thePlayer.triggerAchievement(AchievementList.buildSword);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.enchantments);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
            this.thePlayer.triggerAchievement(AchievementList.bookcase);
        }
        if (itemStack.getItem() == Items.golden_apple && itemStack.getMetadata() == " ".length()) {
            this.thePlayer.triggerAchievement(AchievementList.overpowered);
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return "".length() != 0;
    }
    
    public SlotCrafting(final EntityPlayer thePlayer, final InventoryCrafting craftMatrix, final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
        this.thePlayer = thePlayer;
        this.craftMatrix = craftMatrix;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onCrafting(itemStack);
        final ItemStack[] func_180303_b = CraftingManager.getInstance().func_180303_b(this.craftMatrix, entityPlayer.worldObj);
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < func_180303_b.length) {
            final ItemStack stackInSlot = this.craftMatrix.getStackInSlot(i);
            final ItemStack itemStack2 = func_180303_b[i];
            if (stackInSlot != null) {
                this.craftMatrix.decrStackSize(i, " ".length());
            }
            if (itemStack2 != null) {
                if (this.craftMatrix.getStackInSlot(i) == null) {
                    this.craftMatrix.setInventorySlotContents(i, itemStack2);
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else if (!this.thePlayer.inventory.addItemStackToInventory(itemStack2)) {
                    this.thePlayer.dropPlayerItemWithRandomChoice(itemStack2, "".length() != 0);
                }
            }
            ++i;
        }
    }
}
