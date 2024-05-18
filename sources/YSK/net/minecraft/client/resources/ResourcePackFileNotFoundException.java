package net.minecraft.client.resources;

import java.io.*;

public class ResourcePackFileNotFoundException extends FileNotFoundException
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("Qi\u0002Dr\u001f\"Q17\u0005#\u0004\u00111\u0013\u001c\u0010\u00009VkT\u0010u", "vLqcR");
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ResourcePackFileNotFoundException(final File file, final String s) {
        final String s2 = ResourcePackFileNotFoundException.I["".length()];
        final Object[] array = new Object["  ".length()];
        array["".length()] = s;
        array[" ".length()] = file;
        super(String.format(s2, array));
    }
}
