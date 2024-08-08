package net.futureclient.client;

import java.util.Map;

public class iI<K, V>
{
    public Map<K, V> k;
    
    public iI() {
        super();
    }
    
    public void M(final K k, final V v) {
        this.k.put(k, v);
    }
    
    public void M(final K k) {
        this.k.remove(k);
    }
    
    public static String M(String s) {
        final char c = '\u001f';
        final char c2 = 'M';
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
    
    public Map<K, V> M() {
        return this.k;
    }
}
