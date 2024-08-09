/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FireworkStarFadeRecipe
extends SpecialRecipe {
    private static final Ingredient INGREDIENT_FIREWORK_STAR = Ingredient.fromItems(Items.FIREWORK_STAR);

    public FireworkStarFadeRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() instanceof DyeItem) {
                bl = true;
                continue;
            }
            if (!INGREDIENT_FIREWORK_STAR.test(itemStack)) {
                return true;
            }
            if (bl2) {
                return true;
            }
            bl2 = true;
        }
        return bl2 && bl;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ArrayList<Integer> arrayList = Lists.newArrayList();
        ItemStack itemStack = null;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            Item item = itemStack2.getItem();
            if (item instanceof DyeItem) {
                arrayList.add(((DyeItem)item).getDyeColor().getFireworkColor());
                continue;
            }
            if (!INGREDIENT_FIREWORK_STAR.test(itemStack2)) continue;
            itemStack = itemStack2.copy();
            itemStack.setCount(1);
        }
        if (itemStack != null && !arrayList.isEmpty()) {
            itemStack.getOrCreateChildTag("Explosion").putIntArray("FadeColors", arrayList);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR_FADE;
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

