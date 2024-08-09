/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import net.minecraft.client.gui.recipebook.RecipeBookGui;

public interface IRecipeShownListener {
    public void recipesUpdated();

    public RecipeBookGui getRecipeGui();
}

