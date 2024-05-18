package net.minecraft.src;

import java.util.*;

public class WeightedRandomChestContent extends WeightedRandomItem
{
    private ItemStack theItemId;
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    
    public WeightedRandomChestContent(final int par1, final int par2, final int par3, final int par4, final int par5) {
        super(par5);
        this.theItemId = null;
        this.theItemId = new ItemStack(par1, 1, par2);
        this.theMinimumChanceToGenerateItem = par3;
        this.theMaximumChanceToGenerateItem = par4;
    }
    
    public WeightedRandomChestContent(final ItemStack par1ItemStack, final int par2, final int par3, final int par4) {
        super(par4);
        this.theItemId = null;
        this.theItemId = par1ItemStack;
        this.theMinimumChanceToGenerateItem = par2;
        this.theMaximumChanceToGenerateItem = par3;
    }
    
    public static void generateChestContents(final Random par0Random, final WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, final IInventory par2IInventory, final int par3) {
        for (int var4 = 0; var4 < par3; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            final int var6 = var5.theMinimumChanceToGenerateItem + par0Random.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static void generateDispenserContents(final Random par0Random, final WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, final TileEntityDispenser par2TileEntityDispenser, final int par3) {
        for (int var4 = 0; var4 < par3; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            final int var6 = var5.theMinimumChanceToGenerateItem + par0Random.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                par2TileEntityDispenser.setInventorySlotContents(par0Random.nextInt(par2TileEntityDispenser.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    par2TileEntityDispenser.setInventorySlotContents(par0Random.nextInt(par2TileEntityDispenser.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static WeightedRandomChestContent[] func_92080_a(final WeightedRandomChestContent[] par0ArrayOfWeightedRandomChestContent, final WeightedRandomChestContent... par1ArrayOfWeightedRandomChestContent) {
        final WeightedRandomChestContent[] var2 = new WeightedRandomChestContent[par0ArrayOfWeightedRandomChestContent.length + par1ArrayOfWeightedRandomChestContent.length];
        int var3 = 0;
        for (int var4 = 0; var4 < par0ArrayOfWeightedRandomChestContent.length; ++var4) {
            var2[var3++] = par0ArrayOfWeightedRandomChestContent[var4];
        }
        for (final WeightedRandomChestContent var7 : par1ArrayOfWeightedRandomChestContent) {
            var2[var3++] = var7;
        }
        return var2;
    }
}
