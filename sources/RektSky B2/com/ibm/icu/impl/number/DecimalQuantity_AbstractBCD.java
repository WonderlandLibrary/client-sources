package com.ibm.icu.impl.number;

import com.ibm.icu.impl.*;
import java.text.*;
import com.ibm.icu.text.*;
import java.math.*;

public abstract class DecimalQuantity_AbstractBCD implements DecimalQuantity
{
    protected int scale;
    protected int precision;
    protected byte flags;
    protected static final int NEGATIVE_FLAG = 1;
    protected static final int INFINITY_FLAG = 2;
    protected static final int NAN_FLAG = 4;
    protected double origDouble;
    protected int origDelta;
    protected boolean isApproximate;
    protected int lOptPos;
    protected int lReqPos;
    protected int rReqPos;
    protected int rOptPos;
    private static final double[] DOUBLE_MULTIPLIERS;
    @Deprecated
    public boolean explicitExactDouble;
    static final byte[] INT64_BCD;
    private static final int SECTION_LOWER_EDGE = -1;
    private static final int SECTION_UPPER_EDGE = -2;
    
    public DecimalQuantity_AbstractBCD() {
        this.lOptPos = Integer.MAX_VALUE;
        this.lReqPos = 0;
        this.rReqPos = 0;
        this.rOptPos = Integer.MIN_VALUE;
        this.explicitExactDouble = false;
    }
    
    @Override
    public void copyFrom(final DecimalQuantity _other) {
        this.copyBcdFrom(_other);
        final DecimalQuantity_AbstractBCD other = (DecimalQuantity_AbstractBCD)_other;
        this.lOptPos = other.lOptPos;
        this.lReqPos = other.lReqPos;
        this.rReqPos = other.rReqPos;
        this.rOptPos = other.rOptPos;
        this.scale = other.scale;
        this.precision = other.precision;
        this.flags = other.flags;
        this.origDouble = other.origDouble;
        this.origDelta = other.origDelta;
        this.isApproximate = other.isApproximate;
    }
    
    public DecimalQuantity_AbstractBCD clear() {
        this.lOptPos = Integer.MAX_VALUE;
        this.lReqPos = 0;
        this.rReqPos = 0;
        this.rOptPos = Integer.MIN_VALUE;
        this.flags = 0;
        this.setBcdToZero();
        return this;
    }
    
    @Override
    public void setIntegerLength(int minInt, final int maxInt) {
        assert minInt >= 0;
        assert maxInt >= minInt;
        if (minInt < this.lReqPos) {
            minInt = this.lReqPos;
        }
        this.lOptPos = maxInt;
        this.lReqPos = minInt;
    }
    
    @Override
    public void setFractionLength(final int minFrac, final int maxFrac) {
        assert minFrac >= 0;
        assert maxFrac >= minFrac;
        this.rReqPos = -minFrac;
        this.rOptPos = -maxFrac;
    }
    
    @Override
    public long getPositionFingerprint() {
        long fingerprint = 0L;
        fingerprint ^= this.lOptPos;
        fingerprint ^= this.lReqPos << 16;
        fingerprint ^= (long)this.rReqPos << 32;
        fingerprint ^= (long)this.rOptPos << 48;
        return fingerprint;
    }
    
    @Override
    public void roundToIncrement(final BigDecimal roundingIncrement, final MathContext mathContext) {
        BigDecimal temp = this.toBigDecimal();
        temp = temp.divide(roundingIncrement, 0, mathContext.getRoundingMode()).multiply(roundingIncrement).round(mathContext);
        if (temp.signum() == 0) {
            this.setBcdToZero();
        }
        else {
            this.setToBigDecimal(temp);
        }
    }
    
    @Override
    public void multiplyBy(final BigDecimal multiplicand) {
        if (this.isInfinite() || this.isZero() || this.isNaN()) {
            return;
        }
        BigDecimal temp = this.toBigDecimal();
        temp = temp.multiply(multiplicand);
        this.setToBigDecimal(temp);
    }
    
    @Override
    public void negate() {
        this.flags ^= 0x1;
    }
    
    @Override
    public int getMagnitude() throws ArithmeticException {
        if (this.precision == 0) {
            throw new ArithmeticException("Magnitude is not well-defined for zero");
        }
        return this.scale + this.precision - 1;
    }
    
    @Override
    public void adjustMagnitude(final int delta) {
        if (this.precision != 0) {
            this.scale = Utility.addExact(this.scale, delta);
            this.origDelta = Utility.addExact(this.origDelta, delta);
        }
    }
    
    @Override
    public StandardPlural getStandardPlural(final PluralRules rules) {
        if (rules == null) {
            return StandardPlural.OTHER;
        }
        final String ruleString = rules.select(this);
        return StandardPlural.orOtherFromString(ruleString);
    }
    
    @Override
    public double getPluralOperand(final PluralRules.Operand operand) {
        assert !this.isApproximate;
        switch (operand) {
            case i: {
                return this.isNegative() ? ((double)(-this.toLong(true))) : ((double)this.toLong(true));
            }
            case f: {
                return (double)this.toFractionLong(true);
            }
            case t: {
                return (double)this.toFractionLong(false);
            }
            case v: {
                return this.fractionCount();
            }
            case w: {
                return this.fractionCountWithoutTrailingZeros();
            }
            default: {
                return Math.abs(this.toDouble());
            }
        }
    }
    
    @Override
    public void populateUFieldPosition(final FieldPosition fp) {
        if (fp instanceof UFieldPosition) {
            ((UFieldPosition)fp).setFractionDigits((int)this.getPluralOperand(PluralRules.Operand.v), (long)this.getPluralOperand(PluralRules.Operand.f));
        }
    }
    
    @Override
    public int getUpperDisplayMagnitude() {
        assert !this.isApproximate;
        final int magnitude = this.scale + this.precision;
        final int result = (this.lReqPos > magnitude) ? this.lReqPos : ((this.lOptPos < magnitude) ? this.lOptPos : magnitude);
        return result - 1;
    }
    
    @Override
    public int getLowerDisplayMagnitude() {
        assert !this.isApproximate;
        final int magnitude = this.scale;
        final int result = (this.rReqPos < magnitude) ? this.rReqPos : ((this.rOptPos > magnitude) ? this.rOptPos : magnitude);
        return result;
    }
    
    @Override
    public byte getDigit(final int magnitude) {
        assert !this.isApproximate;
        return this.getDigitPos(magnitude - this.scale);
    }
    
    private int fractionCount() {
        return -this.getLowerDisplayMagnitude();
    }
    
    private int fractionCountWithoutTrailingZeros() {
        return Math.max(-this.scale, 0);
    }
    
    @Override
    public boolean isNegative() {
        return (this.flags & 0x1) != 0x0;
    }
    
    @Override
    public int signum() {
        return this.isNegative() ? -1 : (this.isZero() ? 0 : 1);
    }
    
    @Override
    public boolean isInfinite() {
        return (this.flags & 0x2) != 0x0;
    }
    
    @Override
    public boolean isNaN() {
        return (this.flags & 0x4) != 0x0;
    }
    
    @Override
    public boolean isZero() {
        return this.precision == 0;
    }
    
    public void setToInt(int n) {
        this.setBcdToZero();
        this.flags = 0;
        if (n < 0) {
            this.flags |= 0x1;
            n = -n;
        }
        if (n != 0) {
            this._setToInt(n);
            this.compact();
        }
    }
    
    private void _setToInt(final int n) {
        if (n == Integer.MIN_VALUE) {
            this.readLongToBcd(-n);
        }
        else {
            this.readIntToBcd(n);
        }
    }
    
    public void setToLong(long n) {
        this.setBcdToZero();
        this.flags = 0;
        if (n < 0L) {
            this.flags |= 0x1;
            n = -n;
        }
        if (n != 0L) {
            this._setToLong(n);
            this.compact();
        }
    }
    
    private void _setToLong(final long n) {
        if (n == Long.MIN_VALUE) {
            this.readBigIntegerToBcd(BigInteger.valueOf(n).negate());
        }
        else if (n <= 2147483647L) {
            this.readIntToBcd((int)n);
        }
        else {
            this.readLongToBcd(n);
        }
    }
    
    public void setToBigInteger(BigInteger n) {
        this.setBcdToZero();
        this.flags = 0;
        if (n.signum() == -1) {
            this.flags |= 0x1;
            n = n.negate();
        }
        if (n.signum() != 0) {
            this._setToBigInteger(n);
            this.compact();
        }
    }
    
    private void _setToBigInteger(final BigInteger n) {
        if (n.bitLength() < 32) {
            this.readIntToBcd(n.intValue());
        }
        else if (n.bitLength() < 64) {
            this.readLongToBcd(n.longValue());
        }
        else {
            this.readBigIntegerToBcd(n);
        }
    }
    
    public void setToDouble(double n) {
        this.setBcdToZero();
        this.flags = 0;
        if (Double.compare(n, 0.0) < 0) {
            this.flags |= 0x1;
            n = -n;
        }
        if (Double.isNaN(n)) {
            this.flags |= 0x4;
        }
        else if (Double.isInfinite(n)) {
            this.flags |= 0x2;
        }
        else if (n != 0.0) {
            this._setToDoubleFast(n);
            this.compact();
        }
    }
    
    private void _setToDoubleFast(double n) {
        this.isApproximate = true;
        this.origDouble = n;
        this.origDelta = 0;
        final long ieeeBits = Double.doubleToLongBits(n);
        final int exponent = (int)((ieeeBits & 0x7FF0000000000000L) >> 52) - 1023;
        if (exponent <= 52 && (long)n == n) {
            this._setToLong((long)n);
            return;
        }
        final int fracLength = (int)((52 - exponent) / 3.32192809489);
        if (fracLength >= 0) {
            int i;
            for (i = fracLength; i >= 22; i -= 22) {
                n *= 1.0E22;
            }
            n *= DecimalQuantity_AbstractBCD.DOUBLE_MULTIPLIERS[i];
        }
        else {
            int i;
            for (i = fracLength; i <= -22; i += 22) {
                n /= 1.0E22;
            }
            n /= DecimalQuantity_AbstractBCD.DOUBLE_MULTIPLIERS[-i];
        }
        final long result = Math.round(n);
        if (result != 0L) {
            this._setToLong(result);
            this.scale -= fracLength;
        }
    }
    
    private void convertToAccurateDouble() {
        final double n = this.origDouble;
        assert n != 0.0;
        final int delta = this.origDelta;
        this.setBcdToZero();
        final String dstr = Double.toString(n);
        if (dstr.indexOf(69) != -1) {
            assert dstr.indexOf(46) == 1;
            final int expPos = dstr.indexOf(69);
            this._setToLong(Long.parseLong(dstr.charAt(0) + dstr.substring(2, expPos)));
            this.scale += Integer.parseInt(dstr.substring(expPos + 1)) - (expPos - 1) + 1;
        }
        else if (dstr.charAt(0) == '0') {
            assert dstr.indexOf(46) == 1;
            this._setToLong(Long.parseLong(dstr.substring(2)));
            this.scale += 2 - dstr.length();
        }
        else if (dstr.charAt(dstr.length() - 1) == '0') {
            assert dstr.indexOf(46) == dstr.length() - 2;
            assert dstr.length() - 2 <= 18;
            this._setToLong(Long.parseLong(dstr.substring(0, dstr.length() - 2)));
        }
        else {
            final int decimalPos = dstr.indexOf(46);
            this._setToLong(Long.parseLong(dstr.substring(0, decimalPos) + dstr.substring(decimalPos + 1)));
            this.scale += decimalPos - dstr.length() + 1;
        }
        this.scale += delta;
        this.compact();
        this.explicitExactDouble = true;
    }
    
    @Override
    public void setToBigDecimal(BigDecimal n) {
        this.setBcdToZero();
        this.flags = 0;
        if (n.signum() == -1) {
            this.flags |= 0x1;
            n = n.negate();
        }
        if (n.signum() != 0) {
            this._setToBigDecimal(n);
            this.compact();
        }
    }
    
    private void _setToBigDecimal(BigDecimal n) {
        final int fracLength = n.scale();
        n = n.scaleByPowerOfTen(fracLength);
        final BigInteger bi = n.toBigInteger();
        this._setToBigInteger(bi);
        this.scale -= fracLength;
    }
    
    public long toLong(final boolean truncateIfOverflow) {
        assert truncateIfOverflow || this.fitsInLong();
        long result = 0L;
        int upperMagnitude = Math.min(this.scale + this.precision, this.lOptPos) - 1;
        if (truncateIfOverflow) {
            upperMagnitude = Math.min(upperMagnitude, 17);
        }
        for (int magnitude = upperMagnitude; magnitude >= 0; --magnitude) {
            result = result * 10L + this.getDigitPos(magnitude - this.scale);
        }
        if (this.isNegative()) {
            result = -result;
        }
        return result;
    }
    
    public long toFractionLong(final boolean includeTrailingZeros) {
        long result = 0L;
        int magnitude = -1;
        int lowerMagnitude = Math.max(this.scale, this.rOptPos);
        if (includeTrailingZeros) {
            lowerMagnitude = Math.min(lowerMagnitude, this.rReqPos);
        }
        while (magnitude >= lowerMagnitude && result <= 1.0E17) {
            result = result * 10L + this.getDigitPos(magnitude - this.scale);
            --magnitude;
        }
        if (!includeTrailingZeros) {
            while (result > 0L && result % 10L == 0L) {
                result /= 10L;
            }
        }
        return result;
    }
    
    public boolean fitsInLong() {
        if (this.isZero()) {
            return true;
        }
        if (this.scale < 0) {
            return false;
        }
        final int magnitude = this.getMagnitude();
        if (magnitude < 18) {
            return true;
        }
        if (magnitude > 18) {
            return false;
        }
        for (int p = 0; p < this.precision; ++p) {
            final byte digit = this.getDigit(18 - p);
            if (digit < DecimalQuantity_AbstractBCD.INT64_BCD[p]) {
                return true;
            }
            if (digit > DecimalQuantity_AbstractBCD.INT64_BCD[p]) {
                return false;
            }
        }
        return this.isNegative();
    }
    
    @Override
    public double toDouble() {
        assert !this.isApproximate;
        if (this.isNaN()) {
            return Double.NaN;
        }
        if (this.isInfinite()) {
            return this.isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        long tempLong = 0L;
        final int lostDigits = this.precision - Math.min(this.precision, 17);
        for (int shift = this.precision - 1; shift >= lostDigits; --shift) {
            tempLong = tempLong * 10L + this.getDigitPos(shift);
        }
        double result = (double)tempLong;
        final int _scale = this.scale + lostDigits;
        if (_scale >= 0) {
            int i;
            for (i = _scale; i >= 22; i -= 22) {
                result *= 1.0E22;
                if (Double.isInfinite(result)) {
                    i = 0;
                    break;
                }
            }
            result *= DecimalQuantity_AbstractBCD.DOUBLE_MULTIPLIERS[i];
        }
        else {
            int i;
            for (i = _scale; i <= -22; i += 22) {
                result /= 1.0E22;
                if (result == 0.0) {
                    i = 0;
                    break;
                }
            }
            result /= DecimalQuantity_AbstractBCD.DOUBLE_MULTIPLIERS[-i];
        }
        if (this.isNegative()) {
            result = -result;
        }
        return result;
    }
    
    @Override
    public BigDecimal toBigDecimal() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
        return this.bcdToBigDecimal();
    }
    
    private static int safeSubtract(final int a, final int b) {
        final int diff = a - b;
        if (b < 0 && diff < a) {
            return Integer.MAX_VALUE;
        }
        if (b > 0 && diff > a) {
            return Integer.MIN_VALUE;
        }
        return diff;
    }
    
    public void truncate() {
        if (this.scale < 0) {
            this.shiftRight(-this.scale);
            this.scale = 0;
            this.compact();
        }
    }
    
    @Override
    public void roundToMagnitude(final int magnitude, final MathContext mathContext) {
        int position = safeSubtract(magnitude, this.scale);
        final int _mcPrecision = mathContext.getPrecision();
        if (magnitude == Integer.MAX_VALUE || (_mcPrecision > 0 && this.precision - position > _mcPrecision)) {
            position = this.precision - _mcPrecision;
        }
        if (position > 0 || this.isApproximate) {
            if (this.precision != 0) {
                final byte leadingDigit = this.getDigitPos(safeSubtract(position, 1));
                final byte trailingDigit = this.getDigitPos(position);
                int section = 2;
                if (!this.isApproximate) {
                    if (leadingDigit < 5) {
                        section = 1;
                    }
                    else if (leadingDigit > 5) {
                        section = 3;
                    }
                    else {
                        for (int p = safeSubtract(position, 2); p >= 0; --p) {
                            if (this.getDigitPos(p) != 0) {
                                section = 3;
                                break;
                            }
                        }
                    }
                }
                else {
                    int p = safeSubtract(position, 2);
                    final int minP = Math.max(0, this.precision - 14);
                    if (leadingDigit == 0) {
                        section = -1;
                        while (p >= minP) {
                            if (this.getDigitPos(p) != 0) {
                                section = 1;
                                break;
                            }
                            --p;
                        }
                    }
                    else if (leadingDigit == 4) {
                        while (p >= minP) {
                            if (this.getDigitPos(p) != 9) {
                                section = 1;
                                break;
                            }
                            --p;
                        }
                    }
                    else if (leadingDigit == 5) {
                        while (p >= minP) {
                            if (this.getDigitPos(p) != 0) {
                                section = 3;
                                break;
                            }
                            --p;
                        }
                    }
                    else if (leadingDigit == 9) {
                        section = -2;
                        while (p >= minP) {
                            if (this.getDigitPos(p) != 9) {
                                section = 3;
                                break;
                            }
                            --p;
                        }
                    }
                    else if (leadingDigit < 5) {
                        section = 1;
                    }
                    else {
                        section = 3;
                    }
                    final boolean roundsAtMidpoint = RoundingUtils.roundsAtMidpoint(mathContext.getRoundingMode().ordinal());
                    if (safeSubtract(position, 1) < this.precision - 14 || (roundsAtMidpoint && section == 2) || (!roundsAtMidpoint && section < 0)) {
                        this.convertToAccurateDouble();
                        this.roundToMagnitude(magnitude, mathContext);
                        return;
                    }
                    this.isApproximate = false;
                    this.origDouble = 0.0;
                    this.origDelta = 0;
                    if (position <= 0) {
                        return;
                    }
                    if (section == -1) {
                        section = 1;
                    }
                    if (section == -2) {
                        section = 3;
                    }
                }
                final boolean roundDown = RoundingUtils.getRoundingDirection(trailingDigit % 2 == 0, this.isNegative(), section, mathContext.getRoundingMode().ordinal(), this);
                if (position >= this.precision) {
                    this.setBcdToZero();
                    this.scale = magnitude;
                }
                else {
                    this.shiftRight(position);
                }
                if (!roundDown) {
                    if (trailingDigit == 9) {
                        int bubblePos;
                        for (bubblePos = 0; this.getDigitPos(bubblePos) == 9; ++bubblePos) {}
                        this.shiftRight(bubblePos);
                    }
                    final byte digit0 = this.getDigitPos(0);
                    assert digit0 != 9;
                    this.setDigitPos(0, (byte)(digit0 + 1));
                    ++this.precision;
                }
                this.compact();
            }
        }
    }
    
    @Override
    public void roundToInfinity() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
    }
    
    @Deprecated
    public void appendDigit(final byte value, int leadingZeros, final boolean appendAsInteger) {
        assert leadingZeros >= 0;
        if (value == 0) {
            if (appendAsInteger && this.precision != 0) {
                this.scale += leadingZeros + 1;
            }
            return;
        }
        if (this.scale > 0) {
            leadingZeros += this.scale;
            if (appendAsInteger) {
                this.scale = 0;
            }
        }
        this.shiftLeft(leadingZeros + 1);
        this.setDigitPos(0, value);
        if (appendAsInteger) {
            this.scale += leadingZeros + 1;
        }
    }
    
    @Override
    public String toPlainString() {
        final StringBuilder sb = new StringBuilder();
        if (this.isNegative()) {
            sb.append('-');
        }
        if (this.precision == 0 || this.getMagnitude() < 0) {
            sb.append('0');
        }
        for (int m = this.getUpperDisplayMagnitude(); m >= this.getLowerDisplayMagnitude(); --m) {
            sb.append((char)(48 + this.getDigit(m)));
            if (m == 0) {
                sb.append('.');
            }
        }
        return sb.toString();
    }
    
    public String toScientificString() {
        final StringBuilder sb = new StringBuilder();
        this.toScientificString(sb);
        return sb.toString();
    }
    
    public void toScientificString(final StringBuilder result) {
        assert !this.isApproximate;
        if (this.isNegative()) {
            result.append('-');
        }
        if (this.precision == 0) {
            result.append("0E+0");
            return;
        }
        final int upperPos = Math.min(this.precision + this.scale, this.lOptPos) - this.scale - 1;
        final int lowerPos = Math.max(this.scale, this.rOptPos) - this.scale;
        int p = upperPos;
        result.append((char)(48 + this.getDigitPos(p)));
        if (--p >= lowerPos) {
            result.append('.');
            while (p >= lowerPos) {
                result.append((char)(48 + this.getDigitPos(p)));
                --p;
            }
        }
        result.append('E');
        int _scale = upperPos + this.scale;
        if (_scale < 0) {
            _scale *= -1;
            result.append('-');
        }
        else {
            result.append('+');
        }
        if (_scale == 0) {
            result.append('0');
        }
        final int insertIndex = result.length();
        while (_scale > 0) {
            final int quot = _scale / 10;
            final int rem = _scale % 10;
            result.insert(insertIndex, (char)(48 + rem));
            _scale = quot;
        }
    }
    
    protected abstract byte getDigitPos(final int p0);
    
    protected abstract void setDigitPos(final int p0, final byte p1);
    
    protected abstract void shiftLeft(final int p0);
    
    protected abstract void shiftRight(final int p0);
    
    protected abstract void setBcdToZero();
    
    protected abstract void readIntToBcd(final int p0);
    
    protected abstract void readLongToBcd(final long p0);
    
    protected abstract void readBigIntegerToBcd(final BigInteger p0);
    
    protected abstract BigDecimal bcdToBigDecimal();
    
    protected abstract void copyBcdFrom(final DecimalQuantity p0);
    
    protected abstract void compact();
    
    static {
        DOUBLE_MULTIPLIERS = new double[] { 1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21 };
        INT64_BCD = new byte[] { 9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8 };
    }
}
