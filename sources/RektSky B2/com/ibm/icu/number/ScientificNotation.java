package com.ibm.icu.number;

import com.ibm.icu.impl.number.*;
import com.ibm.icu.text.*;

public class ScientificNotation extends Notation implements Cloneable
{
    int engineeringInterval;
    boolean requireMinInt;
    int minExponentDigits;
    NumberFormatter.SignDisplay exponentSignDisplay;
    
    ScientificNotation(final int engineeringInterval, final boolean requireMinInt, final int minExponentDigits, final NumberFormatter.SignDisplay exponentSignDisplay) {
        this.engineeringInterval = engineeringInterval;
        this.requireMinInt = requireMinInt;
        this.minExponentDigits = minExponentDigits;
        this.exponentSignDisplay = exponentSignDisplay;
    }
    
    public ScientificNotation withMinExponentDigits(final int minExponentDigits) {
        if (minExponentDigits >= 1 && minExponentDigits <= 999) {
            final ScientificNotation other = (ScientificNotation)this.clone();
            other.minExponentDigits = minExponentDigits;
            return other;
        }
        throw new IllegalArgumentException("Integer digits must be between 1 and 999 (inclusive)");
    }
    
    public ScientificNotation withExponentSignDisplay(final NumberFormatter.SignDisplay exponentSignDisplay) {
        final ScientificNotation other = (ScientificNotation)this.clone();
        other.exponentSignDisplay = exponentSignDisplay;
        return other;
    }
    
    @Deprecated
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }
    
    MicroPropsGenerator withLocaleData(final DecimalFormatSymbols symbols, final boolean build, final MicroPropsGenerator parent) {
        return new ScientificHandler(this, symbols, build, parent);
    }
    
    private static class ScientificHandler implements MicroPropsGenerator, MultiplierProducer, Modifier
    {
        final ScientificNotation notation;
        final DecimalFormatSymbols symbols;
        final ScientificModifier[] precomputedMods;
        final MicroPropsGenerator parent;
        int exponent;
        
        private ScientificHandler(final ScientificNotation notation, final DecimalFormatSymbols symbols, final boolean safe, final MicroPropsGenerator parent) {
            this.notation = notation;
            this.symbols = symbols;
            this.parent = parent;
            if (safe) {
                this.precomputedMods = new ScientificModifier[25];
                for (int i = -12; i <= 12; ++i) {
                    this.precomputedMods[i + 12] = new ScientificModifier(i, this);
                }
            }
            else {
                this.precomputedMods = null;
            }
        }
        
        @Override
        public MicroProps processQuantity(final DecimalQuantity quantity) {
            final MicroProps micros = this.parent.processQuantity(quantity);
            assert micros.rounder != null;
            int exponent;
            if (quantity.isZero()) {
                if (this.notation.requireMinInt && micros.rounder instanceof Precision.SignificantRounderImpl) {
                    ((Precision.SignificantRounderImpl)micros.rounder).apply(quantity, this.notation.engineeringInterval);
                    exponent = 0;
                }
                else {
                    micros.rounder.apply(quantity);
                    exponent = 0;
                }
            }
            else {
                exponent = -micros.rounder.chooseMultiplierAndApply(quantity, this);
            }
            if (this.precomputedMods != null && exponent >= -12 && exponent <= 12) {
                micros.modInner = this.precomputedMods[exponent + 12];
            }
            else if (this.precomputedMods != null) {
                micros.modInner = new ScientificModifier(exponent, this);
            }
            else {
                this.exponent = exponent;
                micros.modInner = this;
            }
            micros.rounder = Precision.constructPassThrough();
            return micros;
        }
        
        @Override
        public int getMultiplier(final int magnitude) {
            final int interval = this.notation.engineeringInterval;
            int digitsShown;
            if (this.notation.requireMinInt) {
                digitsShown = interval;
            }
            else if (interval <= 1) {
                digitsShown = 1;
            }
            else {
                digitsShown = (magnitude % interval + interval) % interval + 1;
            }
            return digitsShown - magnitude - 1;
        }
        
        @Override
        public int getPrefixLength() {
            return 0;
        }
        
        @Override
        public int getCodePointCount() {
            throw new AssertionError();
        }
        
        @Override
        public boolean isStrong() {
            return true;
        }
        
        @Override
        public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
            return this.doApply(this.exponent, output, rightIndex);
        }
        
        private int doApply(final int exponent, final NumberStringBuilder output, final int rightIndex) {
            int i = rightIndex;
            i += output.insert(i, this.symbols.getExponentSeparator(), NumberFormat.Field.EXPONENT_SYMBOL);
            if (exponent < 0 && this.notation.exponentSignDisplay != NumberFormatter.SignDisplay.NEVER) {
                i += output.insert(i, this.symbols.getMinusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            }
            else if (exponent >= 0 && this.notation.exponentSignDisplay == NumberFormatter.SignDisplay.ALWAYS) {
                i += output.insert(i, this.symbols.getPlusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            }
            for (int disp = Math.abs(exponent), j = 0; j < this.notation.minExponentDigits || disp > 0; ++j, disp /= 10) {
                final int d = disp % 10;
                final String digitString = this.symbols.getDigitStringsLocal()[d];
                i += output.insert(i - j, digitString, NumberFormat.Field.EXPONENT);
            }
            return i - rightIndex;
        }
    }
    
    private static class ScientificModifier implements Modifier
    {
        final int exponent;
        final ScientificHandler handler;
        
        ScientificModifier(final int exponent, final ScientificHandler handler) {
            this.exponent = exponent;
            this.handler = handler;
        }
        
        @Override
        public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
            return this.handler.doApply(this.exponent, output, rightIndex);
        }
        
        @Override
        public int getPrefixLength() {
            return 0;
        }
        
        @Override
        public int getCodePointCount() {
            throw new AssertionError();
        }
        
        @Override
        public boolean isStrong() {
            return true;
        }
    }
}
