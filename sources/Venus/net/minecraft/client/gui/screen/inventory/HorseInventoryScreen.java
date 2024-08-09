/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.util.ResourceLocation;

public class HorseInventoryScreen
extends ContainerScreen<HorseInventoryContainer> {
    private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/horse.png");
    private final AbstractHorseEntity horseEntity;
    private float mousePosx;
    private float mousePosY;

    public HorseInventoryScreen(HorseInventoryContainer horseInventoryContainer, PlayerInventory playerInventory, AbstractHorseEntity abstractHorseEntity) {
        super(horseInventoryContainer, playerInventory, abstractHorseEntity.getDisplayName());
        this.horseEntity = abstractHorseEntity;
        this.passEvents = false;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        AbstractChestedHorseEntity abstractChestedHorseEntity;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        if (this.horseEntity instanceof AbstractChestedHorseEntity && (abstractChestedHorseEntity = (AbstractChestedHorseEntity)this.horseEntity).hasChest()) {
            this.blit(matrixStack, n3 + 79, n4 + 17, 0, this.ySize, abstractChestedHorseEntity.getInventoryColumns() * 18, 54);
        }
        if (this.horseEntity.func_230264_L__()) {
            this.blit(matrixStack, n3 + 7, n4 + 35 - 18, 18, this.ySize + 54, 18, 18);
        }
        if (this.horseEntity.func_230276_fq_()) {
            if (this.horseEntity instanceof LlamaEntity) {
                this.blit(matrixStack, n3 + 7, n4 + 35, 36, this.ySize + 54, 18, 18);
            } else {
                this.blit(matrixStack, n3 + 7, n4 + 35, 0, this.ySize + 54, 18, 18);
            }
        }
        InventoryScreen.drawEntityOnScreen(n3 + 51, n4 + 60, 17, (float)(n3 + 51) - this.mousePosx, (float)(n4 + 75 - 50) - this.mousePosY, this.horseEntity);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.mousePosx = n;
        this.mousePosY = n2;
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }
}

