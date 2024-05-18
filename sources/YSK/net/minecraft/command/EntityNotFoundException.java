package net.minecraft.command;

public class EntityNotFoundException extends CommandException
{
    private static final String[] I;
    
    public EntityNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("09\u0014%\u0019=2\nf\u001f68\u001c:\u00110x\u001c&\f:\"\u0000f\u0016<\"?'\r=2", "SVyHx");
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityNotFoundException() {
        this(EntityNotFoundException.I["".length()], new Object["".length()]);
    }
}
