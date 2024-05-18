package net.minecraft.client.resources.data;

import java.util.*;
import net.minecraft.client.resources.*;

public class LanguageMetadataSection implements IMetadataSection
{
    private final Collection<Language> languages;
    
    public Collection<Language> getLanguages() {
        return this.languages;
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LanguageMetadataSection(final Collection<Language> languages) {
        this.languages = languages;
    }
}
