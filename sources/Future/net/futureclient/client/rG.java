package net.futureclient.client;

import java.util.ArrayDeque;

public class rG
{
    private ArrayDeque<Float> d;
    private float a;
    private long D;
    private static final int k = 20;
    
    public rG() {
        super();
        this.d = new ArrayDeque<Float>(20);
        pg.M().M().M((n)new GI(this));
    }
    
    public float M() {
        return this.a;
    }
    
    public static float M(final rG rg, final float a) {
        return rg.a = a;
    }
    
    public static long M(final rG rg, final long d) {
        return rg.D = d;
    }
    
    public static long M(final rG rg) {
        return rg.D;
    }
    
    public static String M(String s) {
        final char c = '\u000b';
        final char c2 = '\u0007';
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
    
    public static ArrayDeque M(final rG rg) {
        return rg.d;
    }
}
