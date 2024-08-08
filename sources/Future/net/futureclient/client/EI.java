package net.futureclient.client;

public class EI
{
    private String D;
    private String k;
    
    public EI() {
        super();
    }
    
    public String e() {
        return this.D;
    }
    
    public void e(final String d) {
        this.D = d;
    }
    
    public String M() {
        return this.k;
    }
    
    public void M(final String k) {
        this.k = k;
    }
    
    public static String M(String s) {
        final char c = '\u001b';
        final char c2 = '\u0014';
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
}
