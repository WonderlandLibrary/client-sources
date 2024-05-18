// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.utils;

import java.util.Arrays;
import java.util.List;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Color;
import java.io.InputStream;
import java.io.File;
import java.awt.RenderingHints;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.awt.image.BufferedImage;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Font;

public class NahrFont
{
    private final int boxSize = 512;
    private Font theFont;
    private Graphics2D theGraphics;
    private FontMetrics theMetrics;
    private int startChar;
    private int endChar;
    private float[] xPos;
    private float[] yPos;
    private BufferedImage bufferedImage;
    private BufferedImage bufferedImageBlurred;
    private float extraSpacing;
    private DynamicTexture dynamicTexture;
    private DynamicTexture dynamicTextureBlurred;
    private final Pattern patternControlCode;
    private final Pattern patternUnsupported;
    
    public NahrFont(final Font font, final float spacing, final boolean unicodeFont, final boolean createBlur) {
        this(font, font.getSize(), spacing, unicodeFont, createBlur);
    }
    
    public NahrFont(final Font font, final boolean unicodeFont, final boolean createBlur) {
        this(font, font.getSize(), 0.0f, unicodeFont, createBlur);
    }
    
    public NahrFont(final Object font, final float size, final boolean createBlur) {
        this(font, size, false, createBlur);
    }
    
    public NahrFont(final Object font, final float size, final boolean unicodeFont, final boolean createBlur) {
        this(font, size, 0.0f, unicodeFont, createBlur);
    }
    
    public NahrFont(final Object font, final float size, final float spacing, final boolean unicodeFont, final boolean createBlur) {
        this.extraSpacing = 0.0f;
        this.dynamicTextureBlurred = null;
        this.patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-ORU]");
        this.patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");
        this.startChar = 32;
        this.endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[this.endChar - this.startChar];
        this.yPos = new float[this.endChar - this.startChar];
        this.setupGraphics2D(unicodeFont);
        this.createFont(font, size, createBlur);
    }
    
    private void setupGraphics2D(final boolean unicodeFont) {
        this.bufferedImage = new BufferedImage(512, 512, 2);
        (this.theGraphics = (Graphics2D)this.bufferedImage.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (unicodeFont) {
            this.theGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
    }
    
    private void createFont(final Object font, final float size, final boolean createBlur) {
        try {
            if (font instanceof Font) {
                this.theFont = (Font)font;
            }
            else if (font instanceof File) {
                this.theFont = Font.createFont(0, (File)font).deriveFont(size);
            }
            else if (font instanceof InputStream) {
                this.theFont = Font.createFont(0, (InputStream)font).deriveFont(size);
            }
            else if (font instanceof String) {
                this.theFont = new Font((String)font, 0, Math.round(size));
            }
            else {
                this.theFont = new Font("Verdana", 0, Math.round(size));
            }
            this.theGraphics.setFont(this.theFont);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            this.theFont = new Font("Verdana", 0, Math.round(size));
            this.theGraphics.setFont(this.theFont);
        }
        this.theGraphics.setColor(new Color(255, 255, 255, 0));
        this.theGraphics.fillRect(0, 0, 512, 512);
        this.theGraphics.setColor(Color.white);
        this.theMetrics = this.theGraphics.getFontMetrics();
        float x = 2.0f;
        float y = 2.0f;
        for (int i = this.startChar; i < this.endChar; ++i) {
            this.theGraphics.drawString(Character.toString((char)i), x, y + this.theMetrics.getAscent());
            this.xPos[i - this.startChar] = x;
            this.yPos[i - this.startChar] = y - this.theMetrics.getMaxDescent();
            x += this.theMetrics.stringWidth(Character.toString((char)i)) + 5;
            if (x >= 512 - 5 - this.theMetrics.getMaxAdvance()) {
                x = 2.0f;
                y += this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent() + size / 2.0f + 2.0f;
            }
        }
        this.dynamicTexture = new DynamicTexture(this.bufferedImage);
        if (createBlur) {
            final Kernel kernel = new Kernel(3, 3, new float[] { 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f });
            final ConvolveOp convolveOp = new ConvolveOp(kernel, 1, null);
            this.bufferedImageBlurred = convolveOp.filter(this.bufferedImage, null);
            this.dynamicTextureBlurred = new DynamicTexture(this.bufferedImageBlurred);
        }
    }
    
    public void drawString(String text, final float x, final float y, final FontType fontType, final int color, final int color2) {
        text = this.stripUnsupported(text);
        GL11.glEnable(3042);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final String text2 = this.stripControlCodes(text);
        boolean skip = false;
        switch (fontType) {
            case OUTLINE_THIN: {
                this.drawer(text2, x + 0.5f, y, color2, false);
                this.drawer(text2, x - 0.5f, y, color2, false);
                this.drawer(text2, x, y + 0.5f, color2, false);
                this.drawer(text2, x, y - 0.5f, color2, false);
                break;
            }
            case SHADOW_THIN: {
                this.drawer(text2, x + 0.5f, y + 0.5f, color2, false);
                break;
            }
            case SHADOW_THICK: {
                this.drawer(text2, x + 1.0f, y + 1.0f, color2, false);
                break;
            }
            case SHADOW_BLURRED: {
                if (this.dynamicTextureBlurred != null) {
                    this.drawer(text2, x + 1.0f, y + 1.0f, color2, true);
                    break;
                }
                break;
            }
            case OVERLAY_BLUR: {
                if (this.dynamicTextureBlurred != null) {
                    skip = true;
                    this.drawer(text, x, y, color, false);
                    this.drawer(text2, x, y, color2, true);
                    break;
                }
                break;
            }
            case EMBOSS_BOTTOM: {
                this.drawer(text2, x, y + 0.5f, color2, false);
                break;
            }
            case EMBOSS_TOP: {
                this.drawer(text2, x, y - 0.5f, color2, false);
            }
        }
        if (!skip) {
            this.drawer(text, x, y, color, false);
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public void drawCenteredString(String text, final float x, final float y, final FontType fontType, final int color, final int color2) {
        text = this.stripUnsupported(text);
        GL11.glEnable(3042);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final String text2 = this.stripControlCodes(text);
        boolean skip = false;
        final float width = this.getStringWidth(text) / 2.0f;
        switch (fontType) {
            case OUTLINE_THIN: {
                this.drawer(text2, x + 0.5f - width, y, color2, false);
                this.drawer(text2, x - 0.5f - width, y, color2, false);
                this.drawer(text2, x, y + 0.5f - width, color2, false);
                this.drawer(text2, x, y - 0.5f - width, color2, false);
                break;
            }
            case SHADOW_THIN: {
                this.drawer(text2, x + 0.5f - width, y + 0.5f, color2, false);
                break;
            }
            case SHADOW_THICK: {
                this.drawer(text2, x + 1.0f - width, y + 1.0f, color2, false);
                break;
            }
            case SHADOW_BLURRED: {
                if (this.dynamicTextureBlurred != null) {
                    this.drawer(text2, x + 1.0f - width, y + 1.0f, color2, true);
                    break;
                }
                break;
            }
            case OVERLAY_BLUR: {
                if (this.dynamicTextureBlurred != null) {
                    skip = true;
                    this.drawer(text, x - width, y, color, false);
                    this.drawer(text2, x - width, y, color2, true);
                    break;
                }
                break;
            }
            case EMBOSS_BOTTOM: {
                this.drawer(text2, x - width, y + 0.5f, color2, false);
                break;
            }
            case EMBOSS_TOP: {
                this.drawer(text2, x - width, y - 0.5f, color2, false);
            }
        }
        if (!skip) {
            this.drawer(text, x - width, y, color, false);
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    private void drawer(final String text, float x, float y, final int color, final boolean useBlur) {
        x *= 2.0f;
        y *= 2.0f;
        GL11.glEnable(3553);
        if (useBlur) {
            GL11.glBindTexture(3553, this.dynamicTextureBlurred.getGlTextureId());
        }
        else {
            GL11.glBindTexture(3553, this.dynamicTexture.getGlTextureId());
        }
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        final float startX = x;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == '§' && i + 1 < text.length()) {
                final char oneMore = Character.toLowerCase(text.charAt(i + 1));
                if (oneMore == 'n') {
                    y += this.theMetrics.getAscent() + 2;
                    x = startX;
                }
                final int colorCode = "0123456789abcdefklmnoru".indexOf(oneMore);
                if (colorCode < 16 && colorCode > -1) {
                    final int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                    GL11.glColor4f((newColor >> 16) / 255.0f, (newColor >> 8 & 0xFF) / 255.0f, (newColor & 0xFF) / 255.0f, alpha);
                }
                else if (oneMore == 'f') {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                }
                else if (oneMore == 'r') {
                    GL11.glColor4f(red, green, blue, alpha);
                }
                else if (oneMore == 'u') {
                    final int newColor = -10859859;
                    GL11.glColor4f((newColor >> 16 & 0xFF) / 255.0f, (newColor >> 8 & 0xFF) / 255.0f, (newColor & 0xFF) / 255.0f, alpha);
                }
                ++i;
            }
            else {
                try {
                    final char c = text.charAt(i);
                    this.drawChar(c, x, y);
                    x += this.getStringWidth(Character.toString(c)) * 2.0f;
                }
                catch (ArrayIndexOutOfBoundsException indexException) {
                    indexException.printStackTrace();
                }
            }
        }
    }
    
    public float getStringWidth(final String text) {
        return (float)(this.getBounds(text).getWidth() + this.extraSpacing) / 2.0f;
    }
    
    public float getStringHeight(final String text) {
        return (float)this.getBounds(text).getHeight() / 2.0f;
    }
    
    private Rectangle2D getBounds(final String text) {
        return this.theMetrics.getStringBounds(text, this.theGraphics);
    }
    
    public final float getHeight() {
        return this.theMetrics.getHeight() + this.theMetrics.getDescent() + 3;
    }
    
    private void drawChar(final char character, final float x, final float y) {
        if (character >= this.startChar && character <= this.endChar) {
            final Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
            this.drawTexturedModalRect(x, y - this.theMetrics.getAscent() / 2, this.xPos[character - this.startChar], this.yPos[character - this.startChar], bounds.getWidth(), this.getHeight());
        }
    }
    
    public List listFormattedStringToWidth(final String s, final int width) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, width).split("\n"));
    }
    
    String wrapFormattedStringToWidth(final String s, final float width) {
        final int wrapWidth = this.sizeStringToWidth(s, width);
        if (s.length() <= wrapWidth) {
            return s;
        }
        final String split = s.substring(0, wrapWidth);
        final String split2 = String.valueOf(this.getFormatFromString(split)) + s.substring(wrapWidth + ((s.charAt(wrapWidth) == ' ' || s.charAt(wrapWidth) == '\n') ? 1 : 0));
        return String.valueOf(split) + "\n" + this.wrapFormattedStringToWidth(split2, width);
    }
    
    private int sizeStringToWidth(final String par1Str, final float par2) {
        final int var3 = par1Str.length();
        float var4 = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            final char var8 = par1Str.charAt(var5);
            Label_0207: {
                switch (var8) {
                    case '\n': {
                        --var5;
                        break Label_0207;
                    }
                    case '§': {
                        if (var5 >= var3 - 1) {
                            break Label_0207;
                        }
                        ++var5;
                        final char var9 = par1Str.charAt(var5);
                        if (var9 == 'l' || var9 == 'L') {
                            var7 = true;
                            break Label_0207;
                        }
                        if (var9 == 'r' || var9 == 'R' || this.isFormatColor(var9)) {
                            var7 = false;
                        }
                        break Label_0207;
                    }
                    case ' ': {
                        var6 = var5;
                    }
                    case '-': {
                        var6 = var5;
                    }
                    case '_': {
                        var6 = var5;
                    }
                    case ':': {
                        var6 = var5;
                        break;
                    }
                }
                final String text = String.valueOf(var8);
                var4 += this.getStringWidth(text);
                if (var7) {
                    ++var4;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > par2) {
                break;
            }
            ++var5;
        }
        return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    private String getFormatFromString(final String par0Str) {
        String var1 = "";
        int var2 = -1;
        final int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = par0Str.charAt(var2 + 1);
                if (this.isFormatColor(var4)) {
                    var1 = "§" + var4;
                }
                else {
                    if (!this.isFormatSpecial(var4)) {
                        continue;
                    }
                    var1 = String.valueOf(var1) + "§" + var4;
                }
            }
        }
        return var1;
    }
    
    private boolean isFormatColor(final char par0) {
        return (par0 >= '0' && par0 <= '9') || (par0 >= 'a' && par0 <= 'f') || (par0 >= 'A' && par0 <= 'F');
    }
    
    private boolean isFormatSpecial(final char par0) {
        return (par0 >= 'k' && par0 <= 'o') || (par0 >= 'K' && par0 <= 'O') || par0 == 'r' || par0 == 'R';
    }
    
    private void drawTexturedModalRect(final double x, final double y, final double u, final double v, final double width, final double height) {
        final double scale = 1.0 / 512.0;
        GL11.glBegin(7);
        GL11.glTexCoord2d(u * scale, v * scale);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(u * scale, (v + height) * scale);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2d((u + width) * scale, (v + height) * scale);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2d((u + width) * scale, v * scale);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
    }
    
    public String stripControlCodes(final String s) {
        return this.patternControlCode.matcher(s).replaceAll("");
    }
    
    public String stripUnsupported(final String s) {
        return this.patternUnsupported.matcher(s).replaceAll("");
    }
    
    public Graphics2D getGraphics() {
        return this.theGraphics;
    }
    
    public Font getFont() {
        return this.theFont;
    }
    
    public enum FontType
    {
        PLAIN("PLAIN", 0), 
        SHADOW_BLURRED("SHADOW_BLURRED", 1), 
        SHADOW_THICK("SHADOW_THICK", 2), 
        SHADOW_THIN("SHADOW_THIN", 3), 
        OUTLINE_THIN("OUTLINE_THIN", 4), 
        EMBOSS_TOP("EMBOSS_TOP", 5), 
        EMBOSS_BOTTOM("EMBOSS_BOTTOM", 6), 
        OVERLAY_BLUR("OVERLAY_BLUR", 7);
        
        private FontType(final String s, final int n) {
        }
    }
}
