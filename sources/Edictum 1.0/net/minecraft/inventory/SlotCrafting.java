package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotCrafting extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix;

    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer thePlayer;

    /**
     * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
     */
    private int amountCrafted;

    public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.thePlayer = player;
        this.craftMatrix = craftingInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
        if (this.amountCrafted > 0)
        {
            stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        }

        this.amountCrafted = 0;

        if (stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
        {
            this.thePlayer.addStat(AchievementList.BUILD_WORK_BENCH);
        }

        if (stack.getItem() instanceof ItemPickaxe)
        {
            this.thePlayer.addStat(AchievementList.BUILD_PICKAXE);
        }

        if (stack.getItem() == Item.getItemFromBlock(Blocks.FURNACE))
        {
            this.thePlayer.addStat(AchievementList.BUILD_FURNACE);
        }

        if (stack.getItem() instanceof ItemHoe)
        {
            this.thePlayer.addStat(AchievementList.BUILD_HOE);
        }

        if (stack.getItem() == Items.BREAD)
        {
            this.thePlayer.addStat(AchievementList.MAKE_BREAD);
        }

        if (stack.getItem() == Items.CAKE)
        {
            this.thePlayer.addStat(AchievementList.BAKE_CAKE);
        }

        if (stack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)stack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD)
        {
            this.thePlayer.addStat(AchievementList.BUILD_BETTER_PICKAXE);
        }

        if (stack.getItem() instanceof ItemSword)
        {
            this.thePlayer.addStat(AchievementList.BUILD_SWORD);
        }

        if (stack.getItem() == Item.getItemFromBlock(Blocks.ENCHANTING_TABLE))
        {
            this.thePlayer.addStat(AchievementList.ENCHANTMENTS);
        }

        if (stack.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF))
        {
            this.thePlayer.addStat(AchievementList.BOOKCASE);
        }
    }

    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        this.onCrafting(stack);
        ItemStack[] aitemstack = CraftingManager.getInstance().getRemainingItems(this.craftMatrix, playerIn.worldObj);

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
            ItemStack itemstack1 = aitemstack[i];

            if (itemstack != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
                itemstack = this.craftMatrix.getStackInSlot(i);
            }

            if (itemstack1 != null)
            {
                if (itemstack == null)
                {
                    this.craftMatrix.setInventorySlotContents(i, itemstack1);
                }
                else if (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1))
                {
                    itemstack1.stackSize += itemstack.stackSize;
                    this.craftMatrix.setInventorySlotContents(i, itemstack1);
                }
                else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1))
                {
                    this.thePlayer.dropItem(itemstack1, false);
                }
            }
        }
    }
}
