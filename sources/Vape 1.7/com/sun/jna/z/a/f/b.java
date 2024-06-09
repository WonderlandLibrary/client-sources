package com.sun.jna.z.a.f;

public enum b
{
    THREE_DIMENSIONAL, 
    TWO_DIMENSIONAL;
    
    private static final b[] a;
    
    static {
        final String[] array = new String[2];
        int n = 0;
        final String s;
        final int length = (s = ".\u00c0\u008b¬-\u009c\u0002?\u00d9\u0097º&\u009b\u000e6\u0011.\u00df\u0096¶,\u008a\u000b3\u00da\u0081½:\u009c\u00004\u00d6\u0088").length();
        int char1 = 15;
        int n2 = -1;
        Label_0023: {
            break Label_0023;
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
                                    c2 = 'z';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u0097';
                                    break;
                                }
                                case 2: {
                                    c2 = '\u00c4';
                                    break;
                                }
                                case 3: {
                                    c2 = '\u00f3';
                                    break;
                                }
                                case 4: {
                                    c2 = 'i';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u00d5';
                                    break;
                                }
                                default: {
                                    c2 = 'O';
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
                array[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        final String[] array2 = array;
        a = new b[] { b.THREE_DIMENSIONAL, b.TWO_DIMENSIONAL };
    }
}
