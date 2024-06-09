package com.sun.jna.z.a.f;

import java.util.*;
import java.awt.image.*;
import java.util.function.*;
import org.lwjgl.opengl.*;
import com.sun.jna.z.a.*;
import com.sun.jna.z.a.e.a.a.a.f.*;
import java.nio.*;
import java.awt.*;
import java.awt.geom.*;

public class d
{
    private final Map<Integer, String> a;
    private Font b;
    private FontMetrics c;
    private BufferedImage d;
    private int e;
    
    public float a() {
        return (float)this.a.values().stream().mapToDouble(this::d).max().getAsDouble();
    }
    
    public float b() {
        return this.a.keySet().size() * this.c();
    }
    
    public float a(final char a) {
        final String a2 = this.a.values().stream().filter(com.sun.jna.z.a.f.d::a).findFirst().orElse("" + a);
        return (float)this.c.getStringBounds(a2.substring(0, a2.indexOf(a)), null).getWidth();
    }
    
    public float b(final char a) {
        final float a2 = this.a.keySet().stream().filter(this::a).findFirst().orElse(0);
        return this.c() * a2;
    }
    
    public float c(final char a) {
        return this.c.charWidth(a);
    }
    
    public float c() {
        return this.c.getMaxAscent() + this.c.getMaxDescent();
    }
    
    public d(final String a, final float a) {
        this.a = new n(this);
        this.b = new Font(a, 0, (int)a);
        final int b = f.b ? 1 : 0;
        final GraphicsConfiguration a2 = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final Graphics2D a3 = a2.createCompatibleImage(1, 1, 3).createGraphics();
        a3.setFont(this.b);
        this.c = a3.getFontMetrics();
        this.d = a3.getDeviceConfiguration().createCompatibleImage((int)this.a(), (int)this.b(), 3);
        this.e = GL11.glGenTextures();
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.e);
        final int a4 = b;
        GL11.glTexImage2D(3553, 0, 6408, (int)this.a(), (int)this.b(), 0, 6408, 5121, this.d());
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        if (i.g != 0) {
            f.b = (a4 == 0);
        }
    }
    
    public void a(final String a, final float a, final float a, final int a) {
        GL11.glBindTexture(3553, this.e);
        GL11.glBegin(7);
        final int a2 = f.b ? 1 : 0;
        final Color a3 = a.a(a);
        GL11.glColor4f(a3.getRed() / 255.0f, a3.getGreen() / 255.0f, a3.getBlue() / 255.0f, a3.getAlpha() / 255.0f);
        float a4 = a;
        final char[] a5 = a.toCharArray();
        final int a6 = a5.length;
        int a7 = 0;
        while (a7 < a6) {
            final char a8 = a5[a7];
            final float a9 = this.c(a8);
            final float a10 = this.c();
            final float a11 = 1.0f / this.a() * a9;
            final float a12 = 1.0f / this.b() * a10;
            final float a13 = 1.0f / this.a() * this.a(a8);
            final float a14 = 1.0f / this.b() * this.b(a8);
            GL11.glTexCoord2f(a13, a14);
            GL11.glVertex3f(a4, a, 0.0f);
            GL11.glTexCoord2f(a13 + a11, a14);
            GL11.glVertex3f(a4 + a9, a, 0.0f);
            GL11.glTexCoord2f(a13 + a11, a14 + a12);
            GL11.glVertex3f(a4 + a9, a + a10, 0.0f);
            GL11.glTexCoord2f(a13, a14 + a12);
            GL11.glVertex3f(a4, a + a10, 0.0f);
            a4 += a9;
            ++a7;
            if (a2 != 0) {
                return;
            }
            if (a2 != 0) {
                break;
            }
        }
        GL11.glEnd();
    }
    
    public ByteBuffer d() {
        final Graphics2D a = (Graphics2D)this.d.getGraphics();
        final int b = f.b ? 1 : 0;
        a.setFont(this.b);
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        a.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        a.setColor(Color.WHITE);
        this.a.keySet().stream().forEach(this::a);
        final int[] a2 = new int[this.d.getWidth() * this.d.getHeight()];
        final int a3 = b;
        this.d.getRGB(0, 0, this.d.getWidth(), this.d.getHeight(), a2, 0, this.d.getWidth());
        final ByteBuffer a4 = ByteBuffer.allocateDirect(this.d.getWidth() * this.d.getHeight() * 4);
        int a5 = 0;
    Label_0273_Outer:
        while (a5 < this.d.getHeight()) {
            if (a3 == 0) {
                int a6 = 0;
                while (true) {
                    while (a6 < this.d.getWidth()) {
                        final int a7 = a2[a5 * this.d.getWidth() + a6];
                        a4.put((byte)(a7 >> 16 & 0xFF));
                        a4.put((byte)(a7 >> 8 & 0xFF));
                        a4.put((byte)(a7 & 0xFF));
                        a4.put((byte)(a7 >> 24 & 0xFF));
                        ++a6;
                        if (a3 == 0) {
                            if (a3 != 0) {
                                break;
                            }
                            continue Label_0273_Outer;
                        }
                        else {
                            if (a3 != 0) {
                                break Label_0273_Outer;
                            }
                            continue Label_0273_Outer;
                        }
                    }
                    ++a5;
                    continue;
                }
            }
            return a4;
        }
        a4.flip();
        return a4;
    }
    
    public float a(final String a) {
        final int b = f.b ? 1 : 0;
        float a2 = 0.0f;
        final char[] a3 = a.toCharArray();
        final int a4 = b;
        final int a5 = a3.length;
        int a6 = 0;
        float n = 0.0f;
        while (a6 < a5) {
            final char a7 = a3[a6];
            n = a2 + this.c(a7);
            if (a4 != 0) {
                return n;
            }
            a2 = n;
            ++a6;
            if (a4 != 0) {
                break;
            }
        }
        return n;
    }
    
    public float b(final String a) {
        return (float)this.c(a).getHeight() / 2.0f;
    }
    
    private Rectangle2D c(final String a) {
        return this.c.getStringBounds(a, this.d.getGraphics());
    }
    
    private void a(final Graphics2D a, final Integer a) {
        a.drawString(this.a.get(a), 0.0f, this.c.getMaxAscent() + this.c() * a);
    }
    
    private boolean a(final char a, final Integer a) {
        return this.a.get(a).contains("" + a);
    }
    
    private static boolean a(final char a, final String a) {
        return a.contains("" + a);
    }
    
    private double d(final String a) {
        return this.c.getStringBounds(a, null).getWidth();
    }
}
