package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException
{
    private static final String[] I;
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final Throwable t) {
        final String s = ChatComponentTranslationFormatException.I["  ".length()];
        final Object[] array = new Object[" ".length()];
        array["".length()] = chatComponentTranslation;
        super(String.format(s, array), t);
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final String s) {
        final String s2 = ChatComponentTranslationFormatException.I["".length()];
        final Object[] array = new Object["  ".length()];
        array["".length()] = chatComponentTranslation;
        array[" ".length()] = s;
        super(String.format(s2, array));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final int n) {
        final String s = ChatComponentTranslationFormatException.I[" ".length()];
        final Object[] array = new Object["  ".length()];
        array["".length()] = n;
        array[" ".length()] = chatComponentTranslation;
        super(String.format(s, array));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0010!\u00196:u#\n+;<=\fchp Qym&", "USkYH");
        ChatComponentTranslationFormatException.I[" ".length()] = I("+-%\u0004$\u000b's\f&\u0006&+Em\u0006c!\u00009\u0017& \u0011-\u0006c5\n:Bf ", "bCSeH");
        ChatComponentTranslationFormatException.I["  ".length()] = I("#1\u00176\u0016F4\r0\b\u0003c\u00158\u0016\u0015*\u000b>^Ff\u0016", "fCeYd");
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
