package net.minecraft.command;

public class CommandNotFoundException extends CommandException
{
    private static final String[] I;
    
    public CommandNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    public CommandNotFoundException() {
        this(CommandNotFoundException.I["".length()], new Object["".length()]);
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0006\u0002\u0017+7\u000b\t\th1\u0000\u0003\u001f4?\u0006C\u0014)\"#\u0002\u000f(2", "emzFV");
    }
}
