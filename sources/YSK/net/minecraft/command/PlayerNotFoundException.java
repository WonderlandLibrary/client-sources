package net.minecraft.command;

public class PlayerNotFoundException extends CommandException
{
    private static final String[] I;
    
    public PlayerNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("3\u001b\b\u000b\f>\u0010\u0016H\n5\u001a\u0000\u0014\u00043Z\u0015\n\f)\u0011\u0017H\u0003?\u0000#\t\u0018>\u0010", "Ptefm");
    }
    
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PlayerNotFoundException() {
        this(PlayerNotFoundException.I["".length()], new Object["".length()]);
    }
}
