package net.minecraft.command;

public class CommandException extends Exception
{
    private final Object[] errorObjects;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public CommandException(final String s, final Object... errorObjects) {
        super(s);
        this.errorObjects = errorObjects;
    }
    
    public Object[] getErrorObjects() {
        return this.errorObjects;
    }
}
