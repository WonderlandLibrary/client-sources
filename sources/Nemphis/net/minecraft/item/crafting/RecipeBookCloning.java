/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeBookCloning
implements IRecipe {
    private static final String __OBFID = "CL_00000081";

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
        int var3 = 0;
        ItemStack var4 = null;
        int var5 = 0;
        while (var5 < p_77569_1_.getSizeInventory()) {
            ItemStack var6 = p_77569_1_.getStackInSlot(var5);
            if (var6 != null) {
                if (var6.getItem() == Items.written_book) {
                    if (var4 != null) {
                        return false;
                    }
                    var4 = var6;
                } else {
                    if (var6.getItem() != Items.writable_book) {
                        return false;
                    }
                    ++var3;
                }
            }
            ++var5;
        }
        if (var4 != null && var3 > 0) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        int var2 = 0;
        ItemStack var3 = null;
        int var4 = 0;
        while (var4 < p_77572_1_.getSizeInventory()) {
            ItemStack var5 = p_77572_1_.getStackInSlot(var4);
            if (var5 != null) {
                if (var5.getItem() == Items.written_book) {
                    if (var3 != null) {
                        return null;
                    }
                    var3 = var5;
                } else {
                    if (var5.getItem() != Items.writable_book) {
                        return null;
                    }
                    ++var2;
                }
            }
            ++var4;
        }
        if (var3 != null && var2 >= 1 && ItemEditableBook.func_179230_h(var3) < 2) {
            ItemStack var6 = new ItemStack(Items.written_book, var2);
            var6.setTagCompound((NBTTagCompound)var3.getTagCompound().copy());
            var6.getTagCompound().setInteger("generation", ItemEditableBook.func_179230_h(var3) + 1);
            if (var3.hasDisplayName()) {
                var6.setStackDisplayName(var3.getDisplayName());
            }
            return var6;
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ItemStack[] func_179532_b(InventoryCrafting p_179532_1_) {
        ItemStack[] var2 = new ItemStack[p_179532_1_.getSizeInventory()];
        int var3 = 0;
        while (var3 < var2.length) {
            ItemStack var4 = p_179532_1_.getStackInSlot(var3);
            if (var4 != null && var4.getItem() instanceof ItemEditableBook) {
                var2[var3] = var4;
                break;
            }
            ++var3;
        }
        return var2;
    }
}

