package net.minecraft.src;

import java.io.*;

public class ChatAllowedCharacters
{
    public static final String allowedCharacters;
    public static final char[] allowedCharactersArray;
    
    static {
        allowedCharacters = getAllowedCharacters();
        allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
    
    private static String getAllowedCharacters() {
        String var0 = "";
        try {
            final BufferedReader var2 = new BufferedReader(new InputStreamReader(ChatAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
            String var3 = "";
            while ((var3 = var2.readLine()) != null) {
                if (!var3.startsWith("#")) {
                    var0 = String.valueOf(var0) + var3;
                }
            }
            var2.close();
        }
        catch (Exception ex) {}
        return var0;
    }
    
    public static final boolean isAllowedCharacter(final char par0) {
        return par0 != '§' && (ChatAllowedCharacters.allowedCharacters.indexOf(par0) >= 0 || par0 > ' ');
    }
    
    public static String filerAllowedCharacters(final String par0Str) {
        final StringBuilder var1 = new StringBuilder();
        for (final char var5 : par0Str.toCharArray()) {
            if (isAllowedCharacter(var5)) {
                var1.append(var5);
            }
        }
        return var1.toString();
    }
}
