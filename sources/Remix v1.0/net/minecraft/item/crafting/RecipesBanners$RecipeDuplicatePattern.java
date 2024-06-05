package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;

static class RecipeDuplicatePattern implements IRecipe
{
    private RecipeDuplicatePattern() {
    }
    
    RecipeDuplicatePattern(final Object p_i45779_1_) {
        this();
    }
    
    @Override
    public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
        ItemStack var3 = null;
        ItemStack var4 = null;
        for (int var5 = 0; var5 < p_77569_1_.getSizeInventory(); ++var5) {
            final ItemStack var6 = p_77569_1_.getStackInSlot(var5);
            if (var6 != null) {
                if (var6.getItem() != Items.banner) {
                    return false;
                }
                if (var3 != null && var4 != null) {
                    return false;
                }
                final int var7 = TileEntityBanner.getBaseColor(var6);
                final boolean var8 = TileEntityBanner.func_175113_c(var6) > 0;
                if (var3 != null) {
                    if (var8) {
                        return false;
                    }
                    if (var7 != TileEntityBanner.getBaseColor(var3)) {
                        return false;
                    }
                    var4 = var6;
                }
                else if (var4 != null) {
                    if (!var8) {
                        return false;
                    }
                    if (var7 != TileEntityBanner.getBaseColor(var4)) {
                        return false;
                    }
                    var3 = var6;
                }
                else if (var8) {
                    var3 = var6;
                }
                else {
                    var4 = var6;
                }
            }
        }
        return var3 != null && var4 != null;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
        for (int var2 = 0; var2 < p_77572_1_.getSizeInventory(); ++var2) {
            final ItemStack var3 = p_77572_1_.getStackInSlot(var2);
            if (var3 != null && TileEntityBanner.func_175113_c(var3) > 0) {
                final ItemStack var4 = var3.copy();
                var4.stackSize = 1;
                return var4;
            }
        }
        return null;
    }
    
    @Override
    public int getRecipeSize() {
        return 2;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public ItemStack[] func_179532_b(final InventoryCrafting p_179532_1_) {
        final ItemStack[] var2 = new ItemStack[p_179532_1_.getSizeInventory()];
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final ItemStack var4 = p_179532_1_.getStackInSlot(var3);
            if (var4 != null) {
                if (var4.getItem().hasContainerItem()) {
                    var2[var3] = new ItemStack(var4.getItem().getContainerItem());
                }
                else if (var4.hasTagCompound() && TileEntityBanner.func_175113_c(var4) > 0) {
                    var2[var3] = var4.copy();
                    var2[var3].stackSize = 1;
                }
            }
        }
        return var2;
    }
}
