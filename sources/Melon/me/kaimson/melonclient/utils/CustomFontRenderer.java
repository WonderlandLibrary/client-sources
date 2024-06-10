package me.kaimson.melonclient.utils;

import java.util.regex.*;
import org.newdawn.slick.font.effects.*;
import java.io.*;
import org.newdawn.slick.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class CustomFontRenderer
{
    private static final Pattern COLOR_CODE_PATTERN;
    public final int FONT_HEIGHT = 9;
    private final int[] colorCodes;
    private final Map<String, Float> cachedStringWidth;
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor;
    private String name;
    private float size;
    
    public CustomFontRenderer(final String fontName, final float fontSize) {
        this.colorCodes = new int[] { 0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215 };
        this.cachedStringWidth = new HashMap<String, Float>();
        this.prevScaleFactor = new avr(ave.A()).e();
        this.name = fontName;
        this.size = fontSize;
        final avr resolution = new avr(ave.A());
        try {
            this.prevScaleFactor = resolution.e();
            (this.unicodeFont = new UnicodeFont(this.getFontByName(fontName).deriveFont(fontSize * this.prevScaleFactor / 2.0f))).addAsciiGlyphs();
            this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            this.unicodeFont.loadGlyphs();
        }
        catch (FontFormatException | IOException | SlickException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        this.antiAliasingFactor = (float)resolution.e();
    }
    
    public CustomFontRenderer(final Font font) {
        this(font.getFontName(), (float)font.getSize());
    }
    
    public CustomFontRenderer(final String fontName, final int fontType, final int size) {
        this(new Font(fontName, fontType, size));
    }
    
    private Font getFontByName(final String name) throws IOException, FontFormatException {
        if (name.equalsIgnoreCase("lato hairline")) {
            return this.getFontFromInput("/assets/minecraft/melonclient/fonts/Lato-Hairline.ttf");
        }
        if (name.equalsIgnoreCase("lato black")) {
            return this.getFontFromInput("/assets/minecraft/melonclient/fonts/Lato-Black.ttf");
        }
        if (name.equalsIgnoreCase("lato bold")) {
            return this.getFontFromInput("/assets/minecraft/melonclient/fonts/Lato-Bold.ttf");
        }
        return this.getFontFromInput("/assets/minecraft/melonclient/fonts/Lato-Light.ttf");
    }
    
    private Font getFontFromInput(final String path) throws IOException, FontFormatException {
        return Font.createFont(0, CustomFontRenderer.class.getResourceAsStream(path));
    }
    
    public void drawStringScaled(final String text, final int givenX, final int givenY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawString(text, 0, 0, color);
        GL11.glPopMatrix();
    }
    
    public void drawStringScaled(final String text, final float givenX, final float givenY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawString(text, 0, 0, color);
        GL11.glPopMatrix();
    }
    
    public int drawString(final String text, final int x, final int y, final int color) {
        return this.drawString(text, (float)x, (float)y, color);
    }
    
    public int drawString(final String text, float x, float y, final int color) {
        if (text == null) {
            return 0;
        }
        final avr resolution = new avr(ave.A());
        try {
            if (resolution.e() != this.prevScaleFactor) {
                this.prevScaleFactor = resolution.e();
                (this.unicodeFont = new UnicodeFont(this.getFontByName(this.name).deriveFont(this.size * this.prevScaleFactor / 2.0f))).addAsciiGlyphs();
                this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
                this.unicodeFont.loadGlyphs();
            }
        }
        catch (FontFormatException | IOException | SlickException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        this.antiAliasingFactor = (float)resolution.e();
        GL11.glPushMatrix();
        bfl.a(1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor);
        x *= this.antiAliasingFactor;
        y *= this.antiAliasingFactor;
        final float originalX = x;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        bfl.c(red, green, blue, alpha);
        int currentColor = color;
        final char[] characters = text.toCharArray();
        bfl.f();
        bfl.l();
        bfl.a(770, 771, 1, 0);
        bfl.b(770, 771);
        final String[] parts = CustomFontRenderer.COLOR_CODE_PATTERN.split(text);
        int index = 0;
        for (final String s : parts) {
            for (final String s2 : s.split("\n")) {
                for (final String s3 : s2.split("\r")) {
                    this.unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                    x += this.unicodeFont.getWidth(s3);
                    index += s3.length();
                    if (index < characters.length && characters[index] == '\r') {
                        x = originalX;
                        ++index;
                    }
                }
                if (index < characters.length && characters[index] == '\n') {
                    x = originalX;
                    y += this.getHeight(s2) * 2.0f;
                    ++index;
                }
            }
            if (index < characters.length) {
                final char colorCode = characters[index];
                if (colorCode == '§') {
                    final char colorChar = characters[index + 1];
                    final int codeIndex = "0123456789abcdef".indexOf(colorChar);
                    if (codeIndex < 0) {
                        if (colorChar == 'r') {
                            currentColor = color;
                        }
                    }
                    else {
                        currentColor = this.colorCodes[codeIndex];
                    }
                    index += 2;
                }
            }
        }
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        bfl.i(0);
        bfl.F();
        return (int)x;
    }
    
    public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(nx.a(text), x + 0.5f, y + 0.5f, 0);
        return this.drawString(text, x, y, color);
    }
    
    public void drawCenteredString(final String text, final float x, final float y, final int color) {
        this.drawString(text, x - ((int)this.getWidth(text) >> 1), y, color);
    }
    
    public void drawCenteredTextScaled(final String text, final int givenX, final int givenY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawCenteredString(text, 0.0f, 0.0f, color);
        GL11.glPopMatrix();
    }
    
    public void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawCenteredString(nx.a(text), x + 0.5f, y + 0.5f, color);
        this.drawCenteredString(text, x, y, color);
    }
    
    public float getWidth(final String text) {
        if (this.cachedStringWidth.size() > 1000) {
            this.cachedStringWidth.clear();
        }
        return this.cachedStringWidth.computeIfAbsent(text, e -> this.unicodeFont.getWidth(ChatColor.stripColor(text)) / this.antiAliasingFactor);
    }
    
    public float getCharWidth(final char c) {
        return (float)this.unicodeFont.getWidth(String.valueOf(c));
    }
    
    public float getHeight(final String s) {
        return this.unicodeFont.getHeight(s) / 2.0f;
    }
    
    public UnicodeFont getFont() {
        return this.unicodeFont;
    }
    
    public void drawSplitString(final ArrayList<String> lines, final int x, final int y, final int color) {
        this.drawString(String.join("\n\r", lines), x, y, color);
    }
    
    public List<String> splitString(final String text, final int wrapWidth) {
        final List<String> lines = new ArrayList<String>();
        final String[] splitText = text.split(" ");
        StringBuilder currentString = new StringBuilder();
        for (final String word : splitText) {
            final String potential = (Object)currentString + " " + word;
            if (this.getWidth(potential) >= wrapWidth) {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
            currentString.append(word).append(" ");
        }
        lines.add(currentString.toString());
        return lines;
    }
    
    public String trimStringToWidth(final String text, final int width) {
        return this.trimStringToWidth(text, width, false);
    }
    
    public String trimStringToWidth(final String text, final int width, final boolean reverse) {
        final StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        final int j = reverse ? (text.length() - 1) : 0;
        final int k = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag2 = false;
        for (int l = j; l >= 0 && l < text.length() && i < width; l += k) {
            final char c0 = text.charAt(l);
            final float i2 = this.getCharWidth(c0);
            if (flag) {
                flag = false;
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag2 = false;
                    }
                }
                else {
                    flag2 = true;
                }
            }
            else if (i2 < 0.0f) {
                flag = true;
            }
            else {
                i += (int)i2;
                if (flag2) {
                    ++i;
                }
            }
            if (i > width) {
                break;
            }
            if (reverse) {
                stringbuilder.insert(0, c0);
            }
            else {
                stringbuilder.append(c0);
            }
        }
        return stringbuilder.toString();
    }
    
    static {
        COLOR_CODE_PATTERN = Pattern.compile("§[0123456789abcdefklmnor]");
    }
}
