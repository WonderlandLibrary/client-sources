/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CraftingScreen
extends ContainerScreen<WorkbenchContainer>
implements IRecipeShownListener {
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    private final RecipeBookGui recipeBookGui = new RecipeBookGui();
    private boolean widthTooNarrow;

    public CraftingScreen(WorkbenchContainer workbenchContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(workbenchContainer, playerInventory, iTextComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookContainer)this.container);
        this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
        this.children.add(this.recipeBookGui);
        this.setFocusedDefault(this.recipeBookGui);
        this.addButton(new ImageButton(this.guiLeft + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, this::lambda$init$0));
        this.titleX = 29;
    }

    @Override
    public void tick() {
        super.tick();
        this.recipeBookGui.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
            this.drawGuiContainerBackgroundLayer(matrixStack, f, n, n2);
            this.recipeBookGui.render(matrixStack, n, n2, f);
        } else {
            this.recipeBookGui.render(matrixStack, n, n2, f);
            super.render(matrixStack, n, n2, f);
            this.recipeBookGui.func_230477_a_(matrixStack, this.guiLeft, this.guiTop, true, f);
        }
        this.renderHoveredTooltip(matrixStack, n, n2);
        this.recipeBookGui.func_238924_c_(matrixStack, this.guiLeft, this.guiTop, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
        int n3 = this.guiLeft;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected boolean isPointInRegion(int n, int n2, int n3, int n4, double d, double d2) {
        return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(n, n2, n3, n4, d, d2);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.recipeBookGui.mouseClicked(d, d2, n)) {
            this.setListener(this.recipeBookGui);
            return false;
        }
        return this.widthTooNarrow && this.recipeBookGui.isVisible() ? true : super.mouseClicked(d, d2, n);
    }

    @Override
    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
        return this.recipeBookGui.func_195604_a(d, d2, this.guiLeft, this.guiTop, this.xSize, this.ySize, n3) && bl;
    }

    @Override
    protected void handleMouseClick(Slot slot, int n, int n2, ClickType clickType) {
        super.handleMouseClick(slot, n, n2, clickType);
        this.recipeBookGui.slotClicked(slot);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookGui.recipesUpdated();
    }

    @Override
    public void onClose() {
        this.recipeBookGui.removed();
        super.onClose();
    }

    @Override
    public RecipeBookGui getRecipeGui() {
        return this.recipeBookGui;
    }

    private void lambda$init$0(Button button) {
        this.recipeBookGui.initSearchBar(this.widthTooNarrow);
        this.recipeBookGui.toggleVisibility();
        this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
        ((ImageButton)button).setPosition(this.guiLeft + 5, this.height / 2 - 49);
    }
}

