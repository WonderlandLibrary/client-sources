/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.ConstantAffixModifier;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.MultiplierProducer;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import java.text.Format;

public class ScientificNotation
extends Notation
implements Cloneable {
    int engineeringInterval;
    boolean requireMinInt;
    int minExponentDigits;
    NumberFormatter.SignDisplay exponentSignDisplay;

    ScientificNotation(int n, boolean bl, int n2, NumberFormatter.SignDisplay signDisplay) {
        this.engineeringInterval = n;
        this.requireMinInt = bl;
        this.minExponentDigits = n2;
        this.exponentSignDisplay = signDisplay;
    }

    public ScientificNotation withMinExponentDigits(int n) {
        if (n >= 1 && n <= 999) {
            ScientificNotation scientificNotation = (ScientificNotation)this.clone();
            scientificNotation.minExponentDigits = n;
            return scientificNotation;
        }
        throw new IllegalArgumentException("Integer digits must be between 1 and 999 (inclusive)");
    }

    public ScientificNotation withExponentSignDisplay(NumberFormatter.SignDisplay signDisplay) {
        ScientificNotation scientificNotation = (ScientificNotation)this.clone();
        scientificNotation.exponentSignDisplay = signDisplay;
        return scientificNotation;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
    }

    MicroPropsGenerator withLocaleData(DecimalFormatSymbols decimalFormatSymbols, boolean bl, MicroPropsGenerator microPropsGenerator) {
        return new ScientificHandler(this, decimalFormatSymbols, bl, microPropsGenerator, null);
    }

    private static class ScientificModifier
    implements Modifier {
        final int exponent;
        final ScientificHandler handler;
        static final boolean $assertionsDisabled = !ScientificNotation.class.desiredAssertionStatus();

        ScientificModifier(int n, ScientificHandler scientificHandler) {
            this.exponent = n;
            this.handler = scientificHandler;
        }

        @Override
        public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
            return ScientificHandler.access$100(this.handler, this.exponent, formattedStringBuilder, n2);
        }

        @Override
        public int getPrefixLength() {
            return 1;
        }

        @Override
        public int getCodePointCount() {
            return 0;
        }

        @Override
        public boolean isStrong() {
            return false;
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
            if (!(modifier instanceof ScientificModifier)) {
                return true;
            }
            ScientificModifier scientificModifier = (ScientificModifier)modifier;
            return this.exponent == scientificModifier.exponent;
        }
    }

    private static class ScientificHandler
    implements MicroPropsGenerator,
    MultiplierProducer,
    Modifier {
        final ScientificNotation notation;
        final DecimalFormatSymbols symbols;
        final ScientificModifier[] precomputedMods;
        final MicroPropsGenerator parent;
        int exponent;
        static final boolean $assertionsDisabled = !ScientificNotation.class.desiredAssertionStatus();

        private ScientificHandler(ScientificNotation scientificNotation, DecimalFormatSymbols decimalFormatSymbols, boolean bl, MicroPropsGenerator microPropsGenerator) {
            this.notation = scientificNotation;
            this.symbols = decimalFormatSymbols;
            this.parent = microPropsGenerator;
            if (bl) {
                this.precomputedMods = new ScientificModifier[25];
                for (int i = -12; i <= 12; ++i) {
                    this.precomputedMods[i + 12] = new ScientificModifier(i, this);
                }
            } else {
                this.precomputedMods = null;
            }
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            int n;
            MicroProps microProps = this.parent.processQuantity(decimalQuantity);
            if (!$assertionsDisabled && microProps.rounder == null) {
                throw new AssertionError();
            }
            if (decimalQuantity.isInfinite() || decimalQuantity.isNaN()) {
                microProps.modInner = ConstantAffixModifier.EMPTY;
                return microProps;
            }
            if (decimalQuantity.isZeroish()) {
                if (this.notation.requireMinInt && microProps.rounder instanceof Precision.SignificantRounderImpl) {
                    ((Precision.SignificantRounderImpl)microProps.rounder).apply(decimalQuantity, this.notation.engineeringInterval);
                    n = 0;
                } else {
                    microProps.rounder.apply(decimalQuantity);
                    n = 0;
                }
            } else {
                n = -microProps.rounder.chooseMultiplierAndApply(decimalQuantity, this);
            }
            if (this.precomputedMods != null && n >= -12 && n <= 12) {
                microProps.modInner = this.precomputedMods[n + 12];
            } else if (this.precomputedMods != null) {
                microProps.modInner = new ScientificModifier(n, this);
            } else {
                this.exponent = n;
                microProps.modInner = this;
            }
            microProps.rounder = Precision.constructPassThrough();
            return microProps;
        }

        @Override
        public int getMultiplier(int n) {
            int n2 = this.notation.engineeringInterval;
            int n3 = this.notation.requireMinInt ? n2 : (n2 <= 1 ? 1 : (n % n2 + n2) % n2 + 1);
            return n3 - n - 1;
        }

        @Override
        public int getPrefixLength() {
            return 1;
        }

        @Override
        public int getCodePointCount() {
            return 0;
        }

        @Override
        public boolean isStrong() {
            return false;
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
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            return null;
        }

        @Override
        public boolean semanticallyEquivalent(Modifier modifier) {
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            return true;
        }

        @Override
        public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
            return this.doApply(this.exponent, formattedStringBuilder, n2);
        }

        private int doApply(int n, FormattedStringBuilder formattedStringBuilder, int n2) {
            int n3 = n2;
            n3 += formattedStringBuilder.insert(n3, this.symbols.getExponentSeparator(), NumberFormat.Field.EXPONENT_SYMBOL);
            if (n < 0 && this.notation.exponentSignDisplay != NumberFormatter.SignDisplay.NEVER) {
                n3 += formattedStringBuilder.insert(n3, this.symbols.getMinusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            } else if (n >= 0 && this.notation.exponentSignDisplay == NumberFormatter.SignDisplay.ALWAYS) {
                n3 += formattedStringBuilder.insert(n3, this.symbols.getPlusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            }
            int n4 = Math.abs(n);
            for (int i = 0; i < this.notation.minExponentDigits || n4 > 0; ++i, n4 /= 10) {
                int n5 = n4 % 10;
                String string = this.symbols.getDigitStringsLocal()[n5];
                n3 += formattedStringBuilder.insert(n3 - i, string, NumberFormat.Field.EXPONENT);
            }
            return n3 - n2;
        }

        ScientificHandler(ScientificNotation scientificNotation, DecimalFormatSymbols decimalFormatSymbols, boolean bl, MicroPropsGenerator microPropsGenerator, 1 var5_5) {
            this(scientificNotation, decimalFormatSymbols, bl, microPropsGenerator);
        }

        static int access$100(ScientificHandler scientificHandler, int n, FormattedStringBuilder formattedStringBuilder, int n2) {
            return scientificHandler.doApply(n, formattedStringBuilder, n2);
        }
    }
}

