package optfine;

public class IntegerCache
{
    private static final Integer[] cache;
    private static final int CACHE_SIZE;
    
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
    
    private static Integer[] makeCache(final int n) {
        final Integer[] array = new Integer[n];
        int i = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i < n) {
            array[i] = new Integer(i);
            ++i;
        }
        return array;
    }
    
    public static Integer valueOf(final int n) {
        Integer n2;
        if (n >= 0 && n < 2011 + 3651 - 3224 + 1658) {
            n2 = IntegerCache.cache[n];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = new Integer(n);
        }
        return n2;
    }
    
    static {
        CACHE_SIZE = 118 + 2735 - 2009 + 3252;
        cache = makeCache(1113 + 485 + 774 + 1724);
    }
}
