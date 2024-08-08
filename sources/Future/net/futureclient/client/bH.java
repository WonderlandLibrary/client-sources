package net.futureclient.client;

public class bH
{
    private String K;
    private String M;
    private String d;
    private String a;
    private String D;
    private String k;
    
    public bH(final String s, final String s2, final String s3, final String s4, final String s5) {
        super();
        this.M(s);
        this.b(s2);
        this.e(s3);
        this.C(s4);
        this.B(s5);
    }
    
    public void B(final String d) {
        this.D = d;
    }
    
    public String B() {
        return this.d;
    }
    
    public String C() {
        return this.D;
    }
    
    public void C(final String m) {
        this.M = m;
    }
    
    public String b() {
        return this.K;
    }
    
    public void b(final String d) {
        this.d = d;
    }
    
    public void e(final String a) {
        this.a = a;
    }
    
    public String e() {
        return this.a;
    }
    
    public String i() {
        return this.M;
    }
    
    public void M(final String k) {
        this.K = k;
    }
    
    public static String M(String s) {
        final char c = ';';
        final char c2 = '\u0006';
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
    
    public String M() {
        return this.k;
    }
    
    public bH M(final String k) {
        this.k = k;
        return this;
    }
}
