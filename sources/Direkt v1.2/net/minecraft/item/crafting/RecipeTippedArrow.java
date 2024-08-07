package net.minecraft.item.crafting;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;

class RecipeTippedArrow implements IRecipe {
	private static final ItemStack[] EMPTY_ITEMS = new ItemStack[9];

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		if ((inv.getWidth() == 3) && (inv.getHeight() == 3)) {
			for (int i = 0; i < inv.getWidth(); ++i) {
				for (int j = 0; j < inv.getHeight(); ++j) {
					ItemStack itemstack = inv.getStackInRowAndColumn(i, j);

					if (itemstack == null) { return false; }

					Item item = itemstack.getItem();

					if ((i == 1) && (j == 1)) {
						if (item != Items.LINGERING_POTION) { return false; }
					} else if (item != Items.ARROW) { return false; }
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	@Nullable

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack itemstack = inv.getStackInRowAndColumn(1, 1);

		if ((itemstack != null) && (itemstack.getItem() == Items.LINGERING_POTION)) {
			ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
			PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
			PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
			return itemstack1;
		} else {
			return null;
		}
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	@Nullable
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return EMPTY_ITEMS;
	}
}
