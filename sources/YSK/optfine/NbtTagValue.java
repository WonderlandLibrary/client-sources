package optfine;

public class NbtTagValue
{
    private String value;
    private String tag;
    
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
    
    public NbtTagValue(final String tag, final String value) {
        this.tag = null;
        this.value = null;
        this.tag = tag;
        this.value = value;
    }
    
    public boolean matches(final String s, final String s2) {
        return "".length() != 0;
    }
}
