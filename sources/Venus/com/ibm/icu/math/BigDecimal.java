/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.math;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.math.MathContext;
import java.io.Serializable;
import java.math.BigInteger;

public class BigDecimal
extends Number
implements Serializable,
Comparable<BigDecimal> {
    public static final BigDecimal ZERO = new BigDecimal(0L);
    public static final BigDecimal ONE = new BigDecimal(1L);
    public static final BigDecimal TEN = new BigDecimal(10);
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    private static final byte ispos = 1;
    private static final byte iszero = 0;
    private static final byte isneg = -1;
    private static final int MinExp = -999999999;
    private static final int MaxExp = 999999999;
    private static final int MinArg = -999999999;
    private static final int MaxArg = 999999999;
    private static final MathContext plainMC = new MathContext(0, 0);
    private static final long serialVersionUID = 8245355804974198832L;
    private static byte[] bytecar = new byte[190];
    private static byte[] bytedig = BigDecimal.diginit();
    private byte ind;
    private byte form = 0;
    private byte[] mant;
    private int exp;

    public BigDecimal(java.math.BigDecimal bigDecimal) {
        this(bigDecimal.toString());
    }

    public BigDecimal(BigInteger bigInteger) {
        this(bigInteger.toString(10));
    }

    public BigDecimal(BigInteger bigInteger, int n) {
        this(bigInteger.toString(10));
        if (n < 0) {
            throw new NumberFormatException("Negative scale: " + n);
        }
        this.exp = -n;
    }

    public BigDecimal(char[] cArray) {
        this(cArray, 0, cArray.length);
    }

    public BigDecimal(char[] cArray, int n, int n2) {
        int n3 = 0;
        char c = '\u0000';
        boolean bl = false;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        char c2 = '\u0000';
        int n7 = 0;
        int n8 = 0;
        if (n2 <= 0) {
            this.bad(cArray);
        }
        this.ind = 1;
        if (cArray[n] == '-') {
            if (--n2 == 0) {
                this.bad(cArray);
            }
            this.ind = (byte)-1;
            ++n;
        } else if (cArray[n] == '+') {
            if (--n2 == 0) {
                this.bad(cArray);
            }
            ++n;
        }
        boolean bl2 = false;
        boolean bl3 = false;
        int n9 = 0;
        int n10 = -1;
        int n11 = -1;
        int n12 = n2;
        n3 = n;
        while (n12 > 0) {
            c = cArray[n3];
            if (c >= '0' && c <= '9') {
                n11 = n3;
                ++n9;
            } else if (c == '.') {
                if (n10 >= 0) {
                    this.bad(cArray);
                }
                n10 = n3 - n;
            } else {
                if (c == 'e' || c == 'E') {
                    if (n3 - n > n2 - 2) {
                        this.bad(cArray);
                    }
                    bl = false;
                    if (cArray[n3 + 1] == '-') {
                        bl = true;
                        n4 = n3 + 2;
                    } else {
                        n4 = cArray[n3 + 1] == '+' ? n3 + 2 : n3 + 1;
                    }
                    n5 = n2 - (n4 - n);
                    if (n5 == 0 | n5 > 9) {
                        this.bad(cArray);
                    }
                    int n13 = n5;
                    n6 = n4;
                    while (n13 > 0) {
                        c2 = cArray[n6];
                        if (c2 < '0') {
                            this.bad(cArray);
                        }
                        if (c2 > '9') {
                            if (!UCharacter.isDigit(c2)) {
                                this.bad(cArray);
                            }
                            if ((n7 = UCharacter.digit(c2, 10)) < 0) {
                                this.bad(cArray);
                            }
                        } else {
                            n7 = c2 - 48;
                        }
                        this.exp = this.exp * 10 + n7;
                        --n13;
                        ++n6;
                    }
                    if (bl) {
                        this.exp = -this.exp;
                    }
                    bl3 = true;
                    break;
                }
                if (!UCharacter.isDigit(c)) {
                    this.bad(cArray);
                }
                bl2 = true;
                n11 = n3;
                ++n9;
            }
            --n12;
            ++n3;
        }
        if (n9 == 0) {
            this.bad(cArray);
        }
        if (n10 >= 0) {
            this.exp = this.exp + n10 - n9;
        }
        n12 = n11 - 1;
        for (n3 = n; n3 <= n12; ++n3) {
            c = cArray[n3];
            if (c == '0') {
                ++n;
                --n10;
                --n9;
                continue;
            }
            if (c == '.') {
                ++n;
                --n10;
                continue;
            }
            if (c <= '9' || UCharacter.digit(c, 10) != 0) break;
            ++n;
            --n10;
            --n9;
        }
        this.mant = new byte[n9];
        n6 = n;
        if (bl2) {
            n12 = n9;
            n3 = 0;
            while (n12 > 0) {
                if (n3 == n10) {
                    ++n6;
                }
                if ((c2 = cArray[n6]) <= '9') {
                    this.mant[n3] = (byte)(c2 - 48);
                } else {
                    n7 = UCharacter.digit(c2, 10);
                    if (n7 < 0) {
                        this.bad(cArray);
                    }
                    this.mant[n3] = (byte)n7;
                }
                ++n6;
                --n12;
                ++n3;
            }
        } else {
            n12 = n9;
            n3 = 0;
            while (n12 > 0) {
                if (n3 == n10) {
                    ++n6;
                }
                this.mant[n3] = (byte)(cArray[n6] - 48);
                ++n6;
                --n12;
                ++n3;
            }
        }
        if (this.mant[0] == 0) {
            this.ind = 0;
            if (this.exp > 0) {
                this.exp = 0;
            }
            if (bl3) {
                this.mant = BigDecimal.ZERO.mant;
                this.exp = 0;
            }
        } else if (bl3) {
            this.form = 1;
            n8 = this.exp + this.mant.length - 1;
            if (n8 < -999999999 | n8 > 999999999) {
                this.bad(cArray);
            }
        }
    }

    public BigDecimal(double d) {
        this(new java.math.BigDecimal(d).toString());
    }

    public BigDecimal(int n) {
        int n2 = 0;
        if (n <= 9 && n >= -9) {
            if (n == 0) {
                this.mant = BigDecimal.ZERO.mant;
                this.ind = 0;
            } else if (n == 1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = 1;
            } else if (n == -1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = (byte)-1;
            } else {
                this.mant = new byte[1];
                if (n > 0) {
                    this.mant[0] = (byte)n;
                    this.ind = 1;
                } else {
                    this.mant[0] = (byte)(-n);
                    this.ind = (byte)-1;
                }
            }
            return;
        }
        if (n > 0) {
            this.ind = 1;
            n = -n;
        } else {
            this.ind = (byte)-1;
        }
        int n3 = n;
        n2 = 9;
        while ((n3 /= 10) != 0) {
            --n2;
        }
        this.mant = new byte[10 - n2];
        n2 = 10 - n2 - 1;
        while (true) {
            this.mant[n2] = -((byte)(n % 10));
            if ((n /= 10) == 0) break;
            --n2;
        }
    }

    public BigDecimal(long l) {
        int n = 0;
        if (l > 0L) {
            this.ind = 1;
            l = -l;
        } else {
            this.ind = l == 0L ? (byte)0 : (byte)-1;
        }
        long l2 = l;
        n = 18;
        while ((l2 /= 10L) != 0L) {
            --n;
        }
        this.mant = new byte[19 - n];
        n = 19 - n - 1;
        while (true) {
            this.mant[n] = -((byte)(l % 10L));
            if ((l /= 10L) == 0L) break;
            --n;
        }
    }

    public BigDecimal(String string) {
        this(string.toCharArray(), 0, string.length());
    }

    private BigDecimal() {
    }

    public BigDecimal abs() {
        return this.abs(plainMC);
    }

    public BigDecimal abs(MathContext mathContext) {
        if (this.ind == -1) {
            return this.negate(mathContext);
        }
        return this.plus(mathContext);
    }

    public BigDecimal add(BigDecimal bigDecimal) {
        return this.add(bigDecimal, plainMC);
    }

    public BigDecimal add(BigDecimal bigDecimal, MathContext mathContext) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        byte[] byArray = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        byte by = 0;
        byte by2 = 0;
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        BigDecimal bigDecimal2 = this;
        if (bigDecimal2.ind == 0 && mathContext.form != 0) {
            return bigDecimal.plus(mathContext);
        }
        if (bigDecimal.ind == 0 && mathContext.form != 0) {
            return bigDecimal2.plus(mathContext);
        }
        int n8 = mathContext.digits;
        if (n8 > 0) {
            if (bigDecimal2.mant.length > n8) {
                bigDecimal2 = BigDecimal.clone(bigDecimal2).round(mathContext);
            }
            if (bigDecimal.mant.length > n8) {
                bigDecimal = BigDecimal.clone(bigDecimal).round(mathContext);
            }
        }
        BigDecimal bigDecimal3 = new BigDecimal();
        byte[] byArray2 = bigDecimal2.mant;
        int n9 = bigDecimal2.mant.length;
        byte[] byArray3 = bigDecimal.mant;
        int n10 = bigDecimal.mant.length;
        if (bigDecimal2.exp == bigDecimal.exp) {
            bigDecimal3.exp = bigDecimal2.exp;
        } else if (bigDecimal2.exp > bigDecimal.exp) {
            n = n9 + bigDecimal2.exp - bigDecimal.exp;
            if (n >= n10 + n8 + 1 && n8 > 0) {
                bigDecimal3.mant = byArray2;
                bigDecimal3.exp = bigDecimal2.exp;
                bigDecimal3.ind = bigDecimal2.ind;
                if (n9 < n8) {
                    bigDecimal3.mant = BigDecimal.extend(bigDecimal2.mant, n8);
                    bigDecimal3.exp -= n8 - n9;
                }
                return bigDecimal3.finish(mathContext, false);
            }
            bigDecimal3.exp = bigDecimal.exp;
            if (n > n8 + 1 && n8 > 0) {
                n2 = n - n8 - 1;
                n10 -= n2;
                bigDecimal3.exp += n2;
                n = n8 + 1;
            }
            if (n > n9) {
                n9 = n;
            }
        } else {
            n = n10 + bigDecimal.exp - bigDecimal2.exp;
            if (n >= n9 + n8 + 1 && n8 > 0) {
                bigDecimal3.mant = byArray3;
                bigDecimal3.exp = bigDecimal.exp;
                bigDecimal3.ind = bigDecimal.ind;
                if (n10 < n8) {
                    bigDecimal3.mant = BigDecimal.extend(bigDecimal.mant, n8);
                    bigDecimal3.exp -= n8 - n10;
                }
                return bigDecimal3.finish(mathContext, false);
            }
            bigDecimal3.exp = bigDecimal2.exp;
            if (n > n8 + 1 && n8 > 0) {
                n2 = n - n8 - 1;
                n9 -= n2;
                bigDecimal3.exp += n2;
                n = n8 + 1;
            }
            if (n > n10) {
                n10 = n;
            }
        }
        bigDecimal3.ind = bigDecimal2.ind == 0 ? (byte)1 : bigDecimal2.ind;
        if (bigDecimal2.ind == -1 == (bigDecimal.ind == -1)) {
            n3 = 1;
        } else {
            n3 = -1;
            if (bigDecimal.ind != 0) {
                if (n9 < n10 | bigDecimal2.ind == 0) {
                    byArray = byArray2;
                    byArray2 = byArray3;
                    byArray3 = byArray;
                    n2 = n9;
                    n9 = n10;
                    n10 = n2;
                    bigDecimal3.ind = -bigDecimal3.ind;
                } else if (n9 <= n10) {
                    n4 = 0;
                    n5 = 0;
                    n6 = byArray2.length - 1;
                    n7 = byArray3.length - 1;
                    while (true) {
                        if (n4 <= n6) {
                            by = byArray2[n4];
                        } else {
                            if (n5 > n7) {
                                if (mathContext.form == 0) break;
                                return ZERO;
                            }
                            by = 0;
                        }
                        by2 = n5 <= n7 ? byArray3[n5] : (byte)0;
                        if (by != by2) {
                            if (by >= by2) break;
                            byArray = byArray2;
                            byArray2 = byArray3;
                            byArray3 = byArray;
                            n2 = n9;
                            n9 = n10;
                            n10 = n2;
                            bigDecimal3.ind = -bigDecimal3.ind;
                            break;
                        }
                        ++n4;
                        ++n5;
                    }
                }
            }
        }
        bigDecimal3.mant = BigDecimal.byteaddsub(byArray2, n9, byArray3, n10, n3, false);
        return bigDecimal3.finish(mathContext, false);
    }

    @Override
    public int compareTo(BigDecimal bigDecimal) {
        return this.compareTo(bigDecimal, plainMC);
    }

    public int compareTo(BigDecimal bigDecimal, MathContext mathContext) {
        int n = 0;
        int n2 = 0;
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        if (this.ind == bigDecimal.ind & this.exp == bigDecimal.exp) {
            n = this.mant.length;
            if (n < bigDecimal.mant.length) {
                return -this.ind;
            }
            if (n > bigDecimal.mant.length) {
                return this.ind;
            }
            if (n <= mathContext.digits | mathContext.digits == 0) {
                int n3 = n;
                n2 = 0;
                while (n3 > 0) {
                    if (this.mant[n2] < bigDecimal.mant[n2]) {
                        return -this.ind;
                    }
                    if (this.mant[n2] > bigDecimal.mant[n2]) {
                        return this.ind;
                    }
                    --n3;
                    ++n2;
                }
                return 1;
            }
        } else {
            if (this.ind < bigDecimal.ind) {
                return 1;
            }
            if (this.ind > bigDecimal.ind) {
                return 0;
            }
        }
        BigDecimal bigDecimal2 = BigDecimal.clone(bigDecimal);
        bigDecimal2.ind = -bigDecimal2.ind;
        return this.add((BigDecimal)bigDecimal2, (MathContext)mathContext).ind;
    }

    public BigDecimal divide(BigDecimal bigDecimal) {
        return this.dodivide('D', bigDecimal, plainMC, -1);
    }

    public BigDecimal divide(BigDecimal bigDecimal, int n) {
        MathContext mathContext = new MathContext(0, 0, false, n);
        return this.dodivide('D', bigDecimal, mathContext, -1);
    }

    public BigDecimal divide(BigDecimal bigDecimal, int n, int n2) {
        if (n < 0) {
            throw new ArithmeticException("Negative scale: " + n);
        }
        MathContext mathContext = new MathContext(0, 0, false, n2);
        return this.dodivide('D', bigDecimal, mathContext, n);
    }

    public BigDecimal divide(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('D', bigDecimal, mathContext, -1);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal) {
        return this.dodivide('I', bigDecimal, plainMC, 0);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('I', bigDecimal, mathContext, 0);
    }

    public BigDecimal max(BigDecimal bigDecimal) {
        return this.max(bigDecimal, plainMC);
    }

    public BigDecimal max(BigDecimal bigDecimal, MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) >= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal min(BigDecimal bigDecimal) {
        return this.min(bigDecimal, plainMC);
    }

    public BigDecimal min(BigDecimal bigDecimal, MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) <= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return this.multiply(bigDecimal, plainMC);
    }

    public BigDecimal multiply(BigDecimal bigDecimal, MathContext mathContext) {
        byte[] byArray = null;
        byte[] byArray2 = null;
        int n = 0;
        int n2 = 0;
        byte by = 0;
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        BigDecimal bigDecimal2 = this;
        int n3 = 0;
        int n4 = mathContext.digits;
        if (n4 > 0) {
            if (bigDecimal2.mant.length > n4) {
                bigDecimal2 = BigDecimal.clone(bigDecimal2).round(mathContext);
            }
            if (bigDecimal.mant.length > n4) {
                bigDecimal = BigDecimal.clone(bigDecimal).round(mathContext);
            }
        } else {
            if (bigDecimal2.exp > 0) {
                n3 += bigDecimal2.exp;
            }
            if (bigDecimal.exp > 0) {
                n3 += bigDecimal.exp;
            }
        }
        if (bigDecimal2.mant.length < bigDecimal.mant.length) {
            byArray = bigDecimal2.mant;
            byArray2 = bigDecimal.mant;
        } else {
            byArray = bigDecimal.mant;
            byArray2 = bigDecimal2.mant;
        }
        int n5 = byArray.length + byArray2.length - 1;
        n = byArray[0] * byArray2[0] > 9 ? n5 + 1 : n5;
        BigDecimal bigDecimal3 = new BigDecimal();
        byte[] byArray3 = new byte[n];
        int n6 = byArray.length;
        n2 = 0;
        while (n6 > 0) {
            by = byArray[n2];
            if (by != 0) {
                byArray3 = BigDecimal.byteaddsub(byArray3, byArray3.length, byArray2, n5, by, true);
            }
            --n5;
            --n6;
            ++n2;
        }
        bigDecimal3.ind = (byte)(bigDecimal2.ind * bigDecimal.ind);
        bigDecimal3.exp = bigDecimal2.exp + bigDecimal.exp - n3;
        bigDecimal3.mant = n3 == 0 ? byArray3 : BigDecimal.extend(byArray3, byArray3.length + n3);
        return bigDecimal3.finish(mathContext, false);
    }

    public BigDecimal negate() {
        return this.negate(plainMC);
    }

    public BigDecimal negate(MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.ind = -bigDecimal.ind;
        return bigDecimal.finish(mathContext, false);
    }

    public BigDecimal plus() {
        return this.plus(plainMC);
    }

    public BigDecimal plus(MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        if (mathContext.form == 0 && this.form == 0) {
            if (this.mant.length <= mathContext.digits) {
                return this;
            }
            if (mathContext.digits == 0) {
                return this;
            }
        }
        return BigDecimal.clone(this).finish(mathContext, false);
    }

    public BigDecimal pow(BigDecimal bigDecimal) {
        return this.pow(bigDecimal, plainMC);
    }

    public BigDecimal pow(BigDecimal bigDecimal, MathContext mathContext) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        int n4 = bigDecimal.intcheck(-999999999, 999999999);
        BigDecimal bigDecimal2 = this;
        int n5 = mathContext.digits;
        if (n5 == 0) {
            if (bigDecimal.ind == -1) {
                throw new ArithmeticException("Negative power: " + bigDecimal.toString());
            }
            n = 0;
        } else {
            if (bigDecimal.mant.length + bigDecimal.exp > n5) {
                throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
            }
            if (bigDecimal2.mant.length > n5) {
                bigDecimal2 = BigDecimal.clone(bigDecimal2).round(mathContext);
            }
            n2 = bigDecimal.mant.length + bigDecimal.exp;
            n = n5 + n2 + 1;
        }
        MathContext mathContext2 = new MathContext(n, mathContext.form, false, mathContext.roundingMode);
        BigDecimal bigDecimal3 = ONE;
        if (n4 == 0) {
            return bigDecimal3;
        }
        if (n4 < 0) {
            n4 = -n4;
        }
        boolean bl = false;
        n3 = 1;
        while (true) {
            if ((n4 += n4) < 0) {
                bl = true;
                bigDecimal3 = bigDecimal3.multiply(bigDecimal2, mathContext2);
            }
            if (n3 == 31) break;
            if (bl) {
                bigDecimal3 = bigDecimal3.multiply(bigDecimal3, mathContext2);
            }
            ++n3;
        }
        if (bigDecimal.ind < 0) {
            bigDecimal3 = ONE.divide(bigDecimal3, mathContext2);
        }
        return bigDecimal3.finish(mathContext, true);
    }

    public BigDecimal remainder(BigDecimal bigDecimal) {
        return this.dodivide('R', bigDecimal, plainMC, -1);
    }

    public BigDecimal remainder(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('R', bigDecimal, mathContext, -1);
    }

    public BigDecimal subtract(BigDecimal bigDecimal) {
        return this.subtract(bigDecimal, plainMC);
    }

    public BigDecimal subtract(BigDecimal bigDecimal, MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        BigDecimal bigDecimal2 = BigDecimal.clone(bigDecimal);
        bigDecimal2.ind = -bigDecimal2.ind;
        return this.add(bigDecimal2, mathContext);
    }

    public byte byteValueExact() {
        int n = this.intValueExact();
        if (n > 127 | n < -128) {
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        return (byte)n;
    }

    @Override
    public double doubleValue() {
        return Double.valueOf(this.toString());
    }

    public boolean equals(Object object) {
        int n = 0;
        char[] cArray = null;
        char[] cArray2 = null;
        if (object == null) {
            return true;
        }
        if (!(object instanceof BigDecimal)) {
            return true;
        }
        BigDecimal bigDecimal = (BigDecimal)object;
        if (this.ind != bigDecimal.ind) {
            return true;
        }
        if (this.mant.length == bigDecimal.mant.length & this.exp == bigDecimal.exp & this.form == bigDecimal.form) {
            int n2 = this.mant.length;
            n = 0;
            while (n2 > 0) {
                if (this.mant[n] != bigDecimal.mant[n]) {
                    return true;
                }
                --n2;
                ++n;
            }
        } else {
            cArray = this.layout();
            if (cArray.length != (cArray2 = bigDecimal.layout()).length) {
                return true;
            }
            int n3 = cArray.length;
            n = 0;
            while (n3 > 0) {
                if (cArray[n] != cArray2[n]) {
                    return true;
                }
                --n3;
                ++n;
            }
        }
        return false;
    }

    @Override
    public float floatValue() {
        return Float.valueOf(this.toString()).floatValue();
    }

    public String format(int n, int n2) {
        return this.format(n, n2, -1, -1, 1, 4);
    }

    public String format(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        byte[] byArray = null;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        char[] cArray = null;
        int n14 = 0;
        int n15 = 0;
        if (n < -1 | n == 0) {
            this.badarg("format", 1, String.valueOf(n));
        }
        if (n2 < -1) {
            this.badarg("format", 2, String.valueOf(n2));
        }
        if (n3 < -1 | n3 == 0) {
            this.badarg("format", 3, String.valueOf(n3));
        }
        if (n4 < -1) {
            this.badarg("format", 4, String.valueOf(n3));
        }
        if (n5 != 1 && n5 != 2) {
            if (n5 == -1) {
                n5 = 1;
            } else {
                this.badarg("format", 5, String.valueOf(n5));
            }
        }
        if (n6 != 4) {
            try {
                if (n6 == -1) {
                    n6 = 4;
                } else {
                    new MathContext(9, 1, false, n6);
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                this.badarg("format", 6, String.valueOf(n6));
            }
        }
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.form = n4 == -1 ? (byte)0 : (bigDecimal.ind == 0 ? (byte)0 : ((n7 = bigDecimal.exp + bigDecimal.mant.length) > n4 ? (byte)n5 : (n7 < -5 ? (byte)n5 : (byte)0)));
        if (n2 >= 0) {
            while (true) {
                if (bigDecimal.form == 0) {
                    n8 = -bigDecimal.exp;
                } else if (bigDecimal.form == 1) {
                    n8 = bigDecimal.mant.length - 1;
                } else {
                    n9 = (bigDecimal.exp + bigDecimal.mant.length - 1) % 3;
                    if (n9 < 0) {
                        n9 = 3 + n9;
                    }
                    n8 = ++n9 >= bigDecimal.mant.length ? 0 : bigDecimal.mant.length - n9;
                }
                if (n8 == n2) break;
                if (n8 < n2) {
                    byArray = BigDecimal.extend(bigDecimal.mant, bigDecimal.mant.length + n2 - n8);
                    bigDecimal.mant = byArray;
                    bigDecimal.exp -= n2 - n8;
                    if (bigDecimal.exp >= -999999999) break;
                    throw new ArithmeticException("Exponent Overflow: " + bigDecimal.exp);
                }
                n10 = n8 - n2;
                if (n10 > bigDecimal.mant.length) {
                    bigDecimal.mant = BigDecimal.ZERO.mant;
                    bigDecimal.ind = 0;
                    bigDecimal.exp = 0;
                    continue;
                }
                n11 = bigDecimal.mant.length - n10;
                n12 = bigDecimal.exp;
                bigDecimal.round(n11, n6);
                if (bigDecimal.exp - n12 == n10) break;
            }
        }
        char[] cArray2 = bigDecimal.layout();
        if (n > 0) {
            int n16 = cArray2.length;
            n13 = 0;
            while (n16 > 0 && cArray2[n13] != '.' && cArray2[n13] != 'E') {
                --n16;
                ++n13;
            }
            if (n13 > n) {
                this.badarg("format", 1, String.valueOf(n));
            }
            if (n13 < n) {
                cArray = new char[cArray2.length + n - n13];
                n16 = n - n13;
                n14 = 0;
                while (n16 > 0) {
                    cArray[n14] = 32;
                    --n16;
                    ++n14;
                }
                System.arraycopy(cArray2, 0, cArray, n14, cArray2.length);
                cArray2 = cArray;
            }
        }
        if (n3 > 0) {
            int n17 = cArray2.length - 1;
            n13 = cArray2.length - 1;
            while (n17 > 0 && cArray2[n13] != 'E') {
                --n17;
                --n13;
            }
            if (n13 == 0) {
                cArray = new char[cArray2.length + n3 + 2];
                System.arraycopy(cArray2, 0, cArray, 0, cArray2.length);
                n17 = n3 + 2;
                n14 = cArray2.length;
                while (n17 > 0) {
                    cArray[n14] = 32;
                    --n17;
                    ++n14;
                }
                cArray2 = cArray;
            } else {
                n15 = cArray2.length - n13 - 2;
                if (n15 > n3) {
                    this.badarg("format", 3, String.valueOf(n3));
                }
                if (n15 < n3) {
                    cArray = new char[cArray2.length + n3 - n15];
                    System.arraycopy(cArray2, 0, cArray, 0, n13 + 2);
                    n17 = n3 - n15;
                    n14 = n13 + 2;
                    while (n17 > 0) {
                        cArray[n14] = 48;
                        --n17;
                        ++n14;
                    }
                    System.arraycopy(cArray2, n13 + 2, cArray, n14, n15);
                    cArray2 = cArray;
                }
            }
        }
        return new String(cArray2);
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int intValue() {
        return this.toBigInteger().intValue();
    }

    public int intValueExact() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (this.ind == 0) {
            return 1;
        }
        int n4 = this.mant.length - 1;
        if (this.exp < 0) {
            if (!BigDecimal.allzero(this.mant, (n4 += this.exp) + 1)) {
                throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }
            if (n4 < 0) {
                return 1;
            }
            n = 0;
        } else {
            if (this.exp + n4 > 9) {
                throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
            n = this.exp;
        }
        int n5 = 0;
        int n6 = n4 + n;
        for (n2 = 0; n2 <= n6; ++n2) {
            n5 *= 10;
            if (n2 > n4) continue;
            n5 += this.mant[n2];
        }
        if (n4 + n == 9 && (n3 = n5 / 1000000000) != this.mant[0]) {
            if (n5 == Integer.MIN_VALUE && this.ind == -1 && this.mant[0] == 2) {
                return n5;
            }
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        if (this.ind == 1) {
            return n5;
        }
        return -n5;
    }

    @Override
    public long longValue() {
        return this.toBigInteger().longValue();
    }

    public long longValueExact() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = 0L;
        if (this.ind == 0) {
            return 0L;
        }
        int n4 = this.mant.length - 1;
        if (this.exp < 0) {
            if (!BigDecimal.allzero(this.mant, n = (n4 += this.exp) < 0 ? 0 : n4 + 1)) {
                throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }
            if (n4 < 0) {
                return 0L;
            }
            n2 = 0;
        } else {
            if (this.exp + this.mant.length > 18) {
                throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
            n2 = this.exp;
        }
        long l2 = 0L;
        int n5 = n4 + n2;
        for (n3 = 0; n3 <= n5; ++n3) {
            l2 *= 10L;
            if (n3 > n4) continue;
            l2 += (long)this.mant[n3];
        }
        if (n4 + n2 == 18 && (l = l2 / 1000000000000000000L) != (long)this.mant[0]) {
            if (l2 == Long.MIN_VALUE && this.ind == -1 && this.mant[0] == 9) {
                return l2;
            }
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        if (this.ind == 1) {
            return l2;
        }
        return -l2;
    }

    public BigDecimal movePointLeft(int n) {
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.exp -= n;
        return bigDecimal.finish(plainMC, false);
    }

    public BigDecimal movePointRight(int n) {
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.exp += n;
        return bigDecimal.finish(plainMC, false);
    }

    public int scale() {
        if (this.exp >= 0) {
            return 1;
        }
        return -this.exp;
    }

    public BigDecimal setScale(int n) {
        return this.setScale(n, 7);
    }

    public BigDecimal setScale(int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = this.scale();
        if (n5 == n && this.form == 0) {
            return this;
        }
        BigDecimal bigDecimal = BigDecimal.clone(this);
        if (n5 <= n) {
            n3 = n5 == 0 ? bigDecimal.exp + n : n - n5;
            bigDecimal.mant = BigDecimal.extend(bigDecimal.mant, bigDecimal.mant.length + n3);
            bigDecimal.exp = -n;
        } else {
            if (n < 0) {
                throw new ArithmeticException("Negative scale: " + n);
            }
            n4 = bigDecimal.mant.length - (n5 - n);
            bigDecimal = bigDecimal.round(n4, n2);
            if (bigDecimal.exp != -n) {
                bigDecimal.mant = BigDecimal.extend(bigDecimal.mant, bigDecimal.mant.length + 1);
                --bigDecimal.exp;
            }
        }
        bigDecimal.form = 0;
        return bigDecimal;
    }

    public short shortValueExact() {
        int n = this.intValueExact();
        if (n > Short.MAX_VALUE | n < Short.MIN_VALUE) {
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        return (short)n;
    }

    public int signum() {
        return this.ind;
    }

    public java.math.BigDecimal toBigDecimal() {
        return new java.math.BigDecimal(this.unscaledValue(), this.scale());
    }

    public BigInteger toBigInteger() {
        BigDecimal bigDecimal = null;
        int n = 0;
        byte[] byArray = null;
        if (this.exp >= 0 & this.form == 0) {
            bigDecimal = this;
        } else if (this.exp >= 0) {
            bigDecimal = BigDecimal.clone(this);
            bigDecimal.form = 0;
        } else if (-this.exp >= this.mant.length) {
            bigDecimal = ZERO;
        } else {
            bigDecimal = BigDecimal.clone(this);
            n = bigDecimal.mant.length + bigDecimal.exp;
            byArray = new byte[n];
            System.arraycopy(bigDecimal.mant, 0, byArray, 0, n);
            bigDecimal.mant = byArray;
            bigDecimal.form = 0;
            bigDecimal.exp = 0;
        }
        return new BigInteger(new String(bigDecimal.layout()));
    }

    public BigInteger toBigIntegerExact() {
        if (this.exp < 0 && !BigDecimal.allzero(this.mant, this.mant.length + this.exp)) {
            throw new ArithmeticException("Decimal part non-zero: " + this.toString());
        }
        return this.toBigInteger();
    }

    public char[] toCharArray() {
        return this.layout();
    }

    public String toString() {
        return new String(this.layout());
    }

    public BigInteger unscaledValue() {
        BigDecimal bigDecimal = null;
        if (this.exp >= 0) {
            bigDecimal = this;
        } else {
            bigDecimal = BigDecimal.clone(this);
            bigDecimal.exp = 0;
        }
        return bigDecimal.toBigInteger();
    }

    public static BigDecimal valueOf(double d) {
        return new BigDecimal(new Double(d).toString());
    }

    public static BigDecimal valueOf(long l) {
        return BigDecimal.valueOf(l, 0);
    }

    public static BigDecimal valueOf(long l, int n) {
        BigDecimal bigDecimal = null;
        bigDecimal = l == 0L ? ZERO : (l == 1L ? ONE : (l == 10L ? TEN : new BigDecimal(l)));
        if (n == 0) {
            return bigDecimal;
        }
        if (n < 0) {
            throw new NumberFormatException("Negative scale: " + n);
        }
        bigDecimal = BigDecimal.clone(bigDecimal);
        bigDecimal.exp = -n;
        return bigDecimal;
    }

    private char[] layout() {
        int n = 0;
        StringBuilder stringBuilder = null;
        int n2 = 0;
        int n3 = 0;
        char c = '\u0000';
        char[] cArray = null;
        int n4 = 0;
        char[] cArray2 = new char[this.mant.length];
        int n5 = this.mant.length;
        n = 0;
        while (n5 > 0) {
            cArray2[n] = (char)(this.mant[n] + 48);
            --n5;
            ++n;
        }
        if (this.form != 0) {
            stringBuilder = new StringBuilder(cArray2.length + 15);
            if (this.ind == -1) {
                stringBuilder.append('-');
            }
            n2 = this.exp + cArray2.length - 1;
            if (this.form == 1) {
                stringBuilder.append(cArray2[0]);
                if (cArray2.length > 1) {
                    stringBuilder.append('.').append(cArray2, 1, cArray2.length - 1);
                }
            } else {
                n3 = n2 % 3;
                if (n3 < 0) {
                    n3 = 3 + n3;
                }
                n2 -= n3;
                if (++n3 >= cArray2.length) {
                    stringBuilder.append(cArray2, 0, cArray2.length);
                    for (n5 = n3 - cArray2.length; n5 > 0; --n5) {
                        stringBuilder.append('0');
                    }
                } else {
                    stringBuilder.append(cArray2, 0, n3).append('.').append(cArray2, n3, cArray2.length - n3);
                }
            }
            if (n2 != 0) {
                if (n2 < 0) {
                    c = '-';
                    n2 = -n2;
                } else {
                    c = '+';
                }
                stringBuilder.append('E').append(c).append(n2);
            }
            cArray = new char[stringBuilder.length()];
            n5 = stringBuilder.length();
            if (0 != n5) {
                stringBuilder.getChars(0, n5, cArray, 0);
            }
            return cArray;
        }
        if (this.exp == 0) {
            if (this.ind >= 0) {
                return cArray2;
            }
            cArray = new char[cArray2.length + 1];
            cArray[0] = 45;
            System.arraycopy(cArray2, 0, cArray, 1, cArray2.length);
            return cArray;
        }
        int n6 = this.ind == -1 ? 1 : 0;
        int n7 = this.exp + cArray2.length;
        if (n7 < 1) {
            n4 = n6 + 2 - this.exp;
            cArray = new char[n4];
            if (n6 != 0) {
                cArray[0] = 45;
            }
            cArray[n6] = 48;
            cArray[n6 + 1] = 46;
            n5 = -n7;
            n = n6 + 2;
            while (n5 > 0) {
                cArray[n] = 48;
                --n5;
                ++n;
            }
            System.arraycopy(cArray2, 0, cArray, n6 + 2 - n7, cArray2.length);
            return cArray;
        }
        if (n7 > cArray2.length) {
            n4 = n6 + n7;
            cArray = new char[n4];
            if (n6 != 0) {
                cArray[0] = 45;
            }
            System.arraycopy(cArray2, 0, cArray, n6, cArray2.length);
            n5 = n7 - cArray2.length;
            n = n6 + cArray2.length;
            while (n5 > 0) {
                cArray[n] = 48;
                --n5;
                ++n;
            }
            return cArray;
        }
        n4 = n6 + 1 + cArray2.length;
        cArray = new char[n4];
        if (n6 != 0) {
            cArray[0] = 45;
        }
        System.arraycopy(cArray2, 0, cArray, n6, n7);
        cArray[n6 + n7] = 46;
        System.arraycopy(cArray2, n7, cArray, n6 + n7 + 1, cArray2.length - n7);
        return cArray;
    }

    private int intcheck(int n, int n2) {
        int n3 = this.intValueExact();
        if (n3 < n | n3 > n2) {
            throw new ArithmeticException("Conversion overflow: " + n3);
        }
        return n3;
    }

    private BigDecimal dodivide(char c, BigDecimal bigDecimal, MathContext mathContext, int n) {
        int n2;
        int n3 = 0;
        int n4 = 0;
        byte by = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        byte[] byArray = null;
        byte by2 = 0;
        int n10 = 0;
        byte[] byArray2 = null;
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        BigDecimal bigDecimal2 = this;
        if (bigDecimal.ind == 0) {
            throw new ArithmeticException("Divide by 0");
        }
        if (bigDecimal2.ind == 0) {
            if (mathContext.form != 0) {
                return ZERO;
            }
            if (n == -1) {
                return bigDecimal2;
            }
            return bigDecimal2.setScale(n);
        }
        int n11 = mathContext.digits;
        if (n11 > 0) {
            if (bigDecimal2.mant.length > n11) {
                bigDecimal2 = BigDecimal.clone(bigDecimal2).round(mathContext);
            }
            if (bigDecimal.mant.length > n11) {
                bigDecimal = BigDecimal.clone(bigDecimal).round(mathContext);
            }
        } else {
            if (n == -1) {
                n = bigDecimal2.scale();
            }
            n11 = bigDecimal2.mant.length;
            if (n != -bigDecimal2.exp) {
                n11 = n11 + n + bigDecimal2.exp;
            }
            if ((n11 = n11 - (bigDecimal.mant.length - 1) - bigDecimal.exp) < bigDecimal2.mant.length) {
                n11 = bigDecimal2.mant.length;
            }
            if (n11 < bigDecimal.mant.length) {
                n11 = bigDecimal.mant.length;
            }
        }
        if ((n2 = bigDecimal2.exp - bigDecimal.exp + bigDecimal2.mant.length - bigDecimal.mant.length) < 0 && c != 'D') {
            if (c == 'I') {
                return ZERO;
            }
            return BigDecimal.clone(bigDecimal2).finish(mathContext, false);
        }
        BigDecimal bigDecimal3 = new BigDecimal();
        bigDecimal3.ind = (byte)(bigDecimal2.ind * bigDecimal.ind);
        bigDecimal3.exp = n2;
        bigDecimal3.mant = new byte[n11 + 1];
        int n12 = n11 + n11 + 1;
        byte[] byArray3 = BigDecimal.extend(bigDecimal2.mant, n12);
        int n13 = n12;
        byte[] byArray4 = bigDecimal.mant;
        int n14 = n12;
        int n15 = byArray4[0] * 10 + 1;
        if (byArray4.length > 1) {
            n15 += byArray4[1];
        }
        int n16 = 0;
        block0: while (true) {
            n3 = 0;
            block1: while (n13 >= n14) {
                int n17;
                if (n13 == n14) {
                    block42: {
                        n17 = n13;
                        n4 = 0;
                        while (n17 > 0) {
                            by = n4 < byArray4.length ? byArray4[n4] : (byte)0;
                            if (byArray3[n4] < by) break block1;
                            if (byArray3[n4] <= by) {
                                --n17;
                                ++n4;
                                continue;
                            }
                            break block42;
                        }
                        bigDecimal3.mant[n16] = (byte)(++n3);
                        ++n16;
                        byArray3[0] = 0;
                        break block0;
                    }
                    n5 = byArray3[0];
                } else {
                    n5 = byArray3[0] * 10;
                    if (n13 > 1) {
                        n5 += byArray3[1];
                    }
                }
                n6 = n5 * 10 / n15;
                if (n6 == 0) {
                    n6 = 1;
                }
                n3 += n6;
                if ((byArray3 = BigDecimal.byteaddsub(byArray3, n13, byArray4, n14, -n6, true))[0] != 0) continue;
                n17 = n13 - 2;
                for (n7 = 0; n7 <= n17 && byArray3[n7] == 0; ++n7) {
                    --n13;
                }
                if (n7 == 0) continue;
                System.arraycopy(byArray3, n7, byArray3, 0, n13);
            }
            if (n16 != 0 | n3 != 0) {
                bigDecimal3.mant[n16] = (byte)n3;
                if (++n16 == n11 + 1 || byArray3[0] == 0) break;
            }
            if (n >= 0 && -bigDecimal3.exp > n || c != 'D' && bigDecimal3.exp <= 0) break;
            --bigDecimal3.exp;
            --n14;
        }
        if (n16 == 0) {
            n16 = 1;
        }
        if (c == 'I' | c == 'R') {
            if (n16 + bigDecimal3.exp > n11) {
                throw new ArithmeticException("Integer overflow");
            }
            if (c == 'R') {
                if (bigDecimal3.mant[0] == 0) {
                    return BigDecimal.clone(bigDecimal2).finish(mathContext, false);
                }
                if (byArray3[0] == 0) {
                    return ZERO;
                }
                bigDecimal3.ind = bigDecimal2.ind;
                n8 = n11 + n11 + 1 - bigDecimal2.mant.length;
                bigDecimal3.exp = bigDecimal3.exp - n8 + bigDecimal2.exp;
                n9 = n13;
                for (n4 = n9 - 1; n4 >= 1 && bigDecimal3.exp < bigDecimal2.exp & bigDecimal3.exp < bigDecimal.exp && byArray3[n4] == 0; --n4) {
                    --n9;
                    ++bigDecimal3.exp;
                }
                if (n9 < byArray3.length) {
                    byArray = new byte[n9];
                    System.arraycopy(byArray3, 0, byArray, 0, n9);
                    byArray3 = byArray;
                }
                bigDecimal3.mant = byArray3;
                return bigDecimal3.finish(mathContext, false);
            }
        } else if (byArray3[0] != 0 && (by2 = bigDecimal3.mant[n16 - 1]) % 5 == 0) {
            bigDecimal3.mant[n16 - 1] = (byte)(by2 + 1);
        }
        if (n >= 0) {
            if (n16 != bigDecimal3.mant.length) {
                bigDecimal3.exp -= bigDecimal3.mant.length - n16;
            }
            n10 = bigDecimal3.mant.length - (-bigDecimal3.exp - n);
            bigDecimal3.round(n10, mathContext.roundingMode);
            if (bigDecimal3.exp != -n) {
                bigDecimal3.mant = BigDecimal.extend(bigDecimal3.mant, bigDecimal3.mant.length + 1);
                --bigDecimal3.exp;
            }
            return bigDecimal3.finish(mathContext, true);
        }
        if (n16 == bigDecimal3.mant.length) {
            bigDecimal3.round(mathContext);
            n16 = n11;
        } else {
            if (bigDecimal3.mant[0] == 0) {
                return ZERO;
            }
            byArray2 = new byte[n16];
            System.arraycopy(bigDecimal3.mant, 0, byArray2, 0, n16);
            bigDecimal3.mant = byArray2;
        }
        return bigDecimal3.finish(mathContext, true);
    }

    private void bad(char[] cArray) {
        throw new NumberFormatException("Not a number: " + String.valueOf(cArray));
    }

    private void badarg(String string, int n, String string2) {
        throw new IllegalArgumentException("Bad argument " + n + " to " + string + ": " + string2);
    }

    private static final byte[] extend(byte[] byArray, int n) {
        if (byArray.length == n) {
            return byArray;
        }
        byte[] byArray2 = new byte[n];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        return byArray2;
    }

    private static final byte[] byteaddsub(byte[] byArray, int n, byte[] byArray2, int n2, int n3, boolean bl) {
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = byArray.length;
        int n8 = byArray2.length;
        int n9 = n2 - 1;
        int n10 = n9;
        int n11 = n - 1;
        if (n10 < n11) {
            n10 = n11;
        }
        byte[] byArray3 = null;
        if (bl && n10 + 1 == n7) {
            byArray3 = byArray;
        }
        if (byArray3 == null) {
            byArray3 = new byte[n10 + 1];
        }
        boolean bl2 = false;
        if (n3 == 1) {
            bl2 = true;
        } else if (n3 == -1) {
            bl2 = true;
        }
        int n12 = 0;
        for (n4 = n10; n4 >= 0; --n4) {
            if (n11 >= 0) {
                if (n11 < n7) {
                    n12 += byArray[n11];
                }
                --n11;
            }
            if (n9 >= 0) {
                if (n9 < n8) {
                    n12 = bl2 ? (n3 > 0 ? (n12 += byArray2[n9]) : (n12 -= byArray2[n9])) : (n12 += byArray2[n9] * n3);
                }
                --n9;
            }
            if (n12 < 10 && n12 >= 0) {
                byArray3[n4] = (byte)n12;
                n12 = 0;
                continue;
            }
            n5 = n12 + 90;
            byArray3[n4] = bytedig[n5];
            n12 = bytecar[n5];
        }
        if (n12 == 0) {
            return byArray3;
        }
        byte[] byArray4 = null;
        if (bl && n10 + 2 == byArray.length) {
            byArray4 = byArray;
        }
        if (byArray4 == null) {
            byArray4 = new byte[n10 + 2];
        }
        byArray4[0] = (byte)n12;
        if (n10 < 10) {
            int n13 = n10 + 1;
            n6 = 0;
            while (n13 > 0) {
                byArray4[n6 + 1] = byArray3[n6];
                --n13;
                ++n6;
            }
        } else {
            System.arraycopy(byArray3, 0, byArray4, 1, n10 + 1);
        }
        return byArray4;
    }

    private static final byte[] diginit() {
        int n = 0;
        int n2 = 0;
        byte[] byArray = new byte[190];
        for (n = 0; n <= 189; ++n) {
            n2 = n - 90;
            if (n2 >= 0) {
                byArray[n] = (byte)(n2 % 10);
                BigDecimal.bytecar[n] = (byte)(n2 / 10);
                continue;
            }
            byArray[n] = (byte)((n2 += 100) % 10);
            BigDecimal.bytecar[n] = (byte)(n2 / 10 - 10);
        }
        return byArray;
    }

    private static final BigDecimal clone(BigDecimal bigDecimal) {
        BigDecimal bigDecimal2 = new BigDecimal();
        bigDecimal2.ind = bigDecimal.ind;
        bigDecimal2.exp = bigDecimal.exp;
        bigDecimal2.form = bigDecimal.form;
        bigDecimal2.mant = bigDecimal.mant;
        return bigDecimal2;
    }

    private void checkdigits(BigDecimal bigDecimal, int n) {
        if (n == 0) {
            return;
        }
        if (this.mant.length > n && !BigDecimal.allzero(this.mant, n)) {
            throw new ArithmeticException("Too many digits: " + this.toString());
        }
        if (bigDecimal == null) {
            return;
        }
        if (bigDecimal.mant.length > n && !BigDecimal.allzero(bigDecimal.mant, n)) {
            throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
        }
    }

    private BigDecimal round(MathContext mathContext) {
        return this.round(mathContext.digits, mathContext.roundingMode);
    }

    private BigDecimal round(int n, int n2) {
        boolean bl = false;
        byte by = 0;
        byte[] byArray = null;
        int n3 = this.mant.length - n;
        if (n3 <= 0) {
            return this;
        }
        this.exp += n3;
        byte by2 = this.ind;
        byte[] byArray2 = this.mant;
        if (n > 0) {
            this.mant = new byte[n];
            System.arraycopy(byArray2, 0, this.mant, 0, n);
            bl = true;
            by = byArray2[n];
        } else {
            this.mant = BigDecimal.ZERO.mant;
            this.ind = 0;
            bl = false;
            by = n == 0 ? byArray2[0] : (byte)0;
        }
        byte by3 = 0;
        if (n2 == 4) {
            if (by >= 5) {
                by3 = by2;
            }
        } else if (n2 == 7) {
            if (!BigDecimal.allzero(byArray2, n)) {
                throw new ArithmeticException("Rounding necessary");
            }
        } else if (n2 == 5) {
            if (by > 5) {
                by3 = by2;
            } else if (by == 5 && !BigDecimal.allzero(byArray2, n + 1)) {
                by3 = by2;
            }
        } else if (n2 == 6) {
            if (by > 5) {
                by3 = by2;
            } else if (by == 5) {
                if (!BigDecimal.allzero(byArray2, n + 1)) {
                    by3 = by2;
                } else if (this.mant[this.mant.length - 1] % 2 != 0) {
                    by3 = by2;
                }
            }
        } else if (n2 != 1) {
            if (n2 == 0) {
                if (!BigDecimal.allzero(byArray2, n)) {
                    by3 = by2;
                }
            } else if (n2 == 2) {
                if (by2 > 0 && !BigDecimal.allzero(byArray2, n)) {
                    by3 = by2;
                }
            } else if (n2 == 3) {
                if (by2 < 0 && !BigDecimal.allzero(byArray2, n)) {
                    by3 = by2;
                }
            } else {
                throw new IllegalArgumentException("Bad round value: " + n2);
            }
        }
        if (by3 != 0) {
            if (this.ind == 0) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = by3;
            } else {
                if (this.ind == -1) {
                    by3 = -by3;
                }
                if ((byArray = BigDecimal.byteaddsub(this.mant, this.mant.length, BigDecimal.ONE.mant, 1, by3, bl)).length > this.mant.length) {
                    ++this.exp;
                    System.arraycopy(byArray, 0, this.mant, 0, this.mant.length);
                } else {
                    this.mant = byArray;
                }
            }
        }
        if (this.exp > 999999999) {
            throw new ArithmeticException("Exponent Overflow: " + this.exp);
        }
        return this;
    }

    private static final boolean allzero(byte[] byArray, int n) {
        int n2 = 0;
        if (n < 0) {
            n = 0;
        }
        int n3 = byArray.length - 1;
        for (n2 = n; n2 <= n3; ++n2) {
            if (byArray[n2] == 0) continue;
            return true;
        }
        return false;
    }

    private BigDecimal finish(MathContext mathContext, boolean bl) {
        int n = 0;
        int n2 = 0;
        byte[] byArray = null;
        int n3 = 0;
        int n4 = 0;
        if (mathContext.digits != 0 && this.mant.length > mathContext.digits) {
            this.round(mathContext);
        }
        if (bl && mathContext.form != 0) {
            n = this.mant.length;
            for (n2 = n - 1; n2 >= 1 && this.mant[n2] == 0; --n2) {
                --n;
                ++this.exp;
            }
            if (n < this.mant.length) {
                byArray = new byte[n];
                System.arraycopy(this.mant, 0, byArray, 0, n);
                this.mant = byArray;
            }
        }
        this.form = 0;
        int n5 = this.mant.length;
        n2 = 0;
        while (n5 > 0) {
            block21: {
                block22: {
                    block23: {
                        if (this.mant[n2] == 0) break block21;
                        if (n2 > 0) {
                            byArray = new byte[this.mant.length - n2];
                            System.arraycopy(this.mant, n2, byArray, 0, this.mant.length - n2);
                            this.mant = byArray;
                        }
                        if ((n3 = this.exp + this.mant.length) > 0) {
                            if (n3 > mathContext.digits && mathContext.digits != 0) {
                                this.form = (byte)mathContext.form;
                            }
                            if (n3 - 1 <= 999999999) {
                                return this;
                            }
                        } else if (n3 < -5) {
                            this.form = (byte)mathContext.form;
                        }
                        if (!(--n3 < -999999999 | n3 > 999999999)) break block22;
                        if (this.form != 2) break block23;
                        n4 = n3 % 3;
                        if (n4 < 0) {
                            n4 = 3 + n4;
                        }
                        if ((n3 -= n4) >= -999999999 && n3 <= 999999999) break block22;
                    }
                    throw new ArithmeticException("Exponent Overflow: " + n3);
                }
                return this;
            }
            --n5;
            ++n2;
        }
        this.ind = 0;
        if (mathContext.form != 0) {
            this.exp = 0;
        } else if (this.exp > 0) {
            this.exp = 0;
        } else if (this.exp < -999999999) {
            throw new ArithmeticException("Exponent Overflow: " + this.exp);
        }
        this.mant = BigDecimal.ZERO.mant;
        return this;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((BigDecimal)object);
    }
}

