package net.minecraft.util;

public class ChatAllowedCharacters
{
    public static final char[] allowedCharactersArray;
    
    public static boolean isAllowedCharacter(final char character) {
        return character != '�' && character >= ' ' && character != '\u007f';
    }
    
    public static String filterAllowedCharacters(final String input) {
        final StringBuilder var1 = new StringBuilder();
        for (final char var5 : input.toCharArray()) {
            if (isAllowedCharacter(var5)) {
                var1.append(var5);
            }
        }
        return var1.toString();
    }
    
    static {
        allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
}
