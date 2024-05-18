package me.enrythebest.reborn.cracked.util.font;

import java.awt.image.*;
import java.awt.*;
import me.enrythebest.reborn.cracked.*;
import org.lwjgl.opengl.*;
import java.awt.geom.*;
import net.minecraft.src.*;

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
    
    public CustomFont(final String var1, final int var2) {
        this(var1, var2, 31, 127);
    }
    
    public CustomFont(final String var1, final int var2, final int var3, final int var4) {
        this.zLevel = 0.0f;
        this.startChar = var3;
        this.endChar = var4;
        this.xPos = new int[var4 - var3];
        this.yPos = new int[var4 - var3];
        final BufferedImage var5 = new BufferedImage(256, 256, 2);
        final Graphics var6 = var5.getGraphics();
        this.fSize = var2;
        try {
            final Graphics2D var7 = (Graphics2D)var6;
            var7.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            this.fontName = var1;
            var6.setFont(new Font(var1, 0, var2));
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        var6.setColor(new Color(255, 255, 255, 0));
        var6.fillRect(0, 0, 256, 256);
        var6.setColor(Color.white);
        CustomFont.metrics = var6.getFontMetrics();
        int var9 = 2;
        int var10 = 2;
        for (int var11 = var3; var11 < var4; ++var11) {
            var6.drawString(new StringBuilder().append((char)var11).toString(), var9, var10 + var6.getFontMetrics().getAscent());
            this.xPos[var11 - var3] = var9;
            this.yPos[var11 - var3] = var10 - CustomFont.metrics.getMaxDescent();
            var9 += CustomFont.metrics.stringWidth(new StringBuilder().append((char)var11).toString()) + 2;
            if (var9 >= 250 - CustomFont.metrics.getMaxAdvance()) {
                var9 = 2;
                var10 += CustomFont.metrics.getMaxAscent() + CustomFont.metrics.getMaxDescent() + var2 / 2;
            }
        }
        this.texID = MorbidWrapper.mcObj().renderEngine.allocateAndSetupTexture(var5);
    }
    
    public void drawGoodString(final Gui var1, final String var2, double var3, double var5, final int var7) {
        final float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        final float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        final float var10 = (var7 & 0xFF) / 255.0f;
        var3 *= 2.0;
        var5 *= 2.0;
        GL11.glPushMatrix();
        GL11.glEnable(3553);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(var8, var9, var10);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawString(var1, var2, var3, var5, var8, var9, var10);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void drawString(final Gui var1, String var2, double var3, double var5, final float var7, final float var8, final float var9) {
        var2 = var2.replace("§§", "§");
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.texID);
        final double var10 = var3;
        final double var11 = var5;
        try {
            for (int var12 = 0; var12 < var2.length(); ++var12) {
                char var13 = var2.charAt(var12);
                if (var13 == '|') {
                    var5 = var11 - 1.0;
                }
                else if (var13 == '[') {
                    var5 = var11 - 1.0;
                }
                else if (var13 == ']') {
                    var5 = var11 - 1.0;
                }
                else {
                    var5 = var11;
                }
                if (var13 == '\n') {
                    final double var14 = var5 + (CustomFont.metrics.getAscent() + 4);
                    var3 = var10;
                    ++var12;
                }
                else {
                    if (var13 == '\u00e1' || var13 == '\u00e0' || var13 == '\u00e2' || var13 == '\u00e4' || var13 == '\u00c1' || var13 == '\u00c0' || var13 == '\u00c2' || var13 == '\u00c4') {
                        var13 = 'a';
                    }
                    if (var13 == '\u00e9' || var13 == '\u00e8' || var13 == '\u00ea' || var13 == '\u00eb' || var13 == '\u00c9' || var13 == '\u00c8' || var13 == '\u00ca' || var13 == '\u00cb') {
                        var13 = 'e';
                    }
                    if (var13 == '\u00ed' || var13 == '\u00ec' || var13 == '\u00ee' || var13 == '\u00ef' || var13 == '\u00cd' || var13 == '\u00cc' || var13 == '\u00ce' || var13 == '\u00cf') {
                        var13 = 'i';
                    }
                    if (var13 == '\u00f3' || var13 == '\u00f2' || var13 == '\u00f4' || var13 == '\u00f6' || var13 == '\u00d3' || var13 == '\u00d2' || var13 == '\u00d4' || var13 == '\u00d6') {
                        var13 = 'o';
                    }
                    if (var13 == '\u00fa' || var13 == '\u00f9' || var13 == '\u00fb' || var13 == '\u00fc' || var13 == '\u00da' || var13 == '\u00d9' || var13 == '\u00db' || var13 == '\u00dc') {
                        var13 = 'u';
                    }
                    if (var13 == '§') {
                        ++var12;
                        final char var15 = var2.charAt(var12);
                        if (var15 == 'a' || var15 == 'A') {
                            GL11.glColor4f(0.25f, 1.0f, 0.25f, 1.0f);
                        }
                        if (var15 == 'b' || var15 == 'B') {
                            GL11.glColor4f(0.25f, 1.0f, 1.0f, 1.0f);
                        }
                        if (var15 == 'c' || var15 == 'C') {
                            GL11.glColor4f(1.0f, 0.25f, 0.25f, 1.0f);
                        }
                        if (var15 == 'd' || var15 == 'D') {
                            GL11.glColor4f(1.0f, 0.25f, 1.0f, 1.0f);
                        }
                        if (var15 == 'e' || var15 == 'E') {
                            GL11.glColor4f(1.0f, 1.0f, 0.25f, 1.0f);
                        }
                        if (var15 == 'f' || var15 == 'F') {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        }
                        if (var15 == 'q') {
                            GL11.glColor4f(255.0f, 0.0f, 0.0f, 255.0f);
                        }
                        if (var15 == '0') {
                            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                        }
                        if (var15 == '1') {
                            GL11.glColor4f(0.0f, 0.0f, 0.75f, 1.0f);
                        }
                        if (var15 == '2') {
                            GL11.glColor4f(0.0f, 0.75f, 0.0f, 1.0f);
                        }
                        if (var15 == '3') {
                            GL11.glColor4f(0.0f, 0.75f, 0.75f, 1.0f);
                        }
                        if (var15 == '4') {
                            GL11.glColor4f(0.75f, 0.0f, 0.0f, 1.0f);
                        }
                        if (var15 == '5') {
                            GL11.glColor4f(0.75f, 0.0f, 0.75f, 1.0f);
                        }
                        if (var15 == '6') {
                            GL11.glColor4f(1.0f, 0.75f, 0.0f, 1.0f);
                        }
                        if (var15 == '7') {
                            GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
                        }
                        if (var15 == '8') {
                            GL11.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
                        }
                        if (var15 == 'k') {
                            GL11.glColor4f(1.0f, 0.25f, 0.0f, 1.0f);
                        }
                        if (var15 == 'r') {
                            GL11.glColor4f(var7, var8, var9, 1.0f);
                        }
                        if (var15 == '9') {
                            GL11.glColor4f(0.25f, 0.25f, 1.0f, 1.0f);
                        }
                    }
                    else if (var13 <= '~') {
                        this.drawChar(var1, var13, var3, var5);
                        var3 = (int)(var3 + CustomFont.metrics.getStringBounds(new StringBuilder().append(var13).toString(), null).getWidth());
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private void drawChar(final Gui var1, final char var2, final double var3, final double var5) {
        final Rectangle2D var6 = CustomFont.metrics.getStringBounds(new StringBuilder().append(var2).toString(), null);
        this.drawTexturedModalRect(var3, var5 - 2.0, this.xPos[(byte)var2 - this.startChar], this.yPos[(byte)var2 - this.startChar], (int)var6.getWidth(), (int)var6.getHeight() + CustomFont.metrics.getMaxDescent() + 2);
    }
    
    public void drawTexturedModalRect(final double var1, final double var3, final int var5, final int var6, final int var7, final int var8) {
        final float var9 = 0.00390625f;
        final float var10 = 0.00390625f;
        final Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.addVertexWithUV(var1 + 0.0, var3 + var8, this.zLevel, (var5 + 0) * var9, (var6 + var8) * var10);
        var11.addVertexWithUV(var1 + var7, var3 + var8, this.zLevel, (var5 + var7) * var9, (var6 + var8) * var10);
        var11.addVertexWithUV(var1 + var7, var3 + 0.0, this.zLevel, (var5 + var7) * var9, (var6 + 0) * var10);
        var11.addVertexWithUV(var1 + 0.0, var3 + 0.0, this.zLevel, (var5 + 0) * var9, (var6 + 0) * var10);
        var11.draw();
    }
}
