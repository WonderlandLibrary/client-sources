package net.futureclient.client;

import net.minecraft.util.Session;

public class ci
{
    private String A;
    private String j;
    private String K;
    private String M;
    private String d;
    private Wh a;
    private String D;
    private String k;
    
    public ci(final String s, final String j, final String m) {
        final String a = "";
        final String k = "";
        final String d = "";
        super();
        this.a = Wh.j;
        this.d = s;
        this.D = d;
        this.j = j;
        this.M = m;
        this.k = s;
        this.K = k;
        this.A = a;
    }
    
    public ci(final String d, final String d2, final String m, final String k, final String i) {
        final String a = "";
        final String j = "";
        super();
        this.a = Wh.d;
        this.d = d;
        this.D = d2;
        this.j = j;
        this.M = m;
        this.k = k;
        this.K = i;
        this.A = a;
    }
    
    public ci(final String k, final String i) {
        final String a = "";
        final String m = "";
        final String j = "";
        final String d = "";
        final String d2 = "";
        super();
        this.a = Wh.a;
        this.d = d2;
        this.D = d;
        this.j = j;
        this.M = m;
        this.k = k;
        this.K = i;
        this.A = a;
    }
    
    public ci(final String s) {
        final String a = "";
        final String k = "";
        final String m = "";
        final String j = "";
        final String d = "";
        super();
        this.a = Wh.D;
        this.d = s;
        this.D = d;
        this.j = j;
        this.M = m;
        this.k = s;
        this.K = k;
        this.A = a;
    }
    
    public void B(final String m) {
        this.M = m;
    }
    
    public String B() {
        return this.d;
    }
    
    public String C() {
        return this.j;
    }
    
    public void C(final String d) {
        this.D = d;
    }
    
    public void b(final String a) {
        this.A = a;
    }
    
    public String b() {
        return this.K;
    }
    
    public void e(final String d) {
        this.d = d;
    }
    
    public String e() {
        return this.D;
    }
    
    public String i() {
        return this.k;
    }
    
    public String M() {
        return this.M;
    }
    
    public void M(final String j) {
        this.j = j;
    }
    
    public Session M() {
        if (this.d.isEmpty()) {
            return new Session(this.k, this.D, this.M, "mojang");
        }
        return new Session(this.d, this.D, this.M, "mojang");
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        final int n = string.length() - 1;
        final int n2 = 91;
        final int n3 = 39;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public void M(final Session session) {
        this.D = session.getPlayerID();
        this.M = session.getToken();
    }
    
    public Wh M() {
        return this.a;
    }
    
    public void M(final Wh a) {
        this.a = a;
    }
    
    public String h() {
        return this.A;
    }
}
