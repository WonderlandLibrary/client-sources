/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.Modifier;
import java.text.Format;

public class ConstantAffixModifier
implements Modifier {
    public static final ConstantAffixModifier EMPTY;
    private final String prefix;
    private final String suffix;
    private final Format.Field field;
    private final boolean strong;
    static final boolean $assertionsDisabled;

    public ConstantAffixModifier(String string, String string2, Format.Field field, boolean bl) {
        this.prefix = string == null ? "" : string;
        this.suffix = string2 == null ? "" : string2;
        this.field = field;
        this.strong = bl;
    }

    public ConstantAffixModifier() {
        this.prefix = "";
        this.suffix = "";
        this.field = null;
        this.strong = false;
    }

    @Override
    public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = formattedStringBuilder.insert(n2, this.suffix, this.field);
        return n3 += formattedStringBuilder.insert(n, this.prefix, this.field);
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
    public boolean containsField(Format.Field field) {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return true;
    }

    @Override
    public Modifier.Parameters getParameters() {
        return null;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof ConstantAffixModifier)) {
            return true;
        }
        ConstantAffixModifier constantAffixModifier = (ConstantAffixModifier)modifier;
        return this.prefix.equals(constantAffixModifier.prefix) && this.suffix.equals(constantAffixModifier.suffix) && this.field == constantAffixModifier.field && this.strong == constantAffixModifier.strong;
    }

    public String toString() {
        return String.format("<ConstantAffixModifier prefix:'%s' suffix:'%s'>", this.prefix, this.suffix);
    }

    static {
        $assertionsDisabled = !ConstantAffixModifier.class.desiredAssertionStatus();
        EMPTY = new ConstantAffixModifier();
    }
}

