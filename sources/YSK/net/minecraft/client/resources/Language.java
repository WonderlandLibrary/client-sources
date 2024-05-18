package net.minecraft.client.resources;

public class Language implements Comparable<Language>
{
    private final String region;
    private final boolean bidirectional;
    private final String languageCode;
    private static final String[] I;
    private final String name;
    
    @Override
    public int compareTo(final Language language) {
        return this.languageCode.compareTo(language.languageCode);
    }
    
    @Override
    public int hashCode() {
        return this.languageCode.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        int n;
        if (this == o) {
            n = " ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (!(o instanceof Language)) {
            n = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = (this.languageCode.equals(((Language)o).languageCode) ? 1 : 0);
        }
        return n != 0;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isBidirectional() {
        return this.bidirectional;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Language)o);
    }
    
    public Language(final String languageCode, final String region, final String name, final boolean bidirectional) {
        this.languageCode = languageCode;
        this.region = region;
        this.name = name;
        this.bidirectional = bidirectional;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("r$ogf$~", "WWOOC");
    }
    
    @Override
    public String toString() {
        final String s = Language.I["".length()];
        final Object[] array = new Object["  ".length()];
        array["".length()] = this.name;
        array[" ".length()] = this.region;
        return String.format(s, array);
    }
    
    public String getLanguageCode() {
        return this.languageCode;
    }
}
