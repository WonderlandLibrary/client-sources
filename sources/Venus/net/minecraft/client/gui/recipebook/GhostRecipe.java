/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;

public class GhostRecipe {
    private IRecipe<?> recipe;
    private final List<GhostIngredient> ingredients = Lists.newArrayList();
    private float time;

    public void clear() {
        this.recipe = null;
        this.ingredients.clear();
        this.time = 0.0f;
    }

    public void addIngredient(Ingredient ingredient, int n, int n2) {
        this.ingredients.add(new GhostIngredient(this, ingredient, n, n2));
    }

    public GhostIngredient get(int n) {
        return this.ingredients.get(n);
    }

    public int size() {
        return this.ingredients.size();
    }

    @Nullable
    public IRecipe<?> getRecipe() {
        return this.recipe;
    }

    public void setRecipe(IRecipe<?> iRecipe) {
        this.recipe = iRecipe;
    }

    public void func_238922_a_(MatrixStack matrixStack, Minecraft minecraft, int n, int n2, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            this.time += f;
        }
        for (int i = 0; i < this.ingredients.size(); ++i) {
            GhostIngredient ghostIngredient = this.ingredients.get(i);
            int n3 = ghostIngredient.getX() + n;
            int n4 = ghostIngredient.getY() + n2;
            if (i == 0 && bl) {
                AbstractGui.fill(matrixStack, n3 - 4, n4 - 4, n3 + 20, n4 + 20, 0x30FF0000);
            } else {
                AbstractGui.fill(matrixStack, n3, n4, n3 + 16, n4 + 16, 0x30FF0000);
            }
            ItemStack itemStack = ghostIngredient.getItem();
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemStack, n3, n4);
            RenderSystem.depthFunc(516);
            AbstractGui.fill(matrixStack, n3, n4, n3 + 16, n4 + 16, 0x30FFFFFF);
            RenderSystem.depthFunc(515);
            if (i != 0) continue;
            itemRenderer.renderItemOverlays(minecraft.fontRenderer, itemStack, n3, n4);
        }
    }

    public class GhostIngredient {
        private final Ingredient ingredient;
        private final int x;
        private final int y;
        final GhostRecipe this$0;

        public GhostIngredient(GhostRecipe ghostRecipe, Ingredient ingredient, int n, int n2) {
            this.this$0 = ghostRecipe;
            this.ingredient = ingredient;
            this.x = n;
            this.y = n2;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public ItemStack getItem() {
            ItemStack[] itemStackArray = this.ingredient.getMatchingStacks();
            return itemStackArray[MathHelper.floor(this.this$0.time / 30.0f) % itemStackArray.length];
        }
    }
}

