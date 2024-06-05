package de.tired.api.util.font;

import de.tired.api.performanceMode.PerformanceGui;
import de.tired.api.performanceMode.UsingType;
import de.tired.interfaces.IHook;
import de.tired.module.impl.list.visual.NameProtect;
import de.tired.tired.Tired;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CustomFont extends CFont {
    private final int[] colorCode = new int[32];
    protected CFont.CharData[] boldChars = new CFont.CharData[256];
    protected CFont.CharData[] italicChars = new CFont.CharData[256];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public int FONT_HEIGHT = PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE ? 11 : 9;

    public CustomFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public float drawStringWithColor(String text, double x, double y, int color) {
        float shadowWidth = this.drawString(text, Math.round(x + 1.0), Math.round(y + 3.0), color, true);
        return Math.max(shadowWidth, this.drawString(text, Math.round(x), Math.round(y + 2.0), color, false));
    }

    public void drawStringWithShadow2Good(String text, double x, double y, int color) {
        drawString(text, x + 1.0, y + 1, Color.BLACK.getRGB(), true);
        drawString(text, x + 1.0, y, color, false);

    }

    public void drawOutlinedString(String string, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(string), x - 0.7F, y, -16777216);
        this.drawString(StringUtils.stripControlCodes(string), x + 0.7F, y, -16777216);
        this.drawString(StringUtils.stripControlCodes(string), x, y - 0.7F, -16777216);
        this.drawString(StringUtils.stripControlCodes(string), x, y + 1.0F, -16777216);
        this.drawString(string, x, y, color);
    }

    public float drawStringWithShadow2(String text, double x, double y, int color) {
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            float shadowWidth = this.drawString(text, x + 1.0, y + 3.0, color, true);
            return Math.max(shadowWidth, this.drawString(text, x, y + 2, color, false));
        } else {
            return IHook.MC.fontRendererObj.drawStringWithShadow(text, (float) x, (float) y, color);
        }

    }

    public float drawStringWithShadow(String text, double x, double y, Color color) {
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            float shadowWidth = this.drawString(text, x + 1.0, y + 3.0, color.getRGB(), true);
            return Math.max(shadowWidth, this.drawString(text, x, y + 2, color.getRGB(), false));
        } else {
            return IHook.MC.fontRendererObj.drawStringWithShadow(text, (float) x, (float) y, color.getRGB());
        }
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            float shadowWidth = this.drawString(text, x + 1.0, y + 3.0, color, true);
            return Math.max(shadowWidth, this.drawString(text, x, y + 2, color, false));
        } else {
            return IHook.MC.fontRendererObj.drawStringWithShadow(text, (float) x, (float) y, color);
        }
    }

    public float drawString2(String text, float x, float y, int color) {
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            this.drawString(text, x, y + 2, color, false);
        } else {
            return IHook.MC.fontRendererObj.drawString(text, (float) x, (float) y + 2, color);
        }
        return 0;
    }

    public float drawString3(String text, float x, float y, int color) {
        return this.drawString(text, x, y - 2, color, false);
    }

    public float drawString(String text, float x, float y, int color) {
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            return this.drawString(text, x, y - 2, color, false);
        } else {
            return IHook.MC.fontRendererObj.drawString(text, (float) x, (float) y - 2, color);
        }
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return this.drawString(text, Math.round(x - (float) (this.getStringWidth(text) / 2)), Math.round(y), color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawString3(text, x - (float) (this.getStringWidth(text) / 2), y + 1, Integer.MIN_VALUE);
        return this.drawString3(text, x - (float) (this.getStringWidth(text) / 2), y, color);
    }

    public float drawString(String textIn, double xI, double yI, int color, boolean shadow) {
        double x = xI;
        double y = yI;
        final NameProtect nameProtect = (NameProtect) Tired.INSTANCE.moduleManager.moduleBy(NameProtect.class);
        if (nameProtect != null) {
            if (nameProtect.isState() && IHook.MC.thePlayer != null) {
                if (textIn.contains(IHook.MC.thePlayer.getName())) {
                    textIn = textIn.replaceAll(IHook.MC.thePlayer.getName(), "§c [TIRED] §f" + "User");
                }
            }
        }
        if (shadow) {
            x -= 0.5;
            y -= 0.5;
        }
        x -= 1.0;
        if (textIn == null) {
            return 0.0f;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = Color.BLACK.getRGB();
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float) (color >> 24 & 0xFF) / 255.0f;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        boolean render = true;
        x *= 2.0;
        y = (y - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.color((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, alpha);
        int size = textIn.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());
        for (int i = 0; i < size; ++i) {
            char character = textIn.charAt(i);
            if (character == '\u00a7' && i < size) {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(textIn.charAt(i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                    if (colorIndex < 0 || colorIndex > 15) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float) (colorcode >> 16 & 0xFF) / 255.0f, (float) (colorcode >> 8 & 0xFF) / 255.0f, (float) (colorcode & 0xFF) / 255.0f, alpha);
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(this.texBold.getGlTextureId());
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 18) {
                    strikethrough = true;
                } else if (colorIndex == 19) {
                    underline = true;
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                        currentData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.color((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, alpha);
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                }
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            GL11.glBegin(4);
            this.drawChar(currentData, character, (float) x, (float) y);
            GL11.glEnd();
            if (strikethrough) {
                this.drawLine(x, y + (double) (currentData[character].height / 2), x + (double) currentData[character].width - 8.0, y + (double) (currentData[character].height / 2), 1.0f);
            }
            if (underline) {
                this.drawLine(x, y + (double) currentData[character].height - 2.0, x + (double) currentData[character].width - 8.0, y + (double) currentData[character].height - 2.0, 1.0f);
            }
            x += currentData[character].width - 8 + this.charOffset;
        }
        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
        return (float) x / 2.0f;
    }

    @Override
    public int getStringWidth(String text) {

        if (text == null) {
            return 0;
        }
        if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            int width = 0;
            CFont.CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            int size = text.length();
            for (int i = 0; i < size; ++i) {
                char character = text.charAt(i);
                if (character == '\u00a7') {
                    int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                    bold = false;
                    italic = false;
                    ++i;
                    continue;
                }
                if (character >= currentData.length || character < '\u0000') continue;
                width += currentData[character].width - 8 + this.charOffset;
            }
            return width / 2;
        } else {
            return IHook.MC.fontRendererObj.getStringWidth(text);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index >> 0 & 1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }
}