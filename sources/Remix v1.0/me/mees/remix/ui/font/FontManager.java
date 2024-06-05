package me.mees.remix.ui.font;

import java.awt.*;

public class FontManager
{
    public static MinecraftFontRenderer clickGuiFont;
    public static MinecraftFontRenderer nohomo;
    
    public void loadFonts() {
        FontManager.clickGuiFont = new MinecraftFontRenderer(new Font("Comfortaa", 0, 20), true, true);
        FontManager.nohomo = new MinecraftFontRenderer(new Font("Comfortaa", 0, 23), true, true);
    }
}
