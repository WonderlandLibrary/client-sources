/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.shaders.gui.GuiShaderOptions;

public class GuiSliderShaderOption
extends GuiButtonShaderOption {
    private float sliderValue = 1.0f;
    public boolean dragging;
    private ShaderOption shaderOption = null;

    public GuiSliderShaderOption(int n, int n2, int n3, int n4, int n5, ShaderOption shaderOption, String string) {
        super(n, n2, n3, n4, n5, shaderOption, string);
        this.shaderOption = shaderOption;
        this.sliderValue = shaderOption.getIndexNormalized();
        this.setMessage(GuiShaderOptions.getButtonText(shaderOption, this.width));
    }

    @Override
    protected int getYImage(boolean bl) {
        return 1;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int n, int n2) {
        if (this.visible) {
            if (this.dragging && !Screen.hasShiftDown()) {
                this.sliderValue = (float)(n - (this.x + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0f, 1.0f);
                this.shaderOption.setIndexNormalized(this.sliderValue);
                this.sliderValue = this.shaderOption.getIndexNormalized();
                this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.width));
            }
            minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n3 = (this.isHovered() ? 2 : 1) * 20;
            this.blit(matrixStack, this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 46 + n3, 4, 20);
            this.blit(matrixStack, this.x + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.y, 196, 46 + n3, 4, 20);
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (super.mouseClicked(d, d2, n)) {
            this.sliderValue = (float)(d - (double)(this.x + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0f, 1.0f);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.width));
            this.dragging = true;
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        this.dragging = false;
        return false;
    }

    @Override
    public void valueChanged() {
        this.sliderValue = this.shaderOption.getIndexNormalized();
    }

    @Override
    public boolean isSwitchable() {
        return true;
    }
}

