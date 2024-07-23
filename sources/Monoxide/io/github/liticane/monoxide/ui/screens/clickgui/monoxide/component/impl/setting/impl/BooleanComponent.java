package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl;

import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.ValueComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.shader.shaders.RoundedShader;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.value.impl.BooleanValue;

import java.awt.*;

public class BooleanComponent extends ValueComponent {

    private final Animation toggleAnimation;
    private final Animation hoverAnimation;

    public BooleanValue value;

    public BooleanComponent(MonoxideClickGuiScreen parent, BooleanValue value) {
        super(parent, value);

        this.value = value;

        this.toggleAnimation = new EaseInOutQuad(500, 1.0F);
        this.hoverAnimation = new EaseInOutQuad(500, 1.0F);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);

        float checkX = x + width - 5 - height;
        float checkY = y + 5;

        float checkWidth = height;
        float checkHeight = height;

        boolean isHovered = NewRenderUtil.isHovered(mouseX, mouseY, checkX, checkY, checkWidth, checkHeight);

        toggleAnimation.setDirection(value.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDirection(isHovered ? Direction.FORWARDS : Direction.BACKWARDS);

        fontRenderer20.drawStringWithShadow(value.getName(), x + 5, y + 5, Color.WHITE.getRGB());

        NewRenderUtil.rectangle(checkX, checkY, checkWidth, checkHeight, new Color(45, 45, 45));

        Color color1 = new Color(85, 45, 85, (int) (255 * toggleAnimation.getOutput()));
        Color color2 = new Color(25, 45, 85, (int) (255 * toggleAnimation.getOutput()));

        if (value.isEnabled() || !toggleAnimation.isDone()) {
            NewRenderUtil.horizontalGradient(
                    checkX, checkY, checkWidth, checkHeight,
                    NewColorUtil.interpolateColorsBackAndForth(5, 0, color1, color2),
                    NewColorUtil.interpolateColorsBackAndForth(5, 250, color1, color2)
            );
        }

        if (isHovered || !hoverAnimation.isDone()) {
            NewRenderUtil.rectangle(checkX, checkY, checkWidth, checkHeight, new Color(255, 255, 255, (int) (25 * hoverAnimation.getOutput())));
        }
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        super.clicked(mouseX, mouseY, button);

        float checkX = x + width - 5 - height;
        float checkY = y + 5;

        float checkWidth = height;
        float checkHeight = height;

        if (NewRenderUtil.isHovered(mouseX, mouseY, checkX, checkY, checkWidth, checkHeight) && button == 0) {
            value.setEnabled(!value.isEnabled());
        }
    }
}
