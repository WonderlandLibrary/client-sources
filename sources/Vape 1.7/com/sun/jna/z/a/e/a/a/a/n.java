package com.sun.jna.z.a.e.a.a.a;

import com.sun.jna.z.a.e.a.a.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import com.sun.jna.z.a.*;

class n implements a
{
    long a;
    final g b;
    final h c;
    private static final String[] d;
    
    n(final h a, final g b) {
        this.c = a;
        this.b = b;
    }
    
    @Override
    public void a(final g a, final int a) {
        final int a2 = com.sun.jna.z.a.e.a.a.a.g.c ? 1 : 0;
        long a4 = a;
        int n = a;
        if (a2 == 0) {
            if (a != 0) {
                return;
            }
            a4 = (n = lcmp(System.currentTimeMillis() - this.a, 60000L));
        }
        Label_0161: {
            Label_0157: {
                if (a2 == 0) {
                    if (n <= 0) {
                        break Label_0161;
                    }
                    final g b = this.b;
                    final String[] a3 = com.sun.jna.z.a.e.a.a.a.n.d;
                    b.b(a3[2]);
                    if (a2 != 0) {
                        break Label_0157;
                    }
                    a4 = (i.f.a() ? 1 : 0);
                }
                if (a4 != 0) {
                    final g b2 = this.b;
                    final String[] a3 = com.sun.jna.z.a.e.a.a.a.n.d;
                    b2.b(a3[0]);
                    new C(this).start();
                    this.a = System.currentTimeMillis();
                    if (a2 == 0) {
                        return;
                    }
                }
                final g b3 = this.b;
                final StringBuilder sb = new StringBuilder();
                final String[] a3 = com.sun.jna.z.a.e.a.a.a.n.d;
                b3.b(sb.append(a3[1]).append((this.a - System.currentTimeMillis() + 60000L) / 1000L).append("s").toString());
            }
            if (a2 == 0) {
                return;
            }
        }
        this.b.b(com.sun.jna.z.a.e.a.a.a.n.d[1] + (this.a - System.currentTimeMillis() + 60000L) / 1000L + "s");
    }
    
    static {
        final String[] d2 = new String[3];
        int n = 0;
        final String s;
        final int length = (s = "Y\u00ff\u001b\u000e@\u0005]\u00ff\u0004\u001f\u0004\nY\u00e7\u0003\bM�#$�C").length();
        int char1 = 5;
        int n2 = -1;
        Label_0021: {
            break Label_0021;
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
                                    c2 = '\n';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u009e';
                                    break;
                                }
                                case 2: {
                                    c2 = 'm';
                                    break;
                                }
                                case 3: {
                                    c2 = 'k';
                                    break;
                                }
                                case 4: {
                                    c2 = '$';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u00cc';
                                    break;
                                }
                                default: {
                                    c2 = 'D';
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
                d2[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        d = d2;
    }
}
