package com.polarware.module.api;

import com.polarware.ui.click.screen.Screen;
import com.polarware.ui.click.screen.impl.*;
import com.polarware.util.font.Font;
import com.polarware.util.font.FontManager;
import lombok.Getter;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
public enum Category {
    COMBAT("category.combat", FontManager.getIconsOne(20), "a", 0x2, new CategoryScreen()),
    MOVEMENT("category.movement", FontManager.getIconsOne(20), "b", 0x3, new CategoryScreen()),
    PLAYER("category.player", FontManager.getIconsOne(20), "c", 0x4, new CategoryScreen()),
    RENDER("category.render", FontManager.getIconsOne(20), "g", 0x5, new CategoryScreen()),
    EXPLOIT("category.exploit", FontManager.getIconsOne(20), "d", 0x6, new CategoryScreen()),
    GHOST("category.ghost", FontManager.getIconsOne(20), "f", 0x7, new CategoryScreen()),
    OTHER("category.other", FontManager.getIconsOne(20), "e", 0x8, new CategoryScreen()),
    THEME("category.themes", FontManager.getIconsTwo(20), "m", 0xA, new ThemeScreen());

    // name of category (in case we don't use enum names)
    private final String name;

    // icon character
    private final String icon;

    // optional color for every specific category (module list or click gui)
    private final int color;

    private final Font fontRenderer;

    public final Screen clickGUIScreen;

    Category(final String name, final Font fontRenderer, final String icon, final int color, final Screen clickGUIScreen) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.clickGUIScreen = clickGUIScreen;
        this.fontRenderer = fontRenderer;
    }

}