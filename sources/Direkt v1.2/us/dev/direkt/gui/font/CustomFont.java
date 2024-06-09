package us.dev.direkt.gui.font;

import us.dev.direkt.module.internal.core.ui.InGameUI;

public class CustomFont {
	public static CustomFont INSANCE;
    public StringCache stringCache;
    String fontName;
    private int[] colorCode;

    CustomFont() {
    	INSANCE = this;
    }

    public CustomFont(String fontName, int fontSize) {
        this.setFont(fontName, fontSize);
    }

    public CustomFont(String fontName) {
        this.setFont(fontName);
    }

    public void initialize(String par1Str, int[] colorCode) {
        this.colorCode = colorCode;

        if (par1Str.equals("textures/font/ascii.png") && this.stringCache == null) {
            this.stringCache = new StringCache(colorCode);
            this.stringCache.setDefaultFont(fontName, 18, true);
        }
    }

    public void setFont(String fontName, boolean isAA, boolean enableFont) {
        this.stringCache = new StringCache(this.colorCode);
        this.stringCache.setDefaultFont(fontName, 18, isAA);
        this.fontName = fontName;

        enableFont = FontManager.usingCustomFont;
    }

    public void setFont(String fontName) {
        this.setFont(fontName, 18);
    }

    public void setFont(String fontName, int fontSize) {
        this.stringCache = new StringCache(this.colorCode);
        this.stringCache.setDefaultFont(fontName, fontSize, true);
        this.fontName = fontName;
        InGameUI.sorted = null;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void refresh() {
        this.setFont(fontName);
    }

    public int renderString(String str, float posX, float posY, int color, boolean shadow) {
        return this.stringCache.renderString(str, posX, posY, color, shadow);
    }

    public int renderStringWithShadow(String str, int posX, int posY, int color) {
        int sColor = (color & 16579836) >> 2 | color & -16777216;
        int var6 = this.renderString(str, posX + 0.5F, posY + 0.5F, sColor, true);
        var6 = Math.max(var6, this.renderString(str, posX, posY, color, false));
        return var6;
    }

    public int getStringWidth(String str) {
        return this.stringCache.getStringWidth(str);
    }

    public String stripColorCodes(String original) {
        String colorCode = "0123456789abcdefklmnor";
        for (int x = 0; x < colorCode.length(); x++)
            original = original.replaceAll("\247" + colorCode.indexOf(x), "");
        return original;
    }
}