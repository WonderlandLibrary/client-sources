package lunadevs.luna.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class NahrFont {
    public enum FontType {
        NORMAL, EMBOSS_TOP, EMBOSS_BOTTOM, OUTLINE_THIN, SHADOW_THICK, SHADOW_THIN
    }
    private BufferedImage bufferedImage;
    private DynamicTexture dynamicTexture;
    private final int endChar;
    private float extraSpacing = 0.0F;
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
        this(font, size, 0.0F);
    }
    
 
    public NahrFont(Object font, float size, float spacing) {
        this.fontSize = size;
        this.startChar = 32;
        this.endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[this.endChar - this.startChar];
        this.yPos = new float[this.endChar - this.startChar];
        setupGraphics2D();
        createFont(font, size);
    }
    private void createFont(Object font, float size) {
        try {
            if (font instanceof Font) {
                this.theFont = (Font) font;
            } else if (font instanceof File) {
                this.theFont = Font.createFont(0, (File) font).deriveFont(size);
            } else if (font instanceof InputStream) {
                this.theFont = Font.createFont(0, (InputStream) font).deriveFont(size);
            } else if (font instanceof String) {
                this.theFont = new Font((String) font, 0, Math.round(size));
            } else {
                this.theFont = new Font("Verdana", 0, Math.round(size));
            }
            this.theGraphics.setFont(this.theFont);
        } catch (final Exception e) {
            e.printStackTrace();
            this.theFont = new Font("Verdana", 0, Math.round(size));
        }
        this.theGraphics.setFont(this.theFont);
        this.theGraphics.setColor(new Color(255, 255, 255, 0));
        this.theGraphics.fillRect(0, 0, 256, 256);
        this.theGraphics.setColor(Color.white);
        this.theMetrics = this.theGraphics.getFontMetrics();
        float x = 5.0F;
        float y = 5.0F;
        for (int i = this.startChar; i < this.endChar; i++) {
            this.theGraphics.drawString(Character.toString((char) i), x, y + this.theMetrics.getAscent());
            this.xPos[i - this.startChar] = x;
            this.yPos[i - this.startChar] = y - this.theMetrics.getMaxDescent();
            x += this.theMetrics.stringWidth(Character.toString((char) i)) + 2.0F;
            if (x >= 250 - this.theMetrics.getMaxAdvance()) {
                x = 5.0F;
                y += this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent() + this.fontSize / 2.0F;
            }
        }
        this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("font" + font.toString() + size, this.dynamicTexture = new DynamicTexture(this.bufferedImage));
    }
    private void drawChar(char character, float x, float y) {
        final Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
        drawTexturedModalRect(x, y, this.xPos[character - this.startChar], this.yPos[character - this.startChar], (float) bounds.getWidth(), (float) bounds.getHeight() + this.theMetrics.getMaxDescent() + 1.0F);
    }
    private void drawer(String text, float x, float y, int color) {
        x *= 2.0F;
        y *= 2.0F;
        GlStateManager.func_179098_w();
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        final float alpha = (color >> 24 & 0xFF) / 255.0F;
        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);
        final float startX = x;
        for (int i = 0; i < text.length(); i++)
            if (text.charAt(i) == '\247' && i + 1 < text.length()) {
                final char oneMore = Character.toLowerCase(text.charAt(i + 1));
                if (oneMore == 'n') {
                    y += this.theMetrics.getAscent() + 2;
                    x = startX;
                }
                final int colorCode = Minecraft.getMinecraft().fontRendererObj.func_175064_b(oneMore);
                if (colorCode != 16777215)
                    GlStateManager.color((colorCode >> 16) / 255.0F, (colorCode >> 8 & 0xFF) / 255.0F, (colorCode & 0xFF) / 255.0F, alpha);
                else if (oneMore == 'r')
                    GlStateManager.color(red, green, blue, alpha);
                else if (oneMore == 'g')
                    GlStateManager.color(0.3F, 0.7F, 1.0F, alpha);
                i++;
            } else {
                char character = text.charAt(i);
                try {
                    drawChar(character, x, y);
                } catch (ArrayIndexOutOfBoundsException exception) {
                    character = '?';
                    drawChar(character, x, y);
                }
                x += getStringWidth(Character.toString(character)) * 2.0F;
            }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
    public void drawString(String text, float x, float y, int color, int shadowColor) {
        this.drawString(text, x, y, FontType.SHADOW_THICK, color, shadowColor);
    }
    public void drawString(String text, float x, float y, FontType fontType, int color) {
        this.drawString(text, x, y, fontType, color, (color & 16579836) >> 2 | color & -16777216);
    }
    public void drawString(String text, float x, float y, FontType fontType, int color, int color2) {
        text = stripUnsupported(text);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        final String colorless = stripControlCodes(text);
        switch (fontType) {
            case SHADOW_THICK:
                drawer(colorless, x + 1.0F, y + 1.0F, color2);
                break;
            case SHADOW_THIN:
                drawer(colorless, x + 0.5F, y + 0.5F, color2);
                break;
            case OUTLINE_THIN:
                drawer(colorless, x + 0.5F, y, color2);
                drawer(colorless, x - 0.5F, y, color2);
                drawer(colorless, x, y + 0.5F, color2);
                drawer(colorless, x, y - 0.5F, color2);
                break;
            case EMBOSS_BOTTOM:
                drawer(colorless, x, y + 0.5F, color2);
                break;
            case EMBOSS_TOP:
                drawer(colorless, x, y - 0.5F, color2);
                break;
            case NORMAL:
            default:
                break;
        }
        drawer(text, x, y, color);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.popMatrix();
    }
    private void drawTexturedModalRect(float x, float y, float u, float v, float width, float height) {
        final float scale = 0.0039063F;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        final Tessellator tessellator = Tessellator.getInstance();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(x + 0.0F, y + height, 0.0D, (u + 0.0F) * scale, (v + height) * scale);
        worldRenderer.addVertexWithUV(x + width, y + height, 0.0D, (u + width) * scale, (v + height) * scale);
        worldRenderer.addVertexWithUV(x + width, y + 0.0F, 0.0D, (u + width) * scale, (v + 0.0F) * scale);
        worldRenderer.addVertexWithUV(x + 0.0F, y + 0.0F, 0.0D, (u + 0.0F) * scale, (v + 0.0F) * scale);
        tessellator.draw();
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
        final int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf('\247', var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = par0Str.charAt(var2 + 1);
                if (isFormatColor(var4)) {
                    var1 = "\247" + var4;
                } else if (isFormatSpecial(var4)) {
                    var1 = var1 + "\247" + var4;
                }
            }
        }
        return var1;
    }
    public Graphics2D getGraphics() {
        return this.theGraphics;
    }
    public float getStringHeight(String text) {
        return (float) getBounds(text).getHeight() / 2.0F;
    }
    public float getStringWidth(String text) {
        return (float) (getBounds(text).getWidth() + this.extraSpacing) / 2.0F;
    }
    private boolean isFormatColor(char par0) {
        return par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F';
    }
    private boolean isFormatSpecial(char par0) {
        return par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R';
    }
    public List listFormattedStringToWidth(String s, int width) {
        return Arrays.asList(wrapFormattedStringToWidth(s, width).split("\n"));
    }
    private void setupGraphics2D() {
        this.bufferedImage = new BufferedImage(256, 256, 2);
        this.theGraphics = (Graphics2D) this.bufferedImage.getGraphics();
        this.theGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    private int sizeStringToWidth(String par1Str, float par2) {
        final int var3 = par1Str.length();
        float var4 = 0.0F;
        int var5 = 0;
        int var6 = -1;
        for (boolean var7 = false; var5 < var3; var5++) {
            final char var8 = par1Str.charAt(var5);
            switch (var8) {
                case '\n':
                    var5--;
                    break;
                case '\247':
                    if (var5 < var3 - 1) {
                        var5++;
                        final char var9 = par1Str.charAt(var5);
                        if (var9 != 'l' && var9 != 'L') {
                            if (var9 == 'r' || var9 == 'R' || isFormatColor(var9)) {
                                var7 = false;
                            }
                        } else {
                            var7 = true;
                        }
                    }
                    break;
                case ' ':
                case '-':
                case '_':
                case ':':
                    var6 = var5;
                default:
                    final String text = String.valueOf(var8);
                    var4 += getStringWidth(text);
                    if (var7) {
                        var4 += 1.0F;
                    }
                    break;
            }
            if (var8 == '\n') {
                var5++;
                var6 = var5;
            } else {
                if (var4 > par2) {
                    break;
                }
            }
        }
        return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
    }
    public String stripControlCodes(String s) {
        return this.patternControlCode.matcher(s).replaceAll("");
    }
    public String stripUnsupported(String s) {
        return this.patternUnsupported.matcher(s).replaceAll("");
    }
    public String wrapFormattedStringToWidth(String s, float width) {
        final int wrapWidth = sizeStringToWidth(s, width);
        if (s.length() <= wrapWidth)
            return s;
        final String split = s.substring(0, wrapWidth);
        final String split2 = getFormatFromString(split) + s.substring(wrapWidth + (s.charAt(wrapWidth) == ' ' || s.charAt(wrapWidth) == '\n' ? 1 : 0));
        try {
            return split + "\n" + wrapFormattedStringToWidth(split2, width);
        } catch (final Exception e) {
            System.out.println("Cannot wrap string to width.");
        }
        return "";
    }
}