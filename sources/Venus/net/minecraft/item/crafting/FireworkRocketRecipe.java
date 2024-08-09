/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FireworkRocketRecipe
extends SpecialRecipe {
    private static final Ingredient INGREDIENT_PAPER = Ingredient.fromItems(Items.PAPER);
    private static final Ingredient INGREDIENT_GUNPOWDER = Ingredient.fromItems(Items.GUNPOWDER);
    private static final Ingredient INGREDIENT_FIREWORK_STAR = Ingredient.fromItems(Items.FIREWORK_STAR);

    public FireworkRocketRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        int n = 0;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (INGREDIENT_PAPER.test(itemStack)) {
                if (bl) {
                    return true;
                }
                bl = true;
                continue;
            }
            if (!(INGREDIENT_GUNPOWDER.test(itemStack) ? ++n > 3 : !INGREDIENT_FIREWORK_STAR.test(itemStack))) continue;
            return true;
        }
        return bl && n >= 1;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET, 3);
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("Fireworks");
        ListNBT listNBT = new ListNBT();
        int n = 0;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            CompoundNBT compoundNBT2;
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (INGREDIENT_GUNPOWDER.test(itemStack2)) {
                ++n;
                continue;
            }
            if (!INGREDIENT_FIREWORK_STAR.test(itemStack2) || (compoundNBT2 = itemStack2.getChildTag("Explosion")) == null) continue;
            listNBT.add(compoundNBT2);
        }
        compoundNBT.putByte("Flight", (byte)n);
        if (!listNBT.isEmpty()) {
            compoundNBT.put("Explosions", listNBT);
        }
        return itemStack;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.FIREWORK_ROCKET);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_ROCKET;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }
}

