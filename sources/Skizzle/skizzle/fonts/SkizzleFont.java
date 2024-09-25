/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class SkizzleFont {
    public int IMAGE_WIDTH;
    public int IMAGE_HEIGHT;
    public int texID;
    public boolean antiAlias;
    public IntObject[] chars;
    public int charOffset;
    public int fontHeight;
    public Font font;

    public int getHeight() {
        SkizzleFont Nigga;
        return (Nigga.fontHeight - Nigga.charOffset) / 2;
    }

    public static {
        throw throwable;
    }

    public void drawQuad(float Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5, float Nigga6, float Nigga7, float Nigga8) {
        SkizzleFont Nigga9;
        float Nigga10 = Nigga5 / (float)Nigga9.IMAGE_WIDTH;
        float Nigga11 = Nigga6 / (float)Nigga9.IMAGE_HEIGHT;
        float Nigga12 = Nigga7 / (float)Nigga9.IMAGE_WIDTH;
        float Nigga13 = Nigga8 / (float)Nigga9.IMAGE_HEIGHT;
        GL11.glBegin((int)4);
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
        GL11.glEnd();
    }

    public boolean isAntiAlias() {
        SkizzleFont Nigga;
        return Nigga.antiAlias;
    }

    public void drawString(String Nigga, double Nigga2, double Nigga3, Color Nigga4, boolean Nigga5) {
        SkizzleFont Nigga6;
        Nigga2 *= 2.0;
        Nigga3 = Nigga3 * 2.0 - 2.0;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.0, (double)0.0, (double)0.0);
        TextureUtil.bindTexture(Nigga6.texID);
        Nigga6.glColor(Nigga5 ? new Color(Float.intBitsToFloat(1.10755418E9f ^ 0x7F4F23B2), Float.intBitsToFloat(1.11371315E9f ^ 0x7F2D26CD), Float.intBitsToFloat(1.13225971E9f ^ 0x7E302583), (float)Nigga4.getAlpha() / Float.intBitsToFloat(1.03666656E9f ^ 0x7EB546A5)) : Nigga4);
        int Nigga7 = Nigga.length();
        for (int Nigga8 = 0; Nigga8 < Nigga7; ++Nigga8) {
            char Nigga9 = Nigga.charAt(Nigga8);
            if (Nigga9 >= Nigga6.chars.length || Nigga9 < '\u0000') continue;
            Nigga6.drawChar(Nigga9, (float)Nigga2, (float)Nigga3);
            Nigga2 += (double)(Nigga6.chars[Nigga9].width - Nigga6.charOffset);
        }
        GL11.glPopMatrix();
    }

    public Font getFont() {
        SkizzleFont Nigga;
        return Nigga.font;
    }

    public void setAntiAlias(boolean Nigga) {
        SkizzleFont Nigga2;
        if (Nigga2.antiAlias != Nigga) {
            Nigga2.antiAlias = Nigga;
            Nigga2.setupTexture(Nigga);
        }
    }

    public void glColor(Color Nigga) {
        float Nigga2 = (float)Nigga.getRed() / Float.intBitsToFloat(1.040016E9f ^ 0x7E826293);
        float Nigga3 = (float)Nigga.getGreen() / Float.intBitsToFloat(1.0111401E9f ^ 0x7F3BC61F);
        float Nigga4 = (float)Nigga.getBlue() / Float.intBitsToFloat(1.00747744E9f ^ 0x7F73E2B1);
        float Nigga5 = (float)Nigga.getAlpha() / Float.intBitsToFloat(1.02911994E9f ^ 0x7E281FBF);
        GL11.glColor4f((float)Nigga2, (float)Nigga3, (float)Nigga4, (float)Nigga5);
    }

    public void setupTexture(boolean Nigga) {
        SkizzleFont Nigga2;
        if (Nigga2.font.getSize() <= 15) {
            Nigga2.IMAGE_WIDTH = 256;
            Nigga2.IMAGE_HEIGHT = 256;
        }
        if (Nigga2.font.getSize() <= 43) {
            Nigga2.IMAGE_WIDTH = 512;
            Nigga2.IMAGE_HEIGHT = 512;
        } else if (Nigga2.font.getSize() <= 91) {
            Nigga2.IMAGE_WIDTH = 1024;
            Nigga2.IMAGE_HEIGHT = 1024;
        } else {
            Nigga2.IMAGE_WIDTH = 2048;
            Nigga2.IMAGE_HEIGHT = 2048;
        }
        BufferedImage Nigga3 = new BufferedImage(Nigga2.IMAGE_WIDTH, Nigga2.IMAGE_HEIGHT, 2);
        Graphics2D Nigga4 = (Graphics2D)Nigga3.getGraphics();
        Nigga4.setFont(Nigga2.font);
        Nigga4.setColor(new Color(255, 255, 255, 0));
        Nigga4.fillRect(0, 0, Nigga2.IMAGE_WIDTH, Nigga2.IMAGE_HEIGHT);
        Nigga4.setColor(Color.white);
        int Nigga5 = 0;
        int Nigga6 = 0;
        int Nigga7 = 0;
        for (int Nigga8 = 0; Nigga8 < 2048; ++Nigga8) {
            char Nigga9 = (char)Nigga8;
            BufferedImage Nigga10 = Nigga2.getFontImage(Nigga9, Nigga);
            IntObject Nigga11 = new IntObject(Nigga2, null);
            Nigga11.width = Nigga10.getWidth();
            Nigga11.height = Nigga10.getHeight();
            if (Nigga6 + Nigga11.width >= Nigga2.IMAGE_WIDTH) {
                Nigga6 = 0;
                Nigga7 += Nigga5;
                Nigga5 = 0;
            }
            Nigga11.storedX = Nigga6;
            Nigga11.storedY = Nigga7;
            if (Nigga11.height > Nigga2.fontHeight) {
                Nigga2.fontHeight = Nigga11.height;
            }
            if (Nigga11.height > Nigga5) {
                Nigga5 = Nigga11.height;
            }
            Nigga2.chars[Nigga8] = Nigga11;
            Nigga4.drawImage((Image)Nigga10, Nigga6, Nigga7, null);
            Nigga6 += Nigga11.width;
        }
        try {
            Nigga2.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), Nigga3, true, true);
        }
        catch (NullPointerException Nigga12) {
            Nigga12.printStackTrace();
        }
    }

    public SkizzleFont(Font Nigga, boolean Nigga2, int Nigga3) {
        SkizzleFont Nigga4;
        Nigga4.IMAGE_WIDTH = 1024;
        Nigga4.IMAGE_HEIGHT = 1024;
        Nigga4.chars = new IntObject[2048];
        Nigga4.fontHeight = -1;
        Nigga4.charOffset = 8;
        Nigga4.font = Nigga;
        Nigga4.antiAlias = Nigga2;
        Nigga4.charOffset = Nigga3;
        Nigga4.setupTexture(Nigga2);
    }

    public BufferedImage getFontImage(char Nigga, boolean Nigga2) {
        int Nigga3;
        SkizzleFont Nigga4;
        BufferedImage Nigga5 = new BufferedImage(1, 1, 2);
        Graphics2D Nigga6 = (Graphics2D)Nigga5.getGraphics();
        if (Nigga2) {
            Nigga6.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            Nigga6.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        Nigga6.setFont(Nigga4.font);
        FontMetrics Nigga7 = Nigga6.getFontMetrics();
        int Nigga8 = Nigga7.charWidth(Nigga) + 8;
        if (Nigga8 <= 0) {
            Nigga8 = 7;
        }
        if ((Nigga3 = Nigga7.getHeight() + 3) <= 0) {
            Nigga3 = Nigga4.font.getSize();
        }
        BufferedImage Nigga9 = new BufferedImage(Nigga8, Nigga3, 2);
        Graphics2D Nigga10 = (Graphics2D)Nigga9.getGraphics();
        if (Nigga2) {
            Nigga10.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            Nigga10.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        Nigga10.setFont(Nigga4.font);
        Nigga10.setColor(Color.WHITE);
        Nigga10.drawString(String.valueOf(Nigga), 3, 1 + Nigga7.getAscent());
        return Nigga9;
    }

    public SkizzleFont(Font Nigga, boolean Nigga2) {
        SkizzleFont Nigga3;
        Nigga3.IMAGE_WIDTH = 1024;
        Nigga3.IMAGE_HEIGHT = 1024;
        Nigga3.chars = new IntObject[2048];
        Nigga3.fontHeight = -1;
        Nigga3.charOffset = 8;
        Nigga3.font = Nigga;
        Nigga3.antiAlias = Nigga2;
        Nigga3.charOffset = 8;
        Nigga3.setupTexture(Nigga2);
    }

    public int getStringWidth(String Nigga) {
        int Nigga2 = 0;
        for (char Nigga3 : Nigga.toCharArray()) {
            SkizzleFont Nigga4;
            if (Nigga3 >= Nigga4.chars.length || Nigga3 < '\u0000') continue;
            Nigga2 += Nigga4.chars[Nigga3].width - Nigga4.charOffset;
        }
        return Nigga2 / 2;
    }

    public int getStringHeight(String Nigga) {
        SkizzleFont Nigga2;
        int Nigga3 = 1;
        for (char Nigga4 : Nigga.toCharArray()) {
            if (Nigga4 != '\n') continue;
            ++Nigga3;
        }
        return (Nigga2.fontHeight - Nigga2.charOffset) / 2 * Nigga3;
    }

    public void drawChar(char Nigga, float Nigga2, float Nigga3) throws ArrayIndexOutOfBoundsException {
        try {
            SkizzleFont Nigga4;
            Nigga4.drawQuad(Nigga2, Nigga3, Nigga4.chars[Nigga].width, Nigga4.chars[Nigga].height, Nigga4.chars[Nigga].storedX, Nigga4.chars[Nigga].storedY, Nigga4.chars[Nigga].width, Nigga4.chars[Nigga].height);
        }
        catch (Exception Nigga5) {
            Nigga5.printStackTrace();
            System.out.println(Qprot0.0("\u929c\u71de\ua9f3\u576c\uac17\u172f\u8c6f\ufedc\ua7c5\u0747\u9d0a\uaf08\u1016\u82c5\ud836\u0e82\u42ef\u24bc\ue7df\ub351\u6b4c\u0195\u828f\u18fb\u6bfe\u2fd4\u2f56\u47e4\ub2d0"));
        }
    }

    private class IntObject {
        public int storedX;
        public SkizzleFont this$0;
        public int height;
        public int storedY;
        public int width;

        public IntObject(SkizzleFont skizzleFont, IntObject intObject) {
            this(skizzleFont);
        }

        public IntObject(SkizzleFont skizzleFont) {
            IntObject Nigga;
            Nigga.this$0 = skizzleFont;
        }

        public static {
            throw throwable;
        }
    }
}

