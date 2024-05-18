/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeRepairItem
implements IRecipe {
    private static final String __OBFID = "CL_00002156";

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
        ArrayList var3 = Lists.newArrayList();
        int var4 = 0;
        while (var4 < p_77569_1_.getSizeInventory()) {
            ItemStack var5 = p_77569_1_.getStackInSlot(var4);
            if (var5 != null) {
                var3.add(var5);
                if (var3.size() > 1) {
                    ItemStack var6 = (ItemStack)var3.get(0);
                    if (var5.getItem() != var6.getItem() || var6.stackSize != 1 || var5.stackSize != 1 || !var6.getItem().isDamageable()) {
                        return false;
                    }
                }
            }
            ++var4;
        }
        if (var3.size() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        ItemStack var4;
        ArrayList var2 = Lists.newArrayList();
        int var3 = 0;
        while (var3 < p_77572_1_.getSizeInventory()) {
            var4 = p_77572_1_.getStackInSlot(var3);
            if (var4 != null) {
                var2.add(var4);
                if (var2.size() > 1) {
                    ItemStack var5 = (ItemStack)var2.get(0);
                    if (var4.getItem() != var5.getItem() || var5.stackSize != 1 || var4.stackSize != 1 || !var5.getItem().isDamageable()) {
                        return null;
                    }
                }
            }
            ++var3;
        }
        if (var2.size() == 2) {
            ItemStack var10 = (ItemStack)var2.get(0);
            var4 = (ItemStack)var2.get(1);
            if (var10.getItem() == var4.getItem() && var10.stackSize == 1 && var4.stackSize == 1 && var10.getItem().isDamageable()) {
                Item var11 = var10.getItem();
                int var6 = var11.getMaxDamage() - var10.getItemDamage();
                int var7 = var11.getMaxDamage() - var4.getItemDamage();
                int var8 = var6 + var7 + var11.getMaxDamage() * 5 / 100;
                int var9 = var11.getMaxDamage() - var8;
                if (var9 < 0) {
                    var9 = 0;
                }
                return new ItemStack(var10.getItem(), 1, var9);
            }
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 4;
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
            if (var4 != null && var4.getItem().hasContainerItem()) {
                var2[var3] = new ItemStack(var4.getItem().getContainerItem());
            }
            ++var3;
        }
        return var2;
    }
}

