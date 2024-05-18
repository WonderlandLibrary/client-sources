package net.minecraft.command;

public class SyntaxErrorException extends CommandException
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("3>\u001f\u0003\u000e>5\u0001@\b5?\u0017\u001c\u00063\u007f\u0001\u0000\u0016$0\n", "PQrno");
    }
    
    public SyntaxErrorException() {
        this(SyntaxErrorException.I["".length()], new Object["".length()]);
    }
    
    static {
        I();
    }
    
    public SyntaxErrorException(final String s, final Object... array) {
        super(s, array);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
