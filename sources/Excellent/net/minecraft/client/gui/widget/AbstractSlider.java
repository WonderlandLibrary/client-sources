package net.minecraft.client.gui.widget;

import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mojang.blaze3d.matrix.MatrixStack;

public abstract class AbstractSlider extends Widget {
    protected double sliderValue;

    public AbstractSlider(int x, int y, int width, int height, ITextComponent message, double defaultValue) {
        super(x, y, width, height, message);
        this.sliderValue = defaultValue;
    }

    protected int getYImage(boolean isHovered) {
        return 0;
    }

    protected IFormattableTextComponent getNarrationMessage() {
        return new TranslationTextComponent("gui.narrate.slider", this.getMessage());
    }

    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY) {
        int color = ColorUtil.getColor(this.active ? 20 : 10, 255);
        RectUtil.drawRoundedRectShadowed(matrixStack, this.x + (int) (this.sliderValue * (double) (this.width - 8)) + 2, this.y + 2, this.x + (int) (this.sliderValue * (double) (this.width - 8)) + 6, this.y + 20 - 2, 2, 1, color, color, color, color, true, true, true, true);

    }

    public void onClick(double mouseX, double mouseY) {
        this.changeSliderValue(mouseX);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean flag = keyCode == 263;

        if (flag || keyCode == 262) {
            float f = flag ? -1.0F : 1.0F;
            this.setSliderValue(this.sliderValue + (double) (f / (float) (this.width - 8)));
        }

        return false;
    }

    private void changeSliderValue(double mouseX) {
        this.setSliderValue((mouseX - (double) (this.x + 4)) / (double) (this.width - 8));
    }

    private void setSliderValue(double value) {
        double d0 = this.sliderValue;
        this.sliderValue = MathHelper.clamp(value, 0.0D, 1.0D);

        if (d0 != this.sliderValue) {
            this.func_230972_a_();
        }

        this.func_230979_b_();
    }

    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.changeSliderValue(mouseX);
        super.onDrag(mouseX, mouseY, dragX, dragY);
    }

    public void playDownSound(SoundHandler handler) {
    }

    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(Minecraft.getInstance().getSoundHandler());
    }

    protected abstract void func_230979_b_();

    protected abstract void func_230972_a_();
}
