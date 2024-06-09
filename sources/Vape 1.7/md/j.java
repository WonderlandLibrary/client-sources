package md;

import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class j extends c
{
    public t n;
    public static int o;
    public static boolean p;
    public static int q;
    public static int r;
    public static int s;
    public static boolean t;
    public static boolean u;
    public static int v;
    public static boolean w;
    public static boolean x;
    public static int y;
    public static boolean z;
    public static boolean A;
    public static int B;
    public static boolean C;
    public static int D;
    public static boolean E;
    public static boolean F;
    public static boolean G;
    private static final String[] H;
    
    public j() {
        final String[] a = j.H;
        super(a[0], com.sun.jna.z.a.d.b.Combat, -16711707);
        final int a2 = j.G ? 1 : 0;
        this.n = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[1], 0.35, 0.0, 1.0, 0.001, com.sun.jna.z.a.e.a.a.a.a.b.DECIMAL2);
        if (a2 != 0) {
            int a3 = j.o;
            j.o = ++a3;
        }
    }
    
    @Override
    public k[] k() {
        final int a = j.G ? 1 : 0;
        final k[] array = { this.n };
        if (j.o != 0) {
            j.G = (a == 0);
        }
        return array;
    }
    
    static {
        final String[] h = new String[2];
        int n = 0;
        final String s;
        final int length = (s = ";\u009bL,\u00ff\u009d\u00dc\u0000\u00066\u008aH\u000f\u00fe\u0081").length();
        int char1 = 8;
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
                    Label_0188: {
                        if (n4 > 1) {
                            break Label_0188;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 's';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u00f2';
                                    break;
                                }
                                case 2: {
                                    c2 = '8';
                                    break;
                                }
                                case 3: {
                                    c2 = 'n';
                                    break;
                                }
                                case 4: {
                                    c2 = '\u0090';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u00e5';
                                    break;
                                }
                                default: {
                                    c2 = '¹';
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
                h[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        H = h;
    }
}
