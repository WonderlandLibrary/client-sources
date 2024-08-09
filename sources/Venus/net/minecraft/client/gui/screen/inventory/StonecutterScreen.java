/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.StonecutterContainer;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class StonecutterScreen
extends ContainerScreen<StonecutterContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
    private float sliderProgress;
    private boolean clickedOnSroll;
    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;

    public StonecutterScreen(StonecutterContainer stonecutterContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(stonecutterContainer, playerInventory, iTextComponent);
        stonecutterContainer.setInventoryUpdateListener(this::onInventoryUpdate);
        --this.titleY;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int n3 = this.guiLeft;
        int n4 = this.guiTop;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        int n5 = (int)(41.0f * this.sliderProgress);
        this.blit(matrixStack, n3 + 119, n4 + 15 + n5, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int n6 = this.guiLeft + 52;
        int n7 = this.guiTop + 14;
        int n8 = this.recipeIndexOffset + 12;
        this.func_238853_b_(matrixStack, n, n2, n6, n7, n8);
        this.drawRecipesItems(n6, n7, n8);
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int n, int n2) {
        super.renderHoveredTooltip(matrixStack, n, n2);
        if (this.hasItemsInInputSlot) {
            int n3 = this.guiLeft + 52;
            int n4 = this.guiTop + 14;
            int n5 = this.recipeIndexOffset + 12;
            List<StonecuttingRecipe> list = ((StonecutterContainer)this.container).getRecipeList();
            for (int i = this.recipeIndexOffset; i < n5 && i < ((StonecutterContainer)this.container).getRecipeListSize(); ++i) {
                int n6 = i - this.recipeIndexOffset;
                int n7 = n3 + n6 % 4 * 16;
                int n8 = n4 + n6 / 4 * 18 + 2;
                if (n < n7 || n >= n7 + 16 || n2 < n8 || n2 >= n8 + 18) continue;
                this.renderTooltip(matrixStack, list.get(i).getRecipeOutput(), n, n2);
            }
        }
    }

    private void func_238853_b_(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        for (int i = this.recipeIndexOffset; i < n5 && i < ((StonecutterContainer)this.container).getRecipeListSize(); ++i) {
            int n6 = i - this.recipeIndexOffset;
            int n7 = n3 + n6 % 4 * 16;
            int n8 = n6 / 4;
            int n9 = n4 + n8 * 18 + 2;
            int n10 = this.ySize;
            if (i == ((StonecutterContainer)this.container).getSelectedRecipe()) {
                n10 += 18;
            } else if (n >= n7 && n2 >= n9 && n < n7 + 16 && n2 < n9 + 18) {
                n10 += 36;
            }
            this.blit(matrixStack, n7, n9 - 1, 0, n10, 16, 18);
        }
    }

    private void drawRecipesItems(int n, int n2, int n3) {
        List<StonecuttingRecipe> list = ((StonecutterContainer)this.container).getRecipeList();
        for (int i = this.recipeIndexOffset; i < n3 && i < ((StonecutterContainer)this.container).getRecipeListSize(); ++i) {
            int n4 = i - this.recipeIndexOffset;
            int n5 = n + n4 % 4 * 16;
            int n6 = n4 / 4;
            int n7 = n2 + n6 * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), n5, n7);
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.clickedOnSroll = false;
        if (this.hasItemsInInputSlot) {
            int n2 = this.guiLeft + 52;
            int n3 = this.guiTop + 14;
            int n4 = this.recipeIndexOffset + 12;
            for (int i = this.recipeIndexOffset; i < n4; ++i) {
                int n5 = i - this.recipeIndexOffset;
                double d3 = d - (double)(n2 + n5 % 4 * 16);
                double d4 = d2 - (double)(n3 + n5 / 4 * 18);
                if (!(d3 >= 0.0) || !(d4 >= 0.0) || !(d3 < 16.0) || !(d4 < 18.0) || !((StonecutterContainer)this.container).enchantItem(this.minecraft.player, i)) continue;
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                this.minecraft.playerController.sendEnchantPacket(((StonecutterContainer)this.container).windowId, i);
                return false;
            }
            n2 = this.guiLeft + 119;
            n3 = this.guiTop + 9;
            if (d >= (double)n2 && d < (double)(n2 + 12) && d2 >= (double)n3 && d2 < (double)(n3 + 54)) {
                this.clickedOnSroll = true;
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.clickedOnSroll && this.canScroll()) {
            int n2 = this.guiTop + 14;
            int n3 = n2 + 54;
            this.sliderProgress = ((float)d2 - (float)n2 - 7.5f) / ((float)(n3 - n2) - 15.0f);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0f, 1.0f);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5) * 4;
            return false;
        }
        return super.mouseDragged(d, d2, n, d3, d4);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (this.canScroll()) {
            int n = this.getHiddenRows();
            this.sliderProgress = (float)((double)this.sliderProgress - d3 / (double)n);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0f, 1.0f);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)n) + 0.5) * 4;
        }
        return false;
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && ((StonecutterContainer)this.container).getRecipeListSize() > 12;
    }

    protected int getHiddenRows() {
        return (((StonecutterContainer)this.container).getRecipeListSize() + 4 - 1) / 4 - 3;
    }

    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = ((StonecutterContainer)this.container).hasItemsinInputSlot();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0f;
            this.recipeIndexOffset = 0;
        }
    }
}

