/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.recipebook.RecipeOverlayGui;
import net.minecraft.client.gui.recipebook.RecipeWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;

public class RecipeBookPage {
    private final List<RecipeWidget> buttons = Lists.newArrayListWithCapacity(20);
    private RecipeWidget hoveredButton;
    private final RecipeOverlayGui overlay = new RecipeOverlayGui();
    private Minecraft minecraft;
    private final List<IRecipeUpdateListener> listeners = Lists.newArrayList();
    private List<RecipeList> recipeLists;
    private ToggleWidget forwardButton;
    private ToggleWidget backButton;
    private int totalPages;
    private int currentPage;
    private RecipeBook recipeBook;
    private IRecipe<?> lastClickedRecipe;
    private RecipeList lastClickedRecipeList;

    public RecipeBookPage() {
        for (int i = 0; i < 20; ++i) {
            this.buttons.add(new RecipeWidget());
        }
    }

    public void init(Minecraft minecraft, int n, int n2) {
        this.minecraft = minecraft;
        this.recipeBook = minecraft.player.getRecipeBook();
        for (int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).setPosition(n + 11 + 25 * (i % 5), n2 + 31 + 25 * (i / 5));
        }
        this.forwardButton = new ToggleWidget(n + 93, n2 + 137, 12, 17, false);
        this.forwardButton.initTextureValues(1, 208, 13, 18, RecipeBookGui.RECIPE_BOOK);
        this.backButton = new ToggleWidget(n + 38, n2 + 137, 12, 17, true);
        this.backButton.initTextureValues(1, 208, 13, 18, RecipeBookGui.RECIPE_BOOK);
    }

    public void addListener(RecipeBookGui recipeBookGui) {
        this.listeners.remove(recipeBookGui);
        this.listeners.add(recipeBookGui);
    }

    public void updateLists(List<RecipeList> list, boolean bl) {
        this.recipeLists = list;
        this.totalPages = (int)Math.ceil((double)list.size() / 20.0);
        if (this.totalPages <= this.currentPage || bl) {
            this.currentPage = 0;
        }
        this.updateButtonsForPage();
    }

    private void updateButtonsForPage() {
        int n = 20 * this.currentPage;
        for (int i = 0; i < this.buttons.size(); ++i) {
            RecipeWidget recipeWidget = this.buttons.get(i);
            if (n + i < this.recipeLists.size()) {
                RecipeList recipeList = this.recipeLists.get(n + i);
                recipeWidget.func_203400_a(recipeList, this);
                recipeWidget.visible = true;
                continue;
            }
            recipeWidget.visible = false;
        }
        this.updateArrowButtons();
    }

    private void updateArrowButtons() {
        this.forwardButton.visible = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
        this.backButton.visible = this.totalPages > 1 && this.currentPage > 0;
    }

    public void func_238927_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f) {
        if (this.totalPages > 1) {
            String string = this.currentPage + 1 + "/" + this.totalPages;
            int n5 = this.minecraft.fontRenderer.getStringWidth(string);
            this.minecraft.fontRenderer.drawString(matrixStack, string, n - n5 / 2 + 73, n2 + 141, -1);
        }
        this.hoveredButton = null;
        for (RecipeWidget recipeWidget : this.buttons) {
            recipeWidget.render(matrixStack, n3, n4, f);
            if (!recipeWidget.visible || !recipeWidget.isHovered()) continue;
            this.hoveredButton = recipeWidget;
        }
        this.backButton.render(matrixStack, n3, n4, f);
        this.forwardButton.render(matrixStack, n3, n4, f);
        this.overlay.render(matrixStack, n3, n4, f);
    }

    public void func_238926_a_(MatrixStack matrixStack, int n, int n2) {
        if (this.minecraft.currentScreen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
            this.minecraft.currentScreen.func_243308_b(matrixStack, this.hoveredButton.getToolTipText(this.minecraft.currentScreen), n, n2);
        }
    }

    @Nullable
    public IRecipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    @Nullable
    public RecipeList getLastClickedRecipeList() {
        return this.lastClickedRecipeList;
    }

    public void setInvisible() {
        this.overlay.setVisible(true);
    }

    public boolean func_198955_a(double d, double d2, int n, int n2, int n3, int n4, int n5) {
        this.lastClickedRecipe = null;
        this.lastClickedRecipeList = null;
        if (this.overlay.isVisible()) {
            if (this.overlay.mouseClicked(d, d2, n)) {
                this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
                this.lastClickedRecipeList = this.overlay.getRecipeList();
            } else {
                this.overlay.setVisible(true);
            }
            return false;
        }
        if (this.forwardButton.mouseClicked(d, d2, n)) {
            ++this.currentPage;
            this.updateButtonsForPage();
            return false;
        }
        if (this.backButton.mouseClicked(d, d2, n)) {
            --this.currentPage;
            this.updateButtonsForPage();
            return false;
        }
        for (RecipeWidget recipeWidget : this.buttons) {
            if (!recipeWidget.mouseClicked(d, d2, n)) continue;
            if (n == 0) {
                this.lastClickedRecipe = recipeWidget.getRecipe();
                this.lastClickedRecipeList = recipeWidget.getList();
            } else if (n == 1 && !this.overlay.isVisible() && !recipeWidget.isOnlyOption()) {
                this.overlay.func_201703_a(this.minecraft, recipeWidget.getList(), recipeWidget.x, recipeWidget.y, n2 + n4 / 2, n3 + 13 + n5 / 2, recipeWidget.getWidth());
            }
            return false;
        }
        return true;
    }

    public void recipesShown(List<IRecipe<?>> list) {
        for (IRecipeUpdateListener iRecipeUpdateListener : this.listeners) {
            iRecipeUpdateListener.recipesShown(list);
        }
    }

    public Minecraft func_203411_d() {
        return this.minecraft;
    }

    public RecipeBook func_203412_e() {
        return this.recipeBook;
    }
}

