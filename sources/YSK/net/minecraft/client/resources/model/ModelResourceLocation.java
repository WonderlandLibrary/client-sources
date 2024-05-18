package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import org.apache.commons.lang3.*;

public class ModelResourceLocation extends ResourceLocation
{
    private static final String[] I;
    private final String variant;
    
    public ModelResourceLocation(final ResourceLocation resourceLocation, final String s) {
        this(resourceLocation.toString(), s);
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + (char)(0x74 ^ 0x57) + this.variant;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o instanceof ModelResourceLocation && super.equals(o)) {
            return this.variant.equals(((ModelResourceLocation)o).variant);
        }
        return "".length() != 0;
    }
    
    @Override
    public int hashCode() {
        return (0xA3 ^ 0xBC) * super.hashCode() + this.variant.hashCode();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0019%&/\u001b\u001b", "wJTBz");
        ModelResourceLocation.I[" ".length()] = I("=\u0007\u001b\u001e\u0011?", "Shisp");
    }
    
    public ModelResourceLocation(final String s, final String s2) {
        final int length = "".length();
        final StringBuilder append = new StringBuilder(String.valueOf(s)).append((char)(0xB4 ^ 0x97));
        String s3;
        if (s2 == null) {
            s3 = ModelResourceLocation.I[" ".length()];
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            s3 = s2;
        }
        this(length, parsePathString(append.append(s3).toString()));
    }
    
    protected ModelResourceLocation(final int n, final String... array) {
        final int length = "".length();
        final String[] array2 = new String["  ".length()];
        array2["".length()] = array["".length()];
        array2[" ".length()] = array[" ".length()];
        super(length, array2);
        String lowerCase;
        if (StringUtils.isEmpty((CharSequence)array["  ".length()])) {
            lowerCase = ModelResourceLocation.I["".length()];
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            lowerCase = array["  ".length()].toLowerCase();
        }
        this.variant = lowerCase;
    }
    
    public ModelResourceLocation(final String s) {
        this("".length(), parsePathString(s));
    }
    
    protected static String[] parsePathString(final String s) {
        final String[] array = new String["   ".length()];
        array[" ".length()] = s;
        final String[] array2 = array;
        final int index = s.indexOf(0x73 ^ 0x50);
        String substring = s;
        if (index >= 0) {
            array2["  ".length()] = s.substring(index + " ".length(), s.length());
            if (index > " ".length()) {
                substring = s.substring("".length(), index);
            }
        }
        System.arraycopy(ResourceLocation.splitObjectName(substring), "".length(), array2, "".length(), "  ".length());
        return array2;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getVariant() {
        return this.variant;
    }
}
