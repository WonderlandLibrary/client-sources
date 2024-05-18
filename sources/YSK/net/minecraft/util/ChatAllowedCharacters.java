package net.minecraft.util;

public class ChatAllowedCharacters
{
    public static final char[] allowedCharactersArray;
    
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        final char[] allowedCharactersArray2 = new char[0x52 ^ 0x5D];
        allowedCharactersArray2["".length()] = (char)(0xB3 ^ 0x9C);
        allowedCharactersArray2[" ".length()] = (char)(0x2D ^ 0x27);
        allowedCharactersArray2["  ".length()] = (char)(0x2C ^ 0x21);
        allowedCharactersArray2["   ".length()] = (char)(0x8D ^ 0x84);
        allowedCharactersArray2[0x60 ^ 0x65] = (char)(0xCB ^ 0xC7);
        allowedCharactersArray2[0x27 ^ 0x21] = (char)(0x5A ^ 0x3A);
        allowedCharactersArray2[0x88 ^ 0x8F] = (char)(0x6C ^ 0x53);
        allowedCharactersArray2[0x53 ^ 0x5B] = (char)(0xB4 ^ 0x9E);
        allowedCharactersArray2[0xB5 ^ 0xBC] = (char)(0xDF ^ 0x83);
        allowedCharactersArray2[0x1C ^ 0x16] = (char)(0x95 ^ 0xA9);
        allowedCharactersArray2[0x6 ^ 0xD] = (char)(0x2A ^ 0x14);
        allowedCharactersArray2[0xA1 ^ 0xAD] = (char)(0x41 ^ 0x3D);
        allowedCharactersArray2[0x61 ^ 0x6C] = (char)(0x87 ^ 0xA5);
        allowedCharactersArray2[0x56 ^ 0x58] = (char)(0xAF ^ 0x95);
        allowedCharactersArray = allowedCharactersArray2;
    }
    
    public static String filterAllowedCharacters(final String s) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray;
        final int length = (charArray = s.toCharArray()).length;
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < length) {
            final char c = charArray[i];
            if (isAllowedCharacter(c)) {
                sb.append(c);
            }
            ++i;
        }
        return sb.toString();
    }
    
    public static boolean isAllowedCharacter(final char c) {
        if (c != 154 + 42 - 173 + 144 && c >= (0x4E ^ 0x6E) && c != 108 + 19 - 119 + 119) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
