/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class RecipeTabToggleWidget
extends ToggleWidget {
    private final RecipeBookCategories category;
    private float animationTime;

    public RecipeTabToggleWidget(RecipeBookCategories recipeBookCategories) {
        super(0, 0, 35, 27, false);
        this.category = recipeBookCategories;
        this.initTextureValues(153, 2, 35, 0, RecipeBookGui.RECIPE_BOOK);
    }

    public void startAnimation(Minecraft minecraft) {
        ClientRecipeBook clientRecipeBook = minecraft.player.getRecipeBook();
        List<RecipeList> list = clientRecipeBook.getRecipes(this.category);
        if (minecraft.player.openContainer instanceof RecipeBookContainer) {
            for (RecipeList recipeList : list) {
                for (IRecipe<?> iRecipe : recipeList.getRecipes(clientRecipeBook.func_242141_a((RecipeBookContainer)minecraft.player.openContainer))) {
                    if (!clientRecipeBook.isNew(iRecipe)) continue;
                    this.animationTime = 15.0f;
                    return;
                }
            }
        }
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.animationTime > 0.0f) {
            float f2 = 1.0f + 0.1f * (float)Math.sin(this.animationTime / 15.0f * (float)Math.PI);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(this.x + 8, this.y + 12, 0.0f);
            RenderSystem.scalef(1.0f, f2, 1.0f);
            RenderSystem.translatef(-(this.x + 8), -(this.y + 12), 0.0f);
        }
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int n3 = this.xTexStart;
        int n4 = this.yTexStart;
        if (this.stateTriggered) {
            n3 += this.xDiffTex;
        }
        if (this.isHovered()) {
            n4 += this.yDiffTex;
        }
        int n5 = this.x;
        if (this.stateTriggered) {
            n5 -= 2;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.blit(matrixStack, n5, this.y, n3, n4, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcon(minecraft.getItemRenderer());
        if (this.animationTime > 0.0f) {
            RenderSystem.popMatrix();
            this.animationTime -= f;
        }
    }

    private void renderIcon(ItemRenderer itemRenderer) {
        int n;
        List<ItemStack> list = this.category.getIcons();
        int n2 = n = this.stateTriggered ? -2 : 0;
        if (list.size() == 1) {
            itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(list.get(0), this.x + 9 + n, this.y + 5);
        } else if (list.size() == 2) {
            itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(list.get(0), this.x + 3 + n, this.y + 5);
            itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(list.get(1), this.x + 14 + n, this.y + 5);
        }
    }

    public RecipeBookCategories func_201503_d() {
        return this.category;
    }

    public boolean func_199500_a(ClientRecipeBook clientRecipeBook) {
        List<RecipeList> list = clientRecipeBook.getRecipes(this.category);
        this.visible = false;
        if (list != null) {
            for (RecipeList recipeList : list) {
                if (!recipeList.isNotEmpty() || !recipeList.containsValidRecipes()) continue;
                this.visible = true;
                break;
            }
        }
        return this.visible;
    }
}

