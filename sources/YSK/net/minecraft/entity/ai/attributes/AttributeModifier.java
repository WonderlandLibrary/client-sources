package net.minecraft.entity.ai.attributes;

import org.apache.commons.lang3.*;
import io.netty.util.internal.*;
import net.minecraft.util.*;
import java.util.*;

public class AttributeModifier
{
    private final String name;
    private final int operation;
    private static final String[] I;
    private final UUID id;
    private final double amount;
    private boolean isSaved;
    
    public AttributeModifier(final UUID id, final String name, final double amount, final int operation) {
        this.isSaved = (" ".length() != 0);
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
        Validate.notEmpty((CharSequence)name, AttributeModifier.I["".length()], new Object["".length()]);
        Validate.inclusiveBetween(0L, 2L, (long)operation, AttributeModifier.I[" ".length()]);
    }
    
    private static void I() {
        (I = new String[0x79 ^ 0x7E])["".length()] = I("(\u00055\f\u0003\f\u000f#E\u000b\u0004\u00074E\u0006\u0004\u0004?\n\u0011E\b4E\u0000\b\u001a%\u001c", "ejQee");
        AttributeModifier.I[" ".length()] = I("?#!\u0003\u0019\u001f)w\r\u0005\u0013?6\u0016\u001c\u0019#", "vMWbu");
        AttributeModifier.I["  ".length()] = I("\u001b\u001a;9*8\u001b;.\u000e5\n&-*?\u001c4*.5\u001b!?~", "ZnOKC");
        AttributeModifier.I["   ".length()] = I("Bb\u0005\u0019&\u001c#\u001e\u0000,\u0000\u007f", "nBjiC");
        AttributeModifier.I[0xB2 ^ 0xB6] = I("cL#\u00115*Qj", "OlMpX");
        AttributeModifier.I[0xB ^ 0xE] = I("|x>2O", "PXWVr");
        AttributeModifier.I[0x1C ^ 0x1A] = I("[O:/\u0007\u001e\u000e%#\u000f\u0012R", "woIJu");
    }
    
    public AttributeModifier setSaved(final boolean isSaved) {
        this.isSaved = isSaved;
        return this;
    }
    
    public int getOperation() {
        return this.operation;
    }
    
    @Override
    public int hashCode() {
        int n;
        if (this.id != null) {
            n = this.id.hashCode();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public AttributeModifier(final String s, final double n, final int n2) {
        this(MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()), s, n, n2);
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isSaved() {
        return this.isSaved;
    }
    
    static {
        I();
    }
    
    @Override
    public String toString() {
        return AttributeModifier.I["  ".length()] + this.amount + AttributeModifier.I["   ".length()] + this.operation + AttributeModifier.I[0x33 ^ 0x37] + this.name + (char)(0x67 ^ 0x40) + AttributeModifier.I[0x7B ^ 0x7E] + this.id + AttributeModifier.I[0xBB ^ 0xBD] + this.isSaved + (char)(0x0 ^ 0x7D);
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final AttributeModifier attributeModifier = (AttributeModifier)o;
            if (this.id != null) {
                if (!this.id.equals(attributeModifier.id)) {
                    return "".length() != 0;
                }
            }
            else if (attributeModifier.id != null) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public UUID getID() {
        return this.id;
    }
}
