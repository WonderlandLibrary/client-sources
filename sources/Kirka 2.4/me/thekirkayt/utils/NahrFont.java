/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NahrFont {
    private BufferedImage bufferedImage;
    private DynamicTexture dynamicTexture;
    private final int endChar;
    private float extraSpacing = 0.0f;
    private final float fontSize;
    private final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OG]");
    private final Pattern patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");
    private ResourceLocation resourceLocation;
    private final int startChar;
    private Font theFont;
    private Graphics2D theGraphics;
    private FontMetrics theMetrics;
    private final float[] xPos;
    private final float[] yPos;

    public NahrFont(Object font, float size) {
        this(font, size, 0.0f);
    }

    public NahrFont(Object font, float size, float spacing) {
        this.fontSize = size;
        this.startChar = 32;
        this.endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[this.endChar - this.startChar];
        this.yPos = new float[this.endChar - this.startChar];
        this.setupGraphics2D();
        this.createFont(font, size);
    }

    private void createFont(Object font, float size) {
        DynamicTexture dynamicTexture;
        try {
            this.theFont = font instanceof Font ? (Font)font : (font instanceof File ? Font.createFont(0, (File)font).deriveFont(size) : (font instanceof InputStream ? Font.createFont(0, (InputStream)font).deriveFont(size) : (font instanceof String ? new Font((String)font, 0, Math.round(size)) : new Font("Verdana", 0, Math.round(size)))));
            this.theGraphics.setFont(this.theFont);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.theFont = new Font("Verdana", 0, Math.round(size));
            this.theGraphics.setFont(this.theFont);
        }
        this.theGraphics.setColor(new Color(255, 255, 255, 0));
        this.theGraphics.fillRect(0, 0, 256, 256);
        this.theGraphics.setColor(Color.white);
        this.theMetrics = this.theGraphics.getFontMetrics();
        float x = 5.0f;
        float y = 5.0f;
        for (int i = this.startChar; i < this.endChar; ++i) {
            this.theGraphics.drawString(Character.toString((char)i), x, y + (float)this.theMetrics.getAscent());
            this.xPos[i - this.startChar] = x;
            this.yPos[i - this.startChar] = y - (float)this.theMetrics.getMaxDescent();
            x += (float)this.theMetrics.stringWidth(Character.toString((char)i)) + 2.0f;
            if (!(x >= (float)(250 - this.theMetrics.getMaxAdvance()))) continue;
            x = 5.0f;
            y += (float)(this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent()) + this.fontSize / 2.0f;
        }
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        String string = "font" + font.toString() + size;
        this.dynamicTexture = dynamicTexture = new DynamicTexture(this.bufferedImage);
        this.resourceLocation = textureManager.getDynamicTextureLocation(string, dynamicTexture);
    }

    private void drawChar(char character, float x, float y) throws ArrayIndexOutOfBoundsException {
        Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
        this.drawTexturedModalRect(x, y, this.xPos[character - this.startChar], this.yPos[character - this.startChar], (float)bounds.getWidth() + 1.0f, (float)bounds.getHeight() + (float)this.theMetrics.getMaxDescent() + 1.0f);
    }

    private void drawer(String text, float x, float y, int color) {
        y *= 2.0f;
        GL11.glEnable((int)3553);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        float startX = x *= 2.0f;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == '\u00a7' && i + 1 < text.length()) {
                int colorCode;
                char oneMore = Character.toLowerCase(text.charAt(i + 1));
                if (oneMore == 'n') {
                    y += (float)(this.theMetrics.getAscent() + 2);
                    x = startX;
                }
                if ((colorCode = "0123456789abcdefklmnorg".indexOf(oneMore)) < 16) {
                    try {
                        int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                        GL11.glColor4f((float)((float)(newColor >> 16) / 255.0f), (float)((float)(newColor >> 8 & 255) / 255.0f), (float)((float)(newColor & 255) / 255.0f), (float)alpha);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (oneMore == 'f') {
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
                } else if (oneMore == 'r') {
                    GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
                } else if (oneMore == 'g') {
                    GL11.glColor4f((float)0.3f, (float)0.7f, (float)1.0f, (float)alpha);
                }
                ++i;
                continue;
            }
            try {
                char c = text.charAt(i);
                this.drawChar(c, x, y);
                x += this.getStringWidth(Character.toString(c)) * 2.0f;
                continue;
            }
            catch (ArrayIndexOutOfBoundsException indexException) {
                text.charAt(i);
            }
        }
    }

    public void drawString(String text, float x, float y, FontType fontType, int color, int color2) {
        text = this.stripUnsupported(text);
        GL11.glEnable((int)3042);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        String text2 = this.stripControlCodes(text);
        switch (fontType.ordinal()) {
            case 4: {
                this.drawer(text2, x + 1.0f, y + 1.0f, color2);
                break;
            }
            case 5: {
                this.drawer(text2, x + 0.5f, y + 0.5f, color2);
                break;
            }
            case 3: {
                this.drawer(text2, x + 0.5f, y, color2);
                this.drawer(text2, x - 0.5f, y, color2);
                this.drawer(text2, x, y + 0.5f, color2);
                this.drawer(text2, x, y - 0.5f, color2);
                break;
            }
            case 2: {
                this.drawer(text2, x, y + 0.5f, color2);
                break;
            }
            case 1: {
                this.drawer(text2, x, y - 0.5f, color2);
            }
        }
        this.drawer(text, x, y, color);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    private void drawTexturedModalRect(float x, float y, float u, float v, float width, float height) {
        float scale = 0.0039063f;
        WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
        Tessellator tesselator = Tessellator.instance;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(x + 0.0f, y + height, 0.0, (u + 0.0f) * 0.0039063f, (v + height) * 0.0039063f);
        worldRenderer.addVertexWithUV(x + width, y + height, 0.0, (u + width) * 0.0039063f, (v + height) * 0.0039063f);
        worldRenderer.addVertexWithUV(x + width, y + 0.0f, 0.0, (u + width) * 0.0039063f, (v + 0.0f) * 0.0039063f);
        worldRenderer.addVertexWithUV(x + 0.0f, y + 0.0f, 0.0, (u + 0.0f) * 0.0039063f, (v + 0.0f) * 0.0039063f);
        tesselator.draw();
    }

    private Rectangle2D getBounds(String text) {
        return this.theMetrics.getStringBounds(text, this.theGraphics);
    }

    public Font getFont() {
        return this.theFont;
    }

    private String getFormatFromString(String par0Str) {
        String var1 = "";
        int var2 = -1;
        int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 >= var3 - 1) continue;
            char var4 = par0Str.charAt(var2 + 1);
            if (this.isFormatColor(var4)) {
                var1 = "\u00a7" + var4;
                continue;
            }
            if (!this.isFormatSpecial(var4)) continue;
            var1 = String.valueOf(String.valueOf(var1)) + "\u00a7" + var4;
        }
        return var1;
    }

    public Graphics2D getGraphics() {
        return this.theGraphics;
    }

    public float getStringHeight(String text) {
        return (float)this.getBounds(text).getHeight() / 2.0f;
    }

    public float getStringWidth(String text) {
        return (float)(this.getBounds(text).getWidth() + (double)this.extraSpacing) / 2.0f;
    }

    private boolean isFormatColor(char par0) {
        return par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F';
    }

    private boolean isFormatSpecial(char par0) {
        return par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R';
    }

    public List listFormattedStringToWidth(String s, int width) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, width).split("\n"));
    }

    private void setupGraphics2D() {
        this.bufferedImage = new BufferedImage(256, 256, 2);
        this.theGraphics = (Graphics2D)this.bufferedImage.getGraphics();
        this.theGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private int sizeStringToWidth(String par1Str, float par2) {
        int var3 = par1Str.length();
        float var2 = 0.0f;
        int var4 = -1;
        boolean var5 = false;
        for (var3 = 0; var3 < var3; ++var3) {
            char var6 = par1Str.charAt(var3);
            switch (var6) {
                case '\n': {
                    --var3;
                    break;
                }
                case '\u00a7': {
                    char var7;
                    if (var3 >= var3 - 1) break;
                    if ((var7 = par1Str.charAt(++var3)) == 'l' || var7 == 'L') {
                        var5 = true;
                        break;
                    }
                    if (var7 != 'r' && var7 != 'R' && !this.isFormatColor(var7)) break;
                    var5 = false;
                    break;
                }
                case ' ': {
                    var4 = var3;
                }
                case '-': {
                    var4 = var3;
                }
                case '_': {
                    var4 = var3;
                }
                case ':': {
                    var4 = var3;
                }
                default: {
                    String text = String.valueOf(var6);
                    var2 += this.getStringWidth(text);
                    if (!var5) break;
                    var2 += 1.0f;
                }
            }
            if (var6 == '\n') {
                var4 = ++var3;
                continue;
            }
            if (var2 > par2) break;
        }
        return var3 != var3 && var4 != -1 && var4 < var3 ? var4 : var3;
    }

    public String stripControlCodes(String s) {
        return this.patternControlCode.matcher(s).replaceAll("");
    }

    public String stripUnsupported(String s) {
        return this.patternUnsupported.matcher(s).replaceAll("");
    }

    public String wrapFormattedStringToWidth(String s, float width) {
        int wrapWidth = this.sizeStringToWidth(s, width);
        if (s.length() <= wrapWidth) {
            return s;
        }
        String split = s.substring(0, wrapWidth);
        String split2 = String.valueOf(String.valueOf(this.getFormatFromString(split))) + s.substring(wrapWidth + (s.charAt(wrapWidth) == ' ' || s.charAt(wrapWidth) == '\n' ? 1 : 0));
        try {
            return String.valueOf(String.valueOf(split)) + "\n" + this.wrapFormattedStringToWidth(split2, width);
        }
        catch (Exception e) {
            System.out.println("Cannot wrap string to width.");
            return "";
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

