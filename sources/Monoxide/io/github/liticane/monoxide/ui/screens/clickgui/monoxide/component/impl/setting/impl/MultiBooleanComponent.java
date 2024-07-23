package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.ValueComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.util.render.updated.NewStencilUtil;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class MultiBooleanComponent extends ValueComponent {

    private Animation openAnimation;
    private Animation hoverAnimation;

    private MultiBooleanValue value;

    public MultiBooleanComponent(MonoxideClickGuiScreen parent, MultiBooleanValue value) {
        super(parent, value);
        this.value = value;

        openAnimation = new EaseInOutQuad(500, 1.0F);
        hoverAnimation = new EaseInOutQuad(500, 1.0F);
    }

    @Override
    public void init() {
        super.init();

        this.expanded = false;

        openAnimation.setDirection(Direction.BACKWARDS);
        openAnimation.reset();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        float buttonWidth = getButtonWidth();
        float buttonHeight = getButtonHeight();

        float posX = x + width - 5 - buttonWidth;
        float posY = y + 5;

        this.height = buttonHeight;

        boolean isHovered = NewRenderUtil.isHovered(mouseX, mouseY, posX, posY, buttonWidth, buttonHeight);

        hoverAnimation.setDirection(isHovered ? Direction.FORWARDS : Direction.BACKWARDS);

        fontRenderer20.drawStringWithShadow(value.getName(), x + 5, y + 5, Color.WHITE.getRGB());

        Color color1 = new Color(85, 45, 85).brighter().brighter();
        Color color2 = new Color(25, 45, 85).brighter().brighter();

        NewRenderUtil.rectangle(
                posX, posY, buttonWidth, buttonHeight,
                new Color(35, 35, 35)
        );

        fontRenderer20.drawCenteredStringWithShadow(value.getName(), posX + buttonWidth / 2.0F,
                posY + 3, Color.WHITE.getRGB());

        if (expanded || !openAnimation.isDone())
            for (int j = 0; j < value.getValues().length; j++) {
                String string = value.getValues()[j];

                float stringY = posY + (fontRenderer20.FONT_HEIGHT + 5) * (j + 1) * openAnimation.getOutput().floatValue() + 3;

                fontRenderer20.drawCenteredStringWithShadow(
                        string, posX + buttonWidth / 2.0F, stringY,
                        value.get(string)
                                ? NewColorUtil.interpolateColorsBackAndForth(7, (int) (posY + buttonWidth / 2), color1, color2).getRGB()
                                : new Color(175, 175, 175).getRGB()
                );
            }

        if (NewRenderUtil.isHovered(mouseX, mouseY, posX, posY, buttonWidth, buttonHeight) || !hoverAnimation.isDone()) {
            NewRenderUtil.rectangle(
                    posX, posY, buttonWidth, buttonHeight,
                    new Color(255, 255, 255, (int) (25 * hoverAnimation.getOutput()))
            );
        }

        super.draw(mouseX, mouseY);
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        float buttonWidth = getButtonWidth();
        float buttonHeight = getButtonHeight();

        float posX = x + width - 5 - buttonWidth;
        float posY = y + 5;

        boolean isHovered = NewRenderUtil.isHovered(mouseX, mouseY, posX, posY, buttonWidth, buttonHeight);

        if (isHovered && button == 1) {
            expanded = !expanded;

            openAnimation.setDirection(isExpanded() ? Direction.FORWARDS : Direction.BACKWARDS);
            openAnimation.reset();
        }

        if (expanded && button == 0) {
            for (int j = 0; j < value.getValues().length; j++) {
                String string = value.getValues() [j];

                float stringX = posX + 3;
                float stringY = posY + (fontRenderer20.FONT_HEIGHT + 5) * j + 3;

                if (NewRenderUtil.isHovered(mouseX, mouseY, stringX, stringY, buttonWidth, fontRenderer20.getHeight())) {
                    value.set(string, !value.get(string));
                }
            }
        }

        super.clicked(mouseX, mouseY, button);
    }

    private float getButtonWidth() {
        String[] array = value.getValues();
        Arrays.sort(array, Comparator.comparingInt(String::length));
        return fontRenderer20.getStringWidth(array[0]) + 30;
    }

    private float getButtonHeight() {
        return height + (fontRenderer20.FONT_HEIGHT + 5) * (value.getValues().length) * openAnimation.getOutput().floatValue() + 1;
    }
}
