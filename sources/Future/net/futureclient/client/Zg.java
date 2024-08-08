package net.futureclient.client;

import java.util.concurrent.TimeUnit;

public final class Zg
{
    private long k;
    
    public Zg() {
        super();
    }
    
    public long e() {
        return this.k;
    }
    
    public static String M(String s) {
        final char c = '\u0012';
        final char c2 = '=';
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
    
    public long M() {
        return this.M(TimeUnit.MILLISECONDS);
    }
    
    public boolean M(final long n) {
        return this.M(n, TimeUnit.MILLISECONDS);
    }
    
    public void M() {
        this.k = System.nanoTime();
    }
    
    public long M(final TimeUnit timeUnit) {
        return timeUnit.convert(System.nanoTime() - this.k, TimeUnit.NANOSECONDS);
    }
    
    public boolean M(final long n, final TimeUnit timeUnit) {
        final long convert;
        if ((convert = timeUnit.convert(System.nanoTime() - this.k, TimeUnit.NANOSECONDS)) >= n) {
            this.M();
        }
        return convert >= n;
    }
}
