package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class SliderComponent extends ValueComponent {

    private final NumberValue numberValue;
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public SliderComponent(NumberValue numberValue, float posX, float posY, float height) {
        super(numberValue, posX, posY, height);
        this.numberValue = numberValue;
    }

    @Override
    public float draw(int mouseX, int mouseY) {
        float sliderX = getPosX() + 2;
        float sliderWidth = getWidth() - 4;
        float sliderY = getPosY() + 2;
        float sliderHeight = getHeight() - 4;
        float length = MathHelper
                .floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue())
                        / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderWidth);
        RenderUtil.drawRect(sliderX, sliderY, length, sliderHeight, new Color(41, 146, 222).getRGB());
        if(Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight())) {
            double min1 = numberValue.getMinimum().floatValue();
            double max1 = numberValue.getMaximum().floatValue();
            double newValue = MathUtil.round((mouseX - sliderX) * (max1 - min1) / (sliderWidth - 1.0f) + min1, numberValue.getDecimals());
            numberValue.setValue(newValue);
        }
        fontRenderer.drawCenteredStringWithShadow(numberValue.getName() + ": " + numberValue.getValue().floatValue(), getPosX() + getWidth() / 2, getPosY() + getFinalHeight() / 2 - fontRenderer.FONT_HEIGHT / 2, new Color(139, 141, 145, 255).getRGB());
        return fontRenderer.getStringWidthInt(numberValue.getName()  + ": " + numberValue.getValue().floatValue()) + 6;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

}
