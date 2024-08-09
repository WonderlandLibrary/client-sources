/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TippedArrowRecipe
extends SpecialRecipe {
    public TippedArrowRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for (int i = 0; i < craftingInventory.getWidth(); ++i) {
                for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth());
                    if (itemStack.isEmpty()) {
                        return true;
                    }
                    Item item = itemStack.getItem();
                    if (!(i == 1 && j == 1 ? item != Items.LINGERING_POTION : item != Items.ARROW)) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack itemStack = craftingInventory.getStackInSlot(1 + craftingInventory.getWidth());
        if (itemStack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        }
        ItemStack itemStack2 = new ItemStack(Items.TIPPED_ARROW, 8);
        PotionUtils.addPotionToItemStack(itemStack2, PotionUtils.getPotionFromItem(itemStack));
        PotionUtils.appendEffects(itemStack2, PotionUtils.getFullEffectsFromItem(itemStack));
        return itemStack2;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n >= 2 && n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_TIPPEDARROW;
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

