package com.sun.jna.z.a.e.a.a.a.a;

public enum c
{
    CENTER, 
    LEFT, 
    RIGHT, 
    TOP, 
    BOTTOM;
    
    private static final c[] a;
    
    static {
        final String[] array = new String[5];
        int n = 0;
        String s;
        int n2 = (s = "\u0094T¡\u0098\u00cd±\u0003\u0083^¿\u0005\u0085X¨\u0084\u00dc").length();
        int n3 = 6;
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
                        Label_0250: {
                            if (n7 > 1) {
                                break Label_0250;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u00d7';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u0011';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u00ef';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u00cc';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u0088';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u00e3';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u0085';
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
                                continue Label_0023;
                            }
                            n2 = (s = "\u0095^»\u0098\u00c7®\u0004\u009bT©\u0098").length();
                            n3 = 6;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            array[n++] = intern;
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
        final String[] array2 = array;
        a = new c[] { c.CENTER, c.LEFT, c.RIGHT, c.TOP, c.BOTTOM };
    }
}
