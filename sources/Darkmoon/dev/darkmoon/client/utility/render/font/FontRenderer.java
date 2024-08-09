package dev.darkmoon.client.utility.render.font;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.util.NameProtect;
import dev.darkmoon.client.utility.render.shader.Shader;
import dev.darkmoon.client.utility.render.ColorUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FontRenderer extends CFont {
    private static final Shader FONT_SUBSTRING = new Shader("darkmoon/shaders/font_substring.fsh", true);
    protected CFont.CharData[] boldChars = new CFont.CharData[1104];
    protected CFont.CharData[] italicChars = new CFont.CharData[1104];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[1104];
    private final int[] colorCode = new int[32];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public FontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public void drawStringWithDropShadow(String text, float x, float y, int color) {
        for (int i = 0; i < 5; i++) {
            this.drawString(text, (float) x + 0.5f * i, (float) y + 0.5f * i, new Color(0, 0, 0, 100 - i * 20).hashCode());

        }
        this.drawString(text, (float) x, (float) y, color);
    }
    public float drawString2(String text, float x, float y, int color) {
        return drawString2(text, x, y, color, false);
    }
    public float drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public float drawString(String text, double x, double y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public void drawSubstring(String text, float x, float y, int color, float maxWidth) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        FONT_SUBSTRING.useProgram();
        FONT_SUBSTRING.setupUniform4f("inColor", (color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F,
                (color & 255) / 255F, (color >> 24 & 255) / 255F);
        FONT_SUBSTRING.setupUniform1f("width", maxWidth);
        FONT_SUBSTRING.setupUniform1f("maxWidth", (x + maxWidth) * 2);
        drawString(text, x, y, color);
        FONT_SUBSTRING.unloadProgram();
        GlStateManager.disableBlend();
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < (float) width; k += j) {
            char c0 = text.charAt(k);
            float f1 = this.getStringWidthCust(Character.toString(c0));

            if (flag) {
                flag = false;

                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag1 = false;
                    }
                } else {
                    flag1 = true;
                }
            } else if (f1 < 0.0F) {
                flag = true;
            } else {
                f += f1;

                if (flag1) {
                    ++f;
                }
            }

            if (f > (float) width) {
                break;
            }

            if (reverse) {
                stringbuilder.insert(0, c0);
            } else {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }
    public float drawStringWithShadow(String text, float x, float y, int color) {
        float shadowWidth = this.drawString(text, (double) x + 0.5, (double) y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        float shadowWidth = this.drawString(text, x + 0.5, y + 0.5, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return this.drawString(text, x - (float) this.getStringWidth(text) / 2.0f, y, color);
    }

    public int drawString(String text, double x, double y, boolean shadow, int color, float scale) {
        return drawString(text, (float) x, (float) y, shadow, color, scale);
    }
    public int drawString(String text, double x, double y, int color, float scale) {
        return drawString(text, x, y, false, color, scale);
    }

    public float drawGradientCenteredString(String text, float x, float y, Color color, Color color2) {
        return this.drawGradientString(text, x - (float) this.getStringWidth(text) / 2.0f, y, color, color2);
    }

    public float drawCenteredString(String text, double x, double y, int color) {
        return this.drawString(text, x - (double) ((float) this.getStringWidth(text) / 2.0f), y, color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawString(text, (double) (x - (float) this.getStringWidth(text) / 2.0f) + 0.45, (double) y + 0.5, color, true);
        return this.drawString(text, x - (float) this.getStringWidth(text) / 2.0f, y, color);
    }

    public void drawStringWithOutline(String text, double x, double y, int color) {
        this.drawString(ChatFormatting.stripFormatting(text), x - 0.5, y, 0);
        this.drawString(ChatFormatting.stripFormatting(text), x + 0.5, y, 0);
        this.drawString(ChatFormatting.stripFormatting(text), x, y - 0.5, 0);
        this.drawString(ChatFormatting.stripFormatting(text), x, y + 0.5, 0);
        this.drawString(text, x, y, color);
    }

    public void drawCenteredStringWithOutline(String text, double x, double y, int color) {
        this.drawCenteredString(ChatFormatting.stripFormatting(text), x - 0.5, y, 0);
        this.drawCenteredString(ChatFormatting.stripFormatting(text), x + 0.5, y, 0);
        this.drawCenteredString(ChatFormatting.stripFormatting(text), x, y - 0.5, 0);
        this.drawCenteredString(ChatFormatting.stripFormatting(text), x, y + 0.5, 0);
        this.drawCenteredString(text, x, y, color);
    }

    public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
        this.drawString(text, x - (double) ((float) this.getStringWidth(text) / 2.0f) + 0.45, y + 0.5, color, true);
        return this.drawString(text, x - (double) ((float) this.getStringWidth(text) / 2.0f), y, color);
    }
    public String fixStr(String input) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = input.toCharArray();
        for (char c : buffer) {
            if ((int) c < this.charData.length && this.charData[c] != null)
                builder.append(c);
        }
        return builder.toString();
    }
    public float drawString2(String text, double x, double y, int color, boolean shadow) {
        try {
            if (text == null) {
                return 0;
            }
            x -= 1;
            text = fixStr(text);
            GL11.glColor4f(1, 1, 1, 1);
            GlStateManager.color(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_BLEND);

            if (text == null) {
                return 0;
            }

            if (color == 553648127) {
                color = 16777215;
            }

            if ((color & 0xFC000000) == 0) {
                color |= -16777216;
            }

            if (shadow) {
                color = (color & 0xFCFCFC) >> 2 | color & new Color(20, 20, 20, 200).getRGB();
            }

            float alpha = (color >> 24 & 0xFF) / 255.0F;
            x *= 2.0D;
            y = (y - 3.0D) * 2.0D;

            GL11.glPushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, alpha);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(tex.getGlTextureId());
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());
            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);
                if (String.valueOf(character).equals("§")) {
                    char next = text.charAt(i + 1);
                    int colorIndex = "0123456789abcdefklmnor".indexOf(next);
                    if (next == 'r') {
                        GlStateManager.color((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F,
                                (color & 255) / 255F, alpha);
                    } else if (colorIndex < 16) {
                        GlStateManager.bindTexture(tex.getGlTextureId());

                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        int colorcode = this.colorCode[colorIndex];
                        GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F,
                                (colorcode & 0xFF) / 255.0F, alpha);
                    }
                    i++;
                } else if (character < charData.length) {
                    GL11.glBegin(GL11.GL_TRIANGLES);
                    drawChar(charData, character, (float) x, (float) y);
                    GL11.glEnd();
                    x += charData[character].width - 8 + charOffset;
                }
            }

            GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
            GL11.glPopMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (float) x / 2;
    }
    public float drawString(String text2, double x, double y, int color, boolean shadow) {
        try {
            x -= 1.0;

            if (text2 == null) {
                return 0.0f;
            }

            if (DarkMoon.getInstance().getModuleManager() != null && DarkMoon.getInstance().getModuleManager().getModule(NameProtect.class).isEnabled() && text2.equalsIgnoreCase(Minecraft.getMinecraft().session.getUsername())) {
                text2 = NameProtect.protectedNick;
            }

            if (color == 0x20FFFFFF) {
                color = 0xFFFFFF;
            }
            if ((color & 0xFC000000) == 0) {
                color |= 0xFF000000;
            }
            if (shadow) {
                color = (color & 0xFCFCFC) >> 2 | color & new Color(20, 20, 20, 200).getRGB();
            }
            CFont.CharData[] currentData = this.charData;
            float alpha = (float) (color >> 24 & 0xFF) / 255.0f;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            x *= 2.0;
            y = (y - 3.0) * 2.0;
            GL11.glPushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, alpha);
            int size;


            size = text2.length();

            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            for (int i = 0; i < size; ++i) {
                char character;


                character = text2.charAt(i);

                if (String.valueOf(character).equals("\u00a7")) {
                    int colorIndex = 21;
                    try {


                        colorIndex = "0123456789abcdefklmnor".indexOf(text2.charAt(i + 1));

                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }
                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
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
                    this.drawLine(x, y + (double) ((float) currentData[character].height / 2.0f), x + (double) currentData[character].width - 8.0, y + (double) ((float) currentData[character].height / 2.0f), 1.0f);
                }
                if (underline) {
                    this.drawLine(x, y + (double) currentData[character].height - 2.0, x + (double) currentData[character].width - 8.0, y + (double) currentData[character].height - 2.0, 1.0f);
                }
                x += currentData[character].width - 8 + this.charOffset;
            }
            GL11.glPopMatrix();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (float) x / 2.0f;

    }

    public float drawGradientString(String text2, double x, double y, int offset, Color color1, Color color2) {
        try {
            x -= 1.0;


            if (text2 == null) {
                return 0.0f;
            }

            CFont.CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            x *= 2.0;
            y = (y - 3.0) * 2.0;
            GL11.glPushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            //GlStateManager.color((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, alpha);
            int size;


            size = text2.length();

            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            for (int i = 0; i < size; ++i) {
                Color color = ColorUtility.gradient(7, i * -(offset), color1, color2);
                ColorUtility.setColor(color);
                char character;


                character = text2.charAt(i);

                if (String.valueOf(character).equals("\u00a7")) {
                    int colorIndex = 21;
                    try {


                        colorIndex = "0123456789abcdefklmnor".indexOf(text2.charAt(i + 1));

                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }
                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }
                        int colorcode = this.colorCode[colorIndex];
                        ColorUtility.setColor(color);
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
                        ColorUtility.setColor(color);
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
                    this.drawLine(x, y + (double) ((float) currentData[character].height / 2.0f), x + (double) currentData[character].width - 8.0, y + (double) ((float) currentData[character].height / 2.0f), 1.0f);
                }
                if (underline) {
                    this.drawLine(x, y + (double) currentData[character].height - 2.0, x + (double) currentData[character].width - 8.0, y + (double) currentData[character].height - 2.0, 1.0f);
                }
                x += currentData[character].width - 8 + this.charOffset;
            }
            GL11.glPopMatrix();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (float) x / 2.0f;

    }

    public float drawGradientString(String text2, double x, double y, Color color1, Color color2) {
        try {
            x -= 1.0;


            if (text2 == null) {
                return 0.0f;
            }

            CFont.CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            x *= 2.0;
            y = (y - 3.0) * 2.0;
            GL11.glPushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            //GlStateManager.color((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, alpha);
            int size;


            size = text2.length();

            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            for (int i = 0; i < size; ++i) {
                Color color = ColorUtility.gradient(7, i * -30, color1, color2);
                ColorUtility.setColor(color);
                char character;


                character = text2.charAt(i);

                if (String.valueOf(character).equals("\u00a7")) {
                    int colorIndex = 21;
                    try {


                        colorIndex = "0123456789abcdefklmnor".indexOf(text2.charAt(i + 1));

                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }
                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }
                        int colorcode = this.colorCode[colorIndex];
                        ColorUtility.setColor(color);
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
                        ColorUtility.setColor(color);
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
                    this.drawLine(x, y + (double) ((float) currentData[character].height / 2.0f), x + (double) currentData[character].width - 8.0, y + (double) ((float) currentData[character].height / 2.0f), 1.0f);
                }
                if (underline) {
                    this.drawLine(x, y + (double) currentData[character].height - 2.0, x + (double) currentData[character].width - 8.0, y + (double) currentData[character].height - 2.0, 1.0f);
                }
                x += currentData[character].width - 8 + this.charOffset;
            }
            GL11.glPopMatrix();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (float) x / 2.0f;

    }

    @Override
    public int getStringWidth(String text) {
        if (text == null)
            return 0;

        if (DarkMoon.getInstance().getModuleManager() != null && DarkMoon.getInstance().getModuleManager().getModule(NameProtect.class).isEnabled() && text.equalsIgnoreCase(Minecraft.getMinecraft().session.getUsername())) {
            text = NameProtect.protectedNick;
        }
        int width = 0;
        CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            if ((character == '§') && (i < size)) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        currentData = this.boldItalicChars;
                    } else
                        currentData = this.boldChars;
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        currentData = this.boldItalicChars;
                    } else
                        currentData = this.italicChars;
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                i++;
            } else if ((character < currentData.length) && (character >= 0)) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }

        return width / 2;
    }

    public int getStringWidthCust(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (String.valueOf(character).equals("\u00a7") && i < size) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    currentData = italic ? this.boldItalicChars : this.boldChars;
                } else if (colorIndex == 20) {
                    italic = true;
                    currentData = bold ? this.boldItalicChars : this.italicChars;
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                ++i;
                continue;
            }
            if (character >= currentData.length || character < '\u0000') continue;
            width += currentData[character].width - 8 + this.charOffset;
        }
        return (width - this.charOffset) / 2;
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
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public List wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        if ((double) this.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            char lastColorCode = '\uffff';
            String[] var8 = words;
            int var9 = words.length;
            for (int var10 = 0; var10 < var9; ++var10) {
                String word = var8[var10];
                for (int i = 0; i < word.toCharArray().length; ++i) {
                    char c = word.toCharArray()[i];
                    if (!String.valueOf(c).equals("\u00a7") || i >= word.toCharArray().length - 1) continue;
                    lastColorCode = word.toCharArray()[i + 1];
                }
                StringBuilder stringBuilder = new StringBuilder();
                if ((double) this.getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
                    currentWord = currentWord + word + " ";
                    continue;
                }
                finalWords.add(currentWord);
                currentWord = "" + lastColorCode + word + " ";
            }
            if (currentWord.length() > 0) {
                if ((double) this.getStringWidth(currentWord) < width) {
                    finalWords.add("" + lastColorCode + currentWord + " ");
                    currentWord = "";
                } else {
                    for (Object s : this.formatString(currentWord, width)) {
                        finalWords.add((String) s);
                    }
                }
            }
        } else {
            finalWords.add(text);
        }
        return finalWords;
    }

    public List formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        String currentWord = "";
        char lastColorCode = '\uffff';
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (String.valueOf(c).equals("\u00a7") && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }
            StringBuilder stringBuilder = new StringBuilder();
            if ((double) this.getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < width) {
                currentWord = currentWord + c;
                continue;
            }
            finalWords.add(currentWord);
            currentWord = "" + lastColorCode + c;
        }
        if (currentWord.length() > 0) {
            finalWords.add(currentWord);
        }
        return finalWords;
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
