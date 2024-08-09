/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders
extends GuiButtonOF {
    public GuiButtonDownloadShaders(int n, int n2, int n3) {
        super(n, n2, n3, 22, 20, "");
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.visible) {
            super.render(matrixStack, n, n2, f);
            ResourceLocation resourceLocation = new ResourceLocation("optifine/textures/icons.png");
            Config.getTextureManager().bindTexture(resourceLocation);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.blit(matrixStack, this.x + 3, this.y + 2, 0, 0, 16, 16);
        }
    }
}

