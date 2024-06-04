package com.polarware.ui.click.components.category;

import com.polarware.Client;
import com.polarware.module.api.Category;
import com.polarware.ui.click.RiseClickGUI;
import com.polarware.ui.click.screen.impl.HomeScreen;
import com.polarware.util.gui.GUIUtil;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SidebarCategory implements InstanceAccess {

    private List<CategoryComponent> categories;
    /* Information */
    public double sidebarWidth = 80;
    private double opacity, fadeOpacity;
    private long lastTime = 0;

    public SidebarCategory() {
        categories = Arrays.stream(Category.values())
                .map(CategoryComponent::new)
                .collect(Collectors.toList());
    }

    public void preRenderClickGUI() {
        /* ClickGUI */
        final RiseClickGUI clickGUI = Client.INSTANCE.getStandardClickGUI();
        final Color color = new Color(clickGUI.sidebarColor.getRed(), clickGUI.sidebarColor.getGreen(), clickGUI.sidebarColor.getBlue(), (int) Math.min(opacity, clickGUI.sidebarColor.getAlpha()));

        RenderUtil.roundedRectangle(
                clickGUI.position.x, clickGUI.position.y,
                sidebarWidth + 20, clickGUI.scale.y,
                getStandardClickGUI().getRound(),
                ColorUtil.withAlpha(color, 180)
        );
    }

    public void renderSidebar(final float mouseX, final float mouseY) {
        /* ClickGUI */
        final RiseClickGUI clickGUI = Client.INSTANCE.getStandardClickGUI();

        /* Animations */
        final long time = System.currentTimeMillis();

        if (lastTime == 0)
            lastTime = time;

        final boolean hoverCategory = clickGUI.selectedScreen instanceof HomeScreen;

        if (GUIUtil.mouseOver(clickGUI.position.x, clickGUI.position.y, opacity > 0 ? 70 : 10,
                clickGUI.scale.y, mouseX, mouseY) || !hoverCategory)
            opacity = Math.min(opacity + (time - lastTime) * 2, 255);
        else
            opacity = Math.max(opacity - (time - lastTime), 0);

        if (GUIUtil.mouseOver(clickGUI.position.x, clickGUI.position.y, fadeOpacity > 0 ? 70 : 10,
                clickGUI.scale.y, mouseX, mouseY) && hoverCategory)
            fadeOpacity = Math.min(fadeOpacity + (time - lastTime) * 2, 255);
        else
            fadeOpacity = Math.max(fadeOpacity - (time - lastTime), 0);

        /* Sidebar background */
        lastTime = time;

        /* Renders all categories */
        double offsetTop = - 21.5;

        for (final CategoryComponent category : categories)
            category.render((offsetTop += 21.5), sidebarWidth, (int) opacity, clickGUI.selectedScreen);
    }

    public void bloom() {
        for (final CategoryComponent category : categories)
            category.bloom(opacity);
    }

    public void clickSidebar(final float mouseX, final float mouseY, final int button) {
        if (opacity > 0)
            for (final CategoryComponent category : categories)
                category.click(mouseX, mouseY, button);
    }

    public void release() {
        if (opacity > 0)
            for (final CategoryComponent category : categories)
                category.release();
    }
}
