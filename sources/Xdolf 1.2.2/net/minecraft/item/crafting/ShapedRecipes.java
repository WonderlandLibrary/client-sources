package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShapedRecipes implements IRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private final int recipeWidth;

    /** How many vertical slots this recipe uses. */
    private final int recipeHeight;

    /** Is a array of ItemStack that composes the recipe. */
    private final ItemStack[] recipeItems;

    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;
    private boolean copyIngredientNBT;

    public ShapedRecipes(int width, int height, ItemStack[] p_i1917_3_, ItemStack output)
    {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = p_i1917_3_;

        for (int i = 0; i < this.recipeItems.length; ++i)
        {
            if (this.recipeItems[i] == null)
            {
                this.recipeItems[i] = ItemStack.field_190927_a;
            }
        }

        this.recipeOutput = output;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack.getItem().hasContainerItem())
            {
                nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
            }
        }

        return nonnulllist;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        for (int i = 0; i <= 3 - this.recipeWidth; ++i)
        {
            for (int j = 0; j <= 3 - this.recipeHeight; ++j)
            {
                if (this.checkMatch(inv, i, j, true))
                {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                int k = i - p_77573_2_;
                int l = j - p_77573_3_;
                ItemStack itemstack = ItemStack.field_190927_a;

                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
                {
                    if (p_77573_4_)
                    {
                        itemstack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
                    }
                    else
                    {
                        itemstack = this.recipeItems[k + l * this.recipeWidth];
                    }
                }

                ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(i, j);

                if (!itemstack1.func_190926_b() || !itemstack.func_190926_b())
                {
                    if (itemstack1.func_190926_b() != itemstack.func_190926_b())
                    {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem())
                    {
                        return false;
                    }

                    if (itemstack.getMetadata() != 32767 && itemstack.getMetadata() != itemstack1.getMetadata())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = this.getRecipeOutput().copy();

        if (this.copyIngredientNBT)
        {
            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack1 = inv.getStackInSlot(i);

                if (!itemstack1.func_190926_b() && itemstack1.hasTagCompound())
                {
                    itemstack.setTagCompound(itemstack1.getTagCompound().copy());
                }
            }
        }

        return itemstack;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }
}
