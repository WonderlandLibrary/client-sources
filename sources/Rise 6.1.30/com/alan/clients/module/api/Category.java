package com.alan.clients.module.api;

import com.alan.clients.font.Fonts;
import com.alan.clients.ui.click.standard.screen.Screen;
import com.alan.clients.ui.click.standard.screen.impl.*;
import com.alan.clients.util.font.Font;
import lombok.Getter;
@Getter
public enum Category {
    SEARCH("category.search", Fonts.ICONS_2.get(17), "U", 0x1, new SearchScreen()),
    COMBAT("category.combat", Fonts.ICONS_1.get(17), "a", 0x2, new CategoryScreen()),
    MOVEMENT("category.movement", Fonts.ICONS_1.get(17), "b", 0x3, new CategoryScreen()),
    PLAYER("category.player", Fonts.ICONS_1.get(17), "c", 0x4, new CategoryScreen()),
    RENDER("category.render", Fonts.ICONS_1.get(17), "g", 0x5, new CategoryScreen()),
    EXPLOIT("category.exploit", Fonts.ICONS_1.get(17), "a", 0x6, new CategoryScreen()),
    GHOST("category.ghost", Fonts.ICONS_1.get(17), "f", 0x7, new CategoryScreen()),
//    AI("category.ai", Fonts.ICONS_1.get(17), "e", 0x8, new AIScreen()),
    SCRIPT("category.script", Fonts.ICONS_2.get(17), "m", 0x7, new CommunityScreen()),

    THEME("category.themes", Fonts.ICONS_2.get(17), "U", 0xA, new ThemeScreen()),

    LANGUAGE("category.language", Fonts.ICONS_2.get(17), "U", 0xA, new LanguageScreen())/*,

    COMMUNITY("category.irc", Fonts.ICONS_2.get(17), "j", 0x9, new CommunityScreen())*/;

    // name of category (in case we don't use enum names)
    @Getter
    private final String name;

    // icon character
    private final String icon;

    // optional color for every specific category (module list or click gui)
    @Getter
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