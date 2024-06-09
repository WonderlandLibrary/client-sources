package com.sun.jna.z.a.a;

import com.sun.jna.z.a.*;
import com.sun.jna.z.a.d.*;
import org.lwjgl.input.*;
import java.util.*;

public class b extends a
{
    private static final String[] j;
    
    @Override
    public void a() {
        final int a = com.sun.jna.z.a.a.a.h;
        final String b = this.b;
        String[] a2 = com.sun.jna.z.a.a.b.j;
        if (b.equalsIgnoreCase(a2[7])) {
            for (final c a3 : com.sun.jna.z.a.h.d.a.a.values()) {
                a3.b(-301);
                if (a != 0) {
                    return;
                }
                if (a != 0) {
                    break;
                }
            }
            a2 = com.sun.jna.z.a.a.b.j;
            com.sun.jna.z.a.a.a.e(a2[5]);
            return;
        }
        while (true) {
            for (final c a3 : com.sun.jna.z.a.h.d.a.a.values()) {
                final String lowerCase = a3.a().toLowerCase();
                if (a == 0) {
                    int n2;
                    int keyIndex;
                    final int n = keyIndex = (n2 = (lowerCase.equalsIgnoreCase(this.b) ? 1 : 0));
                    if (a == 0) {
                        if (n != 0) {
                            n2 = (keyIndex = Keyboard.getKeyIndex(this.c.toUpperCase()));
                        }
                        else {
                            if (a != 0) {
                                break;
                            }
                            continue;
                        }
                    }
                    if (a == 0) {
                        if (keyIndex == 0) {
                            a2 = com.sun.jna.z.a.a.b.j;
                            com.sun.jna.z.a.a.a.e(a2[4]);
                            if (a == 0) {
                                return;
                            }
                        }
                        final String c = this.c;
                        a2 = com.sun.jna.z.a.a.b.j;
                        n2 = (c.equalsIgnoreCase(a2[1]) ? 1 : 0);
                    }
                    Label_0232: {
                        if (n2 != 0) {
                            a3.b(-301);
                            if (a == 0) {
                                break Label_0232;
                            }
                        }
                        a3.b(Keyboard.getKeyIndex(this.c.toUpperCase()));
                    }
                    final StringBuilder sb = new StringBuilder();
                    a2 = com.sun.jna.z.a.a.b.j;
                    com.sun.jna.z.a.a.a.e(sb.append(a2[3]).append(this.b).append(a2[0]).append(this.c).toString());
                    return;
                }
                com.sun.jna.z.a.a.a.e(lowerCase);
                return;
            }
            new StringBuilder().append(this.b).append(com.sun.jna.z.a.a.b.j[6]).toString();
            continue;
        }
    }
    
    @Override
    public String b() {
        return b.j[2];
    }
    
    static {
        final String[] i = new String[8];
        int n = 0;
        String s;
        int n2 = (s = "\u0003\u001cG\u00ef+*=\u00c0Z\u0013\u00e2d\u00f82\u0004\u00ca\u0015\t\u00e8\u0004\u00c6\u0013\t\u00e9\u0002\u0003\u001b-\u00ef\u001f\u001e\u00ad*0'\u0084\u001c\b\u00f8*;}\u0084.\u0015\u00f4d,#\u00c1\u0016\u000b\u00e4*8s\u00cb\u000f\u0013\u00ad076\u0084\u0011\u0002\u00f4d12\u00c9\u001fI\u0010\u00ef\u001f\u001e\u00ef-17\u00d7Z\u0004\u00e1!>!\u00c1\u001e").length();
        int n3 = 14;
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
                        Label_0246: {
                            if (n7 > 1) {
                                break Label_0246;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '¤';
                                        break;
                                    }
                                    case 1: {
                                        c2 = 'z';
                                        break;
                                    }
                                    case 2: {
                                        c2 = 'g';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u008d';
                                        break;
                                    }
                                    case 4: {
                                        c2 = 'D';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '_';
                                        break;
                                    }
                                    default: {
                                        c2 = 'S';
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
                            n2 = (s = "\u0084\u0014\b\u00f9d9<\u00d1\u0014\u0003\u0005\u00c7\u0016\u0002\u00ec6").length();
                            n3 = 10;
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
