/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractSlider
extends Widget {
    protected double sliderValue;

    public AbstractSlider(int n, int n2, int n3, int n4, ITextComponent iTextComponent, double d) {
        super(n, n2, n3, n4, iTextComponent);
        this.sliderValue = d;
    }

    @Override
    protected int getYImage(boolean bl) {
        return 1;
    }

    @Override
    protected IFormattableTextComponent getNarrationMessage() {
        return new TranslationTextComponent("gui.narrate.slider", this.getMessage());
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int n, int n2) {
        minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int n3 = (this.isHovered() ? 2 : 1) * 20;
        this.blit(matrixStack, this.x + (int)(this.sliderValue * (double)(this.width - 8)), this.y, 0, 46 + n3, 4, 20);
        this.blit(matrixStack, this.x + (int)(this.sliderValue * (double)(this.width - 8)) + 4, this.y, 196, 46 + n3, 4, 20);
    }

    @Override
    public void onClick(double d, double d2) {
        this.changeSliderValue(d);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        boolean bl;
        boolean bl2 = bl = n == 263;
        if (bl || n == 262) {
            float f = bl ? -1.0f : 1.0f;
            this.setSliderValue(this.sliderValue + (double)(f / (float)(this.width - 8)));
        }
        return true;
    }

    private void changeSliderValue(double d) {
        this.setSliderValue((d - (double)(this.x + 4)) / (double)(this.width - 8));
    }

    private void setSliderValue(double d) {
        double d2 = this.sliderValue;
        this.sliderValue = MathHelper.clamp(d, 0.0, 1.0);
        if (d2 != this.sliderValue) {
            this.func_230972_a_();
        }
        this.func_230979_b_();
    }

    @Override
    protected void onDrag(double d, double d2, double d3, double d4) {
        this.changeSliderValue(d);
        super.onDrag(d, d2, d3, d4);
    }

    @Override
    public void playDownSound(SoundHandler soundHandler) {
    }

    @Override
    public void onRelease(double d, double d2) {
        super.playDownSound(Minecraft.getInstance().getSoundHandler());
    }

    protected abstract void func_230979_b_();

    protected abstract void func_230972_a_();
}

