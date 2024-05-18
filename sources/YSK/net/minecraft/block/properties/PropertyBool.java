package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyBool extends PropertyHelper<Boolean>
{
    private final ImmutableSet<Boolean> allowedValues;
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName((Boolean)comparable);
    }
    
    @Override
    public Collection<Boolean> getAllowedValues() {
        return (Collection<Boolean>)this.allowedValues;
    }
    
    @Override
    public String getName(final Boolean b) {
        return b.toString();
    }
    
    public static PropertyBool create(final String s) {
        return new PropertyBool(s);
    }
    
    protected PropertyBool(final String s) {
        super(s, Boolean.class);
        this.allowedValues = (ImmutableSet<Boolean>)ImmutableSet.of((Object)(boolean)(" ".length() != 0), (Object)(boolean)("".length() != 0));
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
