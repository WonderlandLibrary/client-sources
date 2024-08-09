/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.range.PrefixInfixSuffixLengthHelper;
import com.ibm.icu.util.ICUException;
import java.text.Format;

public class SimpleModifier
implements Modifier {
    private final String compiledPattern;
    private final Format.Field field;
    private final boolean strong;
    private final int prefixLength;
    private final int suffixOffset;
    private final int suffixLength;
    private final Modifier.Parameters parameters;
    private static final int ARG_NUM_LIMIT = 256;
    static final boolean $assertionsDisabled = !SimpleModifier.class.desiredAssertionStatus();

    public SimpleModifier(String string, Format.Field field, boolean bl) {
        this(string, field, bl, null);
    }

    public SimpleModifier(String string, Format.Field field, boolean bl, Modifier.Parameters parameters) {
        if (!$assertionsDisabled && string == null) {
            throw new AssertionError();
        }
        this.compiledPattern = string;
        this.field = field;
        this.strong = bl;
        this.parameters = parameters;
        int n = SimpleFormatterImpl.getArgumentLimit(string);
        if (n == 0) {
            this.prefixLength = string.charAt(1) - 256;
            if (!$assertionsDisabled && 2 + this.prefixLength != string.length()) {
                throw new AssertionError();
            }
            this.suffixOffset = -1;
            this.suffixLength = 0;
        } else {
            if (!$assertionsDisabled && n != 1) {
                throw new AssertionError();
            }
            if (string.charAt(1) != '\u0000') {
                this.prefixLength = string.charAt(1) - 256;
                this.suffixOffset = 3 + this.prefixLength;
            } else {
                this.prefixLength = 0;
                this.suffixOffset = 2;
            }
            this.suffixLength = 3 + this.prefixLength < string.length() ? string.charAt(this.suffixOffset) - 256 : 0;
        }
    }

    @Override
    public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        return this.formatAsPrefixSuffix(formattedStringBuilder, n, n2);
    }

    @Override
    public int getPrefixLength() {
        return this.prefixLength;
    }

    @Override
    public int getCodePointCount() {
        int n = 0;
        if (this.prefixLength > 0) {
            n += Character.codePointCount(this.compiledPattern, 2, 2 + this.prefixLength);
        }
        if (this.suffixLength > 0) {
            n += Character.codePointCount(this.compiledPattern, 1 + this.suffixOffset, 1 + this.suffixOffset + this.suffixLength);
        }
        return n;
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
        return this.parameters;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof SimpleModifier)) {
            return true;
        }
        SimpleModifier simpleModifier = (SimpleModifier)modifier;
        if (this.parameters != null && simpleModifier.parameters != null && this.parameters.obj == simpleModifier.parameters.obj) {
            return false;
        }
        return this.compiledPattern.equals(simpleModifier.compiledPattern) && this.field == simpleModifier.field && this.strong == simpleModifier.strong;
    }

    public int formatAsPrefixSuffix(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        if (this.suffixOffset == -1) {
            return formattedStringBuilder.splice(n, n2, this.compiledPattern, 2, 2 + this.prefixLength, this.field);
        }
        if (this.prefixLength > 0) {
            formattedStringBuilder.insert(n, this.compiledPattern, 2, 2 + this.prefixLength, this.field);
        }
        if (this.suffixLength > 0) {
            formattedStringBuilder.insert(n2 + this.prefixLength, this.compiledPattern, 1 + this.suffixOffset, 1 + this.suffixOffset + this.suffixLength, this.field);
        }
        return this.prefixLength + this.suffixLength;
    }

    public static void formatTwoArgPattern(String string, FormattedStringBuilder formattedStringBuilder, int n, PrefixInfixSuffixLengthHelper prefixInfixSuffixLengthHelper, Format.Field field) {
        int n2;
        int n3 = SimpleFormatterImpl.getArgumentLimit(string);
        if (n3 != 2) {
            throw new ICUException();
        }
        int n4 = 1;
        int n5 = 0;
        int n6 = string.charAt(n4);
        ++n4;
        if (n6 < 256) {
            n6 = 0;
        } else {
            formattedStringBuilder.insert(n + n5, string, n4, n4 + (n6 -= 256), field);
            n4 += n6;
            n5 += n6;
            ++n4;
        }
        int n7 = string.charAt(n4);
        ++n4;
        if (n7 < 256) {
            n7 = 0;
        } else {
            formattedStringBuilder.insert(n + n5, string, n4, n4 + (n7 -= 256), field);
            n4 += n7;
            n5 += n7;
            ++n4;
        }
        if (n4 == string.length()) {
            n2 = 0;
        } else {
            n2 = string.charAt(n4) - 256;
            formattedStringBuilder.insert(n + n5, string, ++n4, n4 + n2, field);
            n5 += n2;
        }
        prefixInfixSuffixLengthHelper.lengthPrefix = n6;
        prefixInfixSuffixLengthHelper.lengthInfix = n7;
        prefixInfixSuffixLengthHelper.lengthSuffix = n2;
    }
}

