package net.minecraft.command;

public class NumberInvalidException extends CommandException
{
    private static final String[] I;
    
    static {
        I();
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("6\u0018\u001b\u001f\r;\u0013\u0005\\\u000b0\u0019\u0013\u0000\u00056Y\u0018\u0007\u0001{\u001e\u0018\u0004\r9\u001e\u0012", "Uwvrl");
    }
    
    public NumberInvalidException(final String s, final Object... array) {
        super(s, array);
    }
    
    public NumberInvalidException() {
        this(NumberInvalidException.I["".length()], new Object["".length()]);
    }
}
