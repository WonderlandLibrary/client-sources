package me.darkmagician6.morbid.util.font;

import java.awt.image.*;
import java.awt.*;
import me.darkmagician6.morbid.*;
import org.lwjgl.opengl.*;
import java.awt.geom.*;

public class CustomFont
{
    private int texID;
    private int[] xPos;
    private int[] yPos;
    private int startChar;
    private int endChar;
    private int fSize;
    protected float zLevel;
    private static FontMetrics metrics;
    private String fontName;
    private Graphics graphics;
    
    public CustomFont(final String s, final int i) {
        this(s, i, 31, 127);
    }
    
    public CustomFont(final String s, final int i, final int j, final int k) {
        this.zLevel = 0.0f;
        this.startChar = j;
        this.endChar = k;
        this.xPos = new int[k - j];
        this.yPos = new int[k - j];
        final BufferedImage bufferedimage = new BufferedImage(256, 256, 2);
        final Graphics g = bufferedimage.getGraphics();
        this.fSize = i;
        try {
            final Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            this.fontName = s;
            g.setFont(new Font(s, 0, i));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, 256, 256);
        g.setColor(Color.white);
        CustomFont.metrics = g.getFontMetrics();
        int l = 2;
        int i2 = 2;
        for (int j2 = j; j2 < k; ++j2) {
            g.drawString("" + (char)j2, l, i2 + g.getFontMetrics().getAscent());
            this.xPos[j2 - j] = l;
            this.yPos[j2 - j] = i2 - CustomFont.metrics.getMaxDescent();
            l += CustomFont.metrics.stringWidth("" + (char)j2) + 2;
            if (l >= 250 - CustomFont.metrics.getMaxAdvance()) {
                l = 2;
                i2 += CustomFont.metrics.getMaxAscent() + CustomFont.metrics.getMaxDescent() + i / 2;
            }
        }
        this.texID = MorbidWrapper.mcObj().p.a(bufferedimage);
    }
    
    public void drawGoodString(final awx g, final String s, double i, double j, final int color) {
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        i *= 2.0;
        j *= 2.0;
        GL11.glPushMatrix();
        GL11.glEnable(3553);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(red, green, blue);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawString(g, s, i, j, red, green, blue);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void drawString(final awx gui, String s, double i, double j, final float red, final float green, final float blue) {
        s = s.replace("§§", "§");
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.texID);
        final double k = i;
        final double realY = j;
        try {
            for (int l = 0; l < s.length(); ++l) {
                j = realY;
                char c1 = s.charAt(l);
                if (c1 == '|') {
                    --j;
                }
                else if (c1 == '[') {
                    --j;
                }
                else if (c1 == ']') {
                    --j;
                }
                else {
                    j = realY;
                }
                if (c1 == '\n') {
                    j += CustomFont.metrics.getAscent() + 4;
                    i = k;
                    ++l;
                }
                else {
                    if (c1 == '\u00e1' || c1 == '\u00e0' || c1 == '\u00e2' || c1 == '\u00e4' || c1 == '\u00c1' || c1 == '\u00c0' || c1 == '\u00c2' || c1 == '\u00c4') {
                        c1 = 'a';
                    }
                    if (c1 == '\u00e9' || c1 == '\u00e8' || c1 == '\u00ea' || c1 == '\u00eb' || c1 == '\u00c9' || c1 == '\u00c8' || c1 == '\u00ca' || c1 == '\u00cb') {
                        c1 = 'e';
                    }
                    if (c1 == '\u00ed' || c1 == '\u00ec' || c1 == '\u00ee' || c1 == '\u00ef' || c1 == '\u00cd' || c1 == '\u00cc' || c1 == '\u00ce' || c1 == '\u00cf') {
                        c1 = 'i';
                    }
                    if (c1 == '\u00f3' || c1 == '\u00f2' || c1 == '\u00f4' || c1 == '\u00f6' || c1 == '\u00d3' || c1 == '\u00d2' || c1 == '\u00d4' || c1 == '\u00d6') {
                        c1 = 'o';
                    }
                    if (c1 == '\u00fa' || c1 == '\u00f9' || c1 == '\u00fb' || c1 == '\u00fc' || c1 == '\u00da' || c1 == '\u00d9' || c1 == '\u00db' || c1 == '\u00dc') {
                        c1 = 'u';
                    }
                    if (c1 == '§') {
                        ++l;
                        final char c2 = s.charAt(l);
                        if (c2 == 'a' || c2 == 'A') {
                            GL11.glColor4f(0.25f, 1.0f, 0.25f, 1.0f);
                        }
                        if (c2 == 'b' || c2 == 'B') {
                            GL11.glColor4f(0.25f, 1.0f, 1.0f, 1.0f);
                        }
                        if (c2 == 'c' || c2 == 'C') {
                            GL11.glColor4f(1.0f, 0.25f, 0.25f, 1.0f);
                        }
                        if (c2 == 'd' || c2 == 'D') {
                            GL11.glColor4f(1.0f, 0.25f, 1.0f, 1.0f);
                        }
                        if (c2 == 'e' || c2 == 'E') {
                            GL11.glColor4f(1.0f, 1.0f, 0.25f, 1.0f);
                        }
                        if (c2 == 'f' || c2 == 'F') {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        }
                        if (c2 == 'q') {
                            GL11.glColor4f(255.0f, 0.0f, 0.0f, 255.0f);
                        }
                        if (c2 == '0') {
                            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                        }
                        if (c2 == '1') {
                            GL11.glColor4f(0.0f, 0.0f, 0.75f, 1.0f);
                        }
                        if (c2 == '2') {
                            GL11.glColor4f(0.0f, 0.75f, 0.0f, 1.0f);
                        }
                        if (c2 == '3') {
                            GL11.glColor4f(0.0f, 0.75f, 0.75f, 1.0f);
                        }
                        if (c2 == '4') {
                            GL11.glColor4f(0.75f, 0.0f, 0.0f, 1.0f);
                        }
                        if (c2 == '5') {
                            GL11.glColor4f(0.75f, 0.0f, 0.75f, 1.0f);
                        }
                        if (c2 == '6') {
                            GL11.glColor4f(1.0f, 0.75f, 0.0f, 1.0f);
                        }
                        if (c2 == '7') {
                            GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
                        }
                        if (c2 == '8') {
                            GL11.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
                        }
                        if (c2 == 'k') {
                            GL11.glColor4f(1.0f, 0.25f, 0.0f, 1.0f);
                        }
                        if (c2 == 'r') {
                            GL11.glColor4f(red, green, blue, 1.0f);
                        }
                        if (c2 == '9') {
                            GL11.glColor4f(0.25f, 0.25f, 1.0f, 1.0f);
                        }
                    }
                    else if (c1 <= '~') {
                        this.drawChar(gui, c1, i, j);
                        i = (int)(i + CustomFont.metrics.getStringBounds("" + c1, null).getWidth());
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private void drawChar(final awx gui, final char c, final double i, final double j) {
        final Rectangle2D rectangle2d = CustomFont.metrics.getStringBounds("" + c, null);
        this.drawTexturedModalRect(i, j - 2.0, this.xPos[(byte)c - this.startChar], this.yPos[(byte)c - this.startChar], (int)rectangle2d.getWidth(), (int)rectangle2d.getHeight() + CustomFont.metrics.getMaxDescent() + 2);
    }
    
    public void drawTexturedModalRect(final double i, final double d, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final bgd var9 = bgd.a;
        var9.b();
        var9.a(i + 0.0, d + par6, this.zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
        var9.a(i + par5, d + par6, this.zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
        var9.a(i + par5, d + 0.0, this.zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
        var9.a(i + 0.0, d + 0.0, this.zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
        var9.a();
    }
}
