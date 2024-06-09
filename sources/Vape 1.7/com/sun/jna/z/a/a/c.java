package com.sun.jna.z.a.a;

import com.sun.jna.z.a.*;
import com.sun.jna.z.a.f.*;

public class c extends a
{
    private static final String[] j;
    
    @Override
    public void a() {
        this.c = this.c.toLowerCase();
        final int a = com.sun.jna.z.a.a.a.h;
        final f a2 = com.sun.jna.z.a.i.f.d;
        final String b = this.b;
        String[] a3 = c.j;
        final boolean equalsIgnoreCase;
        final boolean b2 = equalsIgnoreCase = b.equalsIgnoreCase(a3[1]);
        final boolean contains;
        Label_0127: {
            if (a == 0) {
                if (b2) {
                    contains = a2.a.contains(this.c);
                    if (a != 0) {
                        break Label_0127;
                    }
                    if (!contains) {
                        a2.a.add(this.c);
                        com.sun.jna.z.a.a.a.e(a3[3] + this.c + a3[2]);
                        if (a == 0) {
                            return;
                        }
                    }
                }
                final String b3 = this.b;
                a3 = c.j;
                b3.equalsIgnoreCase(a3[5]);
            }
        }
        if (a == 0) {
            if (!b2) {
                return;
            }
            a2.a.contains(this.c);
        }
        if (a == 0) {
            if (!contains) {
                return;
            }
            a2.a.remove(this.c);
        }
        com.sun.jna.z.a.a.a.e(this.c + c.j[0]);
    }
    
    @Override
    public String b() {
        return c.j[4];
    }
    
    static {
        final String[] i = new String[6];
        int n = 0;
        String s;
        int n2 = (s = "Bu\u00db\u00cd\u00ad\u00c8c\u0006\u0007\u00d8\u00d2\u00ad\u00d3&\u0004U\u00d7\u00c5¬\u00dauBK\u00d7\u00d3¶\u0003\u0003C\u00da\u0018\u00c5A\u009e\u00e1¦\u00dac\u0006\u0007\u00ca\u00cf\u00e2\u00d8t\u000bB\u00d0\u00c4±\u009ej\u000bT\u00ca\u0002\u00c5F").length();
        int n3 = 26;
        int n4 = -1;
    Label_0023:
        while (true) {
            while (true) {
                ++n4;
                final String s2 = s;
                final int n5 = n4;
                String s3 = s2.substring(n5, n5 + n3);
                int n6 = -1;
                while (true) {
                    final char[] charArray = s3.toCharArray();
                    int length;
                    int n8;
                    final int n7 = n8 = (length = charArray.length);
                    int n9 = 0;
                    while (true) {
                        Label_0248: {
                            if (n7 > 1) {
                                break Label_0248;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = 'b';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\'';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '¾';
                                        break;
                                    }
                                    case 3: {
                                        c2 = ' ';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u00c2';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '¾';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u0006';
                                        break;
                                    }
                                }
                                charArray[length] = (char)(c ^ c2);
                                ++n9;
                            } while (n7 == 0);
                        }
                        if (n7 > n9) {
                            continue;
                        }
                        break;
                    }
                    final String intern = new String(charArray).intern();
                    switch (n6) {
                        default: {
                            i[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "\u0004U\u00d7\u00c5¬\u00da\u0003\u0006B\u00d2").length();
                            n3 = 6;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            i[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0023;
                        }
                    }
                    ++n4;
                    final String s4 = s;
                    final int n10 = n4;
                    s3 = s4.substring(n10, n10 + n3);
                    n6 = 0;
                }
            }
            break;
        }
        j = i;
    }
}
