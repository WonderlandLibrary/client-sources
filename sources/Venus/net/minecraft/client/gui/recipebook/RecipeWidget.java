/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeWidget
extends Widget {
    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private static final ITextComponent field_243412_b = new TranslationTextComponent("gui.recipebook.moreRecipes");
    private RecipeBookContainer<?> field_203401_p;
    private RecipeBook book;
    private RecipeList list;
    private float time;
    private float animationTime;
    private int currentIndex;

    public RecipeWidget() {
        super(0, 0, 25, 25, StringTextComponent.EMPTY);
    }

    public void func_203400_a(RecipeList recipeList, RecipeBookPage recipeBookPage) {
        this.list = recipeList;
        this.field_203401_p = (RecipeBookContainer)recipeBookPage.func_203411_d().player.openContainer;
        this.book = recipeBookPage.func_203412_e();
        List<IRecipe<?>> list = recipeList.getRecipes(this.book.func_242141_a(this.field_203401_p));
        for (IRecipe<?> iRecipe : list) {
            if (!this.book.isNew(iRecipe)) continue;
            recipeBookPage.recipesShown(list);
            this.animationTime = 15.0f;
            break;
        }
    }

    public RecipeList getList() {
        return this.list;
    }

    public void setPosition(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        boolean bl;
        if (!Screen.hasControlDown()) {
            this.time += f;
        }
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(RECIPE_BOOK);
        int n3 = 29;
        if (!this.list.containsCraftableRecipes()) {
            n3 += 25;
        }
        int n4 = 206;
        if (this.list.getRecipes(this.book.func_242141_a(this.field_203401_p)).size() > 1) {
            n4 += 25;
        }
        boolean bl2 = bl = this.animationTime > 0.0f;
        if (bl) {
            float f2 = 1.0f + 0.1f * (float)Math.sin(this.animationTime / 15.0f * (float)Math.PI);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(this.x + 8, this.y + 12, 0.0f);
            RenderSystem.scalef(f2, f2, 1.0f);
            RenderSystem.translatef(-(this.x + 8), -(this.y + 12), 0.0f);
            this.animationTime -= f;
        }
        this.blit(matrixStack, this.x, this.y, n3, n4, this.width, this.height);
        List<IRecipe<?>> list = this.getOrderedRecipes();
        this.currentIndex = MathHelper.floor(this.time / 30.0f) % list.size();
        ItemStack itemStack = list.get(this.currentIndex).getRecipeOutput();
        int n5 = 4;
        if (this.list.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
            minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, this.x + n5 + 1, this.y + n5 + 1);
            --n5;
        }
        minecraft.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(itemStack, this.x + n5, this.y + n5);
        if (bl) {
            RenderSystem.popMatrix();
        }
    }

    private List<IRecipe<?>> getOrderedRecipes() {
        List<IRecipe<?>> list = this.list.getDisplayRecipes(false);
        if (!this.book.func_242141_a(this.field_203401_p)) {
            list.addAll(this.list.getDisplayRecipes(true));
        }
        return list;
    }

    public boolean isOnlyOption() {
        return this.getOrderedRecipes().size() == 1;
    }

    public IRecipe<?> getRecipe() {
        List<IRecipe<?>> list = this.getOrderedRecipes();
        return list.get(this.currentIndex);
    }

    public List<ITextComponent> getToolTipText(Screen screen) {
        ItemStack itemStack = this.getOrderedRecipes().get(this.currentIndex).getRecipeOutput();
        ArrayList<ITextComponent> arrayList = Lists.newArrayList(screen.getTooltipFromItem(itemStack));
        if (this.list.getRecipes(this.book.func_242141_a(this.field_203401_p)).size() > 1) {
            arrayList.add(field_243412_b);
        }
        return arrayList;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    protected boolean isValidClickButton(int n) {
        return n == 0 || n == 1;
    }
}

