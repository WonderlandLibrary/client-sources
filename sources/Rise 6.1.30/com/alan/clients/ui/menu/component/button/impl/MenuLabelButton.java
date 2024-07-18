package com.alan.clients.ui.menu.component.button.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;

import java.awt.*;

public class MenuLabelButton extends MenuTextButton {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(14, Weight.LIGHT);
    private static final Color FONT_COLOR = ColorUtil.withAlpha(Color.WHITE, 150);
    private final Color color;

    public MenuLabelButton(double x, double y, double width, double height, Runnable runnable, String name, Color color) {
        super(x, y, width, height, runnable, name);
        this.color = ColorUtil.withAlpha(color, 150);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.roundedRectangle(getX(), getY(), getWidth(), getHeight(), 2, color);
        FONT_RENDERER.draw(name, getX() + getWidth() / 2.0F - FONT_RENDERER.width(name) / 2.0F, getY() + getHeight() / 2.0F - FONT_RENDERER.height() / 4.0F + 0.5F, FONT_COLOR.getRGB());
    }
}
