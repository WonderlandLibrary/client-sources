package lol.point.returnclient.ui.components;

import lol.point.Return;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;

import java.awt.*;

public class UIButton {

    private FastFontRenderer text = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private FastFontRenderer icon = Return.INSTANCE.fontManager.getFont("Return-Icons 20");
    public String buttonText, buttonIcon;
    private float width, height;

    public UIButton(String buttonText, String buttonIcon, float width, float height) {
        this.buttonText = buttonText;
        this.buttonIcon = buttonIcon;
        this.width = width;
        this.height = height;
    }

    public void drawButton(int mouseX, int mouseY, float x, float y, float iconX, float iconY) {
        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        boolean hover = RenderUtil.hovered(mouseX, mouseY, x, y, width, height);
        if (hover) {
            RenderUtil.gradientH(x, y, width, height, gradient1, gradient2);
        }

        ShaderUtil.renderGlow(() -> {
            if (hover) {
                RenderUtil.gradientH(x, y, width, height, gradient1, gradient2);
            }
        }, 2, 2, 0.86f);

        RenderUtil.rectangle(hover ? x + 1 : x, hover ? y + 1 : y, hover ? width - 2 : width, hover ? height - 2 : height, hover ? new Color(33, 33, 33).brighter() : new Color(33, 33, 33));

        text.drawStringWithShadow(buttonText, x + (width / 2) - (text.getWidth(buttonText) / 2), y + 6, -1);
        if (!buttonIcon.isEmpty() || !buttonIcon.isBlank()) {
            icon.drawStringWithShadow(buttonIcon, iconX, iconY, -1);
        }
    }

    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY, float x, float y) {
        boolean hover = RenderUtil.hovered(mouseX, mouseY, x, y, width, height);
        return mouseButton == 0 && hover;
    }
}
