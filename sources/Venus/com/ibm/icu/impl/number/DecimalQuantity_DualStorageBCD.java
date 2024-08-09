/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class DecimalQuantity_DualStorageBCD
extends DecimalQuantity_AbstractBCD {
    private byte[] bcdBytes;
    private long bcdLong = 0L;
    private boolean usingBytes = false;
    static final boolean $assertionsDisabled = !DecimalQuantity_DualStorageBCD.class.desiredAssertionStatus();

    @Override
    public int maxRepresentableDigits() {
        return 0;
    }

    public DecimalQuantity_DualStorageBCD() {
        this.setBcdToZero();
        this.flags = 0;
    }

    public DecimalQuantity_DualStorageBCD(long l) {
        this.setToLong(l);
    }

    public DecimalQuantity_DualStorageBCD(int n) {
        this.setToInt(n);
    }

    public DecimalQuantity_DualStorageBCD(double d) {
        this.setToDouble(d);
    }

    public DecimalQuantity_DualStorageBCD(BigInteger bigInteger) {
        this.setToBigInteger(bigInteger);
    }

    public DecimalQuantity_DualStorageBCD(BigDecimal bigDecimal) {
        this.setToBigDecimal(bigDecimal);
    }

    public DecimalQuantity_DualStorageBCD(DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD) {
        this.copyFrom(decimalQuantity_DualStorageBCD);
    }

    public DecimalQuantity_DualStorageBCD(Number number) {
        if (number instanceof Long) {
            this.setToLong(number.longValue());
        } else if (number instanceof Integer) {
            this.setToInt(number.intValue());
        } else if (number instanceof Float) {
            this.setToDouble(number.doubleValue());
        } else if (number instanceof Double) {
            this.setToDouble(number.doubleValue());
        } else if (number instanceof BigInteger) {
            this.setToBigInteger((BigInteger)number);
        } else if (number instanceof BigDecimal) {
            this.setToBigDecimal((BigDecimal)number);
        } else if (number instanceof com.ibm.icu.math.BigDecimal) {
            this.setToBigDecimal(((com.ibm.icu.math.BigDecimal)number).toBigDecimal());
        } else {
            throw new IllegalArgumentException("Number is of an unsupported type: " + number.getClass().getName());
        }
    }

    @Override
    public DecimalQuantity createCopy() {
        return new DecimalQuantity_DualStorageBCD(this);
    }

    @Override
    protected byte getDigitPos(int n) {
        if (this.usingBytes) {
            if (n < 0 || n >= this.precision) {
                return 1;
            }
            return this.bcdBytes[n];
        }
        if (n < 0 || n >= 16) {
            return 1;
        }
        return (byte)(this.bcdLong >>> n * 4 & 0xFL);
    }

    @Override
    protected void setDigitPos(int n, byte by) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (this.usingBytes) {
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = by;
        } else if (n >= 16) {
            this.switchStorage();
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = by;
        } else {
            int n2 = n * 4;
            this.bcdLong = this.bcdLong & (15L << n2 ^ 0xFFFFFFFFFFFFFFFFL) | (long)by << n2;
        }
    }

    @Override
    protected void shiftLeft(int n) {
        if (!this.usingBytes && this.precision + n > 16) {
            this.switchStorage();
        }
        if (this.usingBytes) {
            int n2;
            this.ensureCapacity(this.precision + n);
            for (n2 = this.precision + n - 1; n2 >= n; --n2) {
                this.bcdBytes[n2] = this.bcdBytes[n2 - n];
            }
            while (n2 >= 0) {
                this.bcdBytes[n2] = 0;
                --n2;
            }
        } else {
            this.bcdLong <<= n * 4;
        }
        this.scale -= n;
        this.precision += n;
    }

    @Override
    protected void shiftRight(int n) {
        if (this.usingBytes) {
            int n2;
            for (n2 = 0; n2 < this.precision - n; ++n2) {
                this.bcdBytes[n2] = this.bcdBytes[n2 + n];
            }
            while (n2 < this.precision) {
                this.bcdBytes[n2] = 0;
                ++n2;
            }
        } else {
            this.bcdLong >>>= n * 4;
        }
        this.scale += n;
        this.precision -= n;
    }

    @Override
    protected void popFromLeft(int n) {
        if (!$assertionsDisabled && n > this.precision) {
            throw new AssertionError();
        }
        if (this.usingBytes) {
            for (int i = this.precision - 1; i >= this.precision - n; --i) {
                this.bcdBytes[i] = 0;
            }
        } else {
            this.bcdLong &= (1L << (this.precision - n) * 4) - 1L;
        }
        this.precision -= n;
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
        if (!$assertionsDisabled && n == 0) {
            throw new AssertionError();
        }
        long l = 0L;
        int n2 = 16;
        while (n != 0) {
            l = (l >>> 4) + ((long)n % 10L << 60);
            n /= 10;
            --n2;
        }
        if (!$assertionsDisabled && this.usingBytes) {
            throw new AssertionError();
        }
        this.bcdLong = l >>> n2 * 4;
        this.scale = 0;
        this.precision = 16 - n2;
    }

    @Override
    protected void readLongToBcd(long l) {
        if (!$assertionsDisabled && l == 0L) {
            throw new AssertionError();
        }
        if (l >= 10000000000000000L) {
            this.ensureCapacity();
            int n = 0;
            while (l != 0L) {
                this.bcdBytes[n] = (byte)(l % 10L);
                l /= 10L;
                ++n;
            }
            if (!$assertionsDisabled && !this.usingBytes) {
                throw new AssertionError();
            }
            this.scale = 0;
            this.precision = n;
        } else {
            long l2 = 0L;
            int n = 16;
            while (l != 0L) {
                l2 = (l2 >>> 4) + (l % 10L << 60);
                l /= 10L;
                --n;
            }
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.usingBytes) {
                throw new AssertionError();
            }
            this.bcdLong = l2 >>> n * 4;
            this.scale = 0;
            this.precision = 16 - n;
        }
    }

    @Override
    protected void readBigIntegerToBcd(BigInteger bigInteger) {
        if (!$assertionsDisabled && bigInteger.signum() == 0) {
            throw new AssertionError();
        }
        this.ensureCapacity();
        int n = 0;
        while (bigInteger.signum() != 0) {
            BigInteger[] bigIntegerArray = bigInteger.divideAndRemainder(BigInteger.TEN);
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = bigIntegerArray[0].byteValue();
            bigInteger = bigIntegerArray[5];
            ++n;
        }
        this.scale = 0;
        this.precision = n;
    }

    @Override
    protected BigDecimal bcdToBigDecimal() {
        if (this.usingBytes) {
            BigDecimal bigDecimal = new BigDecimal(this.toNumberString());
            if (this.isNegative()) {
                bigDecimal = bigDecimal.negate();
            }
            return bigDecimal;
        }
        long l = 0L;
        for (int i = this.precision - 1; i >= 0; --i) {
            l = l * 10L + (long)this.getDigitPos(i);
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(l);
        long l2 = bigDecimal.scale() + this.scale;
        bigDecimal = l2 <= Integer.MIN_VALUE ? BigDecimal.ZERO : bigDecimal.scaleByPowerOfTen(this.scale);
        if (this.isNegative()) {
            bigDecimal = bigDecimal.negate();
        }
        return bigDecimal;
    }

    @Override
    protected void compact() {
        if (this.usingBytes) {
            int n;
            int n2;
            for (n2 = 0; n2 < this.precision && this.bcdBytes[n2] == 0; ++n2) {
            }
            if (n2 == this.precision) {
                this.setBcdToZero();
                return;
            }
            this.shiftRight(n2);
            for (n = this.precision - 1; n >= 0 && this.bcdBytes[n] == 0; --n) {
            }
            this.precision = n + 1;
            if (this.precision <= 16) {
                this.switchStorage();
            }
        } else {
            if (this.bcdLong == 0L) {
                this.setBcdToZero();
                return;
            }
            int n = Long.numberOfTrailingZeros(this.bcdLong) / 4;
            this.bcdLong >>>= n * 4;
            this.scale += n;
            this.precision = 16 - Long.numberOfLeadingZeros(this.bcdLong) / 4;
        }
    }

    private void ensureCapacity() {
        this.ensureCapacity(40);
    }

    private void ensureCapacity(int n) {
        int n2;
        if (n == 0) {
            return;
        }
        int n3 = n2 = this.usingBytes ? this.bcdBytes.length : 0;
        if (!this.usingBytes) {
            this.bcdBytes = new byte[n];
        } else if (n2 < n) {
            byte[] byArray = new byte[n * 2];
            System.arraycopy(this.bcdBytes, 0, byArray, 0, n2);
            this.bcdBytes = byArray;
        }
        this.usingBytes = true;
    }

    private void switchStorage() {
        if (this.usingBytes) {
            this.bcdLong = 0L;
            for (int i = this.precision - 1; i >= 0; --i) {
                this.bcdLong <<= 4;
                this.bcdLong |= (long)this.bcdBytes[i];
            }
            this.bcdBytes = null;
            this.usingBytes = false;
        } else {
            this.ensureCapacity();
            for (int i = 0; i < this.precision; ++i) {
                this.bcdBytes[i] = (byte)(this.bcdLong & 0xFL);
                this.bcdLong >>>= 4;
            }
            if (!$assertionsDisabled && !this.usingBytes) {
                throw new AssertionError();
            }
        }
    }

    @Override
    protected void copyBcdFrom(DecimalQuantity decimalQuantity) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = (DecimalQuantity_DualStorageBCD)decimalQuantity;
        this.setBcdToZero();
        if (decimalQuantity_DualStorageBCD.usingBytes) {
            this.ensureCapacity(decimalQuantity_DualStorageBCD.precision);
            System.arraycopy(decimalQuantity_DualStorageBCD.bcdBytes, 0, this.bcdBytes, 0, decimalQuantity_DualStorageBCD.precision);
        } else {
            this.bcdLong = decimalQuantity_DualStorageBCD.bcdLong;
        }
    }

    @Deprecated
    public String checkHealth() {
        if (this.usingBytes) {
            int n;
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
            for (n = 0; n < this.precision; ++n) {
                if (this.getDigitPos(n) >= 10) {
                    return "Digit exceeding 10 in byte array";
                }
                if (this.getDigitPos(n) >= 0) continue;
                return "Digit below 0 in byte array";
            }
            for (n = this.precision; n < this.bcdBytes.length; ++n) {
                if (this.getDigitPos(n) == 0) continue;
                return "Nonzero digits outside of range in byte array";
            }
        } else {
            int n;
            if (this.bcdBytes != null) {
                for (n = 0; n < this.bcdBytes.length; ++n) {
                    if (this.bcdBytes[n] == 0) continue;
                    return "Nonzero digits in byte array but we are in long mode";
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
            for (n = 0; n < this.precision; ++n) {
                if (this.getDigitPos(n) >= 10) {
                    return "Digit exceeding 10 in long";
                }
                if (this.getDigitPos(n) >= 0) continue;
                return "Digit below 0 in long (?!)";
            }
            for (n = this.precision; n < 16; ++n) {
                if (this.getDigitPos(n) == 0) continue;
                return "Nonzero digits outside of range in long";
            }
        }
        return null;
    }

    @Deprecated
    public boolean isUsingBytes() {
        return this.usingBytes;
    }

    public String toString() {
        return String.format("<DecimalQuantity %d:%d %s %s%s>", this.lReqPos, this.rReqPos, this.usingBytes ? "bytes" : "long", this.isNegative() ? "-" : "", this.toNumberString());
    }

    private String toNumberString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.usingBytes) {
            if (this.precision == 0) {
                stringBuilder.append('0');
            }
            for (int i = this.precision - 1; i >= 0; --i) {
                stringBuilder.append(this.bcdBytes[i]);
            }
        } else {
            stringBuilder.append(Long.toHexString(this.bcdLong));
        }
        stringBuilder.append("E");
        stringBuilder.append(this.scale);
        return stringBuilder.toString();
    }
}

