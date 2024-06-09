package com.sun.jna.z.a.d;

public enum b
{
    Other, 
    Player, 
    World, 
    Render, 
    Combat, 
    Utility, 
    None;
    
    public static boolean b;
    
    static {
        final String[] array = new String[7];
        int n = 0;
        String s;
        int n2 = (s = "\u00f8§?\u00d48\b\u0006\u00e9\u00ad<\u00d2<\u000e\u0005\u00f4¼:\u00d3+\u0006\u00eb¤3\u00cf<\u000e\u0004\u00f5§<\u00d3").length();
        int n3 = 6;
        int n4 = -1;
    Label_0024:
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
                                        c2 = '»';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u00c8';
                                        break;
                                    }
                                    case 2: {
                                        c2 = 'R';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '¶';
                                        break;
                                    }
                                    case 4: {
                                        c2 = 'Y';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '|';
                                        break;
                                    }
                                    default: {
                                        c2 = '_';
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
                                continue Label_0024;
                            }
                            n2 = (s = "\u00ec§ \u00da=\u0007\u00ee¼;\u00da0\b&").length();
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
                            break Label_0024;
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
    }
}
