/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.newFont;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFont {
    public DynamicTexture tex;
    public boolean fractionalMetrics;
    public float imgSize = Float.intBitsToFloat(9.8879379E8f ^ 0x7EEFCBC9);
    public Font font;
    public int FONT_HEIGHT;
    public int fontHeight = -1;
    public boolean antiAlias;
    public CharData[] charData = new CharData[256];
    public int charOffset = 0;

    public boolean isFractionalMetrics() {
        CFont Nigga;
        return Nigga.fractionalMetrics;
    }

    public DynamicTexture setupTexture(Font Nigga, boolean Nigga2, boolean Nigga3, CharData[] Nigga4) {
        CFont Nigga5;
        BufferedImage Nigga6 = Nigga5.generateFontImage(Nigga, Nigga2, Nigga3, Nigga4);
        try {
            return new DynamicTexture(Nigga6);
        }
        catch (Exception Nigga7) {
            Nigga7.printStackTrace();
            return null;
        }
    }

    public static {
        throw throwable;
    }

    public void drawQuad(float Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5, float Nigga6, float Nigga7, float Nigga8) {
        CFont Nigga9;
        float Nigga10 = Nigga5 / Nigga9.imgSize;
        float Nigga11 = Nigga6 / Nigga9.imgSize;
        float Nigga12 = Nigga7 / Nigga9.imgSize;
        float Nigga13 = Nigga8 / Nigga9.imgSize;
        GL11.glTexCoord2f((float)(Nigga10 + Nigga12), (float)Nigga11);
        GL11.glVertex2d((double)(Nigga + Nigga3), (double)Nigga2);
        GL11.glTexCoord2f((float)Nigga10, (float)Nigga11);
        GL11.glVertex2d((double)Nigga, (double)Nigga2);
        GL11.glTexCoord2f((float)Nigga10, (float)(Nigga11 + Nigga13));
        GL11.glVertex2d((double)Nigga, (double)(Nigga2 + Nigga4));
        GL11.glTexCoord2f((float)Nigga10, (float)(Nigga11 + Nigga13));
        GL11.glVertex2d((double)Nigga, (double)(Nigga2 + Nigga4));
        GL11.glTexCoord2f((float)(Nigga10 + Nigga12), (float)(Nigga11 + Nigga13));
        GL11.glVertex2d((double)(Nigga + Nigga3), (double)(Nigga2 + Nigga4));
        GL11.glTexCoord2f((float)(Nigga10 + Nigga12), (float)Nigga11);
        GL11.glVertex2d((double)(Nigga + Nigga3), (double)Nigga2);
    }

    public void setFont(Font Nigga) {
        CFont Nigga2;
        Nigga2.font = Nigga;
        Nigga2.tex = Nigga2.setupTexture(Nigga, Nigga2.antiAlias, Nigga2.fractionalMetrics, Nigga2.charData);
    }

    public void setAntiAlias(boolean Nigga) {
        CFont Nigga2;
        if (Nigga2.antiAlias != Nigga) {
            Nigga2.antiAlias = Nigga;
            Nigga2.tex = Nigga2.setupTexture(Nigga2.font, Nigga, Nigga2.fractionalMetrics, Nigga2.charData);
        }
    }

    public CFont(Font Nigga, boolean Nigga2, boolean Nigga3) {
        CFont Nigga4;
        Nigga4.FONT_HEIGHT = Nigga4.fontHeight * -10;
        Nigga4.font = Nigga;
        Nigga4.antiAlias = Nigga2;
        Nigga4.fractionalMetrics = Nigga3;
        Nigga4.tex = Nigga4.setupTexture(Nigga, Nigga2, Nigga3, Nigga4.charData);
    }

    public void drawChar(CharData[] Nigga, char Nigga2, float Nigga3, float Nigga4) throws ArrayIndexOutOfBoundsException {
        try {
            CFont Nigga5;
            Nigga5.drawQuad(Nigga3, Nigga4, Nigga[Nigga2].width, Nigga[Nigga2].height, Nigga[Nigga2].storedX, Nigga[Nigga2].storedY, Nigga[Nigga2].width, Nigga[Nigga2].height);
        }
        catch (Exception Nigga6) {
            Nigga6.printStackTrace();
            System.out.println(Qprot0.0("\u3a60\u71de\u0117\u576c\u057b\ubfd3\u8c6f\u5638\ua7c5\uae2b\u35f6\uaf08\ub8f2\u82c5\u715a\ua67e\u42ef\u8c58\ue7df\u1a3d\uc3b0\u0195\u2a6b\u18fb\uc292\u8728\u2f56\uef00\ub2d0"));
        }
    }

    public BufferedImage generateFontImage(Font Nigga, boolean Nigga2, boolean Nigga3, CharData[] Nigga4) {
        CFont Nigga5;
        float Nigga6 = Float.intBitsToFloat(1.08758451E9f ^ 0x7F5338DE);
        int Nigga7 = (int)Nigga5.imgSize;
        BufferedImage Nigga8 = new BufferedImage((int)((float)Nigga7 * Nigga6), (int)((float)Nigga7 * Nigga6), 2);
        Graphics2D Nigga9 = (Graphics2D)Nigga8.getGraphics();
        Nigga9.setFont(Nigga);
        Nigga9.setColor(new Color(255, 255, 255, 0));
        Nigga9.fillRect(0, 0, Nigga7, Nigga7);
        Nigga9.setColor(Color.WHITE);
        Nigga9.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, Nigga3 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        Nigga9.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, Nigga2 ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        Nigga9.setRenderingHint(RenderingHints.KEY_ANTIALIASING, Nigga2 ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics Nigga10 = Nigga9.getFontMetrics();
        int Nigga11 = 0;
        int Nigga12 = 0;
        int Nigga13 = 1;
        for (int Nigga14 = 0; Nigga14 < Nigga4.length; ++Nigga14) {
            char Nigga15 = (char)Nigga14;
            CharData Nigga16 = new CharData();
            Rectangle2D Nigga17 = Nigga10.getStringBounds(String.valueOf(Nigga15), Nigga9);
            Nigga16.width = Nigga17.getBounds().width + 8;
            Nigga16.height = Nigga17.getBounds().height;
            if (Nigga12 + Nigga16.width >= Nigga7) {
                Nigga12 = 0;
                Nigga13 += Nigga11;
                Nigga11 = 0;
            }
            if (Nigga16.height > Nigga11) {
                Nigga11 = Nigga16.height;
            }
            Nigga16.storedX = Nigga12;
            Nigga16.storedY = Nigga13;
            if (Nigga16.height > Nigga5.fontHeight) {
                Nigga5.fontHeight = Nigga16.height;
            }
            Nigga4[Nigga14] = Nigga16;
            Nigga9.drawString(String.valueOf(Nigga15), Nigga12 + 2, Nigga13 + Nigga10.getAscent());
            Nigga12 += Nigga16.width;
        }
        return Nigga8;
    }

    public void setFractionalMetrics(boolean Nigga) {
        CFont Nigga2;
        if (Nigga2.fractionalMetrics != Nigga) {
            Nigga2.fractionalMetrics = Nigga;
            Nigga2.tex = Nigga2.setupTexture(Nigga2.font, Nigga2.antiAlias, Nigga, Nigga2.charData);
        }
    }

    protected static class CharData {
        public int height;
        public int storedY;
        public int storedX;
        public int width;

        public CharData() {
            CharData Nigga;
        }

        public static {
            throw throwable;
        }
    }
}

