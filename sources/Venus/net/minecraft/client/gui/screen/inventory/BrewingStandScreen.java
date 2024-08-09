/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BrewingStandContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BrewingStandScreen
extends ContainerScreen<BrewingStandContainer> {
    private static final ResourceLocation BREWING_STAND_GUI_TEXTURES = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public BrewingStandScreen(BrewingStandContainer brewingStandContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(brewingStandContainer, playerInventory, iTextComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        int n3;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BREWING_STAND_GUI_TEXTURES);
        int n4 = (this.width - this.xSize) / 2;
        int n5 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n4, n5, 0, 0, this.xSize, this.ySize);
        int n6 = ((BrewingStandContainer)this.container).func_216982_e();
        int n7 = MathHelper.clamp((18 * n6 + 20 - 1) / 20, 0, 18);
        if (n7 > 0) {
            this.blit(matrixStack, n4 + 60, n5 + 44, 176, 29, n7, 4);
        }
        if ((n3 = ((BrewingStandContainer)this.container).func_216981_f()) > 0) {
            int n8 = (int)(28.0f * (1.0f - (float)n3 / 400.0f));
            if (n8 > 0) {
                this.blit(matrixStack, n4 + 97, n5 + 16, 176, 0, 9, n8);
            }
            if ((n8 = BUBBLELENGTHS[n3 / 2 % 7]) > 0) {
                this.blit(matrixStack, n4 + 63, n5 + 14 + 29 - n8, 185, 29 - n8, 12, n8);
            }
        }
    }
}

