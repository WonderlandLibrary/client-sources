package fr.dog.theme;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.ArrayList;

public class ThemeManager extends ArrayList<Theme> {
    @Setter
    @Getter
    private Theme currentTheme;

    public ThemeManager() {
        this.add(new Theme("Aubergine", EnumChatFormatting.DARK_PURPLE, new Color(170, 7, 107), new Color(97, 4, 95), false));
        this.add(new Theme("BubleGum", EnumChatFormatting.LIGHT_PURPLE, new Color(255, 27, 107), new Color(69, 202, 255), false));
        this.add(new Theme("BlueSky", EnumChatFormatting.DARK_AQUA, new Color(60,150,250), new Color(140,194,250), false));
        this.add(new Theme("Cool", EnumChatFormatting.RED, new Color(64, 58, 62), new Color(190, 88, 105), false));
        this.add(new Theme("Cherry-blossoms", EnumChatFormatting.LIGHT_PURPLE, new Color(251, 211, 233), new Color(187, 55, 125), false));
        this.add(new Theme("Dog", EnumChatFormatting.GREEN, new Color(255, 186, 0), new Color(0, 255, 216), false));
        this.add(new Theme("Express", EnumChatFormatting.DARK_PURPLE, new Color(239, 116, 92), new Color(52, 7, 61), false));
        this.add(new Theme("LightPurple", EnumChatFormatting.LIGHT_PURPLE, new Color(194, 0, 235), new Color(255, 178, 234), false));
        this.add(new Theme("Netflix", EnumChatFormatting.DARK_RED, new Color(142, 14, 0), new Color(31, 28, 24), false));
        this.add(new Theme("Opal", EnumChatFormatting.AQUA, new Color(60,150,255), new Color(209,225,229), false));
        this.add(new Theme("OpalGreen", EnumChatFormatting.GREEN, new Color(6,171,224), new Color(15,244,131), false));
        this.add(new Theme("Rainbows", EnumChatFormatting.YELLOW, new Color(0, 0, 0), new Color(0, 0, 0), true));
        this.add(new Theme("Sky", EnumChatFormatting.AQUA, new Color(160, 222, 255), new Color(202, 244, 255), false));
        this.add(new Theme("SunSet", EnumChatFormatting.YELLOW, new Color(11, 72, 107), new Color(245, 98, 23), false));
        this.add(new Theme("Spiderman", EnumChatFormatting.RED, new Color(0, 153, 247), new Color(241, 23, 18), false));
        this.add(new Theme("Tenacity", EnumChatFormatting.LIGHT_PURPLE, new Color(45, 185, 238), new Color(233, 135, 215), false));
        this.add(new Theme("Toprak", EnumChatFormatting.RED, new Color(255,30,30), new Color(255,30,30), false));
        this.add(new Theme("VisonOfGrandeur", EnumChatFormatting.BLUE, new Color(0, 0, 70), new Color(28, 181, 224), false));
        this.add(new Theme("Vice", EnumChatFormatting.AQUA, new Color(63,187,254), new Color(165,65,255), false));


        currentTheme = this.get(0);
    }

    public Theme getThemeByName(String name) {
        for (Theme t : this) {
            if (name.equalsIgnoreCase(t.getName())) {
                return t;
            }
        }
        return null;
    }
}
