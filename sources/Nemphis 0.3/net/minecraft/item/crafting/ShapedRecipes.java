/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipes
implements IRecipe {
    private final int recipeWidth;
    private final int recipeHeight;
    private final ItemStack[] recipeItems;
    private final ItemStack recipeOutput;
    private boolean field_92101_f;
    private static final String __OBFID = "CL_00000093";

    public ShapedRecipes(int p_i1917_1_, int p_i1917_2_, ItemStack[] p_i1917_3_, ItemStack p_i1917_4_) {
        this.recipeWidth = p_i1917_1_;
        this.recipeHeight = p_i1917_2_;
        this.recipeItems = p_i1917_3_;
        this.recipeOutput = p_i1917_4_;
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
        int var3 = 0;
        while (var3 <= 3 - this.recipeWidth) {
            int var4 = 0;
            while (var4 <= 3 - this.recipeHeight) {
                if (this.checkMatch(p_77569_1_, var3, var4, true)) {
                    return true;
                }
                if (this.checkMatch(p_77569_1_, var3, var4, false)) {
                    return true;
                }
                ++var4;
            }
            ++var3;
        }
        return false;
    }

    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
        int var5 = 0;
        while (var5 < 3) {
            int var6 = 0;
            while (var6 < 3) {
                ItemStack var10;
                int var7 = var5 - p_77573_2_;
                int var8 = var6 - p_77573_3_;
                ItemStack var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    var9 = p_77573_4_ ? this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth] : this.recipeItems[var7 + var8 * this.recipeWidth];
                }
                if ((var10 = p_77573_1_.getStackInRowAndColumn(var5, var6)) != null || var9 != null) {
                    if (var10 == null && var9 != null || var10 != null && var9 == null) {
                        return false;
                    }
                    if (var9.getItem() != var10.getItem()) {
                        return false;
                    }
                    if (var9.getMetadata() != 32767 && var9.getMetadata() != var10.getMetadata()) {
                        return false;
                    }
                }
                ++var6;
            }
            ++var5;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        ItemStack var2 = this.getRecipeOutput().copy();
        if (this.field_92101_f) {
            int var3 = 0;
            while (var3 < p_77572_1_.getSizeInventory()) {
                ItemStack var4 = p_77572_1_.getStackInSlot(var3);
                if (var4 != null && var4.hasTagCompound()) {
                    var2.setTagCompound((NBTTagCompound)var4.getTagCompound().copy());
                }
                ++var3;
            }
        }
        return var2;
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    public ShapedRecipes func_92100_c() {
        this.field_92101_f = true;
        return this;
    }
}

