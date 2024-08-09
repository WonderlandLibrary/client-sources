/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeBookStatus;
import net.minecraft.util.ResourceLocation;

public class RecipeBook {
    protected final Set<ResourceLocation> recipes = Sets.newHashSet();
    protected final Set<ResourceLocation> newRecipes = Sets.newHashSet();
    private final RecipeBookStatus field_242138_c = new RecipeBookStatus();

    public void copyFrom(RecipeBook recipeBook) {
        this.recipes.clear();
        this.newRecipes.clear();
        this.field_242138_c.func_242150_a(recipeBook.field_242138_c);
        this.recipes.addAll(recipeBook.recipes);
        this.newRecipes.addAll(recipeBook.newRecipes);
    }

    public void unlock(IRecipe<?> iRecipe) {
        if (!iRecipe.isDynamic()) {
            this.unlock(iRecipe.getId());
        }
    }

    protected void unlock(ResourceLocation resourceLocation) {
        this.recipes.add(resourceLocation);
    }

    public boolean isUnlocked(@Nullable IRecipe<?> iRecipe) {
        return iRecipe == null ? false : this.recipes.contains(iRecipe.getId());
    }

    public boolean isUnlocked(ResourceLocation resourceLocation) {
        return this.recipes.contains(resourceLocation);
    }

    public void lock(IRecipe<?> iRecipe) {
        this.lock(iRecipe.getId());
    }

    protected void lock(ResourceLocation resourceLocation) {
        this.recipes.remove(resourceLocation);
        this.newRecipes.remove(resourceLocation);
    }

    public boolean isNew(IRecipe<?> iRecipe) {
        return this.newRecipes.contains(iRecipe.getId());
    }

    public void markSeen(IRecipe<?> iRecipe) {
        this.newRecipes.remove(iRecipe.getId());
    }

    public void markNew(IRecipe<?> iRecipe) {
        this.markNew(iRecipe.getId());
    }

    protected void markNew(ResourceLocation resourceLocation) {
        this.newRecipes.add(resourceLocation);
    }

    public boolean func_242142_a(RecipeBookCategory recipeBookCategory) {
        return this.field_242138_c.func_242151_a(recipeBookCategory);
    }

    public void func_242143_a(RecipeBookCategory recipeBookCategory, boolean bl) {
        this.field_242138_c.func_242152_a(recipeBookCategory, bl);
    }

    public boolean func_242141_a(RecipeBookContainer<?> recipeBookContainer) {
        return this.func_242145_b(recipeBookContainer.func_241850_m());
    }

    public boolean func_242145_b(RecipeBookCategory recipeBookCategory) {
        return this.field_242138_c.func_242158_b(recipeBookCategory);
    }

    public void func_242146_b(RecipeBookCategory recipeBookCategory, boolean bl) {
        this.field_242138_c.func_242159_b(recipeBookCategory, bl);
    }

    public void func_242140_a(RecipeBookStatus recipeBookStatus) {
        this.field_242138_c.func_242150_a(recipeBookStatus);
    }

    public RecipeBookStatus func_242139_a() {
        return this.field_242138_c.func_242149_a();
    }

    public void func_242144_a(RecipeBookCategory recipeBookCategory, boolean bl, boolean bl2) {
        this.field_242138_c.func_242152_a(recipeBookCategory, bl);
        this.field_242138_c.func_242159_b(recipeBookCategory, bl2);
    }
}

