package com.polarware.ui.click.components.category;

import com.polarware.Client;
import com.polarware.module.api.Category;
import com.polarware.ui.click.RiseClickGUI;
import com.polarware.ui.click.screen.Screen;
import com.polarware.util.animation.Animation;
import com.polarware.util.font.FontManager;
import com.polarware.util.gui.GUIUtil;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.localization.Localization;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static com.polarware.util.animation.Easing.LINEAR;

public final class CategoryComponent implements InstanceAccess {

    private Animation animation = new Animation(LINEAR, 500);
    public final Category category;
    private long lastTime = 0;

    private float x, y;
    private boolean down;

    public CategoryComponent(final Category category) {
        this.category = category;
    }

    public void render(final double offset, final double sidebarWidth, final double opacity, final Screen selectedScreen) {
        final RiseClickGUI clickGUI = Client.INSTANCE.getStandardClickGUI();

        if (System.currentTimeMillis() - lastTime > 300) lastTime = System.currentTimeMillis();
        final long time = System.currentTimeMillis();

        /* Gets position depending on sidebar animation */
        x = (float) (clickGUI.position.x + 8);
        y = (float) (clickGUI.position.y + offset) + 16;

        String name = Localization.get(category.getName());

        /* Animations */
        animation.setDuration(200);
        animation.run(selectedScreen.equals(category.getClickGUIScreen()) ? 255 : 0);

        final double width = category.getFontRenderer().width(category.getIcon())
                + FontManager.getProductSansMedium(16).width(" " + name) + 12;

        GlStateManager.pushMatrix();

        /* Draws selection */
        RenderUtil.roundedRectangle(
                x + 1.5,
                y - 6.5,
                width,
                20,
                4,
                ColorUtil.withAlpha(getTheme().getAccentColor(new Vector2d(0, y / 5D)), (int) (Math.min(animation.getValue(), opacity))).darker()
        );

        int color = new Color(255, 255, 255, Math.min(selectedScreen.equals(category.getClickGUIScreen()) ? 255 : 200, (int) opacity)).hashCode();

        category.getFontRenderer().drawString(
                category.getIcon(),
                (float) (x + animation.getValue() / 80f + 2),
                y,
                color
        );

        FontManager.getProductSansMedium(16).drawString(
                name,
                (float) (x + animation.getValue() / 80f + 16),
                y + 2.0F,
                color
        );

        GlStateManager.popMatrix();

        lastTime = time;
    }

    public void click(final float mouseX, final float mouseY, final int button) {
        final boolean left = button == 0;

        if (GUIUtil.mouseOver(x - 11, y - 6.5, 27.5, 24, mouseX, mouseY) && left) {
            this.getStandardClickGUI().switchScreen(this.category);
            down = true;
        }
    }

    public void bloom(final double opacity) {
        final double width = category.getFontRenderer().width(category.getIcon());

        RenderUtil.roundedRectangle(
                x + 1.5,
                y - 5.5,
                width + 8,
                15,
                4,
                ColorUtil.withAlpha(getTheme().getAccentColor(new Vector2d(0, y / 5D)), (int) (Math.min(animation.getValue(), opacity))).darker()
        );
    }

    public void release() {
        down = false;
    }
}