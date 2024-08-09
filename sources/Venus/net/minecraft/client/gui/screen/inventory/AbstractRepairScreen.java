/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AbstractRepairScreen<T extends AbstractRepairContainer>
extends ContainerScreen<T>
implements IContainerListener {
    private ResourceLocation guiTexture;

    public AbstractRepairScreen(T t, PlayerInventory playerInventory, ITextComponent iTextComponent, ResourceLocation resourceLocation) {
        super(t, playerInventory, iTextComponent);
        this.guiTexture = resourceLocation;
    }

    protected void initFields() {
    }

    @Override
    protected void init() {
        super.init();
        this.initFields();
        ((AbstractRepairContainer)this.container).addListener(this);
    }

    @Override
    public void onClose() {
        super.onClose();
        ((AbstractRepairContainer)this.container).removeListener(this);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        RenderSystem.disableBlend();
        this.renderNameField(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    protected void renderNameField(MatrixStack matrixStack, int n, int n2, float f) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(this.guiTexture);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        this.blit(matrixStack, n3 + 59, n4 + 20, 0, this.ySize + (((AbstractRepairContainer)this.container).getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((((AbstractRepairContainer)this.container).getSlot(0).getHasStack() || ((AbstractRepairContainer)this.container).getSlot(1).getHasStack()) && !((AbstractRepairContainer)this.container).getSlot(2).getHasStack()) {
            this.blit(matrixStack, n3 + 99, n4 + 45, this.xSize, 0, 28, 21);
        }
    }

    @Override
    public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
        this.sendSlotContents(container, 0, container.getSlot(0).getStack());
    }

    @Override
    public void sendWindowProperty(Container container, int n, int n2) {
    }

    @Override
    public void sendSlotContents(Container container, int n, ItemStack itemStack) {
    }
}

