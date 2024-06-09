/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import us.amerikan.gui.UnicodeFontRenderer;

public class Fonts {
    public static UnicodeFontRenderer smallKrypton;
    public static UnicodeFontRenderer menuKrypton;
    public static UnicodeFontRenderer arrayKrypton;
    public static UnicodeFontRenderer hotbarKrypton;
    public static UnicodeFontRenderer clFont;

    public static void loadFonts() {
        InputStream is2 = Fonts.class.getResourceAsStream("fonts/Comfortaa.ttf");
        InputStream is1 = Fonts.class.getResourceAsStream("fonts/timeburnerbold.ttf");
        Font font = null;
        try {
            font = Font.createFont(0, is2);
        }
        catch (FontFormatException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        Font font1 = null;
        try {
            font1 = Font.createFont(0, is1);
        }
        catch (FontFormatException e4) {
            e4.printStackTrace();
        }
        catch (IOException e5) {
            e5.printStackTrace();
        }
        smallKrypton = new UnicodeFontRenderer(font.deriveFont(20.0f));
        menuKrypton = new UnicodeFontRenderer(font.deriveFont(150.0f));
        arrayKrypton = new UnicodeFontRenderer(font.deriveFont(40.0f));
        hotbarKrypton = new UnicodeFontRenderer(font.deriveFont(60.0f));
        clFont = new UnicodeFontRenderer(font.deriveFont(20.0f));
        if (Minecraft.getMinecraft().gameSettings.language != null) {
            menuKrypton.setUnicodeFlag(true);
            smallKrypton.setUnicodeFlag(true);
            arrayKrypton.setUnicodeFlag(true);
            hotbarKrypton.setUnicodeFlag(true);
            smallKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            menuKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            arrayKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            hotbarKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            clFont.setUnicodeFlag(true);
            clFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
        }
    }

    public static enum FontType {
        EMBOSS_BOTTOM,
        EMBOSS_TOP,
        NORMAL,
        OUTLINE_THIN,
        SHADOW_THICK,
        SHADOW_THIN;
        
    }

}

