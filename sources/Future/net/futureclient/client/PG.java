package net.futureclient.client;

import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Graphics2D;
import org.lwjgl.opengl.GL11;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.awt.Font;

public class PG
{
    public int A;
    public Font j;
    public dG[] K;
    public int M;
    public DynamicTexture d;
    private float a;
    public boolean D;
    public boolean k;
    
    public PG(final Font j, final boolean d, final boolean k) {
        final int n = 256;
        final float a = 512.0f;
        super();
        this.a = a;
        this.K = new dG[n];
        final int m = 0;
        this.A = -1;
        this.M = m;
        this.j = j;
        this.D = d;
        this.k = k;
        this.d = this.M(j, d, k, this.K);
    }
    
    public boolean e() {
        return this.D;
    }
    
    public void e(final boolean d) {
        if (this.D != d) {
            this.D = d;
            this.d = this.M(this.j, d, this.k, this.K);
        }
    }
    
    public void M(final dG[] array, final char c, final float n, final float n2) throws ArrayIndexOutOfBoundsException {
        try {
            this.M(n, n2, (float)array[c].M, (float)array[c].d, (float)array[c].a, (float)array[c].D, (float)array[c].M, (float)array[c].d);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String M(String s) {
        final char c = 'z';
        final char c2 = 'y';
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n;
        int i = n = length - 1;
        final char[] array2 = array;
        final char c3 = c2;
        final char c4 = c;
        while (i >= 0) {
            final char[] array3 = array2;
            final String s2 = s;
            final int n2 = n;
            final char char1 = s2.charAt(n2);
            --n;
            array3[n2] = (char)(char1 ^ c4);
            if (n < 0) {
                break;
            }
            final char[] array4 = array2;
            final String s3 = s;
            final int n3 = n--;
            array4[n3] = (char)(s3.charAt(n3) ^ c3);
            i = n;
        }
        return new String(array2);
    }
    
    public DynamicTexture M(final Font font, final boolean b, final boolean b2, final dG[] array) {
        final BufferedImage m = this.M(font, b, b2, array);
        try {
            return new DynamicTexture(m);
        }
        catch (Exception ex) {
            final DynamicTexture dynamicTexture = null;
            ex.printStackTrace();
            return dynamicTexture;
        }
    }
    
    public void M(final float n, final float n2, final float n3, final float n4, float n5, float n6, float n7, float n8) {
        n5 /= this.a;
        n6 /= this.a;
        n7 /= this.a;
        n8 /= this.a;
        GL11.glTexCoord2f(n5 + n7, n6);
        GL11.glVertex2d((double)(n + n3), (double)n2);
        GL11.glTexCoord2f(n5, n6);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glTexCoord2f(n5, n6 + n8);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n5, n6 + n8);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n5 + n7, n6 + n8);
        GL11.glVertex2d((double)(n + n3), (double)(n2 + n4));
        GL11.glTexCoord2f(n5 + n7, n6);
        GL11.glVertex2d((double)(n + n3), (double)n2);
    }
    
    public void M(final boolean k) {
        if (this.k != k) {
            this.k = k;
            this.d = this.M(this.j, this.D, k, this.K);
        }
    }
    
    public boolean M() {
        return this.k;
    }
    
    public void M(final Font j) {
        this.j = j;
        this.d = this.M(j, this.D, this.k, this.K);
    }
    
    public BufferedImage M(final Font font, final boolean b, final boolean b2, final dG[] array) {
        final int n2;
        final int n = n2 = (int)this.a;
        final BufferedImage bufferedImage;
        final Graphics2D graphics2D2;
        final Graphics2D graphics2D = graphics2D2 = (Graphics2D)(bufferedImage = new BufferedImage(n2, n2, 2)).getGraphics();
        final int n3 = 0;
        final Graphics2D graphics2D3 = graphics2D2;
        graphics2D2.setFont(font);
        final int n4 = 255;
        final int n5 = 255;
        graphics2D3.setColor(new Color(n5, n4, n5, 0));
        final int n6 = 0;
        final int n7 = n;
        graphics2D.fillRect(n3, n6, n7, n7);
        graphics2D3.setColor(Color.WHITE);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, b2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, b ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2D2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, b ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        final FontMetrics fontMetrics = graphics2D2.getFontMetrics();
        int d = 0;
        int a = 0;
        int n8 = 1;
        int i = 0;
        int n9 = 0;
        while (i < array.length) {
            final char c = (char)n9;
            final Object object = new Object(this) {
                public int M;
                public int d;
                public int a;
                public int D;
                public final PG k;
                
                public dG(final PG k) {
                    this.k = k;
                    super();
                }
            };
            final Rectangle2D stringBounds = fontMetrics.getStringBounds(String.valueOf(c), graphics2D2);
            final int n10 = a;
            final Rectangle2D rectangle2D = stringBounds;
            final Object object2 = object;
            object2.M = stringBounds.getBounds().width + 8;
            object2.d = rectangle2D.getBounds().height;
            if (n10 + object.M >= n) {
                a = 0;
                n8 += d;
                d = 0;
            }
            if (object.d > d) {
                d = object.d;
            }
            final Object object3 = object;
            final int d2 = n8;
            final Object object4 = object;
            object4.a = a;
            object4.D = d2;
            if (object3.d > this.A) {
                this.A = object.d;
            }
            array[n9] = object;
            final Graphics2D graphics2D4 = graphics2D2;
            final String value = String.valueOf(c);
            final int n11 = a;
            graphics2D4.drawString(value, n11 + 2, n8 + fontMetrics.getAscent());
            final Object object5 = object;
            ++n9;
            a = n11 + object5.M;
            i = n9;
        }
        return bufferedImage;
    }
    
    public int M(final String s) {
        int n = 0;
        final char[] charArray;
        final int length = (charArray = s.toCharArray()).length;
        int i = 0;
        int n2 = 0;
        while (i < length) {
            final char c;
            if ((c = charArray[n2]) < this.K.length) {
                n += this.K[c].M - 8 + this.M;
            }
            i = ++n2;
        }
        return n / 2;
    }
    
    public Font M() {
        return this.j;
    }
    
    public int M() {
        return this.A / 2 - 2;
    }
}
