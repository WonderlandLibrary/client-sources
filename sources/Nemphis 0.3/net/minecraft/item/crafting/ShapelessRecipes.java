/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ShapelessRecipes
implements IRecipe {
    private final ItemStack recipeOutput;
    private final List recipeItems;
    private static final String __OBFID = "CL_00000094";

    public ShapelessRecipes(ItemStack p_i1918_1_, List p_i1918_2_) {
        this.recipeOutput = p_i1918_1_;
        this.recipeItems = p_i1918_2_;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
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

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
        ArrayList var3 = Lists.newArrayList((Iterable)this.recipeItems);
        int var4 = 0;
        while (var4 < p_77569_1_.func_174923_h()) {
            int var5 = 0;
            while (var5 < p_77569_1_.func_174922_i()) {
                ItemStack var6 = p_77569_1_.getStackInRowAndColumn(var5, var4);
                if (var6 != null) {
                    boolean var7 = false;
                    for (ItemStack var9 : var3) {
                        if (var6.getItem() != var9.getItem() || var9.getMetadata() != 32767 && var6.getMetadata() != var9.getMetadata()) continue;
                        var7 = true;
                        var3.remove(var9);
                        break;
                    }
                    if (!var7) {
                        return false;
                    }
                }
                ++var5;
            }
            ++var4;
        }
        return var3.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}

