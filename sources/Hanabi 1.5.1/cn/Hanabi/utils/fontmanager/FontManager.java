package cn.Hanabi.utils.fontmanager;

import cn.Hanabi.*;
import java.awt.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;

public class FontManager
{
    public static String fontName;
    private HashMap fonts;
    public UnicodeFontRenderer comfortaa10;
    public UnicodeFontRenderer comfortaa11;
    public UnicodeFontRenderer comfortaa12;
    public UnicodeFontRenderer comfortaa13;
    public UnicodeFontRenderer comfortaa15;
    public UnicodeFontRenderer comfortaa16;
    public UnicodeFontRenderer comfortaa17;
    public UnicodeFontRenderer comfortaa18;
    public UnicodeFontRenderer comfortaa20;
    public UnicodeFontRenderer comfortaa25;
    public UnicodeFontRenderer comfortaa30;
    public UnicodeFontRenderer comfortaa35;
    public UnicodeFontRenderer comfortaa40;
    public UnicodeFontRenderer comfortaa45;
    public UnicodeFontRenderer comfortaa50;
    public UnicodeFontRenderer comfortaa70;
    public UnicodeFontRenderer raleway10;
    public UnicodeFontRenderer raleway11;
    public UnicodeFontRenderer raleway12;
    public UnicodeFontRenderer raleway13;
    public UnicodeFontRenderer raleway15;
    public UnicodeFontRenderer raleway16;
    public UnicodeFontRenderer raleway17;
    public UnicodeFontRenderer raleway18;
    public UnicodeFontRenderer raleway20;
    public UnicodeFontRenderer raleway25;
    public UnicodeFontRenderer raleway30;
    public UnicodeFontRenderer raleway35;
    public UnicodeFontRenderer raleway40;
    public UnicodeFontRenderer raleway45;
    public UnicodeFontRenderer raleway50;
    public UnicodeFontRenderer raleway70;
    public UnicodeFontRenderer usans10;
    public UnicodeFontRenderer usans11;
    public UnicodeFontRenderer usans12;
    public UnicodeFontRenderer usans13;
    public UnicodeFontRenderer usans14;
    public UnicodeFontRenderer usans15;
    public UnicodeFontRenderer usans16;
    public UnicodeFontRenderer usans17;
    public UnicodeFontRenderer usans18;
    public UnicodeFontRenderer usans19;
    public UnicodeFontRenderer usans20;
    public UnicodeFontRenderer usans21;
    public UnicodeFontRenderer usans22;
    public UnicodeFontRenderer usans23;
    public UnicodeFontRenderer usans24;
    public UnicodeFontRenderer usans25;
    public UnicodeFontRenderer usans30;
    public UnicodeFontRenderer usans35;
    public UnicodeFontRenderer usans40;
    public UnicodeFontRenderer usans45;
    public UnicodeFontRenderer usans50;
    public UnicodeFontRenderer usans70;
    public UnicodeFontRenderer icon10;
    public UnicodeFontRenderer icon11;
    public UnicodeFontRenderer icon12;
    public UnicodeFontRenderer icon13;
    public UnicodeFontRenderer icon14;
    public UnicodeFontRenderer icon15;
    public UnicodeFontRenderer icon16;
    public UnicodeFontRenderer icon17;
    public UnicodeFontRenderer icon18;
    public UnicodeFontRenderer icon19;
    public UnicodeFontRenderer icon20;
    public UnicodeFontRenderer icon21;
    public UnicodeFontRenderer icon22;
    public UnicodeFontRenderer icon23;
    public UnicodeFontRenderer icon24;
    public UnicodeFontRenderer icon25;
    public UnicodeFontRenderer icon30;
    public UnicodeFontRenderer icon35;
    public UnicodeFontRenderer icon40;
    public UnicodeFontRenderer icon45;
    public UnicodeFontRenderer icon50;
    public UnicodeFontRenderer icon70;
    public UnicodeFontRenderer icon100;
    public UnicodeFontRenderer icon130;
    public UnicodeFontRenderer alt10;
    public UnicodeFontRenderer alt11;
    public UnicodeFontRenderer alt12;
    public UnicodeFontRenderer alt13;
    public UnicodeFontRenderer alt14;
    public UnicodeFontRenderer alt15;
    public UnicodeFontRenderer alt16;
    public UnicodeFontRenderer alt17;
    public UnicodeFontRenderer alt18;
    public UnicodeFontRenderer alt19;
    public UnicodeFontRenderer alt20;
    public UnicodeFontRenderer alt21;
    public UnicodeFontRenderer alt22;
    public UnicodeFontRenderer alt23;
    public UnicodeFontRenderer alt24;
    public UnicodeFontRenderer alt25;
    public UnicodeFontRenderer alt30;
    public UnicodeFontRenderer alt35;
    public UnicodeFontRenderer alt40;
    public UnicodeFontRenderer alt45;
    public UnicodeFontRenderer alt50;
    public UnicodeFontRenderer alt70;
    public UnicodeFontRenderer alt100;
    public UnicodeFontRenderer alt130;
    public UnicodeFontRenderer wqy16;
    public UnicodeFontRenderer wqy18;
    public UnicodeFontRenderer wqy25;
    
    public FontManager() {
        this.fonts = new HashMap();
    }
    
    public void initFonts() {
        this.comfortaa10 = this.getFont("comfortaa", 10.0f);
        this.comfortaa11 = this.getFont("comfortaa", 11.0f);
        this.comfortaa12 = this.getFont("comfortaa", 12.0f);
        this.comfortaa13 = this.getFont("comfortaa", 13.0f);
        this.comfortaa15 = this.getFont("comfortaa", 15.0f);
        this.comfortaa16 = this.getFont("comfortaa", 16.0f);
        this.comfortaa17 = this.getFont("comfortaa", 17.0f);
        this.comfortaa18 = this.getFont("comfortaa", 18.0f);
        this.comfortaa20 = this.getFont("comfortaa", 20.0f);
        this.comfortaa25 = this.getFont("comfortaa", 25.0f);
        this.comfortaa30 = this.getFont("comfortaa", 30.0f);
        this.comfortaa35 = this.getFont("comfortaa", 35.0f);
        this.comfortaa40 = this.getFont("comfortaa", 40.0f);
        this.comfortaa45 = this.getFont("comfortaa", 45.0f);
        this.comfortaa50 = this.getFont("comfortaa", 50.0f);
        this.comfortaa70 = this.getFont("comfortaa", 70.0f);
        this.raleway10 = this.getFont("raleway", 10.0f);
        this.raleway11 = this.getFont("raleway", 11.0f);
        this.raleway12 = this.getFont("raleway", 12.0f);
        this.raleway13 = this.getFont("raleway", 13.0f);
        this.raleway15 = this.getFont("raleway", 15.0f);
        this.raleway16 = this.getFont("raleway", 16.0f);
        this.raleway17 = this.getFont("raleway", 17.0f);
        this.raleway18 = this.getFont("raleway", 18.0f);
        this.raleway20 = this.getFont("raleway", 20.0f);
        this.raleway25 = this.getFont("raleway", 25.0f);
        this.raleway30 = this.getFont("raleway", 30.0f);
        this.raleway35 = this.getFont("raleway", 35.0f);
        this.raleway40 = this.getFont("raleway", 40.0f);
        this.raleway45 = this.getFont("raleway", 45.0f);
        this.raleway50 = this.getFont("raleway", 50.0f);
        this.raleway70 = this.getFont("raleway", 70.0f);
        this.usans10 = this.getFont("usans", 10.0f, true);
        this.usans11 = this.getFont("usans", 11.0f, true);
        this.usans12 = this.getFont("usans", 12.0f, true);
        this.usans13 = this.getFont("usans", 13.0f, true);
        this.usans14 = this.getFont("usans", 14.0f, true);
        this.usans15 = this.getFont("usans", 15.0f, true);
        this.usans16 = this.getFont("usans", 16.0f, true);
        this.usans17 = this.getFont("usans", 17.0f, true);
        this.usans18 = this.getFont("usans", 18.0f, true);
        this.usans19 = this.getFont("usans", 19.0f, true);
        this.usans20 = this.getFont("usans", 20.0f, true);
        this.usans21 = this.getFont("usans", 21.0f, true);
        this.usans22 = this.getFont("usans", 22.0f, true);
        this.usans23 = this.getFont("usans", 23.0f, true);
        this.usans24 = this.getFont("usans", 24.0f, true);
        this.usans25 = this.getFont("usans", 25.0f, true);
        this.usans30 = this.getFont("usans", 30.0f, true);
        this.usans35 = this.getFont("usans", 35.0f, true);
        this.usans40 = this.getFont("usans", 40.0f, true);
        this.usans45 = this.getFont("usans", 45.0f, true);
        this.usans50 = this.getFont("usans", 50.0f, true);
        this.usans70 = this.getFont("usans", 70.0f, true);
        this.icon10 = this.getFontWithCustomGlyph("icon", 10.0f, 59648, 59673);
        this.icon11 = this.getFontWithCustomGlyph("icon", 11.0f, 59648, 59673);
        this.icon12 = this.getFontWithCustomGlyph("icon", 12.0f, 59648, 59673);
        this.icon13 = this.getFontWithCustomGlyph("icon", 13.0f, 59648, 59673);
        this.icon14 = this.getFontWithCustomGlyph("icon", 14.0f, 59648, 59673);
        this.icon15 = this.getFontWithCustomGlyph("icon", 15.0f, 59648, 59673);
        this.icon16 = this.getFontWithCustomGlyph("icon", 16.0f, 59648, 59673);
        this.icon17 = this.getFontWithCustomGlyph("icon", 17.0f, 59648, 59673);
        this.icon18 = this.getFontWithCustomGlyph("icon", 18.0f, 59648, 59673);
        this.icon19 = this.getFontWithCustomGlyph("icon", 19.0f, 59648, 59673);
        this.icon20 = this.getFontWithCustomGlyph("icon", 20.0f, 59648, 59673);
        this.icon21 = this.getFontWithCustomGlyph("icon", 21.0f, 59648, 59673);
        this.icon22 = this.getFontWithCustomGlyph("icon", 22.0f, 59648, 59673);
        this.icon23 = this.getFontWithCustomGlyph("icon", 23.0f, 59648, 59673);
        this.icon24 = this.getFontWithCustomGlyph("icon", 24.0f, 59648, 59673);
        this.icon25 = this.getFontWithCustomGlyph("icon", 25.0f, 59648, 59673);
        this.icon30 = this.getFontWithCustomGlyph("icon", 30.0f, 59648, 59673);
        this.icon35 = this.getFontWithCustomGlyph("icon", 35.0f, 59648, 59673);
        this.icon40 = this.getFontWithCustomGlyph("icon", 40.0f, 59648, 59673);
        this.icon45 = this.getFontWithCustomGlyph("icon", 45.0f, 59648, 59673);
        this.icon50 = this.getFontWithCustomGlyph("icon", 50.0f, 59648, 59673);
        this.icon70 = this.getFontWithCustomGlyph("icon", 70.0f, 59648, 59673);
        this.icon100 = this.getFontWithCustomGlyph("icon", 100.0f, 59648, 59673);
        this.icon130 = this.getFontWithCustomGlyph("icon", 130.0f, 59648, 59673);
        this.wqy16 = this.getFontWithCJK("wqy_microhei", 16.0f);
        this.wqy18 = this.getFontWithCJK("wqy_microhei", 18.0f);
        this.wqy25 = this.getFontWithCJK("wqy_microhei", 25.0f);
        Hanabi.INSTANCE.loadFont = false;
    }
    
    public UnicodeFontRenderer getFontWithCustomGlyph(final String name, final float size, final int fontPageStart, final int fontPageEnd) {
        FontManager.fontName = name;
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(size)) {
                return this.fonts.get(name).get(size);
            }
            final InputStream inputStream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size), fontPageStart, fontPageEnd);
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(((IMinecraft)Minecraft.getMinecraft()).getLanguageManager().isCurrentLanguageBidirectional());
            final HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll(this.fonts.get(name));
            }
            map.put(size, unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return unicodeFont;
    }
    
    public UnicodeFontRenderer getFontWithCJK(final String name, final float size) {
        FontManager.fontName = name;
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(size)) {
                return this.fonts.get(name).get(size);
            }
            final InputStream inputStream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size), true);
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(((IMinecraft)Minecraft.getMinecraft()).getLanguageManager().isCurrentLanguageBidirectional());
            final HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll(this.fonts.get(name));
            }
            map.put(size, unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return unicodeFont;
    }
    
    public UnicodeFontRenderer getFont(final String name, final float size, final boolean bol) {
        FontManager.fontName = name;
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(size)) {
                return this.fonts.get(name).get(size);
            }
            final InputStream inputStream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/" + name + ".otf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(((IMinecraft)Minecraft.getMinecraft()).getLanguageManager().isCurrentLanguageBidirectional());
            final HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll(this.fonts.get(name));
            }
            map.put(size, unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return unicodeFont;
    }
    
    public UnicodeFontRenderer getFont(final String name, final float size) {
        FontManager.fontName = name;
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(size)) {
                return this.fonts.get(name).get(size);
            }
            final InputStream inputStream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(((IMinecraft)Minecraft.getMinecraft()).getLanguageManager().isCurrentLanguageBidirectional());
            final HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll(this.fonts.get(name));
            }
            map.put(size, unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return unicodeFont;
    }
    
    static {
        FontManager.fontName = "";
    }
}
