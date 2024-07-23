package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.ValueComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.color.ColorUtil;
import io.github.liticane.monoxide.util.render.shader.shaders.RoundedShader;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.util.render.updated.NewStencilUtil;
import io.github.liticane.monoxide.value.impl.ModeValue;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ModeComponent extends ValueComponent {

    private Animation openAnimation;
    private Animation hoverAnimation;

    private ModeValue value;

    public ModeComponent(MonoxideClickGuiScreen parent, ModeValue value) {
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

        NewRenderUtil.rectangle(
                posX, posY, buttonWidth, buttonHeight,
                new Color(35, 35, 35)
        );

        Color color1 = new Color(85, 45, 85).brighter().brighter();
        Color color2 = new Color(25, 45, 85).brighter().brighter();

        List<String> options = Arrays.stream(value.getValues()).sorted(Comparator.comparingInt(string -> value.is(string) ? 0 : 1)).toList();

        if (expanded || !openAnimation.isDone())
            for (int j = 1; j < options.size(); j++) {
                String string = options.get(j);

                float stringY = posY + (fontRenderer20.FONT_HEIGHT + 5) * j * openAnimation.getOutput().floatValue() + 3;

                fontRenderer20.drawCenteredStringWithShadow(
                        string, posX + buttonWidth / 2.0F, stringY,
                        new Color(175, 175, 175).getRGB()
                );
            }

        fontRenderer20.drawCenteredStringWithShadow(value.getValue(), posX + buttonWidth / 2.0F, posY + 3,
                NewColorUtil.interpolateColorsBackAndForth(7, (int) (posX + buttonWidth / 2), color1, color2).getRGB());

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
            List<String> options = Arrays.stream(value.getValues()).sorted(Comparator.comparingInt(string -> value.is(string) ? 0 : 1)).toList();

            for (int j = 0; j < options.size(); j++) {
                String string = expanded || !openAnimation.finished(Direction.BACKWARDS) ? options.get(j) : value.getValue();

                float stringX = posX + 3;
                float stringY = posY + (fontRenderer20.FONT_HEIGHT + 5) * j + 3;

                if (NewRenderUtil.isHovered(mouseX, mouseY, stringX, stringY, buttonWidth, fontRenderer20.getHeight())) {
                    value.setValue(string);
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
        return height + (fontRenderer20.FONT_HEIGHT + 5) * (value.getValues().length - 1) * openAnimation.getOutput().floatValue() + 1;
    }
}
