/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.Modifier;
import java.text.Format;
import java.util.Arrays;

public class ConstantMultiFieldModifier
implements Modifier {
    protected final char[] prefixChars;
    protected final char[] suffixChars;
    protected final Format.Field[] prefixFields;
    protected final Format.Field[] suffixFields;
    private final boolean overwrite;
    private final boolean strong;
    private final Modifier.Parameters parameters;

    public ConstantMultiFieldModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean bl, boolean bl2) {
        this(formattedStringBuilder, formattedStringBuilder2, bl, bl2, null);
    }

    public ConstantMultiFieldModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean bl, boolean bl2, Modifier.Parameters parameters) {
        this.prefixChars = formattedStringBuilder.toCharArray();
        this.suffixChars = formattedStringBuilder2.toCharArray();
        this.prefixFields = formattedStringBuilder.toFieldArray();
        this.suffixFields = formattedStringBuilder2.toFieldArray();
        this.overwrite = bl;
        this.strong = bl2;
        this.parameters = parameters;
    }

    @Override
    public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = formattedStringBuilder.insert(n, this.prefixChars, this.prefixFields);
        if (this.overwrite) {
            n3 += formattedStringBuilder.splice(n + n3, n2 + n3, "", 0, 0, null);
        }
        n3 += formattedStringBuilder.insert(n2 + n3, this.suffixChars, this.suffixFields);
        return n3;
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
    public boolean containsField(Format.Field field) {
        int n;
        for (n = 0; n < this.prefixFields.length; ++n) {
            if (this.prefixFields[n] != field) continue;
            return false;
        }
        for (n = 0; n < this.suffixFields.length; ++n) {
            if (this.suffixFields[n] != field) continue;
            return false;
        }
        return true;
    }

    @Override
    public Modifier.Parameters getParameters() {
        return this.parameters;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof ConstantMultiFieldModifier)) {
            return true;
        }
        ConstantMultiFieldModifier constantMultiFieldModifier = (ConstantMultiFieldModifier)modifier;
        if (this.parameters != null && constantMultiFieldModifier.parameters != null && this.parameters.obj == constantMultiFieldModifier.parameters.obj) {
            return false;
        }
        return Arrays.equals(this.prefixChars, constantMultiFieldModifier.prefixChars) && Arrays.equals(this.prefixFields, constantMultiFieldModifier.prefixFields) && Arrays.equals(this.suffixChars, constantMultiFieldModifier.suffixChars) && Arrays.equals(this.suffixFields, constantMultiFieldModifier.suffixFields) && this.overwrite == constantMultiFieldModifier.overwrite && this.strong == constantMultiFieldModifier.strong;
    }

    public String toString() {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        this.apply(formattedStringBuilder, 0, 0);
        int n = this.getPrefixLength();
        return String.format("<ConstantMultiFieldModifier prefix:'%s' suffix:'%s'>", formattedStringBuilder.subSequence(0, n), formattedStringBuilder.subSequence(n, formattedStringBuilder.length()));
    }
}

