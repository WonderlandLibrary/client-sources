/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public abstract class AbstractRecipeBookGui
extends RecipeBookGui {
    private Iterator<Item> field_212964_i;
    private Set<Item> field_212965_j;
    private Slot field_212966_k;
    private Item field_212967_l;
    private float field_212968_m;

    @Override
    protected void func_205702_a() {
        this.toggleRecipesBtn.initTextureValues(152, 182, 28, 18, RECIPE_BOOK);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.slotNumber < this.field_201522_g.getSize()) {
            this.field_212966_k = null;
        }
    }

    @Override
    public void setupGhostRecipe(IRecipe<?> iRecipe, List<Slot> list) {
        ItemStack itemStack = iRecipe.getRecipeOutput();
        this.ghostRecipe.setRecipe(iRecipe);
        this.ghostRecipe.addIngredient(Ingredient.fromStacks(itemStack), list.get((int)2).xPos, list.get((int)2).yPos);
        NonNullList<Ingredient> nonNullList = iRecipe.getIngredients();
        this.field_212966_k = list.get(1);
        if (this.field_212965_j == null) {
            this.field_212965_j = this.func_212958_h();
        }
        this.field_212964_i = this.field_212965_j.iterator();
        this.field_212967_l = null;
        Iterator iterator2 = nonNullList.iterator();
        for (int i = 0; i < 2; ++i) {
            if (!iterator2.hasNext()) {
                return;
            }
            Ingredient ingredient = (Ingredient)iterator2.next();
            if (ingredient.hasNoMatchingItems()) continue;
            Slot slot = list.get(i);
            this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
        }
    }

    protected abstract Set<Item> func_212958_h();

    @Override
    public void func_230477_a_(MatrixStack matrixStack, int n, int n2, boolean bl, float f) {
        super.func_230477_a_(matrixStack, n, n2, bl, f);
        if (this.field_212966_k != null) {
            if (!Screen.hasControlDown()) {
                this.field_212968_m += f;
            }
            int n3 = this.field_212966_k.xPos + n;
            int n4 = this.field_212966_k.yPos + n2;
            AbstractGui.fill(matrixStack, n3, n4, n3 + 16, n4 + 16, 0x30FF0000);
            this.mc.getItemRenderer().renderItemAndEffectIntoGUI(this.mc.player, this.func_212961_n().getDefaultInstance(), n3, n4);
            RenderSystem.depthFunc(516);
            AbstractGui.fill(matrixStack, n3, n4, n3 + 16, n4 + 16, 0x30FFFFFF);
            RenderSystem.depthFunc(515);
        }
    }

    private Item func_212961_n() {
        if (this.field_212967_l == null || this.field_212968_m > 30.0f) {
            this.field_212968_m = 0.0f;
            if (this.field_212964_i == null || !this.field_212964_i.hasNext()) {
                if (this.field_212965_j == null) {
                    this.field_212965_j = this.func_212958_h();
                }
                this.field_212964_i = this.field_212965_j.iterator();
            }
            this.field_212967_l = this.field_212964_i.next();
        }
        return this.field_212967_l;
    }
}

