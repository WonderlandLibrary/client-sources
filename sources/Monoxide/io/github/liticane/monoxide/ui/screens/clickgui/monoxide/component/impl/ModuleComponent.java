package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.Component;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class ModuleComponent extends Component {

    private final Animation toggleAnimation = new EaseInOutQuad(500, 1.0F);
    private final Animation hoverAnimation = new EaseInOutQuad(500, 1.0F);

    private final Module module;

    public ModuleComponent(MonoxideClickGuiScreen parent, Module module) {
        super(parent);
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        hoverAnimation.setDirection(
                NewRenderUtil.isHovered(mouseX, mouseY, x, y, width, height) ? Direction.FORWARDS : Direction.BACKWARDS
        );

        NewRenderUtil.rectangle(
                x, y, width, height,
                new Color(25, 25, 25)
        );

        if (module.isEnabled() || !toggleAnimation.isDone()) {
            Color color1 = new Color(25, 45, 85, (int) (255 * toggleAnimation.getOutput())).darker();
            Color color2 = new Color(85, 45, 65, (int) (255 * toggleAnimation.getOutput())).darker();

            NewRenderUtil.horizontalGradient(
                    x, y, width, height,
                    NewColorUtil.interpolateColorsBackAndForth(5, (int) x, color1, color2),
                    NewColorUtil.interpolateColorsBackAndForth(5, (int) (x + width), color1, color2)
            );
        }

        if (NewRenderUtil.isHovered(mouseX, mouseY, x, y, width, height) || !hoverAnimation.isDone()) {
            NewRenderUtil.horizontalGradient(
                    x, y, width, height,
                    new Color(255, 255, 255, (int) (25 * hoverAnimation.getOutput())),
                    new Color(255, 255, 255, (int) (25 * hoverAnimation.getOutput()))
            );
        }

        FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI Medium", 18);

        fontRenderer.drawStringWithShadow(
                module.getName(),
                x + width / 2.0F - fontRenderer.getStringWidth(module.getName()) / 2.0F,
                y + height / 2.0F - fontRenderer.getHeight() / 2.0F,
                Color.WHITE.getRGB()
        );
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        if (NewRenderUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
            switch (button) {
                case 0:
                    toggleAnimation.setDirection(!module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
                    module.toggle();
                    break;
                case 1:
                    parent.getSettingPopUpComponent().setModule(module);
                    parent.getSettingPopUpComponent().init();
                    parent.getSettingPopUpComponent().setExpanded(true);
                    break;
            }
        }

    }

    @Override
    public void released(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyboard(char character, int keyCode) {

    }
}
