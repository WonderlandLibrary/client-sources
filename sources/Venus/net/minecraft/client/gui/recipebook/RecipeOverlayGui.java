/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

public class RecipeOverlayGui
extends AbstractGui
implements IRenderable,
IGuiEventListener {
    private static final ResourceLocation RECIPE_BOOK_TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");
    private final List<RecipeButtonWidget> buttonList = Lists.newArrayList();
    private boolean visible;
    private int x;
    private int y;
    private Minecraft mc;
    private RecipeList recipeList;
    private IRecipe<?> lastRecipeClicked;
    private float time;
    private boolean field_201704_n;

    public void func_201703_a(Minecraft minecraft, RecipeList recipeList, int n, int n2, int n3, int n4, float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        this.mc = minecraft;
        this.recipeList = recipeList;
        if (minecraft.player.openContainer instanceof AbstractFurnaceContainer) {
            this.field_201704_n = true;
        }
        boolean bl = minecraft.player.getRecipeBook().func_242141_a((RecipeBookContainer)minecraft.player.openContainer);
        List<IRecipe<?>> list = recipeList.getDisplayRecipes(false);
        List list2 = bl ? Collections.emptyList() : recipeList.getDisplayRecipes(true);
        int n5 = list.size();
        int n6 = n5 + list2.size();
        int n7 = n6 <= 16 ? 4 : 5;
        int n8 = (int)Math.ceil((float)n6 / (float)n7);
        this.x = n;
        this.y = n2;
        int n9 = 25;
        float f7 = this.x + Math.min(n6, n7) * 25;
        if (f7 > (f6 = (float)(n3 + 50))) {
            this.x = (int)((float)this.x - f * (float)((int)((f7 - f6) / f)));
        }
        if ((f5 = (float)(this.y + n8 * 25)) > (f4 = (float)(n4 + 50))) {
            this.y = (int)((float)this.y - f * (float)MathHelper.ceil((f5 - f4) / f));
        }
        if ((f3 = (float)this.y) < (f2 = (float)(n4 - 100))) {
            this.y = (int)((float)this.y - f * (float)MathHelper.ceil((f3 - f2) / f));
        }
        this.visible = true;
        this.buttonList.clear();
        for (int i = 0; i < n6; ++i) {
            boolean bl2 = i < n5;
            IRecipe iRecipe = bl2 ? list.get(i) : (IRecipe)list2.get(i - n5);
            int n10 = this.x + 4 + 25 * (i % n7);
            int n11 = this.y + 5 + 25 * (i / n7);
            if (this.field_201704_n) {
                this.buttonList.add(new FurnaceRecipeButtonWidget(this, n10, n11, iRecipe, bl2));
                continue;
            }
            this.buttonList.add(new RecipeButtonWidget(this, n10, n11, iRecipe, bl2));
        }
        this.lastRecipeClicked = null;
    }

    @Override
    public boolean changeFocus(boolean bl) {
        return true;
    }

    public RecipeList getRecipeList() {
        return this.recipeList;
    }

    public IRecipe<?> getLastRecipeClicked() {
        return this.lastRecipeClicked;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (n != 0) {
            return true;
        }
        for (RecipeButtonWidget recipeButtonWidget : this.buttonList) {
            if (!recipeButtonWidget.mouseClicked(d, d2, n)) continue;
            this.lastRecipeClicked = recipeButtonWidget.recipe;
            return false;
        }
        return true;
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.visible) {
            this.time += f;
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(RECIPE_BOOK_TEXTURE);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 170.0f);
            int n3 = this.buttonList.size() <= 16 ? 4 : 5;
            int n4 = Math.min(this.buttonList.size(), n3);
            int n5 = MathHelper.ceil((float)this.buttonList.size() / (float)n3);
            int n6 = 24;
            int n7 = 4;
            int n8 = 82;
            int n9 = 208;
            this.func_238923_c_(matrixStack, n4, n5, 24, 4, 82, 208);
            RenderSystem.disableBlend();
            for (RecipeButtonWidget recipeButtonWidget : this.buttonList) {
                recipeButtonWidget.render(matrixStack, n, n2, f);
            }
            RenderSystem.popMatrix();
        }
    }

    private void func_238923_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6) {
        this.blit(matrixStack, this.x, this.y, n5, n6, n4, n4);
        this.blit(matrixStack, this.x + n4 * 2 + n * n3, this.y, n5 + n3 + n4, n6, n4, n4);
        this.blit(matrixStack, this.x, this.y + n4 * 2 + n2 * n3, n5, n6 + n3 + n4, n4, n4);
        this.blit(matrixStack, this.x + n4 * 2 + n * n3, this.y + n4 * 2 + n2 * n3, n5 + n3 + n4, n6 + n3 + n4, n4, n4);
        for (int i = 0; i < n; ++i) {
            this.blit(matrixStack, this.x + n4 + i * n3, this.y, n5 + n4, n6, n3, n4);
            this.blit(matrixStack, this.x + n4 + (i + 1) * n3, this.y, n5 + n4, n6, n4, n4);
            for (int j = 0; j < n2; ++j) {
                if (i == 0) {
                    this.blit(matrixStack, this.x, this.y + n4 + j * n3, n5, n6 + n4, n4, n3);
                    this.blit(matrixStack, this.x, this.y + n4 + (j + 1) * n3, n5, n6 + n4, n4, n4);
                }
                this.blit(matrixStack, this.x + n4 + i * n3, this.y + n4 + j * n3, n5 + n4, n6 + n4, n3, n3);
                this.blit(matrixStack, this.x + n4 + (i + 1) * n3, this.y + n4 + j * n3, n5 + n4, n6 + n4, n4, n3);
                this.blit(matrixStack, this.x + n4 + i * n3, this.y + n4 + (j + 1) * n3, n5 + n4, n6 + n4, n3, n4);
                this.blit(matrixStack, this.x + n4 + (i + 1) * n3 - 1, this.y + n4 + (j + 1) * n3 - 1, n5 + n4, n6 + n4, n4 + 1, n4 + 1);
                if (i != n - 1) continue;
                this.blit(matrixStack, this.x + n4 * 2 + n * n3, this.y + n4 + j * n3, n5 + n3 + n4, n6 + n4, n4, n3);
                this.blit(matrixStack, this.x + n4 * 2 + n * n3, this.y + n4 + (j + 1) * n3, n5 + n3 + n4, n6 + n4, n4, n4);
            }
            this.blit(matrixStack, this.x + n4 + i * n3, this.y + n4 * 2 + n2 * n3, n5 + n4, n6 + n3 + n4, n3, n4);
            this.blit(matrixStack, this.x + n4 + (i + 1) * n3, this.y + n4 * 2 + n2 * n3, n5 + n4, n6 + n3 + n4, n4, n4);
        }
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    public boolean isVisible() {
        return this.visible;
    }

    class FurnaceRecipeButtonWidget
    extends RecipeButtonWidget {
        final RecipeOverlayGui this$0;

        public FurnaceRecipeButtonWidget(RecipeOverlayGui recipeOverlayGui, int n, int n2, IRecipe<?> iRecipe, boolean bl) {
            this.this$0 = recipeOverlayGui;
            super(recipeOverlayGui, n, n2, iRecipe, bl);
        }

        @Override
        protected void func_201505_a(IRecipe<?> iRecipe) {
            ItemStack[] itemStackArray = iRecipe.getIngredients().get(0).getMatchingStacks();
            this.field_201506_o.add(new RecipeButtonWidget.Child(this, 10, 10, itemStackArray));
        }
    }

    class RecipeButtonWidget
    extends Widget
    implements IRecipePlacer<Ingredient> {
        private final IRecipe<?> recipe;
        private final boolean isCraftable;
        protected final List<Child> field_201506_o;
        final RecipeOverlayGui this$0;

        public RecipeButtonWidget(RecipeOverlayGui recipeOverlayGui, int n, int n2, IRecipe<?> iRecipe, boolean bl) {
            this.this$0 = recipeOverlayGui;
            super(n, n2, 200, 20, StringTextComponent.EMPTY);
            this.field_201506_o = Lists.newArrayList();
            this.width = 24;
            this.height = 24;
            this.recipe = iRecipe;
            this.isCraftable = bl;
            this.func_201505_a(iRecipe);
        }

        protected void func_201505_a(IRecipe<?> iRecipe) {
            this.placeRecipe(3, 3, -1, iRecipe, iRecipe.getIngredients().iterator(), 0);
        }

        @Override
        public void setSlotContents(Iterator<Ingredient> iterator2, int n, int n2, int n3, int n4) {
            ItemStack[] itemStackArray = iterator2.next().getMatchingStacks();
            if (itemStackArray.length != 0) {
                this.field_201506_o.add(new Child(this, 3 + n4 * 7, 3 + n3 * 7, itemStackArray));
            }
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            int n3;
            RenderSystem.enableAlphaTest();
            this.this$0.mc.getTextureManager().bindTexture(RECIPE_BOOK_TEXTURE);
            int n4 = 152;
            if (!this.isCraftable) {
                n4 += 26;
            }
            int n5 = n3 = this.this$0.field_201704_n ? 130 : 78;
            if (this.isHovered()) {
                n3 += 26;
            }
            this.blit(matrixStack, this.x, this.y, n4, n3, this.width, this.height);
            for (Child child : this.field_201506_o) {
                RenderSystem.pushMatrix();
                float f2 = 0.42f;
                int n6 = (int)((float)(this.x + child.field_201706_b) / 0.42f - 3.0f);
                int n7 = (int)((float)(this.y + child.field_201707_c) / 0.42f - 3.0f);
                RenderSystem.scalef(0.42f, 0.42f, 1.0f);
                this.this$0.mc.getItemRenderer().renderItemAndEffectIntoGUI(child.field_201705_a[MathHelper.floor(this.this$0.time / 30.0f) % child.field_201705_a.length], n6, n7);
                RenderSystem.popMatrix();
            }
            RenderSystem.disableAlphaTest();
        }

        public class Child {
            public final ItemStack[] field_201705_a;
            public final int field_201706_b;
            public final int field_201707_c;
            final RecipeButtonWidget this$1;

            public Child(RecipeButtonWidget recipeButtonWidget, int n, int n2, ItemStack[] itemStackArray) {
                this.this$1 = recipeButtonWidget;
                this.field_201706_b = n;
                this.field_201707_c = n2;
                this.field_201705_a = itemStackArray;
            }
        }
    }
}

