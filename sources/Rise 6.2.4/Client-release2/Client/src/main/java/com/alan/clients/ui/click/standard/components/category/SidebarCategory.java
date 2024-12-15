package com.alan.clients.ui.click.standard.components.category;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.api.Category;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import lombok.Getter;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SidebarCategory implements Accessor {

    private final List<CategoryComponent> categories;
    /* Information */
    public double sidebarWidth = 100;
    private double opacity, fadeOpacity;
    @Getter
    private boolean hovering;
    private long lastTime = 0;
    private Animation animation = new Animation(Easing.EASE_OUT_EXPO, 300);
    private Animation dropShadowAnimation = new Animation(Easing.LINEAR, 300);

    public SidebarCategory() {
        categories = Arrays.stream(Category.values())
                .map(CategoryComponent::new)
                .collect(Collectors.toList());
    }

    public void preRenderClickGUI() {
        /* ClickGUI */
        final RiseClickGUI clickGUI = Client.INSTANCE.getClickGUI();
        final Color color = Colors.SECONDARY.getWithAlpha((int) opacity);

        animation.setDuration(hovering ? 700 : 2000);
        animation.run(hovering ? 0 : -sidebarWidth / 1.5f);

        RenderUtil.roundedRectangle(clickGUI.position.x, clickGUI.position.y, sidebarWidth + animation.getValue(), clickGUI.scale.y, getClickGUI().getRound(), color, true, false, false, true);

        dropShadowAnimation.setDuration(1000);
        dropShadowAnimation.run(clickGUI.getSelectedScreen().hideSideBar() ? 255 : 0);

        RenderUtil.horizontalGradient(clickGUI.position.x + sidebarWidth + animation.getValue(), clickGUI.position.y,
                30, clickGUI.scale.y, ColorUtil.withAlpha(Color.BLACK, (int) (Math.min(dropShadowAnimation.getValue(), opacity / 7))),
                new Color(0, 0, 0, 0));
    }

    public void renderSidebar(final float mouseX, final float mouseY) {
        /* ClickGUI */
        final RiseClickGUI clickGUI = Client.INSTANCE.getClickGUI();

        /* Animations */
        final long time = System.currentTimeMillis();

        if (lastTime == 0) lastTime = time;

        final boolean hoverCategory = clickGUI.selectedScreen.hideSideBar();

        if ((hovering = (!Mouse.isButtonDown(0) || hovering) && GUIUtil.mouseOver(clickGUI.position.x - 200, clickGUI.position.y, hovering ? 310 : 210, clickGUI.scale.y, mouseX, mouseY) || !hoverCategory)) {
            opacity = Math.min(opacity + (time - lastTime) * 2, 255);
//            sidebarWidth = Math.min((sidebarWidth + (time - lastTime) * 5 * (0.1 - sidebarWidth / 750)), 89);
        } else {
            opacity = Math.max(opacity - (time - lastTime) * 1.5f, 0);
//            sidebarWidth = Math.max((sidebarWidth - (time - lastTime) * 5 * (0.1 - sidebarWidth / 750)), 55);
        }

        if (GUIUtil.mouseOver(clickGUI.position.x, clickGUI.position.y, fadeOpacity > 0 ? 70 : 10, clickGUI.scale.y, mouseX, mouseY) && hoverCategory) {
            fadeOpacity = Math.min(fadeOpacity + (time - lastTime) * 2, 255);
        } else {
            fadeOpacity = Math.max(fadeOpacity - (time - lastTime), 0);
        }

        /* Drop shadow */
//        RenderUtil.horizontalGradient(clickGUI.position.x + sidebarWidth - 3 - 10, clickGUI.position.y, 20, clickGUI.scale.y, new Color(0, 0, 0, hoverCategory ? (int) (fadeOpacity * 0.25) : 100),
//                new Color(0, 0, 0, 0));

        /* Sidebar background */
        lastTime = time;
//        RenderUtil.dropShadow(4, clickGUI.position.x + 20, clickGUI.position.y, (float) sidebarWidth - 20, clickGUI.scale.y, 60, 1);

        //        RenderUtil.rectangle(clickGUI.position.x + 15, clickGUI.position.y, sidebarWidth - 15, clickGUI.scale.y, color);

        /* Renders all categories */
        double offsetTop = 10;

        for (final CategoryComponent category : categories) {
            category.render((offsetTop += 19.5), sidebarWidth + animation.getValue(), (int) opacity, clickGUI.selectedScreen);
        }

        final float posX = (float) (clickGUI.position.getX() + 9 + animation.getValue());
        final float posY = clickGUI.position.getY() + ((19.5F + 30) / 2.0F - Fonts.MAIN.get(42, Weight.REGULAR).height() / 2.0F);

        Fonts.MAIN.get(32, Weight.REGULAR).draw(Client.NAME, posX + 5, posY + 2, ColorUtil.withAlpha(Color.WHITE, (int) opacity).hashCode());
        Fonts.MAIN.get(16, Weight.REGULAR).draw(Client.VERSION_FULL, posX + 5 + Fonts.MAIN.get(32, Weight.REGULAR).width(Client.NAME), posY, ColorUtil.withAlpha(getTheme().getFirstColor(), (int) Math.min(opacity, 200)).getRGB());

//        this.poppinsBold.drawString(Rise.NAME, (float) (clickGUI.position.x + sidebarWidth - 56), clickGUI.position.y + 12, new Color(0, 0, 0, (int) Math.min(opacity, 100)).hashCode());
//        this.poppinsBold.drawString(Rise.NAME, (float) (clickGUI.position.x + sidebarWidth - 56), clickGUI.position.y + 11, new Color(clickGUI.accentColor.getRed(), clickGUI.accentColor.getGreen(), clickGUI.accentColor.getBlue(), (int) opacity).hashCode());
    }

    public void bloom() {
        for (final CategoryComponent category : categories) {
            category.bloom(opacity);
        }
    }

    public void clickSidebar(final float mouseX, final float mouseY, final int button) {
        if (opacity > 0) {
            for (final CategoryComponent category : categories) {
                category.click(mouseX, mouseY, button);
            }
        }
    }

    public void release() {
        if (opacity > 0) {
            for (final CategoryComponent category : categories) {
                category.release();
            }
        }
    }
}
