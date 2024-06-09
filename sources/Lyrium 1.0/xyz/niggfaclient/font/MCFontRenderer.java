// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.font;

import java.util.Iterator;
import xyz.niggfaclient.Client;
import com.mojang.realmsclient.gui.ChatFormatting;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.render.Streamer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class MCFontRenderer extends CFont
{
    protected CharData[] boldChars;
    protected CharData[] italicChars;
    protected CharData[] boldItalicChars;
    private int[] colorCode;
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;
    public static Random random;
    
    public MCFontRenderer(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.boldChars = new CharData[256];
        this.italicChars = new CharData[256];
        this.boldItalicChars = new CharData[256];
        this.colorCode = new int[32];
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }
    
    public float drawString(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x, y, color, false);
    }
    
    public float drawString(final String text, final double x, final double y, final int color) {
        return this.drawString(text, x, y, color, false);
    }
    
    public float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        final float shadowWidth = this.drawString(text, x + 0.5, y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }
    
    public float drawStringWithShadow(final String text, final double x, final double y, final int color) {
        final float shadowWidth = this.drawString(text, x + 0.5, y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }
    
    public float drawCenteredString(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public float drawCenteredString(final String text, final double x, final double y, final int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public float drawCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        final float shadowWidth = this.drawString(text, x + 0.5 - this.getStringWidth(text) / 2, y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x - this.getStringWidth(text) / 2, y, color, false));
    }
    
    public float getMiddleOfBox(final float boxHeight) {
        return boxHeight / 2.0f - this.getHeight() / 2.0f;
    }
    
    public float drawString(final String text, double x, double y, int color, final boolean shadow) {
        --x;
        if (text == null) {
            return 0.0f;
        }
        if (color == 553648127) {
            color = 16777215;
        }
        if ((color & 0xFC000000) == 0x0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = ((color & 0xFCFCFC) >> 2 | (color & new Color(14, 14, 14, 150).getRGB()));
        }
        CharData[] currentData = this.charData;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        boolean randomCase = false;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        final boolean render = true;
        x *= 2.0;
        y = (y - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
        final int size = text.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(3553, this.tex.getGlTextureId());
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (String.valueOf(character).equals("§") && i < size) {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    final int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0f, (colorcode >> 8 & 0xFF) / 255.0f, (colorcode & 0xFF) / 255.0f, alpha);
                }
                else if (colorIndex == 16) {
                    randomCase = true;
                }
                else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.texBold.getGlTextureId());
                        currentData = this.boldChars;
                    }
                }
                else if (colorIndex == 18) {
                    strikethrough = true;
                }
                else if (colorIndex == 19) {
                    underline = true;
                }
                else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                        currentData = this.italicChars;
                    }
                }
                else {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    currentData = this.charData;
                    GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                }
                ++i;
            }
            else if (character < currentData.length) {
                if (randomCase) {
                    character += (char)MathUtils.getRandomInRange(0, character);
                }
                GL11.glBegin(4);
                this.drawChar(currentData, character, (float)x, (float)y);
                GL11.glEnd();
                if (strikethrough) {
                    this.drawLine(x, y + currentData[character].height / 2, x + currentData[character].width - 8.0, y + currentData[character].height / 2, 1.0f);
                }
                if (underline) {
                    this.drawLine(x, y + currentData[character].height - 2.0, x + currentData[character].width - 8.0, y + currentData[character].height - 2.0, 1.0f);
                }
                x += currentData[character].width - 8 + this.charOffset;
            }
        }
        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
        return (float)x / 2.0f;
    }
    
    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        text = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        int width = 0;
        final CharData[] currentData = this.charData;
        for (int size = text.length(), i = 0; i < size; ++i) {
            final char character = text.charAt(i);
            if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }
    
    public int getStringWidthProtected(String text) {
        if (text == null) {
            return 0;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.getName() != null && text.length() > 1 && ModuleManager.getModule(Streamer.class).isEnabled()) {
            text = text.replaceAll(mc.thePlayer.getName(), ChatFormatting.DARK_PURPLE + Client.getInstance().clientName + " User" + ChatFormatting.RESET);
            for (final String s : Streamer.PLAYERS) {
                text = text.replaceAll(s, "Player");
            }
        }
        text = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        int width = 0;
        final CharData[] currentData = this.charData;
        for (int size = text.length(), i = 0; i < size; ++i) {
            final char character = text.charAt(i);
            if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }
    
    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setAntiAlias(final boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setFractionalMetrics(final boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }
    
    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }
    
    private void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            final int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index >> 0 & 0x1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }
    
    static {
        MCFontRenderer.random = new Random();
    }
}
