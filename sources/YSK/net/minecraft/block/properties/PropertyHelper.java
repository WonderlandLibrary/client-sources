package net.minecraft.block.properties;

import com.google.common.base.*;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T>
{
    private static final String[] I;
    private final String name;
    private final Class<T> valueClass;
    
    protected PropertyHelper(final String name, final Class<T> valueClass) {
        this.valueClass = valueClass;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(PropertyHelper.I["".length()], (Object)this.name).add(PropertyHelper.I[" ".length()], (Object)this.valueClass).add(PropertyHelper.I["  ".length()], (Object)this.getAllowedValues()).toString();
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
    public Class<T> getValueClass() {
        return this.valueClass;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return "".length() != 0;
        }
        final PropertyHelper propertyHelper = (PropertyHelper)o;
        if (this.valueClass.equals(propertyHelper.valueClass) && this.name.equals(propertyHelper.name)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("6\n9!", "XkTDO");
        PropertyHelper.I[" ".length()] = I("\u001b;\b9\u0015", "xWiCo");
        PropertyHelper.I["  ".length()] = I("\u0003\u0006.?\u000e\u0006", "ugBJk");
    }
    
    @Override
    public int hashCode() {
        return (0xB9 ^ 0xA6) * this.valueClass.hashCode() + this.name.hashCode();
    }
}
