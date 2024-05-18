package net.minecraft.entity.ai.attributes;

import net.minecraft.util.*;

public class RangedAttribute extends BaseAttribute
{
    private final double minimumValue;
    private String description;
    private static final String[] I;
    private final double maximumValue;
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("7\u000e%<\u0017\u000f\nk#\u001b\u0016\u0012.u\u0019\u001b\t%:\u000eZ\u0005.u\u0018\u0013\u0000,0\bZ\u0013#4\u0014Z\n*-\u0013\u0017\u0012&u\f\u001b\u000b>0[", "zgKUz");
        RangedAttribute.I[" ".length()] = I(" 13\u0012\u0005\b u\u0005\u0011\b!0S\u0013\u0005:;\u001c\u0004D60S\u001c\u000b#0\u0001P\u0010<4\u001dP\t=;\u001a\u001d\u00119u\u0005\u0011\b!0R", "dTUsp");
        RangedAttribute.I["  ".length()] = I("\r\"\u000108%3G',%2\u0002q.()\t>9i%\u0002q/  \u00004?i3\u000f0#i*\u0006)$$2\nq;(+\u00124l", "IGgQM");
    }
    
    public RangedAttribute(final IAttribute attribute, final String s, final double n, final double minimumValue, final double maximumValue) {
        super(attribute, s, n);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException(RangedAttribute.I["".length()]);
        }
        if (n < minimumValue) {
            throw new IllegalArgumentException(RangedAttribute.I[" ".length()]);
        }
        if (n > maximumValue) {
            throw new IllegalArgumentException(RangedAttribute.I["  ".length()]);
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    static {
        I();
    }
    
    public RangedAttribute setDescription(final String description) {
        this.description = description;
        return this;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public double clampValue(double clamp_double) {
        clamp_double = MathHelper.clamp_double(clamp_double, this.minimumValue, this.maximumValue);
        return clamp_double;
    }
}
