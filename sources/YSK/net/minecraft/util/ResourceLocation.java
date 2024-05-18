package net.minecraft.util;

import org.apache.commons.lang3.*;

public class ResourceLocation
{
    private static final String[] I;
    protected final String resourceDomain;
    protected final String resourcePath;
    
    public String getResourceDomain() {
        return this.resourceDomain;
    }
    
    protected static String[] splitObjectName(final String s) {
        final String[] array = new String["  ".length()];
        array[" ".length()] = s;
        final String[] array2 = array;
        final int index = s.indexOf(0x4E ^ 0x74);
        if (index >= 0) {
            array2[" ".length()] = s.substring(index + " ".length(), s.length());
            if (index > " ".length()) {
                array2["".length()] = s.substring("".length(), index);
            }
        }
        return array2;
    }
    
    public String getResourcePath() {
        return this.resourcePath;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ResourceLocation)) {
            return "".length() != 0;
        }
        final ResourceLocation resourceLocation = (ResourceLocation)o;
        if (this.resourceDomain.equals(resourceLocation.resourceDomain) && this.resourcePath.equals(resourceLocation.resourcePath)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected ResourceLocation(final int n, final String... array) {
        String lowerCase;
        if (StringUtils.isEmpty((CharSequence)array["".length()])) {
            lowerCase = ResourceLocation.I["".length()];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            lowerCase = array["".length()].toLowerCase();
        }
        this.resourceDomain = lowerCase;
        Validate.notNull((Object)(this.resourcePath = array[" ".length()]));
    }
    
    public ResourceLocation(final String s, final String s2) {
        final int length = "".length();
        final String[] array = new String["  ".length()];
        array["".length()] = s;
        array[" ".length()] = s2;
        this(length, array);
    }
    
    static {
        I();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.resourceDomain) + (char)(0x23 ^ 0x19) + this.resourcePath;
    }
    
    public ResourceLocation(final String s) {
        this("".length(), splitObjectName(s));
    }
    
    @Override
    public int hashCode() {
        return (0xA5 ^ 0xBA) * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(");\n\u0004,63\u0002\u0015", "DRdaO");
    }
}
