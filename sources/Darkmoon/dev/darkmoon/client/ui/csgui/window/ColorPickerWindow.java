package dev.darkmoon.client.ui.csgui.window;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.ui.csgui.CsGui;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.animation.impl.EaseInOutQuad;
import lombok.Getter;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ColorPickerWindow extends Window {
    @Getter
    private final ColorSetting colorSetting;
    private Animation animation = new EaseInOutQuad(250, 1, Direction.FORWARDS);
    private final NumberSetting hueSlider = new NumberSetting("Hue", 1, 1, 360, 1);
    private boolean isHueDragging;
    private int hueValue;

    public ColorPickerWindow(float x, float y, float width, float height, ColorSetting colorSetting) {
        super(x, y, width, height);
        this.colorSetting = colorSetting;
        float[] color = ColorUtility.getRGBAf(colorSetting.get());
        float[] hueArray = Color.RGBtoHSB((int) (color[0] * 255f), (int) (color[1] * 255f), (int) (color[2] * 255f), color);
        hueValue = (int) (hueArray[0] * 360f);
    }

    @Override
    public void init() {
        animation = new EaseInOutQuad(250, 1, Direction.FORWARDS);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (animation.finished(Direction.BACKWARDS)) {
            CsGui.colorPicker = null;
            return;
        }

        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        RenderUtility.scaleStart(x + width / 2f, y + height / 2f, animation.getOutput());

        RenderUtility.drawGradientGlow(x, y, width, height, 7, color, color2);
        RenderUtility.drawRoundedRect(x, y, width, height, 5, new Color(0, 0, 0).getRGB());
        int hueColor = ColorUtility.HUEtoRGB(hueValue);
        RenderUtility.drawRoundedGradientRect(x + 5, y + 5, width - 10, height - 20, 0, 0, Color.WHITE.getRGB(), Color.BLACK.getRGB(), hueColor, Color.BLACK.getRGB());

        int pixelColor = 0;
        if (RenderUtility.isHovered(mouseX, mouseY, x + 5, y + 5, width - 10, height - 20)) {
            if (!isHueDragging) {
                if (Mouse.isButtonDown(0)) {
                    float saturation = (mouseX - (x + 5)) / (width - 10);
                    float brightness = 1 - ((mouseY - (y + 5)) / (height - 20));
                    pixelColor = Color.HSBtoRGB(hueValue / 360f, saturation, brightness);
                }
            }
        }

        if (pixelColor != 0) {
            colorSetting.setColor(pixelColor);
        }

        if (isHueDragging) {
            hueValue = getSliderValue(hueSlider, x + 5, mouseX);
        }

        float inc = 0.2F;
        float times = 1 / inc;
        float xHuePos = x + 5;
        float size = (width - 10) / times;
        for (int i = 0; i < times; i++) {
            boolean last = i == times - 1;
            if (last)
                size--;
            RenderUtility.drawGradientRect(xHuePos, y + height - 10, size, 5,
                    Color.HSBtoRGB(inc * i, 1.0F, 1.0F),
                    Color.HSBtoRGB(inc * (i + 1), 1.0F, 1.0F),
                    Color.HSBtoRGB(inc * (i + 1), 1.0F, 1.0F),
                    Color.HSBtoRGB(inc * i, 1.0F, 1.0F));
            if (!last)
                xHuePos += size;
        }

        RenderUtility.drawRoundedRect(x + 4 + getPos(hueSlider, hueValue), y + height - 11, 2, 7, 1, -1);

        RenderUtility.scaleEnd();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!RenderUtility.isHovered(mouseX, mouseY, x, y, width, height) && animation.isDone()) {
            animation.setDirection(Direction.BACKWARDS);
            animation.setDuration(225);
        }
        if (RenderUtility.isHovered(mouseX, mouseY, x + 5, y + height - 10, width - 10, 5)  && mouseButton == 0) {
            isHueDragging = true;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int state) {
        isHueDragging = false;
    }

    public int getSliderValue(NumberSetting numberSetting, float posX, int mouseX) {
        int delta = (int) (numberSetting.getMaxValue() - numberSetting.getMinValue());
        float clickedX = mouseX - posX;
        float value = clickedX / (width - 10);
        float outValue = (float) (numberSetting.getMinValue() + delta * value);
        return (int) MathUtility.clamp((int) MathUtility.round(outValue, numberSetting.getIncrement()), (float) numberSetting.getMinValue(), (float) numberSetting.getMaxValue());
    }

    public int getPos(NumberSetting numberSetting, int value) {
        int delta = (int) (numberSetting.getMaxValue() - numberSetting.getMinValue());
        return (int) ((width - 10) * (value - numberSetting.getMinValue()) / delta);
    }
}
