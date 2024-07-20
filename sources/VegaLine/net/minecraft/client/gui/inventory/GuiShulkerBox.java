/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiShulkerBox
extends GuiContainer {
    private static final ResourceLocation field_190778_u = new ResourceLocation("textures/gui/container/shulker_box.png");
    private final IInventory field_190779_v;
    private final InventoryPlayer field_190780_w;

    public GuiShulkerBox(InventoryPlayer p_i47233_1_, IInventory p_i47233_2_) {
        Minecraft.getMinecraft();
        super(new ContainerShulkerBox(p_i47233_1_, p_i47233_2_, Minecraft.player));
        this.field_190780_w = p_i47233_1_;
        this.field_190779_v = p_i47233_2_;
        ++this.ySize;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.field_190779_v.getDisplayName().getUnformattedText(), 8.0f, 6.0, 0x404040);
        this.fontRendererObj.drawString(this.field_190780_w.getDisplayName().getUnformattedText(), 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(field_190778_u);
        int i = (width - this.xSize) / 2;
        int j = (height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}

