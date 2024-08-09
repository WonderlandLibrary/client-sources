/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.UFieldPosition;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.FieldPosition;

public abstract class DecimalQuantity_AbstractBCD
implements DecimalQuantity {
    protected int scale;
    protected int precision;
    protected byte flags;
    protected static final int NEGATIVE_FLAG = 1;
    protected static final int INFINITY_FLAG = 2;
    protected static final int NAN_FLAG = 4;
    protected double origDouble;
    protected int origDelta;
    protected boolean isApproximate;
    protected int lReqPos = 0;
    protected int rReqPos = 0;
    private static final double[] DOUBLE_MULTIPLIERS;
    @Deprecated
    public boolean explicitExactDouble = false;
    static final byte[] INT64_BCD;
    private static final int SECTION_LOWER_EDGE = -1;
    private static final int SECTION_UPPER_EDGE = -2;
    static final boolean $assertionsDisabled;

    @Override
    public void copyFrom(DecimalQuantity decimalQuantity) {
        this.copyBcdFrom(decimalQuantity);
        DecimalQuantity_AbstractBCD decimalQuantity_AbstractBCD = (DecimalQuantity_AbstractBCD)decimalQuantity;
        this.lReqPos = decimalQuantity_AbstractBCD.lReqPos;
        this.rReqPos = decimalQuantity_AbstractBCD.rReqPos;
        this.scale = decimalQuantity_AbstractBCD.scale;
        this.precision = decimalQuantity_AbstractBCD.precision;
        this.flags = decimalQuantity_AbstractBCD.flags;
        this.origDouble = decimalQuantity_AbstractBCD.origDouble;
        this.origDelta = decimalQuantity_AbstractBCD.origDelta;
        this.isApproximate = decimalQuantity_AbstractBCD.isApproximate;
    }

    public DecimalQuantity_AbstractBCD clear() {
        this.lReqPos = 0;
        this.rReqPos = 0;
        this.flags = 0;
        this.setBcdToZero();
        return this;
    }

    @Override
    public void setMinInteger(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (n < this.lReqPos) {
            n = this.lReqPos;
        }
        this.lReqPos = n;
    }

    @Override
    public void setMinFraction(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        this.rReqPos = -n;
    }

    @Override
    public void applyMaxInteger(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (this.precision == 0) {
            return;
        }
        if (n <= this.scale) {
            this.setBcdToZero();
            return;
        }
        int n2 = this.getMagnitude();
        if (n <= n2) {
            this.popFromLeft(n2 - n + 1);
            this.compact();
        }
    }

    @Override
    public long getPositionFingerprint() {
        long l = 0L;
        l ^= (long)(this.lReqPos << 16);
        return l ^= (long)this.rReqPos << 32;
    }

    @Override
    public void roundToIncrement(BigDecimal bigDecimal, MathContext mathContext) {
        if (!$assertionsDisabled && bigDecimal.stripTrailingZeros().precision() == 1 && bigDecimal.stripTrailingZeros().unscaledValue().intValue() == 5 && bigDecimal.stripTrailingZeros().unscaledValue().intValue() == 1) {
            throw new AssertionError();
        }
        BigDecimal bigDecimal2 = this.toBigDecimal();
        if ((bigDecimal2 = bigDecimal2.divide(bigDecimal, 0, mathContext.getRoundingMode()).multiply(bigDecimal).round(mathContext)).signum() == 0) {
            this.setBcdToZero();
        } else {
            this.setToBigDecimal(bigDecimal2);
        }
    }

    @Override
    public void multiplyBy(BigDecimal bigDecimal) {
        if (this.isZeroish()) {
            return;
        }
        BigDecimal bigDecimal2 = this.toBigDecimal();
        bigDecimal2 = bigDecimal2.multiply(bigDecimal);
        this.setToBigDecimal(bigDecimal2);
    }

    @Override
    public void negate() {
        this.flags = (byte)(this.flags ^ 1);
    }

    @Override
    public int getMagnitude() throws ArithmeticException {
        if (this.precision == 0) {
            throw new ArithmeticException("Magnitude is not well-defined for zero");
        }
        return this.scale + this.precision - 1;
    }

    @Override
    public void adjustMagnitude(int n) {
        if (this.precision != 0) {
            this.scale = Utility.addExact(this.scale, n);
            this.origDelta = Utility.addExact(this.origDelta, n);
            Utility.addExact(this.scale, this.precision);
        }
    }

    @Override
    public StandardPlural getStandardPlural(PluralRules pluralRules) {
        if (pluralRules == null) {
            return StandardPlural.OTHER;
        }
        String string = pluralRules.select(this);
        return StandardPlural.orOtherFromString(string);
    }

    @Override
    public double getPluralOperand(PluralRules.Operand operand) {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        switch (1.$SwitchMap$com$ibm$icu$text$PluralRules$Operand[operand.ordinal()]) {
            case 1: {
                return this.isNegative() ? (double)(-this.toLong(false)) : (double)this.toLong(false);
            }
            case 2: {
                return this.toFractionLong(false);
            }
            case 3: {
                return this.toFractionLong(true);
            }
            case 4: {
                return this.fractionCount();
            }
            case 5: {
                return this.fractionCountWithoutTrailingZeros();
            }
        }
        return Math.abs(this.toDouble());
    }

    @Override
    public void populateUFieldPosition(FieldPosition fieldPosition) {
        if (fieldPosition instanceof UFieldPosition) {
            ((UFieldPosition)fieldPosition).setFractionDigits((int)this.getPluralOperand(PluralRules.Operand.v), (long)this.getPluralOperand(PluralRules.Operand.f));
        }
    }

    @Override
    public int getUpperDisplayMagnitude() {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        int n = this.scale + this.precision;
        int n2 = this.lReqPos > n ? this.lReqPos : n;
        return n2 - 1;
    }

    @Override
    public int getLowerDisplayMagnitude() {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        int n = this.scale;
        int n2 = this.rReqPos < n ? this.rReqPos : n;
        return n2;
    }

    @Override
    public byte getDigit(int n) {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        return this.getDigitPos(n - this.scale);
    }

    private int fractionCount() {
        return -this.getLowerDisplayMagnitude();
    }

    private int fractionCountWithoutTrailingZeros() {
        return Math.max(-this.scale, 0);
    }

    @Override
    public boolean isNegative() {
        return (this.flags & 1) != 0;
    }

    @Override
    public int signum() {
        return this.isNegative() ? -1 : (this.isZeroish() && !this.isInfinite() ? 0 : 1);
    }

    @Override
    public boolean isInfinite() {
        return (this.flags & 2) != 0;
    }

    @Override
    public boolean isNaN() {
        return (this.flags & 4) != 0;
    }

    @Override
    public boolean isZeroish() {
        return this.precision == 0;
    }

    public void setToInt(int n) {
        this.setBcdToZero();
        this.flags = 0;
        if (n < 0) {
            this.flags = (byte)(this.flags | 1);
            n = -n;
        }
        if (n != 0) {
            this._setToInt(n);
            this.compact();
        }
    }

    private void _setToInt(int n) {
        if (n == Integer.MIN_VALUE) {
            this.readLongToBcd(-((long)n));
        } else {
            this.readIntToBcd(n);
        }
    }

    public void setToLong(long l) {
        this.setBcdToZero();
        this.flags = 0;
        if (l < 0L) {
            this.flags = (byte)(this.flags | 1);
            l = -l;
        }
        if (l != 0L) {
            this._setToLong(l);
            this.compact();
        }
    }

    private void _setToLong(long l) {
        if (l == Long.MIN_VALUE) {
            this.readBigIntegerToBcd(BigInteger.valueOf(l).negate());
        } else if (l <= Integer.MAX_VALUE) {
            this.readIntToBcd((int)l);
        } else {
            this.readLongToBcd(l);
        }
    }

    public void setToBigInteger(BigInteger bigInteger) {
        this.setBcdToZero();
        this.flags = 0;
        if (bigInteger.signum() == -1) {
            this.flags = (byte)(this.flags | 1);
            bigInteger = bigInteger.negate();
        }
        if (bigInteger.signum() != 0) {
            this._setToBigInteger(bigInteger);
            this.compact();
        }
    }

    private void _setToBigInteger(BigInteger bigInteger) {
        if (bigInteger.bitLength() < 32) {
            this.readIntToBcd(bigInteger.intValue());
        } else if (bigInteger.bitLength() < 64) {
            this.readLongToBcd(bigInteger.longValue());
        } else {
            this.readBigIntegerToBcd(bigInteger);
        }
    }

    public void setToDouble(double d) {
        this.setBcdToZero();
        this.flags = 0;
        if (Double.doubleToRawLongBits(d) < 0L) {
            this.flags = (byte)(this.flags | 1);
            d = -d;
        }
        if (Double.isNaN(d)) {
            this.flags = (byte)(this.flags | 4);
        } else if (Double.isInfinite(d)) {
            this.flags = (byte)(this.flags | 2);
        } else if (d != 0.0) {
            this._setToDoubleFast(d);
            this.compact();
        }
    }

    private void _setToDoubleFast(double d) {
        int n;
        this.isApproximate = true;
        this.origDouble = d;
        this.origDelta = 0;
        long l = Double.doubleToLongBits(d);
        int n2 = (int)((l & 0x7FF0000000000000L) >> 52) - 1023;
        if (n2 <= 52 && (double)((long)d) == d) {
            this._setToLong((long)d);
            return;
        }
        int n3 = (int)((double)(52 - n2) / 3.32192809489);
        if (n3 >= 0) {
            for (n = n3; n >= 22; n -= 22) {
                d *= 1.0E22;
            }
            d *= DOUBLE_MULTIPLIERS[n];
        } else {
            for (n = n3; n <= -22; n += 22) {
                d /= 1.0E22;
            }
            d /= DOUBLE_MULTIPLIERS[-n];
        }
        long l2 = Math.round(d);
        if (l2 != 0L) {
            this._setToLong(l2);
            this.scale -= n3;
        }
    }

    private void convertToAccurateDouble() {
        double d = this.origDouble;
        if (!$assertionsDisabled && d == 0.0) {
            throw new AssertionError();
        }
        int n = this.origDelta;
        this.setBcdToZero();
        String string = Double.toString(d);
        if (string.indexOf(69) != -1) {
            if (!$assertionsDisabled && string.indexOf(46) != 1) {
                throw new AssertionError();
            }
            int n2 = string.indexOf(69);
            this._setToLong(Long.parseLong(string.charAt(0) + string.substring(2, n2)));
            this.scale += Integer.parseInt(string.substring(n2 + 1)) - (n2 - 1) + 1;
        } else if (string.charAt(0) == '0') {
            if (!$assertionsDisabled && string.indexOf(46) != 1) {
                throw new AssertionError();
            }
            this._setToLong(Long.parseLong(string.substring(2)));
            this.scale += 2 - string.length();
        } else if (string.charAt(string.length() - 1) == '0') {
            if (!$assertionsDisabled && string.indexOf(46) != string.length() - 2) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && string.length() - 2 > 18) {
                throw new AssertionError();
            }
            this._setToLong(Long.parseLong(string.substring(0, string.length() - 2)));
        } else {
            int n3 = string.indexOf(46);
            this._setToLong(Long.parseLong(string.substring(0, n3) + string.substring(n3 + 1)));
            this.scale += n3 - string.length() + 1;
        }
        this.scale += n;
        this.compact();
        this.explicitExactDouble = true;
    }

    @Override
    public void setToBigDecimal(BigDecimal bigDecimal) {
        this.setBcdToZero();
        this.flags = 0;
        if (bigDecimal.signum() == -1) {
            this.flags = (byte)(this.flags | 1);
            bigDecimal = bigDecimal.negate();
        }
        if (bigDecimal.signum() != 0) {
            this._setToBigDecimal(bigDecimal);
            this.compact();
        }
    }

    private void _setToBigDecimal(BigDecimal bigDecimal) {
        int n = bigDecimal.scale();
        bigDecimal = bigDecimal.scaleByPowerOfTen(n);
        BigInteger bigInteger = bigDecimal.toBigInteger();
        this._setToBigInteger(bigInteger);
        this.scale -= n;
    }

    public long toLong(boolean bl) {
        if (!($assertionsDisabled || bl || this.fitsInLong())) {
            throw new AssertionError();
        }
        long l = 0L;
        int n = this.scale + this.precision - 1;
        if (bl) {
            n = Math.min(n, 17);
        }
        for (int i = n; i >= 0; --i) {
            l = l * 10L + (long)this.getDigitPos(i - this.scale);
        }
        if (this.isNegative()) {
            l = -l;
        }
        return l;
    }

    public long toFractionLong(boolean bl) {
        long l = 0L;
        int n = -1;
        int n2 = this.scale;
        if (bl) {
            n2 = Math.min(n2, this.rReqPos);
        }
        while (n >= n2 && (double)l <= 1.0E17) {
            l = l * 10L + (long)this.getDigitPos(n - this.scale);
            --n;
        }
        if (!bl) {
            while (l > 0L && l % 10L == 0L) {
                l /= 10L;
            }
        }
        return l;
    }

    public boolean fitsInLong() {
        if (this.isInfinite() || this.isNaN()) {
            return true;
        }
        if (this.isZeroish()) {
            return false;
        }
        if (this.scale < 0) {
            return true;
        }
        int n = this.getMagnitude();
        if (n < 18) {
            return false;
        }
        if (n > 18) {
            return true;
        }
        for (int i = 0; i < this.precision; ++i) {
            byte by = this.getDigit(18 - i);
            if (by < INT64_BCD[i]) {
                return false;
            }
            if (by <= INT64_BCD[i]) continue;
            return true;
        }
        return this.isNegative();
    }

    @Override
    public double toDouble() {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        if (this.isNaN()) {
            return Double.NaN;
        }
        if (this.isInfinite()) {
            return this.isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.toScientificString(stringBuilder);
        return Double.valueOf(stringBuilder.toString());
    }

    @Override
    public BigDecimal toBigDecimal() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
        return this.bcdToBigDecimal();
    }

    private static int safeSubtract(int n, int n2) {
        int n3 = n - n2;
        if (n2 < 0 && n3 < n) {
            return 0;
        }
        if (n2 > 0 && n3 > n) {
            return 1;
        }
        return n3;
    }

    public void truncate() {
        if (this.scale < 0) {
            this.shiftRight(-this.scale);
            this.scale = 0;
            this.compact();
        }
    }

    @Override
    public void roundToNickel(int n, MathContext mathContext) {
        this.roundToMagnitude(n, mathContext, true);
    }

    @Override
    public void roundToMagnitude(int n, MathContext mathContext) {
        this.roundToMagnitude(n, mathContext, false);
    }

    private void roundToMagnitude(int n, MathContext mathContext, boolean bl) {
        int n2 = DecimalQuantity_AbstractBCD.safeSubtract(n, this.scale);
        int n3 = mathContext.getPrecision();
        if (n3 > 0 && this.precision - n3 > n2) {
            n2 = this.precision - n3;
        }
        int n4 = this.getDigitPos(n2);
        if ((n2 > 0 || this.isApproximate || bl && n4 != 0 && n4 != 5) && this.precision != 0) {
            int n5;
            int n6;
            int n7;
            int n8;
            byte by = this.getDigitPos(DecimalQuantity_AbstractBCD.safeSubtract(n2, 1));
            if (!this.isApproximate) {
                if (bl && n4 != 2 && n4 != 7) {
                    n8 = n4 < 2 ? 1 : (n4 < 5 ? 3 : (n4 < 7 ? 1 : 3));
                } else if (by < 5) {
                    n8 = 1;
                } else if (by > 5) {
                    n8 = 3;
                } else {
                    n8 = 2;
                    for (n7 = DecimalQuantity_AbstractBCD.safeSubtract(n2, 2); n7 >= 0; --n7) {
                        if (this.getDigitPos(n7) == 0) continue;
                        n8 = 3;
                        break;
                    }
                }
            } else {
                n6 = Math.max(0, this.precision - 14);
                if (!(by != 0 || bl && n4 != 0 && n4 != 5)) {
                    n8 = -1;
                    for (n7 = DecimalQuantity_AbstractBCD.safeSubtract(n2, 2); n7 >= n6; --n7) {
                        if (this.getDigitPos(n7) == 0) continue;
                        n8 = 1;
                        break;
                    }
                } else if (!(by != 4 || bl && n4 != 2 && n4 != 7)) {
                    n8 = 2;
                    while (n7 >= n6) {
                        if (this.getDigitPos(n7) != 9) {
                            n8 = 1;
                            break;
                        }
                        --n7;
                    }
                } else if (!(by != 5 || bl && n4 != 2 && n4 != 7)) {
                    n8 = 2;
                    while (n7 >= n6) {
                        if (this.getDigitPos(n7) != 0) {
                            n8 = 3;
                            break;
                        }
                        --n7;
                    }
                } else if (!(by != 9 || bl && n4 != 4 && n4 != 9)) {
                    n8 = -2;
                    while (n7 >= n6) {
                        if (this.getDigitPos(n7) != 9) {
                            n8 = 3;
                            break;
                        }
                        --n7;
                    }
                } else {
                    n8 = bl && n4 != 2 && n4 != 7 ? (n4 < 2 ? 1 : (n4 < 5 ? 3 : (n4 < 7 ? 1 : 3))) : (by < 5 ? 1 : 3);
                }
                n5 = RoundingUtils.roundsAtMidpoint(mathContext.getRoundingMode().ordinal());
                if (DecimalQuantity_AbstractBCD.safeSubtract(n2, 1) < this.precision - 14 || n5 != 0 && n8 == 2 || n5 == 0 && n8 < 0) {
                    this.convertToAccurateDouble();
                    this.roundToMagnitude(n, mathContext, bl);
                    return;
                }
                this.isApproximate = false;
                this.origDouble = 0.0;
                this.origDelta = 0;
                if (!(n2 > 0 || bl && n4 != 0 && n4 != 5)) {
                    return;
                }
                if (n8 == -1) {
                    n8 = 1;
                }
                if (n8 == -2) {
                    n8 = 3;
                }
            }
            n7 = bl ? (n4 < 2 || n4 > 7 || n4 == 2 && n8 != 3 || n4 == 7 && n8 == 3 ? 1 : 0) : (n4 % 2 == 0 ? 1 : 0);
            n6 = RoundingUtils.getRoundingDirection(n7 != 0, this.isNegative(), n8, mathContext.getRoundingMode().ordinal(), this) ? 1 : 0;
            if (n2 >= this.precision) {
                this.setBcdToZero();
                this.scale = n;
            } else {
                this.shiftRight(n2);
            }
            if (bl) {
                if (n4 < 5 && n6 != 0) {
                    this.setDigitPos(0, (byte)0);
                    this.compact();
                    return;
                }
                if (n4 >= 5 && n6 == 0) {
                    this.setDigitPos(0, (byte)9);
                    n4 = 9;
                } else {
                    this.setDigitPos(0, (byte)5);
                    return;
                }
            }
            if (n6 == 0) {
                if (n4 == 9) {
                    n5 = 0;
                    while (this.getDigitPos(n5) == 9) {
                        ++n5;
                    }
                    this.shiftRight(n5);
                }
                n5 = this.getDigitPos(0);
                if (!$assertionsDisabled && n5 == 9) {
                    throw new AssertionError();
                }
                this.setDigitPos(0, (byte)(n5 + 1));
                ++this.precision;
            }
            this.compact();
        }
    }

    @Override
    public void roundToInfinity() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
    }

    @Deprecated
    public void appendDigit(byte by, int n, boolean bl) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (by == 0) {
            if (bl && this.precision != 0) {
                this.scale += n + 1;
            }
            return;
        }
        if (this.scale > 0) {
            n += this.scale;
            if (bl) {
                this.scale = 0;
            }
        }
        this.shiftLeft(n + 1);
        this.setDigitPos(0, by);
        if (bl) {
            this.scale += n + 1;
        }
    }

    @Override
    public String toPlainString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.isNegative()) {
            stringBuilder.append('-');
        }
        if (this.precision == 0 || this.getMagnitude() < 0) {
            stringBuilder.append('0');
        }
        for (int i = this.getUpperDisplayMagnitude(); i >= this.getLowerDisplayMagnitude(); --i) {
            stringBuilder.append((char)(48 + this.getDigit(i)));
            if (i != 0) continue;
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }

    public String toScientificString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.toScientificString(stringBuilder);
        return stringBuilder.toString();
    }

    public void toScientificString(StringBuilder stringBuilder) {
        if (!$assertionsDisabled && this.isApproximate) {
            throw new AssertionError();
        }
        if (this.isNegative()) {
            stringBuilder.append('-');
        }
        if (this.precision == 0) {
            stringBuilder.append("0E+0");
            return;
        }
        int n = this.precision - 1;
        int n2 = 0;
        int n3 = n;
        stringBuilder.append((char)(48 + this.getDigitPos(n3)));
        if (--n3 >= n2) {
            stringBuilder.append('.');
            while (n3 >= n2) {
                stringBuilder.append((char)(48 + this.getDigitPos(n3)));
                --n3;
            }
        }
        stringBuilder.append('E');
        int n4 = n + this.scale;
        if (n4 == Integer.MIN_VALUE) {
            stringBuilder.append("-2147483648");
            return;
        }
        if (n4 < 0) {
            n4 *= -1;
            stringBuilder.append('-');
        } else {
            stringBuilder.append('+');
        }
        if (n4 == 0) {
            stringBuilder.append('0');
        }
        int n5 = stringBuilder.length();
        while (n4 > 0) {
            int n6 = n4 / 10;
            int n7 = n4 % 10;
            stringBuilder.insert(n5, (char)(48 + n7));
            n4 = n6;
        }
    }

    public boolean equals(Object object) {
        boolean bl;
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof DecimalQuantity_AbstractBCD)) {
            return true;
        }
        DecimalQuantity_AbstractBCD decimalQuantity_AbstractBCD = (DecimalQuantity_AbstractBCD)object;
        boolean bl2 = bl = this.scale == decimalQuantity_AbstractBCD.scale && this.precision == decimalQuantity_AbstractBCD.precision && this.flags == decimalQuantity_AbstractBCD.flags && this.lReqPos == decimalQuantity_AbstractBCD.lReqPos && this.rReqPos == decimalQuantity_AbstractBCD.rReqPos && this.isApproximate == decimalQuantity_AbstractBCD.isApproximate;
        if (!bl) {
            return true;
        }
        if (this.precision == 0) {
            return false;
        }
        if (this.isApproximate) {
            return this.origDouble == decimalQuantity_AbstractBCD.origDouble && this.origDelta == decimalQuantity_AbstractBCD.origDelta;
        }
        for (int i = this.getUpperDisplayMagnitude(); i >= this.getLowerDisplayMagnitude(); --i) {
            if (this.getDigit(i) == decimalQuantity_AbstractBCD.getDigit(i)) continue;
            return true;
        }
        return false;
    }

    protected abstract byte getDigitPos(int var1);

    protected abstract void setDigitPos(int var1, byte var2);

    protected abstract void shiftLeft(int var1);

    protected abstract void shiftRight(int var1);

    protected abstract void popFromLeft(int var1);

    protected abstract void setBcdToZero();

    protected abstract void readIntToBcd(int var1);

    protected abstract void readLongToBcd(long var1);

    protected abstract void readBigIntegerToBcd(BigInteger var1);

    protected abstract BigDecimal bcdToBigDecimal();

    protected abstract void copyBcdFrom(DecimalQuantity var1);

    protected abstract void compact();

    static {
        $assertionsDisabled = !DecimalQuantity_AbstractBCD.class.desiredAssertionStatus();
        DOUBLE_MULTIPLIERS = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21};
        INT64_BCD = new byte[]{9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8};
    }
}

