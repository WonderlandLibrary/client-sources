package net.minecraft.realms;

import net.minecraft.util.*;

public class RealmsSharedConstants
{
    public static char[] ILLEGAL_FILE_CHARACTERS;
    public static String VERSION_STRING;
    public static int NETWORK_PROTOCOL_VERSION;
    public static int TICKS_PER_SECOND;
    private static final String[] I;
    
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("XJNiB", "idvGz");
    }
    
    static {
        I();
        RealmsSharedConstants.NETWORK_PROTOCOL_VERSION = (0xEE ^ 0xC1);
        RealmsSharedConstants.TICKS_PER_SECOND = (0xD3 ^ 0xC7);
        RealmsSharedConstants.VERSION_STRING = RealmsSharedConstants.I["".length()];
        RealmsSharedConstants.ILLEGAL_FILE_CHARACTERS = ChatAllowedCharacters.allowedCharactersArray;
    }
}
