package com.alan.clients.ui.menu.component.button.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import lombok.Setter;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;

public class MenuTextButton extends MenuButton {

    @Setter
    private Font fontRenderer = Fonts.MAIN.get(24, Weight.BOLD);

    public String name;
    @Setter
    private float round = 5;
    @Setter
    private int glowAlpha = 32;

    public MenuTextButton(double x, double y, double width, double height, Runnable runnable, String name) {
        super(x, y, width, height, runnable);
        this.name = name;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        // Runs the animation update - keep this
        super.draw(mouseX, mouseY, partialTicks);

        // Colors for rendering
        final double value = getY();
        final Color bloomColor = ColorUtil.withAlpha(Color.BLACK, 150);
        final Color fontColor = ColorUtil.withAlpha(TEXT_PRIMARY, (int) (150 + this.getHoverAnimation().getValue()));

        // Renders the background of the button
        getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight(), round, Color.WHITE));
        getLayer(BLOOM).add(() -> RenderUtil.roundedRectangle(this.getX() + 0.5F, value + 0.5F, this.getWidth() - 1, this.getHeight() - 1, round + 1, bloomColor));

        // Renders the button text
        getLayer(REGULAR).add(() -> {
            RenderUtil.roundedRectangle(this.getX(), value, this.getWidth(), this.getHeight(), round,
                    ColorUtil.withAlpha(BUTTON, (int) this.getHoverAnimation().getValue() - 15));
//            RenderUtil.roundedOutlineRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5, 0.5f, ColorUtil.withAlpha(Color.WHITE, (int) ((int) this.getHoverAnimation().getValue() / 1.7f)));
            RenderUtil.roundedOutlineGradientRectangle(this.getX(), value, this.getWidth(), this.getHeight(), round,
                    1, ColorUtil.withAlpha(BORDER_TWO, glowAlpha), ColorUtil.withAlpha(BORDER_ONE, glowAlpha));
            fontRenderer.drawCentered(this.name, (float) (this.getX() + this.getWidth() / 2.0F),
                    (float) (value + this.getHeight() / 2.0F - 4), fontColor.getRGB());
        });
    }
}
