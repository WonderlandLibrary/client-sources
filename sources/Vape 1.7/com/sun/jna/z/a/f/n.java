package com.sun.jna.z.a.f;

import java.util.*;

class n extends HashMap<Integer, String>
{
    final d a;
    private static final String[] b;
    
    n(final d a) {
        this.a = a;
        final Integer value = 0;
        final String[] a2 = n.b;
        this.put(value, a2[0]);
        this.put(1, a2[1]);
        this.put(2, a2[3]);
        this.put(3, a2[2]);
        this.put(4, a2[4]);
    }
    
    static {
        final String[] b2 = new String[5];
        int n = 0;
        String s;
        int n2 = (s = "X\u00cdA\u008f\u00c0|\"Q\u00c6H\u0080\u00c9w+V\u00dfS\u0099\u00d6n0O\u00d8Z\u0092\u00df\u001ax\u00eda¯\u00e0\\\u0002q\u00e6h \u00e9W\u000bv\u00ffs¹\u00f6N\u0010o\u00f8z²\u00ff\u000e\u00da\u2091\u00c1\u20d8F\u0169¦½L´\b9\u00f9\u011d").length();
        int n3 = 26;
        int n4 = -1;
    Label_0022:
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
                                        c2 = '\u0019';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u008f';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u0002';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u00cb';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u0085';
                                        break;
                                    }
                                    case 5: {
                                        c2 = ':';
                                        break;
                                    }
                                    default: {
                                        c2 = 'e';
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
                            b2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0022;
                            }
                            n2 = (s = ")¾0\u00f8±\u000fS.·;!9«)\u00e6¯\u0015X<\u00ad%\u00e8\u00c5\u001c:1¦.\u00e5¾\u0000Z8\u00d3~\u00f7»a8\u00db(b\u0095\u00fb").length();
                            n3 = 10;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            b2[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0022;
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
        b = b2;
    }
}
