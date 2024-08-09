/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SmithingTableScreen
extends AbstractRepairScreen<SmithingTableContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/smithing.png");

    public SmithingTableScreen(SmithingTableContainer smithingTableContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(smithingTableContainer, playerInventory, iTextComponent, GUI_TEXTURE);
        this.titleX = 60;
        this.titleY = 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        RenderSystem.disableBlend();
        super.drawGuiContainerForegroundLayer(matrixStack, n, n2);
    }
}

