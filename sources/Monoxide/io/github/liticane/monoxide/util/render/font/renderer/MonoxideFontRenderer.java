package io.github.liticane.monoxide.util.render.font.renderer;

import de.florianmichael.rclasses.type.tuple.mutable.MutablePair;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class MonoxideFontRenderer extends FontRenderer implements Methods {
    private static int[] colorCode;
    private static final String colorCodeIdentifiers = "0123456789abcdefklmnor";
    private final Font font;
    private MonoxideFontRenderer boldFont;
    private final FontData regular = new FontData(Font.PLAIN), italic = new FontData(Font.ITALIC);

    private int fontHeight;
    private static final float KERNING = 8.2f;

    public MonoxideFontRenderer(Font font) {
        super(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.getTextureManager(), mc.isUnicode());
        generateColorCodes();
        this.font = font;
        setupTexture(regular);
        setupTexture(italic);
    }

    private void setupTexture(FontData fontData) {
        BufferedImage fakeImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) fakeImage.getGraphics();

        Font currentFont = fontData.textType == 0 ? font : font.deriveFont(fontData.textType);
        graphics.setFont(currentFont);
        handleSprites(fontData, currentFont, graphics);
    }

    public void drawLinearString(String text, double x2, float y2, int color) {
        this.drawString(text, x2, y2, color, false, true);
    }

    public void drawLinearStringWithShadow(String text, double x2, float y2, int color) {
        this.drawString(text, x2 + 0.5f, y2 + 0.5f, color, true, true);

        this.drawString(text, x2, y2, color, false, true);
    }

    @Override
    public int drawCenteredString(String name, float x, float y, int color) {
        return drawString(name, x - getStringWidthInt(name) / 2.0F, y, color);
    }

    public void drawCenteredString(String name, float x, float y, Color color) {
        drawCenteredString(name, x, y, color.getRGB());
    }

    @Override
    public int drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x - getStringWidthInt(text) / 2.0F, y, color);
    }

    @Override
    public int drawStringWithShadow(String name, float x, float y, int color) {
        drawString(name, x + .5f, y + .5f, color, true, false);
        return (int) drawString(name, x, y, color, false, false);
    }

    public void drawStringWithShadow(String name, float x, float y, Color color) {
        drawStringWithShadow(name, x, y, color.getRGB());
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean shadow) {
        if (shadow) return this.drawStringWithShadow(text, x, y, color);
        return (int) this.drawString(text, x, y, color, false, false);
    }

    @Override
    public int drawString(String name, float x, float y, int color) {
        return drawString(name, x, y, color, false);
    }

    public void drawString(String name, float x, float y, Color color) {
        drawString(name, x, y, color.getRGB(), false);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow, boolean linear) {
        y += 1f;
        x -= 0.5f;

        if (text == null) {
            return 0;
        }

        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(.5, .5, .5);
        RenderUtil.startBlend();
        RenderUtil.resetColor();
        RenderUtil.color(color);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.regular.texture.getGlTextureId());
        if (linear) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        } else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }

        float returnVal = drawCustomChars(text, x, y, color, shadow);

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GlStateManager.popMatrix();
        RenderUtil.resetColor();
        GlStateManager.bindTexture(0);
        return returnVal;
    }

    private float drawCustomChars(String text, double x, double y, int color, boolean shadow) {
        x = (x - 1) * 2;
        y = (y - 3) * 2;
        FontData currentData = this.regular;
        float alpha = (float) (color >> 24 & 255) / 255f;

        boolean bold = false, italic = false, strikethrough = false, underline = false;

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '§') {
                int colorIndex = 21;

                try {
                    colorIndex = colorCodeIdentifiers.indexOf(text.charAt(index + 1));
                } catch (Exception ignored) {

                }

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.regular.texture.getGlTextureId());
                    currentData = this.regular;

                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }

                    if (shadow) {
                        colorIndex += 16;
                    }

                    RenderUtil.color(colorCode[colorIndex], alpha);
                } else {
                    switch (colorIndex) {
                        case 17:
                            if (hasBoldFont()) {
                                bold = true;
                                if (italic) {
                                    GlStateManager.bindTexture(this.boldFont.italic.texture.getGlTextureId());
                                    currentData = this.boldFont.italic;
                                } else {
                                    GlStateManager.bindTexture(this.boldFont.regular.texture.getGlTextureId());
                                    currentData = this.boldFont.regular;
                                }
                            }
                            break;
                        case 18:
                            strikethrough = true;
                            break;
                        case 19:
                            underline = true;
                            break;
                        case 20:
                            italic = true;
                            if (bold && hasBoldFont()) {
                                GlStateManager.bindTexture(this.boldFont.italic.texture.getGlTextureId());
                                currentData = this.boldFont.italic;
                            } else {
                                GlStateManager.bindTexture(this.italic.texture.getGlTextureId());
                                currentData = this.italic;
                            }
                            break;
                        default:
                            bold = false;
                            italic = false;
                            underline = false;
                            strikethrough = false;
                            RenderUtil.color(color);
                            GlStateManager.bindTexture(this.regular.texture.getGlTextureId());
                            currentData = this.regular;
                            break;
                    }
                }

                ++index;
            } else if (character < currentData.chars.length) {

                drawLetter(x, y, currentData, strikethrough, underline, character);
                x += MathUtil.roundToHalf(currentData.chars[character].width - KERNING);
            }
        }
        return (float) (x / 2);
    }

    public void drawLetter(double x, double y, FontData currentData, boolean strikethrough, boolean underline, char character) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        CharData charData = currentData.chars[character];
        drawQuad((float) x, (float) y, charData.width, charData.height, charData.storedX, charData.storedY, currentData.imageSize.getFirst(), currentData.imageSize.getSecond());
        GL11.glEnd();

        if (strikethrough) {
            this.drawLine(x, y + (double) (charData.height / 2), x + (double) charData.width - 8, y + (double) (charData.height / 2));
        }
        if (underline) {
            this.drawLine(x + 2.5f, y + (double) charData.height - 1, x + charData.width - 6, y + (double) charData.height - 1);
        }
    }

    protected void drawQuad(float x2, float y2, float width, float height, float srcX, float srcY, float imgWidth, float imgHeight) {
        float renderSRCX = srcX / imgWidth;
        float renderSRCY = srcY / imgHeight;
        float renderSRCWidth = width / imgWidth;
        float renderSRCHeight = height / imgHeight;
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x2 + width, y2);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d(x2, y2);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x2, y2 + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x2, y2 + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x2 + width, y2 + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x2 + width, y2);
    }

    @Override
    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    @Override
    public String trimStringToWidth(String text, int width, boolean reverse) {
        if (text == null) return "";
        StringBuilder buffer = new StringBuilder();
        float lineWidth = 0.0F;
        int offset = reverse ? text.length() - 1 : 0;
        int increment = reverse ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;


        for (int index = offset; index >= 0 && index < text.length() && lineWidth < (float) width; index += increment) {
            char character = text.charAt(index);
            float charWidth = this.getCharWidthFloat(character);

            if (var8) {
                var8 = false;

                if (character != 108 && character != 76) {
                    if (character == 114 || character == 82) {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (charWidth < 0) {
                var8 = true;
            } else {
                lineWidth += charWidth;

                if (var9) {
                    ++lineWidth;
                }
            }

            if (lineWidth > (float) width) {
                break;
            }

            if (reverse) {
                buffer.insert(0, character);
            } else {
                buffer.append(character);
            }
        }

        return buffer.toString();
    }

    private float getCharWidthFloat(char c) {
        if (c == 167) {
            return -1;
        } else if (c == 32) {
            return 2;
        } else {
            int var2 = ("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■").indexOf(c);

            if (c > 0 && var2 != -1) {
                return ((regular.chars[var2].width / 2.f) - 4.f);
            } else if (c < regular.chars.length && ((regular.chars[c].width / 2.f) - 4.f) != 0) {
                int var3 = ((int) ((regular.chars[c].width / 2.f) - 4.f)) >>> 4;
                int var4 = ((int) ((regular.chars[c].width / 2.f) - 4.f)) & 15;
                var3 &= 15;
                ++var4;
                return (float) ((var4 - var3) / 2 + 1);
            } else {
                return 0;
            }
        }
    }

    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    @Override
    public float getStringWidth(String text) {
        return getStringWidth(text, KERNING);
    }

    public float getStringWidth(String text, float kerning) {
        if (text == null) {
            return 0;
        }

        float width = 0;
        CharData[] currentData = regular.chars;
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '§') {
                int colorIndex = colorCodeIdentifiers.indexOf(text.charAt(index + 1));
                switch (colorIndex) {
                    case 17:
                        if (hasBoldFont()) {
                            currentData = this.boldFont.regular.chars;
                        }
                        break;
                    case 20:
                        currentData = this.regular.chars;
                        break;
                    default:
                        currentData = regular.chars;
                        break;
                }
                ++index;
            } else if (character < currentData.length) {
                width += currentData[character].width - kerning;
            }
        }

        return width / 2;

    }

    public boolean hasBoldFont() {
        return this.boldFont != null;
    }

    private void drawLine(double x2, double y2, double x1, double y1) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth((float) 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static class FontData {
        private final CharData[] chars = new CharData[256];
        private final int textType;
        private DynamicTexture texture;
        private final MutablePair<Integer, Integer> imageSize = new MutablePair<>(512, 0);

        public FontData(int textType) {
            this.textType = textType;
        }
    }

    private static class CharData {
        private float width;
        private int height, storedX, storedY;
    }

    private void generateColorCodes() {
        if (colorCode == null) {
            colorCode = new int[32];
            for (int i = 0; i < 32; ++i) {
                final int noClue = (i >> 3 & 0x1) * 85;
                int red = (i >> 2 & 0x1) * 170 + noClue;
                int green = (i >> 1 & 0x1) * 170 + noClue;
                int blue = (i & 0x1) * 170 + noClue;
                if (i == 6) {
                    red += 85;
                }
                if (i >= 16) {
                    red /= 4;
                    green /= 4;
                    blue /= 4;
                }
                colorCode[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
            }
        }
    }

    private void handleSprites(FontData fontData, Font currentFont, Graphics2D graphics2D) {
        handleSprites(fontData, currentFont, graphics2D, false);
    }

    private void handleSprites(FontData fontData, Font currentFont, Graphics2D graphics2D, boolean drawString) {
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;
        int index = 0;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        if (drawString) {
            BufferedImage image = new BufferedImage(fontData.imageSize.getFirst(), fontData.imageSize.getSecond(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D) image.getGraphics();

            graphics.setFont(currentFont);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, fontData.imageSize.getFirst(), fontData.imageSize.getSecond());
            graphics.setColor(Color.WHITE);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (CharData data : fontData.chars) {
                char c = (char) index;
                graphics.drawString(String.valueOf(c), data.storedX + 2, data.storedY + fontMetrics.getAscent());
                index++;
            }

            fontData.texture = new DynamicTexture(image);
        } else {
            while (index < fontData.chars.length) {
                char c = (char) index;
                CharData charData = new CharData();
                Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(c), graphics2D);
                charData.width = dimensions.getBounds().width + KERNING;
                charData.height = dimensions.getBounds().height;

                if (positionX + charData.width >= fontData.imageSize.getFirst()) {
                    positionX = 0;
                    positionY += charHeight;
                    charHeight = 0;
                }

                if (charData.height > charHeight) {
                    charHeight = charData.height;
                }

                charData.storedX = positionX;
                charData.storedY = positionY;

                if (charData.height > this.fontHeight) {
                    this.fontHeight = charData.height;
                }

                fontData.chars[index] = charData;
                positionX += (int) charData.width;
                fontData.imageSize.setSecond(positionY + fontMetrics.getAscent());
                ++index;
            }

            handleSprites(fontData, currentFont, graphics2D, true);
        }
    }
}