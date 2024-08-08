package net.futureclient.client;

import java.awt.Color;

public class dH
{
    private Color a;
    private float[] D;
    private float k;
    
    public dH(final Color a) {
        super();
        this.a = a;
        this.D = M(a);
        this.k = a.getAlpha() / 255.0f;
    }
    
    public dH(final float n, final float n2, final float n3) {
        this(n, n2, n3, 1.0f);
    }
    
    public dH(final float[] array) {
        this(array, 1.0f);
    }
    
    public dH(final float[] d, final float k) {
        super();
        this.D = d;
        this.k = k;
        this.a = M(d, k);
    }
    
    public dH(final float n, final float n2, final float n3, final float k) {
        final int n4 = 3;
        super();
        final float[] d = new float[n4];
        d[0] = n;
        d[1] = n2;
        d[2] = n3;
        this.D = d;
        this.k = k;
        this.a = M(this.D, k);
    }
    
    @Override
    public String toString() {
        return new StringBuilder().insert(0, "HSLColor[h=").append(this.D[0]).append(",s=").append(this.D[1]).append(",l=").append(this.D[2]).append(",alpha=").append(this.k).append("]").toString();
    }
    
    public float B() {
        return this.D[1];
    }
    
    public Color B(float max) {
        max = (100.0f - max) / 100.0f;
        max = Math.max(0.0f, this.D[2] * max);
        return M(this.D[0], this.D[1], max, this.k);
    }
    
    public Color C(float min) {
        min = (100.0f + min) / 100.0f;
        min = Math.min(100.0f, this.D[2] * min);
        return M(this.D[0], this.D[1], min, this.k);
    }
    
    public float b() {
        return this.k;
    }
    
    public Color b(final float n) {
        return M(this.D[0], this.D[1], n, this.k);
    }
    
    public float e() {
        return this.D[2];
    }
    
    public Color e() {
        return this.a;
    }
    
    public Color e(final float n) {
        return M(n, this.D[1], this.D[2], this.k);
    }
    
    public Color M(final float n) {
        return M(this.D[0], n, this.D[2], this.k);
    }
    
    public static float[] M(final Color color) {
        final float[] rgbColorComponents = color.getRGBColorComponents(null);
        final float n = rgbColorComponents[0];
        final float n2 = rgbColorComponents[1];
        final float n3 = rgbColorComponents[2];
        final float min = Math.min(n, Math.min(n2, n3));
        final float max = Math.max(n, Math.max(n2, n3));
        float n4 = 0.0f;
        float n5;
        if (max == min) {
            n4 = 0.0f;
            n5 = max;
        }
        else if (max == n) {
            n4 = (60.0f * (n2 - n3) / (max - min) + 360.0f) % 360.0f;
            n5 = max;
        }
        else if (max == n2) {
            n4 = 60.0f * (n3 - n) / (max - min) + 120.0f;
            n5 = max;
        }
        else {
            if (max == n3) {
                n4 = 60.0f * (n - n2) / (max - min) + 240.0f;
            }
            n5 = max;
        }
        final float n6 = (n5 + min) / 2.0f;
        float n7;
        if (max == min) {
            n7 = 0.0f;
        }
        else {
            final float n8 = fcmpg(n6, 0.5f);
            final float n9 = max;
            if (n8 <= 0) {
                n7 = (n9 - min) / (max + min);
            }
            else {
                n7 = (n9 - min) / (2.0f - max - min);
            }
        }
        return new float[] { n4, n7 * 100.0f, n6 * 100.0f };
    }
    
    public float M() {
        return this.D[0];
    }
    
    public Color M() {
        return M((this.D[0] + 180.0f) % 360.0f, this.D[1], this.D[2]);
    }
    
    public static Color M(final float[] array, final float n) {
        return M(array[0], array[1], array[2], n);
    }
    
    public static Color M(final float[] array) {
        return M(array, 1.0f);
    }
    
    private static float M(final float n, final float n2, float n3) {
        if (n3 < 0.0f) {
            ++n3;
        }
        if (n3 > 1.0f) {
            --n3;
        }
        if (6.0f * n3 < 1.0f) {
            return n + (n2 - n) * 6.0f * n3;
        }
        if (2.0f * n3 < 1.0f) {
            return n2;
        }
        if (3.0f * n3 < 2.0f) {
            return n + (n2 - n) * 6.0f * (0.6666667f - n3);
        }
        return n;
    }
    
    public static String M(String s) {
        final char c = 'q';
        final char c2 = '\u0018';
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
    
    public static Color M(final float n, final float n2, final float n3) {
        return M(n, n2, n3, 1.0f);
    }
    
    public static Color M(float n, float n2, float n3, final float n4) {
        if (n2 < 0.0f || n2 > 100.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Saturation");
        }
        if (n3 < 0.0f || n3 > 100.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Lightness");
        }
        if (n4 < 0.0f || n4 > 1.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Alpha");
        }
        n = (n %= 360.0f) / 360.0f;
        n2 /= 100.0f;
        n3 /= 100.0f;
        float n5;
        if (n3 < 0.0) {
            n5 = n3 * (1.0f + n2);
        }
        else {
            n5 = n3 + n2 - n2 * n3;
        }
        n2 = 2.0f * n3 - n5;
        n3 = Math.max(0.0f, M(n2, n5, n + 0.33333334f));
        final float max = Math.max(0.0f, M(n2, n5, n));
        n2 = Math.max(0.0f, M(n2, n5, n - 0.33333334f));
        n3 = Math.min(n3, 1.0f);
        final float min = Math.min(max, 1.0f);
        n2 = Math.min(n2, 1.0f);
        return new Color(n3, min, n2, n4);
    }
    
    public float[] M() {
        return this.D;
    }
}
