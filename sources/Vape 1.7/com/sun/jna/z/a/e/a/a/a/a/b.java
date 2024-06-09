package com.sun.jna.z.a.e.a.a.a.a;

public enum b
{
    DECIMAL, 
    DECIMAL2, 
    INTEGER, 
    PERCENTAGE, 
    NONE;
    
    private static final b[] a;
    
    static {
        final String[] array = new String[5];
        int n = 0;
        String s;
        int n2 = (s = "\u008b+ª7\u001c3\u00d5\b\u0086 ½;\u00167\u00cb\u00f0\u0007\u0086 ½;\u00167\u00cb").length();
        int n3 = 7;
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
                        Label_0247: {
                            if (n7 > 1) {
                                break Label_0247;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '\u00c2';
                                        break;
                                    }
                                    case 1: {
                                        c2 = 'e';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u00fe';
                                        break;
                                    }
                                    case 3: {
                                        c2 = 'r';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '[';
                                        break;
                                    }
                                    case 5: {
                                        c2 = 'v';
                                        break;
                                    }
                                    default: {
                                        c2 = '\u0087';
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
                            n2 = (s = "\u008c*°7\n\u0092 ¬1\u001e8\u00d3\u0083\"»").length();
                            n3 = 4;
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
        a = new b[] { b.DECIMAL, b.DECIMAL2, b.INTEGER, b.PERCENTAGE, b.NONE };
    }
}
