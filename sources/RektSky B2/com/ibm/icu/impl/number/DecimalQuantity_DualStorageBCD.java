package com.ibm.icu.impl.number;

import java.math.*;

public final class DecimalQuantity_DualStorageBCD extends DecimalQuantity_AbstractBCD
{
    private byte[] bcdBytes;
    private long bcdLong;
    private boolean usingBytes;
    
    @Override
    public int maxRepresentableDigits() {
        return Integer.MAX_VALUE;
    }
    
    public DecimalQuantity_DualStorageBCD() {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setBcdToZero();
        this.flags = 0;
    }
    
    public DecimalQuantity_DualStorageBCD(final long input) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToLong(input);
    }
    
    public DecimalQuantity_DualStorageBCD(final int input) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToInt(input);
    }
    
    public DecimalQuantity_DualStorageBCD(final double input) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToDouble(input);
    }
    
    public DecimalQuantity_DualStorageBCD(final BigInteger input) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToBigInteger(input);
    }
    
    public DecimalQuantity_DualStorageBCD(final BigDecimal input) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToBigDecimal(input);
    }
    
    public DecimalQuantity_DualStorageBCD(final DecimalQuantity_DualStorageBCD other) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.copyFrom(other);
    }
    
    public DecimalQuantity_DualStorageBCD(final Number number) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        if (number instanceof Long) {
            this.setToLong(number.longValue());
        }
        else if (number instanceof Integer) {
            this.setToInt(number.intValue());
        }
        else if (number instanceof Float) {
            this.setToDouble(number.doubleValue());
        }
        else if (number instanceof Double) {
            this.setToDouble(number.doubleValue());
        }
        else if (number instanceof BigInteger) {
            this.setToBigInteger((BigInteger)number);
        }
        else if (number instanceof BigDecimal) {
            this.setToBigDecimal((BigDecimal)number);
        }
        else {
            if (!(number instanceof com.ibm.icu.math.BigDecimal)) {
                throw new IllegalArgumentException("Number is of an unsupported type: " + number.getClass().getName());
            }
            this.setToBigDecimal(((com.ibm.icu.math.BigDecimal)number).toBigDecimal());
        }
    }
    
    @Override
    public DecimalQuantity createCopy() {
        return new DecimalQuantity_DualStorageBCD(this);
    }
    
    @Override
    protected byte getDigitPos(final int position) {
        if (this.usingBytes) {
            if (position < 0 || position >= this.precision) {
                return 0;
            }
            return this.bcdBytes[position];
        }
        else {
            if (position < 0 || position >= 16) {
                return 0;
            }
            return (byte)(this.bcdLong >>> position * 4 & 0xFL);
        }
    }
    
    @Override
    protected void setDigitPos(final int position, final byte value) {
        assert position >= 0;
        if (this.usingBytes) {
            this.ensureCapacity(position + 1);
            this.bcdBytes[position] = value;
        }
        else if (position >= 16) {
            this.switchStorage();
            this.ensureCapacity(position + 1);
            this.bcdBytes[position] = value;
        }
        else {
            final int shift = position * 4;
            this.bcdLong = ((this.bcdLong & ~(15L << shift)) | (long)value << shift);
        }
    }
    
    @Override
    protected void shiftLeft(final int numDigits) {
        if (!this.usingBytes && this.precision + numDigits > 16) {
            this.switchStorage();
        }
        if (this.usingBytes) {
            this.ensureCapacity(this.precision + numDigits);
            int i;
            for (i = this.precision + numDigits - 1; i >= numDigits; --i) {
                this.bcdBytes[i] = this.bcdBytes[i - numDigits];
            }
            while (i >= 0) {
                this.bcdBytes[i] = 0;
                --i;
            }
        }
        else {
            this.bcdLong <<= numDigits * 4;
        }
        this.scale -= numDigits;
        this.precision += numDigits;
    }
    
    @Override
    protected void shiftRight(final int numDigits) {
        if (this.usingBytes) {
            int i;
            for (i = 0; i < this.precision - numDigits; ++i) {
                this.bcdBytes[i] = this.bcdBytes[i + numDigits];
            }
            while (i < this.precision) {
                this.bcdBytes[i] = 0;
                ++i;
            }
        }
        else {
            this.bcdLong >>>= numDigits * 4;
        }
        this.scale += numDigits;
        this.precision -= numDigits;
    }
    
    @Override
    protected void setBcdToZero() {
        if (this.usingBytes) {
            this.bcdBytes = null;
            this.usingBytes = false;
        }
        this.bcdLong = 0L;
        this.scale = 0;
        this.precision = 0;
        this.isApproximate = false;
        this.origDouble = 0.0;
        this.origDelta = 0;
    }
    
    @Override
    protected void readIntToBcd(int n) {
        assert n != 0;
        long result = 0L;
        int i;
        for (i = 16; n != 0; n /= 10, --i) {
            result = (result >>> 4) + (n % 10L << 60);
        }
        assert !this.usingBytes;
        this.bcdLong = result >>> i * 4;
        this.scale = 0;
        this.precision = 16 - i;
    }
    
    @Override
    protected void readLongToBcd(long n) {
        assert n != 0L;
        if (n >= 10000000000000000L) {
            this.ensureCapacity();
            int i;
            for (i = 0; n != 0L; n /= 10L, ++i) {
                this.bcdBytes[i] = (byte)(n % 10L);
            }
            assert this.usingBytes;
            this.scale = 0;
            this.precision = i;
        }
        else {
            long result = 0L;
            int j;
            for (j = 16; n != 0L; n /= 10L, --j) {
                result = (result >>> 4) + (n % 10L << 60);
            }
            assert j >= 0;
            assert !this.usingBytes;
            this.bcdLong = result >>> j * 4;
            this.scale = 0;
            this.precision = 16 - j;
        }
    }
    
    @Override
    protected void readBigIntegerToBcd(BigInteger n) {
        assert n.signum() != 0;
        this.ensureCapacity();
        int i;
        BigInteger[] temp;
        for (i = 0; n.signum() != 0; n = temp[0], ++i) {
            temp = n.divideAndRemainder(BigInteger.TEN);
            this.ensureCapacity(i + 1);
            this.bcdBytes[i] = temp[1].byteValue();
        }
        this.scale = 0;
        this.precision = i;
    }
    
    @Override
    protected BigDecimal bcdToBigDecimal() {
        if (this.usingBytes) {
            BigDecimal result = new BigDecimal(this.toNumberString());
            if (this.isNegative()) {
                result = result.negate();
            }
            return result;
        }
        long tempLong = 0L;
        for (int shift = this.precision - 1; shift >= 0; --shift) {
            tempLong = tempLong * 10L + this.getDigitPos(shift);
        }
        BigDecimal result2 = BigDecimal.valueOf(tempLong);
        result2 = result2.scaleByPowerOfTen(this.scale);
        if (this.isNegative()) {
            result2 = result2.negate();
        }
        return result2;
    }
    
    @Override
    protected void compact() {
        if (this.usingBytes) {
            int delta;
            for (delta = 0; delta < this.precision && this.bcdBytes[delta] == 0; ++delta) {}
            if (delta == this.precision) {
                this.setBcdToZero();
                return;
            }
            this.shiftRight(delta);
            int leading;
            for (leading = this.precision - 1; leading >= 0 && this.bcdBytes[leading] == 0; --leading) {}
            this.precision = leading + 1;
            if (this.precision <= 16) {
                this.switchStorage();
            }
        }
        else {
            if (this.bcdLong == 0L) {
                this.setBcdToZero();
                return;
            }
            final int delta = Long.numberOfTrailingZeros(this.bcdLong) / 4;
            this.bcdLong >>>= delta * 4;
            this.scale += delta;
            this.precision = 16 - Long.numberOfLeadingZeros(this.bcdLong) / 4;
        }
    }
    
    private void ensureCapacity() {
        this.ensureCapacity(40);
    }
    
    private void ensureCapacity(final int capacity) {
        if (capacity == 0) {
            return;
        }
        final int oldCapacity = this.usingBytes ? this.bcdBytes.length : 0;
        if (!this.usingBytes) {
            this.bcdBytes = new byte[capacity];
        }
        else if (oldCapacity < capacity) {
            final byte[] bcd1 = new byte[capacity * 2];
            System.arraycopy(this.bcdBytes, 0, bcd1, 0, oldCapacity);
            this.bcdBytes = bcd1;
        }
        this.usingBytes = true;
    }
    
    private void switchStorage() {
        if (this.usingBytes) {
            this.bcdLong = 0L;
            for (int i = this.precision - 1; i >= 0; --i) {
                this.bcdLong <<= 4;
                this.bcdLong |= this.bcdBytes[i];
            }
            this.bcdBytes = null;
            this.usingBytes = false;
        }
        else {
            this.ensureCapacity();
            for (int i = 0; i < this.precision; ++i) {
                this.bcdBytes[i] = (byte)(this.bcdLong & 0xFL);
                this.bcdLong >>>= 4;
            }
            assert this.usingBytes;
        }
    }
    
    @Override
    protected void copyBcdFrom(final DecimalQuantity _other) {
        final DecimalQuantity_DualStorageBCD other = (DecimalQuantity_DualStorageBCD)_other;
        this.setBcdToZero();
        if (other.usingBytes) {
            this.ensureCapacity(other.precision);
            System.arraycopy(other.bcdBytes, 0, this.bcdBytes, 0, other.precision);
        }
        else {
            this.bcdLong = other.bcdLong;
        }
    }
    
    @Deprecated
    public String checkHealth() {
        if (this.usingBytes) {
            if (this.bcdLong != 0L) {
                return "Value in bcdLong but we are in byte mode";
            }
            if (this.precision == 0) {
                return "Zero precision but we are in byte mode";
            }
            if (this.precision > this.bcdBytes.length) {
                return "Precision exceeds length of byte array";
            }
            if (this.getDigitPos(this.precision - 1) == 0) {
                return "Most significant digit is zero in byte mode";
            }
            if (this.getDigitPos(0) == 0) {
                return "Least significant digit is zero in long mode";
            }
            for (int i = 0; i < this.precision; ++i) {
                if (this.getDigitPos(i) >= 10) {
                    return "Digit exceeding 10 in byte array";
                }
                if (this.getDigitPos(i) < 0) {
                    return "Digit below 0 in byte array";
                }
            }
            for (int i = this.precision; i < this.bcdBytes.length; ++i) {
                if (this.getDigitPos(i) != 0) {
                    return "Nonzero digits outside of range in byte array";
                }
            }
        }
        else {
            if (this.bcdBytes != null) {
                for (int i = 0; i < this.bcdBytes.length; ++i) {
                    if (this.bcdBytes[i] != 0) {
                        return "Nonzero digits in byte array but we are in long mode";
                    }
                }
            }
            if (this.precision == 0 && this.bcdLong != 0L) {
                return "Value in bcdLong even though precision is zero";
            }
            if (this.precision > 16) {
                return "Precision exceeds length of long";
            }
            if (this.precision != 0 && this.getDigitPos(this.precision - 1) == 0) {
                return "Most significant digit is zero in long mode";
            }
            if (this.precision != 0 && this.getDigitPos(0) == 0) {
                return "Least significant digit is zero in long mode";
            }
            for (int i = 0; i < this.precision; ++i) {
                if (this.getDigitPos(i) >= 10) {
                    return "Digit exceeding 10 in long";
                }
                if (this.getDigitPos(i) < 0) {
                    return "Digit below 0 in long (?!)";
                }
            }
            for (int i = this.precision; i < 16; ++i) {
                if (this.getDigitPos(i) != 0) {
                    return "Nonzero digits outside of range in long";
                }
            }
        }
        return null;
    }
    
    @Deprecated
    public boolean isUsingBytes() {
        return this.usingBytes;
    }
    
    @Override
    public String toString() {
        return String.format("<DecimalQuantity %s:%d:%d:%s %s %s%s>", (this.lOptPos > 1000) ? "999" : String.valueOf(this.lOptPos), this.lReqPos, this.rReqPos, (this.rOptPos < -1000) ? "-999" : String.valueOf(this.rOptPos), this.usingBytes ? "bytes" : "long", this.isNegative() ? "-" : "", this.toNumberString());
    }
    
    private String toNumberString() {
        final StringBuilder sb = new StringBuilder();
        if (this.usingBytes) {
            if (this.precision == 0) {
                sb.append('0');
            }
            for (int i = this.precision - 1; i >= 0; --i) {
                sb.append(this.bcdBytes[i]);
            }
        }
        else {
            sb.append(Long.toHexString(this.bcdLong));
        }
        sb.append("E");
        sb.append(this.scale);
        return sb.toString();
    }
}
