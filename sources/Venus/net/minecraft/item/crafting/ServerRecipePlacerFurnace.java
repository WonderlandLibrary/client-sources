/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacer;

public class ServerRecipePlacerFurnace<C extends IInventory>
extends ServerRecipePlacer<C> {
    private boolean matches;

    public ServerRecipePlacerFurnace(RecipeBookContainer<C> recipeBookContainer) {
        super(recipeBookContainer);
    }

    @Override
    protected void tryPlaceRecipe(IRecipe<C> iRecipe, boolean bl) {
        ItemStack itemStack;
        this.matches = this.recipeBookContainer.matches(iRecipe);
        int n = this.recipeItemHelper.getBiggestCraftableStack(iRecipe, null);
        if (this.matches && ((itemStack = this.recipeBookContainer.getSlot(0).getStack()).isEmpty() || n <= itemStack.getCount())) {
            return;
        }
        IntArrayList intArrayList = new IntArrayList();
        int n2 = this.getMaxAmount(bl, n, this.matches);
        if (this.recipeItemHelper.canCraft(iRecipe, intArrayList, n2)) {
            if (!this.matches) {
                this.giveToPlayer(this.recipeBookContainer.getOutputSlot());
                this.giveToPlayer(0);
            }
            this.func_201516_a(n2, intArrayList);
        }
    }

    @Override
    protected void clear() {
        this.giveToPlayer(this.recipeBookContainer.getOutputSlot());
        super.clear();
    }

    protected void func_201516_a(int n, IntList intList) {
        IntListIterator intListIterator = intList.iterator();
        Slot slot = this.recipeBookContainer.getSlot(0);
        ItemStack itemStack = RecipeItemHelper.unpack((Integer)intListIterator.next());
        if (!itemStack.isEmpty()) {
            int n2 = Math.min(itemStack.getMaxStackSize(), n);
            if (this.matches) {
                n2 -= slot.getStack().getCount();
            }
            for (int i = 0; i < n2; ++i) {
                this.consumeIngredient(slot, itemStack);
            }
        }
    }
}

