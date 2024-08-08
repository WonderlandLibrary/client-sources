package net.futureclient.client;

public class P
{
    public static final String J = "\u000312";
    public static final String F = "\u000307";
    public static final String G = "\u000308";
    public static final String i = "\u0016";
    public static final String C = "\u000313";
    public static final String g = "\u000306";
    public static final String I = "\u000309";
    public static final String B = "\u000310";
    public static final String H = "\u000311";
    public static final String l = "\u000304";
    public static final String L = "\u000303";
    public static final String E = "\u000301";
    public static final String A = "\u000314";
    public static final String j = "\u001f";
    public static final String K = "\u000f";
    public static final String M = "\u000300";
    public static final String d = "\u000305";
    public static final String a = "\u000315";
    public static final String D = "\u000302";
    public static final String k = "\u0002";
    
    private P() {
        super();
    }
    
    public static String B(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < length) {
            final char char1;
            if ((char1 = s.charAt(i)) == '\u0003') {
                if (++i >= length || !Character.isDigit(s.charAt(i))) {
                    continue;
                }
                if (++i < length && Character.isDigit(s.charAt(i))) {
                    ++i;
                }
                final char char2;
                if (i >= length || (char2 = s.charAt(i)) != ',') {
                    continue;
                }
                if (++i < length) {
                    if (Character.isDigit(s.charAt(i))) {
                        if (++i >= length || !Character.isDigit(s.charAt(i))) {
                            continue;
                        }
                        ++i;
                    }
                    else {
                        --i;
                    }
                }
                else {
                    --i;
                }
            }
            else if (char1 == '\u000f') {
                ++i;
            }
            else {
                final StringBuffer sb2 = sb;
                ++i;
                sb2.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static String b(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        final int n = string.length() - 1;
        final int n2 = 12;
        final int n3 = 94;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public static String e(final String s) {
        return M(B(s));
    }
    
    public static String M(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        int n = 0;
        while (i < length) {
            final char char1;
            if ((char1 = s.charAt(n)) != '\u000f' && char1 != '\u0002' && char1 != '\u001f') {
                if (char1 != '\u0016') {
                    sb.append(char1);
                }
            }
            i = ++n;
        }
        return sb.toString();
    }
}
