/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Modifier;

public class Padder {
    public static final String FALLBACK_PADDING_STRING = " ";
    public static final Padder NONE;
    String paddingString;
    int targetWidth;
    PadPosition position;
    static final boolean $assertionsDisabled;

    public Padder(String string, int n, PadPosition padPosition) {
        this.paddingString = string == null ? FALLBACK_PADDING_STRING : string;
        this.targetWidth = n;
        this.position = padPosition == null ? PadPosition.BEFORE_PREFIX : padPosition;
    }

    public static Padder none() {
        return NONE;
    }

    public static Padder codePoints(int n, int n2, PadPosition padPosition) {
        if (n2 >= 0) {
            String string = String.valueOf(Character.toChars(n));
            return new Padder(string, n2, padPosition);
        }
        throw new IllegalArgumentException("Padding width must not be negative");
    }

    public static Padder forProperties(DecimalFormatProperties decimalFormatProperties) {
        return new Padder(decimalFormatProperties.getPadString(), decimalFormatProperties.getFormatWidth(), decimalFormatProperties.getPadPosition());
    }

    public boolean isValid() {
        return this.targetWidth > 0;
    }

    public int padAndApply(Modifier modifier, Modifier modifier2, FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = modifier.getCodePointCount() + modifier2.getCodePointCount();
        int n4 = this.targetWidth - n3 - formattedStringBuilder.codePointCount();
        if (!($assertionsDisabled || n == 0 && n2 == formattedStringBuilder.length())) {
            throw new AssertionError();
        }
        int n5 = 0;
        if (n4 <= 0) {
            n5 += modifier.apply(formattedStringBuilder, n, n2);
            n5 += modifier2.apply(formattedStringBuilder, n, n2 + n5);
            return n5;
        }
        if (this.position == PadPosition.AFTER_PREFIX) {
            n5 += Padder.addPaddingHelper(this.paddingString, n4, formattedStringBuilder, n);
        } else if (this.position == PadPosition.BEFORE_SUFFIX) {
            n5 += Padder.addPaddingHelper(this.paddingString, n4, formattedStringBuilder, n2 + n5);
        }
        n5 += modifier.apply(formattedStringBuilder, n, n2 + n5);
        n5 += modifier2.apply(formattedStringBuilder, n, n2 + n5);
        if (this.position == PadPosition.BEFORE_PREFIX) {
            n5 += Padder.addPaddingHelper(this.paddingString, n4, formattedStringBuilder, n);
        } else if (this.position == PadPosition.AFTER_SUFFIX) {
            n5 += Padder.addPaddingHelper(this.paddingString, n4, formattedStringBuilder, n2 + n5);
        }
        return n5;
    }

    private static int addPaddingHelper(String string, int n, FormattedStringBuilder formattedStringBuilder, int n2) {
        for (int i = 0; i < n; ++i) {
            formattedStringBuilder.insert(n2, string, null);
        }
        return string.length() * n;
    }

    static {
        $assertionsDisabled = !Padder.class.desiredAssertionStatus();
        NONE = new Padder(null, -1, null);
    }

    public static enum PadPosition {
        BEFORE_PREFIX,
        AFTER_PREFIX,
        BEFORE_SUFFIX,
        AFTER_SUFFIX;


        public static PadPosition fromOld(int n) {
            switch (n) {
                case 0: {
                    return BEFORE_PREFIX;
                }
                case 1: {
                    return AFTER_PREFIX;
                }
                case 2: {
                    return BEFORE_SUFFIX;
                }
                case 3: {
                    return AFTER_SUFFIX;
                }
            }
            throw new IllegalArgumentException("Don't know how to map " + n);
        }

        public int toOld() {
            switch (1.$SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[this.ordinal()]) {
                case 1: {
                    return 1;
                }
                case 2: {
                    return 0;
                }
                case 3: {
                    return 1;
                }
                case 4: {
                    return 0;
                }
            }
            return 1;
        }
    }
}

