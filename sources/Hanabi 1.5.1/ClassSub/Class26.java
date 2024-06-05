package ClassSub;

import java.io.*;
import java.nio.*;

public class Class26 implements Serializable
{
    private static final long serialVersionUID = 1393939L;
    protected transient Class311 GL;
    public static final Class26 transparent;
    public static final Class26 white;
    public static final Class26 yellow;
    public static final Class26 red;
    public static final Class26 blue;
    public static final Class26 green;
    public static final Class26 black;
    public static final Class26 gray;
    public static final Class26 cyan;
    public static final Class26 darkGray;
    public static final Class26 lightGray;
    public static final Class26 pink;
    public static final Class26 orange;
    public static final Class26 magenta;
    public float r;
    public float g;
    public float b;
    public float a;
    
    
    public Class26(final Class26 class26) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = class26.r;
        this.g = class26.g;
        this.b = class26.b;
        this.a = class26.a;
    }
    
    public Class26(final FloatBuffer floatBuffer) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = floatBuffer.get();
        this.g = floatBuffer.get();
        this.b = floatBuffer.get();
        this.a = floatBuffer.get();
    }
    
    public Class26(final float r, final float g, final float b) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }
    
    public Class26(final float n, final float n2, final float n3, final float n4) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = Math.min(n, 1.0f);
        this.g = Math.min(n2, 1.0f);
        this.b = Math.min(n3, 1.0f);
        this.a = Math.min(n4, 1.0f);
    }
    
    public Class26(final int n, final int n2, final int n3) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = n / 255.0f;
        this.g = n2 / 255.0f;
        this.b = n3 / 255.0f;
        this.a = 1.0f;
    }
    
    public Class26(final int n, final int n2, final int n3, final int n4) {
        this.GL = Class197.get();
        this.a = 1.0f;
        this.r = n / 255.0f;
        this.g = n2 / 255.0f;
        this.b = n3 / 255.0f;
        this.a = n4 / 255.0f;
    }
    
    public Class26(final int n) {
        this.GL = Class197.get();
        this.a = 1.0f;
        final int n2 = (n & 0xFF0000) >> 16;
        final int n3 = (n & 0xFF00) >> 8;
        final int n4 = n & 0xFF;
        int n5 = (n & 0xFF000000) >> 24;
        if (n5 < 0) {
            n5 += 256;
        }
        if (n5 == 0) {
            n5 = 255;
        }
        this.r = n2 / 255.0f;
        this.g = n3 / 255.0f;
        this.b = n4 / 255.0f;
        this.a = n5 / 255.0f;
    }
    
    public static Class26 decode(final String s) {
        return new Class26(Integer.decode(s));
    }
    
    public void bind() {
        this.GL.glColor4f(this.r, this.g, this.b, this.a);
    }
    
    @Override
    public int hashCode() {
        return (int)(this.r + this.g + this.b + this.a) * 255;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Class26) {
            final Class26 class26 = (Class26)o;
            return class26.r == this.r && class26.g == this.g && class26.b == this.b && class26.a == this.a;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Color (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
    }
    
    public Class26 darker() {
        return this.darker(0.5f);
    }
    
    public Class26 darker(float n) {
        n = 1.0f - n;
        return new Class26(this.r * n, this.g * n, this.b * n, this.a);
    }
    
    public Class26 brighter() {
        return this.brighter(0.2f);
    }
    
    public int getRed() {
        return (int)(this.r * 255.0f);
    }
    
    public int getGreen() {
        return (int)(this.g * 255.0f);
    }
    
    public int getBlue() {
        return (int)(this.b * 255.0f);
    }
    
    public int getAlpha() {
        return (int)(this.a * 255.0f);
    }
    
    public int getRedByte() {
        return (int)(this.r * 255.0f);
    }
    
    public int getGreenByte() {
        return (int)(this.g * 255.0f);
    }
    
    public int getBlueByte() {
        return (int)(this.b * 255.0f);
    }
    
    public int getAlphaByte() {
        return (int)(this.a * 255.0f);
    }
    
    public Class26 brighter(float n) {
        ++n;
        return new Class26(this.r * n, this.g * n, this.b * n, this.a);
    }
    
    public Class26 multiply(final Class26 class26) {
        return new Class26(this.r * class26.r, this.g * class26.g, this.b * class26.b, this.a * class26.a);
    }
    
    public void add(final Class26 class26) {
        this.r += class26.r;
        this.g += class26.g;
        this.b += class26.b;
        this.a += class26.a;
    }
    
    public void scale(final float n) {
        this.r *= n;
        this.g *= n;
        this.b *= n;
        this.a *= n;
    }
    
    public Class26 addToCopy(final Class26 class26) {
        final Class26 class28;
        final Class26 class27 = class28 = new Class26(this.r, this.g, this.b, this.a);
        class28.r += class26.r;
        final Class26 class29 = class27;
        class29.g += class26.g;
        final Class26 class30 = class27;
        class30.b += class26.b;
        final Class26 class31 = class27;
        class31.a += class26.a;
        return class27;
    }
    
    public Class26 scaleCopy(final float n) {
        final Class26 class27;
        final Class26 class26 = class27 = new Class26(this.r, this.g, this.b, this.a);
        class27.r *= n;
        final Class26 class28 = class26;
        class28.g *= n;
        final Class26 class29 = class26;
        class29.b *= n;
        final Class26 class30 = class26;
        class30.a *= n;
        return class26;
    }
    
    static {
        transparent = new Class26(0.0f, 0.0f, 0.0f, 0.0f);
        white = new Class26(1.0f, 1.0f, 1.0f, 1.0f);
        yellow = new Class26(1.0f, 1.0f, 0.0f, 1.0f);
        red = new Class26(1.0f, 0.0f, 0.0f, 1.0f);
        blue = new Class26(0.0f, 0.0f, 1.0f, 1.0f);
        green = new Class26(0.0f, 1.0f, 0.0f, 1.0f);
        black = new Class26(0.0f, 0.0f, 0.0f, 1.0f);
        gray = new Class26(0.5f, 0.5f, 0.5f, 1.0f);
        cyan = new Class26(0.0f, 1.0f, 1.0f, 1.0f);
        darkGray = new Class26(0.3f, 0.3f, 0.3f, 1.0f);
        lightGray = new Class26(0.7f, 0.7f, 0.7f, 1.0f);
        pink = new Class26(255, 175, 175, 255);
        orange = new Class26(255, 200, 0, 255);
        magenta = new Class26(255, 0, 255, 255);
    }
}
