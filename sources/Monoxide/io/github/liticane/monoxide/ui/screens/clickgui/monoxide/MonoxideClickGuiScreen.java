package io.github.liticane.monoxide.ui.screens.clickgui.monoxide;

import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.CategoryComponent;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.SettingPopUpComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseBackIn;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.shader.access.ShaderAccess;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonoxideClickGuiScreen extends GuiScreen {

    private Animation openAnimation;
    private Animation backgroundAnimation;

    private final List<CategoryComponent> categories = new ArrayList<>();

    @Getter
    private final SettingPopUpComponent settingPopUpComponent = new SettingPopUpComponent(this, null);

    public boolean blockInputs = false;

    public MonoxideClickGuiScreen() {
        int i = 0;
        for (ModuleCategory category : ModuleCategory.values()) {
            CategoryComponent categoryComponent = new CategoryComponent(this, category, 20 + 155 * i, 20);
            categories.add(categoryComponent);
            ++i;
        }

    }

    @Override
    public void initGui() {
        categories.forEach(CategoryComponent::init);

        openAnimation = new EaseBackIn(500, 0.5F, 2F);
        openAnimation.setDirection(Direction.FORWARDS);
        openAnimation.reset();

        backgroundAnimation = new DecelerateAnimation(500, 1.0F);
        backgroundAnimation.setDirection(Direction.FORWARDS);
        backgroundAnimation.reset();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scale = new ScaledResolution(mc);

        if (categories.stream().filter(category -> !category.openAnimation.finished(Direction.BACKWARDS)).toList().isEmpty()
                && openAnimation.finished(Direction.BACKWARDS)) {
            mc.displayGuiScreen(null);
            blockInputs = false;
            return;
        }

        Color color1 = new Color(85, 45, 85, (int) (155 * backgroundAnimation.getOutput()));
        Color color2 = new Color(25, 45, 85, (int) (155 * backgroundAnimation.getOutput()));

        NewRenderUtil.horizontalGradient(
                0, 0, width / 2.0D, height,
                NewColorUtil.interpolateColorsBackAndForth(5, 0, color1, color2),
                NewColorUtil.interpolateColorsBackAndForth(5, width / 2, color1, color2)
        );

        NewRenderUtil.horizontalGradient(
                width / 2.0D, 0, width / 2.0D, height,
                NewColorUtil.interpolateColorsBackAndForth(5, width / 2, color1, color2),
                NewColorUtil.interpolateColorsBackAndForth(5, width, color1, color2)
        );

        NewRenderUtil.verticalGradient(
                0, 0, width, height, false,
                new Color(0, 0, 0, (int) (255 * backgroundAnimation.getOutput().floatValue())),
                new Color(0, 0, 0, 55)
        );

        NewRenderUtil.scale(scale.getScaledWidth() / 2f, scale.getScaledHeight() / 2f, openAnimation.getOutput().floatValue() + .5F, () -> categories.forEach(category -> category.draw(mouseX, mouseY)));

        if (settingPopUpComponent.isExpanded()) {
            settingPopUpComponent.draw(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!settingPopUpComponent.isExpanded())
            categories.forEach(category -> category.clicked(mouseX, mouseY, mouseButton));
        else
            settingPopUpComponent.clicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (!settingPopUpComponent.isExpanded())
            categories.forEach(category -> category.released(mouseX, mouseY, state));
        else
            settingPopUpComponent.released(mouseX, mouseY, state);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (blockInputs)
            return;

        if (!settingPopUpComponent.isExpanded()) {
            categories.forEach(category -> category.keyboard(typedChar, keyCode));

            if (keyCode == 1) {
                blockInputs = true;

                backgroundAnimation.setDirection(Direction.BACKWARDS);
                openAnimation.setDirection(Direction.BACKWARDS);

                categories.forEach(category -> category.openAnimation.changeDirection());
            }
        } else {
            settingPopUpComponent.keyboard(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
