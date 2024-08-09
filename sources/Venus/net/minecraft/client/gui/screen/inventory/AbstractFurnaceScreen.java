/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractFurnaceScreen<T extends AbstractFurnaceContainer>
extends ContainerScreen<T>
implements IRecipeShownListener {
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    public final AbstractRecipeBookGui recipeGui;
    private boolean widthTooNarrowIn;
    private final ResourceLocation guiTexture;

    public AbstractFurnaceScreen(T t, AbstractRecipeBookGui abstractRecipeBookGui, PlayerInventory playerInventory, ITextComponent iTextComponent, ResourceLocation resourceLocation) {
        super(t, playerInventory, iTextComponent);
        this.recipeGui = abstractRecipeBookGui;
        this.guiTexture = resourceLocation;
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrowIn = this.width < 379;
        this.recipeGui.init(this.width, this.height, this.minecraft, this.widthTooNarrowIn, (RecipeBookContainer)this.container);
        this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrowIn, this.width, this.xSize);
        this.addButton(new ImageButton(this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, BUTTON_TEXTURE, this::lambda$init$0));
        this.titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
    }

    @Override
    public void tick() {
        super.tick();
        this.recipeGui.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        if (this.recipeGui.isVisible() && this.widthTooNarrowIn) {
            this.drawGuiContainerBackgroundLayer(matrixStack, f, n, n2);
            this.recipeGui.render(matrixStack, n, n2, f);
        } else {
            this.recipeGui.render(matrixStack, n, n2, f);
            super.render(matrixStack, n, n2, f);
            this.recipeGui.func_230477_a_(matrixStack, this.guiLeft, this.guiTop, true, f);
        }
        this.renderHoveredTooltip(matrixStack, n, n2);
        this.recipeGui.func_238924_c_(matrixStack, this.guiLeft, this.guiTop, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        int n3;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(this.guiTexture);
        int n4 = this.guiLeft;
        int n5 = this.guiTop;
        this.blit(matrixStack, n4, n5, 0, 0, this.xSize, this.ySize);
        if (((AbstractFurnaceContainer)this.container).isBurning()) {
            n3 = ((AbstractFurnaceContainer)this.container).getBurnLeftScaled();
            this.blit(matrixStack, n4 + 56, n5 + 36 + 12 - n3, 176, 12 - n3, 14, n3 + 1);
        }
        n3 = ((AbstractFurnaceContainer)this.container).getCookProgressionScaled();
        this.blit(matrixStack, n4 + 79, n5 + 34, 176, 14, n3 + 1, 16);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.recipeGui.mouseClicked(d, d2, n)) {
            return false;
        }
        return this.widthTooNarrowIn && this.recipeGui.isVisible() ? true : super.mouseClicked(d, d2, n);
    }

    @Override
    protected void handleMouseClick(Slot slot, int n, int n2, ClickType clickType) {
        super.handleMouseClick(slot, n, n2, clickType);
        this.recipeGui.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        return this.recipeGui.keyPressed(n, n2, n3) ? false : super.keyPressed(n, n2, n3);
    }

    @Override
    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
        return this.recipeGui.func_195604_a(d, d2, this.guiLeft, this.guiTop, this.xSize, this.ySize, n3) && bl;
    }

    @Override
    public boolean charTyped(char c, int n) {
        return this.recipeGui.charTyped(c, n) ? true : super.charTyped(c, n);
    }

    @Override
    public void recipesUpdated() {
        this.recipeGui.recipesUpdated();
    }

    @Override
    public RecipeBookGui getRecipeGui() {
        return this.recipeGui;
    }

    @Override
    public void onClose() {
        this.recipeGui.removed();
        super.onClose();
    }

    private void lambda$init$0(Button button) {
        this.recipeGui.initSearchBar(this.widthTooNarrowIn);
        this.recipeGui.toggleVisibility();
        this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrowIn, this.width, this.xSize);
        ((ImageButton)button).setPosition(this.guiLeft + 20, this.height / 2 - 49);
    }
}

