package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyInteger extends PropertyHelper<Integer>
{
    private static final String[] I;
    private final ImmutableSet<Integer> allowedValues;
    
    protected PropertyInteger(final String s, final int n, final int n2) {
        super(s, Integer.class);
        if (n < 0) {
            throw new IllegalArgumentException(PropertyInteger.I["".length()] + s + PropertyInteger.I[" ".length()]);
        }
        if (n2 <= n) {
            throw new IllegalArgumentException(PropertyInteger.I["  ".length()] + s + PropertyInteger.I["   ".length()] + n + PropertyInteger.I[0x91 ^ 0x95]);
        }
        final HashSet hashSet = Sets.newHashSet();
        int i = n;
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i <= n2) {
            hashSet.add(i);
            ++i;
        }
        this.allowedValues = (ImmutableSet<Integer>)ImmutableSet.copyOf((Collection)hashSet);
    }
    
    static {
        I();
    }
    
    @Override
    public Collection<Integer> getAllowedValues() {
        return (Collection<Integer>)this.allowedValues;
    }
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName((Integer)comparable);
    }
    
    @Override
    public String getName(final Integer n) {
        return n.toString();
    }
    
    private static void I() {
        (I = new String[0x3C ^ 0x39])["".length()] = I(")\u0001\u0001D7\u0005\u0004\u001a\u0001a\u000b\u000eO", "dhodA");
        PropertyInteger.I[" ".length()] = I("h(\u0010\u0016\fh'\u0000EHh*\u0017E\u001f: \u0004\u0011\u001d:", "HEeex");
        PropertyInteger.I["  ".length()] = I("\u0003'\u0016Z//*\u001b\u001fy! N", "NFnzY");
        PropertyInteger.I["   ".length()] = I("F5%\u0004>F:5W-\u0014=1\u0003/\u0014x$\u001f+\bx=\u001e$Fp", "fXPwJ");
        PropertyInteger.I[0x1B ^ 0x1F] = I("_", "vzpaA");
    }
    
    public static PropertyInteger create(final String s, final int n, final int n2) {
        return new PropertyInteger(s, n, n2);
    }
    
    @Override
    public int hashCode() {
        return (0x17 ^ 0x8) * super.hashCode() + this.allowedValues.hashCode();
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
            if (false) {
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
        if (o == null || this.getClass() != o.getClass()) {
            return "".length() != 0;
        }
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        return this.allowedValues.equals((Object)((PropertyInteger)o).allowedValues);
    }
}
