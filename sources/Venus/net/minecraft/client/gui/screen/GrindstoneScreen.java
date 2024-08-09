/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.GrindstoneContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GrindstoneScreen
extends ContainerScreen<GrindstoneContainer> {
    private static final ResourceLocation GRINDSTONE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/grindstone.png");

    public GrindstoneScreen(GrindstoneContainer grindstoneContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(grindstoneContainer, playerInventory, iTextComponent);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.drawGuiContainerBackgroundLayer(matrixStack, f, n, n2);
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GRINDSTONE_GUI_TEXTURES);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        if ((((GrindstoneContainer)this.container).getSlot(0).getHasStack() || ((GrindstoneContainer)this.container).getSlot(1).getHasStack()) && !((GrindstoneContainer)this.container).getSlot(2).getHasStack()) {
            this.blit(matrixStack, n3 + 92, n4 + 31, this.xSize, 0, 28, 21);
        }
    }
}

