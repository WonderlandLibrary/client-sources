package com.alan.clients.ui.menu.component.button.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;

public class MenuAccountTypeButton extends MenuTextButton {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(24, Weight.BOLD);
    private final ResourceLocation resourceLocation;

    public MenuAccountTypeButton(double x, double y, double width, double height, Runnable runnable, String name, ResourceLocation resourceLocation) {
        super(x, y, width, height, runnable, name);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        // Runs the animation update - keep this
        this.getHoverAnimation().run(MouseUtil.isHovered(this.getX(), this.getY(), this.getWidth(), this.getHeight(), mouseX, mouseY) ? 100 : 45);

        // Colors for rendering
        final double value = getY();
        final Color bloomColor = ColorUtil.withAlpha(Color.BLACK, 150);
        final Color fontColor = ColorUtil.withAlpha(Color.WHITE, (int) (150 + this.getHoverAnimation().getValue()));

        // Renders the background of the button
        getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, Color.WHITE));
        getLayer(BLOOM).add(() -> RenderUtil.roundedRectangle(this.getX() + 0.5F, value + 0.5F, this.getWidth() - 1, this.getHeight() - 1, 6, bloomColor));

        // Renders the button text
        getLayer(REGULAR).add(() -> {
            RenderUtil.roundedRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5,
                    ColorUtil.withAlpha(BUTTON, (int) this.getHoverAnimation().getValue() - 15));
            RenderUtil.roundedOutlineGradientRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5,
                    1, ColorUtil.withAlpha(BORDER_TWO, 32), ColorUtil.withAlpha(BORDER_ONE, 32));

            int imageSize = 64;
            RenderUtil.image(resourceLocation, this.getX() + this.getWidth() / 2.0F - imageSize / 2, value + this.getHeight() / 2.0F - imageSize / 2, imageSize, imageSize, fontColor);

            FONT_RENDERER.drawCentered(this.name, (float) (this.getX() + this.getWidth() / 2.0F),
                    (float) (value + this.getHeight() / 2.0F - imageSize / 2 - 24), fontColor.getRGB());
        });
    }
}
