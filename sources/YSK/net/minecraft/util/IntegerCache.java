package net.minecraft.util;

public class IntegerCache
{
    private static final Integer[] field_181757_a;
    
    static {
        field_181757_a = new Integer[11425 + 34600 - 38177 + 57687];
        int i = "".length();
        final int length = IntegerCache.field_181757_a.length;
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < length) {
            IntegerCache.field_181757_a[i] = i;
            ++i;
        }
    }
    
    public static Integer func_181756_a(final int n) {
        Integer value;
        if (n > 0 && n < IntegerCache.field_181757_a.length) {
            value = IntegerCache.field_181757_a[n];
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            value = n;
        }
        return value;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
