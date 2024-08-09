/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.item.crafting.RecipeItemHelper;

public class RecipeList {
    private final List<IRecipe<?>> recipes;
    private final boolean singleResultItem;
    private final Set<IRecipe<?>> craftable = Sets.newHashSet();
    private final Set<IRecipe<?>> canFit = Sets.newHashSet();
    private final Set<IRecipe<?>> inBook = Sets.newHashSet();

    public RecipeList(List<IRecipe<?>> list) {
        this.recipes = ImmutableList.copyOf(list);
        this.singleResultItem = list.size() <= 1 ? true : RecipeList.func_243413_a(list);
    }

    private static boolean func_243413_a(List<IRecipe<?>> list) {
        int n = list.size();
        ItemStack itemStack = list.get(0).getRecipeOutput();
        for (int i = 1; i < n; ++i) {
            ItemStack itemStack2 = list.get(i).getRecipeOutput();
            if (ItemStack.areItemsEqual(itemStack, itemStack2) && ItemStack.areItemStackTagsEqual(itemStack, itemStack2)) continue;
            return true;
        }
        return false;
    }

    public boolean isNotEmpty() {
        return !this.inBook.isEmpty();
    }

    public void updateKnownRecipes(RecipeBook recipeBook) {
        for (IRecipe<?> iRecipe : this.recipes) {
            if (!recipeBook.isUnlocked(iRecipe)) continue;
            this.inBook.add(iRecipe);
        }
    }

    public void canCraft(RecipeItemHelper recipeItemHelper, int n, int n2, RecipeBook recipeBook) {
        for (IRecipe<?> iRecipe : this.recipes) {
            boolean bl;
            boolean bl2 = bl = iRecipe.canFit(n, n2) && recipeBook.isUnlocked(iRecipe);
            if (bl) {
                this.canFit.add(iRecipe);
            } else {
                this.canFit.remove(iRecipe);
            }
            if (bl && recipeItemHelper.canCraft(iRecipe, null)) {
                this.craftable.add(iRecipe);
                continue;
            }
            this.craftable.remove(iRecipe);
        }
    }

    public boolean isCraftable(IRecipe<?> iRecipe) {
        return this.craftable.contains(iRecipe);
    }

    public boolean containsCraftableRecipes() {
        return !this.craftable.isEmpty();
    }

    public boolean containsValidRecipes() {
        return !this.canFit.isEmpty();
    }

    public List<IRecipe<?>> getRecipes() {
        return this.recipes;
    }

    public List<IRecipe<?>> getRecipes(boolean bl) {
        ArrayList<IRecipe<?>> arrayList = Lists.newArrayList();
        Set<IRecipe<?>> set = bl ? this.craftable : this.canFit;
        for (IRecipe<?> iRecipe : this.recipes) {
            if (!set.contains(iRecipe)) continue;
            arrayList.add(iRecipe);
        }
        return arrayList;
    }

    public List<IRecipe<?>> getDisplayRecipes(boolean bl) {
        ArrayList<IRecipe<?>> arrayList = Lists.newArrayList();
        for (IRecipe<?> iRecipe : this.recipes) {
            if (!this.canFit.contains(iRecipe) || this.craftable.contains(iRecipe) != bl) continue;
            arrayList.add(iRecipe);
        }
        return arrayList;
    }

    public boolean hasSingleResultItem() {
        return this.singleResultItem;
    }
}

