package net.minecraft.client.gui.recipebook;

import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public interface IRecipeUpdateListener
{
    void recipesShown(List < IRecipe<? >> recipes);
}
