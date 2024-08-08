package net.futureclient.client;

public class TI
{
    private String M;
    private String d;
    private String a;
    private String D;
    private String k;
    
    public TI(final String s, final String s2, final String s3, final String s4) {
        super();
        this.M(s);
        this.b(s2);
        this.e(s3);
        this.B(s4);
    }
    
    public String B() {
        return this.M;
    }
    
    public void B(final String m) {
        this.M = m;
    }
    
    public String C() {
        return this.k;
    }
    
    public String b() {
        return this.d;
    }
    
    public void b(final String a) {
        this.a = a;
    }
    
    public String e() {
        return this.a;
    }
    
    public void e(final String d) {
        this.d = d;
    }
    
    public void M(final String d) {
        this.D = d;
    }
    
    public String M() {
        return this.D;
    }
    
    public TI M(final String k) {
        this.k = k;
        return this;
    }
    
    public static String M(String s) {
        final char c = '\u0002';
        final char c2 = 'q';
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
