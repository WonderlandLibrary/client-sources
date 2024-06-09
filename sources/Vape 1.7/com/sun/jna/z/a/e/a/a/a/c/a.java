package com.sun.jna.z.a.e.a.a.a.c;

public enum a implements d
{
    CENTER, 
    LEFT, 
    RIGHT, 
    FILL;
    
    private static final a[] a;
    
    static {
        final String[] array = new String[4];
        int n = 0;
        String s;
        int n2 = (s = "\u00d1\u00cdZp\u0006\u00d4\u00c1Xh\u0091\u00f4").length();
        int n3 = 4;
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
                        Label_0244: {
                            if (n7 > 1) {
                                break Label_0244;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u0097';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u0084';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u0016';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '<';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u00d4';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '¦';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u0013';
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
                            array[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0022;
                            }
                            n2 = (s = "\u00c5\u00cdQt\u0080\u0004\u00db\u00c1Ph").length();
                            n3 = 5;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            array[n++] = intern;
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
        final String[] array2 = array;
        a = new a[] { com.sun.jna.z.a.e.a.a.a.c.a.CENTER, com.sun.jna.z.a.e.a.a.a.c.a.LEFT, com.sun.jna.z.a.e.a.a.a.c.a.RIGHT, com.sun.jna.z.a.e.a.a.a.c.a.FILL };
    }
}
