/*
 * Decompiled with CFR 0.143.
 */
package me.blitzthunder.neonscreen.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import ml.teamreliant.reliant.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public final class CFontRenderer {
    private static final short IMAGE_WIDTH = 1024;
    private static final short IMAGE_HEIGHT = 1024;
    private static final byte DEFAULT_CHAR_WIDTH = 9;
    private static final byte DEFAULT_CHAR_HEIGHT = 9;
    private final int[] colorCode = new int[32];
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    private final int texID;
    private final Font font;
    private final CharData[] boldChars = new CharData[256];
    private final CharData[] italicChars = new CharData[256];
    private final CharData[] boldItalicChars = new CharData[256];
    private final CharData[] charData = new CharData[256];
    private float fontHeight = -1.0f;
    private int kerning = 1;
    private int boldTexID;
    private int italicTexID;
    private int boldItalicTexID;

    public CFontRenderer(Font font, boolean fractionalMetrics) {
        this.font = font;
        this.texID = this.setupTexture(font, fractionalMetrics, this.charData);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs(fractionalMetrics);
    }

    public float drawStringWithShadow(String text, float x, float y, int color) {
        return Math.max(this.drawString(text, x + 0.5f, y + 0.5f, color, true), this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public float drawCenteredStringNoShadow(String text, float x, float y, int color) {
        return this.drawString(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public float drawString(String text, float x, float y, int color, boolean shadow) {
        if (text == null) {
            return 0.0f;
        }
        if (color == 553648127) {
            color = 16777215;
        }
        if ((color & -67108864) == 0) {
            color |= -16777216;
        }
        if (shadow) {
            color = (color & 16579836) >> 2 | color & -16777216;
        }
        CharData[] curData = this.charData;
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        boolean randomCase = false;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        int size = text.length();
        x *= 4.0f;
        y = (y - 3.0f) * 4.0f;
        GL11.glPushMatrix();
        GL11.glScalef((float)0.25f, (float)0.25f, (float)0.25f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)((float)(color >> 16 & 255) / 255.0f), (float)((float)(color >> 8 & 255) / 255.0f), (float)((float)(color & 255) / 255.0f), (float)alpha);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)this.texID);
        for (int i = 0; i < size; ++i) {
            int ch = text.charAt(i);
            if (ch == 167 && i < size) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GL11.glBindTexture((int)3553, (int)this.texID);
                    curData = this.charData;
                    if (colorIndex < 0 || colorIndex > 15) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GL11.glColor4f((float)((float)(colorcode >> 16 & 255) / 255.0f), (float)((float)(colorcode >> 8 & 255) / 255.0f), (float)((float)(colorcode & 255) / 255.0f), (float)alpha);
                } else if (colorIndex == 16) {
                    randomCase = true;
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GL11.glBindTexture((int)3553, (int)this.boldItalicTexID);
                        curData = this.boldItalicChars;
                    } else {
                        GL11.glBindTexture((int)3553, (int)this.boldTexID);
                        curData = this.boldChars;
                    }
                } else if (colorIndex == 18) {
                    strikethrough = true;
                } else if (colorIndex == 19) {
                    underline = true;
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GL11.glBindTexture((int)3553, (int)this.boldItalicTexID);
                        curData = this.boldItalicChars;
                    } else {
                        GL11.glBindTexture((int)3553, (int)this.italicTexID);
                        curData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GL11.glColor4f((float)((float)(color >> 16 & 255) / 255.0f), (float)((float)(color >> 8 & 255) / 255.0f), (float)((float)(color & 255) / 255.0f), (float)alpha);
                    GL11.glBindTexture((int)3553, (int)this.texID);
                    curData = this.charData;
                }
                ++i;
                continue;
            }
            if (ch >= curData.length || ch < 0) continue;
            if (randomCase) {
                int newChar = 0;
                while (curData[newChar].width != curData[ch].width) {
                    newChar = (char)(Math.random() * 256.0);
                }
                ch = newChar;
            }
            GL11.glBegin((int)4);
            this.drawQuad(x, ch == 91 || ch == 93 ? y - 1.0f : y, curData[ch].width, curData[ch].height, curData[ch].storedX, curData[ch].storedY, curData[ch].width, curData[ch].height);
            GL11.glEnd();
            if (strikethrough) {
                RenderUtils.drawLine((float)x, (float)(y + curData[ch].height / 2.0f), (float)(x + (float)curData[ch].width + (float)this.kerning), (float)(y + curData[ch].height / 2.0f), (float)1.0f);
            }
            if (underline) {
                RenderUtils.drawLine((float)x, (float)(y + curData[ch].height - 2.0f), (float)(x + (float)curData[ch].width + (float)this.kerning), (float)(y + curData[ch].height - 2.0f), (float)1.0f);
            }
            x += (float)(curData[ch].width + this.kerning);
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        return x / 4.0f;
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        int size = text.length();
        for (int i = 0; i < size; ++i) {
            char ch = text.charAt(i);
            width += this.getCharWidth(ch);
        }
        return width / 4;
    }

    private int getCharWidth(char ch) {
        int width = 0;
        CharData[] curData = this.charData;
        boolean bold = false;
        boolean italic = false;
        if (ch == '\u00a7') {
            int colorIndex = "0123456789abcdefklmnor".indexOf(ch);
            if (colorIndex < 16) {
                bold = false;
                italic = false;
            } else if (colorIndex == 17) {
                bold = true;
                curData = italic ? this.boldItalicChars : this.boldChars;
            } else if (colorIndex == 20) {
                italic = true;
                curData = bold ? this.boldItalicChars : this.italicChars;
            } else if (colorIndex == 21) {
                bold = false;
                italic = false;
                curData = this.charData;
            }
        } else if (ch < curData.length && ch >= '\u0000') {
            width = curData[ch].width + this.kerning;
        }
        return width;
    }

    public String trimStringToWidth(String text, int maxWidth, boolean par3) {
        StringBuilder var4 = new StringBuilder();
        int curWidth = 0;
        int var5 = par3 ? text.length() - 1 : 0;
        int var6 = par3 ? -1 : 1;
        boolean var7 = false;
        boolean var8 = false;
        for (int var9 = var5; var9 >= 0 && var9 < text.length() && curWidth / 4 < maxWidth; var9 += var6) {
            char ch = text.charAt(var9);
            int chWidth = this.getCharWidth(ch);
            if (var7) {
                var7 = false;
                if (ch != 'l' && ch != 'L') {
                    if (ch == 'r' || ch == 'R') {
                        var8 = false;
                    }
                } else {
                    var8 = true;
                }
            } else if (chWidth < 0) {
                var7 = true;
            } else {
                curWidth += chWidth;
                if (var8) {
                    ++curWidth;
                }
            }
            if (curWidth / 4 > maxWidth) break;
            if (par3) {
                var4.insert(0, ch);
                continue;
            }
            var4.append(ch);
        }
        return var4.toString();
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; index = (int)((byte)(index + 1))) {
            int shadow = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + shadow;
            int green = (index >> 1 & 1) * 170 + shadow;
            int blue = (index >> 0 & 1) * 170 + shadow;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 7;
                green /= 7;
                blue /= 7;
            }
            this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }
    }

    private void setupBoldItalicIDs(boolean fractionalMetrics) {
        GL11.glDeleteTextures((int)this.boldTexID);
        GL11.glDeleteTextures((int)this.italicTexID);
        GL11.glDeleteTextures((int)this.boldItalicTexID);
        this.boldTexID = this.setupTexture(this.font.deriveFont(1), fractionalMetrics, this.boldChars);
        this.italicTexID = this.setupTexture(this.font.deriveFont(2), fractionalMetrics, this.italicChars);
        this.boldItalicTexID = this.setupTexture(this.font.deriveFont(3), fractionalMetrics, this.boldItalicChars);
    }

    private int setupTexture(Font font, boolean fractionalMetrics, CharData[] chars) {
        BufferedImage img = this.generateFontImage(font, fractionalMetrics, chars);
        try {
            return RenderUtils.applyTexture((int)RenderUtils.genTexture(), (BufferedImage)img, (boolean)true, (boolean)true);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private BufferedImage generateFontImage(Font font, boolean fractionalMetrics, CharData[] chars) {
        BufferedImage bufferedImage = new BufferedImage(1024, 1024, 2);
        Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, 1024, 1024);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fontMetrics = g.getFontMetrics();
        int chHeight = 0;
        int positionX = 0;
        int positionY = 0;
        for (int i = 0; i < chars.length; ++i) {
            char ch = (char)i;
            CharData chData = new CharData();
            float height = (float)fontMetrics.getHeight() / (ch == 'i' ? 1.2f : 1.0f);
            int width = fontMetrics.charWidth(ch);
            if (positionX + chData.width >= 1024) {
                positionX = 0;
                positionY += chHeight;
                chHeight = 0;
            }
            if (chData.height > (float)chHeight) {
                chHeight = (int)chData.height;
            }
            if (chData.height / 4.0f > this.fontHeight) {
                this.fontHeight = chData.height / 4.0f;
            }
            chars[i] = chData;
            g.drawString(String.valueOf(ch), positionX, positionY + fontMetrics.getAscent());
            positionX += chData.width + 1;
        }
        return bufferedImage;
    }

    private void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / 1024.0f;
        float renderSRCY = srcY / 1024.0f;
        float renderSRCWidth = srcWidth / 1024.0f;
        float renderSRCHeight = srcHeight / 1024.0f;
        GL11.glTexCoord2f((float)(renderSRCX + renderSRCWidth), (float)renderSRCY);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glTexCoord2f((float)renderSRCX, (float)renderSRCY);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2f((float)renderSRCX, (float)(renderSRCY + renderSRCHeight));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)renderSRCX, (float)(renderSRCY + renderSRCHeight));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)(renderSRCX + renderSRCWidth), (float)(renderSRCY + renderSRCHeight));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2f((float)(renderSRCX + renderSRCWidth), (float)renderSRCY);
        GL11.glVertex2d((double)(x + width), (double)y);
    }

    public int getHeight() {
        return (int)this.fontHeight;
    }

    private final class CharData {
        private float height;
        private int width;
        private int storedX;
        private int storedY;

        private CharData() {
        }
    }

}

