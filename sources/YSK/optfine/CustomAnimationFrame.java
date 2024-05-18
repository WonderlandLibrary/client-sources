package optfine;

public class CustomAnimationFrame
{
    public int index;
    public int duration;
    public int counter;
    
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public CustomAnimationFrame(final int index, final int duration) {
        this.index = "".length();
        this.duration = "".length();
        this.counter = "".length();
        this.index = index;
        this.duration = duration;
    }
}
