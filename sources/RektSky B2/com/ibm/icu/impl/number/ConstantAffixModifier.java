package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;

public class ConstantAffixModifier implements Modifier
{
    public static final ConstantAffixModifier EMPTY;
    private final String prefix;
    private final String suffix;
    private final NumberFormat.Field field;
    private final boolean strong;
    
    public ConstantAffixModifier(final String prefix, final String suffix, final NumberFormat.Field field, final boolean strong) {
        this.prefix = ((prefix == null) ? "" : prefix);
        this.suffix = ((suffix == null) ? "" : suffix);
        this.field = field;
        this.strong = strong;
    }
    
    public ConstantAffixModifier() {
        this.prefix = "";
        this.suffix = "";
        this.field = null;
        this.strong = false;
    }
    
    @Override
    public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
        int length = output.insert(rightIndex, this.suffix, this.field);
        length += output.insert(leftIndex, this.prefix, this.field);
        return length;
    }
    
    @Override
    public int getPrefixLength() {
        return this.prefix.length();
    }
    
    @Override
    public int getCodePointCount() {
        return this.prefix.codePointCount(0, this.prefix.length()) + this.suffix.codePointCount(0, this.suffix.length());
    }
    
    @Override
    public boolean isStrong() {
        return this.strong;
    }
    
    @Override
    public String toString() {
        return String.format("<ConstantAffixModifier prefix:'%s' suffix:'%s'>", this.prefix, this.suffix);
    }
    
    static {
        EMPTY = new ConstantAffixModifier();
    }
}
