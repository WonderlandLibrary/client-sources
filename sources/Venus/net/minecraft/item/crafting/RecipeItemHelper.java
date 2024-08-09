/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;

public class RecipeItemHelper {
    public final Int2IntMap itemToCount = new Int2IntOpenHashMap();

    public void accountPlainStack(ItemStack itemStack) {
        if (!(itemStack.isDamaged() || itemStack.isEnchanted() || itemStack.hasDisplayName())) {
            this.accountStack(itemStack);
        }
    }

    public void accountStack(ItemStack itemStack) {
        this.func_221264_a(itemStack, 64);
    }

    public void func_221264_a(ItemStack itemStack, int n) {
        if (!itemStack.isEmpty()) {
            int n2 = RecipeItemHelper.pack(itemStack);
            int n3 = Math.min(n, itemStack.getCount());
            this.increment(n2, n3);
        }
    }

    public static int pack(ItemStack itemStack) {
        return Registry.ITEM.getId(itemStack.getItem());
    }

    private boolean containsItem(int n) {
        return this.itemToCount.get(n) > 0;
    }

    private int tryTake(int n, int n2) {
        int n3 = this.itemToCount.get(n);
        if (n3 >= n2) {
            this.itemToCount.put(n, n3 - n2);
            return n;
        }
        return 1;
    }

    private void increment(int n, int n2) {
        this.itemToCount.put(n, this.itemToCount.get(n) + n2);
    }

    public boolean canCraft(IRecipe<?> iRecipe, @Nullable IntList intList) {
        return this.canCraft(iRecipe, intList, 0);
    }

    public boolean canCraft(IRecipe<?> iRecipe, @Nullable IntList intList, int n) {
        return new RecipePicker(this, iRecipe).tryPick(n, intList);
    }

    public int getBiggestCraftableStack(IRecipe<?> iRecipe, @Nullable IntList intList) {
        return this.getBiggestCraftableStack(iRecipe, Integer.MAX_VALUE, intList);
    }

    public int getBiggestCraftableStack(IRecipe<?> iRecipe, int n, @Nullable IntList intList) {
        return new RecipePicker(this, iRecipe).tryPickAll(n, intList);
    }

    public static ItemStack unpack(int n) {
        return n == 0 ? ItemStack.EMPTY : new ItemStack(Item.getItemById(n));
    }

    public void clear() {
        this.itemToCount.clear();
    }

    class RecipePicker {
        private final IRecipe<?> recipe;
        private final List<Ingredient> ingredients;
        private final int ingredientCount;
        private final int[] possessedIngredientStacks;
        private final int possessedIngredientStackCount;
        private final BitSet data;
        private final IntList path;
        final RecipeItemHelper this$0;

        public RecipePicker(RecipeItemHelper recipeItemHelper, IRecipe<?> iRecipe) {
            this.this$0 = recipeItemHelper;
            this.ingredients = Lists.newArrayList();
            this.path = new IntArrayList();
            this.recipe = iRecipe;
            this.ingredients.addAll(iRecipe.getIngredients());
            this.ingredients.removeIf(Ingredient::hasNoMatchingItems);
            this.ingredientCount = this.ingredients.size();
            this.possessedIngredientStacks = this.getUniqueAvailIngredientItems();
            this.possessedIngredientStackCount = this.possessedIngredientStacks.length;
            this.data = new BitSet(this.ingredientCount + this.possessedIngredientStackCount + this.ingredientCount + this.ingredientCount * this.possessedIngredientStackCount);
            for (int i = 0; i < this.ingredients.size(); ++i) {
                IntList intList = this.ingredients.get(i).getValidItemStacksPacked();
                for (int j = 0; j < this.possessedIngredientStackCount; ++j) {
                    if (!intList.contains(this.possessedIngredientStacks[j])) continue;
                    this.data.set(this.getIndex(true, j, i));
                }
            }
        }

        public boolean tryPick(int n, @Nullable IntList intList) {
            int n2;
            int n3;
            if (n <= 0) {
                return false;
            }
            int n4 = 0;
            while (this.dfs(n)) {
                this.this$0.tryTake(this.possessedIngredientStacks[this.path.getInt(0)], n);
                n3 = this.path.size() - 1;
                this.setSatisfied(this.path.getInt(n3));
                for (n2 = 0; n2 < n3; ++n2) {
                    this.toggleResidual((n2 & 1) == 0, this.path.get(n2), this.path.get(n2 + 1));
                }
                this.path.clear();
                this.data.clear(0, this.ingredientCount + this.possessedIngredientStackCount);
                ++n4;
            }
            n3 = n4 == this.ingredientCount ? 1 : 0;
            int n5 = n2 = n3 != 0 && intList != null ? 1 : 0;
            if (n2 != 0) {
                intList.clear();
            }
            this.data.clear(0, this.ingredientCount + this.possessedIngredientStackCount + this.ingredientCount);
            int n6 = 0;
            NonNullList<Ingredient> nonNullList = this.recipe.getIngredients();
            for (int i = 0; i < nonNullList.size(); ++i) {
                if (n2 != 0 && ((Ingredient)nonNullList.get(i)).hasNoMatchingItems()) {
                    intList.add(0);
                    continue;
                }
                for (int j = 0; j < this.possessedIngredientStackCount; ++j) {
                    if (!this.hasResidual(false, n6, j)) continue;
                    this.toggleResidual(true, j, n6);
                    this.this$0.increment(this.possessedIngredientStacks[j], n);
                    if (n2 == 0) continue;
                    intList.add(this.possessedIngredientStacks[j]);
                }
                ++n6;
            }
            return n3 != 0;
        }

        private int[] getUniqueAvailIngredientItems() {
            IntAVLTreeSet intAVLTreeSet = new IntAVLTreeSet();
            for (Ingredient ingredient : this.ingredients) {
                intAVLTreeSet.addAll(ingredient.getValidItemStacksPacked());
            }
            IntIterator intIterator = intAVLTreeSet.iterator();
            while (intIterator.hasNext()) {
                if (this.this$0.containsItem(intIterator.nextInt())) continue;
                intIterator.remove();
            }
            return intAVLTreeSet.toIntArray();
        }

        private boolean dfs(int n) {
            int n2 = this.possessedIngredientStackCount;
            for (int i = 0; i < n2; ++i) {
                if (this.this$0.itemToCount.get(this.possessedIngredientStacks[i]) < n) continue;
                this.visit(false, i);
                while (!this.path.isEmpty()) {
                    int n3;
                    int n4 = this.path.size();
                    boolean bl = (n4 & 1) == 1;
                    int n5 = this.path.getInt(n4 - 1);
                    if (!bl && !this.isSatisfied(n5)) break;
                    int n6 = bl ? this.ingredientCount : n2;
                    for (n3 = 0; n3 < n6; ++n3) {
                        if (this.hasVisited(bl, n3) || !this.hasConnection(bl, n5, n3) || !this.hasResidual(bl, n5, n3)) continue;
                        this.visit(bl, n3);
                        break;
                    }
                    if ((n3 = this.path.size()) != n4) continue;
                    this.path.removeInt(n3 - 1);
                }
                if (this.path.isEmpty()) continue;
                return false;
            }
            return true;
        }

        private boolean isSatisfied(int n) {
            return this.data.get(this.getSatisfiedIndex(n));
        }

        private void setSatisfied(int n) {
            this.data.set(this.getSatisfiedIndex(n));
        }

        private int getSatisfiedIndex(int n) {
            return this.ingredientCount + this.possessedIngredientStackCount + n;
        }

        private boolean hasConnection(boolean bl, int n, int n2) {
            return this.data.get(this.getIndex(bl, n, n2));
        }

        private boolean hasResidual(boolean bl, int n, int n2) {
            return bl != this.data.get(1 + this.getIndex(bl, n, n2));
        }

        private void toggleResidual(boolean bl, int n, int n2) {
            this.data.flip(1 + this.getIndex(bl, n, n2));
        }

        private int getIndex(boolean bl, int n, int n2) {
            int n3 = bl ? n * this.ingredientCount + n2 : n2 * this.ingredientCount + n;
            return this.ingredientCount + this.possessedIngredientStackCount + this.ingredientCount + 2 * n3;
        }

        private void visit(boolean bl, int n) {
            this.data.set(this.getVisitedIndex(bl, n));
            this.path.add(n);
        }

        private boolean hasVisited(boolean bl, int n) {
            return this.data.get(this.getVisitedIndex(bl, n));
        }

        private int getVisitedIndex(boolean bl, int n) {
            return (bl ? 0 : this.ingredientCount) + n;
        }

        public int tryPickAll(int n, @Nullable IntList intList) {
            int n2 = 0;
            int n3 = Math.min(n, this.getMinIngredientCount()) + 1;
            while (true) {
                int n4;
                if (this.tryPick(n4 = (n2 + n3) / 2, null)) {
                    if (n3 - n2 <= 1) {
                        if (n4 > 0) {
                            this.tryPick(n4, intList);
                        }
                        return n4;
                    }
                    n2 = n4;
                    continue;
                }
                n3 = n4;
            }
        }

        private int getMinIngredientCount() {
            int n = Integer.MAX_VALUE;
            for (Ingredient ingredient : this.ingredients) {
                int n2 = 0;
                IntListIterator intListIterator = ingredient.getValidItemStacksPacked().iterator();
                while (intListIterator.hasNext()) {
                    int n3 = (Integer)intListIterator.next();
                    n2 = Math.max(n2, this.this$0.itemToCount.get(n3));
                }
                if (n <= 0) continue;
                n = Math.min(n, n2);
            }
            return n;
        }
    }
}

