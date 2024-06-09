// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import java.util.Objects;
import java.io.InputStream;
import java.awt.Font;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.Color;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.HashMap;
import org.newdawn.slick.UnicodeFont;
import java.util.Map;

public class UnicodeFontRenderer6
{
    public static UnicodeFontRenderer6 instance;
    public final int FONT_HEIGHT = 9;
    private final int[] colorCodes;
    private final float kerning;
    private final Map<String, Float> cachedStringWidth;
    private final float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    
    private UnicodeFontRenderer6(final String fontName, final int fontType, final float fontSize, final float kerning, final float antiAliasingFactor) {
        this.colorCodes = new int[32];
        this.cachedStringWidth = new HashMap<String, Float>();
        this.antiAliasingFactor = antiAliasingFactor;
        Exception e = null;
        try {
            this.unicodeFont = new UnicodeFont(this.getFontByName(fontName).deriveFont(fontSize * this.antiAliasingFactor));
        }
        catch (FontFormatException | IOException ex2) {
            final Exception ex;
            e = ex;
            e.printStackTrace();
        }
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; ++i) {
            final int shadow = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + shadow;
            int green = (i >> 1 & 0x1) * 170 + shadow;
            int blue = (i & 0x1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }
    
    private UnicodeFontRenderer6(final Font font, final float kerning, final float antiAliasingFactor) {
        this.colorCodes = new int[32];
        this.cachedStringWidth = new HashMap<String, Float>();
        this.antiAliasingFactor = antiAliasingFactor;
        this.unicodeFont = new UnicodeFont(new Font(font.getName(), font.getStyle(), (int)(font.getSize() * antiAliasingFactor)));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; ++i) {
            final int shadow = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + shadow;
            int green = (i >> 1 & 0x1) * 170 + shadow;
            int blue = (i & 0x1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }
    
    public static UnicodeFontRenderer6 getFontOnPC(final String name, final int size) {
        return getFontOnPC(name, size, 0);
    }
    
    public static UnicodeFontRenderer6 getFontOnPC(final String name, final int size, final int fontType) {
        return getFontOnPC(name, size, fontType, 0.0f);
    }
    
    public static UnicodeFontRenderer6 getFontOnPC(final String name, final int size, final int fontType, final float kerning) {
        return getFontOnPC(name, size, fontType, kerning, 3.0f);
    }
    
    public static UnicodeFontRenderer6 getFontOnPC(final String name, final int size, final int fontType, final float kerning, final float antiAliasingFactor) {
        return new UnicodeFontRenderer6(new Font(name, fontType, size), kerning, antiAliasingFactor);
    }
    
    public static UnicodeFontRenderer6 getFontFromAssets(final String name, final int size) {
        return getFontOnPC(name, size, 0);
    }
    
    public static UnicodeFontRenderer6 getFontFromAssets(final String name, final int size, final int fontType) {
        return getFontOnPC(name, fontType, size, 0.0f);
    }
    
    public static UnicodeFontRenderer6 getFontFromAssets(final String name, final int size, final float kerning, final int fontType) {
        return getFontFromAssets(name, size, fontType, kerning, 3.0f);
    }
    
    public static UnicodeFontRenderer6 getFontFromAssets(final String name, final int size, final int fontType, final float kerning, final float antiAliasingFactor) {
        return new UnicodeFontRenderer6(name, fontType, (float)size, kerning, antiAliasingFactor);
    }
    
    private Font getFontByName(final String name) throws IOException, FontFormatException {
        return this.getFontFromInput("/assets/minecraft/Aqua/fonts/" + name + ".ttf");
    }
    
    private Font getFontFromInput(final String path) throws IOException, FontFormatException {
        return Font.createFont(0, Objects.requireNonNull(UnicodeFontRenderer2.class.getResourceAsStream(path)));
    }
    
    public void drawStringScaled(final String text, final int givenX, final int givenY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawString(text, 0.0f, 0.0f, color);
        GL11.glPopMatrix();
    }
    
    public void drawStringScaledShadow(final String text, final int givenX, final int givenY, final int color, final double givenScale, final float x, final float y) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0);
        GL11.glPopMatrix();
    }
    
    public int drawString(final String text, float x, float y, final int color) {
        if (text == null) {
            return 0;
        }
        x *= 2.0f;
        y *= 2.0f;
        final float originalX = x;
        GL11.glPushMatrix();
        GlStateManager.scale(1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= this.antiAliasingFactor;
        y *= this.antiAliasingFactor;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean lighting = GL11.glIsEnabled(2896);
        final boolean texture = GL11.glIsEnabled(3553);
        final boolean alphaTest = GL11.glIsEnabled(3008);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        if (alphaTest) {
            GL11.glEnable(3008);
        }
        int currentColor = color;
        final char[] characters = text.toCharArray();
        int index = 0;
        for (final char c : characters) {
            if (c == '\r') {
                x = originalX;
            }
            if (c == '\n') {
                y += this.getHeight(Character.toString(c)) * 2.0f;
            }
            Label_0436: {
                if (c != '§' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '§')) {
                    this.unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
                    x += this.getWidth(Character.toString(c)) * 2.0f * this.antiAliasingFactor;
                }
                else if (c == ' ') {
                    x += this.unicodeFont.getSpaceWidth();
                }
                else if (c == '§' && index != characters.length - 1) {
                    final int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                    if (codeIndex < 0) {
                        break Label_0436;
                    }
                    currentColor = this.colorCodes[codeIndex];
                }
                ++index;
            }
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        if (alphaTest) {
            GL11.glEnable(3008);
        }
        GlStateManager.bindTexture(0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        return (int)x / 2;
    }
    
    public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0);
        return this.drawString(text, x, y, color);
    }
    
    public void drawCenteredString(final String text, final float x, final float y, final int color) {
        this.drawString(text, x - (int)this.getWidth(text) / 2, y, color);
    }
    
    public void drawCenteredTextScaled(final String text, final int givenX, final int givenY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        this.drawCenteredString(text, 0.0f, 0.0f, color);
        GL11.glPopMatrix();
    }
    
    public void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, color);
        this.drawCenteredString(text, x, y, color);
    }
    
    public float getWidth(final String s) {
        if (this.cachedStringWidth.size() > 1000) {
            this.cachedStringWidth.clear();
        }
        float width;
        final String str;
        final char[] array;
        int length;
        int i = 0;
        char c;
        return this.cachedStringWidth.computeIfAbsent(s, e -> {
            width = 0.0f;
            str = StringUtils.stripControlCodes(s);
            str.toCharArray();
            for (length = array.length; i < length; ++i) {
                c = array[i];
                width += this.unicodeFont.getWidth(Character.toString(c)) + this.kerning;
            }
            return width / 2.0f / this.antiAliasingFactor;
        });
    }
    
    public int getStringWidth(final String text) {
        if (EnumChatFormatting.getTextWithoutFormattingCodes(text) == null) {
            return 0;
        }
        int i = 0;
        boolean flag = false;
        for (int j = 0; j < EnumChatFormatting.getTextWithoutFormattingCodes(text).length(); ++j) {
            char c0 = EnumChatFormatting.getTextWithoutFormattingCodes(text).charAt(j);
            float k = this.getWidth(EnumChatFormatting.getTextWithoutFormattingCodes(String.valueOf(c0)));
            if (k < 0.0f && j < EnumChatFormatting.getTextWithoutFormattingCodes(text).length() - 1) {
                ++j;
                c0 = EnumChatFormatting.getTextWithoutFormattingCodes(text).charAt(j);
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag = false;
                    }
                }
                else {
                    flag = true;
                }
                k = 0.0f;
            }
            i += (int)k;
            if (flag && k > 0.0f) {
                ++i;
            }
        }
        return i;
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
    
    public String trimStringToWidth(final String par1Str, final int par2) {
        final StringBuilder builder = new StringBuilder();
        float var5 = 0.0f;
        final int var6 = 0;
        final int var7 = 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
            final char var11 = par1Str.charAt(var10);
            final float var12 = this.getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                }
                else {
                    var9 = true;
                }
            }
            else if (var12 < 0.0f) {
                var8 = true;
            }
            else {
                var5 += var12;
                if (var9) {
                    ++var5;
                }
            }
            if (var5 > par2) {
                break;
            }
            builder.append(var11);
        }
        return builder.toString();
    }
    
    public void drawSplitString(final ArrayList<String> lines, final int x, final int y, final int color) {
        this.drawString(String.join("\n\r", lines), (float)x, (float)y, color);
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
    
    public static UnicodeFontRenderer6 getInstance() {
        return UnicodeFontRenderer6.instance;
    }
}
