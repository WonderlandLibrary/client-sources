package md;

import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class a extends c
{
    public t n;
    public com.sun.jna.z.a.e.a.a.a.a.i o;
    public static int p;
    public static boolean q;
    public static int r;
    public static int s;
    public static int t;
    public static boolean u;
    public static boolean v;
    public static int w;
    public static boolean x;
    public static boolean y;
    public static int z;
    public static boolean A;
    public static boolean B;
    public static int C;
    public static boolean D;
    public static int E;
    public static boolean F;
    public static boolean G;
    public static boolean H;
    private static final String[] I;
    
    public a() {
        final int h = a.H ? 1 : 0;
        final String[] a = md.a.I;
        super(a[2], com.sun.jna.z.a.d.b.Combat, -16711936);
        final int a2 = h;
        this.n = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[1], 4.5, 3.0, 6.0, 0.01, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL);
        this.o = new d(a[0]);
        if (a2 != 0) {
            int a3 = md.a.p;
            md.a.p = ++a3;
        }
    }
    
    @Override
    public k[] k() {
        final int a = md.a.H ? 1 : 0;
        final k[] array = { this.o, this.n };
        if (md.a.p != 0) {
            md.a.H = (a == 0);
        }
        return array;
    }
    
    static {
        final String[] i = new String[3];
        int n = 0;
        final String s;
        final int length = (s = "1A¥\u0019&3\u00d5\u001dJ\u00ad\u0005 O¦\u001c,\u0005 K©\u0018!").length();
        int char1 = 10;
        int n2 = -1;
        Label_0022: {
            break Label_0022;
            do {
                char1 = s.charAt(n2);
                ++n2;
                final String s2 = s;
                final int n3 = n2;
                final char[] charArray = s2.substring(n3, n3 + char1).toCharArray();
                int length2;
                int n5;
                final int n4 = n5 = (length2 = charArray.length);
                int n6 = 0;
                while (true) {
                    Label_0186: {
                        if (n4 > 1) {
                            break Label_0186;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 'r';
                                    break;
                                }
                                case 1: {
                                    c2 = '.';
                                    break;
                                }
                                case 2: {
                                    c2 = '\u00c8';
                                    break;
                                }
                                case 3: {
                                    c2 = '{';
                                    break;
                                }
                                case 4: {
                                    c2 = 'I';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u0013';
                                    break;
                                }
                                default: {
                                    c2 = '¸';
                                    break;
                                }
                            }
                            charArray[length2] = (char)(c ^ c2);
                            ++n6;
                        } while (n4 == 0);
                    }
                    if (n4 > n6) {
                        continue;
                    }
                    break;
                }
                i[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        I = i;
    }
}
