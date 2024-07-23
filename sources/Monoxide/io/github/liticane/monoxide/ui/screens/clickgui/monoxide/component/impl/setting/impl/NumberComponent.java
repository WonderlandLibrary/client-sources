package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.ValueComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.shader.shaders.RoundedShader;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.value.impl.NumberValue;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class NumberComponent extends ValueComponent {

    public Animation animation;

    public NumberValue value;

    public NumberComponent(MonoxideClickGuiScreen parent, NumberValue value) {
        super(parent, value);

        this.value = value;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        fontRenderer20.drawStringWithShadow(
                value.getName(), x + 5, y + 5, Color.WHITE.getRGB()
        );

        float sWidth = width / 2;
        float sHeight = 3;

        float posX = x + width / 2 - 5;
        float posY = y + height / 2 + sHeight / 2;

        float diff = Math.min(width / 2, Math.max(0, mouseX - x - width / 2));
        float min = value.getMinimum().floatValue();
        float max = value.getMaximum().floatValue();
        float current = value.getValue().floatValue();

        float circleRadius = 3;

        float circleX = posX + (width / 2) * (current - min) / (max - min);
        float circleY = posY + circleRadius / 2;

        Color color1 = new Color(85, 45, 85);
        Color color2 = new Color(25, 45, 85);

        NewRenderUtil.horizontalGradient(
                posX, posY, sWidth, sHeight,
                NewColorUtil.interpolateColorsBackAndForth(5, (int) (posX), color1, color2),
                NewColorUtil.interpolateColorsBackAndForth(5, (int) (posX + sWidth), color1, color2)
        );

        NewRenderUtil.circle(
            circleX, circleY, circleRadius, Color.WHITE
        );

        if (dragging) {
            if(diff == 0) {
                value.setValue(min);
            } else {
                double newValue = roundToPlace(
                        ((diff / (width / 2)) * (max - min) + min),
                        value.getDecimals()
                );

                value.setValue(newValue);
            }
        }

        super.draw(mouseX, mouseY);
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {

        float sWidth = width / 2;
        float sHeight = 3;

        float posX = x + width / 2 - 5;
        float posY = y + height / 2 + sHeight / 2;

        if (NewRenderUtil.isHovered(mouseX, mouseY, posX, posY, sWidth, sHeight)) {
            dragging = true;
        }

        super.clicked(mouseX, mouseY, button);
    }

    @Override
    public void released(int mouseX, int mouseY, int button) {

        dragging = false;

        super.released(mouseX, mouseY, button);
    }

    @Override
    public void keyboard(char character, int keyCode) {
        super.keyboard(character, keyCode);
    }

    private static double roundToPlace(double value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
