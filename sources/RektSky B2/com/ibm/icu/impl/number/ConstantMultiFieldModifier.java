package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;

public class ConstantMultiFieldModifier implements Modifier
{
    protected final char[] prefixChars;
    protected final char[] suffixChars;
    protected final NumberFormat.Field[] prefixFields;
    protected final NumberFormat.Field[] suffixFields;
    private final boolean overwrite;
    private final boolean strong;
    
    public ConstantMultiFieldModifier(final NumberStringBuilder prefix, final NumberStringBuilder suffix, final boolean overwrite, final boolean strong) {
        this.prefixChars = prefix.toCharArray();
        this.suffixChars = suffix.toCharArray();
        this.prefixFields = prefix.toFieldArray();
        this.suffixFields = suffix.toFieldArray();
        this.overwrite = overwrite;
        this.strong = strong;
    }
    
    @Override
    public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
        int length = output.insert(leftIndex, this.prefixChars, this.prefixFields);
        if (this.overwrite) {
            length += output.splice(leftIndex + length, rightIndex + length, "", 0, 0, null);
        }
        length += output.insert(rightIndex + length, this.suffixChars, this.suffixFields);
        return length;
    }
    
    @Override
    public int getPrefixLength() {
        return this.prefixChars.length;
    }
    
    @Override
    public int getCodePointCount() {
        return Character.codePointCount(this.prefixChars, 0, this.prefixChars.length) + Character.codePointCount(this.suffixChars, 0, this.suffixChars.length);
    }
    
    @Override
    public boolean isStrong() {
        return this.strong;
    }
    
    @Override
    public String toString() {
        final NumberStringBuilder temp = new NumberStringBuilder();
        this.apply(temp, 0, 0);
        final int prefixLength = this.getPrefixLength();
        return String.format("<ConstantMultiFieldModifier prefix:'%s' suffix:'%s'>", temp.subSequence(0, prefixLength), temp.subSequence(prefixLength, temp.length()));
    }
}
