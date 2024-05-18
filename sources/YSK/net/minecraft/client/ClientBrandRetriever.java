package net.minecraft.client;

public class ClientBrandRetriever
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("9\u0016>\u000e\u001b#\u0016", "OwPgw");
    }
    
    public static String getClientModName() {
        return ClientBrandRetriever.I["".length()];
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
